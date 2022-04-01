package com.spider.core.common.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author professor X
 * @created 2018/8/14
 */
public class HttpRequestUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);
    private static CloseableHttpClient httpClient;
    /**
     * 设置连接超时时间，单位毫秒
     */
    private static final int CONNECT_TIME_OUT = 2000;
    /**
     * 设置从connect Manager(连接池)获取Connection 超时时间，单位毫秒
     */
    private static final int REQUEST_TIME_OUT = 2000;
    /**
     * 请求获取数据的超时时间(即响应时间)，单位毫秒
     */
    private static final int SOCKET_TIME_OUT = 10000;


    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(100);
        cm.setDefaultMaxPerRoute(20);
        cm.setDefaultMaxPerRoute(50);
        httpClient = HttpClients.custom().setConnectionManager(cm).build();
    }

    public static String get(String url, Map<String, Object> params) {
        url = transGetReqUrl(url, params);
        logger.debug(url);
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpGet httpGet = new HttpGet(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(REQUEST_TIME_OUT).setSocketTimeout(SOCKET_TIME_OUT).build();
            httpGet.setConfig(requestConfig);
            httpGet.setConfig(requestConfig);
            httpGet.addHeader("Content-type", "application/json; charset=utf-8");
            httpGet.setHeader("Accept", "application/json");
            response = httpClient.execute(httpGet);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            result = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != response) {
                    response.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.debug(result);
        return result;
    }

    public static String post(String url, String jsonString) {
        CloseableHttpResponse response = null;
        BufferedReader in = null;
        String result = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(CONNECT_TIME_OUT).setConnectionRequestTimeout(REQUEST_TIME_OUT).setSocketTimeout(SOCKET_TIME_OUT).build();
            httpPost.setConfig(requestConfig);
            httpPost.setConfig(requestConfig);
            httpPost.addHeader("Content-type", "application/json; charset=utf-8");
            httpPost.setHeader("Accept", "application/json");
            httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
            response = httpClient.execute(httpPost);
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder();
            String line;
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line).append(NL);
            }
            result = sb.toString();
        } catch (ConnectTimeoutException e) {
            logger.error("连接超时", e);
        } catch (IOException e) {
            logger.error("http调用异常", e);

        } finally {
            try {
                if (null != response) {
                    response.close();
                }
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
                logger.error("响应关闭异常", e);
            }
        }
        return result;
    }

    private static String transGetReqUrl(String url, Map<String, Object> params) {
        StringBuilder builder = new StringBuilder(url);
        builder.append("?");
        for (String key : params.keySet()) {
            builder.append(key);
            builder.append("=");
            builder.append(params.get(key));
            builder.append("&");
        }
        builder.append("rdm=");
        builder.append(Math.random());
        return builder.toString();
    }

    /**
     * 请求数据拼装
     *
     * @param appId  应用ID
     * @param method 方法
     * @param appKey 应用密钥
     * @param param  请求内容
     * @return 响应
     */
    public static String assemblingData(String appId, String method, String appKey, Object param) {
        String data = FastJsonUtil.toJSONString(param);
        String dataEnc = EncryptionUtil.des_encrypt(data, appKey);
        String timestamp = DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN);
        String sign = EncryptionUtil.genSign(appId, method, timestamp, dataEnc);
        Map reqMap = new HashMap(10) {{
            put("app_id", appId);
            put("method", method);
            put("timestamp", timestamp);
            put("sign", sign);
            put("data", dataEnc);
        }};
        return FastJsonUtil.toJSONString(reqMap);
    }
}
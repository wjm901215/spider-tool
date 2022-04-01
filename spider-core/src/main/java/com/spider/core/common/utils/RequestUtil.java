package com.spider.core.common.utils;

import com.spider.core.common.base.RqsBody;
import com.spider.core.common.constant.Constant;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 请求工具类
 *
 * @author Spiderman
 * @version $Id: com.zyiot.tet.common.util.RequestUtil,v 0.1 2018/8/8 14:40 Exp $$
 */
public class RequestUtil {
    /**
     * POST请求，获取请求body内容
     * @param request
     * @return
     */
    public static RqsBody getInputJSON(HttpServletRequest request) {
        RqsBody rqsBody = new RqsBody();
        try {
            StringBuilder buffer = getStringBuilder(request);
            rqsBody = FastJsonUtil.getObject(buffer.toString(), RqsBody.class);
            return rqsBody;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rqsBody;
    }

    public static String getInputXml(HttpServletRequest request) {
        try {
            StringBuilder buffer = getStringBuilder(request);
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static StringBuilder getStringBuilder(HttpServletRequest request) throws IOException {
        InputStream in = request.getInputStream();
        BufferedReader bf = new BufferedReader(new InputStreamReader(in, Constant.UTF8));
        StringBuilder buffer = new StringBuilder();
        char[] buff = new char[2048];
        int bytesRead;
        while (-1 != (bytesRead = bf.read(buff, 0, buff.length))) {
            buffer.append(buff, 0, bytesRead);
        }
        return buffer;
    }

    /**
     * form-data提交
     * @param request
     * @return
     */
    public static RqsBody getParamData(HttpServletRequest request) {
        RqsBody rqsBody = new RqsBody();
        rqsBody.setAppid(request.getParameter("appid"));
        rqsBody.setSid(request.getParameter("sid"));
        rqsBody.setMethod(request.getParameter("method"));
        rqsBody.setSign(request.getParameter("sign"));
        rqsBody.setTimestamp(request.getParameter("timestamp"));
        rqsBody.setData(request.getParameter("data"));
        return rqsBody;
    }
}

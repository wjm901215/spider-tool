package com.spider.core.common.utils;

import com.spider.core.common.constant.Constant;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * DES 工具类
 *
 * @author Spiderman
 * @version $Id: EncryptionUtil,v 0.1 2017/12/11 19:36 Exp $$
 */
public class EncryptionUtil {

    /**
     * DES 加密
     *
     * @param data 待加密数据
     * @param key  加密key
     * @return
     */
    public static String des_encrypt(String data, String key) {
        try {
            if (StringUtils.isBlank(key)) {
                key = Constant._DES_PWD;
            }
            byte[] bt = encrypt(data.getBytes(Constant.CHARSET), key.getBytes(Constant.CHARSET));
            String result = Base64.encodeBase64String(bt);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
    public static String md5AndBase64(String content) {
        try{
            MessageDigest messageDigest = MessageDigest.getInstance(Constant.SECURITY_MD5);
            messageDigest.reset();
            messageDigest.update(content.getBytes(Constant.CHARSET));
            byte[] byteArray = messageDigest.digest();
            StringBuffer md5StrBuff = new StringBuffer();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                    md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
                else
                    md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
            String mdstr = md5StrBuff.toString();
            return Base64.encodeBase64String(mdstr.getBytes());
        }catch(Exception e){

        }
        return null;
        // return encrypt(content,Constant.SECURITY_MD5);
    }
    /**
     * DES 解密
     *
     * @param data 待解密数据
     * @param key  解密key
     * @return
     */
    public static String des_decrypt(String data, String key) {
        try {
            if (StringUtils.isBlank(data)) {
                return null;
            }
            if (StringUtils.isBlank(key)) {
                key = Constant._DES_PWD;
            }
            byte[] buf = Base64.decodeBase64(data);
            byte[] bt = decrypt(buf, key.getBytes(Constant.CHARSET));
            return new String(bt);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *  请求签名
     * @param appId 应用ID
     * @param method 请求方法
     * @param timestamp 时间戳
     * @param dataEnc 加密串
     * @return 签名
     */
    static String genSign(String appId, String method, String timestamp, String dataEnc) {
        String sign = "";
        StringBuilder dataStr = new StringBuilder();
        dataStr.append(appId).append(method).append(timestamp).append(dataEnc);
        try {
            String singStr = new String(dataStr.toString().getBytes(), "UTF-8");
            sign = EncryptionUtil.md5AndBase64(singStr);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sign;
    }
    /**
     * des加密key生成
     * @return
     */
    public static String des_keyGen() {

        //初始化密钥
        try {
            byte[] key = initKey();
            return Base64.encodeBase64String(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }
    /**
     * 生产密钥
     * java 7只支持56位 密钥
     * Bouncy Castle 支持64位密钥
     *
     * @return byte[] 二进制密钥
     * @throws Exception
     */
    public static byte[] initKey() throws Exception {
        /*
         * 实例化密钥生成器
         * 若要使用64位密钥注意替换
         * 讲下述代码中的
         * KeyGenerator.getInstance(KEY_ALGORITHM);
         * 替换为
         * KeyGenerator.getInstance(KEY_ALGORITHM，"BC");
         */
        KeyGenerator kg = KeyGenerator.getInstance(Constant.SECURITY_DES);
        /*
         * 初始化密钥生成器
         * 若要使用64位密钥注意替换
         * 将下述的代码 kg.init(56);
         * 替换为 kg.init(64);
         */
        kg.init(56);
        //生成密钥
        SecretKey secretKey = kg.generateKey();
        //获得密钥的二进制编码形式
        return secretKey.getEncoded();
    }

    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Constant.SECURITY_DES);
        SecretKey secureKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(Constant.SECURITY_DES);
        cipher.init(Cipher.ENCRYPT_MODE, secureKey, sr);
        return cipher.doFinal(data);
    }

    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(Constant.SECURITY_DES);
        SecretKey secureKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(Constant.SECURITY_DES);
        cipher.init(Cipher.DECRYPT_MODE, secureKey, sr);
        return cipher.doFinal(data);
    }
}

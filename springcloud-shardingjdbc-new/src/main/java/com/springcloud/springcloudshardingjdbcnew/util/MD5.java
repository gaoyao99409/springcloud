package com.springcloud.springcloudshardingjdbcnew.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.base.Charsets;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;

public class MD5 {


    public final static String KEY = "morefun_2018!@#";

    public final static String getMD5(String s) {
        HashCode hashCode = Hashing.md5().hashString(s, Charsets.UTF_8);
        return hashCode.toString();
    }

    public static String string2MD5(String inStr) {

        HashCode hashCode = Hashing.md5().hashString(KEY + inStr, Charsets.UTF_8);
        return hashCode.toString();
    }

    public static String string2MD5(String inStr, String key) {

        HashCode hashCode = Hashing.md5().hashString(key + inStr, Charsets.UTF_8);
        return hashCode.toString();
    }

    public static String generateToken(String userId, long time) {
        return getMD5(KEY + userId + time);
    }

    public static String checkKm(long time) {
        return getMD5(KEY + time);
    }

    /**
     * 拦截器里的verify验证
     *
     * @param time
     * @param verify
     * @return
     */
    public static boolean checkVerify(String time, String verify) {
        if (verify.equals(getMD5(KEY + time))) {
            return true;
        }
        return false;
    }

    /**
     * 验证短信码
     *
     * @param mobile
     * @param captchaSign
     * @return
     */
    public static boolean checkCaptcha(String mobile, String captchaSign) {
        if (captchaSign != null) {
            if (captchaSign.equals(getMD5(KEY + mobile))) {
                return true;
            }
        }
        return false;
    }

    /**
     * 验证红包是否有效
     *
     * @param
     * @param
     * @return
     */
    public static boolean checkRedPacket(Integer ordersId, Integer userId, String md) {
        if (md != null && ordersId != null && userId != null) {
            if (md.equals(getMD5(KEY + ordersId + userId))) {
                return true;
            }
        }
        return false;
    }

    public static String compute(String s) {

        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static String genImageMD5URL(String urlString) {

        String fileName = urlString.substring(urlString.lastIndexOf("/") + 1, urlString.length());
        return urlString + "?sign=" + getMD5(fileName + KEY);
    }

    /**
     * 根据文件获取MD5
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static String getMd5ByFile(File file) throws FileNotFoundException {
        String value = null;
        FileInputStream in = new FileInputStream(file);
        try {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    public static void main(String[] args) {

    }

    /**
     * md5 32位加密
     *
     * @param s
     * @return
     */
    public static String md5(String s) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据参数 排序 拼接 生成加密内容
     *
     * @param parameterMap
     * @return
     */
    public static String genMd5Str(Map<String, Object> parameterMap, String keyStr) {
        //参数按照key 的字母顺序升序排列
        TreeMap<String, String> param = new TreeMap<String, String>(new Comparator<String>() {
            @Override
            public int compare(String obj1, String obj2) {

                return obj1.compareTo(obj2);
            }

        });

        parameterMap.keySet().forEach(key -> {
            param.put(key, String.valueOf(parameterMap.get(key)));
        });

        StringBuilder paramBuilder = new StringBuilder();
        param.keySet().forEach(key -> {
            if (!"md5".equals(key)) {
                paramBuilder.append(key).append("=").append(parameterMap.get(key)).append("&");
            }
        });

        String md5Local = "";
        if (paramBuilder.length() != 0) {
            md5Local = MD5.string2MD5(paramBuilder.substring(0, paramBuilder.length() - 1).toString(), keyStr);
//            Object appId = parameterMap.get("appId");
//            if (appId == null) {
//                md5Local = MD5.string2MD5(paramBuilder.substring(0, paramBuilder.length() - 1).toString());
//            } else {
//                md5Local = MD5.string2MD5(paramBuilder.substring(0, paramBuilder.length() - 1).toString(), SecretEnum.getSecret(String.valueOf(appId)));
//            }
        }

        return md5Local;
    }

    public static String genMd5Str(Map<String, Object> parameterMap) {
        return genMd5Str(parameterMap, KEY);
    }

}

package com.blackbread.utils;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

public class URLUtil {
    /**
     * 除去数组中的空值和签名参数
     *
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("")
                    || key.equalsIgnoreCase("sign")
                    || key.equalsIgnoreCase("sign_type")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        return prestr;
    }

    /**
     * 将业务部门发送来的请求进行分析
     * 这里对sign进行了url解码
     *
     * @param body
     * @return
     */
    public static Map<String, String> createParamsMap(String body) {
        Map<String, String> params = new HashMap<String, String>();
        String[] items = body.split("&");
        for (String item : items) {
            String[] pair = item.split("=");
            params.put(pair[0], pair[1]);
        }
        return params;
    }

    /**
     * 将验证返回数据转换成map
     *
     * @param body 验证请求返回的数据
     * @return
     */
    public static Map<String, String> createVerifyParamsMap(String body) {
        try {
            Map<String, String> params = new HashMap<String, String>();
            String decodeResult = "";
            //对body进行解码
            decodeResult = URLDecoder.decode(body, "UTF-8");
            String[] items = decodeResult.split("&");
            for (String item : items) {
                String[] pair = item.split("=");
                params.put(pair[0], pair[1]);
            }
            return params;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}

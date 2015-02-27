package com.blackbread.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.SignatureException;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {
    
    //十六进制下数字到字符的映射数组   
    private final static String[] hexDigits = {"0", "1", "2", "3", "4",   
        "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};   
       
      
    public static String generatePassword(String inputString){   
        return encodeByMD5(inputString);   
     }   
       
        
    public static boolean validatePassword(String password, String inputString){   
        if(password.equals(encodeByMD5(inputString))){   
            return true;   
         } else{   
            return false;   
         }   
     }   
      
    private static String encodeByMD5(String originString){   
        if (originString != null){   
            try{   
                //创建具有指定算法名称的信息摘要   
                 MessageDigest md = MessageDigest.getInstance("MD5");   
                //使用指定的字节数组对摘要进行最后更新，然后完成摘要计算   
                byte[] results = md.digest(originString.getBytes());   
                //将得到的字节数组变成字符串返回   
                 String resultString = byteArrayToHexString(results);   
                return resultString.toUpperCase();   
             } catch(Exception ex){   
                 ex.printStackTrace();   
             }   
         }   
        return null;   
     }   
       
      
    private static String byteArrayToHexString(byte[] b){   
         StringBuffer resultSb = new StringBuffer();   
        for (int i = 0; i < b.length; i++){   
             resultSb.append(byteToHexString(b[i]));   
         }   
        return resultSb.toString();   
     }   
       
      
    private static String byteToHexString(byte b){   
        int n = b;   
        if (n < 0)   
             n = 256 + n;   
        int d1 = n / 16;   
        int d2 = n % 16;   
        return hexDigits[d1] + hexDigits[d2];   
     }   
    
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static String sign(String text, String key, String input_charset) {
    	text = text + key;
        return DigestUtils.md5Hex(getContentBytes(text, input_charset));
    }
    
    /**
     * 签名字符串
     * @param text 需要签名的字符串
     * @param sign 签名结果
     * @param key 密钥
     * @param input_charset 编码格式
     * @return 签名结果
     */
    public static boolean verify(String text, String sign, String key, String input_charset) {
    	text = text + key;
    	String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
    	if(mysign.equals(sign)) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }

    /**
     * @param content
     * @param charset
     * @return
     * @throws SignatureException
     * @throws UnsupportedEncodingException 
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }
    public static void main(String[] args) {
		String ssss=MD5.sign("abc",  "1233444", "UTF-8");
		System.out.println(ssss);
		System.out.println(MD5.verify("abc", ssss, "1233444", "UTF-8"));
	}
}

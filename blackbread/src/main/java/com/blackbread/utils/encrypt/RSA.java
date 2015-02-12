package com.blackbread.utils.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


/**
 * 供服务端使用，对请求进行签名和验证
 * 
 */
public class RSA {
    private static final Logger logger = LoggerFactory
            .getLogger(RSA.class);
	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	
	/**
	 * RSA签名
	 * 服务端用来对字符串内容进行签名
	 * 1、向Alipay发送请求时（使用提交给Alipay的私钥）
	 * 2、向业务部门发送通知时 （使用自己的私钥）
	 * @param content 待签名字符串
	 * @param privateKey 的私钥
	 * @param inputCharset 编码格式
	 * @return 签名值
	 */
	public static String sign(String content,String privateKey,String inputCharset){
		try{
			PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
			KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);
			Signature signature= Signature.getInstance(SIGN_ALGORITHMS);
			signature.initSign(priKey);
			signature.update(content.getBytes(inputCharset));
			
			byte[] signed = signature.sign();
			return Base64.encode(signed);
			
		}
		catch (Exception e) 
        {
            logger.error("rsa sign error",e);
        	e.printStackTrace();
        }
		return null;
        
	}
	
	/**
	* RSA验签名检查
	* 服务端用来对第三方发来的内容进行签名验证
	* 1、对Alipay返回的通知进行签名验证
	* 2、对业务部门发送来的请求进行签名验证
	* @param content 待签名数据
	* @param sign 签名值
	* @param publicKey 对方公钥
	* @param inputCharset 编码格式
	* @return 布尔值
	*/
	public static boolean verify(String content, String sign, String publicKey, String inputCharset)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(publicKey);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(inputCharset) );
		
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
            logger.error("rsa verify error",e);
			e.printStackTrace();
		}
		return false;
		
	}
	
	
	/**
	 * 加密字符串
	 * @param content 被加密字符 
	 * @param publicKeyStr 公钥
	 * @return 加密后的密文
	 * @throws Exception
	 */
	public static String encryptStrByPubKey(String content,String publicKeyStr){
		
		try {
			RSAPublicKey publicKey;
			publicKey = (RSAPublicKey) getPublicKeyFromStr(publicKeyStr);
			//模  
	        String modulus = publicKey.getModulus().toString();  
	        //公钥指数  
	        String public_exponent = publicKey.getPublicExponent().toString();  
	        //使用模和指数生成公钥和私钥  
	        RSAPublicKey pubKey = getPublicKey(modulus, public_exponent);  
	        //
	        String enStr = encryptByPublicKey(content, pubKey);
	        //
	        return enStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
        
	}
	
	/**
	 * 解密字符串
	 * @param content 密文
	 * @param priviteKeyStr 私钥
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public static String decryptStrByPriKey(String content,String priviteKeyStr){
        
		
		try {
			RSAPrivateKey privateKey;
			privateKey = (RSAPrivateKey) getPrivateKeyFromStr(priviteKeyStr);
			//模  
	        String modulus = privateKey.getModulus().toString();
			//私钥指数  
	        String private_exponent = privateKey.getPrivateExponent().toString();
	        RSAPrivateKey priKey = getPrivateKey(modulus, private_exponent); 
	        String deStr = decryptByPrivateKey(content, priKey);
	        return deStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
    /** 
     * 使用模和指数生成RSA公钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */  
    public static RSAPublicKey getPublicKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);  
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();    
        }  
        return null;  
    }  
  
    /** 
     * 使用模和指数生成RSA私钥 
     * 注意：【此代码用了默认补位方式，为RSA/None/PKCS1Padding，不同JDK默认的补位方式可能不同，如Android默认是RSA 
     * /None/NoPadding】 
     *  
     * @param modulus 
     *            模 
     * @param exponent 
     *            指数 
     * @return 
     */  
    public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {  
        try {  
            BigInteger b1 = new BigInteger(modulus);  
            BigInteger b2 = new BigInteger(exponent);  
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");  
            RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);  
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    /** 
     * 公钥加密 
     *  
     * @param data 
     * @param publicKey 
     * @return 
     * @throws Exception 
     */  
    public static String encryptByPublicKey(String data, RSAPublicKey publicKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        // 模长  
        int key_len = publicKey.getModulus().bitLength() / 8;  
        // 加密数据长度 <= 模长-11  
        String[] datas = splitString(data, key_len - 11);  
        String mi = "";  
        //如果明文长度大于模长-11则要分组加密  
        for (String s : datas) {  
            mi += bcd2Str(cipher.doFinal(s.getBytes()));  
        }  
        return mi;  
    }  
  
    /** 
     * 私钥解密 
     *  
     * @param data 
     * @param privateKey 
     * @return 
     * @throws Exception 
     */  
    public static String decryptByPrivateKey(String data, RSAPrivateKey privateKey)  
            throws Exception {  
        Cipher cipher = Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        //模长  
        int key_len = privateKey.getModulus().bitLength() / 8;  
        byte[] bytes = data.getBytes();  
        byte[] bcd = ASCII_To_BCD(bytes, bytes.length);  
//        System.err.println(bcd.length);  
        //如果密文长度大于模长则要分组解密  
        String ming = "";  
        byte[][] arrays = splitArray(bcd, key_len);  
        for(byte[] arr : arrays){  
            ming += new String(cipher.doFinal(arr));  
        }  
        return ming;  
    }  
    /** 
     * ASCII码转BCD码 
     *  
     */  
    public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {  
        byte[] bcd = new byte[asc_len / 2];  
        int j = 0;  
        for (int i = 0; i < (asc_len + 1) / 2; i++) {  
            bcd[i] = asc_to_bcd(ascii[j++]);  
            bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));  
        }  
        return bcd;  
    }  
    public static byte asc_to_bcd(byte asc) {  
        byte bcd;  
  
        if ((asc >= '0') && (asc <= '9'))  
            bcd = (byte) (asc - '0');  
        else if ((asc >= 'A') && (asc <= 'F'))  
            bcd = (byte) (asc - 'A' + 10);  
        else if ((asc >= 'a') && (asc <= 'f'))  
            bcd = (byte) (asc - 'a' + 10);  
        else  
            bcd = (byte) (asc - 48);  
        return bcd;  
    }  
    /** 
     * BCD转字符串 
     */  
    public static String bcd2Str(byte[] bytes) {  
        char temp[] = new char[bytes.length * 2], val;  
  
        for (int i = 0; i < bytes.length; i++) {  
            val = (char) (((bytes[i] & 0xf0) >> 4) & 0x0f);  
            temp[i * 2] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
  
            val = (char) (bytes[i] & 0x0f);  
            temp[i * 2 + 1] = (char) (val > 9 ? val + 'A' - 10 : val + '0');  
        }  
        return new String(temp);  
    }  
    /** 
     * 拆分字符串 
     */  
    public static String[] splitString(String string, int len) {  
        int x = string.length() / len;  
        int y = string.length() % len;  
        int z = 0;  
        if (y != 0) {  
            z = 1;  
        }  
        String[] strings = new String[x + z];  
        String str = "";  
        for (int i=0; i<x+z; i++) {  
            if (i==x+z-1 && y!=0) {  
                str = string.substring(i*len, i*len+y);  
            }else{  
                str = string.substring(i*len, i*len+len);  
            }  
            strings[i] = str;  
        }  
        return strings;  
    }  
    /** 
     *拆分数组  
     */  
    public static byte[][] splitArray(byte[] data,int len){  
        int x = data.length / len;  
        int y = data.length % len;  
        int z = 0;  
        if(y!=0){  
            z = 1;  
        }  
        byte[][] arrays = new byte[x+z][];  
        byte[] arr;  
        for(int i=0; i<x+z; i++){  
            arr = new byte[len];  
            if(i==x+z-1 && y!=0){  
                System.arraycopy(data, i*len, arr, 0, y);  
            }else{  
                System.arraycopy(data, i*len, arr, 0, len);  
            }  
            arrays[i] = arr;  
        }  
        return arrays;  
    }  
    
    
    public static PrivateKey getPrivateKeyFromStr(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
		return privateKey;
	}

	public static PublicKey getPublicKeyFromStr(String key) throws Exception {
		byte[] keyBytes;
		keyBytes = Base64.decode(key);
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PublicKey publicKey = keyFactory.generatePublic(spec);
		return publicKey;
	}

	public static void main(String[] args) throws Exception{
		String pub_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC1mHZJHUj9C4GQriKSwJoAblQWK/MbhEVv9khCJ7scxq5MOdIFVL69/5Ekm4gUhn3p2mkfzmlMBpkBZv1in6BqTHiJv4icjHwrzQ65ejjQqnjiiWSUOPqEMTv9Is6LjKUcEqI19tj5lm+HBlfmI2kgZBhSqwoOj0nMTRDyy/fy+QIDAQAB";
		String pri_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALWYdkkdSP0LgZCuIpLAmgBuVBYr8xuERW/2SEInuxzGrkw50gVUvr3/kSSbiBSGfenaaR/OaUwGmQFm/WKfoGpMeIm/iJyMfCvNDrl6ONCqeOKJZJQ4+oQxO/0izouMpRwSojX22PmWb4cGV+YjaSBkGFKrCg6PScxNEPLL9/L5AgMBAAECgYA0tp0ffDDDw6NIrEO89cu+dEhUZ02anzrI1WUETyQxpjrSRbaBdago3xby2XMXAjNqvsvlP7WxK/kePxcumcemldCH8CqX+OfLjlsc8jv5rOy9+MP3fSwdwMrrlq+6lonv+lW2uC0qkLV6oxxrZkS4brLOwqwrGxQPm3bQYKIpAQJBAO9N7ejx0UNrQw9SUJt+waoSUqZ5qSXXDtOqn4iZe3EASMINpjknh9JOc0MwQORtgZC3xWy6CeaScx8V+ULPEY0CQQDCQ9R0X/8gl2p9DdvhML9u+2hpPj0XZWkidP5qQb+20bymaUVF7h/DRHM+NyrqXZpCG+crANCsLB3C4uqUUk4dAkAe3KoGFMmsLDUl0LPcmehYCqzmE2KhIq8i1Spl74Vf+W1ouWHqlRKLKNrsm1iDHSxMgabQct28Ar8eDzNqTlIxAkEArNY0e6W9E34j3EcsTdpVN6SubJRXPi2XsHRutpLwwvMv6M7YNzN5Rv3rmnryz7mfuRmiPnxnLAfEItRI6NWhcQJBAN1gU2rx5G57oRyG9Y6D0+/teJDcbF9KLENb8MdPcYQMU8vV3YLan/Os8y7EggVEU2rDJ2/tgEHu7WMxN/y11LQ=";
		String content = "testEncoder";
		String signature = sign(content, pri_key, "UTF-8");
		String result = URLEncoder.encode(signature, "UTF-8");
		System.out.println(result);
		boolean result1 = verify(content, signature, pub_key, "UTF-8");
		System.out.println(result1);
		String enStr = encryptStrByPubKey(content, "UTF-8");
		System.out.println(enStr);
		String deStr = decryptStrByPriKey(enStr, "UTF-8");
		System.out.println(deStr);
	}
}

package com.blackbread.utils.encrypt; /**
 * Created by li.xiang on 2014/5/14.
 */

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.Security;
import java.util.Random;

public class AES128 {

    /**
     * Aes加密
     */
    public static String Encrypt(String keyStr, String iv, String content,String charset) {
        String resultStr = "";
        try {
            Security.addProvider(new BouncyCastleProvider());
            Key key = new SecretKeySpec(keyStr.getBytes(), "AES");
            Cipher in = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            in.init(Cipher.ENCRYPT_MODE, key,
                    new IvParameterSpec(iv.getBytes(charset)));
            byte[] enc = in.doFinal(content.getBytes(charset));
            resultStr = Base64.encodeBase64String(enc);

        } catch (Exception e) {

            e.printStackTrace();
            // TODO: handle exception
        }
        return resultStr;

    }

	/*
	 * AES解密
	 */

    public static String Decrypted(String keyStr, String iv, String content,String charset) {
        byte[] dec = {};
        try {
            Security.addProvider(new BouncyCastleProvider());
            Key key = new SecretKeySpec(keyStr.getBytes(charset), "AES");//
            Cipher out = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            out.init(Cipher.DECRYPT_MODE, key,
                    new IvParameterSpec(iv.getBytes(charset)));//
            byte[] baseDecode = Base64.decodeBase64(content);
            dec = out.doFinal(baseDecode);
            return new String(dec,charset);//
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException{
//        String str = "012345678901234567890123456789012345678901234567890123456789";
//        String key = "UsHu9WqhCd5IZucd";
//        String md5key = "toUc45j8Lb4hWVEUogfSOFlOwIXGyB4A";
//        String iv = getRandomString(16);
//        String mi = Encrypt(key,iv,str,"UTF-8");
//        System.out.println(mi);
//        System.out.println(iv);
//        System.out.println(Decrypted(key,iv, mi,"UTF-8"));
        String enStr = "YjjL+U5k5mAwSqW0BTEbBulS2DJiBpiH2ph34sRYVWz0JeFHoKylEjcT7yRUXQ0iEL6TD9gVlK3XFSO+YG/oIVAGIW5Y+T49ksADGuEpGraMOKH62TRJ4769sEuIEdKnaGhXKCk5oiEAHr5j+VVc5ulA3gRV4piQLXYJrgif9Dk7RZ6MV61YC757FAiKohrKhX6dIwxsEx5jUK8G9Rfn1Q1sPdt1+0Qdhwf6RAkdJ0OThWmHP99g5iMFLEF1Yh/aGo4oHiBdv0tJq+VTkULafscKot1ry+ran5dkuzQupci0qpu5XcLsBBYQVZa0bl9L6FoMsEqCdCGQCmK1GmNbYBRrtv/kjuJ+fchrDuzu3U2uClY/5TSWGea7HxWooIla";
        String origin = Decrypted("pJ4jELXfZ7nLdjlY","2k9VnScAHiMdvU0Y",enStr,"UTF-8");
        System.out.println(origin);
    }

}

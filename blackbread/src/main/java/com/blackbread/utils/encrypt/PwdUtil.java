package com.blackbread.utils.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

public class PwdUtil {

	/**
	 * 生成盐
	 * 
	 * @return 盐值
	 */
	public static String genSalt() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	/**
	 * 生成加密密码
	 * 
	 * @param pwd
	 *            密码明文
	 * @param salt
	 *            盐
	 * @return 加密后的密码字符串
	 */
	public static String genPwd(String pwd, String salt) {
		try {
			MessageDigest md;
			md = MessageDigest.getInstance("SHA-256");
			String rawPwd = pwd + salt;
			byte[] bytes = rawPwd.getBytes("UTF-8");
			md.update(bytes);
			byte[] pwdBytes = md.digest();
			String enpwd = Base64.encodeBase64String(pwdBytes);
			return enpwd;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static boolean validatePassword(String password, String inputString,
			String salt) {
		if (password.equals(genPwd(inputString, salt))) {
			return true;
		} else {
			return false;
		}
	}
	public static void main(String[] args) {
		System.out.println(genPwd("abc123",genSalt()));
	}
}

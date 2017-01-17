package com.ken.wms.service.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 加密模块
 * @author Ken
 *
 */
@Component
public class EncryptingModel {

	/**
	 * 对字符串进行 MD5 加密
	 * @param plainString 需要加密的字符串
	 * @return 返回加密后的字符串
	 * @throws NoSuchAlgorithmException 
	 */
	public String MD5(String plainString) throws NoSuchAlgorithmException,NullPointerException{
		
		if(plainString == null)
			throw new NullPointerException();
		
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.update(plainString.getBytes());
		byte[] byteData = messageDigest.digest();
		
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			String hex = Integer.toHexString(0xff & byteData[i]);
			if (hex.length() == 1)
				hexString.append('0');
			hexString.append(hex);
		}
		
		return hexString.toString();
	}
}

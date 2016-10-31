package com.ken.wms.junitTest;

import org.junit.Test;

import com.ken.wms.service.util.EncryptingModel;

public class MD5Test {
	
	@Test
	public void caculateMD5() throws Exception{
		String userID = "1002";
		String password = "123456";
		String salt = "K73V";
		
		EncryptingModel encryptingModel = new EncryptingModel();
		
		String str1 = encryptingModel.MD5(password);
		String passwordDB = encryptingModel.MD5(str1 + userID);
		
		String passwordVa = encryptingModel.MD5(passwordDB +salt);
		
		System.out.println(passwordDB);
		System.out.println(passwordVa);
	}
}

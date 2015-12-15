package com.yeepay.g3.utils.security.cfca;



public class ExampleMain {

	public static void main(String[] args) throws Exception {
		
		// 要签名的原文
		// 对于托管账户的签名，请把整个xml作为原文
		String source = "hello world%25";
		
		// 传入内容，证书路径和证书密码，获得签名
		String s = SignUtil.sign(source, "pfx/hk1001001@test.com.p12.pfx", "123qwe");
		System.out.println(s);
		
		
		// 验证签名
		// 如果要在生产上验证易宝的签名,请在第三个参数传入yeepay.com
		SignUtil.verifySign(source, s, "CUSTOMER_TXP;10000365642;hk1001001@test.com");
	}

}

package com.yeepay.g3.utils.security.cfca;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class Temp {

	public static void main(String[] args) throws Exception {
		FileInputStream file = null;
		FileInputStream file2 = null;
		String source = "hello world";
		try {
			file = new FileInputStream(new File(
					"pfx/hk1001001@test.com.p12.pfx"));
			file2 = new FileInputStream(new File(
					"pfx/t.cer"));
			
			CertificateFactory fac = CertificateFactory.getInstance("X.509");
			X509Certificate cert2 = (X509Certificate)fac.generateCertificate(file2);
			System.out.println(cert2.getSubjectDN().getName());

			KeyStore ks = KeyStore.getInstance("PKCS12");
			ks.load(file, "123qwe".toCharArray());

			String alias = ks.aliases().nextElement();
			System.out.println(alias);

			Certificate cert = ks.getCertificate(alias);
			cert.getPublicKey().getEncoded();
			
			

			PrivateKey k = (PrivateKey) ks
					.getKey(alias, "123qwe".toCharArray());
			System.out.println(k.getAlgorithm());

			Signature signet = Signature.getInstance("SHA1withRSA");
			signet.initSign(k);
			signet.update(source.getBytes());
			byte[] sign = signet.sign();
			
			signet.initVerify(cert2.getPublicKey());
			signet.update(source.getBytes());
			boolean r = signet.verify(sign);
			System.out.println(r);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(file);
		}
	}

}

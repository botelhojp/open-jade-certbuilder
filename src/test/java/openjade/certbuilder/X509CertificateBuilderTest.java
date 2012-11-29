package openjade.certbuilder;


import openjade.certbuilder.X509CertificateBuilder;

import org.junit.Before;
import org.junit.Test;

public class X509CertificateBuilderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testBuilder() throws Exception {
		String path = X509CertificateBuilderTest.class.getResource("/certs").getPath();
		String passwordKeystore = "123456";
		String personID = "00000000001";
		String personName = "Jonhn dd";
		String personEmail = "agent@openjade.org";
		String url = "http://openjade.org/lcr/acopenjade.crl";
		String agentID = "agent_00001";
		String newFileName = "/cert_" + agentID + ".pfx";
		X509CertificateBuilder gen = new X509CertificateBuilder(path + "/keystore_open_jade.p12", passwordKeystore, "alias_ca", true, personID, agentID, personEmail, url);
		StringBuffer cn = new StringBuffer();
		cn.append(personName + ":" + agentID);
		gen.createCertificate(cn.toString(), 1460, path + newFileName, passwordKeystore);
	}

}

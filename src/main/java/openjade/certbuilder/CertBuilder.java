package openjade.certbuilder;

import java.io.InputStream;

import org.apache.log4j.Logger;

public class CertBuilder {

	private static Logger logger = Logger.getLogger(CertBuilder.class);
	private static X509CertificateBuilder builder = null;

	public static void main(String[] args) {
		logger.info("------------------");
		logger.info("Open Jade Builder");
		logger.info("------------------");
		switch (args.length) {
		case 1:
			if (args[0].equals("-test")){
				buildCertTest();
			}else{
				showHelp();
			}				
			break;
		default:
			showHelp();
			break;
		}
		for (int i = 0; i < args.length; i++) {
			logger.debug(args[i]);
		}
	}

	private static void buildCertTest() {
		logger.info("generating certificate for tests...");
		InputStream keystore = CertBuilder.class.getResourceAsStream("/certs/keystore_open_jade.p12");
		//String path = CertBuilder.class.getResource("/certs/keystore_open_jade.p12").getPath();
		String passwordKeystore = "123456";
		String personID = "00000000001";
		String personName = "Jonhn dd";
		String personEmail = "agent@openjade.org";
		String url = "http://openjade.org/lcr/acopenjade.crl";
		String agentID = "agent_00001";
		String newFileName = "cert_" + agentID + ".pfx";
		try {
			builder = new X509CertificateBuilder(keystore, passwordKeystore, "alias_ca", true, personID, agentID, personEmail, url);
			StringBuffer cn = new StringBuffer();
			cn.append(personName + ":" + agentID);
			builder.createCertificate(cn.toString(), 1460, newFileName, passwordKeystore);
		} catch (Throwable e) {
			e.printStackTrace();
		}		
	}

	private static void info(String _message) {
		logger.info(_message);
	}

	@SuppressWarnings("resource")
	private static void showHelp() {
		InputStream is = CertBuilder.class.getResourceAsStream("/help.txt");
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		while (s.hasNext()) {
			info(s.next());
		}
	}
}

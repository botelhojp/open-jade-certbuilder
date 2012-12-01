package openjade.certbuilder;

import java.io.InputStream;
import java.util.Hashtable;

import org.apache.log4j.Logger;

public class CertBuilder {

	private static Logger logger = Logger.getLogger(CertBuilder.class);
	private static X509CertificateBuilder builder = null;

	public static void main(String[] args) {
		logger.info("-----------------------------");
		logger.info("Open Jade Builder - v 1.0.0");
		logger.info("-----------------------------");
		
		if (args.length < 1){
			showHelp();
		}else{
			if (args[0].equals("-test")){
				buildCertTest();
			} else if (args[0].equals("-c")){
				buildCert(args);
			}else{
				showHelp();
			}
		}
	}

	private static void buildCert(String[] args) {
		Hashtable<String, String> table = new Hashtable<String, String>();
		for (int i = 1; i < args.length; i++) {
			String arg = args[i];
			String[] param = arg.split("=");
			if (param != null && param.length == 2){
				table.put(param[0].toLowerCase(), param[1].replace("\"", "").toLowerCase());
			}
		}
		logger.info("generating certificate for tests...");
		InputStream keystore = CertBuilder.class.getResourceAsStream("/certs/keystore_open_jade.p12");
		String alias = "alias_ca";		
		String passwordKeystore = 	getValue(table.get("certpassword"), "");
		String personID = 			getValue(table.get("personid"), "000.000.000-00");
		String personName = 		getValue(table.get("personname"), ""); 
		String personEmail = 		getValue(table.get("personemail"), "");
		String url = 				getValue(table.get("url"), "");
		String agentID = 			getValue(table.get("agendid"), "");
		String newFileName = "cert_" + agentID + ".pfx";
		try {
			builder = new X509CertificateBuilder(keystore, passwordKeystore, alias, true, personID, agentID, personEmail, url);
			StringBuffer cn = new StringBuffer();
			cn.append(personName + ":" + agentID);
			builder.createCertificate(cn.toString(), 1460, newFileName, passwordKeystore);
		} catch (Throwable e) {
			e.printStackTrace();
		}		
	}

	private static String getValue(String str1, String str2) {
		if (str1 != null){
			return str1;
		}else{
			return str2;
		}
	}

	private static void buildCertTest() {
		logger.info("generating certificate for tests...");
		InputStream keystore = CertBuilder.class.getResourceAsStream("/certs/keystore_open_jade.p12");
		String alias = "alias_ca";
		String passwordKeystore = "123456";
		String personID = "00000000001";
		String personName = "Leonard";
		String personEmail = "agent@openjade.org";
		String url = "http://openjade.org/lcr/acopenjade.crl";
		String agentID = "agent_00001";
		String newFileName = "cert_" + agentID + ".pfx";
		try {
			builder = new X509CertificateBuilder(keystore, passwordKeystore, alias, true, personID, agentID, personEmail, url);
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

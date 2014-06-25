package oracle.sysman.emSDK.emaas.platform.savedsearch.test.common;

import static com.jayway.restassured.RestAssured.config;
import static com.jayway.restassured.config.LogConfig.logConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jayway.restassured.RestAssured;

public class CommonTest {

	private String hostname;
	private String portno;
	private String serveruri;

	/**
	 * Sets up RESTAssured defaults before executing test cases Enables logging
	 * Reading the inputs from the testenv.properties file
	 */

	public CommonTest() {
		Properties prop = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("testenv.properties");
			prop.load(input);
			System.out.println("---------------------------------------------------------------------");
			System.out.println("The property values - Hostname: "
					+ prop.getProperty("hostname")+ " and Port: " +prop.getProperty("port"));
			System.out.println("---------------------------------------------------------------------");
			System.out.println("											");
		} catch (IOException e) {

			e.printStackTrace();
		}

		hostname = prop.getProperty("hostname");
		portno = prop.getProperty("port");
		serveruri = "http://" + hostname + ":" + portno;
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = serveruri;
		RestAssured.basePath = "/savedsearch/v1";
		RestAssured.config = config().logConfig(
				logConfig().enablePrettyPrinting(false));

	}

	public String getHOSTNAME() {
		return hostname;
	}

	public String getPortno() {
		return portno;
	}

	public String getServeruri() {
		return serveruri;
	}

}

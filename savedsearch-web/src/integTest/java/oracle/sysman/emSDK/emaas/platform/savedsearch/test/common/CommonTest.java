package oracle.sysman.emSDK.emaas.platform.savedsearch.test.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;
import oracle.sysman.qatool.uifwk.utils.Utils;
public class CommonTest
{

	private final String HOSTNAME;
	private final String portno;
	private final String serveruri;
	private final String authToken;
	private static final String TESTENV_QA_TEST_PROP = "SSF.QA.TESTENV";

	/**
	 * Sets up RESTAssured defaults before executing test cases Enables logging Reading the inputs from the testenv.properties
	 * file
	 */

	public CommonTest()
	{
		Properties prop = new Properties();
		InputStream input = null;
		try {
			System.setProperty(TESTENV_QA_TEST_PROP, "true");			
			input =      this.getClass().getClassLoader().getResourceAsStream("/testenv.properties");
			if(input==null)
			 input =      this.getClass().getClassLoader().getResourceAsStream("testenv.properties");

			prop.load(input);
			System.out.println("---------------------------------------------------------------------");
			System.out.println("The property values - Hostname: " + prop.getProperty("hostname") + " and Port: "
					+ prop.getProperty("port"));
			System.out.println("---------------------------------------------------------------------");
			System.out.println("											");
		}
		catch (IOException e) {

			e.printStackTrace();
		}
		finally {
			if (input != null) {
				try {
					input.close();
				}
				catch (IOException e) {
					//ignore					
				}
			}
		}
		/*HOSTNAME = prop.getProperty("hostname");
		portno = prop.getProperty("port");
		authToken = prop.getProperty("authToken");*/
		HOSTNAME=Utils.getProperty("EMCS_NODE1_HOSTNAME");
		authToken=Utils.getProperty("SAAS_AUTH_TOKEN");
		portno = "7001";
		serveruri = "http://" + HOSTNAME + ":" + portno;
		RestAssured.useRelaxedHTTPSValidation();
		RestAssured.baseURI = serveruri;
		RestAssured.basePath = "/savedsearch/v1";
		RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().enablePrettyPrinting(false));

	}

	public String getAuthToken()
	{
		return authToken;
	}

	public String getHOSTNAME()
	{
		return HOSTNAME;
	}

	public String getPortno()
	{
		return portno;
	}

	public String getServeruri()
	{
		return serveruri;
	}
}

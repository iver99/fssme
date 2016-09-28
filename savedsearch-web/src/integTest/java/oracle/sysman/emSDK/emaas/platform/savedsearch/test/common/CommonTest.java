package oracle.sysman.emSDK.emaas.platform.savedsearch.test.common;

import java.net.URI;
import java.net.URISyntaxException;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.QAToolUtil;
import oracle.sysman.qatool.uifwk.utils.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;

public class CommonTest
{
	private static final String DOMAIN = "www.";
	private static Logger LOGGER = LogManager.getLogger(CommonTest.class);

	public static String getDomainName(String url) throws URISyntaxException
	{
		URI uri = new URI(url);
		String domain = uri.getHost();
		return domain.startsWith(DOMAIN) ? domain.substring(4) : domain;
	}

	public static int getPort(String url) throws URISyntaxException
	{
		URI uri = new URI(url);
		int port = uri.getPort();
		return port;
	}

	private String HOSTNAME;
	private String portno;
	private String serveruri;
	private String authToken;
	private String tenantid;

	private String remoteuser;

	private static final String TESTENV_QA_TEST_PROP = "SSF.QA.TESTENV";

	/**
	 * Sets up RESTAssured defaults before executing test cases Enables logging Reading the inputs from the testenv.properties
	 * file
	 */

	public CommonTest()
	{
		String url = "";
		try {
			url = QAToolUtil.getSavedSearchDeploymentDet();
			HOSTNAME = CommonTest.getDomainName(url);
			portno = CommonTest.getPort(url) + "";
			authToken = Utils.getProperty("SAAS_AUTH_TOKEN");
			tenantid = Utils.getProperty("TENANT_ID");
			remoteuser = Utils.getProperty("SSO_USERNAME");
			serveruri = "http://" + HOSTNAME + ":" + portno;
			RestAssured.useRelaxedHTTPSValidation();
			RestAssured.baseURI = serveruri;
			RestAssured.basePath = "/savedsearch/v1";
			RestAssured.config = RestAssured.config().logConfig(LogConfig.logConfig().enablePrettyPrinting(false));
		}
		catch (Exception e) {
			LOGGER.error("an error occurred while retrving ssf deployment details" + " " + url + " " + portno + e.toString());
		}

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

	public String getRemoteUser()
	{
		return remoteuser;
	}

	public String getServeruri()
	{
		return serveruri;
	}

	public String getTenant()
	{
		return tenantid;
	}
}

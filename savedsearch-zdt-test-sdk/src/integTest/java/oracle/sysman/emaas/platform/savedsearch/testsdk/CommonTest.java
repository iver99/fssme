package oracle.sysman.emaas.platform.savedsearch.testsdk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.SchemaUtil;
import oracle.sysman.qatool.uifwk.utils.Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.LogConfig;

public class CommonTest
{
	private static final String DOMAIN = "www.";
	private static final String SERVICE_MANAGER_URL = "SERVICE_MANAGER_URL";
	private static final String SSF_DEPLOY_URL = "/instances?servicename=SavedSearch";
	private static final String AUTHORIZATION = "Authorization";
	private static final String AUTH_STRING = "Basic d2VibG9naWM6d2VsY29tZTE=";
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
	private String HOSTNAME1;
	private String portno;
	private String serveruri;
	private String authToken;
	private String tenantid;
	private String remoteuser;

	/**
	 * Sets up RESTAssured defaults before executing test cases Enables logging Reading the inputs from the testenv.properties
	 * file
	 */

	public CommonTest()
	{
		String url = "";
		try {
			url = getSavedSearchDeploymentDet();
			HOSTNAME = CommonTest.getDomainName(url);
			HOSTNAME1 = Utils.getProperty("EMCS_NODE3_HOSTNAME");
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
	
	private String getSavedSearchDeploymentDet()
	{
		String data = getDetaildByUrl(Utils.getProperty(SERVICE_MANAGER_URL) + SSF_DEPLOY_URL);
		//String data = QAToolUtil.getDetaildByUrl("http://slc08twq.us.oracle.com:7001//registry/servicemanager/registry/v1"
		//	+ SSF_DEPLOY_URL);

		List<String> urlList = SchemaUtil.getSchemaUrls(data);
		if (urlList == null | urlList.isEmpty()) {
			return null;
		}
		return urlList.get(0);
	}
	
	private String getDetaildByUrl(String url)
	{
		BufferedReader in = null;
		InputStreamReader inReader = null;
		StringBuilder response = new StringBuilder();
		try {
			URL schemaDepUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) schemaDepUrl.openConnection();
			con.setRequestProperty(AUTHORIZATION, AUTH_STRING);
			//int responseCode = con.getResponseCode();
			inReader = new InputStreamReader(con.getInputStream(), "UTF-8");
			in = new BufferedReader(inReader);
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
		}
		catch (IOException e) {
			LOGGER.error("an error occureed while getting details by url" + ":: " + url, e);
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (inReader != null) {
					inReader.close();
				}
			}
			catch (IOException ioEx) {
				//ignore
			}
		}
		return response.toString();

	}

	public String getAuthToken()
	{
		return authToken;
	}

	public String getHOSTNAME()
	{
		return HOSTNAME;
	}
	
	public String getHOSTNAME1()
	{
		return HOSTNAME1;
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

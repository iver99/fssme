package oracle.sysman.emSDK.emaas.platform.savedsearch.test.widget;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class WidgetGroupAPITest
{
	/**
	 * Calling CommonTest.java to Set up RESTAssured defaults & Reading the inputs from the testenv.properties file before
	 * executing test cases
	 */

	static String HOSTNAME;
	static String portno;
	static String serveruri;
	static String authToken;
	static String TENANT_ID_OPC1;
	static String TENANT_ID1;

	@BeforeClass
	public static void setUp()
	{
		CommonTest ct = new CommonTest();
		HOSTNAME = ct.getHOSTNAME();
		portno = ct.getPortno();
		serveruri = ct.getServeruri();
		authToken = ct.getAuthToken();
		TENANT_ID1 = ct.getTenant() + "." + ct.getRemoteUser();
		TENANT_ID_OPC1 = ct.getTenant();
	}

	/**
	 * widget group test
	 */

	@Test
	public void getAllWidgetsGroup()
	{

			Response res = RestAssured.given().log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID_OPC1).header("Authorization", authToken)
					.header(TestConstant.X_HEADER, TENANT_ID1).when().get("/widgetgroups");

			Assert.assertTrue(res.getStatusCode() == 200);

	}
}

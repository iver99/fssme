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
	static String TENANT_ID_OPC1 = TestConstant.TENANT_ID_OPC1;
	static String TENANT_ID1 = TestConstant.TENANT_ID1;

	@BeforeClass
	public static void setUp()
	{
		CommonTest ct = new CommonTest();
		HOSTNAME = ct.getHOSTNAME();
		portno = ct.getPortno();
		serveruri = ct.getServeruri();
		authToken = ct.getAuthToken();
	}

	/**
	 * widget group test
	 */

	@Test
	public void getAllWidgetsGroup()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("GET ALL Widget Groups");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/widgetgroups");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println(res.asString());
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}
}

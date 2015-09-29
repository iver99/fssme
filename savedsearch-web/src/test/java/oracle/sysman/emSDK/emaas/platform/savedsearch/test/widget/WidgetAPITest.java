package oracle.sysman.emSDK.emaas.platform.savedsearch.test.widget;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class WidgetAPITest
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
	 * widget api test
	 */

	@Test
	public void getAllWidgets()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("GET ALL Widgets");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/widgets");

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

	/**
	 * widget api test for filtering widgets by widget group id
	 */

	@Test
	public void getWidgetsByWidgetGroupId()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("GET widgets by widget group id");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/widgets?widgetGroupId=1");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println(res.asString());
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

			res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/widgets?widgetGroup=1");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

			res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/widgets?widgetGroupId=xxx");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * widget screen shot api test
	 */

	@Test
	public void getWidgetScreenshotByWidgetId()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("GET widget screen shot by widget id");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/widgets/2000/screenshot");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println(res.asString());
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

			res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/widgets/99999999/screenshot");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
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

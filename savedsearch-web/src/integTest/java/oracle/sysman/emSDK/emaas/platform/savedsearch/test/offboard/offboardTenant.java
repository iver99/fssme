package oracle.sysman.emSDK.emaas.platform.savedsearch.test.offboard;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class offboardTenant 
{
	/**
	 * Calling CommonTest.java to Set up RESTAssured defaults & Reading the inputs from the testenv.properties file before
	 * executing test cases
	 */

	static String HOSTNAME;
	static String portno;
	static String serveruri;
	static String authToken;
	static String TENANT_ID1;

	@BeforeClass
	public static void setUp()
	{
		CommonTest ct = new CommonTest();
		HOSTNAME = ct.getHOSTNAME();
		portno = ct.getPortno();
		serveruri = ct.getServeruri();
		authToken = ct.getAuthToken();
		TENANT_ID1 = ct.getTenant();
	}

	@Test
	public void offBoardForTenant()
	{
		try {

			Response res1 = RestAssured.given().log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", "CloudServices").header("Authorization", authToken)
					.when().delete("/tool/offboard/wrongTenant");
			
			Assert.assertTrue(res1.getStatusCode() == 404);
			Assert.assertEquals(res1.jsonPath().getString("errorMsg"), "Tenant Id [wrongTenant] does not exist.");
			
			Response res2 = RestAssured.given().log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", "CloudServices").header("Authorization", authToken)
					.when().delete("/tool/offboard/");
			
			Assert.assertTrue(res2.getStatusCode() == 404);
			
			Response res3 = RestAssured.given().log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", "CloudServices").header("Authorization", authToken)
					.when().delete("/tool/offboard/TENANT_ID1");
			
			Assert.assertTrue(res3.getStatusCode() == 200);

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}
}

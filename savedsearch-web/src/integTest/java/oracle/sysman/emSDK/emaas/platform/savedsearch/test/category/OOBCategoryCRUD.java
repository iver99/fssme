package oracle.sysman.emSDK.emaas.platform.savedsearch.test.category;

import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class OOBCategoryCRUD
{
	/**
	 * Calling CommonTest.java to Set up RESTAssured defaults & Reading the inputs from the testenv.properties file before
	 * executing test cases .
	 */

	static String HOSTNAME;
	static String portno;
	static String serveruri;
	static String authToken;
	static String TENANT_ID_OPC1;
	static String TENANT_ID1;

	@AfterClass
	public static void afterTest()
	{

	}

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

	@Test
	public void testCategory5()
	{
		testCategory(5, 6);
	}

	private void testCategory(int category, int defaultFolder)
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Test OOB category " + category);
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/" + category);

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertEquals(res.getStatusCode(), 200);

			System.out.println(res.asString());
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Category Name :" + jp.get("name"));
			System.out.println("Category Id   :" + jp.get("id"));
			System.out.println("Description   :" + jp.get("description"));
			System.out.println("defaultFolder :" + jp.get("defaultFolder"));
			System.out.println("parameters :" + jp.get("parameters"));
			System.out.println("providerName :" + jp.get("providerName"));
			System.out.println("providerVersion :" + jp.get("providerVersion"));
			System.out.println("providerAssetRoot :" + jp.get("providerAssetRoot"));
			Assert.assertEquals(jp.get("id"), category);
			Assert.assertEquals(jp.get("name"), "Target Card");
			Assert.assertNotNull(jp.get("href"));
			Assert.assertTrue(String.valueOf(jp.get("href")).contains("/savedsearch/v1/category/" + jp.get("id")));

			Assert.assertEquals(jp.get("defaultFolder.id"), defaultFolder);
			Assert.assertNotNull(jp.get("defaultFolder.href"));
			Assert.assertTrue(String.valueOf(jp.get("defaultFolder.href")).contains(
					"/savedsearch/v1/folder/" + jp.get("defaultFolder.id")));

			Assert.assertNotNull(jp.get("parameters"));
			List<String> nameList = jp.getList("parameters.name");
			Assert.assertEquals(nameList.size(), 1);
			Assert.assertEquals(nameList.get(0), "DASHBOARD_INELIGIBLE");
			List<String> valueList = jp.getList("parameters.value");
			Assert.assertEquals(nameList.size(), 1);
			Assert.assertEquals(valueList.get(0), "1");

			System.out.println("										");
			System.out.println("----------------------------------------");
			System.out.println("										");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}
}

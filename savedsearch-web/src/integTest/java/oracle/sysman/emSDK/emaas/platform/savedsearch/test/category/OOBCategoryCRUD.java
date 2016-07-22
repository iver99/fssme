package oracle.sysman.emSDK.emaas.platform.savedsearch.test.category;

import java.math.BigInteger;
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

public class OOBCategoryCRUD {
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
	
	@AfterClass
	public static void afterTest()
	{
		
	}
	
	@Test
	public void testCategory5()
	{
		testCategory(new BigInteger("5"), new BigInteger("6"), "Target Card");
	}
	
	private void testCategory(BigInteger category, BigInteger defaultFolder, String categoryName) {
		try {
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/" + category);

			Assert.assertEquals(res.getStatusCode(), 200);
			
			JsonPath jp = res.jsonPath();
			Assert.assertEquals(jp.get("id"), category.toString());
			Assert.assertEquals(jp.get("name"), categoryName);
			Assert.assertNotNull(jp.get("href"));
			Assert.assertTrue(String.valueOf(jp.get("href")).contains("/savedsearch/v1/category/" + jp.get("id")));
			
			Assert.assertEquals(jp.get("defaultFolder.id"), defaultFolder.toString());
			Assert.assertNotNull(jp.get("defaultFolder.href"));
			Assert.assertTrue(String.valueOf(jp.get("defaultFolder.href")).contains("/savedsearch/v1/folder/" + jp.get("defaultFolder.id")));
			
			BigInteger five = new BigInteger("5");
			if (five.compareTo(category) == 0) {
				Assert.assertNotNull(jp.get("parameters"));
				List<String> nameList = jp.getList("parameters.name");
				Assert.assertEquals(nameList.size(), 1);
				Assert.assertEquals(nameList.get(0), "DASHBOARD_INELIGIBLE");
				List<String> valueList = jp.getList("parameters.value");
				Assert.assertEquals(nameList.size(), 1);
				Assert.assertEquals(valueList.get(0), "1");
			}
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}
}

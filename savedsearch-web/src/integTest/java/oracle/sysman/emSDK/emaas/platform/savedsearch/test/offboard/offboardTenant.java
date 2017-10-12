package oracle.sysman.emSDK.emaas.platform.savedsearch.test.offboard;

import java.math.BigInteger;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
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
	
	static String TENANT_ID_USER;
	static BigInteger catid = BigInteger.ONE.negate();
	static BigInteger folderid = BigInteger.ONE.negate();
	static String catName = "";

	String id1 = "";

	public static void createinitObject() throws JSONException {

		String jsonString = "{ \"name\":\"set\",\"description\":\"Folder for  searches\"}";
		Response res = RestAssured.given().contentType(ContentType.JSON).log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID1).header("Authorization", authToken)

		.header(TestConstant.X_HEADER, TENANT_ID_USER).body(jsonString).when().post("/folder");

		folderid = new BigInteger(res.jsonPath().getString("id"));		

		String jsonString1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><CategorySet><Category><Name>MyCategoryTest</Name><Description>Testing</Description>"
				+ "<ProviderName>Name</ProviderName><ProviderVersion>1</ProviderVersion><ProviderAssetRoot>Root</ProviderAssetRoot>"
				+ "</Category></CategorySet>";
		Response res1 = RestAssured.given().contentType(ContentType.XML).log()
				.everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID1).header("Authorization", authToken)
				.header(TestConstant.X_HEADER, TENANT_ID_USER).body(jsonString1)
				.when().post("/importcategories");

		Assert.assertEquals(res1.getStatusCode(), 200);
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for (int index = 0; index < arrfld.length(); index++) {
			JSONObject jsonObj = arrfld.getJSONObject(index);
			catid = new BigInteger(jsonObj.getString("id"));
			catName = jsonObj.getString("name");
		}
	}
	@BeforeClass
	public static void setUp() throws JSONException
	{
		CommonTest ct = new CommonTest();
		HOSTNAME = ct.getHOSTNAME();
		portno = ct.getPortno();
		serveruri = ct.getServeruri();
		authToken = ct.getAuthToken();
		TENANT_ID1 = ct.getTenant();
		TENANT_ID_USER = ct.getTenant() + "." + ct.getRemoteUser();
		//TENANT_ID_OPC1 = ct.getTenant();
		offboardTenant.createinitObject();
	}

	@Test
	/**
	 * create a search using post method
	 */
	public void createSearch()
	{	
			String jsonString1 = "{\"name\":\"Search_List_1\",\"category\":{\"id\":\""
					+ catid
					+ "\"},\"folder\":{\"id\":\""
					+ folderid
					+ "\"},\"description\":\"test get search list 1\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log()
					.everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID1).header("Authorization", authToken)
					.header(TestConstant.X_HEADER, TENANT_ID_USER).body(jsonString1)
					.when().post("/search");
			Assert.assertEquals(res1.getStatusCode(), 201);
		   id1 = res1.jsonPath().getString("id");
	}
	
	@Test(dependsOnMethods = { "createSearch" })
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
					.when().delete("/tool/offboard/" + TENANT_ID1);
			
			Assert.assertTrue(res3.getStatusCode() == 200);

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}
	

	/**
	 * Check for the valid response body when the search does not exist
	 */
	@Test(dependsOnMethods = {"offBoardForTenant"})
	public void verifyDataCleaned()
	{
			Response res = RestAssured.given().log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID1).header("Authorization", authToken)

			.header(TestConstant.X_HEADER, TENANT_ID_USER).when().get("/search/" + id1);
			Assert.assertTrue(res.getStatusCode() == 404);
			Assert.assertEquals(res.asString(), "Search identified by id does not exist");
	}
}

package oracle.sysman.emaas.platform.savedsearch.testsdk;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

/**
 * @author cawei
 */
public class SavedsearchOrderWriteCURD
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

	static int catid = -1;
	static int folderid = -1;
	static String catName = "";

	@AfterClass
	public static void afterTest()
	{
		Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/" + folderid);
		System.out.println(res2.asString());
		System.out.println("Status code is: " + res2.getStatusCode());
		Assert.assertTrue(res2.getStatusCode() == 204);

	}

	public static void createinitObject() throws Exception
	{

		String jsonString = "{ \"name\":\"CustomCat\",\"description\":\"Folder for  searches\"}";
		Response res = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().post("/folder");
		System.out.println(res.asString());
		folderid = res.jsonPath().get("id");

		String jsonString1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><CategorySet><Category><Name>MyCategoryTesting</Name><Description>Testing</Description>"
				+ "<ProviderName>Name</ProviderName><ProviderVersion>1</ProviderVersion><ProviderAssetRoot>Root</ProviderAssetRoot>"
				+ "</Category></CategorySet>";
		Response res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when().post("/importcategories");
		Assert.assertEquals(res1.getStatusCode(), 200);
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for (int index = 0; index < arrfld.length(); index++) {
			System.out.println("verifying categoryids");
			JSONObject jsonObj = arrfld.getJSONObject(index);
			catid = jsonObj.getInt("id");
			catName = jsonObj.getString("name");
			System.out.println("verified categoryids");
		}
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
		try {
			SavedsearchOrderWriteCURD.createinitObject();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createFolder()
	{

	}

	@Test(dependsOnGroups = { "validateFolder" })
	public void createSavedsearch()
	{

	}

	@Test(dependsOnGroups = { "deleteSavedsearch" })
	public void deleteFolder()
	{

	}

	@Test(dependsOnGroups = { "validateSavedsearch" })
	public void deleteSavedsearch()
	{

	}

	@Test(dependsOnGroups = { "updateSavedsearch" })
	public void setLastAccessTime()
	{

	}

	@Test(dependsOnGroups = { "createFolder" })
	public void updateFolder()
	{

	}

	@Test(dependsOnGroups = { "createSavedsearch" })
	public void updateSavedsearch()
	{

	}

	@Test(dependsOnGroups = { "updateFolder" })
	public void validateFolder()
	{

	}

	@Test(dependsOnGroups = { "setLastAccessTime" })
	public void validateSavedsearch()
	{

	}

}

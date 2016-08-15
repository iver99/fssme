package oracle.sysman.emSDK.emaas.platform.savedsearch.test.upgrade;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;

public class CategoryCRUDBeforeUpgradeTest
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
		Assert.assertTrue(res2.getStatusCode() == 204);

	}

    public static void createinitObject() throws JSONException {

		String jsonString = "{ \"name\":\"CustomCat\",\"description\":\"Folder for  searches\"}";
		Response res = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().post("/folder");
		folderid = res.jsonPath().get("id");

		String jsonString1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><CategorySet><Category><Name>MyCategoryTesting</Name><Description>Testing</Description>"
				+ "<ProviderName>Name</ProviderName><ProviderVersion>1</ProviderVersion><ProviderAssetRoot>Root</ProviderAssetRoot>"
				+ "</Category></CategorySet>";
		Response res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when().post("/importcategories");
		Assert.assertEquals(res1.getStatusCode(), 200);
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for (int index = 0; index < arrfld.length(); index++) {
			JSONObject jsonObj = arrfld.getJSONObject(index);
			catid = jsonObj.getInt("id");
			catName = jsonObj.getString("name");
		}
	}

    @BeforeClass
    public static void setUp() throws JSONException {
        CommonTest ct = new CommonTest();
        HOSTNAME = ct.getHOSTNAME();
        portno = ct.getPortno();
        serveruri = ct.getServeruri();
        authToken = ct.getAuthToken();
        TENANT_ID1 = ct.getTenant() + "." + ct.getRemoteUser();
        TENANT_ID_OPC1 = ct.getTenant();
        CategoryCRUDBeforeUpgradeTest.createinitObject();
    }

	/**
	 * Category Negative Case1:
	 */
	@Test
	public void categoryBadURLs()
	{

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/0");

			Assert.assertTrue(res.getStatusCode() == 404);
			Assert.assertEquals(res.asString(), "Category object by ID: 0 does not exist");
	}

	/**
	 * Read Category Details by Id:
	 */
	@Test
	public void getCategoryDetailsbyId()
	{

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/" + catid);
			JsonPath jp = res.jsonPath();
			Assert.assertEquals(jp.get("description"), "Testing");
			Assert.assertEquals(jp.get("name"), "MyCategoryTesting");
			Assert.assertEquals(jp.get("id"), catid);
			Assert.assertEquals(jp.get("providerName"), "Name");
			Assert.assertEquals(jp.get("providerVersion"), "1");
			Assert.assertEquals(jp.get("providerAssetRoot"), "Root");
			//			Assert.assertEquals(jp.get("description"), "Testing");
			//			Assert.assertEquals(jp.get("name"), "MyCategoryTesting");
			//			Assert.assertEquals(jp.get("id"), catid);
			Assert.assertNotNull(jp.get("href"));
			Assert.assertTrue(String.valueOf(jp.get("href")).contains("/savedsearch/v1/category/" + jp.get("id")));
			Assert.assertTrue(res.getStatusCode() == 200);
	}

	/**
	 * Read Category Details by name:
	 */
	@Test
	public void getCategoryDetailsbyName()
	{
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category?name=" + catName);
			JsonPath jp = res.jsonPath();
			Assert.assertEquals(jp.get("description"), "Testing");
			Assert.assertEquals(jp.get("name"), "MyCategoryTesting");
			Assert.assertEquals(jp.get("id"), catid);
			Assert.assertEquals(jp.get("providerName"), "Name");
			Assert.assertEquals(jp.get("providerVersion"), "1");
			Assert.assertEquals(jp.get("providerAssetRoot"), "Root");
			//			Assert.assertEquals(jp.get("description"), "Testing");
			//			Assert.assertEquals(jp.get("name"), "MyCategoryTesting");
			//			Assert.assertEquals(jp.get("id"), catid);
			Assert.assertNotNull(jp.get("href"));
			Assert.assertTrue(String.valueOf(jp.get("href")).contains("/savedsearch/v1/category/" + jp.get("id")));

			Assert.assertTrue(res.getStatusCode() == 200);
	}

	/**
	 * Verify not give category name while read category details
	 */
	@Test
	public void getCategoryNoName()
	{


			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category?name=");
			Assert.assertTrue(res.getStatusCode() == 400);
			Assert.assertEquals(res.asString(), "please give category name");

			Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category?name");
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.asString(), "please give category name");
	}

	@Test
	/**
	 * Verify give wrong category name while read category details
	 */
	public void getCategoryWrongName()
	{

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category?name=abc");

			Assert.assertTrue(res.getStatusCode() == 404);
			Assert.assertEquals(res.asString(), "Category object by Name: abc does not exist");

	}

	@Test
	public void getSearchDetailsById()throws JSONException
	{

			String jsonString1 = "{\"name\":\"Search_Bycat\",\"category\":{\"id\":" + catid + "},\"folder\":{\"id\":" + folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.post("/search");
			JsonPath jp1 = res1.jsonPath();


			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/" + catid + "/searches");
			Assert.assertTrue(res.getStatusCode() == 200);
			String output = res.getBody().asString();
			JSONArray newJArray = new JSONArray(output);
			Assert.assertTrue(newJArray.length() > 0);
			RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp1.get("id"));
			Assert.assertTrue(res.getStatusCode() == 200);
	}

	@Test
	public void getSearchDetailsByIdErrorId()
	{

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/abc/searches");
			Assert.assertTrue(res.getStatusCode() == 400);
			String output = res.getBody().asString();
			Assert.assertEquals(output, "Id/count should be a positive number and not an alphanumeric");
			Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/-1/searches");
			Assert.assertTrue(res1.getStatusCode() == 400);
			String output1 = res1.getBody().asString();
			Assert.assertEquals(output1, "Id/count should be a positive number and not an alphanumeric");
			Response res2 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/4567890/searches");
			Assert.assertTrue(res2.getStatusCode() == 404);
			String output2 = res2.getBody().asString();
			Assert.assertEquals(output2, "Category object by ID: 4567890 does not exist");
	}

	@Test
	/**
	 * Check status and response for get all categories
	 */
	public void listAllCategories()
	{
			Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/categories/");
			JsonPath jp1 = res1.jsonPath();
			Assert.assertTrue(res1.getStatusCode() == 200);
			Assert.assertTrue(jp1.getList("name").size() > 0);
			Assert.assertNotNull(jp1.getList("href").get(0));
			Assert.assertTrue(String.valueOf(jp1.getList("href").get(0)).contains("/savedsearch/v1/category/"));
	}

	/**
	 * searches by more than 2 parameters
	 */
	@Test
	public void searchesbyCategory3QueryParams()
	{
			Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/searches?categoryId=" + catid + "&categoryname=" + catName + "&folderId=" + folderid);

			Assert.assertTrue(res1.getStatusCode() == 200);
	}

	/**
	 * searches by category with incomplete/bad URL
	 */
	@Test
	public void searchesbyCategoryBadRequest()
	{
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryId");
			Assert.assertTrue(res.getStatusCode() == 400);
			Assert.assertEquals(res.asString(), "Please give the value for categoryId");
			Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryId=");
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.asString(), "Please give the value for categoryId");
			Response res2 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryName=");
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.asString(), "Please give the value for categoryName");
	}

	/**
	 * searches by category with category Id which is negative number
	 */
	public void searchesbyCategoryIdNegativeNumber()
	{
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryId=-1");
			Assert.assertTrue(res.getStatusCode() == 400);
			Assert.assertEquals(res.asString(), "Id/count should be a positive number and not an alphanumeric");
	}

	/**
	 * searches by category with category ID which is not exist
	 */
	@Test
	public void searchesbyCategoryIdNotExist()
	{
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryId=4567890");
			Assert.assertTrue(res.getStatusCode() == 404);
			Assert.assertEquals(res.asString(), "Category object by ID: 4567890 does not exist");
	}

	/**
	 * searches by category with category name which is not exist
	 */
	@Test
	public void searchesbyCategory_invalid_name()
	{
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryName=MyAnalytics");

			Assert.assertTrue(res.getStatusCode() == 404);
			// System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Category object by Name: MyAnalytics does not exist");
	}

	/**
	 * searches by category with category whose name & id are not exist
	 */
	@Test
	public void searchesbyCategoryInvalidNameANDid()
	{
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/searches?categoryName=invalidCategory&categoryId=200000");
			Assert.assertTrue(res.getStatusCode() == 404);
			Assert.assertEquals(res.asString(), "Category object by Name: invalidCategory does not exist");
	}

	/**
	 * searches by category with category name
	 */
	@Test
	public void searchesbyCategoryParamcaseCheck()
	{
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryname=Log Analytics");

			Assert.assertTrue(res.getStatusCode() == 400);
			Assert.assertEquals(res.asString(),
					"Please give one and only one query parameter by one of categoryId,categoryName,folderId or lastAccessCount");
	}

	/**
	 * searches by category with category name(case sensitivity)
	 */
	@Test
	public void searchesbyCategoryQuerycaseCheck()
	{
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryName=it analytics");
			Assert.assertTrue(res.getStatusCode() == 404);
			Assert.assertEquals(res.asString(), "Category object by Name: it analytics does not exist");
	}

	/**
	 * List all the searches by category Id
	 */
	@Test
	public void searchesbyCategoryId()
	{
			int position = -1;
			String jsonString1 = "{\"name\":\"Search_A\",\"category\":{\"id\":" + catid + "},\"folder\":{\"id\":" + folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.post("/search");
			JsonPath jp1 = res1.jsonPath();
			Assert.assertTrue(res1.getStatusCode() == 201);
			Response res2 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryId=" + catid);
			JsonPath jp2 = res2.jsonPath();
			List<String> a = new ArrayList<String>();
			a = jp2.get("name");

			for (int i = 0; i < a.size(); i++) {
				if ("Search_A".equals(a.get(i))) {
					position = i;
					Assert.assertEquals(a.get(position), "Search_A");
				}
			}
			Assert.assertTrue(res2.getStatusCode() == 200);
			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp1.get("id"));
			Assert.assertTrue(res3.getStatusCode() == 204);
	}

	/**
	 * List all the searches by category name
	 */
	@Test
	public void searchesbyCategoryName()
	{
			int position = -1;
			String jsonString1 = "{\"name\":\"Search_B\",\"category\":{\"id\":" + catid + "},\"folder\":{\"id\":" + folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.post("/search");
			JsonPath jp1 = res1.jsonPath();

			Assert.assertTrue(res1.getStatusCode() == 201);
			Response res2 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryName=" + catName);
			JsonPath jp2 = res2.jsonPath();
			List<String> a = new ArrayList<String>();
			a = jp2.get("name");
			for (int i = 0; i < a.size(); i++) {
				if ("Search_B".equals(a.get(i))) {
					position = i;
					Assert.assertEquals(a.get(position), "Search_B");
				}
			}

			Assert.assertTrue(res2.getStatusCode() == 200);
			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp1.get("id"));
			Assert.assertTrue(res3.getStatusCode() == 204);
	}

	/**
	 * searches by more than 2 parameters
	 */
	@Test
	public void searchesbyCategoryParams()
	{
			Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/searches?categoryId&categoryname=Log Analytics&folderId=2");
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.asString(), "Please give the value for categoryId");
	}

}

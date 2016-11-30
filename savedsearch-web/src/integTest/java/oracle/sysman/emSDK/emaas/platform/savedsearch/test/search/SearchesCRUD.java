package oracle.sysman.emSDK.emaas.platform.savedsearch.test.search;

import java.util.Arrays;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;

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

public class SearchesCRUD
{
	/**
	 * Calling CommonTest.java to Set up RESTAssured defaults & Reading the inputs from the testenv.properties file before
	 * executing test cases
	 */

	static String HOSTNAME;
	static String portno;
	static String serveruri;
	static String authToken;
	static String TENANT_ID_OPC1 = TestConstant.TENANT_ID_OPC0;
	static int catid = -1;
	static int folderid = -1;
	static String catName = "";
	static int folderid1 = -1;
	static int catid1 = -1;
	static String catName1 = "";
	static String TENANT_ID1 = TestConstant.TENANT_ID0;

	@AfterClass
	public static void afterTest()
	{

		Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/" + folderid);

		Assert.assertEquals(res2.getStatusCode(), 204);
		res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/" + folderid1);
		Assert.assertEquals(res2.getStatusCode(), 204);

		//TenantContext.clearContext();
	}

	public static void createinitObject() throws JSONException {

		String jsonString = "{ \"name\":\"set\",\"description\":\"Folder for  searches\"}";
		Response res = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)

		.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().post("/folder");

		folderid = res.jsonPath().get("id");

		String jsonString2 = "{ \"name\":\"Custom21\",\"description\":\"Folder for  searches\"}";
		Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)

		.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString2).when().post("/folder");

		folderid1 = res2.jsonPath().get("id");

		String jsonString1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><CategorySet><Category><Name>MyCategoryTest</Name><Description>Testing</Description>"
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

		String jsonString3 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><CategorySet><Category><Name>MyCategoryTest1</Name><Description>Testing</Description>"
				+ "<ProviderName>Name</ProviderName><ProviderVersion>1</ProviderVersion><ProviderAssetRoot>Root</ProviderAssetRoot>"
				+ "</Category></CategorySet>";
		Response res3 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString3).when().post("/importcategories");

		Assert.assertEquals(res1.getStatusCode(), 200);
		JSONArray arrfld1 = new JSONArray(res3.getBody().asString());
		for (int index = 0; index < arrfld1.length(); index++) {
			System.out.println("verifying categoryids");
			JSONObject jsonObj = arrfld1.getJSONObject(index);
			catid1 = jsonObj.getInt("id");
			catName1 = jsonObj.getString("name");
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
 		SearchesCRUD.createinitObject();
	}

	@Test
	/**-
	 * Test to verify the flattened folder path details for a search
	 */
	public void flattenedFolderPath()
	{
			String jsonString = "{ \"name\":\"Folder_cont5\",\"description\":\"Folder for EMAAS searches\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/folder");

			JsonPath jp1 = res1.jsonPath();
			Assert.assertTrue(res1.getStatusCode() == 201);
			String jsonString2 = "{\"name\":\"Search_cont\",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ jp1.get("id")
					+ "}"
					+ ",\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING,\"value\":\"my_value\"}]}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString2).when()
					.post("/search");

			JsonPath jp2 = res2.jsonPath();
			Assert.assertTrue(res2.getStatusCode() == 201);
			Response res3 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when()

					.get("/search/" + jp2.get("id") + "?flattenedFolderPath=true");
			JsonPath jp3 = res3.jsonPath();
			Assert.assertEquals(jp3.get("flattenedFolderPath[0]"), "Folder_cont5");
			Assert.assertEquals(jp3.get("flattenedFolderPath[1]"), "All Searches");
		    RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp2.get("id"));

			Response res5 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/folder/" + jp1.get("id"));

			Assert.assertTrue(res5.getStatusCode() == 204);
	}

	@Test
	/**
	 * Test verify the status and response with invalid methods
	 */
	public void lastaccessedSearches_invalidObjects1()
	{
			Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()

			.get("/search/10000000087?updateLastAccessTime=true");
			Assert.assertEquals(res.asString(), "Search identified by ID: 10000000087 does not exist");
			Assert.assertTrue(res.getStatusCode() == 404);
	}

	@Test
	/**
	 * Test verify the status and response with invalid objects on a correct url path
	 */
	public void lastaccessedSearches_invalidObjects3()
	{
			Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()

			.delete("/search/1000000087?updateLastAccessTime=true");
			Assert.assertEquals(res.asString(), "Search with Id: 1000000087 does not exist");
			Assert.assertTrue(res.getStatusCode() == 404);

	}

	@Test
	/**
	 * Test verify the status and response with invalid objects on a correct url path
	 */
	public void lastaccessedSearches_invalidObjects4()
	{

			Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()

			.put("/search/100000000087?updateLastAccessTime=true");
			Assert.assertEquals(res.asString(), "Invalid search id: 100000000087");
			Assert.assertEquals(res.getStatusCode(), 404);

	}

	@Test
	/**
	 * Test to return all last accessed searches
	 */
	public void returnAlllastaccessedSearches()
	{
			Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches/");

			Assert.assertEquals(res.asString(),
					"Please give one and only one query parameter by one of categoryId,categoryName,folderId or lastAccessCount");
			Assert.assertTrue(res.getStatusCode() == 400);

			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches");

			Assert.assertEquals(res1.asString(),
					"Please give one and only one query parameter by one of categoryId,categoryName,folderId or lastAccessCount");
			Assert.assertTrue(res1.getStatusCode() == 400);
	}

	@Test
	/**
	 * Test to return all searches with categoryId
	 */
	public void returnAllSearches_CategoryId()
	{
			Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryId=-1");

			Assert.assertEquals(res.asString(), "Id/count should be a positive number and not an alphanumeric");
			Assert.assertTrue(res.getStatusCode() == 400);
	}

	@Test
	/**
	 * Test to return NO last accessed searches
	 */
	public void returnNolastaccessedSearches()
	{
			Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/searches?lastAccessCount=0");
			Assert.assertEquals(res.asString(), "");
			Assert.assertTrue(res.getStatusCode() == 200);
	}

	@Test
	/**
	 * Test to return all last accessed searches with negative count
	 */
	public void returnNolastaccessedSearches_NegCount()
	{
			Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/searches?lastAccessCount=-1");

			// JsonPath jp = res.jsonPath();
			Assert.assertEquals(res.asString(), "Id/count should be a positive number and not an alphanumeric");
			Assert.assertTrue(res.getStatusCode() == 400);
	}

	@Test
	/**
	 * Test to return all last accessed searches with text as count
	 */
	public void returnNolastaccessedSearches_textCount()
	{
			Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/searches?lastAccessCount=sravan");

			// JsonPath jp = res.jsonPath();
			Assert.assertEquals(res.asString(), "Id/count should be a positive number and not an alphanumeric");
			Assert.assertTrue(res.getStatusCode() == 400);
	}

	@Test
	/**
	 * Check for the valid response body when the search does not exist
	 */
	public void search_check_NONexistency()
	{
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)

			.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/search/555");
			Assert.assertTrue(res.getStatusCode() == 404);
			Assert.assertEquals(res.asString(), "Search identified by ID: 555 does not exist");
	}

	@Test
	/**
	 * create a search using post method
	 */
	public void search_create()
	{
			int position;
			String jsonString = "{\"name\":\"Custom_Search\",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\",\"attributes\":\"test\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/search");

			JsonPath jp1 = res1.jsonPath();
			Assert.assertTrue(res1.getStatusCode() == 201);
			Assert.assertEquals(jp1.get("createdOn"), jp1.get("lastModifiedOn"));
			Assert.assertEquals(jp1.get("createdOn"), jp1.get("lastAccessDate"));
			String jsonString2 = "{\"name\":\"Custom_Search\",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString2).when()
					.post("/search");

			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.jsonPath().getString("message"), "Search name 'Custom_Search' already exist");
			Assert.assertEquals(jp1.getInt("id"), res2.jsonPath().getInt("id"));
			Assert.assertEquals(res2.jsonPath().getInt("errorCode"), 20021);
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)

			.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + folderid);

			JsonPath jp = res.jsonPath();
			List<String> a;
			a = jp.get("name");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Custom_Search")) {
					position = i;
					Assert.assertEquals(a.get(position), "Custom_Search");

				}
			}
	}

	/**
	 * create search with empty parameter name
	 */
	@Test
	public void search_create_emptyparamName()
	{
			String jsonString = "{\"name\":\"TestSearch\",\"displayName\":\"My_Search!!!\",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\" \",\"type\":\"STRING\",\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/search");

			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.asString(), "The name key for search param can not be empty in the input JSON Object");

	}

	@Test
	/**
	 * Search Negative Case5:
	 */
	public void search_create_invalidCategory()
	{


			String jsonString = "{\"name\":\"TestSearch\",\"category\":{\"id\":12000},\"folder\":{\"id\":"
					+ folderid1
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/search");

			Assert.assertTrue(res1.getStatusCode() == 404);
			Assert.assertEquals(res1.asString(), "Can not find category with id: 12000");

	}

	@Test
	/**
	 * Search Negative Case6:
	 */
	public void search_create_invalidfolderId()
	{

			String jsonString = "{\"name\":\"TestSearch\",\"displayName\":\"My_Search!!!\",\"category\":{\"id\":1},\"folder\":{\"id\":3000},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/search");

			Assert.assertTrue(res1.getStatusCode() == 404);
			Assert.assertEquals(res1.asString(), "Can not find folder with id: 3000");

	}

	@Test
	/**
	 * Search Negative Case6:
	 */
	public void search_create_invalidparamType()
	{


			String jsonString = "{\"name\":\"TestSearch\",\"displayName\":\"My_Search!!!\",\"category\":{\"id\":1},\"folder\":{\"id\":3000},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":\"text\",\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/search");

			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.asString(), "Invalid param type, please specify either STRING or CLOB");

	}

	@Test
	/**
	 * create a search using post method
	 */
	public void search_createwithEmptyName()
	{

			String jsonString = "{\"name\":\" \",\"category\":{\"id\":1},\"folder\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/search");

			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.asString(), "The name key for search can not be empty in the input JSON Object");

	}

	@Test
	/**
	 * update a search using put method
	 */
	public void search_edit()
	{

			int position;
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)

			.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + folderid);

			JsonPath jp = res.jsonPath();
			List<String> a;
			a = jp.get("name");
			List<Integer> b;
			b = jp.get("id");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Custom_Search")) {
					position = i;

					int searchID = b.get(position);

					Assert.assertEquals(a.get(position), "Custom_Search");
					String jsonString = "{ \"name\":\"Custom_Search_Edit\",\"category\":{\"id\":"
							+ catid
							+ "}, \"folder\":{\"id\":"
							+ folderid
							+ "},\"queryStr\": \"target.name=mydb.mydomain message like ERR1*\",\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";

					Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
							.put("/search/" + searchID);

					JsonPath jp1 = res1.jsonPath();
					Assert.assertEquals(jp1.get("name"), "Custom_Search_Edit");
					Assert.assertEquals(jp1.get("lastModifiedOn"), jp1.get("lastAccessDate"));
					Assert.assertTrue(res.getStatusCode() == 200);
				}
			}

	}

	@Test
	/**
	 * Edit search's category Id
	 */
	public void search_edit_categoryId()
	{

			String jsonString = "{\"name\":\"Search for test edit category\",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/search");
			JsonPath jp = res1.jsonPath();
			Assert.assertTrue(res1.getStatusCode() == 201);
			String jsonString1 = "{ \"category\":{}}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1)
					.header("X_SSF_API_AUTH", "ORACLE_INTERNAL").body(jsonString1).when().put("/search/" + jp.get("id"));
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.asString(), "The category key for search is missing in the input JSON Object");
			String jsonString2 = "{\"name\":\"Search for test edit category_edit\"}";
			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1)
					.header("X_SSF_API_AUTH", "ORACLE_INTERNAL").body(jsonString2).when().put("/search/" + jp.get("id"));
			JsonPath jp3 = res3.jsonPath();
			Assert.assertTrue(res3.getStatusCode() == 200);
			Assert.assertEquals(jp3.getJsonObject("category").toString(), "{id=" + catid + ", href=" + serveruri
					+ "/savedsearch/v1/category/" + catid + "}");
			Assert.assertEquals(jp3.get("name"), "Search for test edit category_edit");
			String jsonString3 = "{ \"category\":{\"id\":" + catid + "}}";
			Response res4 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1)
					.header("X_SSF_API_AUTH", "ORACLE_INTERNAL").body(jsonString3).when().put("/search/" + jp.get("id"));
			JsonPath jp4 = res4.jsonPath();
			Assert.assertTrue(res4.getStatusCode() == 200);
			Assert.assertEquals(jp4.getJsonObject("category").toString(), "{id=" + catid + ", href=" + serveruri
					+ "/savedsearch/v1/category/" + catid + "}");
			Assert.assertEquals(jp4.get("name"), "Search for test edit category_edit");
			Response res7 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp.get("id"));
			Assert.assertTrue(res7.getStatusCode() == 204);

	}

	@Test
	/**
	 * Edit search with missing FolderId
	 */
	public void search_edit_emptyFolderId()
	{

			String jsonString = "{\"name\":\"Search for test missing folderId\",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/search");

			JsonPath jp = res1.jsonPath();
			Assert.assertTrue(res1.getStatusCode() == 201);
			String jsonString_edit = "{\"name\":\"Search for test missing folderId\",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString_edit).when()
					.put("/search/" + jp.get("id"));
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.asString(), "The folder key for search is missing in the input JSON Object");
			Response res7 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp.get("id"));
			Assert.assertTrue(res7.getStatusCode() == 204);

	}

	/**
	 * Edit search with empty name
	 */
	@Test
	public void search_edit_emptyName()
	{

			String jsonString = "{\"name\":\"Search for test empty name\",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/search");

			JsonPath jp = res1.jsonPath();
			Assert.assertTrue(res1.getStatusCode() == 201);
			String jsonString_edit = "{\"name\":\" \",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString_edit).when()

			.put("/search/" + jp.get("id"));
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.asString(), "The name key for search can not be empty in the input JSON Object");
			Response res7 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp.get("id"));
			Assert.assertTrue(res7.getStatusCode() == 204);

	}

	/**
	 * Edit search with invalid param Name or Type
	 */
	@Test
	public void search_edit_invalidParam()
	{

			String jsonString = "{\"name\":\"Search for test param\",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/search");

			JsonPath jp = res1.jsonPath();
			Assert.assertTrue(res1.getStatusCode() == 201);

			String jsonString_edit = "{\"parameters\":[{\"name\":\" \",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString_edit).when()

			.put("/search/" + jp.get("id"));
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.asString(), "The name key for search param can not be empty in the input JSON Object");

			String jsonString_edit1 = "{\"parameters\":[{\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString_edit1).when()

			.put("/search/" + jp.get("id"));
			Assert.assertTrue(res3.getStatusCode() == 400);
			Assert.assertEquals(res3.asString(), "The name key for search param is missing in the input JSON Object");
			String jsonString_edit2 = "{\"parameters\":[{\"name\":\"sample\",\"value\":\"my_value\"}]}";
			Response res4 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString_edit2).when()

			.put("/search/" + jp.get("id"));
			Assert.assertTrue(res4.getStatusCode() == 400);
			Assert.assertEquals(res4.asString(), "The type key for search param is missing in the input JSON Object");

			String jsonString_edit3 = "{\"parameters\":[{\"name\":\"sample\",\"type\":text	,\"value\":\"my_value\"}]}";
			Response res5 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString_edit3).when()
					.put("/search/" + jp.get("id"));

			Assert.assertTrue(res5.getStatusCode() == 400);
			Assert.assertEquals(res5.asString(), "Invalid param type, please specify either STRING or CLOB");

			Response res7 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp.get("id"));

			Assert.assertTrue(res7.getStatusCode() == 204);

	}

	/*@Test
	/**
	 * Edit System Searches
	 */
	/*public void systemSearch_edit()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to edit a system search");
			System.out.println("                                      ");
			int position = -1;
			int systemsearchId = -1;
			Response res = RestAssured.given().log().everything().header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1)
					.when().get("/searches?categoryId=1");
			JsonPath jp = res.jsonPath();

			List<Boolean> a = new ArrayList<Boolean>();
			a = jp.get("systemSearch");
			List<Integer> b = new ArrayList<Integer>();
			b = jp.get("id");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).toString().equals("true")) {
					position = i;
					System.out.println("Index is:" + position);
					systemsearchId = b.get(position);
					break;
				}

			}

			String jsonString = "{ \"name\":\"System_Search_Edit\"}";

			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.put("/search/" + systemsearchId);
			System.out.println("Status code:" + res1.getStatusCode());
			System.out.println(res1.asString());

			Assert.assertTrue(res1.getStatusCode() == 500);
			Assert.assertEquals(res1.asString(), "Search with Id: " + systemsearchId
					+ " is system search and NOT allowed to edit");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}*/
	@Test
	public void search_fieldCharValidation()
	{


			String result = "abc<";

			String description = "abc>";

			String jsonString = "{\"name\":\""

					+ result
					+ "\""
					+ ",\"category\":{\"id\":"
					+ 1
					+ "},\"folder\":{\"id\":"
					+ 1
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":						\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/search");
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(
					"The search name contains at least one invalid character ('<' or '>'), please correct search name and retry",
					res1.getBody().asString());

			jsonString = "{\"name\":\""
					+ "abc"
					+ "\""
					+ ",\"category\":{\"id\":"
					+ 1
					+ "},\"folder\":{\"id\":"
					+ 1
					+ "},\"description\":\""
					+ description
					+ "\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":						\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";

			res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().post("/search");
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(
					"The search description contains at least one invalid character ('<' or '>'), please correct search description and retry",
					res1.getBody().asString());

			jsonString = "{\"name\":"
					+ "\""
					+ result
					+ "\""
					+ ",\"category\":{\"id\":"
					+ 1
					+ "},\"folder\":{\"id\":"
					+ 1
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":						\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().put("/search/3040");
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(
					"The search name contains at least one invalid character ('<' or '>'), please correct search name and retry",
					res1.getBody().asString());

			jsonString = "{\"name\":\""

					+ "abc"
					+ "\""
					+ ",\"category\":{\"id\":"
					+ 1
					+ "},\"folder\":{\"id\":"
					+ 1
					+ "},\"description\":\""
					+ description
					+ "\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":						\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";

			res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().put("/search/3040");
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(
					"The search description contains at least one invalid character ('<' or '>'), please correct search description and retry",
					res1.getBody().asString());

	}

	@Test
	public void search_fieldValidationLength()
	{


			int n = 65;
			char[] chars = new char[n];
			Arrays.fill(chars, 'c');
			String result = new String(chars);
			chars = new char[257];
			Arrays.fill(chars, 'c');
			String description = new String(chars);

			String jsonString = "{\"name\":\""

					+ result
					+ "\""
					+ ",\"category\":{\"id\":"
					+ 1
					+ "},\"folder\":{\"id\":"
					+ 1
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":						\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/search");
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals("The maximum length of a name is 64 bytes.Please enter valid name.", res1.getBody().asString());

			jsonString = "{\"name\":\""
					+ "abc"
					+ "\""
					+ ",\"category\":{\"id\":"
					+ 1
					+ "},\"folder\":{\"id\":"
					+ 1
					+ "},\"description\":\""
					+ description
					+ "\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":						\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";

			res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().post("/search");
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals("The maximum length of a description is 256 bytes.Please enter valid description.", res1
					.getBody().asString());

			jsonString = "{\"name\":"
					+ "\""
					+ result
					+ "\""
					+ ",\"category\":{\"id\":"
					+ 1
					+ "},\"folder\":{\"id\":"
					+ 1
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":						\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().put("/search/3040");
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals("The maximum length of a name is 64 bytes.Please enter valid name.", res1.getBody().asString());

			jsonString = "{\"name\":\""

					+ "abc"
					+ "\""
					+ ",\"category\":{\"id\":"
					+ 1
					+ "},\"folder\":{\"id\":"
					+ 1
					+ "},\"description\":\""
					+ description
					+ "\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":						\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";

			res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().put("/search/3040");
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals("The maximum length of a description is 256 bytes.Please enter valid description.", res1
					.getBody().asString());

	}

	@Test
	/**
	 * Search Negative Case4:
	 */
	public void search_missingMandateinfo()
	{

			String jsonString = "{\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/search");

			Assert.assertTrue(res.getStatusCode() == 400);
			Assert.assertEquals(res.asString(), "The name key for search is missing in the input JSON Object");
			/*
			 * System.out .println(
			 * "POST operation is in-progress & missing with required field: displayName"
			 * ); System.out.println("											"); String jsonString1 =
			 * "{\"name\":\"MyLostSearch!!!\",\"categoryId\":1,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}"
			 * ; Response res1 = given().contentType(ContentType.JSON)
			 * .log()
			 * .everything().header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when().post("/search");
			 * System.out.println("											");
			 * System.out.println("Status code is: " + res1.getStatusCode());
			 * System.out.println("											");
			 * System.out.println(res1.asString());
			 * Assert.assertTrue(res1.getStatusCode() == 400);
			 * System.out.println("											");
			 * Assert.assertEquals(res1.asString(),
			 * "The displayName key for search is missing in the input JSON Object"
			 * ); System.out.println("											");
			 */
			String jsonString2 = "{\"name\":\"MyLostSearch\",\"folder\":{\"id\":"
					+ folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString2).when()
					.post("/search");
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.asString(), "The category key for search is missing in the input JSON Object");
			String jsonString3 = "{\"displayName\":\"My_Search!!!\",\"category\":{\"id\":"
					+ catid
					+ "},\"name\":\"My_Search\",\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString3).when()
					.post("/search");
			Assert.assertTrue(res3.getStatusCode() == 400);
			Assert.assertEquals(res3.asString(), "The folder key for search is missing in the input JSON Object");
			String jsonString4 = "{\"name\":\"Custom_Search\",\"displayName\":\"My_Search!!!\",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res4 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString4).when()
					.post("/search");
			Assert.assertTrue(res4.getStatusCode() == 400);
			Assert.assertEquals(res4.asString(), "The name key for search parameter is missing in the input JSON Object");
			String jsonString5 = "{\"name\":\"Custom_Search\",\"displayName\":\"My_Search!!!\",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"value\":\"my_value\"}]}";
			Response res5 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString5).when()
					.post("/search");
			Assert.assertTrue(res5.getStatusCode() == 400);
			Assert.assertEquals(res5.asString(), "The type key for search param is missing in the input JSON Object");

	}

	@Test
	/**
	 * Delete a Search using DELETE method
	 */
	public void search_remove()
	{
			int position;
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)

			.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + folderid);

			JsonPath jp = res.jsonPath();
			List<String> a;
			a = jp.get("name");
			List<Integer> b;
			b = jp.get("id");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Custom_Search_Edit")) {
					position = i;
					int mysearchID = b.get(position);
					Response res0 = RestAssured.given().log().everything().header("Authorization", authToken)

					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/search/" + mysearchID);

					JsonPath jp0 = res0.jsonPath();
					Assert.assertEquals(jp0.get("name"), "Custom_Search_Edit");
					Assert.assertEquals(jp0.get("id"), +mysearchID);
					Assert.assertEquals(jp0.get("description"), "mydb.mydomain error logs (ORA*)!!!");
					Assert.assertEquals(jp0.getMap("category").get("id"), catid);
					Assert.assertEquals(jp0.getMap("folder").get("id"), folderid);
					Assert.assertEquals(jp0.get("href"), "http://" + HOSTNAME + ":" + portno + "/savedsearch/v1/search/"
							+ mysearchID);
					Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
							.delete("/search/" + mysearchID);
					Assert.assertTrue(res1.getStatusCode() == 204);
					RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
							.get("/search/" + mysearchID);
				}
			}


	}

	/*@Test
	/**
	 * Delete system Search
	 */
	/*public void systemSearch_delete()
	{		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to delete a system search");
			System.out.println("                                      ");
			int position = -1;
			int systemsearchId = -1;
			Response res = RestAssured.given().log().everything().header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1)
					.when().get("/searches?categoryId=1");
			JsonPath jp = res.jsonPath();

			List<Boolean> a = new ArrayList<Boolean>();
			a = jp.get("systemSearch");
			List<Integer> b = new ArrayList<Integer>();
			b = jp.get("id");

			for (int i = 0; i < a.size(); i++) {
				System.out.println("array" + i + "is " + a.get(i));
				if (a.get(i).toString().equals("true")) {
					position = i;
					System.out.println("Index is:" + position);
					systemsearchId = b.get(position);
					break;
				}

			}

			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + systemsearchId);
			System.out.println("Status code:" + res1.getStatusCode());
			System.out.println(res1.asString());

			Assert.assertTrue(res1.getStatusCode() == 500);
			Assert.assertEquals(res1.asString(), "Search with Id: " + systemsearchId
					+ " is system search and NOT allowed to delete");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}*/
	/*	@Test
		public void SearchUtfTest()
		{
			try {

				String result = new String("\u7537" + "\u6027");
				System.out.println("Result:::::::::::::" + result);

				String sName = "";
				String description = "abc";
				for (int i = 0; i < result.length(); i++) {
					int cp = Character.codePointAt(result, i);
					sName += Character.toString((char) cp);

				}
				System.out.println("------------------------------------------");//
				String jsonString = "{\"name\":\""
						+ result
						+ "\""
						+ ",\"category\":{\"id\":"
						+ 1
						+ "},\"folder\":{\"id\":"
						+ 1
						+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":						\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
				Response res1 = RestAssured.given().contentType("application/json; charset=UTF-8").log().everything()

				.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
						.post("/search");
				System.out.println(res1.asString());
				System.out.println("Status code is: " + res1.getStatusCode());
				Assert.assertTrue(res1.getStatusCode() == 201);
				JsonPath jp2 = res1.jsonPath();
				Response res4_1 = RestAssured.given().contentType("application/json; charset=UTF-8").log().everything()
						.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
						.get("/search/" + jp2.get("id"));

				String str = res4_1.jsonPath().get("name");
				System.out.println("Result:::::::::::::" + str);
				res1 = RestAssured.given().contentType("application/json; charset=UTF-8").log().everything()
						.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
						.delete("/search/" + jp2.get("id"));
				Assert.assertTrue(res1.getStatusCode() == 204);
				System.out.println("Equals :  " + str.equalsIgnoreCase(sName));
				System.out.println("Equals :  " + str.equals(sName));
				System.out.println("Equals :  " + result.equalsIgnoreCase(sName));
				System.out.println("Equals :  " + result.equals(sName));
				System.out.println("Result:::::::::::::" + str.equals(result));
				System.out.println("ResultId:::::::::::::" + jp2.get("id"));
				String str1 = new String(jp2.getString("name").getBytes("UTF-8"), "UTF-8");
				System.out.println("ResultId1:::::::::::::" + str1.equals(result));
				System.out.println(result);
				System.out.println(str1);
				Assert.assertEquals(str, result);
			}
			catch (Exception e) {
				Assert.fail(e.getLocalizedMessage());
			}

		}*/

	@Test
	/**
	 * set last access time to a search using PUT method
	 */
	public void setlastaccesstime_Tosearch()
	{

			String jsonString1 = "{\"name\":\"SearchSet1\",\"category\":{\"id\":" + catid1 + "},\"folder\":{\"id\":" + folderid1
					+ "},\"description\":\"mydb.err logs!!!\",\"queryStr\": \"target.name=mydb.mydomain ERR*\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.post("/search");

			JsonPath jp1 = res1.jsonPath();
			Assert.assertTrue(res1.getStatusCode() == 201, "status code: " + res1.getStatusCode());
			try{
				Thread.sleep(2000);
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			String jsonString2 = "{\"name\":\"SearchSet2\",\"category\":{\"id\":" + catid1 + "},\"folder\":{\"id\":" + folderid1
					+ "},\"description\":\"mydb.err logs!!!\",\"queryStr\": \"target.name=mydb.mydomain ERR*\"}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString2).when()
					.post("/search");

			JsonPath jp2 = res2.jsonPath();
			String str_lastAccessTime = jp2.get("lastAccessDate");
			Assert.assertTrue(res2.getStatusCode() == 201);
			try{
				Thread.sleep(3000);
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/searches?lastAccessCount=2");

			JsonPath jp3 = res3.jsonPath();
			try{
				Thread.sleep(3000);
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			Response res4 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()

			.put("/search/" + jp3.get("id[1]") + "?updateLastAccessTime=true");
			// JsonPath jp4 = res4.jsonPath();
			String str_updateTime = res4.asString();
			Assert.assertTrue(res4.getStatusCode() == 200);
			Response res4_1 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/search/" + jp3.get("id[1]"));

			JsonPath jp4_1 = res4_1.jsonPath();
			Assert.assertTrue(res4_1.getStatusCode() == 200);
			Assert.assertEquals(jp4_1.get("lastAccessDate"), str_updateTime);

			try {
				Thread.sleep(2000);
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			Response res4_3 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()

			.put("/search/" + jp3.get("id[0]") + "?updateLastAccessTime=false");
			// JsonPath jp4 = res4.jsonPath();
			Assert.assertTrue(res4_3.getStatusCode() == 200);
			Response res4_4 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/search/" + jp3.get("id[0]"));

			JsonPath jp4_2 = res4_4.jsonPath();
			Assert.assertTrue(res4_4.getStatusCode() == 200);
			Assert.assertEquals(jp4_2.get("lastAccessDate"), str_lastAccessTime);
			try{
				Thread.sleep(2000);
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			RestAssured.given().contentType(ContentType.JSON).log().everything()
			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/searches?lastAccessCount=2");
			Response res6 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp1.get("id"));
			Assert.assertTrue(res6.getStatusCode() == 204);
			Response res7 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp2.get("id"));

			Assert.assertTrue(res7.getStatusCode() == 204);

	}

	/**
	 * set last access time to a search using PUT method, but the query parameter is not complete
	 */
	@Test
	public void setlastaccesstime_Tosearch_badParameter()
	{

			String jsonString1 = "{\"name\":\"SearchSetLastAccess\",\"category\":{\"id\":" + catid1 + "},\"folder\":{\"id\":"
					+ folderid + "},\"description\":\"mydb.err logs!!!\",\"queryStr\": \"target.name=mydb.mydomain ERR*\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.post("/search");

			JsonPath jp1 = res1.jsonPath();
			Assert.assertTrue(res1.getStatusCode() == 201, "status code: " + res1.getStatusCode());
			try{
				Thread.sleep(2000);
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()

			.put("/search/" + jp1.get("id") + "?updateLastAccessTime");
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.asString(), "please give the value for updateLastAccessTime");
			try{
				Thread.sleep(2000);
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.put("/search/" + jp1.get("id"));
			Assert.assertTrue(res3.getStatusCode() == 400);
			Assert.assertEquals(res3.asString(), "Please specify updateLastAccessTime true or false");
			try{
				Thread.sleep(2000);
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			Response res7 = RestAssured.given().contentType(ContentType.JSON).log().everything()

			.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp1.get("id"));

			Assert.assertTrue(res7.getStatusCode() == 204);

	}

	@Test
	public void getAssetRootTest(){
		Assert.assertEquals(200,
				RestAssured.given().contentType(ContentType.JSON).log().everything()
						.header("Authorization", authToken)
						.header(TestConstant.OAM_HEADER, TENANT_ID1).when()
						.get("search/2000/assetroot").getStatusCode());
	}
	@Test
	/**
	 * Test verify the status and response with invalid objects on a correct url path
	 */
	public void testNotExistSearchEditLastAccess()
	{
		Response res = RestAssured.given().contentType(ContentType.JSON)
				.log().everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).when()
				.put("/search/100000000087/updatelastaccess");
		Assert.assertEquals(res.asString(), "Search identified by ID: 100000000087 does not exist");
		Assert.assertTrue(res.getStatusCode() == 404);
	}

	@Test
	public void testDeleteSearchByName(){
		String inputJson = "{\"name\":\"SearchTestDeletedByName\",\"category\":{\"id\":"
				+ 5
				+ "},\"folder\":{\"id\":"
				+ 6
				+ "}"
				+ ",\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"deletedlater\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING,\"value\":\"my_value\"}]}";
		RestAssured.given().contentType(ContentType.JSON).log().everything()
				.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(inputJson).when()
				.post("/search");
		Response resForDelete1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
				.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
				.delete("/search?searchName=SearchTestDeletedByName");
		Assert.assertEquals(200, resForDelete1.getStatusCode());

		RestAssured.given().contentType(ContentType.JSON).log().everything()
				.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(inputJson).when()
				.post("/search");
		Response resForDelete2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
				.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
				.delete("/search?searchName=SearchTestDeletedByName&isExactly=true");
		Assert.assertEquals(200, resForDelete2.getStatusCode());

		RestAssured.given().contentType(ContentType.JSON).log().everything()
				.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(inputJson).when()
				.post("/search");
		Response resForDelete3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
				.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
				.delete("/search?searchName=TestDeletedBy&isExactly=false");
		Assert.assertEquals(200, resForDelete3.getStatusCode());
	}
	
	@Test
	public void testGetSearchList() {
		// create 2 searches
		String jsonString1 = "{\"name\":\"Search_List_1\",\"category\":{\"id\":"
				+ catid
				+ "},\"folder\":{\"id\":"
				+ folderid
				+ "},\"description\":\"test get search list 1\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING,\"value\":\"my_value\"}]}";
		Response res1 = RestAssured.given().contentType(ContentType.JSON).log()
				.everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1)
				.when().post("/search");
		Assert.assertEquals(res1.getStatusCode(), 201);
		Long id1 = res1.jsonPath().getLong("id");
		String jsonString2 = "{\"name\":\"Search_List_2\",\"category\":{\"id\":"
				+ catid
				+ "},\"folder\":{\"id\":"
				+ folderid
				+ "},\"description\":\"test get search list 2\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING,\"value\":\"my_value\"}]}";
		Response res2 = RestAssured.given().contentType(ContentType.JSON).log()
				.everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString2)
				.when().post("/search");
		Assert.assertEquals(res2.getStatusCode(), 201);
		Long id2 = res2.jsonPath().getLong("id");
		
		// get 2 searches by getSearchList
		String ids = "[" + id1 + "," + id2 + "]";
		Response res3 = RestAssured.given().contentType(ContentType.JSON).log()
				.everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).body(ids)
				.when().put("/search/list");
		Assert.assertEquals(res3.getStatusCode(), 200);
		List<Integer> result = res3.jsonPath().getList("id");
		Assert.assertEquals(result.size(), 2);
		Assert.assertTrue(result.contains(Integer.valueOf(id1.toString())));
		Assert.assertTrue(result.contains(Integer.valueOf(id2.toString())));
		
		// clean the searches
		Response res4 = RestAssured.given().contentType(ContentType.JSON).log()
				.everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).when()
				.delete("/search/" + id1);
		Assert.assertEquals(res4.getStatusCode(), 204);
		Response res5 = RestAssured.given().contentType(ContentType.JSON).log()
				.everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).when()
				.delete("/search/" + id2);
		Assert.assertEquals(res5.getStatusCode(), 204);
	}
}

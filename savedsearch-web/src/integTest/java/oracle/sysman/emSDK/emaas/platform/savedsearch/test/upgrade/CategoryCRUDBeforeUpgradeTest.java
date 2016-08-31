package oracle.sysman.emSDK.emaas.platform.savedsearch.test.upgrade;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
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
			CategoryCRUDBeforeUpgradeTest.createinitObject();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Category Negative Case1:
	 */
	@Test
	public void category_badURLs()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Negative Case1 for CATEGORY");
			System.out.println("											");
			System.out.println("GET operation is in-progress with bad URL");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/0");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Category object by ID: 0 does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Read Category Details by Id:
	 */
	@Test
	public void getCategory_detailsbyId()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("GET operation is in-progress to read category details");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/" + catid);
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("Category Name :" + jp.get("name"));
			System.out.println("Category Id   :" + jp.get("id"));
			System.out.println("Description   :" + jp.get("description"));
			System.out.println("defaultFolder :" + jp.get("defaultFolder"));
			System.out.println("providerName :" + jp.get("providerName"));
			System.out.println("providerVersion :" + jp.get("providerVersion"));
			System.out.println("providerAssetRoot :" + jp.get("providerAssetRoot"));
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
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	/**
	 * Read Category Details by name:
	 */
	@Test
	public void getCategory_detailsbyName()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("GET operation is in-progress to read category details");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category?name=" + catName);
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is : " + res.getStatusCode());
			System.out.println("Category Name  :" + jp.get("name"));
			System.out.println("Category Id    :" + jp.get("id"));
			System.out.println("Description    :" + jp.get("description"));
			System.out.println("defaultFolderId:" + jp.get("defaultFolderId"));
			System.out.println("providerName :" + jp.get("providerName"));
			System.out.println("providerVersion :" + jp.get("providerVersion"));
			System.out.println("providerAssetRoot :" + jp.get("providerAssetRoot"));
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
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Verify not give category name while read category details
	 */
	@Test
	public void getCategory_noName()
	{

		try {
			System.out.println("------------------------------------------");
			System.out.println("GET operation is in-progress to read category details without category name");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category?name=");
			System.out.println("											");
			System.out.println("Status code is : " + res.getStatusCode());

			Assert.assertTrue(res.getStatusCode() == 400);
			Assert.assertEquals(res.asString(), "please give category name");

			Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category?name");
			System.out.println("											");
			System.out.println("Status code is : " + res.getStatusCode());

			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.asString(), "please give category name");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	/**
	 * Verify give wrong category name while read category details
	 */
	public void getCategory_wrongName()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("GET operation is in-progress to read category details with wrong category name");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category?name=abc");
			System.out.println("											");
			System.out.println("Status code is : " + res.getStatusCode());

			Assert.assertTrue(res.getStatusCode() == 404);
			Assert.assertEquals(res.asString(), "Category object by Name: abc does not exist");

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void getSearchDetailsById()
	{
		try {

			String jsonString1 = "{\"name\":\"Search_Bycat\",\"category\":{\"id\":" + catid + "},\"folder\":{\"id\":" + folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.post("/search");
			JsonPath jp1 = res1.jsonPath();

			System.out.println("------------------------------------------");
			System.out.println("GET operation is in-progress to read  details");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/" + catid + "/searches");
			Assert.assertTrue(res.getStatusCode() == 200);
			String output = res.getBody().asString();
			JSONArray newJArray = new JSONArray(output);
			Assert.assertTrue(newJArray.length() > 0);
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp1.get("id"));
			Assert.assertTrue(res.getStatusCode() == 200);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void getSearchDetailsById_ErrorId()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Negative Case 1 to verify the cateogy Id is given alphabet");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/abc/searches");
			Assert.assertTrue(res.getStatusCode() == 400);
			String output = res.getBody().asString();
			Assert.assertEquals(output, "Id/count should be a positive number and not an alphanumeric");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

			System.out.println("------------------------------------------");
			System.out.println("Negative Case 2 to verify the cateogy Id is given negative number");
			System.out.println("											");

			Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/-1/searches");
			Assert.assertTrue(res1.getStatusCode() == 400);
			String output1 = res1.getBody().asString();
			Assert.assertEquals(output1, "Id/count should be a positive number and not an alphanumeric");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

			System.out.println("------------------------------------------");
			System.out.println("Negative Case to verify the cateogy Id is given non-existed ID");
			System.out.println("											");

			Response res2 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/category/4567890/searches");
			Assert.assertTrue(res2.getStatusCode() == 404);
			String output2 = res2.getBody().asString();
			Assert.assertEquals(output2, "Category object by ID: 4567890 does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	/**
	 * Check status and response for get all categories
	 */
	public void listAllCategories()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Using GET method to retrieve the list of categories");
			System.out.println("											");
			Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/categories/");
			JsonPath jp1 = res1.jsonPath();

			System.out.println("Categories existed :" + jp1.get("name"));
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 200);
			System.out.println("There are " + jp1.getList("name").size() + " categories");
			Assert.assertTrue(jp1.getList("name").size() > 0);
			Assert.assertNotNull(jp1.getList("href").get(0));
			Assert.assertTrue(String.valueOf(jp1.getList("href").get(0)).contains("/savedsearch/v1/category/"));
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * searches by more than 2 parameters
	 */
	@Test
	public void searchesbyCategory_3queryParams()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println(
					"This test is to validate the response & status with categoryName, categoryId & folderId combinations");
			Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/searches?categoryId=" + catid + "&categoryname=" + catName + "&folderId=" + folderid);

			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 200);
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * searches by category with incomplete/bad URL
	 */
	@Test
	public void searchesbyCategory_badRequest()
	{
		try {
			System.out
					.println("Case1:This test is to validate the response and status when the searches by category with bad url");
			System.out.println("											");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryId");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Please give the value for categoryId");
			System.out.println("											");
			System.out.println("Case2:This test is to validate the response and status when id is missing");
			System.out.println("											");
			Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryId=");

			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);
			System.out.println(res1.asString());
			Assert.assertEquals(res1.asString(), "Please give the value for categoryId");
			System.out.println("											");
			System.out.println("Case3:This test is to validate the response and status when name is missing");
			System.out.println("											");
			Response res2 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryName=");

			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 400);
			System.out.println(res2.asString());
			Assert.assertEquals(res2.asString(), "Please give the value for categoryName");
			System.out.println("											");
			System.out.println("------------------------------------------");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * searches by category with category Id which is negative number
	 */
	public void searchesbyCategory_id_negativeNumber()
	{
		try {
			System.out.println(
					"This test is to validate the response when the search by category with category ID which is negative number");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryId=-1");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Id/count should be a positive number and not an alphanumeric");
			System.out.println("											");
			System.out.println("------------------------------------------");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

	/**
	 * searches by category with category ID which is not exist
	 */
	@Test
	public void searchesbyCategory_id_notExist()
	{
		try {
			System.out.println(
					"This test is to validate the response when the search by category with category ID which is not exist");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryId=4567890");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Category object by ID: 4567890 does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * searches by category with category name which is not exist
	 */
	@Test
	public void searchesbyCategory_invalid_name()
	{
		try {
			System.out.println(
					"This test is to validate the response when the searches by category with category name which is not exist");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryName=MyAnalytics");

			System.out.println(res.asString());
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			// System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Category object by Name: MyAnalytics does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * searches by category with category whose name & id are not exist
	 */
	@Test
	public void searchesbyCategory_invalid_nameANDid()
	{
		try {
			System.out.println(
					"This test is to validate the response when the search by category whose category name & id are not exist");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/searches?categoryName=invalidCategory&categoryId=200000");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Category object by Name: invalidCategory does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * searches by category with category name
	 */
	@Test
	public void searchesbyCategory_ParamcaseCheck()
	{
		try {
			System.out.println("This test is to validate the response when the search by category(query case check)");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryname=Log Analytics");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(),
					"Please give one and only one query parameter by one of categoryId,categoryName,folderId or lastAccessCount");
			System.out.println("											");
			System.out.println("------------------------------------------");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * searches by category with category name(case sensitivity)
	 */
	@Test
	public void searchesbyCategory_querycaseCheck()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to validate the response when the search by category");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryName=it analytics");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Category object by Name: it analytics does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * List all the searches by category Id
	 */
	@Test
	public void searchesbyCategoryId()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Searches by category id is in-progress using GET method");
			System.out.println("Now creation of searches in the specified category with POST method");
			System.out.println("											");
			int position = -1;
			String jsonString1 = "{\"name\":\"Search_A\",\"category\":{\"id\":" + catid + "},\"folder\":{\"id\":" + folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.post("/search");
			JsonPath jp1 = res1.jsonPath();
			System.out.println("											");
			System.out.println("Search Id  :" + jp1.get("id"));
			System.out.println("Search Name :" + jp1.get("name"));
			System.out.println("Status code is: " + res1.getStatusCode());
			// System.out.println(res1.asString());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("											");
			System.out.println("==POST operation is completed for creation searches using the specified category");
			System.out.println("											");
			Response res2 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryId=" + catid);
			JsonPath jp2 = res2.jsonPath();
			List<String> a = new ArrayList<String>();
			a = jp2.get("name");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Search_A")) {
					position = i;
					String myvalue = a.get(position);
					System.out.println("My Search in categoryId 3 is :" + myvalue);
					Assert.assertEquals(a.get(position), "Search_A");
				}
			}
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 200);
			System.out.println("											");
			System.out.println("Searches in the categoryId:3 are: " + jp2.get("name"));

			System.out.println("==Searches by category id is done");
			System.out.println("Cleaning up the searches that are created in this scenario");
			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp1.get("id"));
			System.out.println(res3.asString());
			System.out.println("Status code is: " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 204);
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * List all the searches by category name
	 */
	@Test
	public void searchesbyCategoryName()
	{
		try {
			System.out.println("------------------------------------------");
			System.out
					.println("This test is to perform the operation that lists all the searches by the specified category name");
			System.out.println("Now creation of searches in the specified category with POST method");
			System.out.println("											");
			int position = -1;
			String jsonString1 = "{\"name\":\"Search_B\",\"category\":{\"id\":" + catid + "},\"folder\":{\"id\":" + folderid
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.post("/search");
			JsonPath jp1 = res1.jsonPath();
			System.out.println("											");
			System.out.println("Search Id  :" + jp1.get("id"));
			System.out.println("Search Name :" + jp1.get("name"));
			System.out.println("Status code is: " + res1.getStatusCode());

			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("											");
			System.out.println("==POST operation is completed for creation searches using the specified category");
			System.out.println("											");

			System.out.println("Searches by category name is in-progress using GET method");
			System.out.println("											");
			Response res2 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?categoryName=" + catName);
			JsonPath jp2 = res2.jsonPath();
			List<String> a = new ArrayList<String>();
			a = jp2.get("name");
			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Search_B")) {
					position = i;
					String myvalue = a.get(position);
					System.out.println("My Search in categoryname- IT Analytics is :" + myvalue);
					Assert.assertEquals(a.get(position), "Search_B");
				}
			}

			System.out.println("Status code is: " + res2.getStatusCode());

			Assert.assertTrue(res2.getStatusCode() == 200);
			System.out.println("											");
			System.out.println("Searches in the category:IT Analytics are: " + jp2.get("name"));

			System.out.println("==Searches by category name is done");
			System.out.println("Cleaning up the searches that are created in this scenario");
			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/search/" + jp1.get("id"));
			System.out.println(res3.asString());
			System.out.println("Status code is: " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 204);
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * searches by more than 2 parameters
	 */
	@Test
	public void searchesbyCategoryParams()
	{
		try {
			System.out.println("------------------------------------------");

			System.out.println("This test is to validate the response & status with more query params");
			Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.get("/searches?categoryId&categoryname=Log Analytics&folderId=2");

			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println(res1.asString());
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.asString(), "Please give the value for categoryId");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

}

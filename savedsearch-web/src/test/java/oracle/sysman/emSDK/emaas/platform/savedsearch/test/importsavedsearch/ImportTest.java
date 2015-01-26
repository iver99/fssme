package oracle.sysman.emSDK.emaas.platform.savedsearch.test.importsavedsearch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;

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

public class ImportTest
{
	static String HOSTNAME;
	static String portno;
	static String serveruri;
	static String authToken;
	static String TENANT_ID_OPC1 = TestConstant.TENANT_ID_OPC1;

	private static final String FOLDER_XML = "oracle/sysman/emSDK/emaas/platform/savedsearch/test/importsavedsearch/Folder.xml";
	private static final String CATEGORY_XML = "oracle/sysman/emSDK/emaas/platform/savedsearch/test/importsavedsearch/Category.xml";
	private static final String SEARCH_XML = "oracle/sysman/emSDK/emaas/platform/savedsearch/test/importsavedsearch/Search.xml";

	@AfterClass
	public static void afterTest()
	{
		TenantContext.clearContext();
	}

	@BeforeClass
	public static void setUp()
	{
		CommonTest ct = new CommonTest();
		HOSTNAME = ct.getHOSTNAME();
		portno = ct.getPortno();
		serveruri = ct.getServeruri();
		authToken = ct.getAuthToken();
	}

	private static String getStringFromInputStream(InputStream is)
	{

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	@Test
	/**
	 * Import Categories
	 */
	public void importCategories() throws Exception
	{
		InputStream stream = ImportTest.class.getClassLoader().getResourceAsStream(CATEGORY_XML);
		String jsonString1 = ImportTest.getStringFromInputStream(stream);
		Response res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
				.body(jsonString1).header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().post("/importcategories");
		Assert.assertEquals(res1.getStatusCode(), 200);
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for (int index = 0; index < arrfld.length(); index++) {
			System.out.println("verifying categoryids");
			JSONObject jsonObj = arrfld.getJSONObject(index);
			Assert.assertTrue(verifyCategory(jsonObj.getInt("id"), jsonObj.getString("name")) == true);
			Assert.assertTrue(jsonObj.getInt("id") > 1);
			System.out.println("verified categoryids");
		}
	}

	@Test
	/**
	 * import categories with invalid format
	 */
	public void importCategories_invalidformat()
	{
		try {
			Response res = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
					.body("").header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().post("/importcategories");
			Assert.assertEquals(res.getStatusCode(), 400);
			Assert.assertEquals(res.asString(), "Please specify input with valid format");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	/**
	 * import folders with invalid format
	 */
	public void importFolder_invalidformat()
	{
		try {
			Response res = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
					.body("").header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().post("/importfolders");
			Assert.assertEquals(res.getStatusCode(), 400);
			Assert.assertEquals(res.asString(), "Please specify input with valid format");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	/**
	 * Import folders
	 */
	public void importFolders() throws Exception
	{
		InputStream stream = ImportTest.class.getClassLoader().getResourceAsStream(FOLDER_XML);
		String jsonString1 = ImportTest.getStringFromInputStream(stream);
		Response res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
				.body(jsonString1).header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().post("/importfolders");
		Assert.assertEquals(res1.getStatusCode(), 200);
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for (int index = 0; index < arrfld.length(); index++) {
			System.out.println("Verifying folders");
			JSONObject jsonObj = arrfld.getJSONObject(index);
			Assert.assertTrue(verifyFolder(jsonObj.getInt("id"), jsonObj.getString("name")) == true);
			System.out.println("Deleting folders");
			Assert.assertTrue(deleteFolder(jsonObj.getInt("id")) == true);
			System.out.println("Deleted folders");
		}
	}

	@Test
	/**
	 * Import Searches
	 */
	public void importSearches() throws Exception
	{
		InputStream stream = ImportTest.class.getClassLoader().getResourceAsStream(SEARCH_XML);
		String jsonString1 = ImportTest.getStringFromInputStream(stream);
		Response res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
				.body(jsonString1).header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().post("/importsearches");
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for (int index = 0; index < arrfld.length(); index++) {
			System.out.println("deleteing folders and  searches::");
			JSONObject jsonObj = arrfld.getJSONObject(index);
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().get("/search/" + jsonObj.getInt("id"));
			JsonPath jp = res.jsonPath();
			System.out.println("deleteing folders and  searches::::" + res.getBody().asString());
			System.out.println("deleteing folders and  searches::::::::" + jp.getMap("folder").get("id"));
			Assert.assertTrue(deleteSearch(jsonObj.getInt("id")) == true);
			if ((int) jp.getMap("folder").get("id") > 1) {
				Assert.assertTrue(deleteFolder((int) jp.getMap("folder").get("id")) == true);
			}
			System.out.println("deleted folders and  searches");
		}

	}

	@Test
	/**
	 * import searches with invalid format
	 */
	public void importSearches_invalidformat()
	{
		try {
			Response res = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
					.body("").header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().post("/importsearches");
			Assert.assertEquals(res.getStatusCode(), 400);
			Assert.assertEquals(res.asString(), "Please specify input with valid format");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	private boolean deleteFolder(int myfolderID)
	{
		Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
				.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().delete("/folder/" + myfolderID);
		System.out.println("											");
		return res1.getStatusCode() == 204;

	}

	private boolean deleteSearch(int mySearchId)
	{
		Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
				.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().delete("/search/" + mySearchId);
		System.out.println("											");
		return res1.getStatusCode() == 204;

	}

	private Boolean verifyCategory(int mycatID, String mycatName)
	{
		Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
				.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().get("/category/" + mycatID);
		JsonPath jp = res1.jsonPath();
		if (res1.getStatusCode() != 200) {
			System.out.println(res1.getStatusCode());
			return false;
		}
		if (!jp.get("name").equals(mycatName)) {
			System.out.println(jp.get("name"));
			return false;
		}
		if (jp.get("href").equals(serveruri + "/category/" + mycatID)) {
			System.out.println(jp.get("href"));
			return false;
		}
		if (jp.get("createdOn") == null || "".equals(jp.get("createdOn"))) {
			System.out.println(jp.get("createdOn"));
			return false;
		}
		return true;

	}

	private boolean verifyFolder(int myfolderID, String myfolderName)
	{
		Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
				.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().get("/folder/" + myfolderID);
		JsonPath jp = res1.jsonPath();
		if (res1.getStatusCode() != 200) {
			System.out.println(res1.getStatusCode());
			return false;
		}
		if (!jp.get("name").equals(myfolderName)) {
			System.out.println(jp.get("name"));
			return false;
		}
		if (jp.get("href").equals(serveruri + "/folder/" + myfolderID)) {
			System.out.println(jp.get("href"));
			return false;
		}
		if (!jp.get("createdOn").equals(jp.get("lastModifiedOn"))) {
			System.out.println(jp.get("createdOn"));
			System.out.println(jp.get("lastModifiedOn"));
			return false;
		}
		if (jp.get("systemFolder").equals(true)) {
			System.out.println(jp.get("systemFolder"));
			return false;
		}
		return true;
	}

	private boolean verifySearch(int mysearchID)
	{
		return true;
	}

}

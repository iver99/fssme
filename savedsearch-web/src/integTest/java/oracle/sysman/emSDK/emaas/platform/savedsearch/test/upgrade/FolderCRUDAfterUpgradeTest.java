package oracle.sysman.emSDK.emaas.platform.savedsearch.test.upgrade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

public class FolderCRUDAfterUpgradeTest
{

	/**
	 * Calling CommonTest.java to Set up RESTAssured defaults & Reading the inputs from the testenv.properties file before
	 * executing test cases
	 */

	static String HOSTNAME;

	static String portno;

	static String serveruri;

	static String authToken;
	static Integer catid = null;;
	static String TENANT_ID_OPC1;
	static String TENANT_ID1;

	@AfterClass
	public static void afterTest()
	{

	}

	//@Test
	/**
	 * Edit a system folder using PUT method
	 */
	/*public void systemFolder_Edit()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to edit a system folder with PUT method");
			System.out.println("											");
			System.out.println("GET operation to select the folder is in progress");
			System.out.println("											");

			String jsonString = "{ \"name\":\"System_Folder_Edit\" }";

			Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.put("/folder/1");
			// JsonPath jp = res.jsonPath();
			System.out.println(res.asString());
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 500);
			Assert.assertEquals(res.asString(), "Folder with Id " + 1 + " is system folder and NOT allowed to edit");

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}*/

	public static void importCategories() throws Exception
	{
		String jsonString1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
				+ "<CategorySet><Category><Name>MyCategory</Name>"
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

			FolderCRUDAfterUpgradeTest.importCategories();

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*@Test
	public void checkBadResuest()
	{
		try {

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, ".emcsadmin").when().get("/searches?folderId=-1");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			System.out.println("------------------------------------------");

			res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, "emcsadmin.").when().get("/searches?folderId=-1");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			System.out.println("------------------------------------------");

			res = RestAssured.given().log().everything().header("Authorization", authToken).header(TestConstant.OAM_HEADER, "")
					.when().get("/searches?folderId=-1");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			System.out.println("------------------------------------------");

			res = RestAssured.given().log().everything().header("Authorization", authToken).when().get("/searches?folderId=-1");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			System.out.println("------------------------------------------");

			res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, "emcsadmin").when().get("/searches?folderId=-1");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			System.out.println("------------------------------------------");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}*/

	@Test
	/**
	 * Folder Negative Case6:
	 */
	public void fetchfolder_notExist()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Negative Case6 for FOLDER");
			System.out.println("GET method is in-progress to validate the folder non-availability");

			System.out.println("											");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1)

					.when().get("/entities?folderId=99999999999");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println("											");
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Folder with the Id 99999999999 does not exist");
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
	 * Check for the valid response body when the folder is not available
	 */
	public void folder_check_NONexistency()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to Check for the valid response body when the folder is not available");
			System.out.println("											");
			System.out.println("Using GET operation to get the details of the specified folderId");
			System.out.println("											");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/folder/333");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("== GET operation is completed");
			System.out.println("											");
			Assert.assertTrue(res.getStatusCode() == 404);
			String resbody = res.asString();

			System.out.println("Result:" + resbody);
			Assert.assertEquals(res.asString(), "Folder with the Id 333 does not exist");
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
	 * create a folder using post method
	 */
	public void folder_create()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new folder");
			int position = -1;
			String jsonString = "{ \"name\":\"Custom_Folder\",\"description\":\"Folder for EMAAS searches\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/folder");
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 201);
			String id = res1.jsonPath().getString("id");
			System.out.println("Verfify whether the timestamp of create&modify is same or not");
			Assert.assertEquals(res1.jsonPath().get("createdOn"), res1.jsonPath().get("lastModifiedOn"));
			System.out.println("											");

			System.out.println("This test is to check for the duplicate entry with re-post");
			System.out.println("											");

			String jsonString2 = "{ \"name\":\"Custom_Folder\" }";
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString2).when()
					.post("/folder");
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			System.out.println("											");
			System.out.println(res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 400);
			String message = res2.jsonPath().getString("message");
			Assert.assertEquals(message, "The folder name 'Custom_Folder' already exist");
			Assert.assertEquals(res1.jsonPath().getInt("id"), res2.jsonPath().getInt("id"));
			Assert.assertEquals(res2.jsonPath().getInt("errorCode"), 30021);
			System.out.println("    ");

			System.out.println("Verifying weather the folder created in a specified location or not with GET method");
			System.out.println("											");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + id);
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("FolderName :" + jp.get("name"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Custom_Folder")) {
					position = i;

					String myvalue = a.get(position);
					System.out.println("==My New Folder is:" + myvalue);
					Assert.assertEquals(a.get(position), "Custom_Folder");
					System.out.println("==GET and assertion operation are successfully completed");
				}
			}
			if (position == -1) {
				System.out.println("==folder does not exist that you are looking for");
			}

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
	 * create a folder using post method in a specified parentId
	 */
	public void folder_create_specifiedParentId()
	{
		try {

			//create folder

			String jsonString1 = "{ \"name\":\"TestFolderParent\", \"description\":\"Folder for EMAAS searches\"}";
			Response resp1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.post("/folder");
			JsonPath jpp1 = resp1.jsonPath();

			Integer parentFolder = jpp1.getInt("id");

			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new folder in a specified directory");
			int position = -1;
			String jsonString = "{ \"name\":\"TestFolder\", \"description\":\"Folder for EMAAS searches\",\"parentFolder\":{\"id\":"
					+ parentFolder.intValue() + "}}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/folder");
			JsonPath jp1 = res1.jsonPath();
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("											");
			System.out.println("Verifying whether the folder created or not in a specified directory with GET method");
			System.out.println("											");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + parentFolder.intValue());
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("FolderName :" + jp.get("name"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("TestFolder")) {
					position = i;

					String myvalue = a.get(position);
					System.out.println("==My New Folder is:" + myvalue);
					Assert.assertEquals(a.get(position), "TestFolder");
					System.out.println("==GET and assertion operations are successfully completed");
				}
			}
			if (position == -1) {
				System.out.println("==folder does not exist that you are looking for");
			}
			System.out.println("cleaning up the folder that is created above using DELETE method");
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/folder/" + jp1.get("id"));
			System.out.println(res2.asString());
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 204);
			System.out.println("Delete parent folder ");
			res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/" + parentFolder.intValue());
			System.out.println(res2.asString());
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 204);
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
	 * create a folder using post method in a specified parentId "0"
	 */
	public void folder_create_specifiedParentId_IdIsZero()
	{
		try {

			String jsonString1 = "{ \"name\":\"TestFolderP\", \"description\":\"Folder for EMAAS searches\"}";
			Response resp1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.post("/folder");
			JsonPath jpp1 = resp1.jsonPath();

			Integer parentFolder = jpp1.getInt("id");

			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new folder in a specified directory");
			int position = -1;
			String jsonString = "{ \"name\":\"TestFolder\", \"description\":\"Folder for EMAAS searches\",\"parentFolder\":{\"id\":0}}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/folder");
			JsonPath jp1 = res1.jsonPath();
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("											");
			System.out.println("Verifying whether the folder created or not in a specified directory with GET method");
			System.out.println("											");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + parentFolder.intValue());
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("FolderName :" + jp.get("name"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("TestFolder")) {
					position = i;

					String myvalue = a.get(position);
					System.out.println("==My New Folder is:" + myvalue);
					Assert.assertEquals(a.get(position), "TestFolder");
					System.out.println("==GET and assertion operations are successfully completed");
				}
			}
			if (position == -1) {
				System.out.println("==folder does not exist that you are looking for");
			}
			System.out.println("cleaning up the folder that is created above using DELETE method");
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/folder/" + jp1.get("id"));
			System.out.println(res2.asString());
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 204);

			res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/" + parentFolder.intValue());
			System.out.println(res2.asString());
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 204);
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
	 * create a folder using post method
	 */
	public void folder_createwithEmptyName()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new folder with blank name");
			String jsonString = "{ \"name\":\" \",\"description\":\"Folder for EMAAS searches\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/folder");
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.asString(), "The name key for folder can not be empty in the input JSON Object");
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
	 * update a folder using put method
	 */
	public void folder_edit()
	{
		try {

			Integer id = getRootId();

			System.out.println("------------------------------------------");
			System.out.println("This test is to edit a folder with PUT method");
			System.out.println("											");
			System.out.println("GET operation to select the folder to be edited is in progress");
			System.out.println("											");
			int position = -1;
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + id.intValue());
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("FolderName :" + jp.get("name"));
			System.out.println("Folder IDs  :" + jp.get("id"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");
			List<Integer> b = new ArrayList<Integer>();
			b = jp.get("id");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Custom_Folder")) {
					position = i;

					int myfolderID = b.get(position);
					System.out.println("==GET operation to select the folder that is to be EDITED is completed");
					System.out.println("											");

					System.out.println("PUT operation to edit the selected folder is in-progress");
					String jsonString = "{ \"name\":\"Custom_Folder_Edit\" }";

					Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
							.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString)
							.when().put("/folder/" + myfolderID);
					System.out.println("											");
					System.out.println("Status code is: " + res1.getStatusCode());
					System.out.println("											");
					System.out.println(res1.asString());
					String c;
					JsonPath jp1 = res1.jsonPath();
					c = jp1.get("name");
					Assert.assertEquals(c, "Custom_Folder_Edit");
					System.out.println("Asserted & FolderName After Edit is:" + c);
					System.out.println("											");
					String str_createdOn = jp1.get("createdOn");
					String str_modifiedOn = jp1.get("lastModifiedOn");
					Assert.assertNotEquals(str_createdOn, str_modifiedOn);
					System.out.println("==PUT operation is completed");

					System.out.println("PUT operation to edit the selected folder is in-proress");
					String jsonString1 = "{ \"description\":\"Folder for EMAAS searches_Edit\"}";
					Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
							.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1)
							.when().put("/folder/" + myfolderID);
					System.out.println("											");
					System.out.println("Status code is: " + res2.getStatusCode());
					System.out.println("											");
					System.out.println(res2.asString());
					String str_desc;
					JsonPath jp2 = res2.jsonPath();
					str_desc = jp2.get("description");
					Assert.assertEquals(str_desc, "Folder for EMAAS searches_Edit");

					System.out.println("											");

					System.out.println("==PUT operation is completed");

					System.out.println("											");
					System.out.println("------------------------------------------");
					System.out.println("											");
				}
			}
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * Edit the folder parentId
	 */
	@Test
	public void folder_edit_specifiedParentId()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new folder");
			int position = -1;

			String jsonString1 = "{ \"name\":\"TestFolder_ParentId\", \"description\":\"Folder for EMAAS searches\"}";
			Response res11 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.post("/folder");
			JsonPath jp11 = res11.jsonPath();
			int pid = jp11.get("id");
			String jsonString = "{ \"name\":\"TestFolder_ParentId\", \"description\":\"Folder for EMAAS searches\",\"parentFolder\":{\"id\":"
					+ pid + "}}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/folder");
			JsonPath jp1 = res1.jsonPath();
			System.out.println(res1.asString());
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("==POST operation is done");
			System.out.println("											");

			System.out.println("GET operation to select the folder to be edited is in progress");
			System.out.println("											");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + pid);
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("FolderName :" + jp.get("name"));
			System.out.println("Folder IDs  :" + jp.get("id"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");
			List<Integer> b = new ArrayList<Integer>();
			b = jp.get("id");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("TestFolder_ParentId")) {
					position = i;

					int myfolderID = b.get(position);
					System.out.println("==GET operation to select the folder that is to be EDITED is completed");
					System.out.println("											");

					System.out.println("PUT operation to edit the selected folder is in-progress");
					String jsonString_parentId = "{\"parentFolder\":{\"id\":" + pid + "}}";

					Response res_parentId = RestAssured.given().contentType(ContentType.JSON).log().everything()
							.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1)
							.body(jsonString_parentId).when().put("/folder/" + myfolderID);
					JsonPath jp_parentId = res_parentId.jsonPath();
					System.out.println("											");
					System.out.println("Status code is: " + res_parentId.getStatusCode());
					System.out.println("											");
					System.out.println(res1.asString());
					System.out.println(jp_parentId.get("parentFolder"));
					Assert.assertTrue(res_parentId.getStatusCode() == 200);

				}
			}

			System.out.println("==Get operation to verify if the folderId has been changed");
			res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + getRootId().intValue());
			jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("FolderName :" + jp.get("name"));
			System.out.println("Folder IDs  :" + jp.get("id"));
			List<String> a1 = new ArrayList<String>();
			a1 = jp.get("name");
			List<Integer> b1 = new ArrayList<Integer>();
			b1 = jp.get("id");

			boolean existflag = false;

			for (int j = 0; j < a1.size(); j++) {
				if (a1.get(j).equals("TestFolder_ParentId")) {
					System.out.println(b1.get(j));
					existflag = true;
					break;
				}
			}

			Assert.assertTrue(existflag);

			System.out.println("cleaning up the folder that is created above using DELETE method");
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/folder/" + jp1.get("id"));
			System.out.println(res2.asString());
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 204);

			res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/" + pid);
			System.out.println(res2.asString());
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 204);

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

	/**
	 * Edit the folder parentId, the parentId is 0
	 */
	@Test
	public void folder_edit_specifiedParentId_IdIsZero()
	{
		try {
			String jsonString1 = "{ \"name\":\"TestFolder_ParentId_Zero1\", \"description\":\"Folder for EMAAS searches\"}";
			Response res11 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.post("/folder");
			JsonPath jp11 = res11.jsonPath();
			int parentId = jp11.get("id");

			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new folder");
			int position = -1;
			String jsonString = "{ \"name\":\"TestFolder_ParentId_Zero\", \"description\":\"Folder for EMAAS searches\",\"parentFolder\":{\"id\":"
					+ parentId + "}}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/folder");
			JsonPath jp1 = res1.jsonPath();
			System.out.println(res1.asString());
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("==POST operation is done");
			System.out.println("											");

			System.out.println("GET operation to select the folder to be edited is in progress");
			System.out.println("											");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + parentId);
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("FolderName :" + jp.get("name"));
			System.out.println("Folder IDs  :" + jp.get("id"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");
			List<Integer> b = new ArrayList<Integer>();
			b = jp.get("id");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("TestFolder_ParentId_Zero")) {
					position = i;

					int myfolderID = b.get(position);
					System.out.println("==GET operation to select the folder that is to be EDITED is completed");
					System.out.println("											");

					System.out.println("PUT operation to edit the selected folder is in-progress");
					String jsonString_parentId = "{\"parentFolder\":{\"id\":0}}";

					Response res_parentId = RestAssured.given().contentType(ContentType.JSON).log().everything()
							.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1)
							.body(jsonString_parentId).when().put("/folder/" + myfolderID);
					JsonPath jp_parentId = res_parentId.jsonPath();
					System.out.println("											");
					System.out.println("Status code is: " + res_parentId.getStatusCode());
					System.out.println("											");
					System.out.println(res1.asString());
					System.out.println(jp_parentId.get("parentFolder"));
					Assert.assertTrue(res_parentId.getStatusCode() == 200);

				}
			}

			Integer id = getRootId();

			System.out.println("==Get operation to verify if the folderId has been changed");
			res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + id.intValue());
			jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("FolderName :" + jp.get("name"));
			System.out.println("Folder IDs  :" + jp.get("id"));
			List<String> a1 = new ArrayList<String>();
			a1 = jp.get("name");
			List<Integer> b1 = new ArrayList<Integer>();
			b1 = jp.get("id");

			boolean existflag = false;

			for (int j = 0; j < a1.size(); j++) {
				if (a1.get(j).equals("TestFolder_ParentId_Zero")) {
					System.out.println(b1.get(j));
					existflag = true;
					break;
				}
			}

			Assert.assertTrue(existflag);

			System.out.println("cleaning up the folder that is created above using DELETE method");
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/folder/" + jp1.get("id"));
			System.out.println(res2.asString());
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 204);

			res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/" + parentId);
			System.out.println(res2.asString());
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 204);

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

	/**
	 * Edit the folder with empty folder name
	 */
	@Test
	public void folder_editwithEmptyName()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to edit a folder with PUT method");
			System.out.println("											");
			System.out.println("Prepare data...");
			String jsonString = "{ \"name\":\"Custom_Folder_EditEmptyName\",\"description\":\"Folder for EMAAS searches\",}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/folder");
			JsonPath jp1 = res1.jsonPath();
			System.out.println(res1.asString());
			System.out.println(jp1.get("id"));
			Assert.assertTrue(res1.getStatusCode() == 201);

			String jsonString1 = "{ \"name\":\" \" }";

			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.put("/folder/" + jp1.get("id"));
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			System.out.println("											");
			System.out.println(res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.asString(), "The name key for folder can not be empty in the input JSON Object");

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

			System.out.println("cleaning up the folder that is created above using DELETE method");
			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/folder/" + jp1.get("id"));
			System.out.println(res3.asString());
			System.out.println("Status code is: " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 204);
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}

	}

	@Test
	public void folder_fieldCharValidation()
	{
		try {

			String result = "abc<";

			String description = "abc>";

			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new folder");
			int position = -1;
			String jsonString = "{ \"name\":" + "\"" + result + "\"" + ",\"description\":\"" + "abc" + "\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/folder");
			Assert.assertEquals(
					"The folder name contains at least one invalid character ('<' or '>'), please correct folder name and retry",
					res1.getBody().asString());
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);

			jsonString = "{ \"name\":" + "\"" + "ABC " + "\"" + ",\"description\":\"" + description + "\"}";
			res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().post("/folder");
			Assert.assertEquals(
					"The folder description contains at least one invalid character ('<' or '>'), please correct folder description and retry",
					res1.getBody().asString());
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

			jsonString = "{ \"name\":" + "\"" + result + "\"" + ",\"description\":\"" + "abc" + "\"}";

			res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().put("/folder/" + 1);

			Assert.assertEquals(
					"The folder name contains at least one invalid character ('<' or '>'), please correct folder name and retry",
					res1.getBody().asString());
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);

			jsonString = "{ \"name\":" + "\"" + "ABC " + "\"" + ",\"description\":\"" + description + "\"}";
			res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().put("/folder/" + 1);
			Assert.assertEquals(
					"The folder description contains at least one invalid character ('<' or '>'), please correct folder description and retry",
					res1.getBody().asString());
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void folder_fieldValidationLength()
	{
		try {

			int n = 65;
			char[] chars = new char[n];
			Arrays.fill(chars, 'c');
			String result = new String(chars);
			chars = new char[257];
			Arrays.fill(chars, 'c');
			String description = new String(chars);

			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new folder");
			int position = -1;
			String jsonString = "{ \"name\":" + "\"" + result + "\"" + ",\"description\":\"" + "abc" + "\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/folder");
			Assert.assertEquals("The maximum length of a name is 64 bytes.Please enter valid name.", res1.getBody().asString());
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);

			jsonString = "{ \"name\":" + "\"" + "ABC " + "\"" + ",\"description\":\"" + description + "\"}";
			res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().post("/folder");
			Assert.assertEquals("The maximum length of a description is 256 bytes.Please enter valid description.", res1
					.getBody().asString());
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

			jsonString = "{ \"name\":" + "\"" + result + "\"" + ",\"description\":\"" + "abc" + "\"}";

			res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().put("/folder/" + 1);

			Assert.assertEquals("The maximum length of a name is 64 bytes.Please enter valid name.", res1.getBody().asString());
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);

			jsonString = "{ \"name\":" + "\"" + "ABC " + "\"" + ",\"description\":\"" + description + "\"}";
			res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().put("/folder/" + 1);
			Assert.assertEquals("The maximum length of a description is 256 bytes.Please enter valid description.", res1
					.getBody().asString());
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	/**
	 * Folder: Invalid folderId:
	 */
	public void folder_InvalidFolderId()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Negative Case3 for FOLDER");
			System.out.println("											");
			System.out.println("GET operation is in-progress with empty folder id");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=0");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Invalid folderId: 0");
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
	 * Folder Negative Case5:
	 */
	public void folder_invalidParentId()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Negative Case5 for FOLDER");
			System.out.println("POST method is in-progress to create a new folder with invalidParentId");

			String jsonString = "{ \"name\":\"TestFolder\", \"description\":\"Folder for EMAAS searches\",\"parentFolder\":{\"id\":333333}}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/folder");
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 404);
			System.out.println(res1.asString());
			Assert.assertEquals(res1.asString(), "Parent folder with Id 333333 does not exist");
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
	 * Folder Negative Case3:
	 */
	public void folder_InvalidType()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Negative Case3 for FOLDER");
			System.out.println("											");
			System.out.println("GET operation is in-progress with Invalid Type");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=me");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Folder Id should be a numeric and not alphanumeric");
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
	 * Folder Negative Case7:
	 */
	public void folder_missingInfo_path()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Negative Case7 for FOLDER");
			System.out.println("											");
			System.out.println("GET operation on URL which is lagging with some info");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Empty folderId");

			Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=");

			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);
			System.out.println(res1.asString());
			Assert.assertEquals(res1.asString(), "Empty folderId");

			Response res2 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities");

			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 400);
			System.out.println(res2.asString());
			Assert.assertEquals(res2.asString(), "Please give folderId");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/*@Test
	/**
	 * Delete a system folder using DELETE method
	 */
	/*public void systemFolder_Delete()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to delete a system folder with DELETE method");
			System.out.println("											");
			System.out.println("GET operation to select the folder is in progress");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1)
					.when().delete("/folder/1");
			// JsonPath jp = res.jsonPath();
			System.out.println(res.asString());
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 500);
			Assert.assertEquals(res.asString(), "Folder with Id " + 1 + " is system folder and NOT allowed to delete");

			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}*/

	@Test
	/**
	 * Folder Negative Case4:
	 */
	public void folder_missingMandateinfo()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("Negative Case4 for FOLDER");
			System.out.println("											");
			System.out.println("POST operation is in-progress & missing with required field: Name");
			System.out.println("											");
			String jsonString = "{\"parent\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\"}";
			Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
					.post("/folder");
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println(res.asString());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println("											");
			Assert.assertEquals(res.asString(), "The name key for folder is missing in the input JSON Object");
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
	 * Delete a folder using DELETE method
	 */
	public void getFolderDelete()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to delete a folder with DELETE method");
			System.out.println("											");
			System.out.println("GET operation to select the folder is in progress");
			System.out.println("											");

			//Get Root folder id 

			Integer id = getRootId();

			int position = -1;
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + id.intValue());
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("FolderName :" + jp.get("name"));
			System.out.println("Folder IDs  :" + jp.get("id"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");
			List<Integer> b = new ArrayList<Integer>();
			b = jp.get("id");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Custom_Folder_Edit")) {
					position = i;
					System.out.println("Index is:" + position);
					int myfolderID = b.get(position);

					System.out.println("My Value is:" + myfolderID);
					System.out.println("==GET operation to select the folder that is to be DELETED is completed");
					System.out.println("											");
					System.out.println("DELETE operation to delete the selected folder is in-progress");
					System.out.println("											");
					Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
							.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
							.delete("/folder/" + myfolderID);
					System.out.println("											");
					System.out.println("Status code is: " + res1.getStatusCode());
					// System.out.println("											");
					System.out.println(res1.asString());
					Assert.assertTrue(res1.getStatusCode() == 204);
					System.out.println("==Delete operation is success and asserted that the folderID: " + myfolderID
							+ " does not exist");
					System.out.println("											");
					System.out.println("------------------------------------------");
					System.out.println("											");
				}
			}
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	/**
	 * Folder Negative Case8: Delete a non-exist folder
	 */
	public void nonexistFolder_Delete()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to delete a non-exist folder with DELETE method");
			System.out.println("											");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/3333333333");
			System.out.println(res.asString());
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			Assert.assertEquals(res.asString(), "Folder with Id 3333333333 does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	/**
	 * searches by folder with folder Id which is negative number
	 */
	@Test
	public void searchesbyFolder_id_negativeNumber()
	{
		try {
			System.out
					.println("This test is to validate the response when the search by folder with folder ID which is negative number");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?folderId=-1");

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

	@Test
	/**
	 * create a folder and searches in it using post method to verify the functionality of find searches by folderId
	 */
	public void searchesByFolderId()
	{
		try {

			Integer id = getRootId();

			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create a new folder and then to create searches in it");

			String jsonString1 = "{ \"name\":\"Folder_searches6\",\"description\":\"Folder for EMAAS searches\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
					.post("/folder");
			JsonPath jp1 = res1.jsonPath();
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");

			System.out.println("FolderName :" + jp1.get("name"));
			System.out.println("Folder ID  :" + jp1.get("id"));
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("------------------------------------------");
			System.out.println("Asserting the details of the folder");
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/folder/" + jp1.get("id"));
			JsonPath jp0 = res.jsonPath();
			Assert.assertEquals(jp0.get("name"), "Folder_searches6");
			Assert.assertEquals(jp0.get("id"), jp1.get("id"));
			Assert.assertEquals(jp0.getMap("parentFolder").get("id"), id);
			Assert.assertEquals(jp0.getMap("parentFolder").get("href"), "http://" + HOSTNAME + ":" + portno
					+ "/savedsearch/v1/folder/" + id);
			Assert.assertEquals(jp0.get("href"), "http://" + HOSTNAME + ":" + portno + "/savedsearch/v1/folder/" + jp0.get("id"));
			Assert.assertEquals(jp0.get("description"), "Folder for EMAAS searches");
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("POST method is in-progress to create searches in the folder whose Id is: " + jp1.get("id"));
			String jsonString2 = "{\"name\":\"Search_s1\",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ jp1.get("id")
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING,\"value\":\"my_value\"}]}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString2).when()
					.post("/search");
			JsonPath jp2 = res2.jsonPath();

			System.out.println("Status code is: " + res2.getStatusCode());

			System.out.println(res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 201);
			System.out.println("											");
			System.out.println("Search Name :" + jp2.get("name"));
			System.out.println("Search ID  :" + jp2.get("id"));
			System.out.println("											");

			System.out.println("------------------------------------------");
			System.out.println("GET method is in-progress to list all the searches in the folderId: " + jp1.get("id"));
			Response res10 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?folderId=" + jp1.get("id"));
			JsonPath jp10 = res10.jsonPath();

			System.out.println("											");
			System.out.println("Status code is: " + res10.getStatusCode());
			System.out.println(res10.asString());
			System.out.println(jp10.get("name"));
			Assert.assertTrue(res10.getStatusCode() == 200);
			Assert.assertTrue(jp10.get("name[0]").equals("Search_s1"));
			System.out.println("Searches in folder id: " + jp1.get("id") + " are -> " + jp10.get("name"));
			System.out.println("											");

			String jsonString3 = "{\"name\":\"Search_s2\",\"category\":{\"id\":"
					+ catid
					+ "},\"folder\":{\"id\":"
					+ jp1.get("id")
					+ "},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample2\",\"type\":STRING,\"value\":\"my_value\"}]}";
			Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString3).when()
					.post("/search");
			JsonPath jp3 = res3.jsonPath();

			System.out.println("Status code is: " + res3.getStatusCode());

			System.out.println(res3.asString());
			Assert.assertTrue(res3.getStatusCode() == 201);
			System.out.println("											");
			System.out.println("Search Name :" + jp3.get("name"));
			System.out.println("Search ID  :" + jp3.get("id"));
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("GET method is in-progress to list all the searches in the folderId: " + jp1.get("id"));
			Response res4 = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + jp1.get("id"));
			JsonPath jp4 = res4.jsonPath();

			System.out.println("											");
			System.out.println("Status code is: " + res4.getStatusCode());
			System.out.println(res4.asString());
			Assert.assertTrue(res4.getStatusCode() == 200);
			System.out.println("Searches in folder id: " + jp1.get("id") + " are -> " + jp4.get("name"));
			System.out.println("											");

			System.out.println("------------------------------------------");
			System.out.println("Delete the folder while it has searches in it using DELETE method");
			System.out.println("											");
			Response res5 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/folder/" + jp1.get("id"));
			System.out.println("											");
			System.out.println("Status code is: " + res5.getStatusCode());
			System.out.println(res5.asString());
			Assert.assertTrue(res5.getStatusCode() == 500);
			Assert.assertEquals(res5.asString(), "The folder can not be deleted as folder is associated with searches");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("Delete the searches from folderId: " + jp1.get("id"));

			if (jp4.get("name[0]") == "Search_s2") {
				Response res6 = RestAssured.given().contentType(ContentType.JSON).log().everything()
						.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
						.delete("/search/" + jp3.get("id"));
				System.out.println("											");
				System.out.println("Status code is: " + res6.getStatusCode());
				Assert.assertTrue(res6.getStatusCode() == 204);
				System.out.println("											");
				Response res7 = RestAssured.given().contentType(ContentType.JSON).log().everything()
						.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
						.delete("/search/" + jp2.get("id"));
				System.out.println("											");
				System.out.println("Status code is: " + res7.getStatusCode());
				Assert.assertTrue(res7.getStatusCode() == 204);
			}
			else {

				Response res7 = RestAssured.given().contentType(ContentType.JSON).log().everything()
						.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
						.delete("/search/" + jp2.get("id"));
				System.out.println("											");
				System.out.println("Status code is: " + res7.getStatusCode());
				Assert.assertTrue(res7.getStatusCode() == 204);
				Response res6 = RestAssured.given().contentType(ContentType.JSON).log().everything()
						.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
						.delete("/search/" + jp3.get("id"));
				System.out.println("											");
				System.out.println("Status code is: " + res6.getStatusCode());
				Assert.assertTrue(res6.getStatusCode() == 204);
				System.out.println("											");
				System.out.println("------------------------------------------");
			}
			System.out.println("Delete the folder whose Id is: " + jp1.get("id"));
			Response res8 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/folder/" + jp1.get("id"));
			System.out.println("											");
			System.out.println("Status code is: " + res8.getStatusCode());
			// System.out.println(res8.asString());
			Assert.assertTrue(res8.getStatusCode() == 204);
			// System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("Delete the folder again whose Id is: " + jp1.get("id"));
			Response res9 = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
					.delete("/folder/" + jp1.get("id"));
			System.out.println("											");
			System.out.println("Status code is: " + res9.getStatusCode());
			System.out.println(res9.asString());
			Assert.assertTrue(res9.getStatusCode() == 404);
			Assert.assertEquals(res9.asString(), "Folder with Id " + jp1.get("id") + " does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	private Integer getRootId()
	{

		Response resroot = RestAssured.given().log().everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("");
		JsonPath jpRoot = resroot.jsonPath();

		List<Integer> id = jpRoot.get("id");
		return id.get(0);
	}

}


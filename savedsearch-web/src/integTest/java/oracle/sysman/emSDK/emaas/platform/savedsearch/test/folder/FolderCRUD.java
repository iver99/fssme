package oracle.sysman.emSDK.emaas.platform.savedsearch.test.folder;

import java.math.BigInteger;
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

public class FolderCRUD {

    /**
     * Calling CommonTest.java to Set up RESTAssured defaults & Reading the inputs from the testenv.properties file before
     * executing test cases
     */
    static String HOSTNAME;

    static String portno;

    static String serveruri;

    static String authToken;
    static BigInteger catid = null;
    ;
    static String TENANT_ID_OPC1;
    static String TENANT_ID1;

    @AfterClass
    public static void afterTest() {

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
			logger.error(e.getLocalizedMessage());
		}
	}*/
    public static void importCategories() throws JSONException {
        String jsonString1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
                + "<CategorySet><Category><Name>MyCategory</Name>"
                + "<ProviderName>Name</ProviderName><ProviderVersion>1</ProviderVersion><ProviderAssetRoot>Root</ProviderAssetRoot>"
                + "</Category></CategorySet>";
        Response res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when().post("/importcategories");
        Assert.assertEquals(res1.getStatusCode(), 200);
        JSONArray arrfld = new JSONArray(res1.getBody().asString());
        for (int index = 0; index < arrfld.length(); index++) {
            JSONObject jsonObj = arrfld.getJSONObject(index);
            catid = new BigInteger(jsonObj.getString("id"));
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

        try {

            FolderCRUD.importCategories();

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
			logger.error(e.getLocalizedMessage());
		}

	}*/

    @Test
    /**
     * Folder Negative Case6:
     */
    public void fetchfolderNotExist() {
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1)
                .when().get("/entities?folderId=99999999999");
        Assert.assertTrue(res.getStatusCode() == 404);
        Assert.assertEquals(res.asString(), "Folder with the Id 99999999999 does not exist");
    }

    @Test
    /**
     * Check for the valid response body when the folder is not available
     */
    public void folderCheckNONexistency() {
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/folder/333");

        Assert.assertTrue(res.getStatusCode() == 404);

        Assert.assertEquals(res.asString(), "Folder with the Id 333 does not exist");
    }

    @Test
    /**
     * create a folder using post method
     */
    public void folderCreate() {
        int position;
        String jsonString = "{ \"name\":\"Custom_Folder\",\"description\":\"Folder for EMAAS searches\"}";
        Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
                .post("/folder");
        Assert.assertTrue(res1.getStatusCode() == 201);
        String id = res1.jsonPath().getString("id");
        Assert.assertEquals(res1.jsonPath().get("createdOn"), res1.jsonPath().get("lastModifiedOn"));

        String jsonString2 = "{ \"name\":\"Custom_Folder\" }";
        Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString2).when()
                .post("/folder");
        Assert.assertTrue(res2.getStatusCode() == 400);
        System.out.println(res2.jsonPath().toString());
        String message = res2.jsonPath().getString("message");
        Assert.assertEquals(message, "The folder name 'Custom_Folder' already exist");
        Assert.assertEquals(res1.jsonPath().getString("id"), res2.jsonPath().getString("id"));
        Assert.assertEquals(res2.jsonPath().getInt("errorCode"), 30021);
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + id);
        JsonPath jp = res.jsonPath();
        List<String> a;
        a = jp.get("name");

        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals("Custom_Folder")) {
                position = i;
                Assert.assertEquals(a.get(position), "Custom_Folder");
            }
        }

    }

    @Test
    /**
     * create a folder using post method in a specified parentId
     */
    public void folderCreateSpecifiedParentId() {
        String jsonString1 = "{ \"name\":\"TestFolderParent\", \"description\":\"Folder for EMAAS searches\"}";
        Response resp1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
                .post("/folder");
        JsonPath jpp1 = resp1.jsonPath();
        String parentFolder = jpp1.getString("id");

        int position;
        String jsonString = "{ \"name\":\"TestFolder\", \"description\":\"Folder for EMAAS searches\",\"parentFolder\":{\"id\":\""
                + parentFolder + "\"}}";
        Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
                .post("/folder");
        JsonPath jp1 = res1.jsonPath();
        Assert.assertTrue(res1.getStatusCode() == 201);
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + parentFolder);
        JsonPath jp = res.jsonPath();
        List<String> a;
        a = jp.get("name");
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals("TestFolder")) {
                position = i;
                Assert.assertEquals(a.get(position), "TestFolder");
            }
        }
        Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                .delete("/folder/" + jp1.get("id"));
        Assert.assertTrue(res2.getStatusCode() == 204);
        res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/" + parentFolder);
        Assert.assertTrue(res2.getStatusCode() == 204);
    }

    @Test
    /**
     * create a folder using post method in a specified parentId "0"
     */
    public void folderCreateSpecifiedParentIdIdIsZero() {
        String jsonString1 = "{ \"name\":\"TestFolderP\", \"description\":\"Folder for EMAAS searches\"}";
        Response resp1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
                .post("/folder");
        JsonPath jpp1 = resp1.jsonPath();

        String parentFolder = jpp1.getString("id");

        int position;
        String jsonString = "{ \"name\":\"TestFolder\", \"description\":\"Folder for EMAAS searches\",\"parentFolder\":{\"id\":\"0\"}}";
        Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
                .post("/folder");
        JsonPath jp1 = res1.jsonPath();
        Assert.assertTrue(res1.getStatusCode() == 201);
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + parentFolder);
        JsonPath jp = res.jsonPath();
        List<String> a;
        a = jp.get("name");

        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals("TestFolder")) {
                position = i;
                Assert.assertEquals(a.get(position), "TestFolder");
            }
        }
        Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                .delete("/folder/" + jp1.get("id"));
        Assert.assertTrue(res2.getStatusCode() == 204);

        res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/" + parentFolder);
        Assert.assertTrue(res2.getStatusCode() == 204);
    }

    @Test
    /**
     * create a folder using post method
     */
    public void folderCreatewithEmptyName() {
        String jsonString = "{ \"name\":\" \",\"description\":\"Folder for EMAAS searches\"}";
        Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
                .post("/folder");
        Assert.assertTrue(res1.getStatusCode() == 400);
        Assert.assertEquals(res1.asString(), "The name key for folder can not be empty in the input JSON Object");
    }

    @Test
    /**
     * update a folder using put method
     */
    public void folderEdit() {
        String id = getRootId();
        int position;
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + id);
        JsonPath jp = res.jsonPath();
        List<String> a = jp.get("name");
        List<String> b = jp.get("id");
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals("Custom_Folder")) {
                position = i;
                String myfolderID = b.get(position);
                String jsonString = "{ \"name\":\"Custom_Folder_Edit\" }";
                Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                        .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString)
                        .when().put("/folder/" + myfolderID);
                String c;
                JsonPath jp1 = res1.jsonPath();
                c = jp1.get("name");
                Assert.assertEquals(c, "Custom_Folder_Edit");
                String str_createdOn = jp1.get("createdOn");
                String str_modifiedOn = jp1.get("lastModifiedOn");
                Assert.assertNotEquals(str_createdOn, str_modifiedOn);
                String jsonString1 = "{ \"description\":\"Folder for EMAAS searches_Edit\"}";
                Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                        .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1)
                        .when().put("/folder/" + myfolderID);
                String str_desc;
                JsonPath jp2 = res2.jsonPath();
                str_desc = jp2.get("description");
                Assert.assertEquals(str_desc, "Folder for EMAAS searches_Edit");
            }
        }
    }

    /**
     * Edit the folder parentId
     */
    @Test
    public void folderEditSpecifiedParentId() {
        int position;

        String jsonString1 = "{ \"name\":\"TestFolder_ParentId\", \"description\":\"Folder for EMAAS searches\"}";
        Response res11 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
                .post("/folder");
        JsonPath jp11 = res11.jsonPath();
        String pid = jp11.get("id");
        String jsonString = "{ \"name\":\"TestFolder_ParentId\", \"description\":\"Folder for EMAAS searches\",\"parentFolder\":{\"id\":\""
                + pid + "\"}}";
        Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
                .post("/folder");
        JsonPath jp1 = res1.jsonPath();
        Assert.assertTrue(res1.getStatusCode() == 201);
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + pid);
        JsonPath jp = res.jsonPath();
        List<String> a = jp.get("name");
        List<String> b = jp.get("id");

        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals("TestFolder_ParentId")) {
                position = i;
                String myfolderID = b.get(position);
                String jsonString_parentId = "{\"parentFolder\":{\"id\":\"" + pid + "\"}}";

                Response res_parentId = RestAssured.given().contentType(ContentType.JSON).log().everything()
                        .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1)
                        .body(jsonString_parentId).when().put("/folder/" + myfolderID);
                Assert.assertTrue(res_parentId.getStatusCode() == 200);
            }
        }
        res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + getRootId());
        jp = res.jsonPath();
        List<String> a1 = jp.get("name");

        boolean existflag = false;

        for (int j = 0; j < a1.size(); j++) {
            if (a1.get(j).equals("TestFolder_ParentId")) {
                existflag = true;
                break;
            }
        }

        Assert.assertTrue(existflag);

        Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                .delete("/folder/" + jp1.get("id"));
        Assert.assertTrue(res2.getStatusCode() == 204);

        res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/" + pid);
        Assert.assertTrue(res2.getStatusCode() == 204);
    }

    /**
     * Edit the folder parentId, the parentId is 0
     */
    @Test
    public void folderEditSpecifiedParentIdIdIsZero() {
        String jsonString1 = "{ \"name\":\"TestFolder_ParentId_Zero1\", \"description\":\"Folder for EMAAS searches\"}";
        Response res11 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
                .post("/folder");
        JsonPath jp11 = res11.jsonPath();
        String parentId = jp11.get("id");

        int position;
        String jsonString = "{ \"name\":\"TestFolder_ParentId_Zero\", \"description\":\"Folder for EMAAS searches\",\"parentFolder\":{\"id\":\""
                + parentId + "\"}}";
        Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
                .post("/folder");
        JsonPath jp1 = res1.jsonPath();
        Assert.assertTrue(res1.getStatusCode() == 201);
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + parentId);
        JsonPath jp = res.jsonPath();
        List<String> a = jp.get("name");
        List<String> b = jp.get("id");

        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals("TestFolder_ParentId_Zero")) {
                position = i;

                String myfolderID = b.get(position);
                String jsonString_parentId = "{\"parentFolder\":{\"id\":0}}";
                Response res_parentId = RestAssured.given().contentType(ContentType.JSON).log().everything()
                        .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1)
                        .body(jsonString_parentId).when().put("/folder/" + myfolderID);
                Assert.assertTrue(res_parentId.getStatusCode() == 200);

            }
        }

        String id = getRootId();

        res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + id);
        jp = res.jsonPath();
        List<String> a1 = jp.get("name");

        boolean existflag = false;

        for (int j = 0; j < a1.size(); j++) {
            if (a1.get(j).equals("TestFolder_ParentId_Zero")) {
                existflag = true;
                break;
            }
        }

        Assert.assertTrue(existflag);

        Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                .delete("/folder/" + jp1.get("id"));
        Assert.assertTrue(res2.getStatusCode() == 204);

        res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/" + parentId);
        Assert.assertTrue(res2.getStatusCode() == 204);

    }

    /**
     * Edit the folder with empty folder name
     */
    @Test
    public void folderEditwithEmptyName() {
        String jsonString = "{ \"name\":\"Custom_Folder_EditEmptyName\",\"description\":\"Folder for EMAAS searches\",}";
        Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
                .post("/folder");
        JsonPath jp1 = res1.jsonPath();
        Assert.assertTrue(res1.getStatusCode() == 201);
        String jsonString1 = "{ \"name\":\" \" }";
        Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
                .put("/folder/" + jp1.get("id"));
        Assert.assertTrue(res2.getStatusCode() == 400);
        Assert.assertEquals(res2.asString(), "The name key for folder can not be empty in the input JSON Object");
        Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                .delete("/folder/" + jp1.get("id"));
        Assert.assertTrue(res3.getStatusCode() == 204);
    }

    @Test
    public void folderFieldCharValidation() {
        String result = "abc<";

        String description = "abc>";

        String jsonString = "{ \"name\":" + "\"" + result + "\"" + ",\"description\":\"" + "abc" + "\"}";
        Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
                .post("/folder");
        Assert.assertEquals(
                "The folder name contains at least one invalid character ('<' or '>'), please correct folder name and retry",
                res1.getBody().asString());
        Assert.assertTrue(res1.getStatusCode() == 400);

        jsonString = "{ \"name\":" + "\"" + "ABC " + "\"" + ",\"description\":\"" + description + "\"}";
        res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().post("/folder");
        Assert.assertEquals(
                "The folder description contains at least one invalid character ('<' or '>'), please correct folder description and retry",
                res1.getBody().asString());
        Assert.assertTrue(res1.getStatusCode() == 400);
        jsonString = "{ \"name\":" + "\"" + result + "\"" + ",\"description\":\"" + "abc" + "\"}";
        res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().put("/folder/" + 1);

        Assert.assertEquals(
                "The folder name contains at least one invalid character ('<' or '>'), please correct folder name and retry",
                res1.getBody().asString());
        Assert.assertTrue(res1.getStatusCode() == 400);

        jsonString = "{ \"name\":" + "\"" + "ABC " + "\"" + ",\"description\":\"" + description + "\"}";
        res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().put("/folder/" + 1);
        Assert.assertEquals(
                "The folder description contains at least one invalid character ('<' or '>'), please correct folder description and retry",
                res1.getBody().asString());
        Assert.assertTrue(res1.getStatusCode() == 400);
    }

    @Test
    public void folderFieldValidationLength() {
        int n = 65;
        char[] chars = new char[n];
        Arrays.fill(chars, 'c');
        String result = new String(chars);
        chars = new char[257];
        Arrays.fill(chars, 'c');
        String description = new String(chars);

        String jsonString = "{ \"name\":" + "\"" + result + "\"" + ",\"description\":\"" + "abc" + "\"}";
        Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
                .post("/folder");
        Assert.assertEquals("The maximum length of a name is 64 bytes.Please enter valid name.", res1.getBody().asString());
        Assert.assertTrue(res1.getStatusCode() == 400);

        jsonString = "{ \"name\":" + "\"" + "ABC " + "\"" + ",\"description\":\"" + description + "\"}";
        res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().post("/folder");
        Assert.assertEquals("The maximum length of a description is 256 bytes.Please enter valid description.", res1
                .getBody().asString());
        Assert.assertTrue(res1.getStatusCode() == 400);

        jsonString = "{ \"name\":" + "\"" + result + "\"" + ",\"description\":\"" + "abc" + "\"}";

        res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().put("/folder/" + 1);

        Assert.assertEquals("The maximum length of a name is 64 bytes.Please enter valid name.", res1.getBody().asString());
        Assert.assertTrue(res1.getStatusCode() == 400);

        jsonString = "{ \"name\":" + "\"" + "ABC " + "\"" + ",\"description\":\"" + description + "\"}";
        res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().put("/folder/" + 1);
        Assert.assertEquals("The maximum length of a description is 256 bytes.Please enter valid description.", res1
                .getBody().asString());
        Assert.assertTrue(res1.getStatusCode() == 400);
    }

    @Test
    /**
     * Folder: Invalid folderId:
     */
    public void folderInvalidFolderId() {
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=0");

        Assert.assertTrue(res.getStatusCode() == 400);
        Assert.assertEquals(res.asString(), "Invalid folderId: 0");
    }

    @Test
    /**
     * Folder Negative Case5:
     */
    public void folderInvalidParentId() {
        String jsonString = "{ \"name\":\"TestFolder\", \"description\":\"Folder for EMAAS searches\",\"parentFolder\":{\"id\":333333}}";
        Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
                .post("/folder");
        Assert.assertTrue(res1.getStatusCode() == 404);
        Assert.assertEquals(res1.asString(), "Parent folder with Id 333333 does not exist");
    }

    @Test
    /**
     * Folder Negative Case3:
     */
    public void folderInvalidType() {
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=me");
        Assert.assertEquals(res.getStatusCode(), 404);
    }

    @Test
    /**
     * Folder Negative Case7:
     */
    public void folderMissingInfoPath() {
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId");
        Assert.assertTrue(res.getStatusCode() == 400);
        Assert.assertEquals(res.asString(), "Please give folderId");
        Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=");
        Assert.assertTrue(res1.getStatusCode() == 400);
        Assert.assertEquals(res1.asString(), "Please give folderId");
        Response res2 = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities");
        Assert.assertTrue(res2.getStatusCode() == 400);
        Assert.assertEquals(res2.asString(), "Please give folderId");
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
			logger.error(e.getLocalizedMessage());
		}
	}*/

    @Test
    /**
     * Folder Negative Case4:
     */
    public void folderMissingMandateinfo() {
        String jsonString = "{\"parent\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\"}";
        Response res = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when()
                .post("/folder");
        Assert.assertTrue(res.getStatusCode() == 400);
        Assert.assertEquals(res.asString(), "The name key for folder is missing in the input JSON Object");
    }

    @Test
    /**
     * Delete a folder using DELETE method
     */
    public void getFolderDelete() {
        //Get Root folder id
    	String id = getRootId();
        int position;
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + id);
        JsonPath jp = res.jsonPath();
        List<String> a = jp.get("name");
        List<String> b = jp.get("id");

        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).equals("Custom_Folder_Edit")) {
                position = i;
                String myfolderID = b.get(position);
                Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                        .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                        .delete("/folder/" + myfolderID);
                Assert.assertTrue(res1.getStatusCode() == 204);
            }
        }
    }

    @Test
    /**
     * Folder Negative Case8: Delete a non-exist folder
     */
    public void nonexistFolderDelete() {
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().delete("/folder/3333333333");
        Assert.assertTrue(res.getStatusCode() == 404);
        Assert.assertEquals(res.asString(), "Folder with Id 3333333333 does not exist");
    }

    /**
     * searches by folder with folder Id which is negative number
     */
    @Test
    public void searchesbyFolderIdNegativeNumber() {
        Response res = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?folderId=-1");
        Assert.assertTrue(res.getStatusCode() == 400);
        Assert.assertEquals(res.asString(), "Id/count should be a positive number and not an alphanumeric");
    }

    @Test
    /**
     * create a folder and searches in it using post method to verify the functionality of find searches by folderId
     */
    public void searchesByFolderId() {
    	String id = getRootId();
        String jsonString1 = "{ \"name\":\"Folder_searches6\",\"description\":\"Folder for EMAAS searches\"}";
        Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when()
                .post("/folder");
        JsonPath jp1 = res1.jsonPath();
        System.out.print(res1.getStatusCode());
        Assert.assertTrue(res1.getStatusCode() == 201);
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
        String jsonString2 = "{\"name\":\"Search_s1\",\"category\":{\"id\":\""
                + catid
                + "\"},\"folder\":{\"id\":\""
                + jp1.get("id")
                + "\"},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING,\"value\":\"my_value\"}]}";
        Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString2).when()
                .post("/search");
        JsonPath jp2 = res2.jsonPath();
        System.out.println(res2.getStatusCode());
        Assert.assertTrue(res2.getStatusCode() == 201);
        Response res10 = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/searches?folderId=" + jp1.get("id"));
        JsonPath jp10 = res10.jsonPath();
        Assert.assertTrue(res10.getStatusCode() == 200);
        Assert.assertTrue(jp10.get("name[0]").equals("Search_s1"));
        String jsonString3 = "{\"name\":\"Search_s2\",\"category\":{\"id\":\""
                + catid
                + "\"},\"folder\":{\"id\":\""
                + jp1.get("id")
                + "\"},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample2\",\"type\":STRING,\"value\":\"my_value\"}]}";
        Response res3 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString3).when()
                .post("/search");
        JsonPath jp3 = res3.jsonPath();
        Assert.assertTrue(res3.getStatusCode() == 201);
        Response res4 = RestAssured.given().log().everything().header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/entities?folderId=" + jp1.get("id"));
        JsonPath jp4 = res4.jsonPath();
        Assert.assertTrue(res4.getStatusCode() == 200);
        Response res5 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                .delete("/folder/" + jp1.get("id"));
        Assert.assertTrue(res5.getStatusCode() == 500);
        Assert.assertEquals(res5.asString(), "The folder can not be deleted as folder is associated with searches");
        if ("Search_s2".equals(jp4.get("name[0]"))) {
            Response res6 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                    .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                    .delete("/search/" + jp3.get("id"));
            Assert.assertTrue(res6.getStatusCode() == 204);
            Response res7 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                    .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                    .delete("/search/" + jp2.get("id"));
            Assert.assertTrue(res7.getStatusCode() == 204);
        } else {

            Response res7 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                    .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                    .delete("/search/" + jp2.get("id"));
            Assert.assertTrue(res7.getStatusCode() == 204);
            Response res6 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                    .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                    .delete("/search/" + jp3.get("id"));
            Assert.assertTrue(res6.getStatusCode() == 204);
        }
        Response res8 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                .delete("/folder/" + jp1.get("id"));
        Assert.assertTrue(res8.getStatusCode() == 204);
        Response res9 = RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                .delete("/folder/" + jp1.get("id"));
        Assert.assertTrue(res9.getStatusCode() == 404);
        Assert.assertEquals(res9.asString(), "Folder with Id " + jp1.get("id") + " does not exist");
    }

	private String getRootId()
	{
		Response resroot = RestAssured.given().log().everything().header("Authorization", authToken)
				.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("");
		JsonPath jpRoot = resroot.jsonPath();

		List<String> id = jpRoot.get("id");
		return id.get(0);
	}

}

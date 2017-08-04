package oracle.sysman.emSDK.emaas.platform.savedsearch.test.navigations;

import java.math.BigInteger;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class SavedSearchNavigations {

    /**
     * Calling CommonTest.java to Set up RESTAssured defaults & Reading the inputs from the testenv.properties file before
     * executing test cases
     */
    static String HOSTNAME;
    static String portno;
    static String serveruri;
    static String authToken;
	static BigInteger catid = BigInteger.ONE.negate();
	static BigInteger folderid = BigInteger.ONE.negate();
    static String catName = "";
    static String TENANT_ID_OPC1 = TestConstant.TENANT_ID_OPC0;
    static String TENANT_ID1 = TestConstant.TENANT_ID0;

    @AfterClass
    public static void afterTest() {
        Response res2 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID_OPC1).header("Authorization", authToken)
                .header(TestConstant.X_HEADER, TENANT_ID1).when().delete("/folder/" + folderid);
        Assert.assertTrue(res2.getStatusCode() == 204);
    }

    public static void createinitObject() {

        String jsonString = "{ \"name\":\"FolderTesting\",\"description\":\"Folder for  searches\"}";
        Response res = RestAssured.given().contentType(ContentType.JSON).log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID_OPC1).header("Authorization", authToken)
                .header(TestConstant.X_HEADER, TENANT_ID1).body(jsonString).when().post("/folder");
        folderid = new BigInteger(res.jsonPath().getString("id"));

		/*String jsonString1 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><CategorySet><Category><Name>MyCategoryTesting</Name><Description>Testing</Description><DefaultFolderId>"
                + folderid + "</DefaultFolderId></Category></CategorySet>";
		Response res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken).header(TestConstant.X_HEADER, TENANT_ID1)
				.body(jsonString1).when().post("/importcategories");
		Assert.assertEquals(res1.getStatusCode(), 200);
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for (int index = 0; index < arrfld.length(); index++) {
			System.out.println("verifying categoryids");
			JSONObject jsonObj = arrfld.getJSONObject(index);
			catid = jsonObj.getInt("id");
			catName = jsonObj.getString("name");
			System.out.println("verified categoryids");
		}*/
    }

    @BeforeClass
    public static void setUp() {
        CommonTest ct = new CommonTest();
        HOSTNAME = ct.getHOSTNAME();
        portno = ct.getPortno();
        serveruri = ct.getServeruri();
        authToken = ct.getAuthToken();
        TENANT_ID1 = ct.getTenant() + "." + ct.getRemoteUser();
        TENANT_ID_OPC1 = ct.getTenant();
        SavedSearchNavigations.createinitObject();

    }

    @Test
    /**
     * Check for system folders
     */
    public void getallDefaultFolders() {
        int position;
        Response res = RestAssured.given().log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID_OPC1).header("Authorization", authToken)
                .header(TestConstant.X_HEADER, TENANT_ID1).when().get("/entities?folderId=" + folderid);
        JsonPath jp = res.jsonPath();
        List<String> a = jp.get("name");
        for (int i = 0; i < a.size(); i++) {
            if ("FolderTesting".equals(a.get(i))) {
                position = i;
                Assert.assertEquals(a.get(position), "FolderTesting");

            }
        }
        Assert.assertTrue(res.getStatusCode() == 200);
    }

    @Test
    /**
     * get entities with non existed Folder Id
     */
    public void getEntities_nonexistFolderId() {
        Response res = RestAssured.given().log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID_OPC1).header("Authorization", authToken)
                .header(TestConstant.X_HEADER, TENANT_ID1).when().get("/entities?folderId=3333333333333");
        Assert.assertEquals(res.asString(), "Folder with the Id 3333333333333 does not exist");
        Assert.assertEquals(res.getStatusCode(), 404);
    }

    @Test
    /**
     * Check for root search folder
     */
    public void getMainFolder() {
        Response res = RestAssured.given().log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID_OPC1).header("Authorization", authToken)
                .header(TestConstant.X_HEADER, TENANT_ID1).when().get("/");
        JsonPath jp = res.jsonPath();
        List<String> a = jp.get("name");

        boolean exist = false;
        for (int i = 0; i < a.size(); i++) {
            if ("All Searches".equals(a.get(i))) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            Assert.fail("Root folder: All Searches does not exist!");
        }
        Assert.assertTrue(res.getStatusCode() == 200);
    }

    @Test
    /**
     * Checking details of the root folder
     */
    public void rootFolderDetails() {
    	String id = getRootId();
        Response res = RestAssured.given().log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID_OPC1).header("Authorization", authToken)
                .header(TestConstant.X_HEADER, TENANT_ID1).when().get("/folder/" + id);
        JsonPath jp = res.jsonPath();
        Assert.assertEquals(jp.get("id"), id);
        Assert.assertEquals(jp.get("name"), "All Searches");
        Assert.assertEquals(jp.get("description"), "Search Console Root Folder");
        Assert.assertEquals(jp.get("systemFolder"), true);
    }

    private String getRootId() {
        Response resroot = RestAssured.given().log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID_OPC1).header("Authorization", authToken)
                .header(TestConstant.X_HEADER, TENANT_ID1).when().get("");
        JsonPath jpRoot = resroot.jsonPath();
        List<String> id = jpRoot.get("id");
        return id.get(0);
    }
}

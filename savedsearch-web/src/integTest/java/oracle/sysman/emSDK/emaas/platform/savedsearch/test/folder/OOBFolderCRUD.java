package oracle.sysman.emSDK.emaas.platform.savedsearch.test.folder;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class OOBFolderCRUD {
	static String authToken;
	static String TENANT_ID1;

	@BeforeClass
	public static void setUp()
	{
		CommonTest ct = new CommonTest();
		authToken = ct.getAuthToken();
		TENANT_ID1 = ct.getTenant() + "." + ct.getRemoteUser();
	}
	
	@Test
	public void testFolder401()
	{
		testOOBFolder("401", "UDE Type-Ahead Feature");
	}
	
	private void testOOBFolder(String folderId, String folderName)
	{
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/folder/{folderId}", folderId);
			Assert.assertEquals(res.getStatusCode(), 200);

			JsonPath jp = res.jsonPath();
			Assert.assertEquals(jp.get("id"), folderId);
			Assert.assertEquals(jp.get("name"), folderName);
			Assert.assertEquals(jp.get("owner"), "ORACLE");
			Assert.assertTrue(jp.getBoolean("systemFolder"));
			Assert.assertNotNull(jp.get("href"));
			Assert.assertTrue(String.valueOf(jp.get("href")).contains("/savedsearch/v1/folder/" + jp.get("id")));
	}
}

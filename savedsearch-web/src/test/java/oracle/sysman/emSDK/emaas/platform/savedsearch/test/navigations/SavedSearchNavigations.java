package oracle.sysman.emSDK.emaas.platform.savedsearch.test.navigations;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class SavedSearchNavigations {

	/**
	 * Calling CommonTest.java to Set up RESTAssured defaults & Reading the
	 * inputs from the testenv.properties file before executing test cases
	 * 
	 */

	static String HOSTNAME;
	static String portno;
	static String serveruri;

	@BeforeClass
	public static void setUp() {
		CommonTest ct = new CommonTest();
		HOSTNAME = ct.getHOSTNAME();
		portno = ct.getPortno();
		serveruri = ct.getServeruri();

	}

	@Test
	/**
	 * Check for root search folder
	 */
	public void getMainFolder() {
		try {
			System.out.println("This test is to get the root folder details");
			int position = -1;
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/");
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Type       :" + jp.get("type"));
			System.out.println("Name 	   :" + jp.get("name"));
			System.out.println("ID   	   :" + jp.get("id"));
			System.out.println("URL  	   :" + jp.get("href"));
			System.out.println("Description:" + jp.get("description"));
			System.out.println("CreatedOn  :" + jp.get("createdOn"));
			System.out.println("ModifiedOn :" + jp.get("lastModifiedOn"));
			
			List<String> a = new ArrayList<String>();
			a = jp.get("name");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("All Searches")) {
					position = i;
					String myvalue = a.get(position);
					System.out.println("Top level default folder name is:"
							+ myvalue);
					Assert.assertEquals(a.get(position), "All Searches");
				}
			}
			
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println("------------------------------------------");
			System.out.println("											");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Check for system folders
	 */
	public void getallDefaultFolders() {
		try {
			System.out
					.println("This test is to get all the folders available with details");
			int position = -1;
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/entities?folderId=1");
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Type        :" + jp.get("type[0]"));
			System.out.println("FolderNames :" + jp.get("name"));
			System.out.println("Folder IDs  :" + jp.get("id"));
			System.out.println("Folder URLs :" + jp.get("href"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("IT Analytics Searches")) {
					position = i;
					String myvalue = a.get(position);
					System.out
							.println("My search container name is:" + myvalue);
					Assert.assertEquals(a.get(position),
							"IT Analytics Searches");
					System.out
							.println("==GET & Assert operations are succeeded");

				}
			}
			if (position == -1)
				System.out.println("Container that am looking does not exist");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println("------------------------------------------");
			System.out.println("											");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Checking details of the root folder
	 */
	public void rootFolderDetails() {
		try {
			System.out
					.println("This test is to get the details of the top root folder");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/folder/1");
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("FolderName  :" + jp.get("name"));
			System.out.println("ID   		:" + jp.get("id"));
			System.out.println("Link 		:" + jp.get("href"));
			System.out.println("CreatedOn   :" + jp.get("createdOn"));
			System.out.println("ModifiedOn  :" + jp.get("lastModifiedOn"));
			Assert.assertEquals(jp.get("id"),1);
			Assert.assertEquals(jp.get("name"),"All Searches");
			Assert.assertEquals(jp.get("description"),"Search Console Root Folder");
			Assert.assertEquals(jp.get("systemFolder"), true);
			System.out.println("------------------------------------------");
			System.out.println("											");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*@Test
	*//**
	 * Check for status and response with bad methods
	 *//*
	public void applyBadMethods() {
		try {
			System.out
					.println("This test is to verify response and status with bad use of bad methods");
			System.out.println("Case1");
			String jsonString1 = "{\"name\":\"check1\",\"categoryId\":3,\"folderId\":3,\"description\":\"mydb.err logs!!!\",\"queryStr\": \"target.name=mydb.mydomain ERR*\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString1).when().post("/");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println(res1.asString());
			Assert.assertTrue(res1.getStatusCode() == 405);
			Assert.assertEquals(res1.asString(), "Method Not Allowed");
			System.out.println("											");
			System.out.println("Case2");
			System.out.println("											");
			String jsonString2 = "{\"name\":\"check1\",\"categoryId\":3,\"folderId\":3,\"description\":\"mydb.err logs!!!\",\"queryStr\": \"target.name=mydb.mydomain ERR*\"}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString2).when().put("/");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println(res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 405);
			Assert.assertEquals(res2.asString(), "Method Not Allowed");
			System.out.println("											");
			System.out.println("Case3");
			System.out.println("											");
			String jsonString3 = "{\"name\":\"check1\",\"categoryId\":3,\"folderId\":3,\"description\":\"mydb.err logs!!!\",\"queryStr\": \"target.name=mydb.mydomain ERR*\"}";
			Response res3 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString3).when().delete("/");
			System.out.println("Status code is: " + res3.getStatusCode());
			System.out.println(res3.asString());
			Assert.assertTrue(res3.getStatusCode() == 405);
			Assert.assertEquals(res3.asString(), "Method Not Allowed");
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	

}
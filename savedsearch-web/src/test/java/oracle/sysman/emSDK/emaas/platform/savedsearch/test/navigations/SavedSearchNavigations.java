package oracle.sysman.emSDK.emaas.platform.savedsearch.test.navigations;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class SavedSearchNavigations
{

	/**
	 * Calling CommonTest.java to Set up RESTAssured defaults & Reading the inputs from the testenv.properties file before
	 * executing test cases
	 */

	static String HOSTNAME;
	static String portno;
	static String serveruri;

	@BeforeClass
	public static void setUp()
	{
		CommonTest ct = new CommonTest();
		HOSTNAME = ct.getHOSTNAME();
		portno = ct.getPortno();
		serveruri = ct.getServeruri();
	}

	@Test
	/**
	 * Check for system folders
	 */
	public void getallDefaultFolders()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to get all the folders available with details");
			int position = -1;
			Response res = RestAssured.given().log().everything().when().get("/entities?folderId=1");
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
					System.out.println("My search container name is:" + myvalue);
					Assert.assertEquals(a.get(position), "IT Analytics Searches");
					System.out.println("==GET & Assert operations are succeeded");

				}
			}
			if (position == -1) {
				System.out.println("Container that am looking does not exist");
			}

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	/**
	 * get entities with non existed Folder Id
	 */
	public void getEntities_nonexistFolderId()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to verify get entities with non-existed folder Id");

			Response res = RestAssured.given().log().everything().when().get("/entities?folderId=3333333333333");
			Assert.assertEquals(res.asString(), "Folder with the Id 3333333333333 does not exist");
			Assert.assertEquals(res.getStatusCode(), 404);
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	/**
	 * Check for root search folder
	 */
	public void getMainFolder()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to get the root folder details");
			int position = -1;
			Response res = RestAssured.given().log().everything().when().get("/");
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
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	/**
	 * Checking details of the root folder
	 */
	public void rootFolderDetails()
	{
		try {
			System.out.println("------------------------------------------");
			System.out.println("This test is to get the details of the top root folder");
			Response res = RestAssured.given().log().everything().when().get("/folder/1");
			System.out.println(res.toString());
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("FolderName  :" + jp.get("name"));
			System.out.println("ID   		:" + jp.get("id"));
			System.out.println("Link 		:" + jp.get("href"));
			System.out.println("CreatedOn   :" + jp.get("createdOn"));
			System.out.println("ModifiedOn  :" + jp.get("lastModifiedOn"));
			Assert.assertEquals(jp.get("id"), 1);
			Assert.assertEquals(jp.get("name"), "All Searches");
			Assert.assertEquals(jp.get("description"), "Search Console Root Folder");
			Assert.assertEquals(jp.get("systemFolder"), true);
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

}

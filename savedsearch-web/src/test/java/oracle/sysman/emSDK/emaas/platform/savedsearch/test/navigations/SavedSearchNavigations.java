package oracle.sysman.emSDK.emaas.platform.savedsearch.test.navigations;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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
	 * Check for root search folder
	 */
	public void getMainFolder()
	{
		try {
			System.out.println("This test is to get the root folder details");
			Response res = given().headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().when().get("/");
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Type       :" + jp.get("type[0]"));
			System.out.println("FolderName :" + jp.get("name"));
			System.out.println("Folder ID  :" + jp.get("id"));
			System.out.println("Folder URL :" + jp.get("link"));
			Assert.assertEquals(jp.getString("name"), "[All_searches]");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Check for system folders
	 */
	public void getallDefaultFolders()
	{
		try {
			System.out.println("This test is to get all the folders available with details");
			int position = -1;
			Response res = given().headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().when()
					.get("/1?type=folder");
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Type        :" + jp.get("type[0]"));
			System.out.println("FolderNames :" + jp.get("name"));
			System.out.println("Folder IDs  :" + jp.get("id"));
			System.out.println("Folder URLs :" + jp.get("link"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("ITA_searches")) {
					position = i;
					String myvalue = a.get(position);
					System.out.println("My Search name is:" + myvalue);
					Assert.assertEquals(a.get(position), "ITA_searches");
					System.out.println("==GET & Assert operations are succeeded");

				}
			}
			if (position == -1)
				System.out.println("search does not exist");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
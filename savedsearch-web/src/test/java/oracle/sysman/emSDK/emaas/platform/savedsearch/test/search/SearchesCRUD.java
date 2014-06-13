package oracle.sysman.emSDK.emaas.platform.savedsearch.test.search;

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

public class SearchesCRUD
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
	 * Search Negative Case1:
	 */
	public void search_badURLs()
	{
		try {
			System.out.println("Negative Case1 for SEARCH");
			System.out.println("											");
			System.out.println("GET operation is in-progress with bad URL");
			System.out.println("											");

			Response res = RestAssured.given().headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().when()
					.get("/search");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 405);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Method Not Allowed");
			System.out.println("											");
			System.out.println("POST operation is in-progress with bad URL");
			System.out.println("											");
			String jsonString1 = "{\"categoryId\":1,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"name\": \"target\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().body(jsonString1).when().post("/");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 405);
			System.out.println(res1.asString());
			Assert.assertEquals(res1.asString(), "Method Not Allowed");
			System.out.println("											");
			System.out.println("PUT operation is in-progress with bad URL");
			System.out.println("											");
			String jsonString2 = "{\"displayName\":\"My_Search!!!\",\"categoryId\":1,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"name\": \"target\"}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON).headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().body(jsonString2).when().put("/");
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 405);
			System.out.println(res2.asString());
			Assert.assertEquals(res2.asString(), "Method Not Allowed");
			System.out.println("											");
			System.out.println("DELETE operation is in-progress with bad URL");
			System.out.println("											");
			String jsonString3 = "{\"displayName\":\"My_Search!!!\",\"categoryId\":1,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"name\": \"target\"}";
			Response res3 = RestAssured.given().contentType(ContentType.JSON).headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().body(jsonString3).when().delete("/");
			System.out.println("											");
			System.out.println("Status code is: " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 405);
			System.out.println(res3.asString());
			Assert.assertEquals(res3.asString(), "Method Not Allowed");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Check for the valid response body when the search does not exist
	 */
	public void search_check_NONexistency()
	{
		try {
			System.out.println("This test is to Check for the valid response body when the search isn't available");
			Response res = RestAssured.given().headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().when()
					.get("/search/555");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			Assert.assertTrue(res.getStatusCode() == 404);
			String resbody = res.asString();
			System.out.println("Result:" + resbody);

			Assert.assertEquals(res.asString(), "search object by ID: 555 does not exist");

			System.out.println("Asserted the search and its non existance");
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * create a search using post method
	 */
	public void search_create()
	{
		try {
			System.out.println("This test is to create a search with POST method");
			System.out.println("											");
			int position = -1;
			String jsonString = "{\"name\":\"Custom Search\",\"categoryId\":1,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().body(jsonString).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");
			System.out.println(res1.asString());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("==POST operation is completed");
			System.out.println("											");

			System.out.println("This test is to check for the duplicate entry with re-post");
			System.out.println("											");

			String jsonString2 = "{\"name\":\"Custom Search\",\"categoryId\":1,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON).headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().body(jsonString2).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			System.out.println("											");
			System.out.println(res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.asString(), "search name Custom Search already exist");
			System.out.println("    ");
			System.out.println("GET operation is in-progress to assert the successful search creation");
			System.out.println("											");
			Response res = RestAssured.given().headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().when()
					.get("/2?type=folder");
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("SearchName :" + jp.get("name"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Custom Search")) {
					position = i;

					String myvalue = a.get(position);
					System.out.println("My new Search name is:" + myvalue);
					Assert.assertEquals(a.get(position), "Custom Search");
					System.out.println("==GET & Assert operations are succeeded");

				}
			}
			if (position == -1) {
				System.out.println("search does not exist");
			}

			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Search Negative Case5:
	 */
	public void search_create_invalidCategory()
	{
		try {
			System.out.println("This test is to create a search with POST method using invalid categoryId");
			System.out.println("											");

			String jsonString = "{\"name\":\"TestSearch\",\"categoryId\":2000,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().body(jsonString).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");
			System.out.println(res1.asString() + ", hence POST operation to create a search is not success");
			Assert.assertTrue(res1.getStatusCode() == 404);
			Assert.assertEquals(res1.asString(), "Can not find category with id: 2000");
			System.out.println("											");
			System.out.println("------------------------------------------");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Search Negative Case6:
	 */
	public void search_create_invalidfolderId()
	{
		try {
			System.out.println("This test is to create a search with POST method using invalid folderId");
			System.out.println("											");

			String jsonString = "{\"name\":\"TestSearch\",\"displayName\":\"My_Search!!!\",\"categoryId\":1,\"folderId\":3000,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().body(jsonString).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");
			System.out.println(res1.asString() + ", hence POST operation to create a search is not success");
			Assert.assertTrue(res1.getStatusCode() == 404);
			Assert.assertEquals(res1.asString(), "Can not find folder with id: 3000");
			System.out.println("											");
			System.out.println("------------------------------------------");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Search Negative Case6:
	 */
	public void search_create_invalidparamType()
	{
		try {
			System.out.println("This test is to create a search with POST method using invalid paramType");
			System.out.println("											");

			String jsonString = "{\"name\":\"TestSearch\",\"displayName\":\"My_Search!!!\",\"categoryId\":1,\"folderId\":3000,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":\"text\",\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON).headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().body(jsonString).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");
			System.out.println(res1.asString() + ", hence POST operation to create a search is not success");
			Assert.assertTrue(res1.getStatusCode() == 404);
			Assert.assertEquals(res1.asString(), "Invalid param type, please specify either STRING or CLOB.");
			System.out.println("											");
			System.out.println("------------------------------------------");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * update a search using put method
	 */
	public void search_edit()
	{
		try {
			System.out.println("This test is to edit a search with PUT method");
			System.out.println("											");
			System.out.println("GET operation is in-progress to select the search to be edited");
			System.out.println("											");
			int position = -1;
			Response res = RestAssured.given().headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().when()
					.get("/2?type=folder");
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
				if (a.get(i).equals("Custom Search")) {
					position = i;

					int searchID = b.get(position);

					Assert.assertEquals(a.get(position), "Custom Search");
					System.out.println("==GET operation is completed");
					System.out.println("											");
					System.out.println("PUT operation is in-progress to edit search");
					System.out.println("											");
					String jsonString = "{ \"name\":\"Custom Search_Edit\", \"queryStr\": \"target.name=mydb.mydomain message like ERR1*\",\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";

					Response res1 = RestAssured.given().contentType(ContentType.JSON)
							.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().body(jsonString).when()
							.put("/search/" + searchID);
					System.out.println("											");
					System.out.println("Status code is: " + res1.getStatusCode());
					System.out.println("											");
					System.out.println(res1.asString());
					String c;
					JsonPath jp1 = res1.jsonPath();
					c = jp1.get("name");
					System.out.println("SearchName after Edit is :" + c);
					Assert.assertEquals(jp1.get("name"), "Custom Search_Edit");
					System.out.println("==PUT operation is succeeded");

					Assert.assertTrue(res.getStatusCode() == 200);
					System.out.println("------------------------------------------");
					System.out.println("											");
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Search Negative Case3:
	 */
	public void search_InvalidType()
	{
		try {
			System.out.println("Negative Case3 for SEARCH");
			System.out.println("											");
			System.out.println("GET operation is in-progress with Invalid Type");
			System.out.println("											");

			Response res = RestAssured.given().headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().when()
					.get("/1?type=me");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Type not supported");
			System.out.println("											");

			System.out.println("------------------------------------------");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Search Negative Case4:
	 */
	public void search_missingMandateinfo()
	{
		try {
			System.out.println("Negative Case4 for SEARCH");
			System.out.println("											");
			System.out.println("POST operation is in-progress & missing with required field: Name");
			System.out.println("											");
			String jsonString = "{\"categoryId\":1,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res = RestAssured.given().contentType(ContentType.JSON).headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().body(jsonString).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println(res.asString());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println("											");
			Assert.assertEquals(res.asString(), "The name key for search is missing in the input JSON Object");
			System.out.println("											");
			/*System.out
					.println("POST operation is in-progress & missing with required field: displayName");
			System.out.println("											");
			String jsonString1 = "{\"name\":\"MyLostSearch!!!\",\"categoryId\":1,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString1).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");
			System.out.println(res1.asString());
			Assert.assertTrue(res1.getStatusCode() == 400);
			System.out.println("											");
			Assert.assertEquals(res1.asString(),
					"The displayName key for search is missing in the input JSON Object");
			System.out.println("											");*/
			System.out.println("POST operation is in-progress & missing with required field: categoryId");
			System.out.println("											");
			String jsonString2 = "{\"name\":\"MyLostSearch\",\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON).headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().body(jsonString2).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			System.out.println("											");
			System.out.println(res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 400);
			System.out.println("											");
			Assert.assertEquals(res2.asString(), "The categoryId key for search is missing in the input JSON Object");
			System.out.println("											");
			System.out.println("POST operation is in-progress & missing with required field: folderId");
			System.out.println("											");
			String jsonString3 = "{\"displayName\":\"My_Search!!!\",\"categoryId\":1,\"name\":\"My_Search\",\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res3 = RestAssured.given().contentType(ContentType.JSON).headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().body(jsonString3).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res3.getStatusCode());
			System.out.println("											");
			System.out.println(res3.asString());
			Assert.assertTrue(res3.getStatusCode() == 400);
			System.out.println("											");
			Assert.assertEquals(res3.asString(), "The folderId key for search is missing in the input JSON Object");
			System.out.println("											");
			System.out.println("POST operation is in-progress & missing with required field: name from parameter section");
			System.out.println("											");
			String jsonString4 = "{\"name\":\"Custom Search\",\"displayName\":\"My_Search!!!\",\"categoryId\":1,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res4 = RestAssured.given().contentType(ContentType.JSON).headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().body(jsonString4).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res4.getStatusCode());
			System.out.println("											");
			System.out.println(res4.asString());
			Assert.assertTrue(res4.getStatusCode() == 400);
			System.out.println("											");
			Assert.assertEquals(res4.asString(), "The name key for search parameter is missing in the input JSON Object");
			System.out.println("											");

			System.out.println("POST operation is in-progress & missing with required field: type from parameter section");
			System.out.println("											");
			String jsonString5 = "{\"name\":\"Custom Search\",\"displayName\":\"My_Search!!!\",\"categoryId\":1,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"value\":\"my_value\"}]}";
			Response res5 = RestAssured.given().contentType(ContentType.JSON).headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().body(jsonString5).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res5.getStatusCode());
			System.out.println("											");
			System.out.println(res5.asString());
			Assert.assertTrue(res5.getStatusCode() == 400);
			System.out.println("										");
			Assert.assertEquals(res5.asString(), "The type key for search param is missing in the input JSON Object");

			System.out.println("											");
			System.out.println("------------------------------------------");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Search Negative Case2:
	 */
	public void search_noType()
	{
		try {
			System.out.println("Negative Case2 for SEARCH");
			System.out.println("											");
			System.out.println("GET operation is in-progress with No Type");
			System.out.println("											");

			Response res = RestAssured.given().headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().when()
					.get("/1");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "please specify Type");
			System.out.println("------------------------------------------");
			System.out.println("											");

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Delete a Search using DELETE method
	 */
	public void search_remove()
	{
		try {
			System.out.println("This test is to delete a search with DELETE method");
			System.out.println("											");
			System.out.println("GET operation is in-progress to select the search to be deleted");
			System.out.println("											");
			int position = -1;
			Response res = RestAssured.given().headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().when()
					.get("/2?type=folder");
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			JsonPath jp = res.jsonPath();
			System.out.println("SearchName :" + jp.get("name"));
			System.out.println("Search ID  :" + jp.get("id"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");
			List<Integer> b = new ArrayList<Integer>();
			b = jp.get("id");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Custom Search_Edit")) {
					position = i;
					System.out.println("Index is:" + position);
					int mysearchID = b.get(position);

					System.out.println("My Value is:" + mysearchID);
					System.out.println("==GET operation is completed");
					System.out.println("											");
					System.out.println("DELETE operation is in-progress to delete the selected search");
					System.out.println("											");
					Response res1 = RestAssured.given().contentType(ContentType.JSON)
							.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().when()
							.delete("/search/" + mysearchID);
					System.out.println("											");
					System.out.println("Status code is: " + res1.getStatusCode());
					System.out.println("											");
					System.out.println(res1.asString());
					Assert.assertTrue(res1.getStatusCode() == 204);
					Response res2 = RestAssured.given().contentType(ContentType.JSON)
							.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().when()
							.get("/search/" + mysearchID);
					System.out.println(res2.asString());
					System.out.println("Status code is: " + res2.getStatusCode());
					System.out.println("==DELETE operation is succeeded");
					System.out.println("------------------------------------------");
					System.out.println("											");
				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

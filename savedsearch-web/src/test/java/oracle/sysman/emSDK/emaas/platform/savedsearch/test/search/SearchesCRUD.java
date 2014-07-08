package oracle.sysman.emSDK.emaas.platform.savedsearch.test.search;

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

public class SearchesCRUD {
	/**
	 * Calling CommonTest.java to Set up RESTAssured defaults & Reading the
	 * inputs from the testenv.properties file before executing test cases
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
	 * Search Negative Case1:
	 */
	public void search_badURLsANDMethods() {
		try {
			System.out.println("Negative Case1 for SEARCH");
			System.out.println("											");
			System.out.println("GET operation is in-progress with bad URL");
			System.out.println("											");

			Response res = RestAssured.given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/search");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 405);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Method Not Allowed");
			System.out.println("											");
			System.out.println("POST operation is in-progress with bad URL");
			System.out.println("											");
			String jsonString1 = "{\"category\":{\"id\":1},\"folder\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"name\": \"target\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString1).when().post("/");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 405);
			System.out.println(res1.asString());
			Assert.assertEquals(res1.asString(), "Method Not Allowed");
			System.out.println("											");
			System.out.println("PUT operation is in-progress with bad URL");
			System.out.println("											");
			String jsonString2 = "{\"displayName\":\"My_Search!!!\",\"category\":{\"id\":1},\"folder\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"name\": \"target\"}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString2).when().put("/");
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 405);
			System.out.println(res2.asString());
			Assert.assertEquals(res2.asString(), "Method Not Allowed");
			System.out.println("											");
			System.out.println("DELETE operation is in-progress with bad URL");
			System.out.println("											");
			String jsonString3 = "{\"displayName\":\"My_Search!!!\",\"category\":{\"id\":1},\"folder\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"name\": \"target\"}";
			Response res3 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString3).when().delete("/");
			System.out.println("											");
			System.out.println("Status code is: " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 405);
			System.out.println(res3.asString());
			Assert.assertEquals(res3.asString(), "Method Not Allowed");
			System.out.println("											");
			String jsonString4 = "";
			Response res4 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString4).when().post("/search");
			System.out.println("Status code is: " + res4.getStatusCode());
			System.out.println(res4.asString());
			Assert.assertTrue(res4.getStatusCode() == 400);
			Assert.assertEquals(res4.asString(), "Bad Request");
			System.out.println("											");
			Response res5 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().post("/search/12345");
			System.out.println("Status code is: " + res5.getStatusCode());
			System.out.println(res5.asString());
			Assert.assertTrue(res5.getStatusCode() == 405);
			Assert.assertEquals(res5.asString(), "Method Not Allowed");
			System.out.println("											");
			String jsonString6 = "";
			Response res6 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString6).when().put("/search/12345");
			System.out.println("Status code is: " + res6.getStatusCode());
			System.out.println(res6.asString());
			Assert.assertTrue(res6.getStatusCode() == 400);
			Assert.assertEquals(res6.asString(), "Bad Request");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Check for the valid response body when the search does not exist
	 */
	public void search_check_NONexistency() {
		try {
			System.out
					.println("This test is to Check for the valid response body when the search isn't available");
			Response res = RestAssured.given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/search/555");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			Assert.assertTrue(res.getStatusCode() == 404);
			String resbody = res.asString();
			System.out.println("Result:" + resbody);

			Assert.assertEquals(res.asString(),
					"Search identified by ID: 555 does not exist");

			System.out.println("Asserted the search and its non existance");
			System.out.println("------------------------------------------");
			System.out.println("											");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * create a search using post method
	 */
	public void search_create() {
		try {
			System.out
					.println("This test is to create a search with POST method");
			System.out.println("											");
			int position = -1;
			String jsonString = "{\"name\":\"Custom_Search\",\"category\":{\"id\":1},\"folder\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/search");
			JsonPath jp1 = res1.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");
			System.out.println(res1.asString());
			System.out.println("Custom_Search Id is :" + jp1.get("id"));
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("==POST operation is completed");
			System.out.println("											");

			System.out
					.println("This test is to check for the duplicate entry with re-post");
			System.out.println("											");

			String jsonString2 = "{\"name\":\"Custom_Search\",\"category\":{\"id\":1},\"folder\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString2).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			System.out.println("											");
			System.out.println(res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.asString(),
					"Search with this name already exist: Custom_Search");
			System.out.println("    ");
			System.out
					.println("GET operation is in-progress to assert the successful search creation");
			System.out.println("											");
			Response res = RestAssured.given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/entities?folderId=2");
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("SearchName :" + jp.get("name"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Custom_Search")) {
					position = i;

					String myvalue = a.get(position);
					System.out.println("My new Search name is:" + myvalue);
					Assert.assertEquals(a.get(position), "Custom_Search");
					System.out
							.println("==GET & Assert operations are succeeded");

				}
			}
			if (position == -1) {
				System.out.println("search does not exist");
			}
			/*
			 * Response res3 =
			 * RestAssured.given().contentType(ContentType.JSON).
			 * headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
			 * .log().everything().when().delete("/search/"+jp1.get("id"));
			 * //JsonPath jp6 = res6.jsonPath();
			 * System.out.println(res3.asString());
			 * Assert.assertTrue(res3.getStatusCode() == 204);
			 */
			System.out.println("------------------------------------------");
			System.out.println("											");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Search Negative Case5:
	 */
	public void search_create_invalidCategory() {
		try {
			System.out
					.println("This test is to create a search with POST method using invalid categoryId");
			System.out.println("											");

			String jsonString = "{\"name\":\"TestSearch\",\"category\":{\"id\":12000},\"folder\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");
			System.out
					.println(res1.asString()
							+ ", hence POST operation to create a search is not success");
			Assert.assertTrue(res1.getStatusCode() == 404);
			Assert.assertEquals(res1.asString(),
					"Can not find category with id: 12000");
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Search Negative Case6:
	 */
	public void search_create_invalidfolderId() {
		try {
			System.out
					.println("This test is to create a search with POST method using invalid folderId");
			System.out.println("											");

			String jsonString = "{\"name\":\"TestSearch\",\"displayName\":\"My_Search!!!\",\"category\":{\"id\":1},\"folder\":{\"id\":3000},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");
			System.out
					.println(res1.asString()
							+ ", hence POST operation to create a search is not success");
			Assert.assertTrue(res1.getStatusCode() == 404);
			Assert.assertEquals(res1.asString(),
					"Can not find folder with id: 3000");
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Search Negative Case6:
	 */
	public void search_create_invalidparamType() {
		try {
			System.out
					.println("This test is to create a search with POST method using invalid paramType");
			System.out.println("											");

			String jsonString = "{\"name\":\"TestSearch\",\"displayName\":\"My_Search!!!\",\"category\":{\"id\":1},\"folder\":{\"id\":3000},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":\"text\",\"value\":\"my_value\"}]}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");
			System.out
					.println(res1.asString()
							+ ", hence POST operation to create a search is not success");
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.asString(),
					"Invalid param type, please specify either STRING or CLOB");
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * update a search using put method
	 */
	public void search_edit() {
		try {
			System.out.println("This test is to edit a search with PUT method");
			System.out.println("											");
			System.out
					.println("GET operation is in-progress to select the search to be edited");
			System.out.println("											");
			int position = -1;
			Response res = RestAssured.given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/entities?folderId=2");
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
				if (a.get(i).equals("Custom_Search")) {
					position = i;

					int searchID = b.get(position);

					Assert.assertEquals(a.get(position), "Custom_Search");
					System.out.println("==GET operation is completed");
					System.out.println("											");
					System.out
							.println("PUT operation is in-progress to edit search");
					System.out.println("											");
					String jsonString = "{ \"name\":\"Custom_Search_Edit\", \"queryStr\": \"target.name=mydb.mydomain message like ERR1*\",\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";

					Response res1 = RestAssured.given()
							.contentType(ContentType.JSON)
							.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
							.log().everything().body(jsonString).when()
							.put("/search/" + searchID);
					System.out.println("											");
					System.out.println("Status code is: "
							+ res1.getStatusCode());
					System.out.println("											");
					System.out.println(res1.asString());
					String c;
					JsonPath jp1 = res1.jsonPath();
					c = jp1.get("name");
					System.out.println("SearchName after Edit is :" + c);
					Assert.assertEquals(jp1.get("name"), "Custom_Search_Edit");
					System.out.println("==PUT operation is succeeded");

					Assert.assertTrue(res.getStatusCode() == 200);
					System.out
							.println("------------------------------------------");
					System.out.println("											");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * @Test
	 *//**
	 * Search Negative Case3:
	 */
	/*
	 * public void search_InvalidType() { try {
	 * System.out.println("Negative Case3 for SEARCH");
	 * System.out.println("											"); System.out
	 * .println("GET operation is in-progress with Invalid Type");
	 * System.out.println("											");
	 * 
	 * Response res = RestAssured.given()
	 * .headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
	 * .everything().when().get("/1?type=me");
	 * 
	 * System.out.println("											"); System.out.println("Status code is: "
	 * + res.getStatusCode()); Assert.assertTrue(res.getStatusCode() == 400);
	 * System.out.println(res.asString()); Assert.assertEquals(res.asString(),
	 * "Type not supported"); System.out.println("											");
	 * 
	 * System.out.println("------------------------------------------"); } catch
	 * (Exception e) { e.printStackTrace(); } }
	 */
	@Test
	/**
	 * Search Negative Case4:
	 */
	public void search_missingMandateinfo() {
		try {
			System.out.println("Negative Case4 for SEARCH");
			System.out.println("											");
			System.out
					.println("POST operation is in-progress & missing with required field: Name");
			System.out.println("											");
			String jsonString = "{\"category\":{\"id\":1},\"folder\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println(res.asString());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println("											");
			Assert.assertEquals(res.asString(),
					"The name key for search is missing in the input JSON Object");
			System.out.println("											");
			/*
			 * System.out .println(
			 * "POST operation is in-progress & missing with required field: displayName"
			 * ); System.out.println("											"); String jsonString1 =
			 * "{\"name\":\"MyLostSearch!!!\",\"categoryId\":1,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}"
			 * ; Response res1 = given().contentType(ContentType.JSON)
			 * .headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
			 * .everything().body(jsonString1).when().post("/search");
			 * System.out.println("											");
			 * System.out.println("Status code is: " + res1.getStatusCode());
			 * System.out.println("											");
			 * System.out.println(res1.asString());
			 * Assert.assertTrue(res1.getStatusCode() == 400);
			 * System.out.println("											");
			 * Assert.assertEquals(res1.asString(),
			 * "The displayName key for search is missing in the input JSON Object"
			 * ); System.out.println("											");
			 */
			System.out
					.println("POST operation is in-progress & missing with required field: categoryId");
			System.out.println("											");
			String jsonString2 = "{\"name\":\"MyLostSearch\",\"folder\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString2).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			System.out.println("											");
			System.out.println(res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 400);
			System.out.println("											");
			Assert.assertEquals(res2.asString(),
					"The category key for search is missing in the input JSON Object");
			System.out.println("											");
			System.out
					.println("POST operation is in-progress & missing with required field: folderId");
			System.out.println("											");
			String jsonString3 = "{\"displayName\":\"My_Search!!!\",\"category\":{\"id\":1},\"name\":\"My_Search\",\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res3 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString3).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res3.getStatusCode());
			System.out.println("											");
			System.out.println(res3.asString());
			Assert.assertTrue(res3.getStatusCode() == 400);
			System.out.println("											");
			Assert.assertEquals(res3.asString(),
					"The folder key for search is missing in the input JSON Object");
			System.out.println("											");
			System.out
					.println("POST operation is in-progress & missing with required field: name from parameter section");
			System.out.println("											");
			String jsonString4 = "{\"name\":\"Custom_Search\",\"displayName\":\"My_Search!!!\",\"category\":{\"id\":1},\"folder\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res4 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString4).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res4.getStatusCode());
			System.out.println("											");
			System.out.println(res4.asString());
			Assert.assertTrue(res4.getStatusCode() == 400);
			System.out.println("											");
			Assert.assertEquals(res4.asString(),
					"The name key for search parameter is missing in the input JSON Object");
			System.out.println("											");

			System.out
					.println("POST operation is in-progress & missing with required field: type from parameter section");
			System.out.println("											");
			String jsonString5 = "{\"name\":\"Custom_Search\",\"displayName\":\"My_Search!!!\",\"category\":{\"id\":1},\"folder\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"value\":\"my_value\"}]}";
			Response res5 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString5).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res5.getStatusCode());
			System.out.println("											");
			System.out.println(res5.asString());
			Assert.assertTrue(res5.getStatusCode() == 400);
			System.out.println("										");
			Assert.assertEquals(res5.asString(),
					"The type key for search param is missing in the input JSON Object");

			System.out.println("											");
			System.out.println("------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Search Negative Case2:
	 */
	public void search_useInvalidMethod() {
		try {
			System.out.println("Negative Case2 for SEARCH");
			System.out.println("											");
			System.out.println("POST operation is in-progress with No Type");
			System.out.println("											");

			Response res = RestAssured.given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().post("/");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 405);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Method Not Allowed");
			System.out.println("------------------------------------------");
			System.out.println("											");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Delete a Search using DELETE method
	 */
	public void search_remove() {
		try {
			System.out
					.println("This test is to delete a search with DELETE method");
			System.out.println("											");
			System.out
					.println("GET operation is in-progress to select the search to be deleted");
			System.out.println("											");
			int position = -1;
			Response res = RestAssured.given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/entities?folderId=2");
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
				if (a.get(i).equals("Custom_Search_Edit")) {
					position = i;
					System.out.println("Index is:" + position);
					int mysearchID = b.get(position);

					System.out.println("My Value is:" + mysearchID);
					System.out.println("==GET operation is completed");
					System.out.println("											");
					System.out
							.println("Read the search details before its deletion");
					Response res0 = given()
							.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
							.log().everything().when()
							.get("/search/" + mysearchID);
					JsonPath jp0 = res0.jsonPath();
					System.out.println("											");
					Assert.assertEquals(jp0.get("name"), "Custom_Search_Edit");
					Assert.assertEquals(jp0.get("id"), +mysearchID);
					Assert.assertEquals(jp0.get("description"),
							"mydb.mydomain error logs (ORA*)!!!");
					Assert.assertEquals(jp0.getMap("category").get("id"), 1);
					Assert.assertEquals(jp0.getMap("folder").get("id"), 2);
					Assert.assertEquals(jp0.get("href"), "http://" + HOSTNAME
							+ ":" + portno + "/savedsearch/v1/search/"
							+ mysearchID);
					System.out
							.println("------------------------------------------");
					System.out
							.println("DELETE operation is in-progress to delete the selected search");
					System.out.println("											");
					Response res1 = RestAssured.given()
							.contentType(ContentType.JSON)
							.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
							.log().everything().when()
							.delete("/search/" + mysearchID);
					System.out.println("											");
					System.out.println("Status code is: "
							+ res1.getStatusCode());
					System.out.println("											");
					System.out.println(res1.asString());
					Assert.assertTrue(res1.getStatusCode() == 204);
					Response res2 = RestAssured.given()
							.contentType(ContentType.JSON)
							.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
							.log().everything().when()
							.get("/search/" + mysearchID);
					System.out.println(res2.asString());
					System.out.println("Status code is: "
							+ res2.getStatusCode());
					System.out.println("==DELETE operation is succeeded");
					System.out
							.println("------------------------------------------");
					System.out.println("											");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * set last access time to a search using PUT method
	 */
	public void setlastaccesstime_Tosearch() {
		try {
			System.out
					.println("This test is to create a search with POST method");
			System.out.println("											");

			String jsonString1 = "{\"name\":\"SearchSet1\",\"category\":{\"id\":3},\"folder\":{\"id\":3},\"description\":\"mydb.err logs!!!\",\"queryStr\": \"target.name=mydb.mydomain ERR*\"}";
			Response res1 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString1).when().post("/search");
			JsonPath jp1 = res1.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");
			// System.out.println(res1.asString());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("SearchSet1 Id is :" + jp1.get("id"));
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			String jsonString2 = "{\"name\":\"SearchSet2\",\"category\":{\"id\":3},\"folder\":{\"id\":3},\"description\":\"mydb.err logs!!!\",\"queryStr\": \"target.name=mydb.mydomain ERR*\"}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString2).when().post("/search");
			JsonPath jp2 = res2.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			System.out.println("											");
			// System.out.println(res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 201);
			System.out.println("SearchSet2 Id is :" + jp2.get("id"));
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			System.out.println("==POST operation is completed");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out
					.println("This test is to return the top two last accessed searches with GET method");
			System.out.println("											");
			Response res3 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/searches?lastAccessCount=2");
			JsonPath jp3 = res3.jsonPath();
			// System.out.println(res3.asString());
			System.out.println("Last accessed top 2 search Id's are  :"
					+ jp3.get("id"));
			System.out.println("											");
			System.out.println("------------------------------------------");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			System.out
					.println("Now set the last access time to the search whose id: "
							+ jp3.get("id[1]") + " with PUT method");
			Response res4 = RestAssured
					.given()
					.contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log()
					.everything()
					.when()
					.put("/search/" + jp3.get("id[1]")
							+ "?updateLastAccessTime=true");
			// JsonPath jp4 = res4.jsonPath();
			System.out.println(res4.asString());
			System.out.println("											");
			System.out.println("Status code is: " + res4.getStatusCode());
			Assert.assertTrue(res4.getStatusCode() == 200);
			System.out.println("											");
			System.out.println("------------------------------------------");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			System.out
					.println("This test is to return the top two last accessed searches again with GET method");
			System.out.println("											");
			Response res5 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/searches?lastAccessCount=2");
			JsonPath jp5 = res5.jsonPath();
			// System.out.println(res5.asString());
			System.out.println("Last accessed top 2 search Id's are  :"
					+ jp5.get("id"));
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out
					.println("Cleaning up the searches that are created in this scenario");
			Response res6 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().delete("/search/" + jp5.get("id[0]"));
			// JsonPath jp6 = res6.jsonPath();
			System.out.println(res6.asString());
			Assert.assertTrue(res6.getStatusCode() == 204);
			Response res7 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().delete("/search/" + jp5.get("id[1]"));
			// JsonPath jp7 = res7.jsonPath();
			System.out.println(res7.asString());
			Assert.assertTrue(res7.getStatusCode() == 204);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Test to return all last accessed searches
	 */
	public void returnAlllastaccessedSearches() {
		try {
			System.out
					.println("This test is to return all the last accessed searches with GET method");
			System.out.println("											");
			Response res = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/searches/");
			// System.out.println(res.asString());
			JsonPath jp = res.jsonPath();
			System.out.println("Searches Last Accessed Are :" + jp.get("name"));
			System.out.println("Status code is: " + res.getStatusCode());
			// Assert.assertNotEquals(res.asString(),"");
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Test to return NO last accessed searches
	 */
	public void returnNolastaccessedSearches() {
		try {
			System.out
					.println("This test is NOT to return any last accessed searches with GET method");
			System.out.println("											");
			Response res = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/searches?lastAccessCount=0");
			// JsonPath jp = res.jsonPath();
			// System.out.println(res.asString());
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertEquals(res.asString(), "");
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Test to return all last accessed searches with negative count
	 */
	public void returnNolastaccessedSearches_NegCount() {
		try {
			System.out
					.println("This test is NOT to return any last accessed searches with GET method for negative count");
			System.out.println("											");
			Response res = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/searches?lastAccessCount=-1");
			// JsonPath jp = res.jsonPath();
			// System.out.println(res.asString());
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertEquals(res.asString(),
					"Id/count should be a positive number and not an alphanumeric.");
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Test to return all last accessed searches with text as count
	 */
	public void returnNolastaccessedSearches_textCount() {
		try {
			System.out
					.println("This test is NOT to return any last accessed searches with GET method for text count");
			System.out.println("											");
			Response res = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/searches?lastAccessCount=sravan");
			// JsonPath jp = res.jsonPath();
			// System.out.println(res.asString());
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertEquals(res.asString(),
					"Id/count should be a positive number and not an alphanumeric.");
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Test verify the status and response with invalid methods 
	 */
	public void lastaccessedSearches_invalidObjects1() {
		try {
			System.out
					.println("Case1:This test is to check the status and response with invalid methods");
			System.out.println("											");
			Response res = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/search/10000000087?updateLastAccessTime=true");
			System.out.println(res.asString());
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertEquals(res.asString(),
					"Search identified by ID: 10000000087 does not exist");
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Test verify the status and response with invalid methods 
	 */
	public void lastaccessedSearches_invalidMethod2() {
		try {
			System.out
					.println("Case2:This test is to check the status and response with invalid methods");
			System.out.println("											");
			Response res = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.post("/search/100000087?updateLastAccessTime=true");
			System.out.println(res.asString());
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertEquals(res.asString(), "Method Not Allowed");
			Assert.assertTrue(res.getStatusCode() == 405);
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Test verify the status and response with invalid objects on a correct url path
	 */
	public void lastaccessedSearches_invalidObjects3() {
		try {
			System.out
					.println("Case3:This test is to check the status and response with invalid methods");
			System.out.println("											");
			Response res = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.delete("/search/1000000087?updateLastAccessTime=true");
			System.out.println(res.asString());
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertEquals(res.asString(),
					"Search with Id: 1000000087 does not exist");
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Test verify the status and response with invalid objects on a correct url path
	 */
	public void lastaccessedSearches_invalidObjects4() {
		try {
			System.out
					.println("Case4:This test is to check the status and response with invalid methods");
			System.out.println("											");
			Response res = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.put("/search/100000000087?updateLastAccessTime=true");
			System.out.println(res.asString());
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertEquals(res.asString(),
					"Search identified by ID: 100000000087 does not exist");
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * create a search using post method
	 */
	public void search_createwithEmptyName() {
		try {
			System.out
					.println("POST method is in-progress to create a new search with blank name");
			String jsonString = "{\"name\":\" \",\"category\":{\"id\":1},\"folder\":{\"id\":2},\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/search");
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.asString(),
					"The name key for search can not be empty in the input JSON Object");
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Test to verify the flattened folder path details for a search
	 */
	public void flattenedFolderPath() {
		try {
			System.out
					.println("Create a folder and a serch in it to see the hierarchy of folder path");
			// int position = -1;
			System.out.println("Creating a Folder");
			String jsonString = "{ \"name\":\"Folder_cont1\",\"description\":\"Folder for EMAAS searches\"}";
			Response res1 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/folder");
			JsonPath jp1 = res1.jsonPath();
			// System.out.println(res1.asString());
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("FolderName :" + jp1.get("name"));
			System.out.println("Folder ID  :" + jp1.get("id"));
			System.out.println("											");
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("Creating a Search");
			String jsonString2 = "{\"name\":\"Search_cont1\",\"categoryId\":1,\"folderId\":"
					+ jp1.get("id")
					+ ",\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING,\"value\":\"my_value\"}]}";
			Response res2 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString2).when().post("/search");
			JsonPath jp2 = res2.jsonPath();
			System.out.println("Status code is: " + res2.getStatusCode());
			// System.out.println(res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 201);
			System.out.println("											");
			System.out.println("Search Name :" + jp2.get("name"));
			System.out.println("Search ID  :" + jp2.get("id"));
			System.out.println("											");
			System.out
					.println("Trying to get search with flattened folder details");
			Response res3 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log()
					.everything()
					.when()
					.get("/search/" + jp2.get("id")
							+ "?flattenedFolderPath=true");
			JsonPath jp3 = res3.jsonPath();
			System.out.println("Status code is: " + res3.getStatusCode());
			System.out.println(res3.asString());
			System.out.println("Search Name :" + jp3.get("name"));
			System.out.println("Search ID  :" + jp3.get("id"));
			System.out.println("flattenedFolderPath :"
					+ jp3.get("flattenedFolderPath"));
			Assert.assertEquals(jp3.get("flattenedFolderPath[0]"),
					"Folder_cont1");
			Assert.assertEquals(jp3.get("flattenedFolderPath[1]"),
					"All Searches");
			System.out.println("											");
			System.out.println("Deleting search created above");
			System.out.println("											");
			Response res4 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().delete("/search/" + jp2.get("id"));
			// System.out.println(res4.asString());
			System.out.println("Status code is: " + res4.getStatusCode());
			System.out.println("											");
			System.out.println("Deleting folder created above");
			System.out.println("											");
			Response res5 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().delete("/folder/" + jp1.get("id"));
			System.out.println("											");
			System.out.println("Status code is: " + res5.getStatusCode());
			// System.out.println("											");
			// System.out.println(res5.asString());
			Assert.assertTrue(res5.getStatusCode() == 204);
			System.out.println("------------------------------------------");
			System.out.println("											");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

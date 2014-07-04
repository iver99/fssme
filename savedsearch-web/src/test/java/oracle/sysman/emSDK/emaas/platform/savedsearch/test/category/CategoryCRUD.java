package oracle.sysman.emSDK.emaas.platform.savedsearch.test.category;

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

public class CategoryCRUD {

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
	 * Check status and response for get all categories 
	 */
	public void listAllCategories() {
		try {
			System.out
					.println("Using GET method to retrieve the list of categories");
			System.out.println("											");
			Response res1 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/categories/");
			JsonPath jp1 = res1.jsonPath();
			// System.out.println(res1.asString());
			System.out.println("Categories existed :" + jp1.get("name"));
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 200);
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read Category Details :
	 */
	@Test
	public void getCategory_details() {
		try {

			System.out
					.println("GET operation is in-progress to read category details");
			System.out.println("											");

			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/category/1");
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("Category Name :" + jp.get("name"));
			System.out.println("Category Id   :" + jp.get("id"));
			System.out.println("Description   :" + jp.get("description"));
			Assert.assertEquals(jp.get("description"),
					"Search Category for Log Analytics");
			Assert.assertEquals(jp.get("name"), "Log Analytics");
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Category Negative Case1:
	 */
	@Test
	public void category_badURLs() {
		try {
			System.out.println("Negative Case1 for CATEGORY");
			System.out.println("											");
			System.out.println("GET operation is in-progress with bad URL");
			System.out.println("											");

			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/category/0");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(),
					"Category object by ID: 0 does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * List all the searches by category name
	 */
	@Test
	public void searchesbyCategoryName() {
		try {
			System.out
					.println("This test is to perform the operation that lists all the searches by the specified category name");
			System.out
					.println("Now creation of searches in the specified category with POST method");
			System.out.println("											");
			int position = -1;
			String jsonString1 = "{\"name\":\"Search_B\",\"categoryId\":3,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString1).when().post("/search");
			JsonPath jp1 = res1.jsonPath();
			System.out.println("											");
			System.out.println("Search Id  :" + jp1.get("id"));
			System.out.println("Search Name :" + jp1.get("name"));
			System.out.println("Status code is: " + res1.getStatusCode());
			// System.out.println(res1.asString());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("											");
			System.out
					.println("==POST operation is completed for creation searches using the specified category");
			System.out.println("											");

			System.out
					.println("Searches by category name is in-progress using GET method");
			System.out.println("											");
			Response res2 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/searches?categoryName=IT Analytics");
			JsonPath jp2 = res2.jsonPath();
			List<String> a = new ArrayList<String>();
			a = jp2.get("name");
			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Search_B")) {
					position = i;
					String myvalue = a.get(position);
					System.out
							.println("My Search in categoryname- IT Analytics is :"
									+ myvalue);
					Assert.assertEquals(a.get(position), "Search_B");
				}
			}

			System.out.println("Status code is: " + res2.getStatusCode());
			// System.out.println(res3.asString());
			Assert.assertTrue(res2.getStatusCode() == 200);
			System.out.println("											");
			System.out.println("Searches in the category:IT Analytics are: "
					+ jp2.get("name"));

			System.out.println("==Searches by category name is done");
			System.out
					.println("Cleaning up the searches that are created in this scenario");
			Response res3 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().delete("/search/" + jp1.get("id"));
			System.out.println(res3.asString());
			System.out.println("Status code is: " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 204);
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * List all the searches by category Id
	 */
	@Test
	public void searchesbyCategoryId() {
		try {
			System.out
					.println("Searches by category id is in-progress using GET method");
			System.out
					.println("Now creation of searches in the specified category with POST method");
			System.out.println("											");
			int position = -1;
			String jsonString1 = "{\"name\":\"Search_A\",\"categoryId\":3,\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString1).when().post("/search");
			JsonPath jp1 = res1.jsonPath();
			System.out.println("											");
			System.out.println("Search Id  :" + jp1.get("id"));
			System.out.println("Search Name :" + jp1.get("name"));
			System.out.println("Status code is: " + res1.getStatusCode());
			// System.out.println(res1.asString());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("											");
			System.out
					.println("==POST operation is completed for creation searches using the specified category");
			System.out.println("											");
			Response res2 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/searches?categoryId=3");
			JsonPath jp2 = res2.jsonPath();
			List<String> a = new ArrayList<String>();
			a = jp2.get("name");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Search_A")) {
					position = i;
					String myvalue = a.get(position);
					System.out.println("My Search in categoryId 3 is :"
							+ myvalue);
					Assert.assertEquals(a.get(position), "Search_A");
				}
			}
			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 200);
			System.out.println("											");
			System.out.println("Searches in the categoryId:3 are: "
					+ jp2.get("name"));

			System.out.println("==Searches by category id is done");
			System.out
					.println("Cleaning up the searches that are created in this scenario");
			Response res3 = RestAssured.given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().delete("/search/" + jp1.get("id"));
			System.out.println(res3.asString());
			System.out.println("Status code is: " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 204);
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * searches by category with incomplete/bad URL
	 */
	@Test
	public void searchesbyCategory_badRequest() {
		try {
			System.out
					.println("Case1:This test is to validate the response and status when the searches by category with bad url");
			System.out.println("											");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/searches?categoryId");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(),
					"Please give the value for categoryId");
			System.out.println("											");
			System.out
					.println("Case2:This test is to validate the response and status when id is missing");
			System.out.println("											");
			Response res1 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/searches?categoryId=");

			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);
			System.out.println(res1.asString());
			Assert.assertEquals(res1.asString(),
					"Please give the value for categoryId");
			System.out.println("											");
			System.out
					.println("Case3:This test is to validate the response and status when name is missing");
			System.out.println("											");
			Response res2 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/searches?categoryName=");

			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 400);
			System.out.println(res2.asString());
			Assert.assertEquals(res2.asString(),
					"Please give the value for categoryName");
			System.out.println("											");
			System.out.println("------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * searches by category with category ID which is not exist
	 */
	@Test
	public void searchesbyCategory_id_notExist() {
		try {
			System.out
					.println("This test is to validate the response when the search by category with category ID which is not exist");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/searches?categoryId=4567890");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(),
					"Category object by ID: 4567890 does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * searches by category with category name which is not exist
	 */
	@Test
	public void searchesbyCategory_invalid_name() {
		try {
			System.out
					.println("This test is to validate the response when the searches by category with category name which is not exist");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/searches?categoryName=MyAnalytics");
			System.out.println(res.asString());
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			// System.out.println(res.asString());
			Assert.assertEquals(res.asString(),
					"Category object by Name: MyAnalytics does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * searches by category with category whose name & id are not exist
	 */
	@Test
	public void searchesbyCategory_invalid_nameANDid() {
		try {
			System.out
					.println("This test is to validate the response when the search by category whose category name & id are not exist");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log()
					.everything()
					.when()
					.get("/searches?categoryName=invalidCategory&categoryId=200000");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(),
					"Category object by Name: invalidCategory does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * searches by category with category name(case sensitivity)
	 */
	@Test
	public void searchesbyCategory_querycaseCheck() {
		try {
			System.out
					.println("This test is to validate the response when the search by category");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/searches?categoryName=it analytics");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(),
					"Category object by Name: it analytics does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * searches by category with category name
	 */
	@Test
	public void searchesbyCategory_ParamcaseCheck() {
		try {
			System.out
					.println("This test is to validate the response when the search by category(query case check)");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/searches?categoryname=Log Analytics");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(),
					"please give either categoryId,categoryName,folderId or lastAccessCount");
			System.out.println("											");
			System.out.println("------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * searches by more than 2 parameters
	 */
	@Test
	public void searchesbyCategory_3queryParams() {
		try {

			System.out
					.println("This test is to validate the response & status with categoryName, categoryId & folderId combinations");
			Response res1 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log()
					.everything()
					.when()
					.get("/searches?categoryId=3&categoryname=Log Analytics&folderId=2");

			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 200);
			System.out.println("											");
			System.out.println("------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * searches by more than 2 parameters
	 */
	@Test
	public void searchesbyCategoryParams() {
		try {

			System.out
					.println("This test is to validate the response & status with more query params");
			Response res1 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log()
					.everything()
					.when()
					.get("/searches?categoryId&categoryname=Log Analytics&folderId=2");

			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println(res1.asString());
			Assert.assertTrue(res1.getStatusCode() == 400);
			Assert.assertEquals(res1.asString(),
					"Please give the value for categoryId");
			System.out.println("											");
			System.out.println("------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
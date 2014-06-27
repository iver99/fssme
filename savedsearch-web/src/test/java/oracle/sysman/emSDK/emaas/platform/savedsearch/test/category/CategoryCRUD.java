package oracle.sysman.emSDK.emaas.platform.savedsearch.test.category;

import static com.jayway.restassured.RestAssured.given;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

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

	/**
	 * create a Category using post method
	 */
	@Test
	public void category_crudOperations() {
		try {
			System.out
					.println("This test is to perform the crud operations on a Category only with POST/GET/PUT/DELETE method");
			System.out.println("											");
			System.out
					.println("POST operation is in-progress to create a category");
			System.out.println("											");

			String jsonString = "{ \"name\":\"My Test Category\", \"defaultFolderId\":2,\"parameters\":[{\"name\":\"sample\",\"type\":STRING,\"value\":\"my_value\"}] }";
			Response res = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/category");
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println(res.asString());
			Assert.assertTrue(res.getStatusCode() == 201);

			System.out.println("											");
			JsonPath jp = res.jsonPath();
			System.out.println("CategoryID is	:" + jp.get("id"));
			System.out.println("Name of Category:" + jp.get("name"));
			//System.out.println("DisplayName		:" + jp.get("displayName"));
			System.out.println("DefaultFolderID :" + jp.get("defaultFolderId"));
			Assert.assertEquals(jp.get("name"), "My Test Category");

			Assert.assertEquals(jp.get("defaultFolderId"), 2);
			System.out
					.println("==POST method for caregory is over and asserted the successful category creation");
			System.out.println("											");
			System.out
					.println("This test is to check for the duplicate entry with re-post");
			String jsonString4 = "{ \"name\":\"My Test Category\", \"defaultFolderId\":2,\"parameters\":[{\"name\":\"sample\",\"type\":STRING,\"value\":\"my_value\"}] }";
			Response res4 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString4).when().post("/category");
			System.out.println("											");
			System.out.println("Status code is: " + res4.getStatusCode());
			Assert.assertTrue(res4.getStatusCode() == 400);
			System.out.println(res4.asString());
			Assert.assertEquals(res4.asString(),
					"Category name My Test Category already exist");
			System.out
					.println("	Using GET method to retrieve the above category before edit");
			System.out.println("											");
			Response res1 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/category/" + jp.get("id"));
			System.out.println(res1.asString());
			JsonPath jp1 = res1.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");
			Assert.assertEquals(jp1.get("name"), "My Test Category");
			Assert.assertTrue(res1.getStatusCode() == 200);
			System.out.println("==GET method for caregory is over");
			System.out.println("											");
			System.out
					.println("	Using PUT method to update the above category");

			String jsonString1 = "{ \"name\":\"MyNEWCategory\", \"folderId\":2 }";
			Response res2 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString1).when()
					.put("/category/" + jp.get("id"));
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			System.out.println("											");
			System.out.println(res2.asString());
			JsonPath jp2 = res2.jsonPath();
			Assert.assertTrue(res2.getStatusCode() == 200);
			Assert.assertEquals(jp2.get("name"), "MyNEWCategory");
			/*Assert.assertEquals(jp2.get("displayName"),
					"My_Test_Category AFTER_EDIT!!!");*/

			System.out
					.println("==PUT method for caregory is over and asserted the successful edit");
			System.out.println("											");
			System.out
					.println("	Using DELETE method to remove the above category & verifying its non existence");

			Response res3 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().delete("/category/" + jp.get("id"));
			System.out.println("											");
			System.out.println("Status code is: " + res3.getStatusCode());
			System.out.println("											");
			System.out.println(res3.asString());
			Assert.assertTrue(res3.getStatusCode() == 204);

			System.out.println("==DELETE method for caregory is over");
			System.out.println("											");
			Response res5 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/category/" + jp.get("id"));
			System.out.println("											");
			System.out.println("Status code is: " + res5.getStatusCode());
			Assert.assertTrue(res5.getStatusCode() == 404);
			System.out.println("											");
			System.out.println(res5.asString());
			Assert.assertEquals(res5.asString(),
					"Category object by ID: " + jp.get("id")
							+ " does not exist");
			System.out.println("------------------------------------------");
			System.out.println("											");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * create a Category using post method
	 */
	@Test
	public void category_missingMandateinfo() {
		try {
			System.out.println("Negative Case1 for Category");
			System.out.println("											");
			System.out
					.println("POST operation is in-progress & missing with required field: Name");
			System.out.println("											");

			String jsonString1 = "{ \"defaultFolderId\":2,\"parameters\":[{\"name\":\"sample\",\"type\":STRING,\"value\":\"my_value\"}] }";
			Response res1 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString1).when().post("/category");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");
			Assert.assertTrue(res1.getStatusCode() == 400);
			System.out.println(res1.asString());
			Assert.assertEquals(res1.asString(),
					"The name key for category is missing in the input JSON Object");
			System.out.println("											");
			System.out
					.println("POST operation is in-progress & missing with required field: name (param)");
			System.out.println("											");

			String jsonString2 = "{ \"name\":\"My_Test_Category!!!\", \"defaultFolderId\":2,\"parameters\":[{\"type\":STRING,\"value\":\"my_value\"}] }";
			Response res2 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString2).when().post("/category");
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			System.out.println("											");
			Assert.assertTrue(res2.getStatusCode() == 400);
			System.out.println(res2.asString());
			Assert.assertEquals(res2.asString(),
					"The name key for category param is missing in the input JSON Object");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * create a Category using post method
	 */
	@Test
	public void category_check_NONexistency_defaultFolderId() {
		try {
			System.out.println("Negative Case2 for Category");
			System.out.println("											");
			System.out
					.println("POST operation is in-progress to create a category with non-available defaultFolderId ");
			System.out.println("											");

			String jsonString = "{ \"name\":\"NoCategory\", \"defaultFolderId\":2222,\"parameters\":[{\"name\":\"sample\",\"type\":STRING,\"value\":\"my_value\"}] }";
			Response res = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/category");
			System.out.println("											");
			System.out.println(res.asString());
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println("											");
			// System.out.println(res.asString());
			Assert.assertEquals(res.asString(),
					"Folder with id 2222 does not exist");
			System.out.println("------------------------------------------");
			System.out.println("											");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Category Negative Case3:
	 */
	@Test
	public void category_badURLs() {
		try {
			System.out.println("Negative Case3 for CATEGORY");
			System.out.println("											");
			System.out.println("GET operation is in-progress with bad URL");
			System.out.println("											");

			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/category");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "please give category name");
			System.out.println("											");
			System.out.println("POST operation is in-progress with bad URL");
			System.out.println("											");
			String jsonString1 = "{ \"name\":\"My Category\", \"defaultFolderId\":2222,\"parameters\":[{\"name\":\"sample\",\"type\":STRING,\"value\":\"my_value\"}] }";
			Response res1 = given().contentType(ContentType.JSON)
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
			String jsonString2 = "{ \"name\":\"My Category\", \"defaultFolderId\":2222,\"parameters\":[{\"name\":\"sample\",\"type\":STRING,\"value\":\"my_value\"}] }";
			Response res2 = given().contentType(ContentType.JSON)
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
			String jsonString3 = "{ \"name\":\"My Category\", \"defaultFolderId\":2222,\"parameters\":[{\"name\":\"sample\",\"type\":STRING,\"value\":\"my_value\"}] }";
			Response res3 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString3).when().delete("/");
			System.out.println("											");
			System.out.println("Status code is: " + res3.getStatusCode());
			Assert.assertTrue(res3.getStatusCode() == 405);
			System.out.println(res3.asString());
			Assert.assertEquals(res3.asString(), "Method Not Allowed");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * List all the searches by category name, by category Id
	 */
	@Test
	public void searchesbyCategory() {
		try {
			System.out
					.println("This test is to perform the operation that lists all the searches by the specified category id or by name");
			System.out.println("											");
			System.out
					.println("POST operation is in-progress to create a category");
			System.out.println("											");

			String jsonString = "{ \"name\":\"CategoryforSearches\", \"defaultFolderId\":2,\"parameters\":[{\"name\":\"sample\",\"type\":STRING,\"value\":\"my_value\"}] }";
			Response res = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/category");
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 201);
			System.out.println(res.asString());
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("CategoryID is	:" + jp.get("id"));
			System.out.println("Name of Category:" + jp.get("name"));
			System.out.println("											");
			System.out
					.println("Now creation of searches in the above category with POST method");
			System.out.println("											");

			String jsonString1 = "{\"name\":\"Search_A\",\"categoryId\":"
					+ jp.get("id")
					+ ",\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample1\",\"type\":STRING	,\"value\":\"my_value\"}]}";
			Response res1 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString1).when().post("/search");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println(res1.asString());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("											");
			/*
			 * String jsonString2 =
			 * "{\"name\":\"Search_B\",\"displayName\":\"Search2!!!\",\"categoryId\":"
			 * +jp.get("id")+
			 * ",\"folderId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample2\",\"type\":STRING	,\"value\":\"my_value\"}]}"
			 * ; Response res2 = given().contentType(ContentType.JSON)
			 * .headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
			 * .everything().body(jsonString2).when().post("/search");
			 * System.out.println("											");
			 * System.out.println("Status code is: " + res2.getStatusCode());
			 * 
			 * System.out.println(res2.asString());
			 * Assert.assertTrue(res2.getStatusCode() == 201);
			 * System.out.println("											");
			 */
			System.out
					.println("==POST operation is completed for creation of Category and searches using the same category");
			System.out.println("											");
			System.out
					.println("Searches by category name is in-progress using GET method");
			System.out.println("											");
			Response res3 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/findsearch/category?name=" + jp.get("name"));
			JsonPath jp3 = res3.jsonPath();
			System.out.println("Status code is: " + res3.getStatusCode());
			System.out.println(res3.asString());
			Assert.assertTrue(res3.getStatusCode() == 200);
			System.out.println("											");
			System.out.println("Searches in the category (name:"
					+ jp.get("name") + ") are: " + jp3.get("name"));

			System.out.println("==Searches by category name is done");
			System.out.println("											");
			System.out
					.println("Searches by category id is in-progress using GET method");
			System.out.println("											");
			Response res4 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/findsearch/category?id=" + jp.get("id"));
			JsonPath jp4 = res4.jsonPath();
			System.out.println("Status code is: " + res4.getStatusCode());
			System.out.println(res4.asString());
			Assert.assertTrue(res4.getStatusCode() == 200);
			System.out.println("											");
			System.out.println("Searches in the category (id :" + jp.get("id")
					+ ") are: " + jp4.get("name"));

			System.out.println("==Searches by category id is done");
			System.out.println("											");
			System.out
					.println("Delete category using DELETE method while the searches are defined in that category");
			Response res5 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().delete("/category/" + jp.get("id"));
			System.out.println("Status code is: " + res5.getStatusCode());
			System.out.println(res5.asString());
			Assert.assertTrue(res5.getStatusCode() == 500);
			Assert.assertEquals(res5.asString(),
					"Error occurred while deleting the category");

			System.out.println("											");
			System.out
					.println("Delete searches using DELETE method from the category whose id is :"
							+ jp.get("id"));
			Response res6 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().delete("/search/" + jp4.get("id[0]"));
			System.out.println("											");
			System.out.println("Status code is: " + res6.getStatusCode());
			System.out.println(res6.asString());
			Assert.assertTrue(res6.getStatusCode() == 204);
			System.out
					.println("checking the deletion of the search from the category, and how many searches still exist using the category id :"
							+ jp.get("id") + ".");
			Response res7 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/findsearch/category?id=" + jp.get("id"));
			JsonPath jp7 = res7.jsonPath();
			System.out.println("Status code is: " + res7.getStatusCode());
			Assert.assertTrue(res7.getStatusCode() == 200);
			System.out.println("Searches in the category (id :" + jp.get("id")
					+ ") are: " + jp7.get("name"));
			System.out.println("											");
			System.out
					.println("Category does not have any searches in it and the category can be deleted, - using DELETE method now");

			System.out.println("											");

			Response res8 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().delete("/category/" + jp.get("id"));
			System.out.println("											");
			System.out.println("Status code is: " + res8.getStatusCode());
			System.out.println(res8.asString());
			Assert.assertTrue(res8.getStatusCode() == 204);
			System.out.println("==Category whose id:" + jp.get("id")
					+ " has got deleted successfully");
			System.out.println("------------------------------------------");
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
					.everything().when().get("/findsearch/category?id");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(),
					"please give either category id or name");
			System.out.println("											");
			System.out
					.println("Case2:This test is to validate the response and status when id is missing");
			System.out.println("											");
			Response res1 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/findsearch/category?id=");

			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 400);
			System.out.println(res1.asString());
			Assert.assertEquals(res1.asString(),
					"please give either category id or name");
			System.out.println("											");
			System.out
					.println("Case3:This test is to validate the response and status when name is missing");
			System.out.println("											");
			Response res2 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/findsearch/category?name=");

			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 400);
			System.out.println(res2.asString());
			Assert.assertEquals(res1.asString(),
					"please give either category id or name");
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
					.everything().when().get("/findsearch/category?id=6789");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(),
					"Category object by ID: 6789 does not exist");
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
					.println("This test is to validate the response when the search by category with category name which is not exist");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/findsearch/category?name=MyAnalytics");
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
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/searches?categoryName=invalidCategory");

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
	 * searches by category with category name
	 */
	@Test
	public void searchesbyCategory_caseCheck1() {
		try {
			System.out
					.println("This test is to validate the response when the search by category");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/searches?categoryName=it analytics");

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
	public void searchesbyCategory_caseCheck2() {
		try {
			System.out
					.println("This test is to validate the response when the search by category(query case check)");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/searches?categoryname=it analytics");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(),
					"please give either categoryId,categoryName or folderId");
			System.out.println("											");
			System.out.println("------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * searches by category with category with name & id combinations
	 */
	@Test
	public void searchesbyCategory_nameANDidCombinations() {
		try {
			System.out
					.println("Case1:This test is to validate the response and status when the search by category with name & id combinations");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/searches/category?id&name=LA");

			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println(res.asString());

			System.out.println("											");
			System.out
					.println("Case2:This test is to validate the response & status when the search by category with valid category name & invalid id");
			Response res1 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/searches/category?id=4321&name=LA");

			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 404);
			System.out.println(res1.asString());
			Assert.assertEquals(res1.asString(),
					"Category object by ID: 4321 does not exist");
			System.out
					.println("Case3:This test is to validate the response & status when the search by category with valid category name & valid id");
			Response res2 = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/searches/category?id=1&name=LA");

			System.out.println("Status code is: " + res2.getStatusCode());
			Assert.assertTrue(res2.getStatusCode() == 200);
			System.out.println(res2.asString());
			// Assert.assertEquals(res2.asString(), "[]");
			System.out.println("											");
			System.out.println("------------------------------------------");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This test is to check the ability of fetch,update and deletion by its
	 * name instead of ID
	 */
	@Test
	public void category_CRUDOperationsByName() {
		try {
			System.out
					.println("This test is to perform the CRUD operations on a Category by its name with GET/PUT/DELETE method");
			System.out.println("											");
			System.out
					.println("POST operation is in-progress to create a category");
			System.out.println("											");
			String jsonString = "{ \"name\":\"SSCategory\", \"defaultFolderId\":3,\"parameters\":[{\"name\":\"ss_sample\",\"type\":STRING,\"value\":\"ss_value\"}] }";
			Response res = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/category");
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println(res.asString());
			Assert.assertTrue(res.getStatusCode() == 201);
			System.out.println("											");
			JsonPath jp = res.jsonPath();
			System.out.println("CategoryID is	:" + jp.get("id"));
			System.out.println("Name of Category:" + jp.get("name"));
			Assert.assertEquals(jp.get("name"), "SSCategory");
			Assert.assertEquals(jp.get("defaultFolderId"), 3);
			System.out
					.println("==POST method for caregory is over and asserted the successful category creation");
			System.out.println("											");
			System.out.println("Editing category by its name is in-progress");
			System.out.println("											");
			String jsonString1 = "{ \"name\":\"SSCategory_EDIT\", \"defaultFolderId\":3,\"parameters\":[{\"name\":\"ss_sample\",\"type\":STRING,\"value\":\"ss_value\"}] }";
			Response res1 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString1).when()
					.put("/category?name=" + jp.get("name"));
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");
			System.out.println(res1.asString());
			Assert.assertTrue(res1.getStatusCode() == 200);
			System.out.println("											");
			JsonPath jp1 = res1.jsonPath();
			System.out.println("Name of Category:" + jp1.get("name"));
			Assert.assertEquals(jp1.get("name"), "SSCategory_EDIT");
			System.out
					.println("==PUT method for caregory is over and asserted the successful edit");
			System.out.println("											");
			System.out.println("Delete category by its name is in-progress");
			System.out.println("											");
			Response res2 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.delete("/category?name=" + jp.get("name"));
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			System.out.println("											");
			System.out.println(res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 404);
			Assert.assertEquals(res2.asString(),
					"Category object by Name: " + jp.get("name") +" does not exist");
			System.out.println("											");
			System.out.println("Retrieve the category by its name using GET method");
			Response res3 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.get("/category?name=" + jp1.get("name"));
			JsonPath jp2 = res3.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res3.getStatusCode());
			System.out.println("											");
			//System.out.println(res3.asString());
			System.out.println("Name of Category:" + jp2.get("name"));
			Assert.assertTrue(res3.getStatusCode() == 200);
			Assert.assertEquals(jp2.get("name"), "SSCategory_EDIT");
			System.out.println("==GET operation is done");
			System.out.println("											");
			Response res4 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when()
					.delete("/category?name=" + jp2.get("name"));
			System.out.println("											");
			System.out.println("Status code is: " + res4.getStatusCode());
			//System.out.println("											");
			System.out.println(res4.asString());
			Assert.assertTrue(res4.getStatusCode() == 204);
			System.out.println("==Category deletion is successful by its name");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
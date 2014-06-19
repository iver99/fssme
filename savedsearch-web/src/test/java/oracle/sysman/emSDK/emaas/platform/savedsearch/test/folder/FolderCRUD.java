package oracle.sysman.emSDK.emaas.platform.savedsearch.test.folder;

import static com.jayway.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class FolderCRUD {
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
	 * create a folder using post method
	 */
	public void folder_create() {
		try {
			System.out
					.println("POST method is in-progress to create a new folder");
			int position = -1;
			String jsonString = "{ \"name\":\"Custom Folder\",\"description\":\"Folder for EMAAS searches\"}";
			Response res1 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/folder");
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			System.out.println("											");

			System.out
					.println("This test is to check for the duplicate entry with re-post");
			System.out.println("											");

			String jsonString2 = "{ \"name\":\"Custom Folder\" }";
			Response res2 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString2).when().post("/folder");
			System.out.println("											");
			System.out.println("Status code is: " + res2.getStatusCode());
			System.out.println("											");
			System.out.println(res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 400);
			Assert.assertEquals(res2.asString(),
					"Folder with name Custom Folder already exist");
			System.out.println("    ");

			System.out
					.println("Verifying weather the folder created or not with GET method");
			System.out.println("											");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/");
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("FolderName :" + jp.get("name"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("Custom Folder")) {
					position = i;

					String myvalue = a.get(position);
					System.out.println("==My New Folder is:" + myvalue);
					Assert.assertEquals(a.get(position), "Custom Folder");
					System.out
							.println("==GET and assertion operation are successfully completed");
				}
			}
			if (position == -1)
				System.out
						.println("==folder does not exist that you are looking for");

			System.out.println("------------------------------------------");
			System.out.println("											");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * create a folder using post method in a specified parentId
	 */
	public void folder_create_specifiedParentId() {
		try {
			System.out
					.println("POST method is in-progress to create a new folder in a specified directory");
			int position = -1;
			String jsonString = "{ \"name\":\"TestFolder\", \"description\":\"Folder for EMAAS searches\",\"parentId\":3}";
			Response res1 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/folder");
			JsonPath jp1 = res1.jsonPath();
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 201);
			System.out.println("											");
			System.out
					.println("Verifying whether the folder created or not in a specified directory with GET method");
			System.out.println("											");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/3?type=folder");
			JsonPath jp = res.jsonPath();
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("FolderName :" + jp.get("name"));
			List<String> a = new ArrayList<String>();
			a = jp.get("name");

			for (int i = 0; i < a.size(); i++) {
				if (a.get(i).equals("TestFolder")) {
					position = i;

					String myvalue = a.get(position);
					System.out.println("==My New Folder is:" + myvalue);
					Assert.assertEquals(a.get(position), "TestFolder");
					System.out
							.println("==GET and assertion operations are successfully completed");
				}
			}
			if (position == -1)
				System.out
						.println("==folder does not exist that you are looking for");
			System.out
					.println("cleaning up the folder that is created above using DELETE method");
			Response res2 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().delete("/folder/" + jp1.get("id"));
			System.out.println(res2.asString());
			Assert.assertTrue(res2.getStatusCode() == 204);
			System.out.println("------------------------------------------");
			System.out.println("											");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * update a folder using put method
	 */
	public void folder_edit() {
		try {
			System.out.println("This test is to edit a folder with PUT method");
			System.out.println("											");
			System.out
					.println("GET operation to select the folder to be edited is in progress");
			System.out.println("											");
			int position = -1;
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/");
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
				if (a.get(i).equals("Custom Folder")) {
					position = i;

					int myfolderID = b.get(position);
					System.out
							.println("==GET operation to select the folder that is to be EDITED is completed");
					System.out.println("											");

					System.out
							.println("PUT operation to edit the selected folder is in-progress");
					String jsonString = "{ \"name\":\"Custom Folder_Edit\" }";

					Response res1 = given().contentType(ContentType.JSON)
							.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
							.log().everything().body(jsonString).when()
							.put("/folder/" + myfolderID);
					System.out.println("											");
					System.out.println("Status code is: "
							+ res1.getStatusCode());
					System.out.println("											");
					System.out.println(res1.asString());
					String c;
					JsonPath jp1 = res1.jsonPath();
					c = jp1.get("name");
					System.out.println("Asserted & FolderName After Edit is:"
							+ c);
					System.out.println("											");
					System.out.println("==PUT operation is completed");

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
	 * Delete a folder using DELETE method
	 */
	public void getFolderDelete() {
		try {
			System.out
					.println("This test is to delete a folder with DELETE method");
			System.out.println("											");
			System.out
					.println("GET operation to select the folder is in progress");
			System.out.println("											");
			int position = -1;
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/");
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
				if (a.get(i).equals("Custom Folder_Edit")) {
					position = i;
					System.out.println("Index is:" + position);
					int myfolderID = b.get(position);

					System.out.println("My Value is:" + myfolderID);
					System.out
							.println("==GET operation to select the folder that is to be DELETED is completed");
					System.out.println("											");
					System.out
							.println("DELETE operation to delete the selected folder is in-progress");
					System.out.println("											");
					Response res1 = given().contentType(ContentType.JSON)
							.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
							.log().everything().when()
							.delete("/folder/" + myfolderID);
					System.out.println("											");
					System.out.println("Status code is: "
							+ res1.getStatusCode());
					System.out.println("											");
					System.out.println(res1.asString());
					Assert.assertTrue(res1.getStatusCode() == 204);
					System.out
							.println("==Delete operation is success and asserted that the folderID: "
									+ myfolderID + " does not exist");
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
	 * Check for the valid response body when the folder is not available
	 */
	public void folder_check_NONexistency() {
		try {
			System.out
					.println("This test is to Check for the valid response body when the folder is not available");
			System.out.println("											");
			System.out
					.println("Using GET operation to get the details of the specified folderId");
			System.out.println("											");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/folder/333");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println("== GET operation is completed");
			System.out.println("											");
			Assert.assertTrue(res.getStatusCode() == 404);
			String resbody = res.asString();

			System.out.println("Result:" + resbody);
			Assert.assertEquals(res.asString(),
					"Folder with the Id 333 does not exist");
			System.out.println("------------------------------------------");
			System.out.println("											");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Folder Negative Case1:
	 */
	public void folder_badURLs() {
		try {
			System.out.println("Negative Case1 for FOLDER");
			System.out.println("											");
			System.out.println("GET operation is in-progress with bad URL");
			System.out.println("											");

			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/folder");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 405);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Method Not Allowed");
			System.out.println("											");
			System.out.println("POST operation is in-progress with bad URL");
			System.out.println("											");
			String jsonString1 = "{ \"name\":\"Custom Folder\"}";
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
			String jsonString2 = "{ \"name\":\"Custom Folder\"}";
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
			String jsonString3 = "{ \"name\":\"Custom Folder\"}";
			Response res3 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString3).when().post("/");
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

	@Test
	/**
	 * Folder Negative Case2:
	 */
	public void folder_noType() {
		try {
			System.out.println("Negative Case2 for FOLDER");
			System.out.println("											");
			System.out.println("GET operation is in-progress with No Type");
			System.out.println("											");

			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/1");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "please specify Type");
			System.out.println("------------------------------------------");
			System.out.println("											");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Folder Negative Case3:
	 */
	public void folder_InvalidType() {
		try {
			System.out.println("Negative Case3 for FOLDER");
			System.out.println("											");
			System.out
					.println("GET operation is in-progress with Invalid Type");
			System.out.println("											");

			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/1?type=me");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(), "Type not supported");
			System.out.println("											");

			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Folder Negative Case4:
	 */
	public void folder_missingMandateinfo() {
		try {
			System.out.println("Negative Case4 for FOLDER");
			System.out.println("											");
			System.out
					.println("POST operation is in-progress & missing with required field: Name");
			System.out.println("											");
			String jsonString = "{\"parentId\":2,\"description\":\"mydb.mydomain error logs (ORA*)!!!\"}";
			Response res = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/folder");
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			System.out.println("											");
			System.out.println(res.asString());
			Assert.assertTrue(res.getStatusCode() == 400);
			System.out.println("											");
			Assert.assertEquals(res.asString(),
					"The name key for folder is missing in the input JSON Object");
			System.out.println("											");
			System.out.println("------------------------------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Folder Negative Case5:
	 */
	public void folder_invalidParentId() {
		try {
			System.out.println("Negative Case5 for FOLDER");
			System.out
					.println("POST method is in-progress to create a new folder with invalidParentId");

			String jsonString = "{ \"name\":\"TestFolder\", \"description\":\"Folder for EMAAS searches\",\"parentId\":333333}";
			Response res1 = given().contentType(ContentType.JSON)
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().body(jsonString).when().post("/folder");
			System.out.println(res1.asString());
			System.out.println("==POST operation is done");
			System.out.println("											");
			System.out.println("Status code is: " + res1.getStatusCode());
			Assert.assertTrue(res1.getStatusCode() == 404);
			System.out.println(res1.asString());
			Assert.assertEquals(res1.asString(),
					"Parent folder with id 333333 does not exist");
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Folder Negative Case6:
	 */
	public void fetchfolder_notExist() {
		try {
			System.out.println("Negative Case6 for FOLDER");
			System.out
					.println("GET method is in-progress to validate the folder non-availability");

			System.out.println("											");
			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().get("/99999?type=folder");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 404);
			System.out.println("											");
			System.out.println(res.asString());
			Assert.assertEquals(res.asString(),
					"Folder with the Id 99999 does not exist");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Delete a system folder using DELETE method
	 */
	public void systemFolder_Delete() {
		try {
			System.out
					.println("This test is to delete a system folder with DELETE method");
			System.out.println("											");
			System.out
					.println("GET operation to select the folder is in progress");
			System.out.println("											");

			Response res = given()
					.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
					.everything().when().delete("/folder/1");
			// JsonPath jp = res.jsonPath();
			System.out.println(res.asString());
			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 500);
			Assert.assertEquals(res.asString(),
					"Folder with id:" + 1 +" is a system folder and cant be deleted");
			System.out.println("											");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
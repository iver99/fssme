package oracle.sysman.emSDK.emaas.platform.savedsearch.test.importsavedsearch;
import java.util.Enumeration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
//import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class ImportTest
{
	static String HOSTNAME;
	static String portno;
	static String serveruri;
	static String authToken;
	static String TENANT_ID_OPC1 = TestConstant.TENANT_ID_OPC1;
	static String TENANT_ID1 = TestConstant.TENANT_ID1;

	private static final String FOLDER_XML = "Folder.xml";
	private static final String CATEGORY_XML = "Category.xml";
	private static final String SEARCH_XML = "Search.xml";

	@AfterClass
	public static void afterTest()
	{
		//TenantContext.clearContext();
	}

	@BeforeClass
	public static void setUp()
	{
		CommonTest ct = new CommonTest();
		HOSTNAME = ct.getHOSTNAME();
		portno = ct.getPortno();
		serveruri = ct.getServeruri();
		authToken = ct.getAuthToken();
	}

	private static String getStringFromInputStream(InputStream is)
	{

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {

			br = new BufferedReader(new InputStreamReader(is));

			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (br != null) {
				try {
					br.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}

	@Test
	/**
	 * Import Categories
	 */
	public void importCategories() throws Exception
	{


		InputStream stream = getResourceAsStream(CATEGORY_XML,ImportTest.class);
		
		String jsonString1 = ImportTest.getStringFromInputStream(stream);
		Response res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
				.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header("X-REMOTE-USER", TENANT_ID1).body(jsonString1)
				.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().post("/importcategories");
		Assert.assertEquals(res1.getStatusCode(), 200);
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for (int index = 0; index < arrfld.length(); index++) {
			System.out.println("verifying categoryids");
			JSONObject jsonObj = arrfld.getJSONObject(index);
			Assert.assertTrue(verifyCategory(jsonObj.getInt("id"), jsonObj.getString("name")) == true);
			Assert.assertTrue(jsonObj.getInt("id") > 1);
			System.out.println("verified categoryids");
		}
	}

	@Test
	/**
	 * import categories with invalid format
	 */
	public void importCategories_invalidformat()
	{
		try {
			Response res = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
					.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header("X-REMOTE-USER", TENANT_ID1).body("")
					.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().post("/importcategories");
			Assert.assertEquals(res.getStatusCode(), 400);
			Assert.assertEquals(res.asString(), "Please specify input with valid format");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	/**
	 * import folders with invalid format
	 */
	public void importFolder_invalidformat()
	{
		try {
			Response res = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
					.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header("X-REMOTE-USER", TENANT_ID1).body("")
					.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().post("/importfolders");
			Assert.assertEquals(res.getStatusCode(), 400);
			Assert.assertEquals(res.asString(), "Please specify input with valid format");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	/**
	 * Import folders
	 */
	public void importFolders() throws Exception
	{
		InputStream stream = getResourceAsStream(FOLDER_XML,ImportTest.class);		
		String jsonString1 = ImportTest.getStringFromInputStream(stream);
		Response res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
				.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header("X-REMOTE-USER", TENANT_ID1).body(jsonString1)
				.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().post("/importfolders");
		Assert.assertEquals(res1.getStatusCode(), 200);
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for (int index = 0; index < arrfld.length(); index++) {
			System.out.println("Verifying folders");
			JSONObject jsonObj = arrfld.getJSONObject(index);
			Assert.assertTrue(verifyFolder(jsonObj.getInt("id"), jsonObj.getString("name")) == true);
			System.out.println("Deleting folders");
			Assert.assertTrue(deleteFolder(jsonObj.getInt("id")) == true);
			System.out.println("Deleted folders");
		}
	}

	@Test
	/**
	 * Import Searches
	 */
	public void importSearches() throws Exception
	{
		InputStream stream = getResourceAsStream(SEARCH_XML,ImportTest.class);		
		String jsonString1 = ImportTest.getStringFromInputStream(stream);
		Response res1 = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
				.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header("X-REMOTE-USER", TENANT_ID1).body(jsonString1)
				.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().post("/importsearches");
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for (int index = 0; index < arrfld.length(); index++) {
			System.out.println("deleteing folders and  searches::");
			JSONObject jsonObj = arrfld.getJSONObject(index);
			Response res = RestAssured.given().log().everything().header("Authorization", authToken)
					.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header("X-REMOTE-USER", TENANT_ID1)
					.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().get("/search/" + jsonObj.getInt("id"));
			JsonPath jp = res.jsonPath();
			System.out.println("deleteing folders and  searches::::" + res.getBody().asString());
			System.out.println("deleteing folders and  searches::::::::" + jp.getMap("folder").get("id"));
			Assert.assertTrue(deleteSearch(jsonObj.getInt("id")) == true);
			if ((int) jp.getMap("folder").get("id") > 1) {
				Assert.assertTrue(deleteFolder((int) jp.getMap("folder").get("id")) == true);
			}
			System.out.println("deleted folders and  searches");
		}

	}

	@Test
	/**
	 * import searches with invalid format
	 */
	public void importSearches_invalidformat()
	{
		try {
			Response res = RestAssured.given().contentType(ContentType.XML).log().everything().header("Authorization", authToken)
					.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header("X-REMOTE-USER", TENANT_ID1).body("")
					.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().post("/importsearches");
			Assert.assertEquals(res.getStatusCode(), 400);
			Assert.assertEquals(res.asString(), "Please specify input with valid format");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	private boolean deleteFolder(int myfolderID)
	{
		Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
				.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header("X-REMOTE-USER", TENANT_ID1)
				.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().delete("/folder/" + myfolderID);
		System.out.println("											");
		return res1.getStatusCode() == 204;

	}

	private boolean deleteSearch(int mySearchId)
	{
		Response res1 = RestAssured.given().contentType(ContentType.JSON).log().everything().header("Authorization", authToken)
				.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header("X-REMOTE-USER", TENANT_ID1)
				.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().delete("/search/" + mySearchId);
		System.out.println("											");
		return res1.getStatusCode() == 204;

	}

	private Boolean verifyCategory(int mycatID, String mycatName)
	{
		Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
				.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header("X-REMOTE-USER", TENANT_ID1)
				.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().get("/category/" + mycatID);
		JsonPath jp = res1.jsonPath();
		if (res1.getStatusCode() != 200) {
			System.out.println(res1.getStatusCode());
			return false;
		}
		if (!jp.get("name").equals(mycatName)) {
			System.out.println(jp.get("name"));
			return false;
		}
		if (jp.get("href").equals(serveruri + "/category/" + mycatID)) {
			System.out.println(jp.get("href"));
			return false;
		}
		if (jp.get("createdOn") == null || "".equals(jp.get("createdOn"))) {
			System.out.println(jp.get("createdOn"));
			return false;
		}
		return true;

	}

	private boolean verifyFolder(int myfolderID, String myfolderName)
	{
		Response res1 = RestAssured.given().log().everything().header("Authorization", authToken)
				.header(TestConstant.SSF_HEADER, TestConstant.SSF_HEADER).header("X-REMOTE-USER", TENANT_ID1)
				.header(TestConstant.HEADER_TENANT_ID, TENANT_ID_OPC1).when().get("/folder/" + myfolderID);
		JsonPath jp = res1.jsonPath();
		if (res1.getStatusCode() != 200) {
			System.out.println(res1.getStatusCode());
			return false;
		}
		if (!jp.get("name").equals(myfolderName)) {
			System.out.println(jp.get("name"));
			return false;
		}
		if (jp.get("href").equals(serveruri + "/folder/" + myfolderID)) {
			System.out.println(jp.get("href"));
			return false;
		}
		if (!jp.get("createdOn").equals(jp.get("lastModifiedOn"))) {
			System.out.println(jp.get("createdOn"));
			System.out.println(jp.get("lastModifiedOn"));
			return false;
		}
		if (jp.get("systemFolder").equals(true)) {
			System.out.println(jp.get("systemFolder"));
			return false;
		}
		return true;
	}

	private boolean verifySearch(int mysearchID)
	{
		return true;
	}




 public static URL getResource(String resourceName, Class callingClass) {
      URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
      if (url == null && resourceName.startsWith("/")) {
          //certain classloaders need it without the leading /
          url = Thread.currentThread().getContextClassLoader()
              .getResource(resourceName.substring(1));
      }

      ClassLoader cluClassloader = ImportTest.class.getClassLoader();
      if (cluClassloader == null) {
          cluClassloader = ClassLoader.getSystemClassLoader();
      }
      if (url == null) {
          url = cluClassloader.getResource(resourceName);
      }
      if (url == null && resourceName.startsWith("/")) {
          //certain classloaders need it without the leading /
          url = cluClassloader.getResource(resourceName.substring(1));
      }

      if (url == null) {
          ClassLoader cl = callingClass.getClassLoader();

          if (cl != null) {
              url = cl.getResource(resourceName);
          }
      }

      if (url == null) {
          url = callingClass.getResource(resourceName);
      }
      
      if ((url == null) && (resourceName != null) && (resourceName.charAt(0) != '/')) {
          return getResource('/' + resourceName, callingClass);
      }

      return url;
  }
  
  
  public static InputStream getResourceAsStream(String resourceName, Class callingClass) {
      URL url = getResource(resourceName, callingClass);

      try {
          return (url != null) ? url.openStream() : null;
      } catch (IOException e) {
          return null;
      }
  }

  
  public static List<URL> getResources(String resourceName, Class callingClass) {
      List<URL> ret = new ArrayList<URL>();
      Enumeration<URL> urls = new Enumeration<URL>() {
          public boolean hasMoreElements() {
              return false;
          }
          public URL nextElement() {
              return null;
          }
          
      };
      try {
          urls = Thread.currentThread().getContextClassLoader()
              .getResources(resourceName);
      } catch (IOException e) {
          //ignore
      }
      if (!urls.hasMoreElements() && resourceName.startsWith("/")) {
          //certain classloaders need it without the leading /
          try {
              urls = Thread.currentThread().getContextClassLoader()
                  .getResources(resourceName.substring(1));
          } catch (IOException e) {
              // ignore
          }
      }

      ClassLoader cluClassloader = ImportTest.class.getClassLoader();
      if (cluClassloader == null) {
          cluClassloader = ClassLoader.getSystemClassLoader();
      }
      if (!urls.hasMoreElements()) {
          try {
              urls = cluClassloader.getResources(resourceName);
          } catch (IOException e) {
              // ignore
          }
      }
      if (!urls.hasMoreElements() && resourceName.startsWith("/")) {
          //certain classloaders need it without the leading /
          try {
              urls = cluClassloader.getResources(resourceName.substring(1));
          } catch (IOException e) {
              // ignore
          }
      }

      if (!urls.hasMoreElements()) {
          ClassLoader cl = callingClass.getClassLoader();

          if (cl != null) {
              try {
                  urls = cl.getResources(resourceName);
              } catch (IOException e) {
                  // ignore
              }
          }
      }

      if (!urls.hasMoreElements()) {
          URL url = callingClass.getResource(resourceName);
          if (url != null) {
              ret.add(url);
          }
      }
      while (urls.hasMoreElements()) {
          ret.add(urls.nextElement());
      }

      
      if (ret.isEmpty() && (resourceName != null) && (resourceName.charAt(0) != '/')) {
          return getResources('/' + resourceName, callingClass);
      }
      return ret;
  }


}

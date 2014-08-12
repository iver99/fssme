package oracle.sysman.emSDK.emaas.platform.savedsearch.test.importsavedsearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
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
	
	private static final String FOLDER_XML="oracle/sysman/emSDK/emaas/platform/savedsearch/test/importsavedsearch/Folder.xml";
	private static final String CATEGORY_XML="oracle/sysman/emSDK/emaas/platform/savedsearch/test/importsavedsearch/Category.xml";
	private static final String SEARCH_XML="oracle/sysman/emSDK/emaas/platform/savedsearch/test/importsavedsearch/Search.xml";
	
	
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
	 * Import folders 
	 */
	public void importFolders() throws Exception {
		InputStream stream=  ImportTest.class.getClassLoader().getResourceAsStream(FOLDER_XML);
	    String jsonString1 = getStringFromInputStream(stream);
		Response res1 = 
				RestAssured.given().
				contentType(ContentType.XML)
				.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
				.everything().body(jsonString1).when().post("/importfolders");
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for(int index=0;index<arrfld.length();index++)
		{
			System.out.println("Deleing folders");
			JSONObject jsonObj = arrfld.getJSONObject(index);			
			Assert.assertTrue(deleteFolder(jsonObj.getInt("id"))==true);
			System.out.println("Deleted folders");
		}
	}
	
	@Test
	/**
	 * Import Categories 
	 */
	public void importCategories() throws Exception {		
		InputStream stream=  ImportTest.class.getClassLoader().getResourceAsStream(CATEGORY_XML);	    
	    String jsonString1 = getStringFromInputStream(stream);
		Response res1 = RestAssured.
				given().contentType(ContentType.XML)
				.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
				.everything().body(jsonString1).when().post("/importcategories");
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for(int index=0;index<arrfld.length();index++)
		{
			System.out.println("verifying categoryids");
			JSONObject jsonObj = arrfld.getJSONObject(index);
			Assert.assertTrue(jsonObj.getInt("id")>1);
			System.out.println("verified categoryids");
		}
	}
	
	
	
	@Test
	/**
	 * Import Categories 
	 */
	public void importSearches() throws Exception {		
		InputStream stream=  ImportTest.class.getClassLoader().getResourceAsStream(SEARCH_XML);	    
	    String jsonString1 = getStringFromInputStream(stream);
		Response res1 =  RestAssured.
				given().contentType(ContentType.XML)
				.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log()
				.everything().body(jsonString1).when().post("/importsearches");		
		JSONArray arrfld = new JSONArray(res1.getBody().asString());
		for(int index=0;index<arrfld.length();index++)
		{
			System.out.println("deleteing folders and  searches::");
			JSONObject jsonObj = arrfld.getJSONObject(index);			
			Response res = RestAssured.given().headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME)
					.log().everything().when()
					.get("/search/" +jsonObj.getInt("id") );
			JsonPath jp = res.jsonPath();			
			System.out.println("deleteing folders and  searches::::" +  res.getBody().asString());
			System.out.println("deleteing folders and  searches::::::::" +jp.getMap("folder").get("id"));
			Assert.assertTrue(deleteSearch(jsonObj.getInt("id"))==true);
			if(((int)jp.getMap("folder").get("id"))>1)
			Assert.assertTrue(deleteFolder((int)jp.getMap("folder").get("id"))==true);			
			System.out.println("deleted folders and  searches");
		}
		
	}
	
	
	private static String getStringFromInputStream(InputStream is) {
		 
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder(); 
		String line;
		try {
 
			br = new BufferedReader(new InputStreamReader(is));

while ((line = br.readLine()) != null) {
				sb.append(line);
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
 
		return sb.toString();
 
	}
	
	
	
	private boolean deleteFolder(int myfolderID )
	{
		Response res1 = RestAssured.given().contentType(ContentType.JSON)
				.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().when()
				.delete("/folder/" + myfolderID);
		System.out.println("											");		
		return res1.getStatusCode() == 204;
		
	}
	
	private boolean deleteSearch(int mySearchId)
	{
		Response res1 = RestAssured.given().contentType(ContentType.JSON)
				.headers("X-USER-IDENTITY-DOMAIN-NAME", HOSTNAME).log().everything().when()
				.delete("/search/" + mySearchId);
		System.out.println("											");		
		return res1.getStatusCode() == 204;
		
	}
	
	

}





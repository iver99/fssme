package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.widget;

import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;

import org.codehaus.jettison.json.JSONArray;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

public class WidgetAPITest {
	

	
		/**
		 * Calling CommonTest.java to Set up RESTAssured defaults & Reading the inputs from the testenv.properties file before
		 * executing test cases
		 */

		static String HOSTNAME;
		static String portno;
		static String serveruri;
		static String authToken;

		@BeforeClass
		public static void setUp()
		{
			CommonTest ct = new CommonTest();
			HOSTNAME = ct.getHOSTNAME();
			portno = ct.getPortno();
			serveruri = ct.getServeruri();
			authToken = ct.getAuthToken();
		}

		/**
		 *widget api test
		 */
		

		

  @Test
  public void getAllWidgets() {
	  try {
			System.out.println("------------------------------------------");
			System.out.println("GET ALL Widgets");
			System.out.println("											");

			Response res = RestAssured.given().log().everything().header("Authorization", authToken).when().get("/widgets");

			System.out.println("											");
			System.out.println("Status code is: " + res.getStatusCode());
			Assert.assertTrue(res.getStatusCode() == 200);
			System.out.println(res.asString());
			System.out.println("											");
			System.out.println("------------------------------------------");
			System.out.println("											");
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
  }
}

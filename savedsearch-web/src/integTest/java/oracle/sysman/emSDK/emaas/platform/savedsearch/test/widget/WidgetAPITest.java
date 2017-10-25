package oracle.sysman.emSDK.emaas.platform.savedsearch.test.widget;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;

public class WidgetAPITest
{

	/**
	 * Calling CommonTest.java to Set up RESTAssured defaults & Reading the inputs from the testenv.properties file before
	 * executing test cases
	 */

	static String HOSTNAME;
	static String portno;
	static String serveruri;
	static String authToken;
	static String TENANT_ID_OPC1 = TestConstant.TENANT_ID_OPC0;
	static String TENANT_ID1 = TestConstant.TENANT_ID0;

	@BeforeClass
	public static void setUp()
	{
		CommonTest ct = new CommonTest();
		HOSTNAME = ct.getHOSTNAME();
		portno = ct.getPortno();
		serveruri = ct.getServeruri();
		authToken = ct.getAuthToken();
		TENANT_ID1 = ct.getTenant() + "." + ct.getRemoteUser();
		TENANT_ID_OPC1 = ct.getTenant();
	}

	/**
	 * widget api test
	 */

	@Test
	public void getAllWidgets()
	{
		try {

			Response res = RestAssured.given().log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID_OPC1).header("Authorization", authToken)
					.header(TestConstant.X_HEADER, TENANT_ID1).when().get("/widgets");

			Assert.assertTrue(res.getStatusCode() == 200);
		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}

	@Test
	public void testWidgetWithNullProviderName(){
		try	{
			String input ="{\"name\":\"Widget with null provider name\",\"category\":{\"id\":1},\"folder\":{\"id\":\"2\"},\"description\":\"\",\"queryStr\":\"* | stats count as logrecords by 'Log Source' | sort -logrecords\",\"isWidget\":true,\"parameters\":[{\"name\":\"time\",\"type\":\"STRING\",\"value\":{\"type\":\"relative\",\"duration\":60,\"timeUnit\":\"MINUTE\"}},{\"name\":\"WIDGET_KOC_NAME\",\"type\":\"STRING\",\"value\":\"emcla-visualization\"},{\"name\":\"WIDGET_VIEWMODEL\",\"type\":\"STRING\",\"value\":\"/js/viewmodel/search/widget/VisualizationWidget.js\"},{\"name\":\"WIDGET_TEMPLATE\",\"type\":\"STRING\",\"value\":\"/html/search/widgets/visualizationWidget.html\"},{\"name\":\"TARGET_FILTER\",\"type\":\"CLOB\",\"value\":[]},{\"name\":\"VISUALIZATION_TYPE_KEY\",\"type\":\"STRING\",\"value\":\"PIE\"},{\"name\":\"APPLICATION_CONTEXT\",\"type\":\"CLOB\",\"value\":\"{\\\"data\\\":{\\\"version\\\":\\\"1.23\\\",\\\"timeperiod\\\":\\\"LAST_60_MINUTE\\\",\\\"timeduration\\\":60,\\\"timeunit\\\":\\\"MINUTE\\\"}}\"},{\"name\":\"SHOW_LOG_SCALE\",\"type\":\"STRING\"}]}";

			Response createTestWidgetResponse = RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID_OPC1).header("Authorization", authToken)
					.header(TestConstant.X_HEADER, TENANT_ID1)
					.body(input).when()
					.post("/search");
			Assert.assertTrue(createTestWidgetResponse.getStatusCode() == 201);

			Response getWidgetListRespose = RestAssured.given().log().everything().header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID_OPC1).header("Authorization", authToken)
					.header(TestConstant.X_HEADER, TENANT_ID1).when().get("/widgets");
			Assert.assertTrue(getWidgetListRespose.getStatusCode() == 200);

			JsonPath jsonPathForCreate  = createTestWidgetResponse.jsonPath();
			String searchId = jsonPathForCreate.getString("id");

			Response deleteResponseForCreate =  RestAssured.given().contentType(ContentType.JSON).log().everything()
					.header("Authorization", authToken).header("X-USER-IDENTITY-DOMAIN-NAME", TENANT_ID_OPC1).header(TestConstant.X_HEADER, TENANT_ID1).when()
					.delete("/search/" + searchId);
			Assert.assertTrue(deleteResponseForCreate.getStatusCode()== 204);
		}catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
	}
}

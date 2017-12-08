package oracle.sysman.emSDK.emaas.platform.savedsearch.test.upgrade;

import java.text.SimpleDateFormat;
import java.util.Date;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;

import org.codehaus.jettison.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
/**
 * Created by shangwan on 2017/12/7.
 */
public class TestZDTSyncComparisonBeforeUpgradeTest {
    /**
     * Calling CommonTest.java to Set up RESTAssured defaults & Reading the inputs from the testenv.properties file before
     * executing test cases .
     */
    public static String currentDate = null;

    static String HOSTNAME;
    static String portno;
    static String serveruri;
    static String authToken;
    static String TENANT_ID_OPC1;
    static String TENANT_ID1;

    @BeforeClass
    public static void setUp() throws JSONException {
        CommonTest ct = new CommonTest();
        HOSTNAME = ct.getHOSTNAME();
        portno = ct.getPortno();
        serveruri = ct.getServeruri();
        authToken = ct.getAuthToken();
        TENANT_ID1 = ct.getTenant() + "." + ct.getRemoteUser();
        TENANT_ID_OPC1 = ct.getTenant();
    }

    @Test
    public void testSyncComparsionData() {
        try
        {
            //call /zdt/compare/result API to insert data into ems_zdt_comparator table
            String jsonString = "{\"lastComparisonDateTime\":\"2017-05-12 15:20:21\", \"comparisonType\":\"full\",\"comparisonResult\":\"{}\",\"divergencePercentage\":0.11,\"nextScheduledComparisonDateTime\":\"2017-05-12 15:20:21\"}";
            Response res1 = RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .log()
                    .everything()
                    .header("Authorization", authToken)
		    .header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString).when().put("/zdt/compare/result");
            Assert.assertTrue(res1.getStatusCode() == 200);

            //call sync API to insert data into ems_zdt_sync table
            Response res = RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .log()
                    .everything()
                    .header("Authorization", authToken)
		    .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/zdt/sync");
            //verify sync API successfully
            Assert.assertTrue(res.getStatusCode() == 200);
            Assert.assertTrue("Sync is successful!".equals(res.jsonPath().getString("msg").trim()));

           //verify the compare status
            Response res2 = RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .log()
                    .everything()
                    .header("Authorization", authToken)
		    .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/zdt/compare/status");
            Assert.assertTrue(res2.getStatusCode() == 200);
            Assert.assertTrue(res2.jsonPath().getString("lastComparisonDateTime").contains("2017-05-12 15:20:21"));

            Date now = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            currentDate = dateFormat.format(now);

            String jsonString1 = "{\"lastComparisonDateTime\":\""+ currentDate +"\", \"comparisonType\":\"full\",\"comparisonResult\":\"{}\",\"divergencePercentage\":0.11,\"nextScheduledComparisonDateTime\":\""+ currentDate +"\"}";
            Response res3 = RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .log()
                    .everything()
                    .header("Authorization", authToken)
		    .header(TestConstant.OAM_HEADER, TENANT_ID1).body(jsonString1).when().put("/zdt/compare/result");
            Assert.assertTrue(res3.getStatusCode() == 200);

            Response res4 = RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .log()
                    .everything()
                    .header("Authorization", authToken)
		    .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/zdt/compare/status");
            Assert.assertTrue(res4.getStatusCode() == 200);
            Assert.assertTrue(res4.jsonPath().getString("lastComparisonDateTime").contains(currentDate));
        }
        catch(Exception e)
        {
            Assert.fail("Test failed: " + e.getLocalizedMessage());
        }
    }
}

package oracle.sysman.emSDK.emaas.platform.savedsearch.test.upgrade;

import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import org.codehaus.jettison.json.JSONException;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
/**
 * Created by shangwan on 2017/12/7.
 */
public class TestZDTSyncComparisonAfterUpgradeTest {
    /**
     * Calling CommonTest.java to Set up RESTAssured defaults & Reading the inputs from the testenv.properties file before
     * executing test cases .
     */

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
    public void testSyncComparsionDataAfterUpgrade() {
        try
        {
            Response res = RestAssured
                    .given()
                    .contentType(ContentType.JSON)
                    .log()
                    .everything()
                    .header("Authorization", authToken)
		    .header(TestConstant.OAM_HEADER, TENANT_ID1).when().get("/zdt/compare/status");
            Assert.assertEquals(res.getStatusCode(), 200);;
            Assert.assertEquals(res.jsonPath().getString("lastComparisonDateTime"), "2017-05-12 15:20:21.000");
        }
        catch (Exception e)
        {
            Assert.fail("Test failed: " + e.getLocalizedMessage());
        }
    }
}

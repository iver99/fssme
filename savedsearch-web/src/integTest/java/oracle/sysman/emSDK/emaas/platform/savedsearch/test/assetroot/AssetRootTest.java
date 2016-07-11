package oracle.sysman.emSDK.emaas.platform.savedsearch.test.assetroot;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

/**
 * Created by xiadai on 2016/7/11.
 */
public class AssetRootTest {
    static String HOSTNAME;
    static String authToken;
    static String TENANT_ID_OPC1 = TestConstant.TENANT_ID_OPC0;
    static String TENANT_ID1 = TestConstant.TENANT_ID0;
    private ArrayList<Long> searchIdToBeDelete = new ArrayList<Long>();
    private String targetType = "host_loganalytics_test";

    @BeforeClass
    public static void setUp()
    {
        CommonTest ct = new CommonTest();
        HOSTNAME = ct.getHOSTNAME();
        authToken = ct.getAuthToken();
        TENANT_ID1 = ct.getTenant() + "." + ct.getRemoteUser();
        TENANT_ID_OPC1 = ct.getTenant();
    }

    @Test
    public void getAssetRootTest(){
      Assert.assertEquals(200,
                RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                .get("assetroot/2000").getStatusCode());
    }
}

package oracle.sysman.emSDK.emaas.platform.savedsearch.test.targetcard;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.CommonTest;
import oracle.sysman.emSDK.emaas.platform.savedsearch.test.common.TestConstant;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.annotation.Target;
import java.util.ArrayList;

/**
 * Created by xidai on 6/29/2016.
 */
public class TargetCardCURD {
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
    public void targetCardCreateTest(){
        String inputCreateJson = "{" +
                "  \"name\": \"link_host_loganalytics_test\"," +
                "  \"category\": {" +
                "    \"id\": 5" +
                "  }," +
                "  \"folder\": {" +
                "    \"id\": 6" +
                "  }," +
                "  \"parameters\": [" +
                "    {" +
                "      \"name\": \"TargetMetaData\"," +
                "      \"value\": {" +
                "        \"Version\": \"1\"," +
                "        \"UtilityVersion\": \"1\"," +
                "        \"lastUpdateTimeStamp\": \"1455278054050\"" +
                "      }," +
                "      \"type\": \"STRING\"" +
                "    }," +
                "    {" +
                "      \"name\": \"Links\"," +
                "      \"value\": {" +
                "        \"static\": [" +
                "          {" +
                "            \"text\": \"keyText\"," +
                "            \"url\": \"emsaasui/emlacore/html/log-analytics-search.html\"," +
                "            \"translationPath\": \"/emsaasui/resources/Translations\"" +
                "          }" +
                "        ]," +
                "        \"dynamicUrlEP\": \"\"" +
                "      }," +
                "      \"type\": \"CLOB\"" +
                "    }" +
                "  ]" +
                "}";
        Response createResponse = getResponseForCreateNewSearch(inputCreateJson);
        JsonPath jsonPathForCreate  = createResponse.jsonPath();
        System.out.println(createResponse.getStatusLine());
        Assert.assertEquals(201,createResponse.getStatusCode());
        searchIdToBeDelete.add(jsonPathForCreate.getLong("id"));


        Response getTargetCardByName = getResponseForTargetCardByName(targetType);
        JsonPath jsonPathForGetTargetCardByName = getTargetCardByName.jsonPath();
        System.out.println(getTargetCardByName.getStatusLine());
        Assert.assertEquals(200,getTargetCardByName.getStatusCode());

        Response deleteResponse = getResponseForDelete(jsonPathForCreate.getLong("id"));
        System.out.println(deleteResponse.getStatusLine());
        Assert.assertEquals(204,deleteResponse.getStatusCode());
    }


    private Response getResponseForTargetCardByName(String targetType) {
        return RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                .get("targetcardlinks?targetType="+targetType);
    }

    private Response getResponseForCreateNewSearch(String jsonForCreateSavedSearch) {
        return RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1)
                .body(jsonForCreateSavedSearch).when()
                .post("targetcardlinks");
    }

    private Response getResponseForDelete(Long searchId){
      return  RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                .delete("/targetcardlinks/" + searchId);
    }
}

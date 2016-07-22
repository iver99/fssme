package oracle.sysman.emSDK.emaas.platform.savedsearch.test.search;

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

import java.util.ArrayList;

/**
 * Created by xidai on 5/11/2016.
 */
public class OdsCURD {
    static String HOSTNAME;
    static String authToken;
    static String TENANT_ID_OPC1 = TestConstant.TENANT_ID_OPC0;
    static String TENANT_ID1 = TestConstant.TENANT_ID0;
    static ArrayList<String> odsIdToBeDelete;

    @BeforeClass
    public static void setUp()
    {
        CommonTest ct = new CommonTest();
        HOSTNAME = ct.getHOSTNAME();
        authToken = ct.getAuthToken();
        TENANT_ID1 = ct.getTenant() + "." + ct.getRemoteUser();
        TENANT_ID_OPC1 = ct.getTenant();
        odsIdToBeDelete = new ArrayList<String>();
    }

    //Delete the ods entity has been created during the test.
    @AfterClass
    public static void destroy(){
        for(String searchId : odsIdToBeDelete){
            Response deleteResponseForCreate =  RestAssured.given().contentType(ContentType.JSON).log().everything()
                    .header("Authorization", authToken).header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                    .delete("/search/" + searchId);
            System.out.println(deleteResponseForCreate.getStatusLine());
            Assert.assertTrue(deleteResponseForCreate.getStatusCode()== 204);
        }
    }


    //Test create an ods entity for a new search.
    @Test
    public void odsSearchCreateTest(){
            //input json
            String inputCreateJson ="{" +
                    "    \"name\": \"DD Test\",  " +
                    "    \"category\": {\"id\":\"1\"}, " +
                    "    \"folder\": {\"id\":\"3\"}, " +
                    "    \"description\": \"Search for Demo\",  " +
                    "    \"parameters\":[" +
                    "        {" +
                    "             \"name\":\"ODS_ENTITY\"," +
                    "             \"type\":\"STRING\"," +
                    "             \"value\":\"TRUE\"" +
                    "        }" +
                    "     ]" +
                    "}";
        Response createResponse = getResponseForCreateNewSearch(inputCreateJson);
        JsonPath jsonPathForCreate  = createResponse.jsonPath();
        System.out.println(createResponse.getStatusLine());
        Assert.assertTrue(createResponse.getStatusCode()== 201);
        odsIdToBeDelete.add(jsonPathForCreate.getString("id"));
    }

    //For the non system search
    //Test the funciton of switch a non-ods saved search to an ods one.
    @Test
    public void createOdsEntityForExsitingNonSystemSearch(){
        //create a search for test
        String jsonForCreateSavedSearch = "{\"name\":\"Test_Search\",\"category\":{\"id\":\""
                + 1
                + "\"},\"folder\":{\"id\":\""
                + 1
                + "\"},\"description\":\" \",\"queryStr\": \"target.name=mydb.mydomain message like ERR*\",\"parameters\":[{\"name\":\"sample\",\"type\":\"STRING\",\"value\":\"my_value\",\"attributes\":\"test\"}]}";
        Response testCreateSearchResponse = getResponseForCreateNewSearch(jsonForCreateSavedSearch);
        JsonPath testCreateSearchResponseJsonPath = testCreateSearchResponse.jsonPath();

        //create an ods entity for existing non-system search
        Response createForExistingSearchResponse = getResponseForCreateOdsForExistingSearch(testCreateSearchResponseJsonPath.getString("id"));
        JsonPath jsonPathForCreateForExistingSearch = createForExistingSearchResponse.jsonPath();
        System.out.println(createForExistingSearchResponse.getStatusLine());
        Assert.assertTrue(createForExistingSearchResponse.getStatusCode()== 200);

        odsIdToBeDelete.add(jsonPathForCreateForExistingSearch.getString("id"));
    }

    //For the system search
    //Test the function of switch a non-ods saved search to an ods one.
    @Test
    public void createEntityForExsitingSystemSearch(){
        //create an ods entity for existing system search
        Response createForExistingSearchResponse = getResponseForCreateOdsForExistingSearch("2023");
        System.out.println(createForExistingSearchResponse.getBody().asString());
        Assert.assertTrue(createForExistingSearchResponse.getStatusCode()== 200
                ||createForExistingSearchResponse.getBody().asString()
                    .startsWith("Exist Entity ID"));
    }

    private Response getResponseForCreateOdsForExistingSearch(String searchId) {
        return RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1).when()
                .put("/search/"+searchId+"/odsentity");
    }

    private Response getResponseForCreateNewSearch(String jsonForCreateSavedSearch) {
        return RestAssured.given().contentType(ContentType.JSON).log().everything()
                .header("Authorization", authToken)
                .header(TestConstant.OAM_HEADER, TENANT_ID1)
                .body(jsonForCreateSavedSearch).when()
                .post("/search");
    }
}
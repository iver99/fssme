package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.targetcard;




import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JsonUtil;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;

import static org.testng.Assert.*;

/**
 * Created by xidai on 6/29/2016.
 */
@Test(groups={"s2"})
public class TargetCardLinksFilterAPITest {
    private  TargetCardLinksFilterAPI targetCardLinksFilterAPI;
    @Mocked
    SearchManager searchManager;
    @Mocked
    UriInfo uriInfo;
    @Mocked
    URI uri;
    @Mocked
    EntityJsonUtil entityJsonUtil;
    @Mocked
    Search search;

    @BeforeMethod
    public void setUp (){
       targetCardLinksFilterAPI= new TargetCardLinksFilterAPI();
        targetCardLinksFilterAPI.uri = uriInfo;
    }
    @Test
    public void testGetSearchByName() throws Exception {
        final String targetcardName="name";
        final ArrayList<Search> searches = new ArrayList<>();
        searches.add(search);
        final JSONObject jsonTargetcardObject = new JSONObject();
        new Expectations(){
            {
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getTargetCard(withAny(targetcardName));
                result = searches;
                uriInfo.getBaseUri();
                result = uri;
                EntityJsonUtil.getTargetCardJsonObj(uriInfo.getBaseUri(),withAny(search));
                result = jsonTargetcardObject;
            }
        };
        Assert.assertEquals(200,targetCardLinksFilterAPI.getSearchByName(targetcardName).getStatus());
    }

    @Test
    public void testDeleteTargetCard() throws Exception {
        new Expectations(){
            {
                SearchManager.getInstance();
                result = searchManager;
                searchManager.deleteTargetCard(anyLong,false);
            }
        };
        Assert.assertEquals(204,targetCardLinksFilterAPI.deleteTargetCard(1000).getStatus());
    }

    @Test
    public void testRegisterTargertCard() throws Exception {
        final JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","6");
        category.put("id","6");
        inputJson.put("name","Link_demo");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);

        new Expectations(){
            {
                SearchManager.getInstance();
                result = searchManager;
                searchManager.saveTargetCard(withAny(search));
                result = search;
                EntityJsonUtil.getTargetCardJsonObj(withAny(uriInfo.getBaseUri()),search);
                result = inputJson;
            }
        };
        Assert.assertEquals(201,targetCardLinksFilterAPI.registerTargertCard(inputJson).getStatus());
    }
}
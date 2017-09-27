package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search;

import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.json.VersionedLink;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
import oracle.sysman.emaas.platform.savedsearch.targetmodel.services.OdsDataService;
import oracle.sysman.emaas.platform.savedsearch.targetmodel.services.OdsDataServiceImpl;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/24/2016.
 */
@Test(groups={"s2"})
public class SearchAPITest {
	private static final BigInteger TEST_ID_10 = BigInteger.TEN;
	private static final BigInteger TEST_ID_100 = new BigInteger("100");
	private static final BigInteger TEST_ID_999 = new BigInteger("999");
	private SearchAPI api = new SearchAPI();
    @BeforeMethod
    public void setUp() throws JSONException {
        api.uri = uriInfo;
    }
    @Mocked
    UriInfo uriInfo;
    @Mocked
    URI uri;
    @Mocked
    Search search;
    @Mocked
    SearchManager searchManager;
    @Mocked
    OdsDataService odsDataService;
    @Mocked
    OdsDataServiceImpl odsDataServiceImpl;
    @Mocked
    DependencyStatus dependencyStatus;
    @Test
    public void testCreateSearch() throws JSONException {
        JSONObject inputJson = new JSONObject();
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
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
		inputJson.put("federationSupported", "FEDERATION_ONLY");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch2nd() throws JSONException {
        JSONObject inputJson = new JSONObject();
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
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
		inputJson.put("federationSupported", "NON_FEDERATION_ONLY");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch3th() throws JSONException {
        JSONObject inputJson = new JSONObject();
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
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
		inputJson.put("federationSupported", "FEDERATION_AND_NON_FEDERATION");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch5th() throws JSONException {
        JSONObject inputJson = new JSONObject();
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
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name"," ");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
       new Expectations(){
           {
               dependencyStatus.isDatabaseUp();
               result = true;
               SearchManager.getInstance();
               result = searchManager;
           }
       };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch6th() throws JSONException {
        JSONObject inputJson = new JSONObject();
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
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","<> ");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch4th() throws JSONException {
        JSONObject inputJson = new JSONObject();
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
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name"," ");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch7th() throws JSONException {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("attributes",new JSONObject());
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","name ");
        inputJson.put("description","description");
        inputJson.put("category",category);
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch8th() throws JSONException {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("attributes",new JSONObject());

        p1.put("type","STRING");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","name ");
        inputJson.put("description","description");
        inputJson.put("category",category);
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch9th() throws JSONException {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("attributes",new JSONObject());
        p1.put("name","time");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","name ");
        inputJson.put("description","description");
        inputJson.put("category",category);
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch10th() throws JSONException {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("attributes",new JSONObject());
        p1.put("name","time");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");

        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","name ");
        inputJson.put("description","description");
        inputJson.put("category",category);
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch11th() throws JSONException {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("attributes",new JSONObject());
        p1.put("name","time");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");

        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","name ");
        inputJson.put("description","description");

        inputJson.put("folder",folder);
        inputJson.put("type","SOMthing");
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        api.createSearch(inputJson);
    }
    @Test
    public void testCreateSearch12th() throws JSONException {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("attributes",new JSONObject());
        p1.put("name","time");
        p1.put("value","ALL");
        p2.put("name","additionalInfo");

        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","name ");
        inputJson.put("description","description");
        inputJson.put("category",category);
        inputJson.put("folder","folder");
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        api.createSearch(inputJson);
    }

    /**
     * test create ods entity
     *
     * @throws JSONException
     */
    @Test
    public void testCreateSearch13th() throws JSONException, EMAnalyticsFwkException {
        JSONObject category = new JSONObject();
        category.put("id","999");
        JSONObject folder = new JSONObject();
        folder.put("id","999");
        JSONObject p = new JSONObject();
        p.put("name","ODS_ENTITY");
        p.put("type","STRING");
        p.put("value","TRUE");
        JSONArray parameter = new JSONArray();
        parameter.put(p);
        
        JSONObject inputJson = new JSONObject();
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.saveSearch(withAny(search));
                result = search;
                searchManager.editSearch(withAny(search));
                result = search;
		search.getId();
                result = "999";
                search.getName();
                result = "searchName";
                odsDataServiceImpl.createOdsEntity(anyString,anyString);
                result = "odsentitymeid";
                search.getParameters();
                result = new ArrayList<>();
            }
        };
        api.createSearch(inputJson);
    }

    @Mocked
    Throwable throwable;
    @Test
    public void testCreateSearchEMAnalyticsFwkException() throws JSONException, EMAnalyticsFwkException {
        JSONObject category = new JSONObject();
        category.put("id","999");
        JSONObject folder = new JSONObject();
        folder.put("id","999");
        JSONObject p = new JSONObject();
        p.put("name","ODS_ENTITY");
        p.put("type","STRING");
        p.put("value","TRUE");
        JSONArray parameter = new JSONArray();
        parameter.put(p);

        JSONObject inputJson = new JSONObject();
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.saveSearch(withAny(search));
                result = new EMAnalyticsFwkException(throwable);
            }
        };
        api.createSearch(inputJson);
    }

    @Test
    public void testDeleteSearch() throws JSONException, EMAnalyticsFwkException {
        new Expectations() {
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.deleteSearch((BigInteger)any, anyBoolean);
                OdsDataServiceImpl.getInstance();
                result = odsDataServiceImpl;
                odsDataServiceImpl.deleteOdsEntity((BigInteger)any);
            }
        };
        Assert.assertNotNull(api.deleteSearch(TEST_ID_100));

    }
    @Test
    public void testDeleteSearch2nd() throws JSONException, EMAnalyticsFwkException {
        new Expectations() {
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.deleteSearch((BigInteger)any, anyBoolean);
                result = new EMAnalyticsFwkException(throwable);
                OdsDataServiceImpl.getInstance();
                result = odsDataServiceImpl;
                odsDataServiceImpl.deleteOdsEntity((BigInteger)any);
            }
        };
        Assert.assertNotNull(api.deleteSearch(TEST_ID_100));

    }
    @Test
    public void testEditSearch() throws JSONException, EMAnalyticsFwkException {
        JSONObject inputJson = new JSONObject();
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
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);

        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearch((BigInteger)any);
                result =search;
                searchManager.editSearch(withAny(search));
                result = search;
            }
        };
        Assert.assertNotNull(api.editSearch(inputJson,"",TEST_ID_10));
    }
    @Test
    public void testEditSearch2nd() throws JSONException, EMAnalyticsFwkException {
        JSONObject inputJson = new JSONObject();
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
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);

        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearch((BigInteger)any);
                result =search;
                searchManager.editSearch(withAny(search));
                result = search;
            }
        };
        Assert.assertNotNull(api.editSearch(inputJson,"ORACLE_INTERNAL",TEST_ID_10));
    }

    @Test
    public void testEditSearch3th() throws JSONException, EMAnalyticsFwkException {
        JSONObject inputJson = new JSONObject();
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
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);

        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearch((BigInteger)any);
                result =search;
                searchManager.editSearch(withAny(search));
                result = search;
            }
        };
        Assert.assertNotNull(api.editSearch(inputJson,"ORACLE_INTERNAL",TEST_ID_10));
    }
    @Test
    public void testEditSearch4th() throws JSONException, EMAnalyticsFwkException {
        JSONObject inputJson = new JSONObject();
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
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearch((BigInteger)any);
                result =new EMAnalyticsWSException(throwable);
            }
        };
        Assert.assertNotNull(api.editSearch(inputJson,"ORACLE_INTERNAL",TEST_ID_10));
    }
    @Test
    public void testEditSearch5th() throws JSONException, EMAnalyticsFwkException {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name", "time");
        p1.put("type", " ");
        p1.put("value", "ALL");
        p2.put("name", " ");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);

        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearch((BigInteger)any);
                result =search;
            }
        };
        Assert.assertNotNull(api.editSearch(inputJson,"ORACLE_INTERNAL",TEST_ID_10));
    }


    @Test
    public void testEditSearchAccessDate() throws JSONException {
        Assert.assertNotNull(api.editSearchAccessDate(TEST_ID_10, true));
    }
    @Test
    public void testEditSearchAccessDate2nd() throws JSONException {
        new Expectations() {
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "categoryId=11";
            }
        };
        Assert.assertNotNull(api.editSearchAccessDate(TEST_ID_10,true));
    }
    @Test
    public void testEditSearchAccessDate3th() throws JSONException {
        new Expectations() {
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = null;
            }
        };
        Assert.assertNotNull(api.editSearchAccessDate(TEST_ID_10,true));
    }
    @Test
    public void testEditSearchAccessDate4th() throws JSONException {
        new Expectations() {
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "searchId = 1";
            }
        };
        Assert.assertNotNull(api.editSearchAccessDate(TEST_ID_10,false));
    }

    @Test
    public void testEditSearchAccessDate5th() throws JSONException {
        new Expectations() {
            {
                uriInfo.getRequestUri();
                result = uri;
                uri.getQuery();
                result = "searchId";
            }
        };
        Assert.assertNotNull(api.editSearchAccessDate(TEST_ID_10,true));
    }
    @Mocked
    FolderManager folderManager;
    @Mocked
    Folder folderMocked;
    @Mocked
    CategoryManager categoryManager;
    @Mocked
    Category categoryMocked;

    @Test
    public void testGetSearch() throws JSONException, EMAnalyticsFwkException {
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        Assert.assertNotNull(api.getSearch(TEST_ID_10,true));
    }
    
    @Test
    public void testGetExportedSearch() throws JSONException {
    	JSONArray array = new JSONArray();
    	array.put("12345");
    	new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        api.getSearchData(array);
    }
    
    @Test
    public void testGetSearch2nd() throws JSONException, EMAnalyticsFwkException {
        final List<Search> searches = new ArrayList<Search>();
        for (int i = 0; i <= 2; i++) {
            searches.add(new SearchImpl());
        }
        new Expectations() {
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        Assert.assertNotNull(api.getSearch(TEST_ID_10,true));
    }
    
    @Test
    public void testGetExportedSearchException() throws JSONException, EMAnalyticsFwkException {
    	JSONArray array = new JSONArray();
    	array.put("12345");
    	final List<Search> searches = new ArrayList<Search>();
        for (int i = 0; i <= 2; i++) {
            searches.add(new SearchImpl());
        }
    	new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchWithoutOwner((BigInteger)any);
                result = new EMAnalyticsFwkException(throwable);
            }
        };
        api.getSearchData(array);
    }
    
    @Test
    public void testImportSearchUpdate(@Mocked final PersistenceManager persistenceManager, 
    		@Mocked final TenantInfo tenantInfo,
    		@Mocked final EntityManager em,
    		@Mocked final Query query) throws JSONException, EMAnalyticsFwkException {
    	JSONArray array = new JSONArray();
        JSONObject inputJson = new JSONObject();
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
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        inputJson.put("owner", "me");
        inputJson.put("id", "123456");
        inputJson.put("editable", true);
        array.put(inputJson);
        final List<Map<String, Object>> idAndNameList = new ArrayList<Map<String, Object>>();
        Map<String, Object> idMap = new HashMap<String, Object>();
        idMap.put("SEARCH_ID", "123456");
        idMap.put("NAME", "Demo Search");
        idAndNameList.add(idMap);
        final Search search = new SearchImpl();
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchIdAndNameByUniqueKey("Demo Search", new BigInteger("999"), new BigInteger("1119"), new BigInteger("0"),"me");
                result = idAndNameList;
                searchManager.editSearch((Search)any);
                result = search;
            }
        };
        api.importData(true,array);
    }

    
    @Test
    public void testImportSearchInsert1() throws JSONException, EMAnalyticsFwkException {
    	JSONArray array = new JSONArray();
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p1.put("name","ODS_ENTITY");
        p1.put("type","STRING");
        p1.put("value","TRUE");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        inputJson.put("owner", "me");
        inputJson.put("id", "123456");
        inputJson.put("editable", true);
        array.put(inputJson);
        final List<Map<String, Object>> idAndNameList = new ArrayList<Map<String, Object>>();
        Map<String, Object> idMap = new HashMap<String, Object>();
        idMap.put("SEARCH_ID", "123456");
        idMap.put("NAME", "Demo Search");
        idAndNameList.add(idMap);
        
        final Search search = new SearchImpl();
        search.setId(new BigInteger("56789"));
        search.setName("updatedName");
        search.setParameters(new ArrayList<SearchParameter>());
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchIdAndNameByUniqueKey("Demo Search", new BigInteger("999"), new BigInteger("1119"), new BigInteger("0"),"me");
                result = idAndNameList;
                searchManager.saveSearch((Search)any);
                result = search;
                OdsDataServiceImpl.getInstance();
                result = odsDataServiceImpl;
                odsDataServiceImpl.createOdsEntity(anyString,anyString);
                result = "odsentitymeid";
                
                searchManager.editSearch(search);
                result = search;
            }
        };
        api.importData(false,array);
    }
    
    @Test
    public void testImportSearchInsert2() throws JSONException, EMAnalyticsFwkException {
    	JSONArray array = new JSONArray();
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name","time");
        p1.put("type","STRING");
        p1.put("value","ALL");
        p1.put("name","ODS_ENTITY");
        p1.put("type","STRING");
        p1.put("value","TRUE");
        p2.put("name","additionalInfo");
        p2.put("type","CLOB");
        p2.put("value","this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id","999");
        category.put("id","1119");
        inputJson.put("name","Demo Search");
        inputJson.put("category",category);
        inputJson.put("description","Search for demo");
        inputJson.put("folder",folder);
        inputJson.put("parameters",parameter);
        inputJson.put("owner", "me");
        inputJson.put("id", "123456");
        inputJson.put("editable", true);
        array.put(inputJson);
        final List<Map<String, Object>> idAndNameList = new ArrayList<Map<String, Object>>();
        
        
        final Search search = new SearchImpl();
        search.setId(new BigInteger("56789"));
        search.setName("updatedName");
        search.setParameters(new ArrayList<SearchParameter>());
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchIdAndNameByUniqueKey("Demo Search", new BigInteger("999"), new BigInteger("1119"), new BigInteger("0"),"me");
                result = idAndNameList;
                searchManager.saveSearch((Search)any);
                result = search;
                OdsDataServiceImpl.getInstance();
                result = odsDataServiceImpl;
                odsDataServiceImpl.createOdsEntity(anyString,anyString);
                result = "odsentitymeid";
                
                searchManager.editSearch(search);
                result = search;
            }
        };
        api.importData(false,array);
    }
    
    @Test
    public void testGetSearchEMAnalyticsFwkException() throws JSONException, EMAnalyticsFwkException {
        final List<Search> searches = new ArrayList<Search>();
        for (int i = 0; i <= 2; i++) {
            searches.add(new SearchImpl());
        }
        new Expectations() {
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                FolderManager.getInstance();
                result = new EMAnalyticsFwkException(throwable);
            }
        };
        Assert.assertNotNull(api.getSearch(TEST_ID_10, true));
    }

    /**
     * test createOdsEntity
     *
     * @throws JSONException
     */
    @Test
    public void testCreateOdsEntity() throws JSONException, EMAnalyticsFwkException {
        new Expectations() {
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearch((BigInteger)any);
                result = search;
                search.getId();
                result = new BigInteger("999");
                search.getName();
                result = "Saved Search";
                search.getParameters();
                result  = new ArrayList<SearchParameter>();

                OdsDataServiceImpl.getInstance();
                result = odsDataServiceImpl;
                odsDataServiceImpl.createOdsEntity(anyString,anyString);
                result = "odsentitymeid";

            }
        };
    	Assert.assertNotNull(api.createOdsEntity(TEST_ID_999));
    }

    @Mocked
    private RegistryLookupUtil registryLookupUtil;
    @Mocked
    private TenantContext tenantContext;
    @Mocked
    private Link link;
    @Mocked
    private VersionedLink linkInfo;
    @Test
    public void testGetAssetRoot() throws JSONException, EMAnalyticsFwkException {
        new Expectations() {
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                searchManager.getSearch((BigInteger)any);
                result = search;
                categoryManager.getCategory((BigInteger)any);
                result = categoryMocked;
                search.getCategoryId();
                result = 1L;
                categoryMocked.getProviderName();
                result = "LoganService";
                categoryMocked.getProviderAssetRoot();
                result = "assetroot";
                categoryMocked.getProviderVersion();
                result = "1.0";
                TenantContext.getContext().gettenantName();
                result = "emasstesttenant1";
                RegistryLookupUtil.getServiceExternalLink(anyString,anyString,anyString,anyString);
                result = linkInfo;
                RegistryLookupUtil.replaceWithVanityUrl(linkInfo,anyString,anyString);
                result = link;
            }
        };
        Assert.assertEquals(200,api.getAssetRoot(new BigInteger("1")).getStatus());
    }
    @Test
    public void testEditLastAccess() {
        Assert.assertNotNull(api.editLastAccess(TEST_ID_10));
    }

    @Test
    public void testDeleteSearchName() throws EMAnalyticsFwkException {
        api.deleteSearchByName("searchName", "a");
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.deleteSearchByName(anyString, anyBoolean);
            }
        };
        api.deleteSearchByName("searchName", "true");
        api.deleteSearchByName("searchName", "false");
        api.deleteSearchByName("searchName", null);
    }

    @Test
    public void testDeleteSearchNameEMAnalyticsFwkException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.deleteSearchByName(anyString, anyBoolean);
                result = new EMAnalyticsFwkException(throwable);
            }
        };
        api.deleteSearchByName("searchName", "true");
    }
    
    @SuppressWarnings("unchecked")
	@Test
    public void testGetSearchList() throws JSONException, EMAnalyticsFwkException {
		final List<Search> realList = new ArrayList<Search>();
		realList.add(new SearchImpl());
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchListByIds((List<BigInteger>) any);
                result = realList;
            }
        };
        Assert.assertEquals(api.getSearchList(null).getStatus(), 404);
        ArrayNode wrongJson = new ObjectMapper().createArrayNode();
        wrongJson.add("abc");
        Assert.assertEquals(api.getSearchList(wrongJson.toString()).getStatus(), 404);
        ArrayNode inputJson = new ObjectMapper().createArrayNode();
        inputJson.add(1234L);
        Assert.assertNotNull(api.getSearchList(inputJson.toString()));
    }
    
    @Test
    public void testGetSearchByName() throws EMAnalyticsFwkException {
        final Search searchObj = new SearchImpl();
        new Expectations(){
            {
                SearchManager.getInstance();
                result = searchManager;
                dependencyStatus.isDatabaseUp();
                result = true;
                searchManager.getSearchWithoutOwnerByName(anyString);
                result = searchObj;
            }
        };
        Assert.assertNotNull(api.getSearchByName("searchName"));
    }
    
    @Test
    public void testGetSearchByParam() throws EMAnalyticsFwkException {
        final List<Search> searchList = new ArrayList<Search>();
        searchList.add(new SearchImpl());
        
        new Expectations(){
            {
                SearchManager.getInstance();
                result = searchManager;
                dependencyStatus.isDatabaseUp();
                result = true;
                searchManager.getSearchListWithoutOwnerByParam(anyString, anyString);
                result = searchList;
            }
        };
        
        Response res = api.getSearchByParam("name", null);
        Assert.assertEquals(res.getStatus(), 400);
        Assert.assertNotNull(api.getSearchByParam("name", "value"));
    }
}

package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;
import oracle.sysman.emaas.platform.savedsearch.targetmodel.services.OdsDataService;
import oracle.sysman.emaas.platform.savedsearch.targetmodel.services.OdsDataServiceImpl;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.UriInfo;

/**
 * Created by xidai on 2/24/2016.
 */
@Test(groups = {"s2"})
public class SearchAPITest {
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

    @Test
    public void testCreateSearch() throws JSONException {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name", "time");
        p1.put("type", "STRING");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "Demo Search");
        inputJson.put("category", category);
        inputJson.put("description", "Search for demo");
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
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
        p1.put("name", "time");
        p1.put("type", "STRING");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "Demo Search");
        inputJson.put("category", category);
        inputJson.put("description", "Search for demo");
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
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
        p1.put("name", "time");
        p1.put("type", "STRING");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "Demo Search");
        inputJson.put("category", category);
        inputJson.put("description", "Search for demo");
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
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
        p1.put("name", "time");
        p1.put("type", "STRING");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", " ");
        inputJson.put("category", category);
        inputJson.put("description", "Search for demo");
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
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
        p1.put("name", "time");
        p1.put("type", "STRING");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "<> ");
        inputJson.put("category", category);
        inputJson.put("description", "Search for demo");
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
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
        p1.put("name", "time");
        p1.put("type", "STRING");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", " ");
        inputJson.put("category", category);
        inputJson.put("description", "Search for demo");
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
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
        p1.put("attributes", new JSONObject());
        p1.put("name", "time");
        p1.put("type", "STRING");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "name ");
        inputJson.put("description", "description");
        inputJson.put("category", category);
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
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
        p1.put("attributes", new JSONObject());

        p1.put("type", "STRING");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "name ");
        inputJson.put("description", "description");
        inputJson.put("category", category);
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
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
        p1.put("attributes", new JSONObject());
        p1.put("name", "time");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "name ");
        inputJson.put("description", "description");
        inputJson.put("category", category);
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
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
        p1.put("attributes", new JSONObject());
        p1.put("name", "time");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");

        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "name ");
        inputJson.put("description", "description");
        inputJson.put("category", category);
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
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
        p1.put("attributes", new JSONObject());
        p1.put("name", "time");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");

        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "name ");
        inputJson.put("description", "description");

        inputJson.put("folder", folder);
        inputJson.put("type", "SOMthing");
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
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
        p1.put("attributes", new JSONObject());
        p1.put("name", "time");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");

        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "name ");
        inputJson.put("description", "description");
        inputJson.put("category", category);
        inputJson.put("folder", "folder");
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
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
        category.put("id", "999");
        JSONObject folder = new JSONObject();
        folder.put("id", "999");
        JSONObject p = new JSONObject();
        p.put("name", "ODS_ENTITY");
        p.put("type", "STRING");
        p.put("value", "TRUE");
        JSONArray parameter = new JSONArray();
        parameter.put(p);

        JSONObject inputJson = new JSONObject();
        inputJson.put("name", "Demo Search");
        inputJson.put("category", category);
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
                SearchManager.getInstance();
                result = searchManager;
                searchManager.saveSearch(withAny(search));
                result = search;
                searchManager.editSearch(withAny(search));
                result = search;
                odsDataServiceImpl.createOdsEntity(anyString, anyString);
                result = "odsentitymeid";
                search.getParameters();
                result = new ArrayList<>();
            }
        };
        api.createSearch(inputJson);
    }

    @Test
    public void testDeleteSearch() throws JSONException, EMAnalyticsFwkException {
        new Expectations() {
            {
                SearchManager.getInstance();
                result = searchManager;
                searchManager.deleteSearch(anyLong, anyBoolean);
                OdsDataServiceImpl.getInstance();
                result = odsDataServiceImpl;
                odsDataServiceImpl.deleteOdsEntity(anyLong);
            }
        };
        Assert.assertNotNull(api.deleteSearch(100L));

    }

    @Test
    public void testDeleteSearch2nd() throws JSONException, EMAnalyticsFwkException {
        new Expectations() {
            {
                SearchManager.getInstance();
                result = searchManager;
                searchManager.deleteSearch(anyLong, anyBoolean);
                result = new EMAnalyticsFwkException(new Throwable());
                OdsDataServiceImpl.getInstance();
                result = odsDataServiceImpl;
                odsDataServiceImpl.deleteOdsEntity(anyLong);
            }
        };
        Assert.assertNotNull(api.deleteSearch(100L));

    }

    @Test
    public void testEditSearch() throws JSONException, EMAnalyticsFwkException {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name", "time");
        p1.put("type", "STRING");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "Demo Search");
        inputJson.put("category", category);
        inputJson.put("description", "Search for demo");
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);

        new Expectations() {
            {
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearch(anyLong);
                result = search;
                searchManager.editSearch(withAny(search));
                result = search;
            }
        };
        Assert.assertNotNull(api.editSearch(inputJson, "", 10L));
    }

    @Test
    public void testEditSearch2nd() throws JSONException, EMAnalyticsFwkException {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name", "time");
        p1.put("type", "STRING");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "Demo Search");
        inputJson.put("category", category);
        inputJson.put("description", "Search for demo");
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);

        new Expectations() {
            {
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearch(anyLong);
                result = search;
                searchManager.editSearch(withAny(search));
                result = search;
            }
        };
        Assert.assertNotNull(api.editSearch(inputJson, "ORACLE_INTERNAL", 10L));
    }

    @Test
    public void testEditSearch3th() throws JSONException, EMAnalyticsFwkException {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name", "time");
        p1.put("type", "STRING");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "Demo Search");
        inputJson.put("category", category);
        inputJson.put("description", "Search for demo");
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);

        new Expectations() {
            {
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearch(anyLong);
                result = search;
                searchManager.editSearch(withAny(search));
                result = search;
            }
        };
        Assert.assertNotNull(api.editSearch(inputJson, "ORACLE_INTERNAL", 10L));
    }

    @Test
    public void testEditSearch4th() throws JSONException, EMAnalyticsFwkException {
        JSONObject inputJson = new JSONObject();
        JSONObject category = new JSONObject();
        JSONObject folder = new JSONObject();
        JSONArray parameter = new JSONArray();
        JSONObject p1 = new JSONObject();
        JSONObject p2 = new JSONObject();
        p1.put("name", "time");
        p1.put("type", "STRING");
        p1.put("value", "ALL");
        p2.put("name", "additionalInfo");
        p2.put("type", "CLOB");
        p2.put("value", "this is a demo");
        parameter.put(p1);
        parameter.put(p2);
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "Demo Search");
        inputJson.put("category", category);
        inputJson.put("description", "Search for demo");
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);
        new Expectations() {
            {
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearch(anyLong);
                result = new EMAnalyticsWSException(new Throwable());
            }
        };
        Assert.assertNotNull(api.editSearch(inputJson, "ORACLE_INTERNAL", 10L));
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
        folder.put("id", "999");
        category.put("id", "1119");
        inputJson.put("name", "");
        inputJson.put("category", category);
        inputJson.put("description", "Search for demo");
        inputJson.put("folder", folder);
        inputJson.put("parameters", parameter);

        new Expectations() {
            {
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearch(anyLong);
                result = search;
            }
        };
        Assert.assertNotNull(api.editSearch(inputJson, "ORACLE_INTERNAL", 10L));
    }


    @Test
    public void testEditSearchAccessDate() throws JSONException {

        Assert.assertNotNull(api.editSearchAccessDate(10L, true));
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
        Assert.assertNotNull(api.editSearchAccessDate(10L, true));
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
        Assert.assertNotNull(api.editSearchAccessDate(10L, true));
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
        Assert.assertNotNull(api.editSearchAccessDate(10L, false));
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
        Assert.assertNotNull(api.editSearchAccessDate(10L, true));
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
        final String[] path = {"path", "path", "path"};
        for (int i = 0; i <= 2; i++) {
            searches.add(new SearchImpl());
        }
        new Expectations() {
            {
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        Assert.assertNotNull(api.getSearch(10L, true));
    }

    @Test
    public void testGetSearch2nd() throws JSONException, EMAnalyticsFwkException {
        final List<Search> searches = new ArrayList<Search>();
        for (int i = 0; i <= 2; i++) {
            searches.add(new SearchImpl());
        }
        new Expectations() {
            {
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        Assert.assertNotNull(api.getSearch(10L, true));
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
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearch(anyLong);
                result = search;
                search.getId();
                result = 999;
                search.getName();
                result = "Saved Search";
                search.getParameters();
                result = new ArrayList<SearchParameter>();

                OdsDataServiceImpl.getInstance();
                result = odsDataServiceImpl;
                odsDataServiceImpl.createOdsEntity(anyString, anyString);
                result = "odsentitymeid";

            }
        };
        Assert.assertNotNull(api.createOdsEntity(999L));
    }

    @Mocked
    private RegistryLookupUtil registryLookupUtil;
    @Mocked
    private TenantContext tenantContext;
    @Mocked
    private Link link;

    @Test
    public void testGetAssetRoot() throws JSONException, EMAnalyticsFwkException {
        new Expectations() {
            {
                searchManager.getSearch(anyLong);
                result = search;
                categoryManager.getCategory(anyLong);
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
                RegistryLookupUtil.getServiceExternalLink(anyString, anyString, anyString, anyString);
                result = link;
                RegistryLookupUtil.replaceWithVanityUrl(link, anyString, anyString);
                result = link;
            }
        };
        Assert.assertEquals(200, api.getAssetRoot(1L).getStatus());
    }

}
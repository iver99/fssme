package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.category;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import mockit.*;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.TestHelper;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.UriInfo;

/**
 * Created by xidai on 2/22/2016.
 */
@Test(groups={"s2"})
public class CategoryAPITest {
    @Mocked
    UriInfo uriInfo;
    @Mocked
    CategoryManager categoryManager;
    @Mocked
    Category category;
    @Mocked
    URI uri;
    @Mocked
    EntityJsonUtil entityJsonUtil;
    private CategoryAPI categoryAPI = new CategoryAPI();

    @BeforeMethod
	public void setUp(){
		Deencapsulation.setField(categoryAPI, "uri", uriInfo);
	}

    @Test
    public void testGetCategory() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                CategoryManager.getInstance();
                result = categoryManager;
                categoryManager.getCategory(anyLong);
                result = category;
                uriInfo.getBaseUri();
                result = uri;
                EntityJsonUtil.getFullCategoryJsonObj((URI)any, (Category)any);
                result = new JSONObject();
            }
        };
        categoryAPI.getCategory((int) 1L);
    }
    @Mocked
    Throwable throwable;
    @Test
    public void testGetCategoryEMAnalyticsFwkException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                CategoryManager.getInstance();
                result = categoryManager;
                categoryManager.getCategory(anyLong);
                result = new EMAnalyticsFwkException(throwable);
            }
        };
        categoryAPI.getCategory((int) 1L);

    }

    @Test
    public void getCategoryByName() throws EMAnalyticsFwkException {
        categoryAPI.getCategoryByName(null);
        categoryAPI.getCategoryByName("");
        new Expectations(){
            {
                CategoryManager.getInstance();
                result =categoryManager;
                categoryManager.getCategory(anyString);
                result = category;
                uriInfo.getBaseUri();
                result = uri;
                EntityJsonUtil.getFullCategoryJsonObj((URI)any, (Category)any);
                result = new JSONObject();
            }
        };
        categoryAPI.getCategoryByName("name");
    }

    @Test
    public void testGetCategoryByNameEMAnalyticsFwkException() throws EMAnalyticsFwkException {
        categoryAPI.getCategoryByName(null);
        categoryAPI.getCategoryByName("");
        new Expectations(){
            {
                CategoryManager.getInstance();
                result =categoryManager;
                categoryManager.getCategory(anyString);
                result = new EMAnalyticsFwkException(throwable);
            }
        };
        categoryAPI.getCategoryByName("name");
    }
    @Mocked
    SearchManager searchManager;
    @Mocked
    Search search;
    @Test
    public void testGetSearchesByCategory() throws EMAnalyticsFwkException, JSONException {
        categoryAPI.getSearchesByCategory(null, "true");
        categoryAPI.getSearchesByCategory("", "true");
        categoryAPI.getSearchesByCategory("a", "true");
        categoryAPI.getSearchesByCategory("-1", "true");
        final List<Search> searches = new ArrayList<>();
        searches.add(search);
        new Expectations(){
            {
                SearchManager.getInstance();
                result = searchManager;
                CategoryManager.getInstance();
                result = categoryManager;
                searchManager.getSystemSearchListByCategoryId(anyLong);
                result = searches;
                searchManager.getSearchListByCategoryId(anyLong);
                result = searches;
                uriInfo.getBaseUri();
                result = uri;
                EntityJsonUtil.getFullSearchJsonObj((URI)any, (Search)any, null, false);
                result = new JSONObject();
            }
        };
        categoryAPI.getSearchesByCategory("1", "true");
        categoryAPI.getSearchesByCategory("1", null);
    }

    @Test
    public void testGetSearchesByCategoryEMAnalyticsFwkException() throws EMAnalyticsFwkException, JSONException {
        new Expectations(){
            {
                SearchManager.getInstance();
                result = searchManager;
                CategoryManager.getInstance();
                result = categoryManager;
                searchManager.getSystemSearchListByCategoryId(anyLong);
                result =  new EMAnalyticsFwkException(throwable);
            }
        };
        categoryAPI.getSearchesByCategory("1", "true");
        categoryAPI.getSearchesByCategory("1", null);
    }

    @Test
    public void testGetSearchesByCategoryEMAnalyticsFwkException2() throws EMAnalyticsFwkException, JSONException {
        final List<Search> searches = new ArrayList<>();
        searches.add(search);
        new Expectations(){
            {
                SearchManager.getInstance();
                result = searchManager;
                CategoryManager.getInstance();
                result = categoryManager;
                searchManager.getSystemSearchListByCategoryId(anyLong);
                result = searches;
                searchManager.getSearchListByCategoryId(anyLong);
                result = searches;
                uriInfo.getBaseUri();
                result =  new EMAnalyticsFwkException(throwable);
            }
        };
        categoryAPI.getSearchesByCategory("1", "true");
        categoryAPI.getSearchesByCategory("1", null);
    }

}
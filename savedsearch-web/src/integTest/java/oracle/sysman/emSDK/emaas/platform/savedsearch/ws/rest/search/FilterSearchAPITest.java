package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchSummaryImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.TestHelper;

import org.codehaus.jettison.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/24/2016.
 */
@Test(groups = {"s2"})
public class FilterSearchAPITest {
    private FilterSearchAPI filterSearchAPI = new FilterSearchAPI();

    @BeforeMethod
    public void setUp(){
        filterSearchAPI.uri = TestHelper.mockUriInfo();
    }

    @Mocked
    CategoryManager categoryManager;
    @Mocked
    SearchManager searchManager;
    @Mocked
    Search search;
    @Mocked
    URI uri;
    @Mocked
    Category category;
    @Mocked
    FolderManager folderManager;
    @Mocked
    Folder folder;
    @Mocked
    SearchSummary searchSummary;
    @Mocked
    Throwable throwable;
    @Test
    public void testGetAllSearches() throws EMAnalyticsFwkException {
        final List<Search> searches = new ArrayList<Search>();
        for (int i = 0; i <= 2; i++) {
            searches.add(new SearchImpl());
        }
        new Expectations() {
            {
                CategoryManager.getInstance();
                result = categoryManager;
                uri.getQuery();
                result = "categoryId=11";
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchListByCategoryId(anyLong);
                result = searches;
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches2nd(){

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches3th() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                CategoryManager.getInstance();
                result = categoryManager;
                categoryManager.getCategory(anyLong);
                result = new EMAnalyticsFwkException(throwable);
                uri.getQuery();
                result = "categoryId=11";
                SearchManager.getInstance();
                result = searchManager;
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches4th() throws EMAnalyticsFwkException {
        final List<Search> searches = new ArrayList<Search>();
        for (int i = 0; i <= 2; i++) {
            searches.add(new SearchImpl());
        }
        new Expectations() {
            {
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getFolder(anyLong);
                result = folder;
                uri.getQuery();
                result = "folderId=11";
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchListByFolderId(anyLong);
                result = searches;
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches5th() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getFolder(anyLong);
                result = new EMAnalyticsFwkException(throwable);
                uri.getQuery();
                result = "folderId=11";
                SearchManager.getInstance();
                result =searchManager;
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches6th() throws EMAnalyticsFwkException {
        final List<Search> searches = new ArrayList<Search>();
        final String[] path = {"path", "path", "path"};
        for (int i = 0; i <= 2; i++) {
            searches.add(search);
        }
        new Expectations() {
            {
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getPathForFolderId(anyLong);
                result = path;
                uri.getQuery();
                result = "lastAccessCount=11";
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchListByLastAccessDate(anyInt);
                result = searches;
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches7th() throws EMAnalyticsFwkException {
        final List<Search> searches = new ArrayList<>();
        final String[] path = {"path", "path", "path"};
        for (int i = 0; i <= 2; i++) {
            searches.add(search);
        }
        new Expectations() {
            {
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getPathForFolderId(anyLong);
                result =  new EMAnalyticsFwkException(throwable);
                uri.getQuery();
                result = "lastAccessCount=11";
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchListByLastAccessDate(anyInt);
                result = searches;
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches8th() throws EMAnalyticsFwkException {
        final List<Search> searches = new ArrayList<Search>();
        final String[] path = {"path", "path", "path"};
        for (int i = 0; i <= 2; i++) {
            searches.add(search);
        }
        new Expectations() {
            {
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getPathForFolderId(anyLong);
                result =  new JSONException("");
                uri.getQuery();
                result = "lastAccessCount=11";
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchListByLastAccessDate(anyInt);
                result = searches;
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches9th() throws EMAnalyticsFwkException {
        final List<Search> searches = new ArrayList<Search>();
        final String[] path = {"path", "path", "path"};
        for (int i = 0; i <= 2; i++) {
            searches.add(search);
        }
        new Expectations() {
            {
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getPathForFolderId(anyLong);
                result =  new Exception("");
                uri.getQuery();
                result = "lastAccessCount=11";
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchListByLastAccessDate(anyInt);
                result = searches;
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches10th() throws EMAnalyticsFwkException {
        final List<Search> searches = new ArrayList<Search>();
        for (int i = 0; i <= 2; i++) {
            searches.add(search);
        }
        new Expectations() {
            {
                CategoryManager.getInstance();
                result = categoryManager;
                categoryManager.getCategory(anyString);
                result = category;
//                categoryManager.getCategory(anyLong);
//                result = category;
                category.getId();
                result = 1;
                uri.getQuery();
                result = "categoryName=11";
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchListByCategoryId(anyLong);
                result = searches;
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches11th() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                CategoryManager.getInstance();
                result = categoryManager;
                categoryManager.getCategory(anyString);
                result = new EMAnalyticsFwkException(throwable);
                uri.getQuery();
                result = "categoryName=11";
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches12th(){
        new Expectations() {
            {
                uri.getQuery();
                result = "folderId=-11";
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches13th(){
        new Expectations() {
            {
                uri.getQuery();
                result = "categoryId=-11";
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches14th(){
        new Expectations() {
            {
                uri.getQuery();
                result = null;
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches15th(){
        new Expectations() {
            {
                uri.getQuery();
                result = "query";
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches16th(){
        new Expectations() {
            {
                uri.getQuery();
                result = null;
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }
}
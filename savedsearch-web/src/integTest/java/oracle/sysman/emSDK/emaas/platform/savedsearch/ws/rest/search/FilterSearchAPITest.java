package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search;

import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchSummary;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.TestHelper;

import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
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
    @Mocked
    DependencyStatus dependencyStatus;
    @Test
    public void testGetAllSearches() throws EMAnalyticsFwkException {
        final List<Search> searches = new ArrayList<Search>();
        for (int i = 0; i <= 2; i++) {
            searches.add(new SearchImpl());
        }
        new Expectations() {
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                CategoryManager.getInstance();
                result = categoryManager;
                uri.getQuery();
                result = "categoryId=11";
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchListByCategoryId((BigInteger) any);
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
                dependencyStatus.isDatabaseUp();
                result = true;
                CategoryManager.getInstance();
                result = categoryManager;
                categoryManager.getCategory((BigInteger) any);
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
                dependencyStatus.isDatabaseUp();
                result = true;
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getFolder((BigInteger) any);
                result = folder;
                uri.getQuery();
                result = "folderId=11";
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchListByFolderId((BigInteger) any);
                result = searches;
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches5th() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getFolder((BigInteger) any);
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
                dependencyStatus.isDatabaseUp();
                result = true;
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getPathForFolderId((BigInteger) any);
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
        for (int i = 0; i <= 2; i++) {
            searches.add(search);
        }
        new Expectations() {
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getPathForFolderId((BigInteger) any);
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
        for (int i = 0; i <= 2; i++) {
            searches.add(search);
        }
        new Expectations() {
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getPathForFolderId((BigInteger) any);
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
        for (int i = 0; i <= 2; i++) {
            searches.add(search);
        }
        new Expectations() {
            {
                dependencyStatus.isDatabaseUp();
                result = true;
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getPathForFolderId((BigInteger) any);
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
                dependencyStatus.isDatabaseUp();
                result = true;
                CategoryManager.getInstance();
                result = categoryManager;
                categoryManager.getCategory(anyString);
                result = category;
//                categoryManager.getCategory(anyLong);
//                result = category;
                category.getId();
                result = BigInteger.ONE;
                uri.getQuery();
                result = "categoryName=11";
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchListByCategoryId((BigInteger) any);
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
    public void testGetAllSearches12th() {
        new Expectations() {
            {
                uri.getQuery();
                result = "folderId=-11";
            }
        };
        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri, "", "", "", ""));
    }

    @Test
    public void testGetAllSearches13th() {
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
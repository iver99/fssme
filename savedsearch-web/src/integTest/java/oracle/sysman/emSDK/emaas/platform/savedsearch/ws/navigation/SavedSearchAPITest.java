package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.navigation;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

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
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.TestHelper;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.jetty.util.ajax.JSON;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/18/2016.
 */
@Test (groups = {"s2"})
public class SavedSearchAPITest {
    private SavedSearchAPI savedSearchAPI = new SavedSearchAPI();
    @Mocked
    PersistenceManager persistenceManager;
    @Mocked
    EntityManager entityManager;
    @Mocked
    TenantContext tenantContext;
    @Mocked
    TenantInfo tenantInfo;
    @Mocked
    EmAnalyticsFolder emAnalyticsFolder;
    @Mocked
    FolderManager folderManager;
    @Mocked
    SearchManager searchManager;
    @Mocked
    Folder folder;
    @Mocked
    CategoryManager categoryManager;
    @Mocked
    Throwable throwable;
    @BeforeMethod
    public void setUp(){
        savedSearchAPI.uri = TestHelper.mockUriInfo();
    }

    @Test (groups = {"s2"})
    public void testGetAllCategory() throws EMAnalyticsFwkException {

        final List<Category> catList1 = new ArrayList<Category>();
        for(int i=0;i<=3;i++)catList1.add(new CategoryImpl());
        new Expectations(){
            {
                categoryManager.getInstance();
                result = categoryManager;
                categoryManager.getAllCategories();
                returns(catList1);
            }
        };
        Assert.assertNotNull(savedSearchAPI.getAllCategory());
    }
    @Test (groups = {"s2"})
    public void testGetAllCategory2nd() throws EMAnalyticsFwkException {
        SavedSearchAPI savedSearchAPI = new SavedSearchAPI();
        final List<Category> catList2 = new ArrayList<Category>();
        for(int i=0;i<=3;i++)catList2.add(new CategoryImpl());
        new Expectations(){
            {
                categoryManager.getInstance();
                result = categoryManager;
                categoryManager.getAllCategories();
                result = new EMAnalyticsFwkException(throwable);
            }
        };
        Assert.assertNotNull(savedSearchAPI.getAllCategory());
    }

    @Test (groups = {"s2"})
    public void testGetAllCategory3th() throws EMAnalyticsFwkException {
        SavedSearchAPI savedSearchAPI = new SavedSearchAPI();
        final List<Category> catList3 = new ArrayList<Category>();
        for(int i=0;i<=3;i++)catList3.add(new CategoryImpl());
        new Expectations(){
            {
                categoryManager.getInstance();
                result = categoryManager;
                categoryManager.getAllCategories();
                result = new  Exception(throwable);
            }
        };
        Assert.assertNotNull(savedSearchAPI.getAllCategory());
    }

    @Test (groups = {"s2"})
    public void testGetDetails() throws EMAnalyticsFwkException {
        final Folder folder = new FolderImpl();
        final List<Folder> folderList = new ArrayList<Folder>();
        folderList.add(folder);

        final List<Search> searchLists = new ArrayList<Search>();
        searchLists.add(new SearchImpl());
        new Expectations(){
            {
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getSubFolders(anyLong);
                result = folderList;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchListByFolderId(anyLong);
                result = searchLists;
                entityJsonUtil.getSimpleFolderJsonObj((URI)any, (Folder)any, true);
                result = new JSONObject();
                EntityJsonUtil.getSimpleSearchJsonObj((URI)any, (Search)any, null, true);
                result = new JSONObject();
            }
        };
        Assert.assertNotNull(savedSearchAPI.getDetails(""));
        Assert.assertNotNull(savedSearchAPI.getDetails(null));
        Assert.assertNotNull(savedSearchAPI.getDetails("id"));
        Assert.assertNotNull(savedSearchAPI.getDetails("-111"));
        Assert.assertNotNull(savedSearchAPI.getDetails("111"));
    }


    @Test (groups = {"s2"})
    public void testGetDetailsJSONException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                FolderManager.getInstance();
                result = new JSONException(throwable);
            }
        };
        Assert.assertNotNull(savedSearchAPI.getDetails("111"));
    }

    @Test (groups = {"s2"})
    public void testGetDetailsEMAnalyticsFwkException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                FolderManager.getInstance();
                result = new EMAnalyticsFwkException(throwable);
            }
        };
        Assert.assertNotNull(savedSearchAPI.getDetails("111"));
    }

    @Test (groups = {"s2"})
    public void testGetDetailsUnsupportedEncodingException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                FolderManager.getInstance();
                result = new UnsupportedEncodingException();
            }
        };
        Assert.assertNotNull(savedSearchAPI.getDetails("111"));
    }

    @Mocked
    EntityJsonUtil entityJsonUtil;
    @Test (groups = {"s2"})
    public void testGetRootFolders() throws EMAnalyticsFwkException {

        final Folder folder = new FolderImpl();
        final List<Folder> folderList = new ArrayList<Folder>();
        folderList.add(folder);

        final List<Search> searchLists = new ArrayList<Search>();
        searchLists.add(new SearchImpl());
        new Expectations(){
            {
                FolderManager.getInstance();
                result = folderManager;
                folderManager.getSubFolders(anyLong);
                result = folderList;
                SearchManager.getInstance();
                result = searchManager;
                searchManager.getSearchListByFolderId(anyLong);
                result = searchLists;
                entityJsonUtil.getSimpleFolderJsonObj((URI)any, (Folder)any, true);
                result = new JSONObject();
                EntityJsonUtil.getSimpleSearchJsonObj((URI)any, (Search)any, null, true);
                result = new JSONObject();
            }
        };
        Assert.assertNotNull(savedSearchAPI.getRootFolders());
    }

    @Test (groups = {"s2"})
    public void testGetRootFoldersEMAnalyticsFwkException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                FolderManager.getInstance();
                result = new EMAnalyticsFwkException(throwable);
            }
        };
        Assert.assertNotNull(savedSearchAPI.getRootFolders());
    }
    @Test (groups = {"s2"})
    public void testGetRootFoldersException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                FolderManager.getInstance();
                result = new Exception(throwable);
            }
        };
        Assert.assertNotNull(savedSearchAPI.getRootFolders());
    }
}
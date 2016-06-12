package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import mockit.Mock;
import mockit.MockUp;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchSummaryImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.TestHelper;

import org.codehaus.jettison.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by xidai on 2/24/2016.
 */
@Test(groups={"s2"})
public class FilterSearchAPITest {
    private FilterSearchAPI filterSearchAPI = new FilterSearchAPI();
    @BeforeMethod
    public void setUp() throws Exception {
        filterSearchAPI.uri = TestHelper.mockUriInfo();
    }
    
    @Test
    public void testGetAllSearches( ) throws Exception {
        final CategoryImpl category = new CategoryImpl();
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public Category getCategory(long categoryId) throws EMAnalyticsFwkException
            {
                return category;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "categoryId=11";
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }
    @Test
    public void testGetAllSearches2nd( ) throws Exception {

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }
    @Test
    public void testGetAllSearches3th( ) throws Exception {
        final CategoryImpl category = new CategoryImpl();
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public Category getCategory(long categoryId) throws EMAnalyticsFwkException
            {   if(true)
                throw new EMAnalyticsFwkException(new Throwable());
                return category;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "categoryId=11";
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }
    @Test
    public void testGetAllSearches4th( ) throws Exception {
        final Folder folder = new FolderImpl();
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<FolderManagerImpl>(){
            @Mock
            public Folder getFolder(long folderId) throws EMAnalyticsFwkException
            {
                return folder;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByFolderId(long folderId) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "folderId=11";
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }
    @Test
    public void testGetAllSearches5th( ) throws Exception {
        final Folder folder = new FolderImpl();
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<FolderManagerImpl>(){
            @Mock
            public Folder getFolder(long folderId) throws EMAnalyticsFwkException
            {
                if(true){throw new EMAnalyticsFwkException(new Throwable());}
                return folder;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByFolderId(long folderId) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "folderId=11";
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }

    @Test
    public void testGetAllSearches6th( ) throws Exception {
        final Folder folder = new FolderImpl();
        final List<Search> searches = new ArrayList<Search>();
        final String[] path = {"path","path","path"};
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<FolderManagerImpl>(){
            @Mock
            public Folder getFolder(long folderId) throws EMAnalyticsFwkException
            {
                return folder;
            }
            @Mock
            public String[] getPathForFolderId(long folderId) throws EMAnalyticsFwkException
            {
                return path;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByLastAccessDate(int count) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<SearchSummaryImpl>(){
            @Mock
            public Integer getFolderId(){
                return 1;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "lastAccessCount=11";
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }

    @Test
    public void testGetAllSearches7th( ) throws Exception {
        final Folder folder = new FolderImpl();
        final List<Search> searches = new ArrayList<Search>();
        final String[] path = {"path","path","path"};
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<FolderManagerImpl>(){
            @Mock
            public Folder getFolder(long folderId) throws EMAnalyticsFwkException
            {
                if(true){throw new EMAnalyticsFwkException(new Throwable());}
                return folder;
            }
            @Mock
            public String[] getPathForFolderId(long folderId) throws EMAnalyticsFwkException
            {

                if(true){throw new EMAnalyticsFwkException(new Throwable());}
                return path;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByLastAccessDate(int count) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<SearchSummaryImpl>(){
            @Mock
            public Integer getFolderId(){
                return 1;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "lastAccessCount=11";
            }
        };
        new MockUp<Throwable>(){
            @Mock
            public void printStackTrace(){
            }

        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }
    @Test
    public void testGetAllSearches8th( ) throws Exception {
        final Folder folder = new FolderImpl();
        final List<Search> searches = new ArrayList<Search>();
        final String[] path = {"path","path","path"};
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<FolderManagerImpl>(){
            @Mock
            public Folder getFolder(long folderId) throws EMAnalyticsFwkException
            {
                if(true){throw new EMAnalyticsFwkException(new Throwable());}
                return folder;
            }
            @Mock
            public String[] getPathForFolderId(long folderId) throws EMAnalyticsFwkException,JSONException
            {
                if(true){
                    throw new JSONException("");
                }
                return path;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByLastAccessDate(int count) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<SearchSummaryImpl>(){
            @Mock
            public Integer getFolderId(){
                return 1;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "lastAccessCount=11";
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }

    @Test
    public void testGetAllSearches9th( ) throws Exception {
        final Folder folder = new FolderImpl();
        final List<Search> searches = new ArrayList<Search>();
        final String[] path = {"path","path","path"};
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<FolderManagerImpl>(){
            @Mock
            public Folder getFolder(long folderId) throws EMAnalyticsFwkException
            {
                if(true){throw new EMAnalyticsFwkException(new Throwable());}
                return folder;
            }
            @Mock
            public String[] getPathForFolderId(long folderId) throws EMAnalyticsFwkException,Exception
            {
                if(true){
                    throw new Exception("");
                }
                return path;
            }

        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByLastAccessDate(int count) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<SearchSummaryImpl>(){
            @Mock
            public Integer getFolderId(){
                return 1;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "lastAccessCount=11";
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }

    @Test
    public void testGetAllSearches10th( ) throws Exception {
        final CategoryImpl category = new CategoryImpl();
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public Category getCategory(String categoryName) throws EMAnalyticsFwkException
            {
                return category;
            }
            @Mock
            public Category getCategory(long categoryId) throws EMAnalyticsFwkException
            {
                return category;
            }
        };
        new MockUp<CategoryImpl>(){
           @Mock
            public Integer getId()
            {
                return 1;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "categoryName=11";
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }
    @Test
    public void testGetAllSearches11th( ) throws Exception {
        final CategoryImpl category = new CategoryImpl();
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public Category getCategory(String categoryName) throws EMAnalyticsFwkException
            {
                if(true){throw new EMAnalyticsFwkException(new Throwable());}
                return category;
            }
            @Mock
            public Category getCategory(long categoryId) throws EMAnalyticsFwkException
            {
                if(true){throw new EMAnalyticsFwkException(new Throwable());}
                return category;
            }
        };
        new MockUp<CategoryImpl>(){
            @Mock
            public Integer getId()
            {
                return 1;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "categoryName=11";
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }
    @Test
    public void testGetAllSearches12th( ) throws Exception {
        final Folder folder = new FolderImpl();
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<FolderManagerImpl>(){
            @Mock
            public Folder getFolder(long folderId) throws EMAnalyticsFwkException
            {
                return folder;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByFolderId(long folderId) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "folderId=-11";
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }
    @Test
    public void testGetAllSearches13th( ) throws Exception {
        final CategoryImpl category = new CategoryImpl();
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public Category getCategory(long categoryId) throws EMAnalyticsFwkException
            {
                return category;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "categoryId=-11";
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }
    @Test
    public void testGetAllSearches14th( ) throws Exception {
        final CategoryImpl category = new CategoryImpl();
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public Category getCategory(long categoryId) throws EMAnalyticsFwkException
            {
                return category;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return null;
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }
    @Test
    public void testGetAllSearches15th( ) throws Exception {
        final CategoryImpl category = new CategoryImpl();
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public Category getCategory(long categoryId) throws EMAnalyticsFwkException
            {
                return category;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "query";
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }
    @Test
    public void testGetAllSearches16th( ) throws Exception {
        final CategoryImpl category = new CategoryImpl();
        final List<Search> searches = new ArrayList<Search>();
        for(int i = 0;i<=2;i++){searches.add(new SearchImpl());}
        new MockUp<CategoryManagerImpl>(){
            @Mock
            public Category getCategory(long categoryId) throws EMAnalyticsFwkException
            {
                return category;
            }
        };
        new MockUp<SearchManagerImpl>(){
            @Mock
            public List<Search> getSearchListByCategoryId(long categoryId) throws EMAnalyticsFwkException
            {
                return searches;
            }

        };
        new MockUp<URI>(){
            @Mock
            public String getQuery() {
                return "cateGoryNam=11";
            }
        };

        Assert.assertNotNull(filterSearchAPI.getAllSearches(filterSearchAPI.uri,"","","",""));
    }
}
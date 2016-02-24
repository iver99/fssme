package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.search;

import mockit.Mock;
import mockit.MockUp;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.*;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import org.codehaus.jettison.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 2/24/2016.
 */
public class FilterSearchAPITest {
    private FilterSearchAPI filterSearchAPI = new FilterSearchAPI();
    @BeforeMethod
    public void setUp() throws Exception {

        final UriInfo uri = new UriInfo() {
            @Override
            public String getPath() {
                return null;
            }

            @Override
            public String getPath(boolean decode) {
                return null;
            }

            @Override
            public List<PathSegment> getPathSegments() {
                return null;
            }

            @Override
            public List<PathSegment> getPathSegments(boolean decode) {
                return null;
            }

            @Override
            public URI getRequestUri() {
                URI uril = null;
                try{
                 uril = new URI("");
                }catch(Exception e){
                    e.printStackTrace();
                }
                return uril;

            }

            @Override
            public UriBuilder getRequestUriBuilder() {
                return null;
            }

            @Override
            public URI getAbsolutePath() {
                return null;
            }

            @Override
            public UriBuilder getAbsolutePathBuilder() {
                return null;
            }

            @Override
            public URI getBaseUri() {
                return null;
            }

            @Override
            public UriBuilder getBaseUriBuilder() {
                return null;
            }


            @Override
            public MultivaluedMap<String, String> getPathParameters() {
                return null;
            }

            @Override
            public MultivaluedMap<String, String> getPathParameters(boolean decode) {
                return null;
            }

            @Override
            public MultivaluedMap<String, String> getQueryParameters() {
                return null;
            }

            @Override
            public MultivaluedMap<String, String> getQueryParameters(boolean decode) {
                return null;
            }

            @Override
            public List<String> getMatchedURIs() {
                return null;
            }

            @Override
            public List<String> getMatchedURIs(boolean decode) {
                return null;
            }

            @Override
            public List<Object> getMatchedResources() {
                return null;
            }

            @Override
            public URI resolve(URI uri) {
                return null;
            }

            @Override
            public URI relativize(URI uri) {
                return null;
            }
        };

        filterSearchAPI.uri = uri;

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
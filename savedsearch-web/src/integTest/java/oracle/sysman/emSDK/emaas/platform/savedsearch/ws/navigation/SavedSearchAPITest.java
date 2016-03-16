package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.navigation;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.*;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
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
 * Created by xidai on 2/18/2016.
 */
@Test (groups = {"s2"})
public class SavedSearchAPITest {
    private SavedSearchAPI savedSearchAPI = new SavedSearchAPI();


    @BeforeMethod
    public void setUp() throws Exception {

        UriInfo uri = new UriInfo() {
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
                return null;
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

        savedSearchAPI.uri = uri;

    }

    @Test (groups = {"s2"})
    public void testGetAllCategory(@Mocked final CategoryManager cm, @Mocked final CategoryManagerImpl anyCmi) throws Exception {

        final List<Category> catList1 = new ArrayList<Category>();
        for(int i=0;i<=3;i++)catList1.add(new CategoryImpl());
        new Expectations(){
            {
                cm.getInstance();
                result = anyCmi;
                anyCmi.getAllCategories();
                returns(catList1);
            }
        };
        Assert.assertNotNull(savedSearchAPI.getAllCategory());
    }
    @Test (groups = {"s2"})
    public void testGetAllCategory2nd(@Mocked final CategoryManager cm, @Mocked final CategoryManagerImpl anyCmi) throws Exception {
        SavedSearchAPI savedSearchAPI = new SavedSearchAPI();
        final List<Category> catList2 = new ArrayList<Category>();
        for(int i=0;i<=3;i++)catList2.add(new CategoryImpl());
        new Expectations(){
            {
                cm.getInstance();
                result = anyCmi;
                anyCmi.getAllCategories();
                result = new EMAnalyticsFwkException(10, new Throwable());
            }
        };
        Assert.assertNotNull(savedSearchAPI.getAllCategory());
    }

    @Test (groups = {"s2"})
    public void testGetAllCategory3th(@Mocked final CategoryManager cm, @Mocked final CategoryManagerImpl anyCmi) throws Exception {
        SavedSearchAPI savedSearchAPI = new SavedSearchAPI();
        final List<Category> catList3 = new ArrayList<Category>();
        for(int i=0;i<=3;i++)catList3.add(new CategoryImpl());
        new Expectations(){
            {
                cm.getInstance();
                result = anyCmi;
                anyCmi.getAllCategories();
                result = new  Exception();
            }
        };
        Assert.assertNotNull(savedSearchAPI.getAllCategory());
    }

    @Test (groups = {"s1"})
    public void testGetDetails() throws Exception {
        Assert.assertNotNull(savedSearchAPI.getDetails(""));
        Assert.assertNotNull(savedSearchAPI.getDetails(null));
        Assert.assertNotNull(savedSearchAPI.getDetails("id"));

    }

    @Test (groups = {"s2"})
    public void testGetRootFolders() throws Exception {

        final Folder folder = new FolderImpl();
        final List<Folder> folderList = new ArrayList<Folder>();
        folderList.add(folder);

        final List<Search> searchLists = new ArrayList<Search>();
        searchLists.add(new SearchImpl());
        new Expectations(){
            {
                new MockUp<FolderManagerImpl>(){
                    @Mock
                    public Folder getFolder(long folderId) throws EMAnalyticsFwkException{
                        return folder;
                    }
                    @Mock
                    public List<Folder> getSubFolders(long folderId) throws EMAnalyticsFwkException {
                        return folderList;
                    }
                };

                new MockUp<SearchManagerImpl>(){
                  @Mock
                  public List<Search> getSearchListByFolderId(long folderId) throws EMAnalyticsFwkException
                  {
                      folderId = 10L;
                      return searchLists;
                  }
                };
            }
        };
        Assert.assertNotNull(savedSearchAPI.getRootFolders());
    }
}
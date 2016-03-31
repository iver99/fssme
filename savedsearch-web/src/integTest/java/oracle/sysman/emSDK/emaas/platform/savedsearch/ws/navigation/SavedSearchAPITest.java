package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.navigation;

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
    @BeforeMethod
    public void setUp() throws Exception {
        savedSearchAPI.uri = TestHelper.mockUriInfo();
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

    @Test (groups = {"s2"})
    public void testGetDetails() throws Exception {
        Assert.assertNotNull(savedSearchAPI.getDetails(""));
        Assert.assertNotNull(savedSearchAPI.getDetails(null));
        Assert.assertNotNull(savedSearchAPI.getDetails("id"));

        Assert.assertNotNull(savedSearchAPI.getDetails("111"));
    }


    @Test (groups = {"s2"})
    public void testGetDetails2nd() throws Exception {
        Assert.assertNotNull(savedSearchAPI.getDetails(""));
        Assert.assertNotNull(savedSearchAPI.getDetails(null));
        Assert.assertNotNull(savedSearchAPI.getDetails("id"));
        final List<Folder> folderList = new ArrayList<>();
        folderList.add(folder);
        new Expectations(){
            {
                FolderManager.getInstance();
                result =folderManager;
                SearchManager.getInstance();
                result =searchManager;
                folderManager.getFolder(anyLong);
                folderManager.getSubFolders(anyLong);
                result = folderList;
            }
        };
        Assert.assertNotNull(savedSearchAPI.getDetails("111"));
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
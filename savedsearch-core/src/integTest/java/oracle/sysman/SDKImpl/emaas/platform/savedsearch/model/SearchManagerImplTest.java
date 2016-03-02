package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;

/**
 * @author qianqi
 * @since 16-3-2.
 */
public class SearchManagerImplTest {

    SearchManagerImpl searchManager;

    @BeforeMethod
    public void testGetInstance() throws Exception {
        searchManager = SearchManagerImpl.getInstance();
    }

    @Test
    public void testCreateNewSearch() throws Exception {
        searchManager.createNewSearch();
    }

    @Test
    public void testDeleteSearch(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final EmAnalyticsSearch emAnalyticsSearch) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo)any);
                result = entityManager;
                EmAnalyticsObjectUtil.getSearchByIdForDelete(anyLong,entityManager);
                result = emAnalyticsSearch;

            }
        };
        searchManager.deleteSearch(1234,true);
    }

    @Test
    public void testEditSearch() throws Exception {

    }

    @Test
    public void testGetSearch() throws Exception {

    }

    @Test
    public void testGetSearchByName() throws Exception {

    }

    @Test
    public void testGetSearchCountByFolderId() throws Exception {

    }

    @Test
    public void testGetSearchListByCategoryId() throws Exception {

    }

    @Test
    public void testGetSearchListByFolderId() throws Exception {

    }

    @Test
    public void testGetSearchListByLastAccessDate() throws Exception {

    }

    @Test
    public void testGetSystemSearchListByCategoryId() throws Exception {

    }

    @Test
    public void testGetWidgetListByCategoryId() throws Exception {

    }

    @Test
    public void testGetWidgetScreenshotById() throws Exception {

    }

    @Test
    public void testModifyLastAccessDate() throws Exception {

    }

    @Test
    public void testSaveMultipleSearch() throws Exception {

    }

    @Test
    public void testSaveMultipleSearch1() throws Exception {

    }

    @Test
    public void testSaveSearch() throws Exception {

    }
}
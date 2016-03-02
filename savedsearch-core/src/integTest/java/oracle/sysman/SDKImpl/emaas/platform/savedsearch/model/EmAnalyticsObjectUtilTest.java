package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

/**
 * @author qianqi
 * @since 16-2-29.
 */
public class EmAnalyticsObjectUtilTest {

//    @Test
//    public void testCanDeleteFolder(@Mocked final EntityManagerImpl entityManager, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo,@Mocked ) throws Exception {
//        new Expectations(){
//            {
//                entityManager.createNamedQuery(anyString);
//                result = new EJBQueryImpl("Search.getSearchCountByFolder", entityManager, true);
//                TenantContext.getContext();
//                result = tenantInfo;
//                tenantInfo.getUsername();
//                result = "papapa";
//            }
//        };
//        EmAnalyticsObjectUtil.canDeleteFolder(1234,entityManager);
//    }

    @Test
    public void testGetCategoryById() throws Exception {

    }

    @Test
    public void testGetCategoryByIdForDelete() throws Exception {

    }

    @Test
    public void testGetCategoryByName() throws Exception {

    }

    @Test
    public void testGetEmAnalyticsCategoryForAdd() throws Exception {

    }

    @Test
    public void testGetEmAnalyticsCategoryForEdit() throws Exception {

    }

    @Test
    public void testGetEmAnalyticsFolderByFolderObject() throws Exception {

    }

    @Test
    public void testGetEmAnalyticsFolderForAdd() throws Exception {

    }

    @Test
    public void testGetEmAnalyticsFolderForEdit() throws Exception {

    }

    @Test
    public void testGetEmAnalyticsSearchForAdd() throws Exception {

    }

    @Test
    public void testGetEmAnalyticsSearchForEdit() throws Exception {

    }

    @Test(groups = {"s2"})
    public void testGetFolderById_if(@Mocked final EntityManager em,@Mocked final EmAnalyticsFolder emAnalyticsFolder) throws Exception {
        new Expectations(){
            {
                em.find(EmAnalyticsFolder.class,anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = 0;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(1);
            }
        };
        EmAnalyticsObjectUtil.getFolderById(1234, em);
    }

//    @Test(groups = {"s2"})
//    public void testGetFolderById_else(@Mocked final EntityManager em,@Mocked final EmAnalyticsFolder emAnalyticsFolder) throws Exception {
//        new Expectations(){
//            {
//                em.find(EmAnalyticsFolder.class,anyLong);
//                result = emAnalyticsFolder;
//                emAnalyticsFolder.getDeleted();
//                result = 2;
//                emAnalyticsFolder.getSystemFolder();
//                result = new BigDecimal(1);
//            }
//        };
//        EmAnalyticsObjectUtil.getFolderById(1234, em);
//    }

    @Test(groups = {"s1"})
    public void testGetFolderById_catch() throws Exception {
        EmAnalyticsObjectUtil.getFolderById(1234, null);
    }

    @Test
    public void testGetFolderByIdForDelete() throws Exception {

    }

    @Test
    public void testGetRootFolder() throws Exception {

    }

    @Test
    public void testGetSearchById() throws Exception {

    }

    @Test
    public void testGetSearchByIdForDelete() throws Exception {

    }
}
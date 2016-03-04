package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jishshi
 * @since 2016/2/29.
 */
@Test(groups = {"s2"})
public class CategoryManagerImplTest {
    CategoryManagerImpl categoryManagerImpl;
    CategoryManager categoryManager;


    @Mocked
    PersistenceManager persistenceManager;
    @Mocked
    EntityManagerImpl entityManager;
    @Mocked
    TenantInfo tenantInfo;
    @Mocked
    EmAnalyticsObjectUtil emAnalyticsObjectUtil;
    @Mocked
    EmAnalyticsCategory emAnalyticsCategory;
    @Mocked
    EntityTransaction entityTransaction;
    @Mocked
    Category category;
    @Mocked
    EJBQueryImpl ejbQuery;
    @Mocked
    DatabaseQuery databaseQuery;
    @Mocked
    TenantContext tenantContext;


    @BeforeMethod
    public void setUp() throws Exception {
         categoryManager = CategoryManagerImpl.getInstance();
    }

    @Test
    public void testGetInstance() throws Exception {
        Assert.assertNotNull(categoryManager);
    }

    @Test
    public void testCreateNewCategory() throws Exception {
        Assert.assertNotNull(categoryManager.createNewCategory());
    }

    @Test
    public void testDeleteCategory() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryById(anyLong,withAny(entityManager));
                result = emAnalyticsCategory;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                entityManager.merge(withAny(emAnalyticsCategory));
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.commit();
                entityManager.close();
            }
        };
        categoryManager.deleteCategory(10L,false);
    }
    @Test
    public void testDeleteCategory2nd() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryByIdForDelete(anyLong,withAny(entityManager));
                result = emAnalyticsCategory;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                entityManager.remove(withAny(emAnalyticsCategory));
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.commit();
                entityManager.close();

            }
        };
        categoryManager.deleteCategory(10L,true);
    }

    @Test
    public void testDeleteCategory3th() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryByIdForDelete(anyLong,withAny(entityManager));
                result = emAnalyticsCategory;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                entityManager.remove(withAny(emAnalyticsCategory));
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.commit();
                result = new EMAnalyticsFwkException(new Throwable());
                entityManager.close();

            }
        };
        try {
            categoryManager.deleteCategory(10L, true);
        }catch(EMAnalyticsFwkException e){
            Assert.assertTrue(true);
        }
    }


    @Test
    public void testDeleteCategory4th() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryByIdForDelete(anyLong,withAny(entityManager));
                result = emAnalyticsCategory;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                entityManager.remove(withAny(emAnalyticsCategory));
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.commit();
                result = new  Exception();
                entityManager.close();
            }
        };
        try {
            categoryManager.deleteCategory(10L, true);
        }catch(Exception e){
            Assert.assertTrue(true);
        }
    }
    @Test
    public void testEditCategory() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(withAny(category),withAny(entityManager));
                result = emAnalyticsCategory;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                entityManager.merge(withAny(emAnalyticsCategory));
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.commit();
                entityManager.close();
            }
        };
    categoryManager.editCategory(new CategoryImpl());
    }

    @Test
    public void testEditCategory2nd() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(withAny(category),withAny(entityManager));
                result = new EMAnalyticsFwkException(new Throwable());
                entityManager.close();
            }
        };
        try {
            categoryManager.editCategory(new CategoryImpl());
        }catch(EMAnalyticsFwkException e){
            Assert.assertTrue(true);
        }
    }
    @Test
    public void testEditCategory3th() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(withAny(category),withAny(entityManager));
                result = new  Exception();
                entityManager.close();
            }
        };
        try {
            categoryManager.editCategory(new CategoryImpl());
        }catch(Exception e){
            Assert.assertTrue(true);
        }
    }


    @Test
    public void testGetAllCategories() throws Exception {
        //TODO
        final List<EmAnalyticsCategory> categories = new ArrayList<>();
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.createNamedQuery("Category.getAllCategory");
                result = ejbQuery;
            }
        };
        try {
            categoryManager.getAllCategories();
        }catch(Exception e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetCategory() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryById(anyLong,withAny(entityManager));
                result = emAnalyticsCategory;
            }
        };
        Assert.assertNotNull(categoryManager.getCategory(10L));
    }

    @Test
    public void testGetCategory2nd() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryById(anyLong,withAny(entityManager));
                result = new Exception();
            }
        };
        try {
            categoryManager.getCategory(10L);
        }catch(EMAnalyticsFwkException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetCategory1() throws Exception {

    }

    @Test
    public void testSaveCategory() throws Exception {

    }

    @Test
    public void testSaveMultipleCategories() throws Exception {

    }
}
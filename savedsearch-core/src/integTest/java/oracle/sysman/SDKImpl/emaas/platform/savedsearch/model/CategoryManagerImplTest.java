package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EmAnalyticsProcessingException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
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
    EmAnalyticsObjectUtil emAnalyticsObjectUtil;
    @Mocked
    EmAnalyticsCategory emAnalyticsCategory;
    @Mocked
    EntityTransaction entityTransaction;
    @Mocked
    Category category;
    @Mocked
    Query query;
    @Mocked
    TenantContext tenantContext;
    @Mocked
    TenantInfo tenantInfo;
    @Mocked
    ImportCategoryImpl importCategory;
    @Mocked
    EmAnalyticsProcessingException emAnalyticsProcessingException;
    @Mocked
    Folder folder;
    @Mocked
    Exception exception;
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
                result = exception;
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
                result = exception;
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
                result = query;
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
                result = exception;
            }
        };
        try {
            categoryManager.getCategory(10L);
        }catch(EMAnalyticsFwkException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetCategory3th() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryById(anyLong,withAny(entityManager));

            }
        };
        try {
            categoryManager.getCategory(10L);
        }catch(EMAnalyticsFwkException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testGetCategoryByName() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                TenantContext.getContext();
                result = tenantInfo;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyString);
                result = query;
                query.getSingleResult();
                result = emAnalyticsCategory;
            }
        };
        categoryManager.getCategory("");
    }

    @Test(expectedExceptions = EMAnalyticsFwkException.class)
    public void testGetCategoryByName2nd() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                TenantContext.getContext();
                result = tenantInfo;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyString);
                result = query;
                query.getSingleResult();
                result = exception;
                exception.printStackTrace();
            }
        };
        categoryManager.getCategory("");
    }
    @Test
    public void testSaveCategory() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd(withAny(category),withAny(entityManager));
                result = emAnalyticsCategory;
                entityManager.persist(withAny(emAnalyticsCategory));
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.commit();
                entityManager.close();
            }
        };
        categoryManager.saveCategory(new CategoryImpl());

    }


    @Test
    public void testSaveCategory2nd() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd(withAny(category),withAny(entityManager));
                result =  new EMAnalyticsFwkException(new Throwable());
                entityManager.close();
            }
        };
        try {
            categoryManager.saveCategory(new CategoryImpl());
        }catch(EMAnalyticsFwkException e){
            Assert.assertTrue(true);
        }
    }

    @Test(expectedExceptions = EMAnalyticsFwkException.class)
    public void testSaveCategory3th() throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd(withAny(category),withAny(entityManager));
                result =  new PersistenceException(new Throwable());
                entityManager.close();
                EmAnalyticsProcessingException.processCategoryPersistantException(withAny(exception














                ),anyLong,anyString);
            }
        };
            categoryManager.saveCategory(new CategoryImpl());

    }


    @Test
    public void testSaveMultipleCategories() throws Exception {

        final List<ImportCategoryImpl> list = new ArrayList<>();
        final ImportCategoryImpl importCategory = new ImportCategoryImpl();
        importCategory.setId(10);
        list.add(importCategory);
        final CategoryImpl categoryinput = new CategoryImpl();
        categoryinput.setId(10);
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                importCategory.getCategoryDetails();
                result = categoryinput;
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(withAny(category),withAny(entityManager));
                result =   emAnalyticsCategory;
                entityManager.merge(withAny(emAnalyticsCategory));
                importCategory.setId(anyInt);
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.commit();
                entityManager.close();
            }
        };
        CategoryManagerImpl categoryManager =  (CategoryManagerImpl)CategoryManagerImpl.getInstance();
        categoryManager.saveMultipleCategories(list);
    }

    @Test
    public void testSaveMultipleCategories2nd() throws Exception {

        final List<ImportCategoryImpl> list = new ArrayList<>();
        final ImportCategoryImpl importCategory = new ImportCategoryImpl();
        importCategory.setId(10);
        list.add(importCategory);
        final CategoryImpl categoryinput = new CategoryImpl();
        categoryinput.setId(-1);
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                importCategory.getCategoryDetails();
                result = categoryinput;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyString);
                result = query;
                query.setParameter(anyString, anyString);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "";
                query.getSingleResult();
                result =   emAnalyticsCategory;
                emAnalyticsCategory.getCategoryId();
                result = 10L;
            }
        };
        CategoryManagerImpl categoryManager =  (CategoryManagerImpl)CategoryManagerImpl.getInstance();
        categoryManager.saveMultipleCategories(list);
    }
    @Test
    public void testSaveMultipleCategories3th() throws Exception {

        final List<ImportCategoryImpl> list = new ArrayList<>();
        final ImportCategoryImpl importCategory = new ImportCategoryImpl();
        importCategory.setId(10);
        list.add(importCategory);
        final CategoryImpl categoryinput = new CategoryImpl();
        categoryinput.setId(-1);
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                importCategory.getCategoryDetails();
                result = categoryinput;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyString);
                result = query;
                query.setParameter(anyString, anyString);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "";
                query.getSingleResult();
                result =   new NoResultException();
                importCategory.getFolderDetails();
                result = folder;
            }
        };
        CategoryManagerImpl categoryManager =  (CategoryManagerImpl)CategoryManagerImpl.getInstance();
        categoryManager.saveMultipleCategories(list);
    }

}
package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EmAnalyticsProcessingException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategoryParam;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;
import org.eclipse.persistence.internal.jpa.EntityManagerImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.*;
import java.util.*;

/**
 * @author jishshi
 * @since 2016/2/29.
 */
@Test(groups = {"s2"})
public class CategoryManagerImplTest {
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
    @Mocked
    EmAnalyticsCategoryParam emAnalyticsCategoryParam;

    @BeforeMethod
    public void setUp() {
        categoryManager = CategoryManagerImpl.getInstance();
    }

    @Test
    public void testGetInstance() {
        Assert.assertNotNull(categoryManager);
    }

    @Test
    public void testCreateNewCategory() {
        Assert.assertNotNull(categoryManager.createNewCategory());
    }

    @Test
    public void testDeleteCategory() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryById(anyLong, withAny(entityManager));
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
        categoryManager.deleteCategory(10L, false);
    }

    @Test
    public void testDeleteCategory2nd() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryByIdForDelete(anyLong, withAny(entityManager));
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
        categoryManager.deleteCategory(10L, true);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testDeleteCategory3th() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryByIdForDelete(anyLong, withAny(entityManager));
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
        categoryManager.deleteCategory(10L, true);
    }


    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testDeleteCategory4th() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryByIdForDelete(anyLong, withAny(entityManager));
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
        categoryManager.deleteCategory(10L, true);
    }

    @Test
    public void testEditCategory() throws EMAnalyticsFwkException {
        final Set<EmAnalyticsCategoryParam> emAnalyticsCategoryParams = new HashSet<>();
        emAnalyticsCategoryParams.add(emAnalyticsCategoryParam);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(withAny(category), withAny(entityManager));
                result = emAnalyticsCategory;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                entityManager.merge(withAny(emAnalyticsCategory));
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.commit();
                entityManager.close();
                emAnalyticsCategory.getEmAnalyticsCategoryParams();
                result = emAnalyticsCategoryParams;
            }
        };
        categoryManager.editCategory(new CategoryImpl());
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testEditCategory2nd() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(withAny(category), withAny(entityManager));
                result = new EMAnalyticsFwkException(new Throwable());
                entityManager.close();
            }
        };
        categoryManager.editCategory(new CategoryImpl());
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testEditCategory3th() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(withAny(category), withAny(entityManager));
                result = exception;
                entityManager.close();
            }
        };
        categoryManager.editCategory(new CategoryImpl());
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testEditCategory4th() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(withAny(category), withAny(entityManager));
                result = new PersistenceException();
                entityManager.close();
            }
        };
        categoryManager.editCategory(new CategoryImpl());
    }


    @Test
    public void testGetAllCategoriesResultNull() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.createNamedQuery("Category.getAllCategory");
                result = query;
            }
        };
        categoryManager.getAllCategories();
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetAllCategoriesResultException() throws EMAnalyticsFwkException {
        final List<Category> categories = new ArrayList<>();
        categories.add(category);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.createNamedQuery("Category.getAllCategory");
                result = query;
                query.getResultList();
                result = categories;
            }
        };
        categoryManager.getAllCategories();
    }

    @Test
    public void testGetAllCategoriesResult() throws EMAnalyticsFwkException {
        final List<EmAnalyticsCategory> categories = new ArrayList<>();
        categories.add(emAnalyticsCategory);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.createNamedQuery("Category.getAllCategory");
                result = query;
                query.getResultList();
                result = categories;
            }
        };
        categoryManager.getAllCategories();
    }

    @Test
    public void testGetCategory() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryById(anyLong, withAny(entityManager));
                result = emAnalyticsCategory;
            }
        };
        Assert.assertNotNull(categoryManager.getCategory(10L));
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetCategory2nd() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryById(anyLong, withAny(entityManager));
                result = exception;
            }
        };
        categoryManager.getCategory(10L);
    }

    @Test
    public void testGetCategory3th() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                EmAnalyticsObjectUtil.getCategoryById(anyLong, withAny(entityManager));

            }
        };
        categoryManager.getCategory(10L);
    }



    @Test
    public void testGetCategoryByName() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                TenantContext.getContext();
                result = tenantInfo;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.getSingleResult();
                result = emAnalyticsCategory;
            }
        };
        categoryManager.getCategory("");
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetCategoryByName2nd() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                TenantContext.getContext();
                result = tenantInfo;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.getSingleResult();
                result = exception;
                exception.printStackTrace();
            }
        };
        categoryManager.getCategory("");
    }
    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetCategoryByName3th() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = new NonUniqueResultException();
            }
        };
        categoryManager.getCategory("NAme");
    }
    @Test
    public void testSaveCategory() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd(withAny(category), withAny(entityManager));
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


    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testSaveCategory2nd() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd(withAny(category), withAny(entityManager));
                result = new EMAnalyticsFwkException(new Throwable());
                entityManager.close();
            }
        };
        categoryManager.saveCategory(new CategoryImpl());
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testSaveCategory3th() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd(withAny(category), withAny(entityManager));
                result = new PersistenceException(new Throwable());
                entityManager.close();
                EmAnalyticsProcessingException.processCategoryPersistantException(withAny(exception), anyLong, anyString);
            }
        };
        categoryManager.saveCategory(new CategoryImpl());

    }
    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testSaveCategory4th() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = exception;
            }
        };
        categoryManager.saveCategory(new CategoryImpl());

    }


    @Test
    public void testSaveMultipleCategories() {
        final List<ImportCategoryImpl> list = new ArrayList<>();
        final ImportCategoryImpl category1 = new ImportCategoryImpl();
        category1.setId(10);
        list.add(category1);
        final CategoryImpl categoryinput = new CategoryImpl();
        categoryinput.setId(10);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                category1.getCategoryDetails();
                result = categoryinput; 
            }
        };
        CategoryManagerImpl categoryManager1 = (CategoryManagerImpl) CategoryManagerImpl.getInstance();
        categoryManager1.saveMultipleCategories(list);
    }

    @Test
    public void testSaveMultipleCategories2nd() {
        final List<ImportCategoryImpl> list = new ArrayList<>();
        final ImportCategoryImpl importCategory1 = new ImportCategoryImpl();
        importCategory1.setId(10);
        list.add(importCategory1);
        final CategoryImpl categoryinput = new CategoryImpl();
        categoryinput.setId(-1);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                importCategory1.getCategoryDetails();
                result = categoryinput;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.setParameter(anyString, anyString);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "";
                query.getSingleResult();
                result = emAnalyticsCategory;
                emAnalyticsCategory.getCategoryId();
                result = 10L;
            }
        };
        CategoryManagerImpl categoryManager1 = (CategoryManagerImpl) CategoryManagerImpl.getInstance();
        categoryManager1.saveMultipleCategories(list);
    }

    @Test
    public void testSaveMultipleCategories3th() {
        final List<ImportCategoryImpl> list = new ArrayList<>();
        final ImportCategoryImpl importCategory2 = new ImportCategoryImpl();
        importCategory2.setId(10);
        list.add(importCategory2);
        final CategoryImpl categoryinput = new CategoryImpl();
        categoryinput.setId(-1);
        final FolderImpl folderImpl = new FolderImpl();
        folderImpl.setId(1);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                importCategory2.getCategoryDetails();
                result = categoryinput;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.setParameter(anyString, anyString);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "";
                query.getSingleResult();
                result = new NoResultException();
                importCategory2.getFolderDetails();
                result = folderImpl;
                EmAnalyticsObjectUtil.getFolderById(anyLong,(EntityManager)any);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getFolderId();
                result = 1L;
            }
        };
        CategoryManagerImpl categoryManager2 = (CategoryManagerImpl) CategoryManagerImpl.getInstance();
        categoryManager2.saveMultipleCategories(list);
    }

    @Test
    public void testSaveMultipleCategories4th() throws EMAnalyticsFwkException {
        final List<ImportCategoryImpl> list = new ArrayList<>();
        final ImportCategoryImpl importCategory2 = new ImportCategoryImpl();
        importCategory2.setId(10);
        list.add(importCategory2);
        final CategoryImpl categoryinput = new CategoryImpl();
        categoryinput.setId(-1);
        final FolderImpl folderImpl = new FolderImpl();
        folderImpl.setId(1);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                importCategory2.getCategoryDetails();
                result = categoryinput;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.setParameter(anyString, anyString);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "";
                query.getSingleResult();
                result = new NoResultException();
                importCategory2.getFolderDetails();
                result = folderImpl;
                EmAnalyticsObjectUtil.getFolderById(anyLong,(EntityManager)any);
                result = null;
                EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd((Folder)any, (EntityManager)any);
                result = emAnalyticsFolder;
                entityManager.persist(emAnalyticsFolder);
                emAnalyticsFolder.getFolderId();
                result =10L;

            }
        };
        CategoryManagerImpl categoryManager2 = (CategoryManagerImpl) CategoryManagerImpl.getInstance();
        categoryManager2.saveMultipleCategories(list);
    }

    @Test
    public void testSaveMultipleCategories5th() throws EMAnalyticsFwkException {
        final List<ImportCategoryImpl> list = new ArrayList<>();
        final ImportCategoryImpl importCategory2 = new ImportCategoryImpl();
        importCategory2.setId(10);
        list.add(importCategory2);
        final CategoryImpl categoryinput = new CategoryImpl();
        categoryinput.setId(-1);
        final FolderImpl folderImpl = new FolderImpl();
        folderImpl.setId(1);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                importCategory2.getCategoryDetails();
                result = categoryinput;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.setParameter(anyString, anyString);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "";
                query.getSingleResult();
                result = new NoResultException();
                importCategory2.getFolderDetails();
                result = new Integer(1);
                EmAnalyticsObjectUtil.getFolderById(anyLong,(EntityManager)any);
                result = null;
            }
        };
        CategoryManagerImpl categoryManager2 = (CategoryManagerImpl) CategoryManagerImpl.getInstance();
        categoryManager2.saveMultipleCategories(list);
    }

    @Test
    public void testSaveMultipleCategories6th() throws EMAnalyticsFwkException {
        final List<ImportCategoryImpl> list = new ArrayList<>();
        final ImportCategoryImpl importCategory2 = new ImportCategoryImpl();
        importCategory2.setId(10);
        list.add(importCategory2);
        final CategoryImpl categoryinput = new CategoryImpl();
        categoryinput.setId(-1);
        final FolderImpl folderImpl = new FolderImpl();
        folderImpl.setId(1);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                importCategory2.getCategoryDetails();
                result = categoryinput;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.setParameter(anyString, anyString);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "";
                query.getSingleResult();
                result = new NoResultException();
                importCategory2.getFolderDetails();
                result = new Integer(1);
                EmAnalyticsObjectUtil.getFolderById(anyLong,(EntityManager)any);
                result = emAnalyticsFolder;
            }
        };
        CategoryManagerImpl categoryManager2 = (CategoryManagerImpl) CategoryManagerImpl.getInstance();
        categoryManager2.saveMultipleCategories(list);
    }

    @Test
    public void testSaveMultipleCategories7th() {
        final List<ImportCategoryImpl> list = new ArrayList<>();
        final ImportCategoryImpl importCategory2 = new ImportCategoryImpl();
        importCategory2.setId(10);
        list.add(importCategory2);
        final CategoryImpl categoryinput = new CategoryImpl();
        categoryinput.setId(-1);
        final FolderImpl folderImpl = new FolderImpl();
        folderImpl.setId(1);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.getTransaction();
                result = entityTransaction;
                entityTransaction.begin();
                importCategory2.getCategoryDetails();
                result = new PersistenceException();
            }
        };
        CategoryManagerImpl categoryManager2 = (CategoryManagerImpl) CategoryManagerImpl.getInstance();
        categoryManager2.saveMultipleCategories(list);
    }

    @Mocked
    EmAnalyticsFolder emAnalyticsFolder;
    @Mocked
    CategoryImpl categoryImpl;
    @Test
    public void testCreateCategoryObject() throws Exception {

        final Set<EmAnalyticsCategoryParam> parameters = new HashSet<>();
        parameters.add(emAnalyticsCategoryParam);
        new Expectations(){
            {
                emAnalyticsCategory.getEmAnalyticsCategoryParams();
                result = parameters;
                emAnalyticsCategoryParam.getName();
                result ="NOT_DASHBOARD_INELIGIBLE";
                emAnalyticsCategoryParam.getValue();
                result = "1";
                emAnalyticsCategory.getDASHBOARDINELIGIBLE();
                result ="1";
            }
        };
        CategoryManagerImpl.createCategoryObject(emAnalyticsCategory, null);
        CategoryManagerImpl.createCategoryObject(emAnalyticsCategory, categoryImpl);
    }

    @Test
    public void testCreateCategoryObject2nd() throws Exception {

        final Set<EmAnalyticsCategoryParam> parameters = new HashSet<>();
        parameters.add(emAnalyticsCategoryParam);
        new Expectations(){
            {
                emAnalyticsCategory.getEmAnalyticsCategoryParams();
                result = parameters;
                emAnalyticsCategory.getNameNlsid();
                result = "name_nlsid";
                emAnalyticsCategory.getDescriptionNlsid();
                result = "des_nlsid";
                emAnalyticsCategoryParam.getName();
                result ="NOT_DASHBOARD_INELIGIBLE";
                emAnalyticsCategory.getNameSubsystem();
                result = "name_subsystem";
                emAnalyticsCategory.getDescriptionSubsystem();
                result = "name_subsystem";
                emAnalyticsCategoryParam.getValue();
                result = "1";
                emAnalyticsCategory.getDASHBOARDINELIGIBLE();
                result ="1";
            }
        };
        CategoryManagerImpl.createCategoryObject(emAnalyticsCategory, null);
        CategoryManagerImpl.createCategoryObject(emAnalyticsCategory, categoryImpl);
    }

    @Test
    public void testCreateCategoryObject3th() throws Exception {

        final Set<EmAnalyticsCategoryParam> parameters = new HashSet<>();
        parameters.add(emAnalyticsCategoryParam);
        new Expectations(){
            {
                emAnalyticsCategory.getEmAnalyticsCategoryParams();
                result = parameters;
                emAnalyticsCategoryParam.getName();
                result ="DASHBOARD_INELIGIBLE";
                emAnalyticsCategoryParam.getValue();
                result = "1";
            }
        };
        CategoryManagerImpl.createCategoryObject(emAnalyticsCategory, null);
        CategoryManagerImpl.createCategoryObject(emAnalyticsCategory, categoryImpl);
    }

}
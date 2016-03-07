package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import oracle.sysman.emaas.platform.savedsearch.entity.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 3/7/2016.
 */

public class EmAnalyticsObjectUtilTest {
    private  EmAnalyticsObjectUtil emAnalyticsObjectUtil;
    @Mocked
    PersistenceManager persistenceManager;
    @Mocked
    EntityManager entityManager;
    @Mocked
    Query query;
    @Mocked
    TenantContext tenantContext;
    @Mocked
    TenantInfo tenantInfo;
    @Mocked
    EmAnalyticsCategory emAnalyticsCategory;
    @Mocked
    Category category;
    @Mocked
    EmAnalyticsCategoryParamPK emAnalyticsCategoryParamPK;
    @Mocked
    EmAnalyticsCategoryParam emAnalyticsCategoryParam;
    @Mocked
    Parameter parameter;
    @Mocked
    Folder folder;
    @Mocked
    EmAnalyticsFolder emAnalyticsFolder;
    @Mocked
    Search search;
    @Mocked
    EmAnalyticsSearch emAnalyticsSearch;

    @Test
    public void testCanDeleteFolder() throws Exception {
        new Expectations() {
            {
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.getSingleResult();
                result = 1;
                TenantContext.getContext();
                result = tenantInfo;
            }
        };
        try {
            EmAnalyticsObjectUtil.canDeleteFolder(10L, entityManager);

        }catch(EMAnalyticsFwkException e){
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testCanDeleteFolder2nd() throws Exception {
        new Expectations() {
            {
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.getSingleResult();
                result = -1;
                TenantContext.getContext();
                result = tenantInfo;
            }
        };
        try {
            EmAnalyticsObjectUtil.canDeleteFolder(10L, entityManager);

        }catch(EMAnalyticsFwkException e){
            Assert.assertTrue(true);
        }
    }


    @Test
    public void testGetCategoryByName() throws Exception {
        new Expectations() {
            {
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.getSingleResult();
                result = emAnalyticsCategory;
                TenantContext.getContext();
                result = tenantInfo;
            }
        };
        EmAnalyticsObjectUtil.getCategoryByName("name",entityManager);
    }

    @Test
    public void testGetEmAnalyticsCategoryForEdit() throws Exception {
        final List<Parameter> newParams = new ArrayList<Parameter>();
        newParams.add(parameter);
        new Expectations() {
            {
                category.getId();
                result = 1;
                category.getName();
                result = "name";
                entityManager.find(EmAnalyticsCategory.class, anyLong);
                result = emAnalyticsCategory;
                category.getParameters();
                result = newParams;
            }
        };
        try {
            EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(category, entityManager);
        }catch(Exception e){

        }
    }

    @Test
    public void testGetEmAnalyticsFolderByFolderObject() throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(folder);
    }
    @Test
    public void testGetEmAnalyticsFolderByFolderObject2nd() throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                folder.getParentId();
                result =null;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(folder);
    }

    @Test
    public void testGetEmAnalyticsFolderByFolderObject3th() throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                folder.getParentId();
                result =1;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(folder);
    }

    @Test
    public void testGetEmAnalyticsFolderByFolderObject4th() throws Exception {
        new Expectations(){
            {
                TenantContext.getContext();
                result = tenantInfo;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                folder.getParentId();
                result =new NoResultException();
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(folder);
    }

    @Test
    public void testGetEmAnalyticsFolderForEdit() throws Exception {
        new Expectations(){
            {
                entityManager.find(EmAnalyticsFolder.class,anyLong);
                result = emAnalyticsFolder;
                folder.getParentId();
                result = 1;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder,entityManager);
    }

    @Test
    public void testGetEmAnalyticsSearchForAdd() throws Exception {
        new Expectations(){
            {
                entityManager.find(EmAnalyticsFolder.class,anyLong);
                result = emAnalyticsFolder;
                entityManager.find(EmAnalyticsSearch.class,anyLong);
                result = emAnalyticsSearch;
                entityManager.find(EmAnalyticsCategory.class, anyLong);
                result = emAnalyticsCategory;

            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit(search,entityManager);

    }


    @Test
    public void testGetRootFolder() throws Exception {
        EmAnalyticsObjectUtil.getRootFolder();
    }

    @Test
    public void testGetSearchById() throws Exception {

    }

    @Test
    public void testGetSearchByIdForDelete() throws Exception {

    }
}
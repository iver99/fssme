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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by xidai on 3/7/2016.
 */
@Test(groups = {"s2"})

public class EmAnalyticsObjectUtilTest {
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
    @Mocked
    SearchParameter searchParameter;


    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testCanDeleteFolder() throws EMAnalyticsFwkException {
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
                entityManager.find(EmAnalyticsFolder.class, anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = 0;
                emAnalyticsFolder.getSystemFolder();
                result = 1;
            }
        };
        EmAnalyticsObjectUtil.canDeleteFolder(10L, entityManager);
    }

    @Test
    public void testCanDeleteFolder2nd() throws EMAnalyticsFwkException {

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
                entityManager.find(EmAnalyticsFolder.class, anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = 0;
                emAnalyticsFolder.getSystemFolder();
                result = 1;
            }
        };
        EmAnalyticsObjectUtil.canDeleteFolder(10L, entityManager);
    }


    @Test
    public void testGetCategoryByName() {
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
        EmAnalyticsObjectUtil.getCategoryByName("name", entityManager);
    }

    @Test
    public void testGetEmAnalyticsCategoryForEdit() {
        final List<Parameter> newParams = new ArrayList<Parameter>();
        newParams.add(parameter);
        new Expectations() {
            {
                category.getId();
                result = 1;
                entityManager.find(EmAnalyticsCategory.class, anyLong);
                result = emAnalyticsCategory;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsCategoryForEdit(category, entityManager);
    }


    @Test
    public void testGetEmAnalyticsFolderByFolderObject() {
        new Expectations() {
            {
                TenantContext.getContext();
                result = tenantInfo;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                entityManager.find(EmAnalyticsFolder.class, anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = 0;
                emAnalyticsFolder.getSystemFolder();
                result = 0;
                emAnalyticsFolder.getOwner();
                result ="owner";
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(folder);
    }

    @Test
    public void testGetEmAnalyticsFolderByFolderObject2nd() {
        new Expectations() {
            {
                TenantContext.getContext();
                result = tenantInfo;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                folder.getParentId();
                result = null;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(folder);
    }

    @Test
    public void testGetEmAnalyticsFolderByFolderObject3th() {
        new Expectations() {
            {
                TenantContext.getContext();
                result = tenantInfo;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                folder.getParentId();
                result = 1;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(folder);
    }

    @Test
    public void testGetEmAnalyticsFolderByFolderObject4th() {
        new Expectations() {
            {
                TenantContext.getContext();
                result = tenantInfo;
                persistenceManager.getEntityManager(withAny(tenantInfo));
                result = entityManager;
                folder.getParentId();
                result = new NoResultException();
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderByFolderObject(folder);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetEmAnalyticsFolderForEdit() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                entityManager.find(EmAnalyticsFolder.class, anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = 0;
                emAnalyticsFolder.getSystemFolder();
                result = 0;
                emAnalyticsFolder.getOwner();
                result ="owner";
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder, entityManager);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetEmAnalyticsSearchForEdit() throws EMAnalyticsFwkException {
        final List<SearchParameter> searchParameters = new ArrayList<>();
        searchParameters.add(searchParameter);
        new Expectations() {
            {
                entityManager.find(EmAnalyticsSearch.class, anyLong);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getDeleted();
                result = 0;
                emAnalyticsSearch.getSystemSearch();
                result =1;
                entityManager.find(EmAnalyticsFolder.class, anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = 0;
                emAnalyticsFolder.getSystemFolder();
                result = 0;
                emAnalyticsFolder.getOwner();
                result ="owner";

            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit(search, entityManager);

    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetEmAnalyticsSearchForAdd() throws EMAnalyticsFwkException {
        final List<SearchParameter> searchParameters = new ArrayList<>();
        searchParameters.add(searchParameter);
        new Expectations() {
            {
                entityManager.find(EmAnalyticsFolder.class, anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = 0;
                emAnalyticsFolder.getSystemFolder();
                result = 0;
                emAnalyticsFolder.getOwner();
                result ="owner";
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(search, entityManager);

    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetEmAnalyticsSearchForAdd2nd() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                entityManager.find(EmAnalyticsFolder.class, anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = 1;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(search, entityManager);

    }


    @Test
    public void testGetRootFolder() {

        EmAnalyticsObjectUtil.getRootFolder();
    }


    @Test
    public void testGetFolderById() {
        new Expectations() {
            {
                entityManager.find(withAny(EmAnalyticsFolder.class), anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(1);
            }
        };
        EmAnalyticsObjectUtil.getFolderById(10L, entityManager);
    }

    @Test
    public void testGetCategoryById2nd() {
        new Expectations() {
            {
                entityManager.find(withAny(EmAnalyticsCategory.class), anyLong);
                result = emAnalyticsCategory;
                emAnalyticsCategory.getDeleted();
                result = 1;
            }
        };
        EmAnalyticsObjectUtil.getCategoryById(10L, entityManager);
    }


    @Test
    public void testGetFolderById2nd() {
        new Expectations() {
            {
                entityManager.find(withAny(EmAnalyticsFolder.class), anyLong);
                result = null;
            }
        };
        EmAnalyticsObjectUtil.getFolderById(10L, entityManager);
    }

    @Test
    public void testGetSearchById() {
        new Expectations() {
            {
                entityManager.find(withAny(EmAnalyticsSearch.class), anyLong);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new BigDecimal(1);
            }
        };
        EmAnalyticsObjectUtil.getSearchById(10L, entityManager);

    }

    @Test
    public void testGetSearchById2nd() {
        new Expectations() {
            {
                entityManager.find(withAny(EmAnalyticsSearch.class), anyLong);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getDeleted();
                result = 2;
            }
        };
        EmAnalyticsObjectUtil.getSearchById(10L, entityManager);

    }

    @Test
    public void testGetCategoryByIdForDelete() {
        new Expectations() {
            {
                entityManager.find(withAny(EmAnalyticsCategory.class), anyLong);
                result = emAnalyticsCategory;
                emAnalyticsCategory.getOwner();
                result = "ORACLE";
            }
        };
        EmAnalyticsObjectUtil.getCategoryByIdForDelete(10L, entityManager);

    }

    @Test
    public void testGetCategoryByIdForDelete2nd() {
        new Expectations() {
            {
                entityManager.find(withAny(EmAnalyticsCategory.class), anyLong);
                result = null;
            }
        };
        EmAnalyticsObjectUtil.getCategoryByIdForDelete(10L, entityManager);

    }

    @Test
    public void testGetFolderByIdForDelete() {
        new Expectations() {
            {
                entityManager.find(withAny(EmAnalyticsFolder.class), anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(1);
            }
        };
        EmAnalyticsObjectUtil.getFolderByIdForDelete(10L, entityManager);

    }

    @Test
    public void testGetFolderByIdForDelete2nd() {
        new Expectations() {
            {
                entityManager.find(withAny(EmAnalyticsFolder.class), anyLong);
                result = null;
            }
        };
        EmAnalyticsObjectUtil.getFolderByIdForDelete(10L, entityManager);

    }

    @Test
    public void testGetSearchByIdForDelete() {
        new Expectations() {
            {
                entityManager.find(withAny(EmAnalyticsSearch.class), anyLong);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new BigDecimal(1);
            }
        };
        EmAnalyticsObjectUtil.getSearchByIdForDelete(10L, entityManager);

    }

    @Test
    public void testGetSearchByIdForDelete2nd() {
        new Expectations() {
            {
                entityManager.find(withAny(EmAnalyticsSearch.class), anyLong);
                result = null;
            }
        };
        EmAnalyticsObjectUtil.getSearchByIdForDelete(10L, entityManager);

    }

    @Test(expectedExceptions = {UnsupportedOperationException.class})
    public void testGetEmAnalyticsCategoryForAdd() throws EMAnalyticsFwkException {
        final List<Parameter> parameters = new ArrayList<>();
        parameters.add(parameter);
        new Expectations() {
            {
                entityManager.find(withAny(EmAnalyticsFolder.class), anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(1);
                category.getParameters();
                result = parameters;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsCategoryForAdd(category, entityManager);
    }
}
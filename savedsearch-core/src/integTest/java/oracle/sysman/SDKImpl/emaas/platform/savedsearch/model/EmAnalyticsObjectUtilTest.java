package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.Expectations;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.StrictExpectations;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import oracle.sysman.emaas.platform.savedsearch.entity.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.*;

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
    Folder folder;
    @Mocked
    EmAnalyticsFolder emAnalyticsFolder;
    @Mocked
    Search search;
    @Mocked
    EmAnalyticsSearch emAnalyticsSearch;



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

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testCanDeleteFolder3th() throws EMAnalyticsFwkException {
        final List<EmAnalyticsFolder>  emAnalyticsFolders = new ArrayList<>();
        emAnalyticsFolders.add(emAnalyticsFolder);
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
                tenantInfo.getUsername();
                result = "userName";
                query.getResultList();
                result = emAnalyticsFolders;
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

    @Mocked
    ExecutionContext executionContext;

    @Test
    public void testGetEmAnalyticsCategoryForEdit() {
        final List<Parameter> newParams = new ArrayList<Parameter>();
        Parameter parameter = new Parameter();
        parameter.setName("param_name");
        newParams.add(parameter);
        final Set<EmAnalyticsCategoryParam> emAnalyticsCategoryParams = new HashSet<>();
        emAnalyticsCategoryParams.add(emAnalyticsCategoryParam);
        new Expectations() {
            {
                category.getId();
                result = 1L;
                entityManager.find(EmAnalyticsCategory.class, anyLong);
                result = emAnalyticsCategory;
                emAnalyticsCategory.getDeleted();
                result = 0L;
                entityManager.refresh(emAnalyticsCategory);
                category.getName();
                result = "category_name";
                category.getDescription();
                result = "category_description";
                ExecutionContext.getExecutionContext();
                result = executionContext;
                executionContext.getCurrentUser();
                result = "current_user";
                category.getProviderName();
                result = "provider_name";
                category.getProviderVersion();
                result = "provider_vsersion";
                category.getProviderDiscovery();
                result = "provider_discovery";
                category.getProviderAssetRoot();
                result = "provider_assetroot";
                category.getDefaultFolderId();
                result = 1;
                entityManager.find(EmAnalyticsFolder.class, anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(1);
                category.getParameters();
                result = newParams;
                category.getId();
                result = 1L;
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1L;
                emAnalyticsCategory.getEmAnalyticsCategoryParams();
                result = emAnalyticsCategoryParams;
                emAnalyticsCategoryParam.getCategoryId();
                result = 2L;
                emAnalyticsCategoryParam.getName();
                result = "DASHBOARD_INELIGIBLE";
                emAnalyticsCategoryParam.getValue();
                result = "1";
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
        SearchParameter searchParameter = new SearchParameter();
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
        SearchParameter searchParameter = new SearchParameter();
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
        Parameter parameter = new Parameter();
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

    @Test
    public void testGetEmAnalyticsFolderForAdd() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                folder.getDescription();
                result = "folder_des";
                ExecutionContext.getExecutionContext();
                result = executionContext;
                executionContext.getCurrentUser();
                result = "current_user";
                folder.getParentId();
                result = 1;
                entityManager.find(EmAnalyticsFolder.class, (EntityManager)any);
                result = emAnalyticsFolder;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd(folder, entityManager);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testGetEmAnalyticsFolderForAddEMAnalyticsFwkException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                folder.getDescription();
                result = "folder_des";
                ExecutionContext.getExecutionContext();
                result = executionContext;
                executionContext.getCurrentUser();
                result = "current_user";
                folder.getParentId();
                result = 1;
                entityManager.find(EmAnalyticsFolder.class, (EntityManager)any);
                result = null;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderForAdd(folder, entityManager);
    }

    @Test
    public void testgetEmAnalyticsFolderForEdit() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                folder.getId();
                result = 1;
                entityManager.find(EmAnalyticsFolder.class, (EntityManager)any);
                result = emAnalyticsFolder;
                folder.getName();
                result = "folder_name";
                folder.getDescription();
                result = "folder_des";
                ExecutionContext.getExecutionContext();
                result = executionContext;
                executionContext.getCurrentUser();
                result = "current_user";
                folder.isSystemFolder();
                result = false;
                folder.isUiHidden();
                result = false;
                folder.getParentId();
                result = 1;
                emAnalyticsFolder.getSystemFolder();
                result =1;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder, entityManager);
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testgetEmAnalyticsFolderForEditEMAnalyticsFwkException() throws EMAnalyticsFwkException {
        new Expectations(){
            {
                folder.getId();
                result = 1;
                entityManager.find(EmAnalyticsFolder.class, (EntityManager)any);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = 1;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsFolderForEdit(folder, entityManager);
    }

    @Test
    public void testGetEmAnalyticsSearchForAddCC() throws EMAnalyticsFwkException {
        final List<SearchParameter> params = new ArrayList<>();
        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setName("new Param");
        searchParameter.setAttributes("attributes");
        searchParameter.setType(ParameterType.CLOB);
        params.add(searchParameter);
        final Set<EmAnalyticsSearchParam> emAnalyticsSearchParams = new HashSet<>();
        new Expectations(){
            {
                search.getName();
                result = "sarch_name";
                search.getDescription();
                result = "description";
                search.getFolderId();
                result = 1;
                entityManager.find(EmAnalyticsFolder.class, (EntityManager)any);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = 0;
                emAnalyticsFolder.getSystemFolder();
                result = 1;
                search.getCategoryId();
                result = 1;
                entityManager.find(EmAnalyticsCategory.class, (EntityManager)any);
                result = emAnalyticsCategory;
                ExecutionContext.getExecutionContext();
                result = executionContext;
                executionContext.getCurrentUser();
                result = "current_user";
                search.isLocked();
                result =false;
                search.getMetadata();
                result = "metaData";
                search.getQueryStr();
                result = "queryString";
                search.isUiHidden();
                result = false;
                search.getIsWidget();
                result = false;
                search.getParameters();
                result = params;
                emAnalyticsSearch.getEmAnalyticsSearchParams();
                result = emAnalyticsSearchParams;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(search, entityManager);
    }

    @Test
    public void testGetEmAnalyticsSearchForAddParametrs() throws EMAnalyticsFwkException {
        final List<SearchParameter> params = new ArrayList<>();
        for(int i =0; i<=14; i++){
            params.add(new SearchParameter());
        }
        params.get(0).setName("NAME_WIDGET_SOURCE");
        params.get(1).setName("WIDGET_GROUP_NAME");
        params.get(2).setName("WIDGET_SCREENSHOT_HREF");
        params.get(3).setName("WIDGET_ICON");
        params.get(4).setName("WIDGET_KOC_NAME");
        params.get(5).setName("WIDGET_VIEWMODEL");
        params.get(6).setName("WIDGET_TEMPLATE");
        params.get(7).setName("WIDGET_SUPPORT_TIME_CONTROL");
        params.get(8).setName("WIDGET_LINKED_DASHBOARD");
        params.get(8).setValue("1");
        params.get(9).setName("WIDGET_DEFAULT_WIDTH");
        params.get(9).setValue("1");
        params.get(10).setName("WIDGET_DEFAULT_HEIGHT");
        params.get(10).setValue("1");
        params.get(11).setName("DASHBOARD_INELIGIBLE");
        params.get(11).setValue("1");
        params.get(12).setName("PROVIDER_NAME");
        params.get(13).setName("PROVIDER_VERSION");
        params.get(14).setName("PROVIDER_ASSET_ROOT");
        final Set<EmAnalyticsSearchParam> emAnalyticsSearchParams = new HashSet<>();
        new Expectations(){
            {
                search.getName();
                result = "sarch_name";
                search.getDescription();
                result = "description";
                search.getFolderId();
                result = 1;
                entityManager.find(EmAnalyticsFolder.class, (EntityManager)any);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getDeleted();
                result = 0;
                emAnalyticsFolder.getSystemFolder();
                result = 1;
                search.getCategoryId();
                result = 1;
                entityManager.find(EmAnalyticsCategory.class, (EntityManager)any);
                result = emAnalyticsCategory;
                ExecutionContext.getExecutionContext();
                result = executionContext;
                executionContext.getCurrentUser();
                result = "current_user";
                search.isLocked();
                result =false;
                search.getMetadata();
                result = "metaData";
                search.getQueryStr();
                result = "queryString";
                search.isUiHidden();
                result = false;
                search.getIsWidget();
                result = false;
                search.getParameters();
                result = params;
            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd(search, entityManager);
    }

    @Test
    public void testGetEmAnalyticsSearchForEditCC() throws EMAnalyticsFwkException {
        final List<SearchParameter> params = new ArrayList<>();
        SearchParameter searchParameter = new SearchParameter();
        searchParameter.setName("new Param");
        searchParameter.setAttributes("attributes");
        searchParameter.setType(ParameterType.CLOB);
        params.add(searchParameter);
        final Set<EmAnalyticsSearchParam> emAnalyticsSearchParams = new HashSet<>();
        new Expectations(){
            {
                search.getId();
                result = 1;
                entityManager.find(EmAnalyticsSearch.class, anyLong);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new BigDecimal(1);
                entityManager.refresh(emAnalyticsSearch);
                search.getMetadata();
                result = "metaData";
                search.getName();
                result = "search_name";
                search.getDescription();
                result = "search_des";
                search.getFolderId();
                result = 1;
                entityManager.find(EmAnalyticsFolder.class, anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(1);
                search.getCategoryId();
                result = 1;
                entityManager.find(EmAnalyticsCategory.class, anyLong);
                result = emAnalyticsCategory;
                ExecutionContext.getExecutionContext();
                result = executionContext;
                executionContext.getCurrentUser();
                result = "current_user";
                search.getQueryStr();
                result = "query_str";
                search.getIsWidget();
                result = false;
                search.getParameters();
                result = params;
                emAnalyticsSearch.getEmAnalyticsSearchParams();
                result = emAnalyticsSearchParams;
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1L;

            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit(search, entityManager);
    }

    @Test
    public void testMoveParamsToSearchEdit() throws EMAnalyticsFwkException {
        final List<SearchParameter> params = new ArrayList<>();
        for(int i =0; i<=14; i++){
            params.add(new SearchParameter());
        }
        params.get(0).setName("NAME_WIDGET_SOURCE");
        params.get(0).setType(ParameterType.STRING);
        params.get(1).setName("WIDGET_GROUP_NAME");
        params.get(1).setType(ParameterType.STRING);
        params.get(2).setName("WIDGET_SCREENSHOT_HREF");
        params.get(2).setType(ParameterType.STRING);
        params.get(3).setName("WIDGET_ICON");
        params.get(3).setType(ParameterType.STRING);
        params.get(4).setName("WIDGET_KOC_NAME");
        params.get(4).setType(ParameterType.STRING);
        params.get(5).setName("WIDGET_VIEWMODEL");
        params.get(5).setType(ParameterType.STRING);
        params.get(6).setName("WIDGET_TEMPLATE");
        params.get(6).setType(ParameterType.STRING);
        params.get(7).setName("WIDGET_SUPPORT_TIME_CONTROL");
        params.get(7).setType(ParameterType.STRING);
        params.get(8).setName("WIDGET_LINKED_DASHBOARD");
        params.get(8).setType(ParameterType.STRING);
        params.get(8).setValue("1");
        params.get(9).setName("WIDGET_DEFAULT_WIDTH");
        params.get(9).setValue("1");
        params.get(9).setType(ParameterType.STRING);
        params.get(10).setName("WIDGET_DEFAULT_HEIGHT");
        params.get(10).setValue("1");
        params.get(10).setType(ParameterType.STRING);
        params.get(11).setName("DASHBOARD_INELIGIBLE");
        params.get(11).setValue("1");
        params.get(11).setType(ParameterType.STRING);
        params.get(12).setName("PROVIDER_NAME");
        params.get(12).setType(ParameterType.STRING);
        params.get(13).setName("PROVIDER_VERSION");
        params.get(13).setType(ParameterType.STRING);
        params.get(14).setName("PROVIDER_ASSET_ROOT");
        params.get(14).setType(ParameterType.STRING);
        final Set<EmAnalyticsSearchParam> emAnalyticsSearchParams = new HashSet<>();
        EmAnalyticsSearchParam emAnalyticsSearchParam;
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("NAME_WIDGET_SOURCE");
        emAnalyticsSearchParam.setParamValueStr("value_str");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("WIDGET_GROUP_NAME");
        emAnalyticsSearchParam.setParamValueStr("value_str");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("WIDGET_SCREENSHOT_HREF");
        emAnalyticsSearchParam.setParamValueStr("value_str");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("WIDGET_ICON");
        emAnalyticsSearchParam.setParamValueStr("value_str");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("WIDGET_KOC_NAME");
        emAnalyticsSearchParam.setParamValueStr("value_str");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("WIDGET_VIEWMODEL");
        emAnalyticsSearchParam.setParamValueStr("value_str");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("WIDGET_TEMPLATE");
        emAnalyticsSearchParam.setParamValueStr("value_str");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("WIDGET_SUPPORT_TIME_CONTROL");
        emAnalyticsSearchParam.setParamValueStr("value_str");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("WIDGET_LINKED_DASHBOARD");
        emAnalyticsSearchParam.setParamValueStr("1");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("WIDGET_DEFAULT_WIDTH");
        emAnalyticsSearchParam.setParamValueStr("1");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("WIDGET_DEFAULT_HEIGHT");
        emAnalyticsSearchParam.setParamValueStr("1");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("DASHBOARD_INELIGIBLE");
        emAnalyticsSearchParam.setParamValueStr("1");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("PROVIDER_NAME");
        emAnalyticsSearchParam.setParamValueStr("value_str");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("PROVIDER_VERSION");
        emAnalyticsSearchParam.setParamValueStr("value_str");
        emAnalyticsSearchParams.add(emAnalyticsSearchParam);
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        emAnalyticsSearchParam.setName("PROVIDER_ASSET_ROOT");
        emAnalyticsSearchParam.setParamValueStr("value_str");
        emAnalyticsSearchParam = new EmAnalyticsSearchParam();
        new Expectations(){
            {
                search.getId();
                result = 1;
                entityManager.find(EmAnalyticsSearch.class, anyLong);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new BigDecimal(1);
                entityManager.refresh(emAnalyticsSearch);
                search.getMetadata();
                result = "metaData";
                search.getName();
                result = "search_name";
                search.getDescription();
                result = "search_des";
                search.getFolderId();
                result = 1;
                entityManager.find(EmAnalyticsFolder.class, anyLong);
                result = emAnalyticsFolder;
                emAnalyticsFolder.getSystemFolder();
                result = new BigDecimal(1);
                search.getCategoryId();
                result = 1;
                entityManager.find(EmAnalyticsCategory.class, anyLong);
                result = emAnalyticsCategory;
                ExecutionContext.getExecutionContext();
                result = executionContext;
                executionContext.getCurrentUser();
                result = "current_user";
                search.getQueryStr();
                result = "query_str";
                search.getIsWidget();
                result = false;
                search.getParameters();
                result = params;
                emAnalyticsSearch.getEmAnalyticsSearchParams();
                result = emAnalyticsSearchParams;
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getTenantInternalId();
                result = 1L;

            }
        };
        EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit(search, entityManager);
    }

    @Test
    public void testGetSearchParamByName(){
        final List<EmAnalyticsSearchParam> params = new ArrayList<>();
        params.add(new EmAnalyticsSearchParam());
        params.get(0).setName("NAME_WIDGET_SOURCE");
        params.get(0).setParamType(new BigDecimal("0"));
        new Expectations(){
            {
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyLong);
                result = query;
                query.setParameter(anyString, anyString);
                result = query;
                query.getResultList();
                result = params;
            }
        };
        EmAnalyticsObjectUtil.getSearchParamByName(1L, "NAME_WIDGET_SOURCE", entityManager);
    }
}
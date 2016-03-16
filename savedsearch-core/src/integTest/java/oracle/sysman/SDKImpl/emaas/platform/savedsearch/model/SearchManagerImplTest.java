package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsLastAccess;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qianqi
 * @since 16-3-2.
 */
@Test(groups = {"s2"})
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
    public void testDeleteSearch_mocked(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final EmAnalyticsSearch emAnalyticsSearch) throws Exception {
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
        searchManager.deleteSearch(1234,false);
    }

    @Test
    public void testDeleteSearch_mockednull(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final EmAnalyticsSearch emAnalyticsSearch) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo)any);
                result = entityManager;
                EmAnalyticsObjectUtil.getSearchByIdForDelete(anyLong,entityManager);
                result = null;

            }
        };
        try {
            searchManager.deleteSearch(1234,true);

        }catch (Exception e){

        }
        try{
            searchManager.deleteSearch(1234,false);
        }catch (Exception e){

        }
    }

    @Test
    public void testDeleteSearch_exception(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final EmAnalyticsSearch emAnalyticsSearch) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo)any);
                result = entityManager;
                EmAnalyticsObjectUtil.getSearchByIdForDelete(anyLong,entityManager);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new Exception();
                emAnalyticsSearch.getName();
                result = "namexx";

            }
        };
        try{
            searchManager.deleteSearch(1234,true);
        }catch (Exception e){

        }
    }


    @Test
    public void testEditSearch_EMAnalyticsFwkException(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final EmAnalyticsSearch emAnalyticsSearch) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo)any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit((Search)any,entityManager);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new BigDecimal(1);

            }
        };
        try {
            searchManager.editSearch(new SearchImpl());
        }catch (Exception e){

        }
    }

    @Test
    public void testEditSearch_normal(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final EmAnalyticsSearch emAnalyticsSearch) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo)any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit((Search)any,entityManager);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new BigDecimal(10);

            }
        };
            searchManager.editSearch(new SearchImpl());
    }

    @Test
    public void testEditSearch_PersistenceException(@Mocked final PersistenceManager persistenceManager) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = new PersistenceException();
            }
        };
        try{
            searchManager.editSearch(new SearchImpl());
        }catch (Exception e){

        }
    }

    @Test
    public void testEditSearch_Exception(@Mocked final PersistenceManager persistenceManager) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = new Exception();
            }
        };
        try{
            searchManager.editSearch(new SearchImpl());
        }catch (Exception e){

        }
    }

    @Test
    public void testGetSearch(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final EmAnalyticsSearch emAnalyticsSearch) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo)any);
                result = entityManager;
                EmAnalyticsObjectUtil.getSearchById(anyLong,entityManager);
                result = emAnalyticsSearch;
                entityManager.refresh((EmAnalyticsSearch)any);

            }
        };
        searchManager.getSearch(1234);
    }

    @Test
    public void testGetSearchByName_normal(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createNamedQuery("Search.getSearchByName");
                result = query;
                query.setParameter(anyString, anyLong);
                result = query;
                query.getSingleResult();
                result = emAnalyticsSearch;
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
            }
        };
        searchManager.getSearchByName("name",1234);
    }

    @Test
    public void testGetSearchByName_NoResultException(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createNamedQuery("Search.getSearchByName");
                result = query;
                query.setParameter(anyString, anyLong);
                result = query;
                query.getSingleResult();
                result = new NoResultException();
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
            }
        };
        searchManager.getSearchByName("name",1234);
    }

    @Test
    public void testGetSearchByName_Exception(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createNamedQuery("Search.getSearchByName");
                result = query;
                query.setParameter(anyString, anyLong);
                result = query;
                query.getSingleResult();
                result = new Exception();
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
            }
        };
        try {
            searchManager.getSearchByName("name", 1234);
        }catch (Exception e){

        }
    }

    @Test
    public void testGetSearchCountByFolderId() throws Exception {
        try{
            searchManager.getSearchCountByFolderId(1234);
        }catch (Exception e){

        }
    }

    @Test
    public void testGetSearchListByCategoryId(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        List<EmAnalyticsSearch> searchList = new ArrayList<>();
        searchList.add(emAnalyticsSearch);
        searchList.add(emAnalyticsSearch);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyLong);
                result = query;
                query.getResultList();
                result = emAnalyticsSearch;
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
            }
        };
        searchManager.getSearchListByCategoryId(1234);
    }

    @Test
    public void testGetSearchListByCategoryId_Exception(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        List<EmAnalyticsSearch> searchList = new ArrayList<>();
        searchList.add(emAnalyticsSearch);
        searchList.add(emAnalyticsSearch);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyLong);
                result = query;
                query.getResultList();
                result = emAnalyticsSearch;
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
                entityManager.refresh((EmAnalyticsSearch)any);
                result = new Exception();
            }
        };
        try {
            searchManager.getSearchListByCategoryId(1234);
        }catch (Exception e){

        }
    }

    @Test
    public void testGetSearchListByFolderId(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        List<EmAnalyticsSearch> searchList = new ArrayList<>();
        searchList.add(emAnalyticsSearch);
        searchList.add(emAnalyticsSearch);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyLong);
                result = query;
                query.getResultList();
                result = emAnalyticsSearch;
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
            }
        };
        searchManager.getSearchListByFolderId(1234);
    }

    public void testGetSearchListByFolderId_Exception(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        List<EmAnalyticsSearch> searchList = new ArrayList<>();
        searchList.add(emAnalyticsSearch);
        searchList.add(emAnalyticsSearch);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyLong);
                result = query;
                query.getResultList();
                result = emAnalyticsSearch;
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
                entityManager.refresh((EmAnalyticsSearch)any);
                result = new Exception();
            }
        };
        try {
            searchManager.getSearchListByFolderId(1234);
        }catch (Exception e){

        }
    }

    @Test
    public void testGetSearchListByLastAccessDate(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        List<EmAnalyticsSearch> searchList = new ArrayList<>();
        searchList.add(emAnalyticsSearch);
        searchList.add(emAnalyticsSearch);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createQuery(anyString);
                result = query;
                query.toString();
                result = "nxxx";
                query.setParameter(anyString, anyString);
                result = query;
                query.getResultList();
                result = emAnalyticsSearch;
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
            }
        };
        searchManager.getSearchListByLastAccessDate(1234);
    }

    @Test
    public void testGetSearchListByLastAccessDate_EMAnalyticsFwkException(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        List<EmAnalyticsSearch> searchList = new ArrayList<>();
        searchList.add(emAnalyticsSearch);
        searchList.add(emAnalyticsSearch);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createQuery(anyString);
                result = query;
                query.toString();
                result = "nxxx";
                query.setParameter(anyString, anyString);
                result = query;
                query.getResultList();
                result = new EMAnalyticsFwkException(new Throwable());
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
            }
        };
        try {
            searchManager.getSearchListByLastAccessDate(1234);
        }catch (Exception e){

        }
    }

    @Test
    public void testGetSearchListByLastAccessDate_Exception(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        List<EmAnalyticsSearch> searchList = new ArrayList<>();
        searchList.add(emAnalyticsSearch);
        searchList.add(emAnalyticsSearch);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createQuery(anyString);
                result = query;
                query.toString();
                result = "nxxx";
                query.setParameter(anyString, anyString);
                result = query;
                query.getResultList();
                result = new Exception();
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
            }
        };
        try {
            searchManager.getSearchListByLastAccessDate(1234);
        }catch (Exception e){

        }
    }

    @Test
    public void testGetSystemSearchListByCategoryId(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        List<EmAnalyticsSearch> searchList = new ArrayList<>();
        searchList.add(emAnalyticsSearch);
        searchList.add(emAnalyticsSearch);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyLong);
                result = query;
                query.getResultList();
                result = emAnalyticsSearch;
                TenantContext.getContext();
                result = tenantInfo;
            }
        };
        searchManager.getSystemSearchListByCategoryId(1234);
    }

    @Test
    public void testGetSystemSearchListByCategoryId_Exception(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        List<EmAnalyticsSearch> searchList = new ArrayList<>();
        searchList.add(emAnalyticsSearch);
        searchList.add(emAnalyticsSearch);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyLong);
                result = query;
                query.getResultList();
                result = new Exception();
                TenantContext.getContext();
                result = tenantInfo;
            }
        };
        try {
            searchManager.getSystemSearchListByCategoryId(1234);
        }catch (Exception e){

        }
    }

    @Test
    public void testGetWidgetListByCategoryId(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        List<EmAnalyticsSearch> searchList = new ArrayList<>();
        searchList.add(emAnalyticsSearch);
        searchList.add(emAnalyticsSearch);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyLong);
                result = query;
                query.getResultList();
                result = emAnalyticsSearch;
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
            }
        };
        searchManager.getWidgetListByCategoryId(1234);
    }

    @Test
    public void testGetWidgetListByCategoryId_Exception(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final Query query, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,@Mocked final TenantInfo tenantInfo) throws Exception {
        List<EmAnalyticsSearch> searchList = new ArrayList<>();
        searchList.add(emAnalyticsSearch);
        searchList.add(emAnalyticsSearch);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString, anyLong);
                result = query;
                query.getResultList();
                result = new Exception();
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
            }
        };
        try {
            searchManager.getWidgetListByCategoryId(1234);
        }catch (Exception e){

        }
    }

    @Test
    public void testGetWidgetScreenshotById(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,@Mocked final EmAnalyticsSearch emAnalyticsSearch) throws Exception {
        new Expectations(){
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getSearchById(anyLong,(EntityManager)any);
                result = emAnalyticsSearch;
            }
        };
        searchManager.getWidgetScreenshotById(1234);
    }

//// TODO: 16-3-4
//    for the if branch
//    @Test
//    public void testGetWidgetScreenshotById_getSearchMocked(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,@Mocked final EmAnalyticsSearch emAnalyticsSearch,@Mocked final Search search, @Mocked final SearchParameter searchParameter) throws Exception {
//        final List<SearchParameter> paramList = new ArrayList<>();
//        paramList.add(searchParameter);
//        paramList.add(searchParameter);
//        new Expectations(){
//            {
//                PersistenceManager.getInstance();
//                result = persistenceManager;
//                persistenceManager.getEntityManager((TenantInfo) any);
//                result = entityManager;
//                EmAnalyticsObjectUtil.getSearchById(anyLong,(EntityManager)any);
//                result = emAnalyticsSearch;
//                withAny(search).getParameters();
//                result = paramList;
//                searchParameter.getName();
//                result = "WIDGET_VISUAL";
//                searchParameter.getValue();
//                result = "WIDGET_VISUAL";
//            }
//        };
//        searchManager.getWidgetScreenshotById(1234);
//    }

    @Test
    public void testModifyLastAccessDate(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final EmAnalyticsLastAccess emAnalyticsLastAccess) throws Exception {
        List<EmAnalyticsSearch> searchList = new ArrayList<>();
        searchList.add(emAnalyticsSearch);
        searchList.add(emAnalyticsSearch);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                EmAnalyticsObjectUtil.getSearchById(anyLong,entityManager);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getAccessedBy();
                result = "accessedbyxx";
                emAnalyticsSearch.getObjectId();
                result = 1234L;
                emAnalyticsSearch.getObjectType();
                result = 5678L;
                entityManager.find(EmAnalyticsLastAccess.class,(EmAnalyticsLastAccess)any);
                result = emAnalyticsLastAccess;
            }
        };
        searchManager.modifyLastAccessDate(1234);
    }

    @Test
    public void testModifyLastAccessDate_Exception(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil) throws Exception {
        List<EmAnalyticsSearch> searchList = new ArrayList<>();
        searchList.add(emAnalyticsSearch);
        searchList.add(emAnalyticsSearch);
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                EmAnalyticsObjectUtil.getSearchById(anyLong,entityManager);
                result = null;
            }
        };
        try {
            searchManager.modifyLastAccessDate(1234);
        }catch (Exception e){

        }
    }

    @Test
    public void testSaveMultipleSearch_getSystemSearchEquals1(@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,@Mocked EmAnalyticsObjectUtil eABU,@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        List<ImportSearchImpl> importSearchList = new ArrayList<>();
        importSearchList.add(importSearchImpl);
        importSearchList.add(importSearchImpl);

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                importSearchImpl.getSearch();
                result = search;
                importSearchImpl.getFolderDetails();
                result = new Object();
                importSearchImpl.getCategoryDetails();
                result = new Object();
                search.getId();
                result = 1L;
                EmAnalyticsObjectUtil.getSearchById(anyLong,entityManager);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new BigDecimal(1);
            }
        };
        searchManager.saveMultipleSearch(importSearchList);
    }

    @Test
    public void testSaveMultipleSearch_getSystemSearchNotEquals1(@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,@Mocked EmAnalyticsObjectUtil eABU,@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        List<ImportSearchImpl> importSearchList = new ArrayList<>();
        importSearchList.add(importSearchImpl);
        importSearchList.add(importSearchImpl);

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                importSearchImpl.getSearch();
                result = search;
                importSearchImpl.getFolderDetails();
                result = new Object();
                importSearchImpl.getCategoryDetails();
                result = new Object();
                search.getId();
                result = 1L;
                EmAnalyticsObjectUtil.getSearchById(anyLong,entityManager);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new BigDecimal(1333);
            }
        };
        searchManager.saveMultipleSearch(importSearchList,true);
    }

    @Test
    public void testSaveMultipleSearch_objInteger(@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,@Mocked EmAnalyticsObjectUtil eABU,@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        List<ImportSearchImpl> importSearchList = new ArrayList<>();
        importSearchList.add(importSearchImpl);
        importSearchList.add(importSearchImpl);

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                importSearchImpl.getSearch();
                result = search;
                importSearchImpl.getFolderDetails();
                result = new Integer(3333);
                importSearchImpl.getCategoryDetails();
                result = new Object();
                search.getId();
                result = 1L;
                EmAnalyticsObjectUtil.getSearchById(anyLong,entityManager);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new BigDecimal(1333);
                EmAnalyticsObjectUtil.getFolderById(anyLong,entityManager);
                returns (new EmAnalyticsFolder(),null);
            }
        };
        searchManager.saveMultipleSearch(importSearchList);
        searchManager.saveMultipleSearch(importSearchList);
    }

    @Test
    public void testSaveMultipleSearch_objFolderImpl(@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,@Mocked EmAnalyticsObjectUtil eABU,@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        List<ImportSearchImpl> importSearchList = new ArrayList<>();
        importSearchList.add(importSearchImpl);
        importSearchList.add(importSearchImpl);

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                importSearchImpl.getSearch();
                result = search;
                importSearchImpl.getFolderDetails();
                result = new FolderImpl();
                importSearchImpl.getCategoryDetails();
                result = new Object();
                search.getId();
                result = 1L;
                EmAnalyticsObjectUtil.getSearchById(anyLong,entityManager);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new BigDecimal(1333);
            }
        };
        searchManager.saveMultipleSearch(importSearchList);
    }

    @Test
    public void testSaveMultipleSearch_cateObjInteger(@Mocked final EmAnalyticsCategory emAnalyticsCategory, @Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        List<ImportSearchImpl> importSearchList = new ArrayList<>();
        importSearchList.add(importSearchImpl);
        importSearchList.add(importSearchImpl);

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                importSearchImpl.getSearch();
                result = search;
                importSearchImpl.getFolderDetails();
                result = new Object();
                importSearchImpl.getCategoryDetails();
                result = new Integer(2223);
                search.getId();
                result = 1L;
                EmAnalyticsObjectUtil.getSearchById(anyLong,entityManager);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getEmAnalyticsCategory();
                result = emAnalyticsCategory;
                emAnalyticsCategory.getCategoryId();
                returns(2223L,1111L);
            }
        };
        searchManager.saveMultipleSearch(importSearchList);
        searchManager.saveMultipleSearch(importSearchList);
    }

    @Test
    public void testSaveMultipleSearch_cateObjCategoryImpl(@Mocked final EmAnalyticsCategory emAnalyticsCategory, @Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        List<ImportSearchImpl> importSearchList = new ArrayList<>();
        importSearchList.add(importSearchImpl);
        importSearchList.add(importSearchImpl);

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                importSearchImpl.getSearch();
                result = search;
                importSearchImpl.getFolderDetails();
                result = new Object();
                importSearchImpl.getCategoryDetails();
                result = new CategoryImpl();
                search.getId();
                result = 1L;
                EmAnalyticsObjectUtil.getSearchById(anyLong,entityManager);
                result = emAnalyticsSearch;
                search.getCategoryId();
                result =null;
            }
        };
        new MockUp<Throwable>()
        {
            @Mock
            public void printStackTrace(){

            }
        };
        new MockUp<Exception>()
        {
            @Mock
            public void printStackTrace(){

            }
        };
        try {
            searchManager.saveMultipleSearch(importSearchList);
        }catch (Exception e){

        }

    }

    @Test
    public void testSaveMultipleSearch_searchGetIdLT0(@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,@Mocked EmAnalyticsObjectUtil eABU,@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        List<ImportSearchImpl> importSearchList = new ArrayList<>();
        importSearchList.add(importSearchImpl);
        importSearchList.add(importSearchImpl);

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                importSearchImpl.getSearch();
                result = search;
                importSearchImpl.getFolderDetails();
                result = new Object();
                importSearchImpl.getCategoryDetails();
                result = new Object();
                search.getId();
                result = 1L;
                EmAnalyticsObjectUtil.getSearchById(anyLong,entityManager);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new BigDecimal(1333);
            }
        };
        searchManager.saveMultipleSearch(importSearchList);
    }

    @Test
    public void testSaveMultipleSearch_searchGetIdST0(@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,@Mocked EmAnalyticsObjectUtil eABU,@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        List<ImportSearchImpl> importSearchList = new ArrayList<>();
        importSearchList.add(importSearchImpl);
        importSearchList.add(importSearchImpl);

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                importSearchImpl.getSearch();
                result = search;
                importSearchImpl.getFolderDetails();
                result = new Object();
                importSearchImpl.getCategoryDetails();
                result = new Object();
                search.getId();
                result = null;
//                EmAnalyticsObjectUtil.getSearchById(anyLong,entityManager);
//                result = emAnalyticsSearch;
//                emAnalyticsSearch.getSystemSearch();
//                result = new BigDecimal(1333);
            }
        };
        searchManager.saveMultipleSearch(importSearchList);
    }

    @Test
    public void testSaveMultipleSearch_searchGetIdST0_objInteger(@Mocked final Query query, @Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        List<ImportSearchImpl> importSearchList = new ArrayList<>();
        importSearchList.add(importSearchImpl);
        importSearchList.add(importSearchImpl);

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                importSearchImpl.getSearch();
                result = search;
                importSearchImpl.getFolderDetails();
                result = new Integer(11);
                importSearchImpl.getCategoryDetails();
                result = new Object();
                search.getId();
                result = null;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyLong);
                result = query;
                query.setParameter(anyString,anyString);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
                query.getSingleResult();
                result = emAnalyticsSearch;
            }
        };
        searchManager.saveMultipleSearch(importSearchList);
    }

    @Test
    public void testSaveMultipleSearch_searchGetIdST0_objInteger_noResultException(@Mocked final Query query, @Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        List<ImportSearchImpl> importSearchList = new ArrayList<>();
        importSearchList.add(importSearchImpl);
        importSearchList.add(importSearchImpl);

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                importSearchImpl.getSearch();
                result = search;
                importSearchImpl.getFolderDetails();
                result = new Integer(11);
                importSearchImpl.getCategoryDetails();
                result = new Object();
                search.getId();
                result = null;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyLong);
                result = query;
                query.setParameter(anyString,anyString);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
                query.getSingleResult();
                result = new NoResultException();
            }
        };
        searchManager.saveMultipleSearch(importSearchList);
    }

    @Test
    public void testSaveMultipleSearch_searchGetIdST0_cateObjNull(@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        List<ImportSearchImpl> importSearchList = new ArrayList<>();
        importSearchList.add(importSearchImpl);
        importSearchList.add(importSearchImpl);

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                importSearchImpl.getSearch();
                result = search;
                importSearchImpl.getFolderDetails();
                result = new Object();
                importSearchImpl.getCategoryDetails();
                result = null;
                search.getId();
                result = null;
            }
        };
        searchManager.saveMultipleSearch(importSearchList);
    }

    @Test
    public void testSaveMultipleSearch_searchGetIdST0_cateObjInteger(@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        List<ImportSearchImpl> importSearchList = new ArrayList<>();
        importSearchList.add(importSearchImpl);
        importSearchList.add(importSearchImpl);

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                importSearchImpl.getSearch();
                result = search;
                importSearchImpl.getFolderDetails();
                result = new Object();
                importSearchImpl.getCategoryDetails();
                result = new Integer(11);
                search.getId();
                result = null;
            }
        };
        searchManager.saveMultipleSearch(importSearchList);
    }

    @Test
    public void testSaveMultipleSearch_searchGetIdST0_objFolderImpl_cateObjCategoryImpl(@Mocked final Query query, @Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception {
        List<ImportSearchImpl> importSearchList = new ArrayList<>();
        importSearchList.add(importSearchImpl);
        importSearchList.add(importSearchImpl);

        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                TenantContext.getContext();
                result = tenantInfo;
                importSearchImpl.getSearch();
                result = search;
                importSearchImpl.getFolderDetails();
                result = new FolderImpl();
                importSearchImpl.getCategoryDetails();
                result = new CategoryImpl();
                search.getId();
                result = null;
                entityManager.createNamedQuery(anyString);
                result = query;
                query.setParameter(anyString,anyLong);
                result = query;
                query.setParameter(anyString,anyString);
                TenantContext.getContext();
                result = tenantInfo;
                tenantInfo.getUsername();
                result = "userName";
                query.getSingleResult();
                result = new NoResultException();
            }
        };
        searchManager.saveMultipleSearch(importSearchList);
    }


    @Test
    public void testSaveSearch(@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd((Search)any,entityManager);
                result = emAnalyticsSearch;
            }
        };
        searchManager.saveSearch(new SearchImpl());
    }

    @Test
    public void testSaveSearch_EMAnalyticsFwkException(@Mocked final PersistenceManager persistenceManager) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = new EMAnalyticsFwkException(new Throwable());
            }
        };
        try {
            searchManager.saveSearch(new SearchImpl());
        }catch (Exception e){

        }
    }

    @Test
    public void testSaveSearch_PersistenceException(@Mocked final PersistenceManager persistenceManager) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = new PersistenceException();
            }
        };
        try {
            searchManager.saveSearch(new SearchImpl());
        }catch (Exception e){

        }
    }

    @Test
    public void testSaveSearch_Exception(@Mocked final PersistenceManager persistenceManager) throws Exception {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = new Exception();
            }
        };
        try {
            searchManager.saveSearch(new SearchImpl());
        }catch (Exception e){

        }
    }
}
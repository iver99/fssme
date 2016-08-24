package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigDecimal;
import java.util.*;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emaas.platform.savedsearch.entity.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.WidgetChangeNotification;

/**
 * @author qianqi
 * @since 16-3-2.
 */
@Test(groups = { "s2" })
public class SearchManagerImplTest
{

	SearchManagerImpl searchManager;
	@Mocked
	PersistenceManager persistenceManager;
	@Mocked
	EntityManager entityManager;
	@Mocked
	EmAnalyticsObjectUtil emAnalyticsObjectUtil;
	@Mocked
	EmAnalyticsSearch emAnalyticsSearch;
	@Mocked
	WidgetChangeNotification anyWidgetChangeNotification;


    @Test
    public void testCreateNewSearch() {
        searchManager.createNewSearch();
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testDeleteSearchException() throws EMAnalyticsFwkException {
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.getSearchByIdForDelete(anyLong, entityManager);
                result = emAnalyticsSearch;
                emAnalyticsSearch.getSystemSearch();
                result = new Exception();
                emAnalyticsSearch.getName();
                result = "namexx";

            }
        };
        searchManager.deleteSearch(1234, true);
    }

	@Test
	public void testDeleteSearchMocked() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.getSearchByIdForDelete(anyLong, entityManager);
				result = emAnalyticsSearch;

			}
		};
		searchManager.deleteSearch(1234, true);
		searchManager.deleteSearch(1234, false);
	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testDeleteSearchMockednull() throws EMAnalyticsFwkException
    {
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.getSearchByIdForDelete(anyLong, entityManager);
				result = null;

            }
        };
        searchManager.deleteSearch(1234, true);
        searchManager.deleteSearch(1234, false);
    }


	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testEditSearchEMAnalyticsFwkException() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit((Search) any, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getSystemSearch();
				result = new BigDecimal(1);

            }
        };
        searchManager.editSearch(new SearchImpl());
    }


	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testEditSearchException() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = new Exception(throwable);
			}
		};
        searchManager.editSearch(new SearchImpl());
	}

	@Test
	public void testEditSearchNormal()
					throws Exception
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.getEmAnalyticsSearchForEdit((Search) any, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getSystemSearch();
				result = new BigDecimal(10);
				emAnalyticsSearch.getIsWidget();
				result = 1L;
				new WidgetChangeNotification().notifyChange((Search) any);
			}
		};
		searchManager.editSearch(new SearchImpl());
	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testEditSearchPersistenceException() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = new PersistenceException(throwable);
			}
		};
			searchManager.editSearch(new SearchImpl());
	}

	@BeforeMethod
	public void testGetInstance()
	{
		searchManager = SearchManagerImpl.getInstance();
	}

	@Test
	public void testGetSearch()
					throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.getSearchById(anyLong, entityManager);
				result = emAnalyticsSearch;
			}
		};
		searchManager.getSearch(1234);
	}
	@Mocked
	Throwable throwable;
	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetSearchException()throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.getSearchById(anyLong, entityManager);
				result = new Exception(throwable);
			}
		};
		searchManager.getSearch(1234);
	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetSearchNullException()throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.getSearchById(anyLong, entityManager);
				result = null;
			}
		};
		searchManager.getSearch(1234);
	}
	@Test
	public void testGetSearchWithoutOwner() throws EMAnalyticsFwkException {
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				entityManager.find(EmAnalyticsSearch.class, anyLong);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getDeleted();
				result = 0L;
			}
		};
		searchManager.getSearchWithoutOwner(1234);
	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetSearchWithoutOwnerNullException() throws EMAnalyticsFwkException {
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				entityManager.find(EmAnalyticsSearch.class, anyLong);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getDeleted();
				result = 1L;
			}
		};
		searchManager.getSearchWithoutOwner(1234);
	}
	@Mocked
	TenantContext tenantContext;
	@Mocked
	TenantInfo tenantInfo;
	@Mocked
	Query query;
	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetSearchByNameException() throws EMAnalyticsFwkException
	{
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
			searchManager.getSearchByName("name", 1234);
	}

	@Test
	public void testGetSearchByNameNoResultException() throws EMAnalyticsFwkException
	{
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
				result = new NoResultException("");
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
		searchManager.getSearchByName("name", 1234);
	}

	@Test
	public void testGetSearchByNameNormal() throws EMAnalyticsFwkException
	{
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
		searchManager.getSearchByName("name", 1234);
	}

	@Test
	public void testGetSearchCountByFolderId() throws Exception
	{
		try {
			searchManager.getSearchCountByFolderId(1234);
		}
		catch (Exception e) {

		}
	}

	@Test
	public void testGetSearchListByCategoryId() throws EMAnalyticsFwkException
	{
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

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetSearchListByCategoryIdException() throws EMAnalyticsFwkException
	{
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
				entityManager.refresh(any);
				result = new Exception(throwable);
			}
		};
			searchManager.getSearchListByCategoryId(1234);
	}

	@Test
	public void testGetSearchListByFolderId() throws EMAnalyticsFwkException
	{
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
	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetSearchListByFolderIdException() throws EMAnalyticsFwkException
	{
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
				entityManager.refresh(any);
				result = new Exception(throwable);
			}
		};
			searchManager.getSearchListByFolderId(1234);
	}

	@Test
	public void testGetSearchListByLastAccessDate() throws EMAnalyticsFwkException
	{
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

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetSearchListByLastAccessDateEMAnalyticsFwkException() throws EMAnalyticsFwkException
	{
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
				result = new EMAnalyticsFwkException(throwable);
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
			searchManager.getSearchListByLastAccessDate(1234);
	}

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetSearchListByLastAccessDate_Exception() throws EMAnalyticsFwkException
	{
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
				result = new Exception(throwable);
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
			searchManager.getSearchListByLastAccessDate(1234);
	}

	@Test
	public void testGetSystemSearchListByCategoryId() throws EMAnalyticsFwkException
	{
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

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetSystemSearchListByCategoryId_Exception() throws EMAnalyticsFwkException
	{
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
				result = new Exception(throwable);
				TenantContext.getContext();
				result = tenantInfo;
			}
		};
			searchManager.getSystemSearchListByCategoryId(1234);
	}

	@Test
	public void testGetWidgetListByCategoryId() throws EMAnalyticsFwkException
	{
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

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetWidgetListByCategoryIdException() throws EMAnalyticsFwkException
	{
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
				result = new Exception(throwable);
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
			searchManager.getWidgetListByCategoryId(1234);
	}

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetWidgetListByProviderNames()
					throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance().getEntityManager((TenantInfo) any);
				result = entityManager;
				TenantContext.getContext();
			}
		};
		searchManager.getWidgetListByProviderNames(false, Arrays.asList("LoganService"), null);
		searchManager.getWidgetListByProviderNames(true, Arrays.asList("LoganService", "LoganService"), "22");
		searchManager.getWidgetListByProviderNames(false, Arrays.asList("LoganService", "LoganService"), "22");
		new Expectations() {
			{
				PersistenceManager.getInstance().getEntityManager((TenantInfo) any);
				result = new Exception(throwable);
			}
		};
			searchManager.getWidgetListByProviderNames(false, Arrays.asList("LoganService"), null);
	}

	@Test
	public void testGetWidgetListByProviderNamesEmptyNames() throws EMAnalyticsFwkException
	{
		searchManager.getWidgetListByProviderNames(false, null, null);
	}

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetWidgetScreenshotById(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch) throws EMAnalyticsFwkException
    {
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
			}
		};
		searchManager.getWidgetScreenshotById(1234);
	}


	@Mocked
	EmAnalyticsLastAccess emAnalyticsLastAccess;
	@Test
	public void testModifyLastAccessDate()
					throws EMAnalyticsFwkException
	{
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
				EmAnalyticsObjectUtil.getSearchById(anyLong, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getAccessedBy();
				result = "accessedbyxx";
				emAnalyticsSearch.getObjectId();
				result = 1234L;
				emAnalyticsSearch.getObjectType();
				result = 5678L;
				entityManager.find(EmAnalyticsLastAccess.class, any);
				result = emAnalyticsLastAccess;
			}
		};
		searchManager.modifyLastAccessDate(1234);
	}

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testModifyLastAccessDateException() throws EMAnalyticsFwkException
	{
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
				EmAnalyticsObjectUtil.getSearchById(anyLong, entityManager);
				result = null;
			}
		};
			searchManager.modifyLastAccessDate(1234);
	}

    @Test(expectedExceptions = {NullPointerException.class})
	public void testSaveMultipleSearchCateObjCategoryImpl() throws Exception
	{
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
				EmAnalyticsObjectUtil.getSearchById(anyLong, entityManager);
				result = emAnalyticsSearch;
				search.getCategoryId();
				result = null;
			}
		};
		new MockUp<Exception>() {
			@Mock
			public void printStackTrace()
			{

			}
		};
			searchManager.saveMultipleSearch(importSearchList);

	}
	@Mocked
	EmAnalyticsCategory emAnalyticsCategory;
	@Test
	public void testSaveMultipleSearchCateObjInteger() throws Exception
	{
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
				EmAnalyticsObjectUtil.getSearchById(anyLong, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getEmAnalyticsCategory();
				result = emAnalyticsCategory;
				emAnalyticsCategory.getCategoryId();
				returns(2223L, 1111L);
			}
		};
		searchManager.saveMultipleSearch(importSearchList);
		searchManager.saveMultipleSearch(importSearchList);
	}

	@Test
	public void testSaveMultipleSearchGetSystemSearchEquals1() throws Exception
	{
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
				EmAnalyticsObjectUtil.getSearchById(anyLong, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getSystemSearch();
				result = new BigDecimal(1);
			}
		};
		searchManager.saveMultipleSearch(importSearchList);
	}

	@Test
	public void testSaveMultipleSearchGetSystemSearchNotEquals1() throws Exception
	{
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
				EmAnalyticsObjectUtil.getSearchById(anyLong, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getSystemSearch();
				result = new BigDecimal(1333);
			}
		};
		searchManager.saveMultipleSearch(importSearchList, true);
	}

	@Test
	public void testSaveMultipleSearchObjFolderImpl() throws Exception
	{
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
				EmAnalyticsObjectUtil.getSearchById(anyLong, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getSystemSearch();
				result = new BigDecimal(1333);
			}
		};
		searchManager.saveMultipleSearch(importSearchList);
	}

	@Test
	public void testSaveMultipleSearchObjInteger() throws Exception
	{
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
				EmAnalyticsObjectUtil.getSearchById(anyLong, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getSystemSearch();
				result = new BigDecimal(1333);
				EmAnalyticsObjectUtil.getFolderById(anyLong, entityManager);
			}
		};
		searchManager.saveMultipleSearch(importSearchList);
		searchManager.saveMultipleSearch(importSearchList);
	}

	@Test
	public void testSaveMultipleSearchSearchGetIdLT0() throws Exception
	{
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
				EmAnalyticsObjectUtil.getSearchById(anyLong, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getSystemSearch();
				result = new BigDecimal(1333);
			}
		};
		searchManager.saveMultipleSearch(importSearchList);
	}

	@Test
	public void testSaveMultipleSearchSearchGetIdST0() throws Exception
	{
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
	public void testSaveMultipleSearchSearchGetIdST0CateObjInteger() throws Exception
	{
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
	public void testSaveMultipleSearchSearchGetIdST0CateObjNull() throws Exception
	{
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
	public void testSaveMultipleSearchSearchGetIdST0ObjFolderImplCateObjCategoryImpl() throws Exception
	{
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
				query.setParameter(anyString, anyLong);
				result = query;
				query.setParameter(anyString, anyString);
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
	public void testSaveMultipleSearchSearchGetIdST0ObjFolderImplCateObjCategoryImplGetFolderIdNullGetCategoryIdNull() throws Exception
	{
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
				search.getFolderId();
				result = null;
				search.getCategoryId();
				result = null;
				entityManager.createNamedQuery(anyString);
				result = query;
				query.setParameter(anyString, anyLong);
				result = query;
				query.setParameter(anyString, anyString);
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
	public void testSaveMultipleSearchSearchGetIdST0ObjInteger() throws Exception
	{
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
				query.setParameter(anyString, anyLong);
				result = query;
				query.setParameter(anyString, anyString);
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
	@Mocked
	Search search;
	@Mocked
	ImportSearchImpl importSearchImpl;
	@Test
	public void testSaveMultipleSearchSearchGetIdST0ObjIntegerNoResultException() throws Exception
	{
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
				query.setParameter(anyString, anyLong);
				result = query;
				query.setParameter(anyString, anyString);
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
	public void testSaveSearch() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.getEmAnalyticsSearchForAdd((Search) any, entityManager);
				result = emAnalyticsSearch;
			}
		};
		searchManager.saveSearch(new SearchImpl());
	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testSaveSearchEMAnalyticsFwkException() throws EMAnalyticsFwkException
    {
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = new EMAnalyticsFwkException(throwable);
			}
		};
			searchManager.saveSearch(new SearchImpl());
	}


    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testSaveSearchException() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = new Exception(throwable);
			}
		};
			searchManager.saveSearch(new SearchImpl());
	}

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testSaveSearchPersistenceException() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = new PersistenceException(throwable);
			}
		};
			searchManager.saveSearch(new SearchImpl());
	}

	@Test
	public void testGetSearchParamByName() throws EMAnalyticsFwkException {
		new Expectations(){
			{
 				PersistenceManager.getInstance();
				result = persistenceManager;
				TenantContext.getContext();
				result = tenantInfo;
				persistenceManager.getEntityManager((TenantInfo)any);
				EmAnalyticsObjectUtil.getSearchParamByName(anyLong, anyString, entityManager);
				result = "param_value";
			}
		};
		searchManager.getSearchParamByName(1L, "widget_viewmodel");
	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetSearchParamByNameException() throws EMAnalyticsFwkException {
		new Expectations(){
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				TenantContext.getContext();
				result = new Exception(throwable);
			}
		};
		searchManager.getSearchParamByName(1L, "widget_viewmodel");
	}

	@Mocked
	EmAnalyticsFolder emAnalyticsFolder;
	@Mocked
	CategoryManagerImpl categoryManager;
	@Mocked
	Category category;
	@Test
	public void testGetWidgetScreenshotByIdNotNull() throws Exception {
		final Set<EmAnalyticsSearchParam> emAnalyticsSearchParamSet = new HashSet<>();
		EmAnalyticsSearchParam emAnalyticsSearchParam = new EmAnalyticsSearchParam();
		emAnalyticsSearchParam.setName("NAME_WIDGET_SOURCE");
		emAnalyticsSearchParam.setParamValueStr("name_widget_source");
		emAnalyticsSearchParamSet.add(emAnalyticsSearchParam);
		emAnalyticsSearchParam = new EmAnalyticsSearchParam();
		emAnalyticsSearchParam.setName("New_PARAMNAME");
		emAnalyticsSearchParam.setParamValueStr("new_ParamValue");
		emAnalyticsSearchParam.setParamType(new BigDecimal(0));
		emAnalyticsSearchParamSet.add(emAnalyticsSearchParam);
		emAnalyticsSearchParam = new EmAnalyticsSearchParam();
		emAnalyticsSearchParam.setName("WIDGET_VISUAL");
		emAnalyticsSearchParam.setParamValueClob("new_ParamValue");
		emAnalyticsSearchParam.setParamType(new BigDecimal(1));
		emAnalyticsSearchParamSet.add(emAnalyticsSearchParam);
		emAnalyticsSearchParam = new EmAnalyticsSearchParam();
		emAnalyticsSearchParam.setName("WIDGET_VISUAL");
		emAnalyticsSearchParam.setParamType(new BigDecimal(1));
		emAnalyticsSearchParamSet.add(emAnalyticsSearchParam);
		new Expectations(){
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				TenantContext.getContext();
				result = tenantInfo;
				persistenceManager.getEntityManager((TenantInfo)any);
				result = entityManager;
				entityManager.find(EmAnalyticsSearch.class, anyLong);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getDeleted();
				result = 0;
				emAnalyticsSearch.getId();
				result = 1L;
				emAnalyticsSearch.getNameNlsid();
				result = "des_nlsid";
				emAnalyticsSearch.getNameSubsystem();
				result = "name_subsystem";
				emAnalyticsSearch.getDescriptionNlsid();
				result = "des_nlsid";
				emAnalyticsSearch.getDescriptionSubsystem();
				result = "des_subsystem";
				emAnalyticsSearch.getOwner();
				result = "Oracle";
				emAnalyticsSearch.getCreationDate();
				result = new Date();
				emAnalyticsSearch.getLastModifiedBy();
				result = "Oracle";
				emAnalyticsSearch.getEmAnalyticsCategory();
				result = emAnalyticsCategory;
				emAnalyticsCategory.getCategoryId();
				result = 1L;
				emAnalyticsSearch.getEmAnalyticsFolder();
				result = emAnalyticsFolder;
				emAnalyticsFolder.getFolderId();
				result = 1L;
				emAnalyticsSearch.getAccessDate();
				result = new  Date();
				emAnalyticsSearch.getIsWidget();
				result =1;
				emAnalyticsSearch.getEmAnalyticsSearchParams();
				result = emAnalyticsSearchParamSet;
				emAnalyticsSearch.getNAMEWIDGETSOURCE();
				result = "name_widget_source";
				emAnalyticsSearch.getWIDGETGROUPNAME();
				result = "widget_group_name";
				emAnalyticsSearch.getWIDGETSCREENSHOTHREF();
				result = "wodget_screen_shot_href";
				emAnalyticsSearch.getWIDGETICON();
				result = "widget_icon";
				emAnalyticsSearch.getWIDGETKOCNAME();
				result = "widget_koc_name";
				emAnalyticsSearch.getWIDGETVIEWMODEL();
				result ="widget_view_model";
				emAnalyticsSearch.getWIDGETTEMPLATE();
				result = "widget_template";
				emAnalyticsSearch.getWIDGETSUPPORTTIMECONTROL();
				result = "widget_support";
				emAnalyticsSearch.getWIDGETLINKEDDASHBOARD();
				result = 1L;
				emAnalyticsSearch.getWIDGETDEFAULTWIDTH();
				result = 1L;
				emAnalyticsSearch.getWIDGETDEFAULTHEIGHT();
				result = 1L;
				emAnalyticsSearch.getDASHBOARDINELIGIBLE();
				result = "dashboard_ineligible";
				emAnalyticsSearch.getPROVIDERVERSION();
				result = "provider_version";
				emAnalyticsSearch.getPROVIDERASSETROOT();
				result = "provider_assetroot";
				emAnalyticsSearch.getPROVIDERNAME();
				result = "provider_name";
				CategoryManagerImpl.createCategoryObject((EmAnalyticsCategory)any, null);
				result = category;
			}
		};
		searchManager.getWidgetScreenshotById(1L);
	}
}

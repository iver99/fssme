package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

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
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsLastAccess;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;

/**
 * @author qianqi
 * @since 16-3-2.
 */
@Test(groups = { "s2" })
public class SearchManagerImplTest
{

	SearchManagerImpl searchManager;

    @Test
    public void testCreateNewSearch() {
        searchManager.createNewSearch();
    }

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
    public void testDeleteSearchException(@Mocked final PersistenceManager persistenceManager,
                                          @Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
                                          @Mocked final EmAnalyticsSearch emAnalyticsSearch) throws EMAnalyticsFwkException {
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
	public void testDeleteSearchMocked(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch) throws EMAnalyticsFwkException
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
	public void testDeleteSearchMockednull(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch) throws EMAnalyticsFwkException
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
	public void testEditSearchEMAnalyticsFwkException(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch) throws EMAnalyticsFwkException
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
	public void testEditSearchExceptio(@Mocked final PersistenceManager persistenceManager) throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = new Exception();
			}
		};
        searchManager.editSearch(new SearchImpl());
	}

	@Test
	public void testEditSearchNormal(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked final WidgetChangeNotification anyWidgetChangeNotification)
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
	public void testEditSearchPersistenceException(@Mocked final PersistenceManager persistenceManager) throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = new PersistenceException();
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
	public void testGetSearch(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
			@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final EmAnalyticsSearch emAnalyticsSearch)
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

	@Test
	public void testGetSearchWithoutOwner(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
			@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final EmAnalyticsSearch emAnalyticsSearch)
					throws Exception
	{
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
	public void testGetSearchByNameException(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
	public void testGetSearchByNameNoResultException(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
				result = new NoResultException();
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
		searchManager.getSearchByName("name", 1234);
	}

	@Test
	public void testGetSearchByNameNormal(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
	public void testGetSearchListByCategoryId(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
	public void testGetSearchListByCategoryIdException(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
				result = new Exception();
			}
		};
			searchManager.getSearchListByCategoryId(1234);
	}

	@Test
	public void testGetSearchListByFolderId(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
	public void testGetSearchListByFolderIdException(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
				result = new Exception();
			}
		};
			searchManager.getSearchListByFolderId(1234);
	}

	@Test
	public void testGetSearchListByLastAccessDate(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
	public void testGetSearchListByLastAccessDate_EMAnalyticsFwkException(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
				result = new EMAnalyticsFwkException(new Throwable());
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
			searchManager.getSearchListByLastAccessDate(1234);
	}

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetSearchListByLastAccessDate_Exception(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
				result = new Exception();
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
			searchManager.getSearchListByLastAccessDate(1234);
	}

	@Test
	public void testGetSystemSearchListByCategoryId(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
	public void testGetSystemSearchListByCategoryId_Exception(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
				result = new Exception();
				TenantContext.getContext();
				result = tenantInfo;
			}
		};
			searchManager.getSystemSearchListByCategoryId(1234);
	}

	@Test
	public void testGetWidgetListByCategoryId(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
	public void testGetWidgetListByCategoryIdException(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final Query query,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws EMAnalyticsFwkException
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
				result = new Exception();
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
			searchManager.getWidgetListByCategoryId(1234);
	}

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testGetWidgetListByProviderNames(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager em, @Mocked final TenantContext tc, @Mocked final Query query)
					throws EMAnalyticsFwkException
	{
		final List<EmAnalyticsSearch> wgtList = new ArrayList<EmAnalyticsSearch>();
		new Expectations() {
			{
				PersistenceManager.getInstance().getEntityManager((TenantInfo) any);
				result = em;
				TenantContext.getContext();
				result = new TenantInfo("user", 1L);
				em.createQuery(anyString);
				result = query;
				query.getResultList();
				result = wgtList;
			}
		};
		searchManager.getWidgetListByProviderNames(false, Arrays.asList("LoganService"), null);
		searchManager.getWidgetListByProviderNames(true, Arrays.asList("LoganService", "LoganService"), "22");
		searchManager.getWidgetListByProviderNames(false, Arrays.asList("LoganService", "LoganService"), "22");
		new Expectations() {
			{
				PersistenceManager.getInstance().getEntityManager((TenantInfo) any);
				result = new Exception(new Exception("Cannot acquire data source"));
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
	public void testModifyLastAccessDate(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final EmAnalyticsSearch emAnalyticsSearch,
			@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
			@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final EmAnalyticsLastAccess emAnalyticsLastAccess)
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
	public void testModifyLastAccessDateException(@Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final EmAnalyticsSearch emAnalyticsSearch,
			@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo,
			@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil) throws EMAnalyticsFwkException
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
	public void testSaveMultipleSearchCateObjCategoryImpl(@Mocked final EmAnalyticsCategory emAnalyticsCategory,
			@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search,
			@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
			@Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch,
			@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception
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
		new MockUp<Throwable>() {
			@Mock
			public void printStackTrace()
			{

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

	@Test
	public void testSaveMultipleSearchCateObjInteger(@Mocked final EmAnalyticsCategory emAnalyticsCategory,
			@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search,
			@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
			@Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch,
			@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception
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
	public void testSaveMultipleSearchGetSystemSearchEquals1(@Mocked final ImportSearchImpl importSearchImpl,
			@Mocked final Search search, @Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil eABU,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws Exception
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
	public void testSaveMultipleSearchGetSystemSearchNotEquals1(@Mocked final ImportSearchImpl importSearchImpl,
			@Mocked final Search search, @Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil eABU,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws Exception
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
	public void testSaveMultipleSearchObjFolderImpl(@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search,
			@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
			@Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch,
			@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception
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
	public void testSaveMultipleSearchObjInteger(@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search,
			@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
			@Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch,
			@Mocked TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception
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
				returns(new EmAnalyticsFolder(), null);
			}
		};
		searchManager.saveMultipleSearch(importSearchList);
		searchManager.saveMultipleSearch(importSearchList);
	}

	@Test
	public void testSaveMultipleSearchSearchGetIdLT0(@Mocked final ImportSearchImpl importSearchImpl,
			@Mocked final Search search, @Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil eABU,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws Exception
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
	public void testSaveMultipleSearchSearchGetIdST0(@Mocked final ImportSearchImpl importSearchImpl,
			@Mocked final Search search, @Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil eABU,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws Exception
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
	public void testSaveMultipleSearchSearchGetIdST0CateObjInteger(@Mocked final ImportSearchImpl importSearchImpl,
			@Mocked final Search search, @Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil eABU,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked final TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws Exception
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
	public void testSaveMultipleSearchSearchGetIdST0CateObjNull(@Mocked final ImportSearchImpl importSearchImpl,
			@Mocked final Search search, @Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked EmAnalyticsObjectUtil eABU,
			@Mocked final EmAnalyticsSearch emAnalyticsSearch, @Mocked final TenantContext tenantContext,
			@Mocked final TenantInfo tenantInfo) throws Exception
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
	public void testSaveMultipleSearchSearchGetIdST0ObjFolderImplCateObjCategoryImpl(@Mocked final Query query,
			@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search,
			@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
			@Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch,
			@Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception
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
	public void testSaveMultipleSearchSearchGetIdST0ObjFolderImplCateObjCategoryImplGetFolderIdNullGetCategoryIdNull(
			@Mocked final Query query, @Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search,
			@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
			@Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch,
			@Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception
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
	public void testSaveMultipleSearchSearchGetIdST0ObjInteger(@Mocked final Query query,
			@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search,
			@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
			@Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch,
			@Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception
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

	@Test
	public void testSaveMultipleSearchSearchGetIdST0ObjIntegerNoResultException(@Mocked final Query query,
			@Mocked final ImportSearchImpl importSearchImpl, @Mocked final Search search,
			@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager,
			@Mocked EmAnalyticsObjectUtil eABU, @Mocked final EmAnalyticsSearch emAnalyticsSearch,
			@Mocked final TenantContext tenantContext, @Mocked final TenantInfo tenantInfo) throws Exception
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
	public void testSaveSearch(@Mocked final EmAnalyticsSearch emAnalyticsSearch,
			@Mocked EmAnalyticsObjectUtil emAnalyticsObjectUtil, @Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager) throws EMAnalyticsFwkException
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
	public void testSaveSearchEMAnalyticsFwkException(@Mocked final PersistenceManager persistenceManager) throws EMAnalyticsFwkException
    {
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = new EMAnalyticsFwkException(new Throwable());
			}
		};
			searchManager.saveSearch(new SearchImpl());
	}


    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testSaveSearchException(@Mocked final PersistenceManager persistenceManager) throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = new Exception();
			}
		};
			searchManager.saveSearch(new SearchImpl());
	}

    @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testSaveSearchPersistenceException(@Mocked final PersistenceManager persistenceManager) throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = new PersistenceException();
			}
		};
			searchManager.saveSearch(new SearchImpl());
	}
}

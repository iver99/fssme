package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.WidgetChangeNotification;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearchParam;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author qianqi
 * @since 16-3-2.
 */
@Test(groups = { "s2" })
public class SearchManagerImplTest
{
	private static final int TEST_INT_ID = 1234;
	private static final BigInteger TEST_ID = new BigInteger("1234");
	
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

	@Mocked
	Throwable throwable;

	@Mocked
	TenantContext tenantContext;

	@Mocked
	TenantInfo tenantInfo;

	@Mocked
	Query query;

	@Mocked
	EmAnalyticsCategory emAnalyticsCategory;

	@Mocked
	Search search;

	@Mocked
	ImportSearchImpl importSearchImpl;

	@Mocked
	EmAnalyticsFolder emAnalyticsFolder;

	@Mocked
	CategoryManagerImpl categoryManager;
	@Mocked
	Category category;

	@Test
	public void testCreateNewSearch()
	{
		searchManager.createNewSearch();
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
	public void testDeleteSearchException() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.getSearchByIdForDelete((BigInteger) any, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getSystemSearch();
				result = new Exception();
				emAnalyticsSearch.getName();
				result = "namexx";

			}
		};
		searchManager.deleteSearch(TEST_ID, true);
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
				EmAnalyticsObjectUtil.getSearchByIdForDelete((BigInteger) any, entityManager);
				result = emAnalyticsSearch;

			}
		};
		searchManager.deleteSearch(TEST_ID, true);
		searchManager.deleteSearch(TEST_ID, false);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
	public void testDeleteSearchMockednull() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.getSearchByIdForDelete((BigInteger) any, entityManager);
				result = null;

			}
		};
		searchManager.deleteSearch(TEST_ID, true);
		searchManager.deleteSearch(TEST_ID, false);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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
	public void testEditSearchNormal() throws Exception
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
				new WidgetChangeNotification().notify((Search) any, (Date) any);
			}
		};
		searchManager.editSearch(new SearchImpl());
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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
	public void testGetSearch() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.getSearchById((BigInteger) any, entityManager);
				result = emAnalyticsSearch;
			}
		};
		searchManager.getSearch(TEST_ID);
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
				EmAnalyticsObjectUtil.findEmSearchByIdWithoutOwner((BigInteger) any, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getDeleted();
				result = BigInteger.ZERO;
			}
		};
		searchManager.getSearchWithoutOwner(TEST_ID);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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
				query.setParameter(anyString, (BigInteger) any);
				result = query;
				query.getSingleResult();
				result = new Exception();
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
		searchManager.getSearchByName("name", TEST_ID);
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
				query.setParameter(anyString, (BigInteger) any);
				result = query;
				query.getSingleResult();
				result = new NoResultException("");
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
		searchManager.getSearchByName("name", TEST_ID);
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
				query.setParameter(anyString, (BigInteger) any);
				result = query;
				query.getSingleResult();
				result = emAnalyticsSearch;
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
		searchManager.getSearchByName("name", TEST_ID);
	}

	@Test
	public void testGetSearchCountByFolderId() throws Exception
	{
		try {
			searchManager.getSearchCountByFolderId(TEST_ID);
		}
		catch (Exception e) {

		}
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
	public void testGetSearchException() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.getSearchById((BigInteger) any, entityManager);
				result = new Exception(throwable);
			}
		};
		searchManager.getSearch(TEST_ID);
	}
	
	@Test
	public void testGetSearchListByIds() throws EMAnalyticsFwkException
	{
		final List<EmAnalyticsSearch> realList = new ArrayList<EmAnalyticsSearch>();
		realList.add(new EmAnalyticsSearch());
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				entityManager.createNamedQuery(anyString);
				result = query;
				query.setParameter(anyString, any);
				result = query;
				query.getResultList();
				result = realList;
				
			}
		};
		
		List<Search> list1 = searchManager.getSearchListByIds(null);
		Assert.assertEquals(list1.size(), 0);
		List<BigInteger> ids = new ArrayList<BigInteger>();
		List<Search> list2 = searchManager.getSearchListByIds(ids);
		Assert.assertEquals(list2.size(), 0);
		ids.add(BigInteger.valueOf(1234L));
		searchManager.getSearchListByIds(ids);
	}
	
	@Test (expectedExceptions = { EMAnalyticsFwkException.class })
	public void testGetSearchListByIdsException() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				entityManager.createNamedQuery(anyString);
				result = query;
				query.setParameter(anyString, any);
				result = query;
				query.getResultList();
				result = new Exception(throwable);
			}
		};
		
		List<BigInteger> ids = new ArrayList<BigInteger>();
		ids.add(BigInteger.valueOf(1234L));
		searchManager.getSearchListByIds(ids);
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
				query.setParameter(anyString, (BigInteger) any);
				result = query;
				query.getResultList();
				result = emAnalyticsSearch;
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
		searchManager.getSearchListByCategoryId(TEST_ID);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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
				query.setParameter(anyString, (BigInteger) any);
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
		searchManager.getSearchListByCategoryId(TEST_ID);
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
				query.setParameter(anyString, (BigInteger) any);
				result = query;
				query.getResultList();
				result = emAnalyticsSearch;
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
		searchManager.getSearchListByFolderId(TEST_ID);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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
				query.setParameter(anyString, (BigInteger) any);
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
		searchManager.getSearchListByFolderId(TEST_ID);
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
		searchManager.getSearchListByLastAccessDate(TEST_INT_ID);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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
		searchManager.getSearchListByLastAccessDate(TEST_INT_ID);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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
		searchManager.getSearchListByLastAccessDate(TEST_INT_ID);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
	public void testGetSearchNullException() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.getSearchById((BigInteger) any, entityManager);
				result = null;
			}
		};
		searchManager.getSearch(TEST_ID);
	}

	@Test
	public void testGetSearchParamByName() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				TenantContext.getContext();
				result = tenantInfo;
				persistenceManager.getEntityManager((TenantInfo) any);
				EmAnalyticsObjectUtil.getSearchParamByName((BigInteger) any, anyString, entityManager);
				result = "param_value";
			}
		};
		searchManager.getSearchParamByName(BigInteger.ONE, "widget_viewmodel");
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
	public void testGetSearchParamByNameException() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				TenantContext.getContext();
				result = new Exception(throwable);
			}
		};
		searchManager.getSearchParamByName(BigInteger.ONE, "widget_viewmodel");
	}

	@Test
	public void testGetSearchWithoutOwner() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.findEmSearchByIdWithoutOwner((BigInteger) any, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getDeleted();
				result = 0L;
			}
		};
		searchManager.getSearchWithoutOwner(TEST_ID);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
	public void testGetSearchWithoutOwnerNullException() throws EMAnalyticsFwkException
	{
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.findEmSearchByIdWithoutOwner((BigInteger) any, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getDeleted();
				result = 1L;
			}
		};
		searchManager.getSearchWithoutOwner(TEST_ID);
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
				query.setParameter(anyString, (BigInteger) any);
				result = query;
				query.getResultList();
				result = emAnalyticsSearch;
				TenantContext.getContext();
				result = tenantInfo;
			}
		};
		searchManager.getSystemSearchListByCategoryId(TEST_ID);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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
				query.setParameter(anyString, (BigInteger) any);
				result = query;
				query.getResultList();
				result = new Exception(throwable);
				TenantContext.getContext();
				result = tenantInfo;
			}
		};
		searchManager.getSystemSearchListByCategoryId(TEST_ID);
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
				query.setParameter(anyString, (BigInteger) any);
				result = query;
				query.getResultList();
				result = emAnalyticsSearch;
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
		searchManager.getWidgetListByCategoryId(TEST_ID);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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
				query.setParameter(anyString, (BigInteger) any);
				result = query;
				query.getResultList();
				result = new Exception(throwable);
				TenantContext.getContext();
				result = tenantInfo;
				tenantInfo.getUsername();
				result = "userName";
			}
		};
		searchManager.getWidgetListByCategoryId(TEST_ID);
	}

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
	public void testGetWidgetListByProviderNames() throws EMAnalyticsFwkException
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

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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
				EmAnalyticsObjectUtil.findEmSearchByIdWithoutOwner((BigInteger) any, entityManager);
				result = null;
			}
		};
		searchManager.getWidgetScreenshotById(TEST_ID);
	}

	@Test
	public void testGetWidgetScreenshotByIdNotNull() throws Exception
	{
		final Set<EmAnalyticsSearchParam> emAnalyticsSearchParamSet = new HashSet<>();
		EmAnalyticsSearchParam emAnalyticsSearchParam = new EmAnalyticsSearchParam();
		emAnalyticsSearchParam.setName("WIDGET_SOURCE");
		emAnalyticsSearchParam.setParamValueStr("widget_source");
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
		new Expectations() {
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				TenantContext.getContext();
				result = tenantInfo;
				persistenceManager.getEntityManager((TenantInfo) any);
				result = entityManager;
				EmAnalyticsObjectUtil.findEmSearchByIdWithoutOwner((BigInteger) any, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getDeleted();
				result = BigInteger.ZERO;
				emAnalyticsSearch.getId();
				result = BigInteger.ONE;
				emAnalyticsSearch.getOwner();
				result = "Oracle";
				emAnalyticsSearch.getCreationDate();
				result = new Date();
				emAnalyticsSearch.getLastModifiedBy();
				result = "Oracle";
				emAnalyticsSearch.getEmAnalyticsCategory();
				result = emAnalyticsCategory;
				emAnalyticsCategory.getCategoryId();
				result = BigInteger.ONE;
				emAnalyticsSearch.getEmAnalyticsFolder();
				result = emAnalyticsFolder;
				emAnalyticsFolder.getFolderId();
				result = BigInteger.ONE;
				emAnalyticsSearch.getIsWidget();
				result = 1;
				emAnalyticsSearch.getEmAnalyticsSearchParams();
				result = emAnalyticsSearchParamSet;
				emAnalyticsSearch.getWIDGET_SOURCE();
				result = "widget_source";
				emAnalyticsSearch.getWIDGETGROUPNAME();
				result = "widget_group_name";
				emAnalyticsSearch.getWIDGETSCREENSHOTHREF();
				result = "wodget_screen_shot_href";
				emAnalyticsSearch.getWIDGETICON();
				result = "widget_icon";
				emAnalyticsSearch.getWIDGETKOCNAME();
				result = "widget_koc_name";
				emAnalyticsSearch.getWIDGETVIEWMODEL();
				result = "widget_view_model";
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
				CategoryManagerImpl.createCategoryObject((EmAnalyticsCategory) any, null);
				result = category;
			}
		};
		searchManager.getWidgetScreenshotById(BigInteger.ONE);
	}

	@Test(expectedExceptions = { NullPointerException.class })
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
				result = BigInteger.ONE;
				EmAnalyticsObjectUtil.getSearchById((BigInteger) any, entityManager);
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
				result = BigInteger.ONE;
				EmAnalyticsObjectUtil.getSearchById((BigInteger) any, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getEmAnalyticsCategory();
				result = emAnalyticsCategory;
				emAnalyticsCategory.getCategoryId();
				returns(new BigInteger("2223"), new BigInteger("1111"));
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
				result = BigInteger.ONE;
				EmAnalyticsObjectUtil.getSearchById((BigInteger) any, entityManager);
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
				result = BigInteger.ONE;
				EmAnalyticsObjectUtil.getSearchById((BigInteger) any, entityManager);
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
				result = BigInteger.ONE;
				EmAnalyticsObjectUtil.getSearchById((BigInteger) any, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getSystemSearch();
				result = new BigDecimal(1333);
				search.getFolderId();
				result = BigInteger.ONE;
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
				result = new BigInteger("3333");
				importSearchImpl.getCategoryDetails();
				result = new Object();
				search.getId();
				result = BigInteger.ONE;
				EmAnalyticsObjectUtil.getSearchById((BigInteger) any, entityManager);
				result = emAnalyticsSearch;
				emAnalyticsSearch.getSystemSearch();
				result = new BigDecimal(1333);
				EmAnalyticsObjectUtil.getFolderById((BigInteger) any, entityManager);
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
				EmAnalyticsObjectUtil.getSearchById((BigInteger) any, entityManager);
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
				query.setParameter(anyString, (BigInteger) any);
				result = query;
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
	public void testSaveMultipleSearchSearchGetIdST0ObjFolderImplCateObjCategoryImplGetFolderIdNullGetCategoryIdNull()
			throws Exception
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
				query.setParameter(anyString, (BigInteger) any);
				result = query;
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
				result = new BigInteger("11");
				importSearchImpl.getCategoryDetails();
				result = new Object();
				EmAnalyticsObjectUtil.getSearchById((BigInteger) any, entityManager);
				result = null;
				entityManager.createNamedQuery(anyString);
				result = query;
				query.setParameter(anyString, (BigInteger) any);
				result = query;
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
				result = new BigInteger("11");
				importSearchImpl.getCategoryDetails();
				result = new Object();
				EmAnalyticsObjectUtil.getSearchById((BigInteger) any, entityManager);
				result = null;
				entityManager.createNamedQuery(anyString);
				result = query;
				query.setParameter(anyString, (BigInteger) any);
				result = query;
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

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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

	@Test(expectedExceptions = { EMAnalyticsFwkException.class })
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
	public void testDeleteSearchByName() throws EMAnalyticsFwkException {
		final List<EmAnalyticsSearch> emAnalyticsSearchList = new ArrayList<>();
		emAnalyticsSearchList.add(emAnalyticsSearch);
		new Expectations(){
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				TenantContext.getContext();
				result = tenantInfo;
				persistenceManager.getEntityManager((TenantInfo)any);
				result = entityManager;
				EmAnalyticsObjectUtil.getSearchByNameForDelete(anyString, (EntityManager)any);
				result = emAnalyticsSearch;
				entityManager.getTransaction().begin();
				emAnalyticsSearch.getId();
				result =1L;
				entityManager.merge((EmAnalyticsSearch)any);
				entityManager.getTransaction().commit();;
				entityManager.close();
				EmAnalyticsObjectUtil.getSearchListByNamePatternForDelete(anyString, (EntityManager)any);
				result = emAnalyticsSearchList;
			}
		};
		searchManager.deleteSearchByName("searchName", true);
		searchManager.deleteSearchByName("searchName", false);
	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testDeleteSearchByNameResultNull() throws EMAnalyticsFwkException {
		new Expectations(){
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				TenantContext.getContext();
				result = tenantInfo;
				persistenceManager.getEntityManager((TenantInfo)any);
				result = entityManager;
				EmAnalyticsObjectUtil.getSearchByNameForDelete(anyString, (EntityManager)any);
				result = null;
			}
		};
		searchManager.deleteSearchByName("searchName", true);
	}
	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testDeleteSearchByNameResultEmpty() throws EMAnalyticsFwkException {
		new Expectations(){
			{
				PersistenceManager.getInstance();
				result = persistenceManager;
				TenantContext.getContext();
				result = tenantInfo;
				persistenceManager.getEntityManager((TenantInfo)any);
				result = entityManager;
				EmAnalyticsObjectUtil.getSearchListByNamePatternForDelete(anyString, (EntityManager)any);
				result = Collections.emptyList();
			}
		};
		searchManager.deleteSearchByName("searchName", false);
	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testDeleteSearchByNameException() throws EMAnalyticsFwkException {
		new Expectations(){
			{
				PersistenceManager.getInstance();
				result = new Exception(throwable);
			}
		};
		searchManager.deleteSearchByName("searchName", false);
	}
	
    @Test
    public void testGetSearchListWithoutOwnerByName() throws EMAnalyticsFwkException {
        final EmAnalyticsSearch emSearch = new EmAnalyticsSearch();
        new Expectations() {
            {
                PersistenceManager.getInstance();
                result = persistenceManager;
                persistenceManager.getEntityManager((TenantInfo) any);
                result = entityManager;
                EmAnalyticsObjectUtil.findEmSearchByNameWithoutOwner(anyString, entityManager);
                result = emSearch;
            }
        };
        searchManager.getSearchListWithoutOwnerByName("searchName");
    }
}

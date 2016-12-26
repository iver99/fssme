package oracle.sysman.emaas.savedsearch;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.IdGenerator;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.ZDTContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot.ScreenshotData;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Widget;
import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.WidgetChangeNotification;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchManagerTest extends BaseTest
{
	
	private static final String MODULE_NAME = "SavedSearchService"; // application service name
	private final static String ACTION_NAME = "SearchManagerTest";//current class name

	public static Category createTestCategory(CategoryManager cm, Folder folder, String name) throws EMAnalyticsFwkException
	{
		Category cat = new CategoryImpl();
		cat.setName(name);
		String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
		cat.setOwner(currentUser);
		cat.setProviderName("TestProviderName");
		cat.setProviderVersion("TestProviderVersion");
		cat.setProviderDiscovery("TestProviderDiscovery");
		cat.setProviderAssetRoot("TestProviderAssetRoot");
		if (folder != null) {
			cat.setDefaultFolderId(folder.getParentId());
		}
		cat = cm.saveCategory(cat);
		return cat;
	}

	public static Folder createTestFolder(FolderManager fm, String name) throws EMAnalyticsFwkException
	{
		Folder folder = new FolderImpl();
		folder.setName("FolderTest" + System.currentTimeMillis());
		folder.setDescription("TestFolderDescription");
		folder.setUiHidden(false);
		folder = fm.saveFolder(folder);
		return folder;
	}

	public static Search createTestSearch(SearchManager sm, Folder folder, Category cat, String name)
			throws EMAnalyticsFwkException
	{
		Search search = sm.createNewSearch();
		search.setId(IdGenerator.getIntUUID(ZDTContext.getRequestId()));
		search.setDescription("testing purpose");
		search.setName(name);
		search.setFolderId(folder.getId());
		search.setCategoryId(cat.getId());
		search = sm.saveSearch(search);
		return search;
	}

	public static Search createTestWidget(SearchManager sm, Folder folder, Category cat, String name, String base64ScreenShot)
			throws EMAnalyticsFwkException
	{
		Search widget = sm.createNewSearch();
		widget.setId(IdGenerator.getIntUUID(ZDTContext.getRequestId()));
		widget.setDescription("testing purpose");
		widget.setName(name);
		widget.setFolderId(folder.getId());
		widget.setCategoryId(cat.getId());
		widget.setIsWidget(true);

		List<SearchParameter> widgetParams = new ArrayList<SearchParameter>();
		SearchParameter wp1 = new SearchParameter();
		SearchParameter wp2 = new SearchParameter();
		SearchParameter wp3 = new SearchParameter();
		SearchParameter wp4 = new SearchParameter();
		SearchParameter wp5 = new SearchParameter();
		SearchParameter wp6 = new SearchParameter();
		wp1.setName("WIDGET_VIEWMODEL");
		wp1.setType(ParameterType.STRING);
		wp1.setValue("dependencies/widgets/iFrame/js/widget-iframe");
		wp2.setName("WIDGET_KOC_NAME");
		wp2.setType(ParameterType.STRING);
		wp2.setValue("DF_V1_WIDGET_IFRAME");
		wp3.setName("WIDGET_TEMPLATE");
		wp3.setType(ParameterType.STRING);
		wp3.setValue("dependencies/widgets/iFrame/widget-iframe.html");
		wp4.setName("PROVIDER_VERSION");
		wp4.setType(ParameterType.STRING);
		wp4.setValue("0.1");
		wp5.setName("PROVIDER_NAME");
		wp5.setType(ParameterType.STRING);
		wp5.setValue("DB Analytics");
		wp6.setName("PROVIDER_ASSET_ROOT");
		wp6.setType(ParameterType.STRING);
		wp6.setValue("home");
		widgetParams.add(wp1);
		widgetParams.add(wp2);
		widgetParams.add(wp3);
		widgetParams.add(wp4);
		widgetParams.add(wp5);
		widgetParams.add(wp6);
		if (base64ScreenShot != null) {
			SearchParameter wp7 = new SearchParameter();
			wp7.setName("WIDGET_VISUAL");
			wp7.setType(ParameterType.CLOB);
			wp7.setValue(base64ScreenShot);
			widgetParams.add(wp7);
		}

		widget.setParameters(widgetParams);
		widget = sm.saveSearch(widget);
		return widget;
	}

	@BeforeClass
	public void initTenantDetails() throws IOException
	{
		TenantContext.setContext(
				new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
						TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
//		RegistrationManager.getInstance().initComponent();	// comment out temporary since EMCPSSF-397
	}

	@AfterClass
	public void removeTenantDetails()
	{
		TenantContext.clearContext();
	}

	/*
	 * Use this method to create 1000000 searches for performance test purpose
	 */
	//@Test
	public void testCreateSearchPerformance() throws EMAnalyticsFwkException
	{
		FolderManagerImpl fm = FolderManagerImpl.getInstance();
		CategoryManager cm = CategoryManagerImpl.getInstance();
		SearchManager sm = SearchManager.getInstance();
		//Steps to generate data for load testing
		//1. uncomment @Test of this test
		//2. change below count to desired ones
		//3. change TestNg.properties to connect to target DB
		//4. run this test to generate data
		//Note: please do NOT push your change to generate data
		final int categoryCount = 1000; //change this to your desired count
		final int folderCount = 10000;//change this to your desired count
		final int searchCount = 1000000;//change this to your desired count
		for (int i = 0; i < categoryCount; i++) {
			Category cat = SearchManagerTest.createTestCategory(cm, null, "CategoryTest " + i);
			for (int j = 0; j < folderCount / categoryCount; j++) {
				Folder folder = SearchManagerTest.createTestFolder(fm, "FolderTest " + i + "-" + j);
				for (int k = 0; k < searchCount / folderCount; k++) {
					SearchManagerTest.createTestSearch(sm, folder, cat, "Search for performance " + i + "-" + j + "-" + k);
				}
			}
		}
	}

	@Test
	public void testCRUDOnSearchLastAccess() throws EMAnalyticsFwkException
	{
		FolderManagerImpl fm = FolderManagerImpl.getInstance();
		Folder folder = SearchManagerTest.createTestFolder(fm, "FolderTest" + System.currentTimeMillis());

		CategoryManager cm = CategoryManagerImpl.getInstance();
		Category cat = SearchManagerTest.createTestCategory(cm, folder, "CategoryTest" + System.currentTimeMillis());

		SearchManager sm = SearchManager.getInstance();
		Search search = SearchManagerTest.createTestSearch(sm, folder, cat, "Search Name " + System.currentTimeMillis());

		long createdDate = search.getLastAccessDate().getTime();
		search.setName("Search Accessed On " + System.currentTimeMillis());

		search = sm.editSearch(search);
		long editedDate = search.getLastAccessDate().getTime();
		AssertJUnit.assertTrue(createdDate < editedDate);
		sm.deleteSearch(search.getId(), true);
		cm.deleteCategory(cat.getId(), true);
		fm.deleteFolder(folder.getId(), true);
	}

	@Test
	public void testGetSearchByName() throws EMAnalyticsFwkException
	{
		FolderManagerImpl fm = FolderManagerImpl.getInstance();
		Folder folder = SearchManagerTest.createTestFolder(fm, "FolderTest" + System.currentTimeMillis());

		CategoryManager cm = CategoryManagerImpl.getInstance();
		Category cat = SearchManagerTest.createTestCategory(cm, folder, "CategoryTest" + System.currentTimeMillis());

		SearchManager sm = SearchManager.getInstance();
		String searchName = "Search Name " + System.currentTimeMillis();
		Search search = SearchManagerTest.createTestSearch(sm, folder, cat, searchName);

		Search queried = null;

		// soft deletion test
		try {
			queried = sm.getSearchByName(searchName, folder.getId());
		}
		catch (EMAnalyticsFwkException e) {
			AssertJUnit.fail();
		}

		assertSearchEquals(search, queried);

		sm.deleteSearch(search.getId(), true);
		cm.deleteCategory(cat.getId(), true);
		fm.deleteFolder(folder.getId(), true);
	}

	@Test
	public void testGetSearchByName_noresult()
	{
		SearchManager sm = SearchManager.getInstance();
		String searchName = "Search Name " + System.currentTimeMillis();
		try {
			Search queried = sm.getSearchByName(searchName, BigInteger.ONE);
			AssertJUnit.assertNull(queried);
		}
		catch (EMAnalyticsFwkException e) {
			AssertJUnit.fail();
		}

	}

	@Test
	public void testGetSearchListByCategoryId() throws EMAnalyticsFwkException
	{
		FolderManagerImpl fm = FolderManagerImpl.getInstance();
		Folder folder = SearchManagerTest.createTestFolder(fm, "FolderTest" + System.currentTimeMillis());

		CategoryManager cm = CategoryManagerImpl.getInstance();
		Category cat = SearchManagerTest.createTestCategory(cm, folder, "CategoryTest" + System.currentTimeMillis());

		SearchManager sm = SearchManager.getInstance();
		Search search1 = SearchManagerTest.createTestSearch(sm, folder, cat, "Search1 Name " + System.currentTimeMillis());
		Search search2 = SearchManagerTest.createTestSearch(sm, folder, cat, "Search2 Name " + System.currentTimeMillis());

		List<Search> queried = null;

		// soft deletion test
		try {
			queried = sm.getSearchListByCategoryId(cat.getId());
		}
		catch (EMAnalyticsFwkException e) {
			AssertJUnit.fail();
		}

		AssertJUnit.assertNotNull(queried);
		if (queried != null) {
			AssertJUnit.assertEquals(2, queried.size());
			assertSearchEquals(search1, queried.get(0));
			assertSearchEquals(search2, queried.get(1));
		}
		sm.deleteSearch(search1.getId(), true);
		sm.deleteSearch(search2.getId(), true);
		cm.deleteCategory(cat.getId(), true);
		fm.deleteFolder(folder.getId(), true);
	}

	@Test
	public void testGetSearchListByFolderId() throws EMAnalyticsFwkException
	{
		FolderManagerImpl fm = FolderManagerImpl.getInstance();
		Folder folder = SearchManagerTest.createTestFolder(fm, "FolderTest" + System.currentTimeMillis());

		CategoryManager cm = CategoryManagerImpl.getInstance();
		Category cat = SearchManagerTest.createTestCategory(cm, folder, "CategoryTest" + System.currentTimeMillis());

		SearchManager sm = SearchManager.getInstance();
		Search search1 = SearchManagerTest.createTestSearch(sm, folder, cat, "Search1 Name " + System.currentTimeMillis());
		Search search2 = SearchManagerTest.createTestSearch(sm, folder, cat, "Search2 Name " + System.currentTimeMillis());

		List<Search> queried = null;
		// soft deletion test
		try {
			queried = sm.getSearchListByFolderId(folder.getId());
		}
		catch (EMAnalyticsFwkException e) {
			AssertJUnit.fail(e.getLocalizedMessage());
		}

		AssertJUnit.assertNotNull(queried);
		if (queried != null) {
			AssertJUnit.assertEquals(2, queried.size());
			String searchName = queried.get(0).getName();
			if (searchName != null && searchName.equals(search1.getName())) {
				assertSearchEquals(search1, queried.get(0));
				assertSearchEquals(search2, queried.get(1));
			}
			else {
				assertSearchEquals(search2, queried.get(0));
				assertSearchEquals(search1, queried.get(1));
			}
		}

		sm.deleteSearch(search1.getId(), true);
		sm.deleteSearch(search2.getId(), true);
		cm.deleteCategory(cat.getId(), true);
		fm.deleteFolder(folder.getId(), true);
	}

	@Test
	public void testGetSearchListByLastAccessDate() throws EMAnalyticsFwkException
	{
		FolderManagerImpl fm = FolderManagerImpl.getInstance();
		Folder folder = SearchManagerTest.createTestFolder(fm, "FolderTest" + System.currentTimeMillis());

		CategoryManager cm = CategoryManagerImpl.getInstance();
		Category cat = SearchManagerTest.createTestCategory(cm, folder, "CategoryTest" + System.currentTimeMillis());

		SearchManager sm = SearchManager.getInstance();
		Search search1 = SearchManagerTest.createTestSearch(sm, folder, cat, "Search1 Name " + System.currentTimeMillis());
		Search search2 = SearchManagerTest.createTestSearch(sm, folder, cat, "Search2 Name " + System.currentTimeMillis());

		List<Search> searches = sm.getSearchListByLastAccessDate(2);
		AssertJUnit.assertNotNull(searches);
		AssertJUnit.assertEquals(2, searches.size());
		AssertJUnit.assertEquals(search2.getId(), searches.get(0).getId());
		AssertJUnit.assertEquals(search1.getId(), searches.get(1).getId());

		search1.setName("search1 name updated");
		sm.editSearch(search1);
		searches = sm.getSearchListByLastAccessDate(2);
		AssertJUnit.assertNotNull(searches);
		AssertJUnit.assertEquals(2, searches.size());
		AssertJUnit.assertEquals(search1.getId(), searches.get(0).getId());
		AssertJUnit.assertEquals(search2.getId(), searches.get(1).getId());

		sm.deleteSearch(search2.getId(), true);
		sm.deleteSearch(search1.getId(), true);
		cm.deleteCategory(cat.getId(), true);
		fm.deleteFolder(folder.getId(), true);
	}

	@Test
	public void testGetWidgetListByCategoryId() throws EMAnalyticsFwkException
	{
		FolderManagerImpl fm = FolderManagerImpl.getInstance();
		Folder folder = SearchManagerTest.createTestFolder(fm, "FolderTest" + System.currentTimeMillis());

		CategoryManager cm = CategoryManagerImpl.getInstance();
		Category cat = SearchManagerTest.createTestCategory(cm, folder, "CategoryTest" + System.currentTimeMillis());

		SearchManager sm = SearchManager.getInstance();
		Search widget1 = SearchManagerTest.createTestWidget(sm, folder, cat, "Widget1 Name " + System.currentTimeMillis(), null);
		Search widget2 = SearchManagerTest.createTestWidget(sm, folder, cat, "Widget2 Name " + System.currentTimeMillis(), null);

		List<Search> queried = null;

		// soft deletion test
		try {
			queried = sm.getWidgetListByCategoryId(cat.getId());
		}
		catch (EMAnalyticsFwkException e) {
			AssertJUnit.fail();
		}

		AssertJUnit.assertNotNull(queried);
		if (queried != null) {
			AssertJUnit.assertEquals(2, queried.size());
		}

		Search savedWidget1 = null;
		Search savedWidget2 = null;
		if (queried != null) {
			for (Search widget : queried) {
				if (widget1.getId().equals(widget.getId())) {
					savedWidget1 = widget;
				}
				else if (widget2.getId().equals(widget.getId())) {
					savedWidget2 = widget;
				}
			}
		}
		assertSearchEquals(widget1, savedWidget1);
		assertSearchEquals(widget2, savedWidget2);

		sm.deleteSearch(widget1.getId(), true);
		sm.deleteSearch(widget2.getId(), true);
		cm.deleteCategory(cat.getId(), true);
		fm.deleteFolder(folder.getId(), true);
	}

	@Test
	public void testGetWidgetListByProviderNames(@Mocked final WidgetChangeNotification anyWidgetChangeNotification)
			throws EMAnalyticsFwkException
	{
		FolderManagerImpl fm = FolderManagerImpl.getInstance();
		Folder folder = SearchManagerTest.createTestFolder(fm, "FolderTest" + System.currentTimeMillis());

		CategoryManager cm = CategoryManagerImpl.getInstance();
		Category cat1 = SearchManagerTest.createTestCategory(cm, folder, "CategoryTest1" + System.currentTimeMillis());
		Category cat2 = SearchManagerTest.createTestCategory(cm, folder, "CategoryTest2" + System.currentTimeMillis());

		SearchManager sm = SearchManager.getInstance();
		List<Widget> queried = null;
		List<String> providers = new ArrayList<String>();
		providers.add(cat1.getProviderName());
		// query and get original size
		queried = sm.getWidgetListByProviderNames(true, providers, null);
		int origAmount = queried.size();

		Search widget1 = SearchManagerTest.createTestWidget(sm, folder, cat1, "Widget1 Name " + System.currentTimeMillis(), null);
		Search widget2 = SearchManagerTest.createTestWidget(sm, folder, cat1, "Widget2 Name " + System.currentTimeMillis(), null);
		Search widget3 = SearchManagerTest.createTestWidget(sm, folder, cat2, "Widget3 Name " + System.currentTimeMillis(), null);

		try {
			queried = sm.getWidgetListByProviderNames(true, providers, null);
		}
		catch (EMAnalyticsFwkException e) {
			AssertJUnit.fail(e.getLocalizedMessage());
		}

		AssertJUnit.assertNotNull(queried);
		if (queried != null) {
			AssertJUnit.assertEquals(origAmount + 3, queried.size());
		}

		Widget savedWidget1 = null;
		Widget savedWidget2 = null;
		Widget savedWidget3 = null;
		if (queried != null) {
			for (Widget widget : queried) {
				if (widget1.getId().equals(widget.getId())) {
					savedWidget1 = widget;
				}
				else if (widget2.getId().equals(widget.getId())) {
					savedWidget2 = widget;
				}
				else if (widget3.getId().equals(widget.getId())) {
					savedWidget3 = widget;
				}
			}
		}
		assertSearchEquals(widget1, savedWidget1);
		assertSearchEquals(widget2, savedWidget2);
		assertSearchEquals(widget3, savedWidget3);
		AssertJUnit.assertNotNull(savedWidget1);
		AssertJUnit.assertNotNull(savedWidget1.getCategory());
		AssertJUnit.assertEquals(cat1.getId(), savedWidget1.getCategory().getId());
		AssertJUnit.assertNotNull(savedWidget2);
		AssertJUnit.assertNotNull(savedWidget2.getCategory());
		AssertJUnit.assertEquals(cat1.getId(), savedWidget2.getCategory().getId());
		AssertJUnit.assertNotNull(savedWidget3);
		AssertJUnit.assertNotNull(savedWidget3.getCategory());
		AssertJUnit.assertEquals(cat2.getId(), savedWidget3.getCategory().getId());

		// widgetGroupId specified
		savedWidget1 = null;
		savedWidget2 = null;
		savedWidget3 = null;
		try {
			queried = sm.getWidgetListByProviderNames(false, providers, String.valueOf(cat1.getId()));
			for (Widget widget : queried) {
				if (widget1.getId().equals(widget.getId())) {
					savedWidget1 = widget;
				}
				if (widget2.getId().equals(widget.getId())) {
					savedWidget2 = widget;
				}
				if (widget3.getId().equals(widget.getId())) {
					AssertJUnit.fail("Failure: widgets from other widgetGroups are not expected!");
				}
			}
		}
		catch (EMAnalyticsFwkException e) {
			AssertJUnit.fail();
		}
		assertSearchEquals(widget1, savedWidget1);
		assertSearchEquals(widget2, savedWidget2);

		// not include dashboard ineligible widgets
		SearchParameter wp1 = new SearchParameter();
		wp1.setName(SearchManager.SEARCH_PARAM_DASHBOARD_INELIGIBLE);
		wp1.setType(ParameterType.STRING);
		wp1.setValue("1");
		widget2.getParameters().add(wp1);
		new Expectations() {
			{// as there is no 3n environment for unit test cases
				anyWidgetChangeNotification.notify((Search) any, (Date) any);
			}
		};
		sm.editSearch(widget2);
		savedWidget1 = null;

		// not include category ineligible widgets
		/*Parameter pm = new Parameter();
		pm.setName(SearchManager.SEARCH_PARAM_DASHBOARD_INELIGIBLE);
		pm.setType(ParameterType.STRING);
		pm.setValue("1");
		if (cat2.getParameters() == null) {
			cat2.setParameters(new ArrayList<Parameter>());
		}
		cat2.getParameters().add(pm);
		cm.editCategory(cat2);
		
		try {
			queried = sm.getWidgetListByProviderNames(false, providers, null);
			for (Widget widget : queried) {
				if (widget1.getId().equals(widget.getId())) {
					savedWidget1 = widget;
				}
				if (widget2.getId().equals(widget.getId())) {
					AssertJUnit.fail("Failure: inegiligible widgets are not expected!");
				}
				if (widget3.getId().equals(widget.getId())) {
					AssertJUnit.fail("Failure: widgets from inegiligible categories are not expected!");
				}
			}
		}
		catch (EMAnalyticsFwkException e) {
			AssertJUnit.fail();
		}
		assertSearchEquals(widget1, savedWidget1);*/

		// not include ineligible widgets from categories

		sm.deleteSearch(widget1.getId(), true);
		sm.deleteSearch(widget2.getId(), true);
		sm.deleteSearch(widget3.getId(), true);
		cm.deleteCategory(cat1.getId(), true);
		cm.deleteCategory(cat2.getId(), true);
		fm.deleteFolder(folder.getId(), true);
	}

	@Test
	public void testGetWidgetScreenshotById() throws EMAnalyticsFwkException
	{
		FolderManagerImpl fm = FolderManagerImpl.getInstance();
		Folder folder = SearchManagerTest.createTestFolder(fm, "FolderTest" + System.currentTimeMillis());

		CategoryManager cm = CategoryManagerImpl.getInstance();
		Category cat = SearchManagerTest.createTestCategory(cm, folder, "CategoryTest" + System.currentTimeMillis());

		SearchManager sm = SearchManager.getInstance();
		String screenshot = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAL4AAACMCAIAAABNpIRsAAAYKklEQVR4AdxSBRIDIQy8";
		Search widget1 = SearchManagerTest.createTestWidget(sm, folder, cat, "WidgetWithScreenshot " + System.currentTimeMillis(),
				screenshot);
		Search widget2 = SearchManagerTest.createTestWidget(sm, folder, cat,
				"WidgetWithoutScreenshot " + System.currentTimeMillis(), null);

		try {
			ScreenshotData screnshotForWidget1 = sm.getWidgetScreenshotById(widget1.getId());
			ScreenshotData screnshotForWidget2 = sm.getWidgetScreenshotById(widget2.getId());
			AssertJUnit.assertEquals(screnshotForWidget1.getScreenshot(), screenshot);
			AssertJUnit.assertEquals(screnshotForWidget2.getScreenshot(), SearchManager.DEFAULT_WIDGET_SCREENSHOT);
		}
		catch (EMAnalyticsFwkException e) {
			AssertJUnit.fail();
		}

		sm.deleteSearch(widget1.getId(), true);
		sm.deleteSearch(widget2.getId(), true);
		cm.deleteCategory(cat.getId(), true);
		fm.deleteFolder(folder.getId(), true);
	}

	/*
	 * Use this method to see how much time is needed to query searches with given folder ID from 1 million searches with multi-threads
	 * simultaneously.
	 * Several manual steps are needed before run this method
	 * Note: (!!!!!!!!IMPORTANT!!!!!!!!)
	 * 1. use testCreateSearchPerformance() to create 1 million searches before running into this method
	 * 2. query your unit test database and change the folderId in the method
	 * 3. un-comment the @Test and run with JUnit
	 */
	//	@Test
	public void testQueryPerformanceMultiThreadsSearchByFolderId() throws EMAnalyticsFwkException, InterruptedException
	{
		final BigInteger folderId = new BigInteger("101790");

		// Uncomment below code to clearing Entity Manager before the test
		/*		final EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
				emf.createEntityManager().clear();
		 */
		SearchManagerTestMockup.executeRepeatedly(100, 10, new Runnable() {
			@Override
			public void run()
			{
				try {
					final SearchManager sm = SearchManager.getInstance();
					List<Search> searches = sm.getSearchListByFolderId(folderId);
				}
				catch (EMAnalyticsFwkException e) {
					AssertJUnit.fail(e.getLocalizedMessage());
				}
			}

		}, null, null);

		final SearchManager sm = SearchManager.getInstance();
		List<Search> searches = sm.getSearchListByFolderId(folderId);
	}

	/*
	 * Use this method to see how much time is needed to query a search from 1 million searches multi-threads
	 * simultaneously.
	 *
	 * Several manual steps are needed before run this method
	 * Note: (!!!!!!!!IMPORTANT!!!!!!!!)
	 * 1. use testCreateSearchPerformance() to create 1 million searches before running into this method
	 * 2. query your unit test database and change the searchId(or availableSearchIDs) in the method
	 * 3. un-comment the @Test and run with JUnit
	 */
	//	@Test
	public void testQueryPerformanceMultiThreadsSearchById() throws EMAnalyticsFwkException, InterruptedException
	{
		// Uncomment below code to clearing Entity Manager before the test
		/*		final EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
				emf.createEntityManager().clear();
		 */
		final SearchManager sm = SearchManager.getInstance();
		final BigInteger[] availableSearchIDs = {new BigInteger("10139"), new BigInteger("10141"), new BigInteger("10164"), 
				new BigInteger("10560"), new BigInteger("10571"), new BigInteger("10872"), new BigInteger("10876"), 
				new BigInteger("10525"), new BigInteger("11117"), new BigInteger("11129")};

		SearchManagerTestMockup.executeRepeatedly(100, 10, new Runnable() {
			@Override
			public void run()
			{
				try {
					SecureRandom r = SecureRandom.getInstance("SHA1PRNG");
					int randomSearchIndex = r.nextInt(availableSearchIDs.length);
					Search search = sm.getSearch(availableSearchIDs[randomSearchIndex]);
					AssertJUnit.assertNotNull(search);
				} catch (NoSuchAlgorithmException e1) {
					AssertJUnit.fail(e1.getLocalizedMessage());
				} catch (EMAnalyticsFwkException e) {
					AssertJUnit.fail(e.getLocalizedMessage());
				}
			}
		}, null, null);
	}

	/*
	 * Use this method to see how much time is needed to query a search by name from 1 million searches multi-threads
	 * simultaneously.
	 *
	 * Several manual steps are needed before run this method
	 * Note: (!!!!!!!!IMPORTANT!!!!!!!!)
	 * 1. use testCreateSearchPerformance() to create 1 million searches before running into this method
	 * 2. query your unit test database and change the searchName/folderId in the method
	 * 3. un-comment the @Test and run with JUnit
	 */
	//	@Test
	public void testQueryPerformanceMultiThreadsSearchByName() throws EMAnalyticsFwkException, InterruptedException
	{
		final String searchName = "Search for performance 94 1407381675833";
		final BigInteger folderId = new BigInteger("1740");

		// Uncomment below code to clearing Entity Manager before the test
		/*final EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		emf.createEntityManager().clear();*/

		final SearchManager sm = SearchManager.getInstance();

		SearchManagerTestMockup.executeRepeatedly(100, 10, new Runnable() {
			@Override
			public void run()
			{
				try {
					/*Search search = */sm.getSearchByName(searchName, folderId);
					//					AssertJUnit.assertNotNull(search);
				}
				catch (EMAnalyticsFwkException e) {
					AssertJUnit.fail(e.getLocalizedMessage());
				}
			}

		}, null, null);
	}

	/*
	 * Use this method to see how much time is needed to query a search from 1 million searches.
	 * Several manual steps are needed before run this method
	 * Note: (!!!!!!!!IMPORTANT!!!!!!!!)
	 * 1. use testCreateSearchPerformance() to create 1 million searches before running into this method
	 * 2. query your unit test database and change the searchId, searchName and folderId in the method
	 * 3. un-comment the @Test and run with JUnit
	 */
	//	@Test
	public void testQueryPerformanceSingleSearchById() throws EMAnalyticsFwkException
	{
		BigInteger searchId = new BigInteger("990007");
		SearchManager sm = SearchManager.getInstance();
		//		Search search = SearchManagerTest
		//				.createTestSearch(sm, folder, cat, "Search Name for query " + System.currentTimeMillis());

		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		emf.createEntityManager().clear();
		//		emf.getCache().evictAll();

		long start = System.currentTimeMillis();
		//sm.getSearch(search.getId());
		sm.getSearch(searchId);
		long end = System.currentTimeMillis();
	}

	/*
	 * Use this method to see how much time is needed to query a search from 1 million searches.
	 * Several manual steps are needed before run this method
	 * Note: (!!!!!!!!IMPORTANT!!!!!!!!)
	 * 1. use testCreateSearchPerformance() to create 1 million searches before running into this method
	 * 2. query your unit test database and change the searchId, searchName and folderId in the method, as
	 * it's found the JPA cache (or database cache?) have extreme impact on the result
	 * 3. un-comment the @Test and run with JUnit
	 */
	//@Test
	public void testQueryPerformanceSingleSearchByName() throws EMAnalyticsFwkException
	{
		String searchName = "Search for performance 978615 1407383176408";
		BigInteger folderId = new BigInteger("1740");

		//		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		//		emf.createEntityManager().clear();
		//		emf.getCache().evictAll();
		SearchManager sm = SearchManager.getInstance();

		long start = System.currentTimeMillis();
		//sm.getSearchByName(search.getName(), folder.getId());
		sm.getSearchByName(searchName, folderId);
		long end = System.currentTimeMillis();
	}

	/*
	 * Use this method to see how much time is needed to query a search from 1 million searches.
	 * Several manual steps are needed before run this method
	 * Note: (!!!!!!!!IMPORTANT!!!!!!!!)
	 * 1. use testCreateSearchPerformance() to create 1 million searches before running into this method
	 * 2. query your unit test database and change the searchId, searchName and folderId in the method, as
	 * it's found the JPA cache (or database cache?) have extreme impact on the result
	 * 3. un-comment the @Test and run with JUnit
	 */
	//@Test
	public void testQueryPerformanceSingleSearchListByFolderId() throws EMAnalyticsFwkException
	{
		BigInteger folderId = new BigInteger("1740");

		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		emf.createEntityManager().clear();
		//		emf.getCache().evictAll();

		SearchManager sm = SearchManager.getInstance();

		long start = System.currentTimeMillis();
		//List<Search> searches = sm.getSearchListByFolderId(folder.getId());
		List<Search> searches = sm.getSearchListByFolderId(folderId);
		long end = System.currentTimeMillis();
	}

	private void assertSearchEquals(Search expected, Search actual)
	{
		AssertJUnit.assertNotNull(actual);
		AssertJUnit.assertEquals(expected.getId(), actual.getId());
		AssertJUnit.assertEquals(expected.getName(), actual.getName());
		AssertJUnit.assertEquals(expected.getDescription(), actual.getDescription());
		AssertJUnit.assertEquals(expected.getOwner(), actual.getOwner());
		AssertJUnit.assertEquals(expected.getCategoryId(), actual.getCategoryId());
		AssertJUnit.assertEquals(expected.getFolderId(), actual.getFolderId());
		AssertJUnit.assertEquals(expected.getIsWidget(), actual.getIsWidget());
	}

}

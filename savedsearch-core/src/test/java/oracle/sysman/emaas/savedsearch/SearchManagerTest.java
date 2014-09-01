package oracle.sysman.emaas.savedsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsLastAccess;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsLastAccessPK;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

public class SearchManagerTest extends BaseTest
{
	// Important: keep this value the same with sequence step (value for 'INCREMENT BY') for EMS_ANALYTICS_SEARCH_SEQ
	private static final int SEQ_ALLOCATION_SIZE = 1;

	public static Category createTestCategory(CategoryManager cm, Folder folder, String name) throws EMAnalyticsFwkException
	{
		Category cat = new CategoryImpl();
		cat.setName(name);
		String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
		cat.setOwner(currentUser);
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
		search.setDescription("testing purpose");
		search.setName(name);
		search.setFolderId(folder.getId());
		search.setCategoryId(cat.getId());
		search = sm.saveSearch(search);
		return search;
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
		long start = System.currentTimeMillis();
		System.out.println("Start to create " + categoryCount + " categories, " + folderCount + " folders and " + searchCount
				+ " searches");
		for (int i = 0; i < categoryCount; i++) {
			Category cat = SearchManagerTest.createTestCategory(cm, null, "CategoryTest " + i);
			for (int j = 0; j < folderCount / categoryCount; j++) {
				Folder folder = SearchManagerTest.createTestFolder(fm, "FolderTest " + i + "-" + j);
				for (int k = 0; k < searchCount / folderCount; k++) {
					SearchManagerTest.createTestSearch(sm, folder, cat, "Search for performance " + i + "-" + j + "-" + k);
				}
			}
		}
		System.out.println("Total time to create " + categoryCount + " categories, " + folderCount + " folders and "
				+ searchCount + " searches is " + (System.currentTimeMillis() - start) / 1000 + " seconds");
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

		int searchId = search.getId().intValue();

		sm.deleteSearch(search.getId(), true);

		// ensure the last access for the search is deleted also
		EmAnalyticsLastAccess eala = getLastAccessForSearch(searchId);
		AssertJUnit.assertEquals(null, eala);

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
		AssertJUnit.assertEquals(2, queried.size());
		assertSearchEquals(search1, queried.get(0));
		assertSearchEquals(search2, queried.get(1));

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
			AssertJUnit.fail();
		}

		AssertJUnit.assertNotNull(queried);
		AssertJUnit.assertEquals(2, queried.size());
		assertSearchEquals(search1, queried.get(0));
		assertSearchEquals(search2, queried.get(1));

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
		final int folderId = 101790;

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
					e.printStackTrace();
					AssertJUnit.fail(e.getLocalizedMessage());
				}
			}

		}, null, null);

		final SearchManager sm = SearchManager.getInstance();
		List<Search> searches = sm.getSearchListByFolderId(folderId);
		System.out.println(searches.size() + " searches returned");
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
		final int searchId = 990007;

		// Uncomment below code to clearing Entity Manager before the test
		/*		final EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
				emf.createEntityManager().clear();
		 */
		final SearchManager sm = SearchManager.getInstance();
		final int[] availableSearchIDs = { 10139, 10141, 10164, 10560, 10571, 10872, 10876, 10525, 11117, 11129 };

		SearchManagerTestMockup.executeRepeatedly(100, 10, new Runnable() {
			@Override
			public void run()
			{
				Random r = new Random();
				int randomSearchIndex = r.nextInt(availableSearchIDs.length);
				try {
					Search search = sm.getSearch(availableSearchIDs[randomSearchIndex]);
					AssertJUnit.assertNotNull(search);
				}
				catch (EMAnalyticsFwkException e) {
					e.printStackTrace();
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
		final int folderId = 1740;

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
					e.printStackTrace();
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
		int searchId = 990007;
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
		System.out.println("Time spent to query search by ID from 1,000,000 searches: " + (end - start) + "ms");
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
		int folderId = 1740;

		//		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		//		emf.createEntityManager().clear();
		//		emf.getCache().evictAll();
		SearchManager sm = SearchManager.getInstance();

		long start = System.currentTimeMillis();
		//sm.getSearchByName(search.getName(), folder.getId());
		sm.getSearchByName(searchName, folderId);
		long end = System.currentTimeMillis();
		System.out.println("Time spent to query search by name from 1,000,000 searches: " + (end - start) + "ms");
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
		int folderId = 1740;

		EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
		emf.createEntityManager().clear();
		//		emf.getCache().evictAll();

		SearchManager sm = SearchManager.getInstance();

		long start = System.currentTimeMillis();
		//List<Search> searches = sm.getSearchListByFolderId(folder.getId());
		List<Search> searches = sm.getSearchListByFolderId(folderId);
		long end = System.currentTimeMillis();
		System.out.println("Time spent to get search list by folder id from 1,000,000 searches: " + (end - start) + "ms");
		System.out.println("amount of searches " + searches.size());
	}

	@Test
	public void testSearchAllocationSize() throws EMAnalyticsFwkException
	{
		FolderManagerImpl fm = FolderManagerImpl.getInstance();
		Folder folder = SearchManagerTest.createTestFolder(fm, "FolderTest" + System.currentTimeMillis());

		CategoryManager cm = CategoryManagerImpl.getInstance();
		Category cat = SearchManagerTest.createTestCategory(cm, folder, "CategoryTest" + System.currentTimeMillis());

		SearchManager sm = SearchManager.getInstance();
		Search search1 = SearchManagerTest.createTestSearch(sm, folder, cat, "Search Name " + System.currentTimeMillis());

		SearchManagerTestMockup.incrementSeq();

		List<Search> searchList = new ArrayList<Search>();
		searchList.add(search1);
		for (int i = 1; i < SEQ_ALLOCATION_SIZE; i++) {
			Search search = SearchManagerTest.createTestSearch(sm, folder, cat,
					"Search" + i + " Name " + System.currentTimeMillis());
			searchList.add(search);
		}

		Search search = SearchManagerTest.createTestSearch(sm, folder, cat, "Search last Name " + System.currentTimeMillis());
		AssertJUnit.assertEquals(search1.getId() + SEQ_ALLOCATION_SIZE * 2, search.getId().intValue());
		searchList.add(search);

		for (Search searchObj : searchList) {
			sm.deleteSearch(searchObj.getId(), true);
		}
		cm.deleteCategory(cat.getId(), true);
		fm.deleteFolder(folder.getId(), true);
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
		AssertJUnit.assertEquals(expected.getLastAccessDate(), actual.getLastAccessDate());
	}

	private EmAnalyticsLastAccess getLastAccessForSearch(int searchId)
	{
		EntityManagerFactory emf = null;
		EntityManager em = null;
		EmAnalyticsLastAccess eala = null;
		try {
			emf = PersistenceManager.getInstance().getEntityManagerFactory();
			em = emf.createEntityManager();
			EmAnalyticsLastAccessPK ealaPK = new EmAnalyticsLastAccessPK();
			ealaPK.setObjectId(searchId);
			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
			ealaPK.setAccessedBy(currentUser);
			ealaPK.setObjectType(EmAnalyticsLastAccess.LAST_ACCESS_TYPE_SEARCH);
			eala = em.find(EmAnalyticsLastAccess.class, ealaPK);
		}
		catch (Exception e) {
			AssertJUnit.fail(e.getMessage());
		}
		return eala;
	}
}

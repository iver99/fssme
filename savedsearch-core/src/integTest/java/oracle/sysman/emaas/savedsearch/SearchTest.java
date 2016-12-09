package oracle.sysman.emaas.savedsearch;

import java.util.List;

import javax.persistence.Persistence;

import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest
{

	private static Integer folderId;

	private static Integer categoryId;

	private static Integer searchId;

	private static final int TA_SEARCH_ID = 3000;//a system search that always exists
	private static int initialSearchCnt = 0;


	@BeforeClass
	public static void initialization() throws Exception
	{

		try {
			//create a folder to insert search into it
			TenantContext.setContext(new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails()
					.get(QAToolUtil.TENANT_USER_NAME).toString()), TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails()
					.get(QAToolUtil.TENANT_NAME).toString())));
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			Folder folder = objFolder.createNewFolder();

			//set the attribute for new folder
			folder.setName("FolderTest");
			folder.setDescription("testing purpose folder");
			folderId = objFolder.saveFolder(folder).getId();
			AssertJUnit.assertFalse(folderId == 0);

			CategoryManager objCategory = CategoryManagerImpl.getInstance();
			Category cat = new CategoryImpl();
			cat.setName("CategoryTestOne");
			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
			cat.setOwner(currentUser);
			cat.setProviderName("ProviderNameTest");
			cat.setProviderVersion("ProviderVersionTest");
			cat.setProviderDiscovery("ProviderDiscoveryTest");
			cat.setProviderAssetRoot("ProviderAssetRootTest");
			cat = objCategory.saveCategory(cat);
			categoryId = cat.getId();
			AssertJUnit.assertFalse(categoryId == 0);

			SearchManager objSearch = SearchManager.getInstance();
			List<Search> existedSearches = objSearch.getSearchListByCategoryId(999);
			initialSearchCnt = existedSearches != null ? existedSearches.size() : 0;

			//create a new search inside the folder we created
			Search search = objSearch.createNewSearch();
			search.setDescription("testing purpose");
			search.setName("Dummy Search");

			search.setFolderId(folderId);
			search.setCategoryId(categoryId);

			Search s2 = objSearch.saveSearch(search);
			AssertJUnit.assertNotNull(s2);
			searchId = s2.getId();
			AssertJUnit.assertFalse(searchId == 0);
			AssertJUnit.assertNotNull(s2.getCreatedOn());
			AssertJUnit.assertNotNull(s2.getLastModifiedOn());
			AssertJUnit.assertNotNull(s2.getLastAccessDate());
			AssertJUnit.assertEquals(s2.getCreatedOn(), s2.getLastModifiedOn());
			AssertJUnit.assertEquals("Dummy Search", s2.getName());
			AssertJUnit.assertEquals("testing purpose", s2.getDescription());

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@AfterClass
	public static void testDelete() throws Exception
	{

		try {
			SearchManager objSearch = SearchManagerImpl.getInstance();
			Search search = objSearch.getSearch(searchId);
			AssertJUnit.assertNotNull(search);
			objSearch.deleteSearch(search.getId(), true);

			CategoryManager objcategory = CategoryManagerImpl.getInstance();
			objcategory.deleteCategory(search.getCategoryId(), true);

			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			objFolder.deleteFolder(search.getFolderId(), true);

		}
		catch (Exception e) {
			Assert.fail(e.getLocalizedMessage());
		}
		finally {
			TenantContext.clearContext();
		}
	}


	@Test (groups = {"s1"})
	public void testEditSystemSearch() throws Exception
	{
		boolean bresult = false;
		try {
			SearchManager objSearch = SearchManager.getInstance();
			Search search = objSearch.getSearch(TA_SEARCH_ID);
			AssertJUnit.assertNotNull("A system search with id:" + TA_SEARCH_ID + " doesn't exist", search);
			//now set the some value
			search.setName("testName");
			search.setDescription("testcase checking");

			//now update the
			objSearch.editSearch(search);
			AssertJUnit.assertTrue("A system search with id:" + TA_SEARCH_ID + " is updated", false);

		}
		catch (EMAnalyticsFwkException e) {
			bresult = true;
		}
		Assert.assertEquals(bresult, true);
	}

	@Test (groups = {"s1"})
	public void testGetInstance()
	{
		try {
			AssertJUnit.assertTrue(SearchManager.getInstance() != null);
			// Test for Singleton
			SearchManager ins1 = SearchManager.getInstance();
			SearchManager ins2 = SearchManager.getInstance();
			AssertJUnit.assertTrue(ins1 == ins2);
		}
		catch (Exception ex) {
			AssertJUnit.assertFalse(ex.getMessage(), true);
		}
	}

	@Test (groups = {"s1"})
	public void testGetSearch() throws Exception
	{
		try {
			SearchManager objSearch = SearchManager.getInstance();
			Search search = objSearch.getSearch(searchId);
			AssertJUnit.assertNotNull(search);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*@Test
	public void testGetSearchByCategoryId() throws Exception
	{
		SearchManager search = SearchManager.getInstance();
		try {
			List<Search> list = search.getSearchListByCategoryId(999L);
			AssertJUnit.assertNotNull(list);
			AssertJUnit.assertEquals(initialSearchCnt, list.size());
		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(EMAnalyticsFwkException.ERR_GENERIC));
		}
	}*/


	@Test (groups = {"s1"})
	public void testGetSearchByInvalidFolderId(@Mocked final Persistence persistence) throws Exception
	{
		SearchManager search = SearchManager.getInstance();
		try {
			search.getSearchListByFolderId(9999999999L);
		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(EMAnalyticsFwkException.ERR_GENERIC));
		}
	}

	/*@Test
	public void testGetSearchByName() throws Exception
	{
		Search search = SearchManager.getInstance().getSearchByName("WebLogic Servers with small Maximum Heap Size", 1);
		Assert.assertNotNull(search);

		search = SearchManager.getInstance().getSearchByName("WebLogic Servers with small Maximum Heap Size112", 4);
		Assert.assertTrue(search == null);

		List<Search> rtnobj = SearchManager.getInstance().getSystemSearchListByCategoryId(1);
		Assert.assertNotNull(rtnobj);

	}*/

	//	@Test
	//	public void testGetSearchCountByFolderId() throws Exception
	//	{
	//		SearchManager objSearch = SearchManager.getInstance();
	//		//now get the count of the search inside this folder
	//		AssertJUnit.assertEquals(2, objSearch.getSearchCountByFolderId(999));
	//	}



	/*@Test
	public void testModifyLastAccessDate() throws Exception
	{
		SearchManager searchMgr = SearchManager.getInstance();
		try {
			Search search9999 = searchMgr.getSearch(9999);
			AssertJUnit.assertNotNull(search9999);
			Date accessDate0 = search9999.getLastAccessDate();
			AssertJUnit.assertNotNull(accessDate0);

			searchMgr.modifyLastAccessDate(9999);

			search9999 = searchMgr.getSearch(9999);
			AssertJUnit.assertNotNull(search9999);
			Date accessDate1 = search9999.getLastAccessDate();
			AssertJUnit.assertNotNull(accessDate1);

			AssertJUnit.assertTrue(accessDate1.compareTo(accessDate0) > 0);
		}
		catch (Exception e) {
			AssertJUnit.assertTrue(e.getMessage(), false);
		}
	}*/

	@Test (groups = {"s1"})
	public void testSearchNotExist() throws Exception
	{
		SearchManager search = SearchManager.getInstance();
		try {
			search.getSearch(9999999999L);
		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
					EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID));
		}
	}


}

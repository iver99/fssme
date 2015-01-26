package oracle.sysman.emaas.savedsearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBElement;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportSearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.CategoryDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ObjectFactory;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

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

	private static Search dupSearch;
	private static final int TA_SEARCH_ID = 3000;//a system search that always exists

	private static final String TENANT_ID_OPC1 = TestUtils.TENANT_ID_OPC1;

	@BeforeClass
	public static void initialization() throws Exception
	{

		try {
			//create a folder to insert search into it
			Long opc1 = TestUtils.getInternalTenantId(TENANT_ID_OPC1);
			TenantContext.setContext(opc1);
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			Folder folder = objFolder.createNewFolder();

			//set the attribute for new folder
			folder.setName("FolderTest");
			folder.setDescription("testing purpose folder");
			folder.setParentId(1);
			folderId = objFolder.saveFolder(folder).getId();
			AssertJUnit.assertFalse(folderId == 0);

			CategoryManager objCategory = CategoryManagerImpl.getInstance();
			Category cat = new CategoryImpl();
			cat.setName("CategoryTestOne");
			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
			cat.setOwner(currentUser);
			cat = objCategory.saveCategory(cat);
			categoryId = cat.getId();
			AssertJUnit.assertFalse(categoryId == 0);

			SearchManager objSearch = SearchManager.getInstance();

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
			AssertJUnit.assertEquals(s2.getCreatedOn(), s2.getLastAccessDate());
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

	@Test
	public void testBigQueryStr() throws Exception
	{
		//test big query str (length>4000)
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 500; i++) {
			sb.append("I am a big query str");
		}
		final String BIG_QUERYSTR = sb.toString(); //length=10000
		Integer sid = 0;
		int categoryId = 0;
		int folderId = 0;
		SearchManager searchMgr = SearchManager.getInstance();
		CategoryManager objCategory = CategoryManagerImpl.getInstance();
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		try {

			Folder folder = new FolderImpl();
			folder.setName("SearchPramTest23");
			folder.setDescription("Test Parameter Description");
			folder.setUiHidden(false);
			folder.setParentId(1);
			folder = objFolder.saveFolder(folder);
			folderId = folder.getId();

			Category cat = new CategoryImpl();
			cat.setName("CategoryTestOneTest1");
			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
			cat.setOwner(currentUser);
			cat.setDefaultFolderId(folderId);
			cat = objCategory.saveCategory(cat);
			categoryId = cat.getId();

			Search search = searchMgr.createNewSearch();
			search.setDescription("testing big query str");
			search.setName("Search with big query str");
			search.setFolderId(folderId);
			search.setCategoryId(categoryId);
			search.setQueryStr(BIG_QUERYSTR);
			Search savedSearch = searchMgr.saveSearch(search);
			Assert.assertNotNull(savedSearch, "Get NULL search from saveeSearch()");
			sid = savedSearch.getId();
			Assert.assertNotNull(sid, "Search id is  NULL");
			Assert.assertNotNull(savedSearch.getQueryStr(), "Get NULL query str");
			Assert.assertEquals(savedSearch.getQueryStr().length(), BIG_QUERYSTR.length(),
					"Get a different size of big query str");
			Assert.assertEquals(savedSearch.getQueryStr(), BIG_QUERYSTR, "Get a different big query str");

			Search freshSearch = searchMgr.getSearch(sid);
			Assert.assertNotNull(freshSearch, "Get NULL search from getSearch()");
			Assert.assertNotNull(freshSearch.getQueryStr(), "Get NULL query str");
			Assert.assertEquals(freshSearch.getQueryStr().length(), BIG_QUERYSTR.length(),
					"Get a different size of big query str");
			Assert.assertEquals(freshSearch.getQueryStr(), BIG_QUERYSTR, "Get a different big query str");
		}
		finally {
			if (sid != null && sid > 0) {
				searchMgr.deleteSearch(sid, true);
				objCategory.deleteCategory(categoryId, true);
				objFolder.deleteFolder(folderId, true);
			}
		}
	}

	@Test
	public void testDeleteInvalidSearchId() throws Exception
	{
		SearchManager sman = SearchManager.getInstance();
		try {
			sman.deleteSearch(99898987898L, true);

		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
					EMAnalyticsFwkException.ERR_GET_SEARCH_FOR_ID));
		}
	}

	/*@Test
	public void testDeleteSystemSearch() throws Exception
	{
		try {
			SearchManager objSearch = SearchManagerImpl.getInstance();
			objSearch.deleteSearch(TA_SEARCH_ID, true);
			Assert.assertTrue(false, "A system search with id:" + TA_SEARCH_ID + " is deleted unexpectedly");
		}
		catch (EMAnalyticsFwkException e) {
			e.printStackTrace();
			Assert.assertEquals(EMAnalyticsFwkException.ERR_DELETE_SEARCH, e.getErrorCode(),
					"unexpected error code: " + e.getErrorCode());
		}
	}*/

	@Test
	public void testDuplicate() throws Exception
	{
		int folderId = 0;
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		SearchManager objSearch = SearchManager.getInstance();
		CategoryManager objCategory = CategoryManagerImpl.getInstance();
		try {

			Folder folder = new FolderImpl();
			folder.setName("SearchPramTest23");
			folder.setDescription("Test Parameter Description");
			folder.setUiHidden(false);
			folder.setParentId(1);
			folder = objFolder.saveFolder(folder);
			folderId = folder.getId();

			Category cat = new CategoryImpl();
			cat.setName("CategoryTestOne1");
			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
			cat.setOwner(currentUser);
			cat.setDefaultFolderId(folderId);
			cat = objCategory.saveCategory(cat);
			categoryId = cat.getId();

			Search search = objSearch.createNewSearch();
			search.setDescription("temporary search");
			search.setName("My Search");

			search.setFolderId(folderId);
			search.setCategoryId(categoryId);
			dupSearch = objSearch.saveSearch(search);

			search = objSearch.createNewSearch();
			search.setDescription("temporary search");
			search.setName("My Search");

			search.setFolderId(folderId);
			search.setCategoryId(categoryId);
			try {
				objSearch.saveSearch(search);
			}
			catch (EMAnalyticsFwkException emanfe) {
				AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
						EMAnalyticsFwkException.ERR_SEARCH_DUP_NAME));
			}
		}
		finally {
			///delete the search created before
			objSearch.deleteSearch(dupSearch.getId(), true);
			objCategory.deleteCategory(categoryId, true);
			objFolder.deleteFolder(folderId, true);
		}

	}

	@Test
	public void testEditSearch() throws Exception
	{
		SearchManager objSearch = SearchManager.getInstance();
		try {
			Search search = objSearch.getSearch(searchId);
			AssertJUnit.assertNotNull(search);
			//now set the some value
			search.setName("testName");
			search.setDescription("testcase checking");

			//now update the
			Search s2 = objSearch.editSearch(search);

			AssertJUnit.assertNotNull(s2);
			AssertJUnit.assertEquals("testName", s2.getName());
			AssertJUnit.assertEquals("testcase checking", s2.getDescription());

			AssertJUnit.assertNotNull(s2.getCreatedOn());
			AssertJUnit.assertNotNull(s2.getLastModifiedOn());
			AssertJUnit.assertNotNull(s2.getLastAccessDate());
			AssertJUnit.assertEquals(s2.getLastModifiedOn(), s2.getLastModifiedOn());
			AssertJUnit.assertFalse(s2.getCreatedOn().equals(s2.getLastAccessDate()));
			AssertJUnit.assertFalse(s2.getCreatedOn().equals(s2.getLastModifiedOn()));

		}
		catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.fail(e.getLocalizedMessage());
		}

	}

	/*@Test
	public void testEditSystemSearch() throws Exception
	{
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
			Assert.assertEquals(EMAnalyticsFwkException.ERR_UPDATE_SEARCH, e.getErrorCode(),
					"unexpected error code: " + e.getErrorCode());
		}
	}*/

	@Test
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

	@Test
	public void testGetSearch() throws Exception
	{

		try {
			SearchManager objSearch = SearchManager.getInstance();
			Search search = objSearch.getSearch(searchId);
			AssertJUnit.assertNotNull(search);
		}
		catch (Exception e) {
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
			AssertJUnit.assertEquals(2, list.size());
		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(EMAnalyticsFwkException.ERR_GENERIC));
		}
	}*/

	@Test
	public void testGetSearchByInvalidCategoryId() throws Exception
	{
		SearchManager search = SearchManager.getInstance();
		try {
			search.getSearchListByCategoryId(9999999999L);
		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(EMAnalyticsFwkException.ERR_GENERIC));
		}
	}

	@Test
	public void testGetSearchByInvalidFolderId() throws Exception
	{
		SearchManager search = SearchManager.getInstance();
		try {
			search.getSearchListByFolderId(9999999999L);
		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(EMAnalyticsFwkException.ERR_GENERIC));
		}
	}

	@Test
	public void testGetSearchListByFolderId() throws Exception
	{

		List<Search> searchList = new ArrayList<Search>();
		SearchManager objSearch = SearchManager.getInstance();
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		CategoryManager objCategory = CategoryManagerImpl.getInstance();
		Search snew = null;
		int folderId = 0;
		int categoryId = 0;
		try {

			Folder folder = new FolderImpl();
			folder.setName("SearchP");
			folder.setDescription("Test Parameter Description");
			folder.setUiHidden(false);
			folder.setParentId(1);
			folder = objFolder.saveFolder(folder);
			folderId = folder.getId();

			Category cat = new CategoryImpl();
			cat.setName("CategoryTest_1");
			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
			cat.setOwner(currentUser);
			cat.setDefaultFolderId(folderId);
			cat = objCategory.saveCategory(cat);
			categoryId = cat.getId();

			Search search = objSearch.createNewSearch();
			search.setDescription("search");
			search.setName("MySearch");

			search.setFolderId(folderId);
			search.setCategoryId(categoryId);
			snew = objSearch.saveSearch(search);

			//now get the list of the search inside this folder
			searchList = objSearch.getSearchListByFolderId(folderId);

			//now check the length of list and content of search
			AssertJUnit.assertEquals(new Integer(1), new Integer(searchList.size()));

			AssertJUnit.assertEquals("MySearch", searchList.get(0).getName());

		}
		catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.fail(e.getLocalizedMessage());
		}
		finally {
			//delete the new search
			objSearch.deleteSearch(snew.getId(), true);
			objCategory.deleteCategory(categoryId, true);
			objFolder.deleteFolder(folderId, true);
		}

	}

	//	@Test
	//	public void testGetSearchCountByFolderId() throws Exception
	//	{
	//		SearchManager objSearch = SearchManager.getInstance();
	//		//now get the count of the search inside this folder
	//		AssertJUnit.assertEquals(2, objSearch.getSearchCountByFolderId(999));
	//	}

	@Test
	public void testGetSearchListByLastAccessDate() throws Exception
	{
		SearchManager searchMgr = SearchManager.getInstance();
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		CategoryManager objCategory = CategoryManagerImpl.getInstance();
		int searchid1 = 0;
		int searchid2 = 0;
		try {

			Folder folder = new FolderImpl();
			folder.setName("SearchFolerLast");
			folder.setDescription("Test Parameter Description");
			folder.setUiHidden(false);
			folder.setParentId(1);
			folder = objFolder.saveFolder(folder);
			int folderId = folder.getId();

			Category cat = new CategoryImpl();
			cat.setName("CategoryTestlast");
			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
			cat.setOwner(currentUser);
			cat.setDefaultFolderId(folderId);
			cat = objCategory.saveCategory(cat);
			int categoryId = cat.getId();

			Search search = searchMgr.createNewSearch();
			search.setDescription("searchlast1");
			search.setName("searchlast1");

			search.setFolderId(folderId);
			search.setCategoryId(categoryId);
			search = searchMgr.saveSearch(search);
			searchid1 = search.getId();

			Search search1 = searchMgr.createNewSearch();
			search1.setDescription("searchlast1");
			search1.setName("searchlast2");

			search1.setFolderId(folderId);
			search1.setCategoryId(categoryId);
			search1 = searchMgr.saveSearch(search1);
			searchid2 = search1.getId();

			List<Search> list = searchMgr.getSearchListByLastAccessDate(2);
			AssertJUnit.assertNotNull(list);
			AssertJUnit.assertSame(2, list.size());
			//NOTE: assume that OOB searches are less than 100. If real OOB search count is greater than 100, we need to update below expected number
			final int MORE_THAN_MAX_COUNT = 10000;
			List<Search> list2 = searchMgr.getSearchListByLastAccessDate(MORE_THAN_MAX_COUNT);
			AssertJUnit.assertNotNull(list2);
			AssertJUnit.assertNotSame(MORE_THAN_MAX_COUNT, list2.size());

			Search search9998 = searchMgr.getSearch(search.getId());
			AssertJUnit.assertNotNull(search9998);
			search9998.setDescription("update access time");
			searchMgr.editSearch(search9998);

			Search search9999 = searchMgr.getSearch(search1.getId());
			AssertJUnit.assertNotNull(search9999);
			search9999.setDescription("update access time");
			searchMgr.editSearch(search9999);

			List<Search> list3 = searchMgr.getSearchListByLastAccessDate(2);
			AssertJUnit.assertNotNull(list3);
			AssertJUnit.assertEquals(2, list3.size());
			AssertJUnit.assertEquals(search1.getId().intValue(), list3.get(0).getId().intValue());
			AssertJUnit.assertEquals(search.getId().intValue(), list3.get(1).getId().intValue());

			//recover to original state
			search9998.setDescription(null);
			searchMgr.editSearch(search9998);
			search9999.setDescription(null);
			searchMgr.editSearch(search9999);
			searchMgr.deleteSearch(searchid1, true);
			searchMgr.deleteSearch(searchid2, true);
			objCategory.deleteCategory(categoryId, true);
			objFolder.deleteFolder(folderId, true);

		}
		catch (Exception e) {
			// TODO: handle exception
			AssertJUnit.assertTrue(e.getMessage(), false);
			searchMgr.deleteSearch(searchid1, true);
			searchMgr.deleteSearch(searchid2, true);
			objCategory.deleteCategory(categoryId, true);
			objFolder.deleteFolder(folderId, true);
		}
	}

	@Test
	public void testInvalidDataForEdit() throws Exception
	{
		SearchManager objSearch = SearchManager.getInstance();
		Search searchObj = null;
		try {
			///check with invalid folder id
			Search search = objSearch.createNewSearch();
			search.setDescription("temporary search");
			search.setName("My Search");

			search.setFolderId(101143254);
			search.setCategoryId(1);
			try {
				searchObj = objSearch.saveSearch(search);
			}
			catch (EMAnalyticsFwkException emanfe) {
				AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
						EMAnalyticsFwkException.ERR_SEARCH_INVALID_FOLDER));
			}

			//check with invalid category id
			search = objSearch.createNewSearch();
			search.setDescription("temporary search");
			search.setName("My Search");

			search.setFolderId(folderId);
			search.setCategoryId(102386576);
			try {
				searchObj = objSearch.saveSearch(search);
			}
			catch (EMAnalyticsFwkException emanfe) {
				AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
						EMAnalyticsFwkException.ERR_SEARCH_INVALID_CATEGORY));
			}
		}
		finally {
			if (searchObj != null) {
				objSearch.deleteSearch(searchObj.getId(), true);
			}
		}

	}

	@Test
	public void testInvalidDataForSave() throws Exception
	{
		SearchManager objSearch = SearchManager.getInstance();
		Search searchObj = null;
		try {
			///check with invalid folder id
			Search search = objSearch.createNewSearch();
			search.setDescription("temporary search");
			search.setName("My Search");

			search.setFolderId(101143254);
			search.setCategoryId(1);
			try {
				searchObj = objSearch.saveSearch(search);
			}
			catch (EMAnalyticsFwkException emanfe) {
				AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
						EMAnalyticsFwkException.ERR_SEARCH_INVALID_FOLDER));
			}

			//check with invalid category id
			search = objSearch.createNewSearch();
			search.setDescription("temporary search");
			search.setName("My Search");

			search.setFolderId(folderId);
			search.setCategoryId(102386576);
			try {
				searchObj = objSearch.saveSearch(search);
			}
			catch (EMAnalyticsFwkException emanfe) {
				AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
						EMAnalyticsFwkException.ERR_SEARCH_INVALID_CATEGORY));
			}
		}
		finally {
			if (searchObj != null) {
				objSearch.deleteSearch(searchObj.getId(), true);
			}
		}

	}

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

	@Test
	public void testSaveMultipleSearch() throws Exception
	{
		SearchManagerImpl searchMgr = SearchManagerImpl.getInstance();
		List<ImportSearchImpl> list = null;
		List<Search> listResult = null;
		ObjectFactory objectFac = new ObjectFactory();

		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		Folder folder1 = new FolderImpl();
		folder1.setName("SearchPramTest23");
		folder1.setDescription("Test Parameter Description");
		folder1.setUiHidden(false);
		folder1.setParentId(1);
		folder1 = objFolder.saveFolder(folder1);
		int fId = folder1.getId();

		CategoryManager objCategory = CategoryManagerImpl.getInstance();
		Category cat = new CategoryImpl();
		cat.setName("CategoryTestMSearch");
		String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
		cat.setOwner(currentUser);
		cat.setDefaultFolderId(folder1.getParentId());
		cat = objCategory.saveCategory(cat);
		int cId = cat.getId();

		JAXBElement folderId = objectFac.createFolderId(fId);
		JAXBElement categoryId = objectFac.createCategoryId(cId);

		try {
			list = new ArrayList<ImportSearchImpl>();
			ImportSearchImpl search1 = new ImportSearchImpl();
			search1.setFolderDet(folderId);
			search1.setCategoryDet(categoryId);
			search1.setName("ImportSearch1");
			list.add(search1);
			ImportSearchImpl search2 = new ImportSearchImpl();
			search2.setFolderDet(folderId);
			search2.setCategoryDet(categoryId);
			search2.setName("ImportSearch2");
			list.add(search2);
			listResult = searchMgr.saveMultipleSearch(list);
			AssertJUnit.assertNotNull(listResult);
			AssertJUnit.assertEquals(2, listResult.size());

		}
		catch (Exception e) {
			e.printStackTrace();
			AssertJUnit.assertTrue("Failed to save multiple searches", false);
		}
		finally {
			if (listResult != null) {
				for (Search search : listResult) {
					searchMgr.deleteSearch(search.getId(), true);
				}
				objCategory.deleteCategory(cId, true);
				objFolder.deleteFolder(fId, true);
			}
		}

		List<ImportSearchImpl> list2 = null;
		List<Search> listResult2 = null;

		CategoryDetails catDetails = new CategoryDetails();
		catDetails.setName("Demo Analytics");
		JAXBElement category = objectFac.createCategory(catDetails);

		FolderDetails folderDetails = new FolderDetails();
		folderDetails.setName("Demo Searches");
		folderDetails.setParentId(1);
		JAXBElement folder = objectFac.createFolder(folderDetails);

		FolderDetails folderDetails1 = new FolderDetails();
		folderDetails1.setName("Demo Searches1");
		folderDetails1.setParentId(1);
		JAXBElement folder_2 = objectFac.createFolder(folderDetails1);
		try {
			list2 = new ArrayList<ImportSearchImpl>();
			ImportSearchImpl search1 = new ImportSearchImpl();
			search1.setFolderDet(folder);
			search1.setCategoryDet(category);
			search1.setName("ImportSearch1");
			list2.add(search1);
			ImportSearchImpl search2 = new ImportSearchImpl();
			search2.setFolderDet(folder_2);
			search2.setCategoryDet(category);
			search2.setName("ImportSearch2");
			list2.add(search2);
			listResult2 = searchMgr.saveMultipleSearch(list2);
			AssertJUnit.assertNotNull(listResult2);
			AssertJUnit.assertEquals(2, listResult2.size());

		}
		catch (Exception e) {
			AssertJUnit.assertTrue("Failed to save multiple searches", false);
		}
		finally {
			if (listResult2 != null) {
				FolderManager fmgr = FolderManager.getInstance();
				CategoryManager cMgr = CategoryManager.getInstance();
				Set<Integer> catid = new HashSet<Integer>();
				for (Search search : listResult2) {
					searchMgr.deleteSearch(search.getId(), true);
					fmgr.deleteFolder(search.getFolderId(), true);
					catid.add(search.getCategoryId());

				}
				for (Integer id : catid) {
					cMgr.deleteCategory(id, true);
				}
			}
		}

		List<ImportSearchImpl> list3 = null;
		List<Search> listResult3 = null;

		CategoryDetails catDetails2 = new CategoryDetails();
		catDetails2.setName("Demo Analytics 2");
		JAXBElement category2 = objectFac.createCategory(catDetails2);

		FolderDetails folderDetails2 = new FolderDetails();
		folderDetails2.setName("Demo Searches 2");
		folderDetails2.setParentId(1);
		JAXBElement folder2 = objectFac.createFolder(folderDetails2);

		CategoryDetails catDetails3 = new CategoryDetails();
		catDetails3.setName("Demo Analytics 3");
		JAXBElement category3 = objectFac.createCategory(catDetails3);

		FolderDetails folderDetails3 = new FolderDetails();
		folderDetails3.setName("Demo Searches 3");
		folderDetails3.setParentId(1);
		JAXBElement folder3 = objectFac.createFolder(folderDetails3);
		try {
			list3 = new ArrayList<ImportSearchImpl>();
			ImportSearchImpl search1 = new ImportSearchImpl();

			search1.setFolderDet(folder2);
			search1.setCategoryDet(category2);
			search1.setName("ImportSearch1");
			list3.add(search1);

			ImportSearchImpl search2 = new ImportSearchImpl();
			search2.setFolderDet(folder3);
			search2.setCategoryDet(category3);
			search2.setName("ImportSearch2");
			list3.add(search2);
			listResult3 = searchMgr.saveMultipleSearch(list3);
			AssertJUnit.assertNotNull(listResult3);
			AssertJUnit.assertEquals(2, listResult3.size());

		}
		catch (Exception e) {
			AssertJUnit.assertTrue("Failed to save multiple searches", false);
		}
		finally {
			if (listResult3 != null) {
				CategoryManager catMgr = CategoryManager.getInstance();
				FolderManager folderMgr = FolderManager.getInstance();
				for (Search search : listResult3) {
					int catId = search.getCategoryId();
					int foldId = search.getFolderId();
					searchMgr.deleteSearch(search.getId(), true);
					catMgr.deleteCategory(catId, true);
					folderMgr.deleteFolder(foldId, true);

				}
			}
		}
	}

	@Test
	public void testSaveSearch() throws Exception
	{
		//saveSearch() has been tested in initialization(), here only test search with invalid data
		SearchManager searchMgr = SearchManager.getInstance();
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		CategoryManager objCategory = CategoryManagerImpl.getInstance();
		Search search = null;
		int fId = 0;
		int cId = 0;
		try {

			Folder folder1 = new FolderImpl();
			folder1.setName("S1");
			folder1.setDescription("Test Parameter Description");
			folder1.setUiHidden(false);
			folder1.setParentId(1);
			folder1 = objFolder.saveFolder(folder1);
			fId = folder1.getId();

			Category cat = new CategoryImpl();
			cat.setName("C1");
			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
			cat.setOwner(currentUser);
			cat.setDefaultFolderId(folder1.getParentId());
			cat = objCategory.saveCategory(cat);
			cId = cat.getId();
			search = searchMgr.createNewSearch();
			search.setCategoryId(cId);
			search.setFolderId(fId);
			searchMgr.saveSearch(search);
			AssertJUnit.assertTrue("search without name is saved", false);
		}
		catch (Exception e) {
			AssertJUnit.assertEquals("Error while saving the search: null", e.getMessage());
		}
		finally {
			objCategory.deleteCategory(cId, true);
			objFolder.deleteFolder(fId, true);
		}

	}

	@Test
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

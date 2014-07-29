package oracle.sysman.emaas.savedsearch;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchTest extends BaseTest
{

	private static Integer folderId;

	private static Integer searchId;

	private static Search dupSearch;
	private static final int TA_SEARCH_ID = 3000;//a system search that always exists

	@BeforeClass
	public static void initialization() throws Exception
	{

		try {
			//create a folder to insert search into it
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			Folder folder = objFolder.createNewFolder();

			//set the attribute for new folder
			folder.setName("FolderTest");
			folder.setDescription("testing purpose folder");

			folderId = objFolder.saveFolder(folder).getId();
			AssertJUnit.assertFalse(folderId == 0);

			SearchManager objSearch = SearchManager.getInstance();

			//create a new search inside the folder we created
			Search search = objSearch.createNewSearch();
			search.setDescription("testing purpose");
			search.setName("Dummy Search");

			search.setFolderId(folderId);
			search.setCategoryId(1);

			searchId = objSearch.saveSearch(search).getId();
			AssertJUnit.assertFalse(searchId == 0);

			Search search1 = objSearch.getSearch(searchId);
			AssertJUnit.assertEquals("Dummy Search", search1.getName());
			AssertJUnit.assertEquals("testing purpose", search1.getDescription());

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
			//search=objSearch.getSearch(searchId);
			//Assert.assertNull(search);

			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			objFolder.deleteFolder(folderId, true);
			//Assert.assertNull(objFolder.getFolder(folderId));
		}
		catch (Exception e) {
			e.printStackTrace();
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
		SearchManager searchMgr = SearchManager.getInstance();
		try {

			Search search = searchMgr.createNewSearch();
			search.setDescription("testing big query str");
			search.setName("Search with big query str");
			search.setFolderId(1);
			search.setCategoryId(1);
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

	@Test
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
	}

	@Test
	public void testDuplicate() throws Exception
	{
		SearchManager objSearch = SearchManager.getInstance();
		try {
			Search search = objSearch.createNewSearch();
			search.setDescription("temporary search");
			search.setName("My Search");

			search.setFolderId(folderId);
			search.setCategoryId(1);
			dupSearch = objSearch.saveSearch(search);

			search = objSearch.createNewSearch();
			search.setDescription("temporary search");
			search.setName("My Search");

			search.setFolderId(folderId);
			search.setCategoryId(1);
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
		}

	}

	@Test
	public void testEditSearch() throws Exception
	{
		try {
			SearchManager objSearch = SearchManager.getInstance();
			Search search = objSearch.getSearch(searchId);
			AssertJUnit.assertNotNull(search);
			//now set the some value
			search.setName("testName");
			search.setDescription("testcase checking");

			//now update the
			objSearch.editSearch(search);

			search = objSearch.getSearch(searchId);

			AssertJUnit.assertEquals("testName", search.getName());
			AssertJUnit.assertEquals("testcase checking", search.getDescription());

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
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
			e.printStackTrace();
			Assert.assertEquals(EMAnalyticsFwkException.ERR_UPDATE_SEARCH, e.getErrorCode(),
					"unexpected error code: " + e.getErrorCode());
		}
	}

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
	public void testGetSearchCountByFolderId() throws Exception
	{

		//now create one search object inside the folder ID
		SearchManager objSearch = SearchManager.getInstance();
		Search snew = null;
		;
		try {
			Search search = objSearch.createNewSearch();
			search.setDescription("search");
			search.setName("My new Search");

			search.setFolderId(folderId);
			search.setCategoryId(1);
			snew = objSearch.saveSearch(search);

			//now get the count of the search inside this folder
			AssertJUnit.assertEquals(2, objSearch.getSearchListByFolderId(folderId).size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			//delete the new search
			objSearch.deleteSearch(snew.getId(), true);
		}
	}

	@Test
	public void testGetSearchListByFolderId() throws Exception
	{

		List<Search> searchList = new ArrayList<Search>();
		SearchManager objSearch = SearchManager.getInstance();
		Search snew = null;
		try {
			Search search = objSearch.createNewSearch();
			search.setDescription("search");
			search.setName("MySearch");

			search.setFolderId(folderId);
			search.setCategoryId(1);
			snew = objSearch.saveSearch(search);

			//now get the list of the search inside this folder
			searchList = objSearch.getSearchListByFolderId(folderId);

			//now check the length of list and content of search
			AssertJUnit.assertEquals(new Integer(2), new Integer(searchList.size()));

			if (searchList.get(0).getName().equals("Dummy Search")) {
				AssertJUnit.assertEquals("MySearch", searchList.get(1).getName());
			}
			else if (searchList.get(1).getName().equals("Dummy Search")) {
				AssertJUnit.assertEquals("MySearch", searchList.get(0).getName());
				/* Assert.assertEquals("Dummy Search", search.getName());
				 Assert.assertEquals("testing purpose", search.getDescription());
				 Assert.assertEquals("Display dummy Search", search.getDisplayName());*/
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			//delete the new search
			objSearch.deleteSearch(snew.getId(), true);
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

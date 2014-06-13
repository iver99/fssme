package oracle.sysman.emaas.savedsearch;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchTest
{

	private static Integer folderId;
	private static Integer searchId;
	private static Search dupSearch;

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
			objSearch.deleteSearch(search.getId());

			//search=objSearch.getSearch(searchId);
			//Assert.assertNull(search);

			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			objFolder.deleteFolder(folderId);
			//Assert.assertNull(objFolder.getFolder(folderId));
		}
		catch (Exception e) {
			e.printStackTrace();
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
			objSearch.deleteSearch(dupSearch.getId());
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
			AssertJUnit.assertEquals(2, objSearch.getSearchCountByFolderId(folderId));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			//delete the new search
			objSearch.deleteSearch(snew.getId());
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
			objSearch.deleteSearch(snew.getId());
		}

	}

	@Test
	public void testInvalidData() throws Exception
	{
		SearchManager objSearch = SearchManager.getInstance();
		Search searchObj = null;
		try {
			///check with invalid folder id
			Search search = objSearch.createNewSearch();
			search.setDescription("temporary search");
			search.setName("My Search");

			search.setFolderId(1011);
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
			search.setCategoryId(1023);
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
				objSearch.deleteSearch(searchObj.getId());
			}
		}

	}

}

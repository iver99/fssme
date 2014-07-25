package oracle.sysman.emaas.savedsearch;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FolderManagerTest extends BaseTest
{

	private static int folderId;
	private static int childFolderId;

	@AfterClass
	public static void DeleteFolder() throws Exception
	{

		try {
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();

			objFolder.deleteFolder(folderId, true);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeClass
	public static void testSaveFolder() throws Exception
	{
		try {
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			Folder folder = new FolderImpl();
			folder.setName("FolderTest");
			folder.setDescription("TestFolderDescription");
			folder.setUiHidden(false);
			folder = objFolder.saveFolder(folder);
			folderId = folder.getId();
			AssertJUnit.assertFalse(folderId == 0);

			// cross check the content of the folder being saves
			folder = objFolder.getFolder(folderId);
			AssertJUnit.assertTrue("FolderTest".equals(folder.getName()));
			AssertJUnit.assertTrue("TestFolderDescription".equals(folder.getDescription()));
			AssertJUnit.assertTrue(folder.isUiHidden() == false);
			AssertJUnit.assertTrue(folder.isSystemFolder() == false);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeleteInvalidFolderId() throws Exception
	{
		FolderManager foldMan = FolderManager.getInstance();
		try {
			foldMan.deleteFolder(987656788498L, true);
		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
					EMAnalyticsFwkException.ERR_GET_FOLDER_FOR_ID));
		}
	}

	@Test
	public void testDeleteSystemFolder() throws Exception
	{
		FolderManager foldMan = FolderManager.getInstance();
		try {
			foldMan.deleteFolder(1, true);
		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(EMAnalyticsFwkException.ERR_DELETE_FOLDER));
		}
	}

	@Test
	public void testDuplicate() throws Exception
	{
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		try {
			// create one folder
			Folder folder = objFolder.createNewFolder();

			// set the attribute for new folder
			folder.setName("Duplicate test");
			folder.setDescription("to test duplication");

			folder.setParentId(folderId);

			childFolderId = objFolder.saveFolder(folder).getId();

			// try to create second folder with same name and description
			Folder fi2 = objFolder.createNewFolder();
			// set the attribute for new folder
			fi2.setName("Duplicate test");
			fi2.setDescription("Duplicate subfolder");

			fi2.setParentId(folderId);
			try {
				objFolder.saveFolder(fi2);
			}
			catch (EMAnalyticsFwkException emanfe) {
				AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
						EMAnalyticsFwkException.ERR_FOLDER_DUP_NAME));
			}

		}

		finally {
			// delete the child folder
			objFolder.deleteFolder(childFolderId, true);
		}
	}

	@Test
	public void testGetFolder() throws Exception
	{
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		try {
			Folder folder = objFolder.getFolder(folderId);
			AssertJUnit.assertNotNull(folder);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testGetInstance()
	{
		try {
			AssertJUnit.assertTrue(FolderManagerImpl.getInstance() != null);
			// test for Singleton
			FolderManagerImpl ins1 = FolderManagerImpl.getInstance();
			FolderManagerImpl ins2 = FolderManagerImpl.getInstance();
			AssertJUnit.assertTrue(ins1 == ins2);
		}
		catch (Exception ex) {
			AssertJUnit.assertFalse(ex.getMessage(), true);
		}
	}

	@Test
	public void testGetInvalidFolderId() throws Exception
	{
		FolderManager foldMan = FolderManager.getInstance();
		try {
			foldMan.getFolder(987656788498L);
		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
					EMAnalyticsFwkException.ERR_GET_FOLDER_FOR_ID));
		}
	}

	@Test
	public void testGetpathforFolderId() throws Exception
	{
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();

		String[] path = objFolder.getPathForFolderId(folderId);

		Assert.assertNotNull(path);
		Assert.assertNotNull(path.length > 0);

	}

	@Test
	public void testGetSubFolder() throws Exception
	{
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		List<Folder> folderList = new ArrayList<Folder>();
		try {
			// create two folder inside the id=folderId
			Folder folder = objFolder.createNewFolder();

			// set the attribute for new folder
			folder.setName("TestAnalytics");
			folder.setDescription("analyze the test");

			folder.setParentId(folderId);
			//
			objFolder.saveFolder(folder);

			Folder fi2 = objFolder.createNewFolder();
			// set the attribute for new folder
			fi2.setName("TestAnalytics2");
			fi2.setDescription("analyze the test2");

			fi2.setParentId(folderId);
			objFolder.saveFolder(fi2);

			// get the sub folder inside the folderId
			folderList = objFolder.getSubFolders(folderId);

			AssertJUnit.assertEquals(new Integer(2), new Integer(folderList.size()));

			// cross check the things
			if (folderList.get(0).getName().equals("TestAnalytics")) {
				AssertJUnit.assertEquals("TestAnalytics2", folderList.get(1).getName());
			}
			else if (folderList.get(0).getName().equals("TestAnalytics2")) {
				AssertJUnit.assertEquals("TestAnalytics", folderList.get(1).getName());
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			// delete the sub folder we created
			objFolder.deleteFolder(folderList.get(0).getId(), true);
			objFolder.deleteFolder(folderList.get(1).getId(), true);
		}

	}

	@Test
	public void testInvalidParentFolder() throws Exception
	{
		FolderManager fman = FolderManager.getInstance();

		Folder folder = fman.createNewFolder();
		folder.setName("harsh kumar");
		folder.setParentId(987876788);
		try {
			fman.saveFolder(folder);

		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
					EMAnalyticsFwkException.ERR_FOLDER_INVALID_PARENT));
		}

	}

	@Test
	public void testupdate() throws EMAnalyticsFwkException
	{
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		try {

			Folder folder = objFolder.getFolder(folderId);
			AssertJUnit.assertNotNull(folder);
			folder.setName("My folder");
			folder.setDescription("Database search");

			// update the folder
			objFolder.updateFolder(folder);

			// cross check the content of update
			folder = objFolder.getFolder(folderId);
			AssertJUnit.assertNotNull(folder);

			AssertJUnit.assertEquals("My folder", folder.getName());
			AssertJUnit.assertEquals("Database search", folder.getDescription());

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package oracle.sysman.emaas.savedsearch;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FolderManagerTest
{

	private static int folderId;
	private static int childFolderId;

	@AfterClass
	public static void DeleteFolder() throws Exception
	{

		try {
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();

			objFolder.deleteFolder(folderId);

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

			//cross check the content of the folder being saves
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
	public void testDuplicate() throws Exception
	{
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		try {
			//create one folder
			Folder folder = objFolder.createNewFolder();

			//set the attribute for new folder
			folder.setName("Duplicate test");
			folder.setDescription("to test duplication");

			folder.setParentId(folderId);

			childFolderId = objFolder.saveFolder(folder).getId();

			//try to create second folder with same name and description
			Folder fi2 = objFolder.createNewFolder();
			//set the attribute for new folder
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
			//delete the child folder
			objFolder.deleteFolder(childFolderId);
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
			//test for Singleton
			FolderManagerImpl ins1 = FolderManagerImpl.getInstance();
			FolderManagerImpl ins2 = FolderManagerImpl.getInstance();
			AssertJUnit.assertTrue(ins1 == ins2);
		}
		catch (Exception ex) {
			AssertJUnit.assertFalse(ex.getMessage(), true);
		}
	}

	@Test
	public void testGetpathforFolderId() throws Exception
	{
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();

		String[] path = objFolder.getPathForFolderId(folderId);

		Assert.assertNotNull(path);
		Assert.assertNotNull(path.length>0);

	}

	@Test
	public void testGetSubFolder() throws Exception
	{
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		List<Folder> folderList = new ArrayList<Folder>();
		try {
			//create two folder inside the id=folderId
			Folder folder = objFolder.createNewFolder();

			//set the attribute for new folder
			folder.setName("TestAnalytics");
			folder.setDescription("analyze the test");

			folder.setParentId(folderId);
			//
			objFolder.saveFolder(folder);

			Folder fi2 = objFolder.createNewFolder();
			//set the attribute for new folder
			fi2.setName("TestAnalytics2");
			fi2.setDescription("analyze the test2");

			fi2.setParentId(folderId);
			objFolder.saveFolder(fi2);

			//get the sub folder inside the folderId 
			folderList = objFolder.getSubFolders(folderId);

			AssertJUnit.assertEquals(new Integer(2), new Integer(folderList.size()));

			//cross check the things
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
			//delete the sub folder we created
			objFolder.deleteFolder(folderList.get(0).getId());
			objFolder.deleteFolder(folderList.get(1).getId());
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

			//update the folder
			objFolder.updateFolder(folder);

			//cross check the content of update
			folder = objFolder.getFolder(folderId);
			AssertJUnit.assertNotNull(folder);

			AssertJUnit.assertEquals("My folder", folder.getName());
			AssertJUnit.assertEquals("Database search", folder.getDescription());

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*  
	 @Test
	 public void testAllCrud()
	  {
	      try
	      {   
	      	FolderManagerImpl objFolder =  FolderManagerImpl.getInstance();
	      	Folder folder = new FolderImpl();    		
	  		folder.setName("Test Folder");
	  		folder.setDescription("Folder Description");
	  		folder.setDisplayName("Test Folder");
	  		folder.setUiHidden(false);	
	  		
	  		folder = objFolder.saveFolder(folder);
	      	Integer id =  folder.getId();
	      	Assert.assertTrue("Failed to create asset", id > 0);
	      	
	      	folder= objFolder.getFolder(id);
	      	Assert.assertTrue("Test Folder".equals(folder.getName()));
	      	Assert.assertTrue("Folder Description".equals(folder.getDescription()));
	      	Assert.assertTrue("Test Folder".equals(folder.getDisplayName()));
	      	Assert.assertTrue(folder.isUiHidden()==false);
	      	Assert.assertTrue(folder.isSystemFolder()==false);
	      	
	      	
	      	folder = new FolderImpl();    		
	  		folder.setName("Child Test Folder");
	  		folder.setDescription("Child Folder Description");
	  		folder.setDisplayName("Child Test Folder");
	  		folder.setUiHidden(false);	
	  		folder.setParentId(id);    		
	  		folder = objFolder.saveFolder(folder);
	  		folder =objFolder.getFolder(folder.getId());
	  		
	      	Assert.assertTrue("Child Test Folder".equals(folder.getName()));
	      	Assert.assertTrue("Child Folder Description".equals(folder.getDescription()));
	      	Assert.assertTrue("Child Test Folder".equals(folder.getDisplayName()));
	      	Assert.assertTrue(folder.isUiHidden()==false);
	      	Assert.assertTrue(folder.isSystemFolder()==false);
	      	
	      	folder.setDescription("edit descritpion");
	      	folder = objFolder.updateFolder(folder);
	      	folder =objFolder.getFolder(folder.getId());
	      	Assert.assertTrue("edit descritpion".equals(folder.getDescription()));
	      	
	      	Integer childid = folder.getId();
	      	folder = new FolderImpl();    		
	  		folder.setName("Child1 Test Folder");
	  		folder.setDescription("Child1 Folder Description");
	  		folder.setDisplayName("Child1 Test Folder");
	  		folder.setUiHidden(false);	
	  		folder.setParentId(childid);    		
	  		folder = objFolder.saveFolder(folder);
	      	
	      	
	      	List<Folder> listFolder = objFolder.getSubFolders(id);
	      	Assert.assertTrue(listFolder.size()==1);      	
	      	

	      	
	      	String path =objFolder.getPathForFolderId(folder.getId());
	      	Assert.assertTrue(path.equals("/Test Folder/Child Test Folder/Child1 Test Folder"));
	      	
	      	path =objFolder.getPathForFolderId(childid);
	      	Assert.assertTrue(path.equals("/Test Folder/Child Test Folder"));
	      	
	      	path =objFolder.getPathForFolderId(id);
	      	Assert.assertTrue(path.equals("/Test Folder"));
	      	
	      	objFolder.deleteFolder(folder.getId());
	      	objFolder.deleteFolder(childid);
	      	objFolder.deleteFolder(id);
	      }catch(Exception ex)
	      {
	      	Assert.fail(ex.getLocalizedMessage());
	      }
	      
	  }*/

}

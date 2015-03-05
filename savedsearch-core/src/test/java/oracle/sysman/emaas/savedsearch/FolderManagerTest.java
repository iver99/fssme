package oracle.sysman.emaas.savedsearch;

import java.util.ArrayList;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FolderManagerTest extends BaseTest
{

	private static int folderId;

	private static int childFolderId;
	private static int categoryId;
	private static final String TENANT_ID_OPC1 = TestUtils.TENANT_ID_OPC1;
	private static final String TENANT_ID_OPC2 = TestUtils.TENANT_ID_OPC2;
	private static final String TENANT_ID_OPC3 = TestUtils.TENANT_ID_OPC3;

	@Test
	public static void createreadFolderByTenant() throws Exception
	{

		long folderId = 0;
		long folderId1 = 0;
		try {
			TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC2));
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			Folder folder = new FolderImpl();
			folder.setName("FolderTestopc");
			folder.setDescription("TestFolderDescription");
			folder.setUiHidden(false);
			folder = objFolder.saveFolder(folder);
			folderId = folder.getId();
			AssertJUnit.assertFalse(folderId == 0);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			TenantContext.clearContext();
		}

		try {
			TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC3));
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			Folder folder = new FolderImpl();
			folder.setName("FolderTestopc");
			folder.setDescription("TestFolderDescription");
			folder.setUiHidden(false);
			folder = objFolder.saveFolder(folder);
			folderId1 = folder.getId();
			AssertJUnit.assertFalse(folderId1 == 0);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			TenantContext.clearContext();
		}

		Folder folder = null;
		try {
			TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC2));
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			folder = objFolder.getFolder(folderId1);

		}
		catch (Exception e) {
			AssertJUnit.assertTrue(folder == null);
		}
		finally {
			TenantContext.clearContext();
		}

		try {
			TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC3));
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			folder = objFolder.getFolder(folderId);

		}
		catch (Exception e) {
			AssertJUnit.assertTrue(folder == null);
		}
		finally {
			TenantContext.clearContext();
		}

		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();

		try {
			TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC3));
			objFolder.deleteFolder(folderId1, true);
		}
		finally {
			TenantContext.clearContext();
		}

		try {
			TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC2));
			objFolder.deleteFolder(folderId, true);
		}
		finally {
			TenantContext.clearContext();
		}

	}

	@AfterClass
	public static void DeleteFolder() throws Exception
	{

		try {
			TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC1));
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			objFolder.deleteFolder(folderId, true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			TenantContext.clearContext();
		}
	}

	@BeforeClass
	public static void testSaveFolder() throws Exception
	{
		try {

			TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC1));

			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			Folder folder = new FolderImpl();
			folder.setName("FolderTest");
			folder.setDescription("TestFolderDescription");
			folder.setUiHidden(false);
			folder = objFolder.saveFolder(folder);
			folderId = folder.getId();
			AssertJUnit.assertFalse(folderId == 0);
			AssertJUnit.assertNotNull(folder.getOwner());
			// cross check the content of the folder being saves
			folder = objFolder.getFolder(folderId);
			AssertJUnit.assertTrue("FolderTest".equals(folder.getName()));
			AssertJUnit.assertTrue("TestFolderDescription".equals(folder.getDescription()));
			AssertJUnit.assertTrue(folder.isUiHidden() == false);
			AssertJUnit.assertTrue(folder.isSystemFolder() == false);
			AssertJUnit.assertNotNull(folder.getCreatedOn());
			AssertJUnit.assertNotNull(folder.getLastModifiedOn());
			AssertJUnit.assertEquals(folder.getCreatedOn(), folder.getLastModifiedOn());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			TenantContext.clearContext();
		}
	}

	/*@Test
	public void testDeleteSystemFolder() throws Exception
	{
		TenantContext.setContext(TENANT_ID_OPC1);
		FolderManager foldMan = FolderManager.getInstance();
		try {
			foldMan.deleteFolder(TA_FOLDER_ID, true);
			AssertJUnit.assertTrue("A system folder with id " + TA_FOLDER_ID + " is deleted unexpectedly", false);
		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(EMAnalyticsFwkException.ERR_DELETE_FOLDER, emanfe.getErrorCode());
		}
		finally {
			TenantContext.clearContext();
		}
	}*/

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
		finally {
			TenantContext.clearContext();
		}
	}

	@Test
	public void testDeletFolder_withCategory()
	{
		try {
			//create the category with the folder
			TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC1));
			CategoryManager catMan = CategoryManager.getInstance();
			Category category = catMan.createNewCategory();
			category.setName("TestCategoryWithFolder");
			category.setDescription("CategoryTest");
			category.setDefaultFolderId(folderId);
			category.setProviderName("ProviderNameUT");
			category.setProviderVersion("ProviderVersionUT");
			category.setProviderDiscovery("ProviderDiscoveryUT");
			category.setProviderAssetRoot("ProviderAssetRootUT");

			category = catMan.saveCategory(category);
			categoryId = category.getId();

			FolderManager foldMan = FolderManager.getInstance();
			try {
				foldMan.deleteFolder(folderId, true);
			}
			catch (EMAnalyticsFwkException emanfe) {
				AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
						EMAnalyticsFwkException.ERR_DELETE_FOLDER));
			}
		}
		catch (Exception e) {
			AssertJUnit.fail();
		}
		finally {

			CategoryManager catMan = CategoryManager.getInstance();
			try {
				catMan.deleteCategory(categoryId, true);
			}
			catch (EMAnalyticsFwkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TenantContext.clearContext();
		}
	}

	@Test
	public void testDuplicate() throws Exception
	{
		TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC1));
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
			TenantContext.clearContext();
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
		TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC1));
		FolderManager foldMan = FolderManager.getInstance();
		try {
			foldMan.getFolder(987656788498L);
		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
					EMAnalyticsFwkException.ERR_GET_FOLDER_FOR_ID));
		}
		finally {
			TenantContext.clearContext();
		}
	}

	@Test
	public void testGetpathforFolderId() throws Exception
	{
		try {
			TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC1));
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();

			String[] path = objFolder.getPathForFolderId(folderId);

			Assert.assertNotNull(path);
			Assert.assertNotNull(path.length > 0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			TenantContext.clearContext();
		}

	}

	@Test
	public void testGetSubFolder() throws Exception
	{
		TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC1));
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
			TenantContext.clearContext();
		}

	}

	/*@Test
	public void testUpdateSystemFolder() throws EMAnalyticsFwkException
	{
		TenantContext.setContext(TENANT_ID_OPC1);
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		try {

			Folder folder = objFolder.getFolder(TA_FOLDER_ID);
			AssertJUnit.assertNotNull(folder);
			folder.setName("My folder");
			folder.setDescription("Database search");

			// update the folder
			objFolder.updateFolder(folder);
			AssertJUnit.assertTrue("A system folder with id " + TA_FOLDER_ID + " is updated unexpectedly", false);
		}
		catch (EMAnalyticsFwkException e) {
			e.printStackTrace();
			AssertJUnit.assertEquals(EMAnalyticsFwkException.ERR_UPDATE_FOLDER, e.getErrorCode());
		}
		finally {
			TenantContext.clearContext();
		}
	}*/

	@Test
	public void testInvalidParentFolder() throws Exception
	{
		TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC1));

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
		finally {
			TenantContext.clearContext();
		}

	}

	@Test
	public void testUpdate() throws EMAnalyticsFwkException
	{
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		try {

			TenantContext.setContext(TestUtils.getInternalTenantId(TENANT_ID_OPC1));
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

			AssertJUnit.assertNotNull(folder.getCreatedOn());
			AssertJUnit.assertNotNull(folder.getLastModifiedOn());
			AssertJUnit.assertFalse(folder.getCreatedOn().equals(folder.getLastModifiedOn()));

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			TenantContext.clearContext();
		}
	}

}

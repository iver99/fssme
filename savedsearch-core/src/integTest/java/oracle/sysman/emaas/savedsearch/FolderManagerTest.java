package oracle.sysman.emaas.savedsearch;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.QAToolUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;

public class FolderManagerTest extends BaseTest
{

	private static int folderId;




	@AfterClass
	public static void DeleteFolder() throws EMAnalyticsFwkException {

		try {
			TenantContext.setContext(new TenantInfo(
					TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
					TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			objFolder.deleteFolder(folderId, true);
		}
		finally {
			TenantContext.clearContext();
		}
	}

	@BeforeClass
	public static void testSaveFolder() throws EMAnalyticsFwkException {
		try {

			TenantContext.setContext(new TenantInfo(
					TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
					TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
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
		finally {
			TenantContext.clearContext();
		}
	}

	@Test(groups = { "s1" })
	public void entityClassTest()
	{
		EmAnalyticsFolder fld = new EmAnalyticsFolder();
		Date utcNow = DateUtil.getCurrentUTCTime();
		fld.setCreationDate(utcNow);
		fld.setDescription("desc");
		fld.setDescriptionNlsid("desc");
		fld.setDescriptionSubsystem("desc");
		fld.setEmAnalyticsCategories(null);
		fld.setEmAnalyticsFolder(null);
		fld.setEmAnalyticsFolders(null);
		fld.setEmPluginId("null");
		fld.setFolderId(1);
		fld.setName("abc");
		fld.setLastModificationDate(utcNow);
		fld.setLastModifiedBy("admin");
		fld.setUiHidden(new BigDecimal(1));

		Assert.assertNotNull(fld.getCreationDate());
		Assert.assertNotNull(fld.getDescription());
		Assert.assertNotNull(fld.getDescriptionNlsid());
		Assert.assertNotNull(fld.getDescriptionSubsystem());

		Assert.assertNull(fld.getEmAnalyticsCategories());
		Assert.assertNull(fld.getEmAnalyticsFolder());
		Assert.assertNull(fld.getEmAnalyticsFolders());
		Assert.assertNotNull(fld.getEmPluginId());

		Assert.assertNotNull(fld.getFolderId());
		Assert.assertNotNull(fld.getName());

		Assert.assertNotNull(fld.getLastModificationDate());
		Assert.assertNotNull(fld.getLastModifiedBy());
		Assert.assertNotNull(fld.getUiHidden());

	}






	@Test(groups = "s2", expectedExceptions = EMAnalyticsFwkException.class, expectedExceptionsMessageRegExp = "Folder with the Id 0 does not exist")
	public void testGetFolder(@Mocked final PersistenceManager persistenceManager, @Mocked final EntityManager entityManager)
			throws EMAnalyticsFwkException
	{
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		new Expectations(objFolder) {
			{
				PersistenceManager.getInstance().getEntityManager((TenantInfo) any);
				result = entityManager;
				entityManager.find((Class) any, any);
				result = null;
			}
		};
		objFolder.getFolder(0);
	}


	/*@Test
	public void testRootFolder() throws Exception
	{
		TenantContext.setContext(
				new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
						TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();

		Folder obj = objFolder.getRootFolder();

		Assert.assertNotNull(obj);

		//			TenantContext.clearContext();

	}*/

	@Test(groups = { "s2" })
	public void testRootFolder_S2(@Mocked final TenantContext tenantContext, @Mocked final PersistenceManager persistenceManager,
			@Mocked final EntityManager entityManager, @Mocked final EmAnalyticsFolder emAnalyticsFolder) throws EMAnalyticsFwkException {
		new Expectations() {
			{
				TenantContext.getContext();
				result = new TenantInfo("emcsadmin1", 1L);
				PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
				result = entityManager;
				entityManager.createNamedQuery(anyString).setParameter(anyString, anyString).getSingleResult();
				result = null;
			}
		};
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		objFolder.getRootFolder();
	}

	@Test(groups = { "s1" }, expectedExceptions = NullPointerException.class)
	public void testUpdate(@Mocked final QAToolUtil qaToolUtil) throws EMAnalyticsFwkException
	{
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		TenantContext.setContext(
				new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
						TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
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

	/*@Test
	public void testUpdateSystemFolder() throws EMAnalyticsFwkException
	{
		TenantContext.setContext(new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails()
				.get(QAToolUtil.TENANT_USER_NAME).toString()), TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails()
				.get(QAToolUtil.TENANT_NAME).toString())));
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

}


package oracle.sysman.emaas.savedsearch;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.QAToolUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xidai on 6/28/2016.
 */
public class FolderManagerNonGroupsTest {
    private static int folderId;

    private static int childFolderId;
    private static int categoryId;
    private static final String TENANT_ID_OPC2 = TestUtils.TENANT_ID_OPC2;
    private static final String TENANT_ID_OPC3 = TestUtils.TENANT_ID_OPC3;
    private static long TA_FOLDER_ID = 4;

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
    // DISABLE TEST CASES   @Test
    public static void createreadFolderByTenant() throws EMAnalyticsFwkException {

        long folderId = 0;
        long folderId1 = 0;
        try {

            TenantContext.setContext(
                    new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_ID2), TestUtils.getInternalTenantId(TENANT_ID_OPC2)));
            FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
            Folder folder = new FolderImpl();
            folder.setName("FolderTestopc");
            folder.setDescription("TestFolderDescription");
            folder.setUiHidden(false);
            folder = objFolder.saveFolder(folder);
            folderId = folder.getId();
            AssertJUnit.assertFalse(folderId == 0);

        }
        finally {
            TenantContext.clearContext();
        }

        try {

            TenantContext.setContext(
                    new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_ID3), TestUtils.getInternalTenantId(TENANT_ID_OPC3)));

            FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
            Folder folder = new FolderImpl();
            folder.setName("FolderTestopc");
            folder.setDescription("TestFolderDescription");
            folder.setUiHidden(false);
            folder = objFolder.saveFolder(folder);
            folderId1 = folder.getId();
            AssertJUnit.assertFalse(folderId1 == 0);

        }
        finally {
            TenantContext.clearContext();
        }

        try {
            TenantContext.setContext(
                    new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_ID2), TestUtils.getInternalTenantId(TENANT_ID_OPC2)));
            FolderManagerImpl.getInstance();

        }
        finally {
            TenantContext.clearContext();
        }

        try {
            TenantContext.setContext(
                    new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_ID3), TestUtils.getInternalTenantId(TENANT_ID_OPC3)));

            FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
            objFolder.getFolder(folderId);

        }
        finally {
            TenantContext.clearContext();
        }

        FolderManagerImpl objFolder = FolderManagerImpl.getInstance();

        try {
            TenantContext.setContext(
                    new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_ID3), TestUtils.getInternalTenantId(TENANT_ID_OPC3)));
            objFolder.deleteFolder(folderId1, true);
        }
        finally {
            TenantContext.clearContext();
        }

        try {
            TenantContext.setContext(
                    new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_ID2), TestUtils.getInternalTenantId(TENANT_ID_OPC2)));
            objFolder.deleteFolder(folderId, true);
        }
        finally {
            TenantContext.clearContext();
        }

    }

    @Test
    public void testDeleteInvalidFolderId() throws EMAnalyticsFwkException {
        FolderManager foldMan = FolderManager.getInstance();
        TenantContext.setContext(
                new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                        TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
        try {
            foldMan.deleteFolder(987656788498L, true);
        }
        finally {
            TenantContext.clearContext();
        }
    }


    public void testDeleteSystemFolder() throws EMAnalyticsFwkException {

        TenantContext.setContext(
                new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                        TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
        FolderManager foldMan = FolderManager.getInstance();
        try {
            foldMan.deleteFolder(TA_FOLDER_ID, true);
            AssertJUnit.assertTrue("A system folder with id " + TA_FOLDER_ID + " is deleted unexpectedly", false);
        }
        finally {
            TenantContext.clearContext();
        }
    }


    @Test
    public void testDeletFolder_withCategory() throws EMAnalyticsFwkException {
        try {
            //create the category with the folder
            TenantContext.setContext(new TenantInfo(
                    TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                    TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
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
                AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()),
                        new Integer(EMAnalyticsFwkException.ERR_DELETE_FOLDER));
            }
        }
        finally {

            CategoryManager catMan = CategoryManager.getInstance();
            catMan.deleteCategory(categoryId, true);
            TenantContext.clearContext();
        }
    }

    @Test
    public void testDuplicate() throws EMAnalyticsFwkException {
        TenantContext.setContext(
                new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                        TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
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
                objFolder.saveFolder(fi2);


        }

        finally {
            // delete the child folder

            objFolder.deleteFolder(childFolderId, true);
            TenantContext.clearContext();
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
    public void testGetInvalidFolderId() throws EMAnalyticsFwkException {
        TenantContext.setContext(
                new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                        TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
        FolderManager foldMan = FolderManager.getInstance();
        try {
            foldMan.getFolder(987656788498L);
        }
        finally {
            TenantContext.clearContext();
        }
    }

    @Test
    public void testGetpathforFolderId() throws EMAnalyticsFwkException {
        try {
            TenantContext.setContext(new TenantInfo(
                    TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                    TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
            FolderManagerImpl objFolder = FolderManagerImpl.getInstance();

            String[] path = objFolder.getPathForFolderId(folderId);

            Assert.assertNotNull(path);
            Assert.assertNotNull(path.length > 0);
        }
        finally {
            TenantContext.clearContext();
        }

    }

    @Test
    public void testGetSubFolder() throws EMAnalyticsFwkException {
        TenantContext.setContext(
                new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                        TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
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
            if ("TestAnalytics".equals(folderList.get(0).getName())) {
                AssertJUnit.assertEquals("TestAnalytics2", folderList.get(1).getName());
            }
            else if ("TestAnalytics2".equals(folderList.get(0).getName())) {
                AssertJUnit.assertEquals("TestAnalytics", folderList.get(1).getName());
            }

        }finally {
            // delete the sub folder we created
            objFolder.deleteFolder(folderList.get(0).getId(), true);
            objFolder.deleteFolder(folderList.get(1).getId(), true);
            TenantContext.clearContext();
        }

    }

    @Test
    public void testInvalidParentFolder() throws EMAnalyticsFwkException {

        FolderManager fman = FolderManager.getInstance();
        TenantContext.setContext(
                new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_USER_NAME).toString()),
                        TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails().get(QAToolUtil.TENANT_NAME).toString())));
        Folder folder = fman.createNewFolder();
        folder.setName("harsh kumar");
        folder.setParentId(987876788);
        try {
            fman.saveFolder(folder);

        }
        finally {
            TenantContext.clearContext();
        }

    }
}
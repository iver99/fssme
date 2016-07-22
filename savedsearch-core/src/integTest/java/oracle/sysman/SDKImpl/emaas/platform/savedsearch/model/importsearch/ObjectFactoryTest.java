package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch;

import java.math.BigInteger;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategorySet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchSet;

import org.testng.annotations.Test;

/**
 * Created by xidai on 3/11/2016.
 */
@Test(groups={"s1"})
public class ObjectFactoryTest {
    private ObjectFactory objectFactory = new ObjectFactory();

    @Test
    public void testCreateImportSearchImpl() throws Exception {
        objectFactory.createImportSearchImpl();
    }

    @Test
    public void testCreateCategoryDetails() throws Exception {
        objectFactory.createCategoryDetails();
    }

    @Test
    public void testCreateFolderDetails() throws Exception {
        objectFactory.createFolderDetails();
    }

    @Test
    public void testCreateSearchSet() throws Exception {
        objectFactory.createSearchSet();
    }

    @Test
    public void testCreateSearchParameterDetails() throws Exception {
        objectFactory.createSearchParameterDetails();
    }

    @Test
    public void testCreateParameter() throws Exception {
        objectFactory.createParameter();
    }

    @Test
    public void testCreateImportSearchImplSearchParameters() throws Exception {
        objectFactory.createImportSearchImplSearchParameters();
    }

    @Test
    public void testCreateCategoryDetailsParameters() throws Exception {
        objectFactory.createCategoryDetailsParameters();
    }

    @Test
    public void testCreateCategory() throws Exception {
        objectFactory.createCategory(new CategoryDetails());
    }

    @Test
    public void testCreateFolder() throws Exception {
        objectFactory.createFolder(new FolderDetails());
    }

    @Test
    public void testCreateCategoryId() throws Exception {
        objectFactory.createCategoryId(BigInteger.TEN);
    }

    @Test
    public void testCreateCategoryDet() throws Exception {
        objectFactory.createCategoryDet(new Object());
    }

    @Test
    public void testCreateFolderDet() throws Exception {
        objectFactory.createFolderDet(new Object());
    }

    @Test
    public void testCreateSearchSet1() throws Exception {
        objectFactory.createSearchSet(new SearchSet());
    }

    @Test
    public void testCreateFolderId() throws Exception {
        objectFactory.createFolderId(BigInteger.TEN);
    }

    @Test
    public void testCreateFolderSet() throws Exception {
        objectFactory.createFolderSet();
    }

    @Test
    public void testCreateFolderSet1() throws Exception {
        objectFactory.createFolderSet(new FolderSet());
    }

    @Test
    public void testCreateCategorySet() throws Exception {
        objectFactory.createCategorySet(new CategorySet());
    }

    @Test
    public void testCreateCategorySet1() throws Exception {
        objectFactory.createCategorySet();
    }

    @Test
    public void testCreateDefaultFolderId() throws Exception {
        objectFactory.createDefaultFolderId(10);
    }
}
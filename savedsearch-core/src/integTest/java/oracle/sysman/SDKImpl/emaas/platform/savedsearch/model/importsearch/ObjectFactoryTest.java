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
    public void testCreateImportSearchImpl(){
        objectFactory.createImportSearchImpl();
    }

    @Test
    public void testCreateCategoryDetails(){
        objectFactory.createCategoryDetails();
    }

    @Test
    public void testCreateFolderDetails(){
        objectFactory.createFolderDetails();
    }

    @Test
    public void testCreateSearchSet(){
        objectFactory.createSearchSet();
    }

    @Test
    public void testCreateSearchParameterDetails(){
        objectFactory.createSearchParameterDetails();
    }

    @Test
    public void testCreateParameter(){
        objectFactory.createParameter();
    }

    @Test
    public void testCreateImportSearchImplSearchParameters(){
        objectFactory.createImportSearchImplSearchParameters();
    }

    @Test
    public void testCreateCategoryDetailsParameters(){
        objectFactory.createCategoryDetailsParameters();
    }

    @Test
    public void testCreateCategory(){
        objectFactory.createCategory(new CategoryDetails());
    }

    @Test
    public void testCreateFolder(){
        objectFactory.createFolder(new FolderDetails());
    }

    @Test
    public void testCreateCategoryId() {
        objectFactory.createCategoryId(BigInteger.TEN);
    }

    @Test
    public void testCreateCategoryDet(){
        objectFactory.createCategoryDet(new Object());
    }

    @Test
    public void testCreateFolderDet(){
        objectFactory.createFolderDet(new Object());
    }

    @Test
    public void testCreateSearchSet1(){
        objectFactory.createSearchSet(new SearchSet());
    }

    @Test
    public void testCreateFolderId() {
        objectFactory.createFolderId(BigInteger.TEN);
    }

    @Test
    public void testCreateFolderSet(){
        objectFactory.createFolderSet();
    }

    @Test
    public void testCreateFolderSet1(){
        objectFactory.createFolderSet(new FolderSet());
    }

    @Test
    public void testCreateCategorySet(){
        objectFactory.createCategorySet(new CategorySet());
    }

    @Test
    public void testCreateCategorySet1(){
        objectFactory.createCategorySet();
    }

    @Test
    public void testCreateDefaultFolderId(){
        objectFactory.createDefaultFolderId(10);
    }
}
package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author jishshi
 * @since 2016/2/29.
 */
@Test(groups = {"s1"})
public class CategoryManagerImplTest {
    CategoryManagerImpl categoryManagerImpl;
    CategoryManager categoryManager;


    @Mocked
    PersistenceManager persistenceManager;


    @BeforeMethod
    public void setUp() throws Exception {
         categoryManager = CategoryManagerImpl.getInstance();
    }

    @Test
    public void testGetInstance() throws Exception {
        Assert.assertNotNull(categoryManager);
    }

    @Test
    public void testCreateNewCategory() throws Exception {
        Assert.assertNotNull(categoryManager.createNewCategory());
    }

    @Test
    public void testDeleteCategory() throws Exception {
//            categoryManager.deleteCategory(12l,false);
    }

    @Test
    public void testEditCategory() throws Exception {

    }

    @Test
    public void testGetAllCategories() throws Exception {

    }

    @Test
    public void testGetCategory() throws Exception {

    }

    @Test
    public void testGetCategory1() throws Exception {

    }

    @Test
    public void testSaveCategory() throws Exception {

    }

    @Test
    public void testSaveMultipleCategories() throws Exception {

    }
}
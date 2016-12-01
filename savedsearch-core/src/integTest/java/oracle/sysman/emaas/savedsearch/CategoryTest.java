package oracle.sysman.emaas.savedsearch;

import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class CategoryTest extends BaseTest
{
	private static Integer categoryId;


	//@Mocked
    //PersistenceManager persistenceManager;

	@AfterClass
	public static void testDelete() throws EMAnalyticsFwkException {
			CategoryManager catMan = CategoryManager.getInstance();

			Category category = catMan.getCategory(categoryId);

			// to delete the categoryParams
			category.setParameters(new ArrayList<Parameter>());
			category = catMan.editCategory(category);
			AssertJUnit.assertNull(category.getParameters());

			// now delete the category
			catMan.deleteCategory(categoryId, true);
			// here the assertion not required or required

	}

	@BeforeClass
	public static void testSaveCategory() throws EMAnalyticsFwkException {


			CategoryManager catMan = CategoryManager.getInstance();
			Category category = catMan.createNewCategory();
			category.setName("CategoryName");
			category.setDescription("CategoryTest");
			category.setProviderName("ProviderNameTest");
			category.setProviderVersion("ProviderVersionTest");
			category.setProviderDiscovery("ProviderDiscoveryTest");
			category.setProviderAssetRoot("ProviderAssetRootTest");

			// set the parameter for the category
			Parameter sp1 = new Parameter();
			sp1.setName("Param1");
			sp1.setValue("ParamValue1");

			Parameter sp2 = new Parameter();
			sp2.setName("Param2");
			sp2.setValue("ParamValue2");

			List<Parameter> list = new ArrayList<Parameter>();
			list.add(sp1);
			list.add(sp2);
			category.setParameters(list);

			category = catMan.saveCategory(category);
			categoryId = category.getId();
			AssertJUnit.assertFalse(categoryId == 0);

			category = catMan.getCategory(categoryId);
			AssertJUnit.assertEquals(2, category.getParameters().size());
			// assert the value we have saved
			AssertJUnit.assertEquals("CategoryName", category.getName());
			AssertJUnit.assertEquals("CategoryTest", category.getDescription());
			AssertJUnit.assertEquals("ProviderNameTest", category.getProviderName());
			AssertJUnit.assertEquals("ProviderVersionTest", category.getProviderVersion());
			AssertJUnit.assertEquals("ProviderDiscoveryTest", category.getProviderDiscovery());
			AssertJUnit.assertEquals("ProviderAssetRootTest", category.getProviderAssetRoot());
			// Assert.assertEquals("MyCategory", category.getDisplayName());
			AssertJUnit.assertNotNull(category.getCreatedOn());
	}

	@BeforeClass
	public void initTenantDetails()
	{
		TenantContext.setContext(new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails()
				.get(QAToolUtil.TENANT_USER_NAME).toString()), TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails()
				.get(QAToolUtil.TENANT_NAME).toString())));

	}

	/*@AfterClass
	public void removeTenantDetails()
	{
		TenantContext.clearContext();
	}*/

	/*@Test
	public void testDefaultFolder() 
	{

		CategoryManager catMan = CategoryManager.getInstance();
		AssertJUnit.assertNull(catMan.getCategory("Log Analytics").getDefaultFolderId());

	}*/

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testDeleteCategoryInvalidId() throws EMAnalyticsFwkException {
		CategoryManager catMan = CategoryManager.getInstance();
			catMan.deleteCategory(99898987898L, true);

		 
	}

	@Test 
	public void testEditCategory() throws EMAnalyticsFwkException {

			CategoryManager obj = CategoryManagerImpl.getInstance();
			Category category = obj.getCategory(categoryId);

			AssertJUnit.assertNotNull(category);
			// now set the some value
			category.setName("testName");
			category.setDescription("testcase checking");
			category.setDefaultFolderId(1);
			category.setProviderName("ProviderNameTestEdit");
			category.setProviderVersion("ProviderVersionTestEdit");
			category.setProviderDiscovery("ProviderDiscoveryTestEdit");
			category.setProviderAssetRoot("ProviderAssetRootTestEdit");

			// set the parameter for the category
			Parameter sp1 = new Parameter();
			sp1.setName("Param2");
			sp1.setValue("ParamValue2");

			List<Parameter> list = new ArrayList<Parameter>();
			list.add(sp1);

			category.setParameters(list);
			obj.editCategory(category);

			category = obj.getCategory(categoryId);

			// Assert.assertEquals(1,category.getParameters().size());

			AssertJUnit.assertEquals("testName", category.getName());
			AssertJUnit.assertEquals("testcase checking", category.getDescription());
			AssertJUnit.assertEquals("ProviderNameTestEdit", category.getProviderName());
			AssertJUnit.assertEquals("ProviderVersionTestEdit", category.getProviderVersion());
			AssertJUnit.assertEquals("ProviderDiscoveryTestEdit", category.getProviderDiscovery());
			AssertJUnit.assertEquals("ProviderAssetRootTestEdit", category.getProviderAssetRoot());
			// Assert.assertEquals("displayTestName",
			// category.getDisplayName());

	}

	/*@Test
	public void testGetAllCategories() 
	{
		CategoryManager catMan = CategoryManager.getInstance();
		// TODO Harsh .. fix this !!
		// This is not correct.. as when there will be changes to our schema
		// seed data
		// more data will be added to our tables this test will fail.
		// This will probably have no valid test case !! unless we clear all
		// category data, add our entries only and all that will require new
		// schema creation at each test launch.
		AssertJUnit.assertNotNull(new Integer(catMan.getAllCategories().size()));
	}*/

	/*@Test
	public void testGetCategoryById() 
	{
		TenantContext.setContext(TENANT_ID_OPC2);
		CategoryManager catMan = CategoryManager.getInstance();
		AssertJUnit.assertNotNull(catMan.getCategory(1));
		TenantContext.clearContext();
	}*/

	/*@Test
	public void testGetCategoryByName() 
	{
		TenantContext.setContext(TENANT_ID_OPC2);
		CategoryManager catMan = CategoryManager.getInstance();
		AssertJUnit.assertNotNull(catMan.getCategory("Log Analytics"));
		TenantContext.clearContext();
	}*/

	/*@Test
	public void testEditCategory_DuplicateName() 
	{

		try {
			CategoryManager obj = CategoryManagerImpl.getInstance();
			Category category = obj.getCategory(categoryId);

			AssertJUnit.assertNotNull(category);
			// now set the some value
			category.setName("Log Analytics");

			obj.editCategory(category);

		}
		catch (EMAnalyticsFwkException emanfe) {
			AssertJUnit.assertEquals(new Integer(emanfe.getErrorCode()), new Integer(
					EMAnalyticsFwkException.ERR_DUPLICATE_CATEGORY_NAME));
		}
	}*/

        @Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testgetCategoryInvalidId() throws EMAnalyticsFwkException {

		CategoryManager catMan = CategoryManager.getInstance();
			catMan.getCategory(99898987898L);


	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testgetCategoryInvalidName() throws EMAnalyticsFwkException {
		CategoryManager catMan = CategoryManager.getInstance();
			catMan.getCategory("this is invalid name");

	}

	@Test(expectedExceptions = {EMAnalyticsFwkException.class})
	public void testInvalidData() throws EMAnalyticsFwkException {

		CategoryManager catMan = CategoryManager.getInstance();
		Category category = catMan.createNewCategory();
		category.setName("CategoryName");
		category.setDescription("CategoryTest");
		category.setDefaultFolderId(9878787);
			catMan.saveCategory(category);

	}

	@Test 
	public void testSaveCategory_DuplicateName() throws EMAnalyticsFwkException {

		CategoryManager catMan = CategoryManager.getInstance();
		Category category = catMan.createNewCategory();
		category.setName("Log Analytics");
		category.setProviderName("ProviderNameUT");
		category.setProviderVersion("ProviderVersionUT");
		category.setProviderDiscovery("ProviderDiscoveryUT");
		category.setProviderAssetRoot("ProviderAssetRootUT");
			category = catMan.saveCategory(category);


	}
}

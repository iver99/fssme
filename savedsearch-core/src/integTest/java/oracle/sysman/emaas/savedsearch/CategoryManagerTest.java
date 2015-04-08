package oracle.sysman.emaas.savedsearch;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CategoryManagerTest extends BaseTest
{
	private static final String TENANT_ID_OPC1 = TestUtils.TENANT_ID_OPC1;

	@BeforeClass
	public void initTenantDetails()
	{
		TenantContext.setContext(new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_ID1), TestUtils
				.getInternalTenantId(TENANT_ID_OPC1)));
	}

	@AfterClass
	public void removeTenantDetails()
	{
		TenantContext.clearContext();
	}

	@Test
	public void testDeleteCategoryWithSearch() throws EMAnalyticsFwkException
	{
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		Folder folder = new FolderImpl();
		folder.setName("FolderTest" + System.currentTimeMillis());
		folder.setDescription("TestFolderDescription");
		folder.setUiHidden(false);
		folder = objFolder.saveFolder(folder);

		CategoryManager objCategory = CategoryManagerImpl.getInstance();
		Category cat = new CategoryImpl();
		cat.setName("CategoryTest" + System.currentTimeMillis());
		String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
		cat.setOwner(currentUser);
		cat.setDefaultFolderId(folder.getParentId());
		cat.setProviderName("TestProviderName");
		cat.setProviderVersion("TestProviderVersion");
		cat.setProviderDiscovery("TestProviderDiscovery");
		cat.setProviderAssetRoot("TestProviderAssetRoot");
		cat = objCategory.saveCategory(cat);

		SearchManager objSearch = SearchManager.getInstance();

		//create a new search inside the folder we created
		Search search = objSearch.createNewSearch();
		search.setDescription("testing purpose");
		search.setName("Dummy Search");
		search.setFolderId(folder.getId());
		search.setCategoryId(cat.getId());
		search.setIsWidget(false);
		search = objSearch.saveSearch(search);

		// soft deletion test
		try {
			objCategory.deleteCategory(cat.getId(), false);
		}
		catch (EMAnalyticsFwkException e) {
			AssertJUnit.fail();
		}

		// hard deletion test
		boolean catchExpectedException = false;
		try {
			objCategory.deleteCategory(cat.getId(), true);
		}
		catch (EMAnalyticsFwkException e) {
			if ("Error while deleting the category as it has associated searches".equals(e.getMessage())
					&& EMAnalyticsFwkException.ERR_DELETE_CATEGORY == e.getErrorCode()) {
				catchExpectedException = true;
			}
		}
		AssertJUnit.assertTrue(catchExpectedException);

		objSearch.deleteSearch(search.getId(), true);
		objCategory.deleteCategory(cat.getId(), true);
		objFolder.deleteFolder(folder.getId(), true);
	}
}

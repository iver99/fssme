package oracle.sysman.emaas.savedsearch;

import java.math.BigInteger;
import java.util.Properties;

import javax.persistence.EntityManager;

import mockit.Expectations;
import mockit.Mocked;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.QAToolUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
@Test(groups = "s2")
public class CategoryManagerTest extends BaseTest
{

    @Mocked
    QAToolUtil qaToolUtil;

    @Mocked
    PersistenceManager persistenceManager;

    @BeforeMethod
	public void initTenantDetails()
	{
        new Expectations(){{

            final  Properties props = new Properties();
            props.put(QAToolUtil.TENANT_USER_NAME, "TENANT_USER_NAME");
            props.put(QAToolUtil.TENANT_NAME, "TENANT_NAME");

            QAToolUtil.getTenantDetails();
            result = props;

        }};
		TenantContext.setContext(new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails()
				.get(QAToolUtil.TENANT_USER_NAME).toString()), TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails()
				.get(QAToolUtil.TENANT_NAME).toString())));
	}

	@AfterClass
	public void removeTenantDetails()
	{
		TenantContext.clearContext();
	}

	@Test(expectedExceptions = EMAnalyticsFwkException.class)
	public void testDeleteCategoryWithSearch(@Mocked final EntityManager entityManager) throws EMAnalyticsFwkException {
		final EmAnalyticsFolder emAnalyticsFolder = new EmAnalyticsFolder();
		emAnalyticsFolder.setDeleted(BigInteger.ZERO);
		final EmAnalyticsCategory emAnalyticsCategory = new EmAnalyticsCategory();
		emAnalyticsCategory.setDeleted(BigInteger.ZERO);
        new Expectations(){
            {
                entityManager.find(EmAnalyticsFolder.class, (BigInteger) any);
                result = emAnalyticsFolder;
                entityManager.find(EmAnalyticsCategory.class, (BigInteger) any);
                result = emAnalyticsCategory;
            }
        };
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
        Assert.assertFalse(catchExpectedException);

		objSearch.deleteSearch(search.getId(), true);
		objCategory.deleteCategory(cat.getId(), true);
		objFolder.deleteFolder(folder.getId(), true);
	}
}

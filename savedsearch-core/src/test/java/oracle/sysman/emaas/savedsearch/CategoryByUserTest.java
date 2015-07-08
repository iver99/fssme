/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.savedsearch;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class CategoryByUserTest extends BaseTest
{

	private static final String TENANT_ID_OPC1 = TestUtils.TENANT_ID_OPC1;

	@Test
	public void createCategoryByUser() throws EMAnalyticsFwkException
	{
		int catid1 = 0;
		int catid2 = 0;
		TenantContext.setContext(new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_ID1), TestUtils
				.getInternalTenantId(TENANT_ID_OPC1)));
		try {
			CategoryManager objCategory = CategoryManagerImpl.getInstance();
			Category cat = new CategoryImpl();
			cat.setName("CategoryTestByUser");
			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
			cat.setOwner(currentUser);
			cat.setDefaultFolderId(1);
			cat.setProviderName("TestProviderName");
			cat.setProviderVersion("TestProviderVersion");
			cat.setProviderDiscovery("TestProviderDiscovery");
			cat.setProviderAssetRoot("TestProviderAssetRoot");
			cat = objCategory.saveCategory(cat);
			catid1 = cat.getId();
			AssertJUnit.assertTrue(catid1 > 0);

		}
		finally {
			TenantContext.clearContext();
		}

		TenantContext.setContext(new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_USER_ID1), TestUtils
				.getInternalTenantId(TENANT_ID_OPC1)));
		try {
			CategoryManager objCategory = CategoryManagerImpl.getInstance();
			Category cat = new CategoryImpl();
			cat.setName("CategoryTestByUser");
			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
			cat.setOwner(currentUser);
			cat.setDefaultFolderId(1);
			cat.setProviderName("TestProviderName");
			cat.setProviderVersion("TestProviderVersion");
			cat.setProviderDiscovery("TestProviderDiscovery");
			cat.setProviderAssetRoot("TestProviderAssetRoot");
			cat = objCategory.saveCategory(cat);
			catid2 = cat.getId();

		}
		finally {
			TenantContext.clearContext();
		}

		boolean catchExpectedException = false;
		TenantContext.setContext(new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_ID1), TestUtils
				.getInternalTenantId(TENANT_ID_OPC1)));
		try {
			CategoryManager objCategory = CategoryManagerImpl.getInstance();
			objCategory.deleteCategory(catid2, true);
		}
		catch (EMAnalyticsFwkException e) {
			catchExpectedException = true;
		}
		finally {

			TenantContext.clearContext();
		}
		AssertJUnit.assertTrue(catchExpectedException);
		catchExpectedException = false;
		TenantContext.setContext(new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_USER_ID1), TestUtils
				.getInternalTenantId(TENANT_ID_OPC1)));
		try {
			CategoryManager objCategory = CategoryManagerImpl.getInstance();
			objCategory.deleteCategory(catid1, true);
		}
		catch (EMAnalyticsFwkException e) {
			catchExpectedException = true;
		}
		finally {

			TenantContext.clearContext();
		}
		catchExpectedException = false;
		TenantContext.setContext(new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_ID1), TestUtils
				.getInternalTenantId(TENANT_ID_OPC1)));
		try {
			CategoryManager objCategory = CategoryManagerImpl.getInstance();
			objCategory.deleteCategory(catid1, true);
		}
		finally {

			TenantContext.clearContext();
		}

		TenantContext.setContext(new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_USER_ID1), TestUtils
				.getInternalTenantId(TENANT_ID_OPC1)));
		try {
			CategoryManager objCategory = CategoryManagerImpl.getInstance();
			objCategory.deleteCategory(catid2, true);
		}
		finally {

			TenantContext.clearContext();
		}
	}

}
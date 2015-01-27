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
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class MultitenentCategoryTest extends BaseTest
{

	private static final String TENANT_ID_OPC1 = TestUtils.TENANT_ID_OPC1;
	private static final String TENANT_ID_OPC2 = TestUtils.TENANT_ID_OPC2;
	private static final String TENANT_ID_OPC3 = TestUtils.TENANT_ID_OPC3;

	private static Long opc1 = null;
	private static Long opc2 = null;
	private static Long opc3 = null;

	@Test
	public static void categoryTest()
	{
		opc1 = TestUtils.getInternalTenantId(TENANT_ID_OPC1);
		opc2 = TestUtils.getInternalTenantId(TENANT_ID_OPC2);
		opc3 = TestUtils.getInternalTenantId(TENANT_ID_OPC3);

		int id1 = MultitenentCategoryTest.createCategory(opc1);
		int id2 = MultitenentCategoryTest.createCategory(opc2);
		int id3 = MultitenentCategoryTest.createCategory(opc3);
		Assert.assertTrue(id1 > 0);
		Assert.assertTrue(id2 > 0);
		Assert.assertTrue(id3 > 0);

		Assert.assertTrue(MultitenentCategoryTest.getCategory(id1, opc1) != null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id2, opc2) != null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id3, opc3) != null);

		Assert.assertTrue(MultitenentCategoryTest.getCategory(id1, opc2) == null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id1, opc3) == null);

		Assert.assertTrue(MultitenentCategoryTest.getCategory(id2, opc1) == null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id2, opc3) == null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id3, opc1) == null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id3, opc2) == null);

		Assert.assertTrue(MultitenentCategoryTest.deleteCategory(id1, opc1) == true);
		Assert.assertTrue(MultitenentCategoryTest.deleteCategory(id2, opc2) == true);
		Assert.assertTrue(MultitenentCategoryTest.deleteCategory(id3, opc3) == true);
	}

	public static int createCategory(Long value)
	{
		int id = 0;
		TenantContext.setContext(value);
		try {
			CategoryManager fmger = CategoryManager.getInstance();
			Category catObj = new CategoryImpl();
			catObj.setName("TestMultitency");
			catObj.setDescription("TestMultitency Desc");
			catObj.setProviderName("ProviderNameTest");
			catObj.setProviderVersion("ProviderVersionTest");
			catObj.setProviderDiscovery("ProviderDiscoveryTest");
			catObj.setProviderAssetRoot("ProviderAssetRootTest");
			catObj = fmger.saveCategory(catObj);
			id = catObj.getId();
		}
		catch (Exception e) {
			//do nothing	
		}
		finally {
			TenantContext.clearContext();
		}
		return id;
	}

	public static boolean deleteCategory(int id, Long value)
	{
		boolean bResult = false;
		try {
			TenantContext.setContext(value);
			CategoryManager fmger = CategoryManager.getInstance();
			fmger.deleteCategory(id, true);
			bResult = true;
		}
		catch (Exception e) {
			bResult = false;
		}
		finally {
			TenantContext.clearContext();
		}
		return bResult;
	}

	public static Category getCategory(int id, Long value)
	{
		Category catObj = null;
		try {
			TenantContext.setContext(value);
			CategoryManager fmger = CategoryManager.getInstance();
			catObj = fmger.getCategory(id);
		}
		catch (Exception e) {
			catObj = null;
		}
		finally {
			TenantContext.clearContext();
		}
		return catObj;
	}

}

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
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.UpgradeManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;

/**
 * @author vinjoshi
 */
public class MultitenentCategoryTest extends BaseTest
{

	private static final String TENANT_OPC1 = "TENANT_OPC_MT1";
	private static final String TENANT_OPC2 = "TENANT_OPC_MT2";
	private static final String TENANT_OPC3 = "TENANT_OPC_MT3";

	@BeforeClass
	public static void categoryTest()
	{

		MultitenentCategoryTest.setup(TENANT_OPC1);
		MultitenentCategoryTest.setup(TENANT_OPC2);
		MultitenentCategoryTest.setup(TENANT_OPC3);
		int id1 = MultitenentCategoryTest.createCategory(TENANT_OPC1);
		int id2 = MultitenentCategoryTest.createCategory(TENANT_OPC2);
		int id3 = MultitenentCategoryTest.createCategory(TENANT_OPC3);
		Assert.assertTrue(id1 > 0);
		Assert.assertTrue(id2 > 0);
		Assert.assertTrue(id3 > 0);

		Assert.assertTrue(MultitenentCategoryTest.getCategory(id1, TENANT_OPC1) != null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id2, TENANT_OPC2) != null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id3, TENANT_OPC3) != null);

		Assert.assertTrue(MultitenentCategoryTest.getCategory(id1, TENANT_OPC2) == null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id1, TENANT_OPC3) == null);

		Assert.assertTrue(MultitenentCategoryTest.getCategory(id2, TENANT_OPC1) == null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id2, TENANT_OPC3) == null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id3, TENANT_OPC1) == null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id3, TENANT_OPC2) == null);

		Assert.assertTrue(MultitenentCategoryTest.deleteCategory(id1, TENANT_OPC1) == true);
		Assert.assertTrue(MultitenentCategoryTest.deleteCategory(id2, TENANT_OPC2) == true);
		Assert.assertTrue(MultitenentCategoryTest.deleteCategory(id3, TENANT_OPC3) == true);
	}

	public static int createCategory(String value)
	{
		int id = 0;
		TenantContext.setContext(value);
		try {
			CategoryManager fmger = CategoryManager.getInstance();
			Category catObj = new CategoryImpl();
			catObj.setName("TestMultitency");
			catObj.setDescription("TestMultitency Desc");
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

	public static boolean deleteCategory(int id, String value)
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

	public static Category getCategory(int id, String value)
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

	private static void setup(String value)
	{
		TenantContext.setContext(value);
		try {
			AssertJUnit.assertTrue(UpgradeManagerImpl.getInstance().upgradeData() == true);
		}
		catch (Exception e) {
			AssertJUnit.fail(e.getLocalizedMessage());
		}
		finally {
			TenantContext.clearContext();
		}

	}

}

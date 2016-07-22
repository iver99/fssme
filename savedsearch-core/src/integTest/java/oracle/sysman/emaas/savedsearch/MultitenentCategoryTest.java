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

import java.math.BigInteger;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.Assert;

/**
 * @author vinjoshi
 */
public class MultitenentCategoryTest extends BaseTest
{

	private static final String TENANT_ID_OPC1 = TestUtils.TENANT_ID_OPC1;
	private static final String TENANT_ID_OPC2 = TestUtils.TENANT_ID_OPC2;
	private static final String TENANT_ID_OPC3 = TestUtils.TENANT_ID_OPC3;

	private static final String TENANT_ID1 = TestUtils.TENANT_ID1;
	private static final String TENANT_ID2 = TestUtils.TENANT_ID2;
	private static final String TENANT_ID3 = TestUtils.TENANT_ID3;

	private static Long opc1 = null;
	private static Long opc2 = null;
	private static Long opc3 = null;

	private static String username1 = null;
	private static String username2 = null;
	private static String username3 = null;

	//@Test
	public static void categoryTest()
	{
		opc1 = TestUtils.getInternalTenantId(TENANT_ID_OPC1);
		opc2 = TestUtils.getInternalTenantId(TENANT_ID_OPC2);
		opc3 = TestUtils.getInternalTenantId(TENANT_ID_OPC3);
		username1 = TestUtils.getUsername(TENANT_ID1);
		username2 = TestUtils.getUsername(TENANT_ID2);
		username3 = TestUtils.getUsername(TENANT_ID3);

		BigInteger id1 = MultitenentCategoryTest.createCategory(username1, opc1);
		BigInteger id2 = MultitenentCategoryTest.createCategory(username2, opc2);
		BigInteger id3 = MultitenentCategoryTest.createCategory(username3, opc3);
		Assert.assertTrue(BigInteger.ZERO.compareTo(id1) == -1);
		Assert.assertTrue(BigInteger.ZERO.compareTo(id2) == -1);
		Assert.assertTrue(BigInteger.ZERO.compareTo(id3) == -1);

		Assert.assertTrue(MultitenentCategoryTest.getCategory(id1, opc1, username1) != null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id2, opc2, username2) != null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id3, opc3, username3) != null);

		Assert.assertTrue(MultitenentCategoryTest.getCategory(id1, opc2, username2) == null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id1, opc3, username3) == null);

		Assert.assertTrue(MultitenentCategoryTest.getCategory(id2, opc1, username1) == null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id2, opc3, username3) == null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id3, opc1, username1) == null);
		Assert.assertTrue(MultitenentCategoryTest.getCategory(id3, opc2, username2) == null);

		Assert.assertTrue(MultitenentCategoryTest.deleteCategory(id1, opc1, username1) == true);
		Assert.assertTrue(MultitenentCategoryTest.deleteCategory(id2, opc2, username2) == true);
		Assert.assertTrue(MultitenentCategoryTest.deleteCategory(id3, opc3, username3) == true);
	}

	public static BigInteger createCategory(String username, Long value)
	{
		BigInteger id = BigInteger.ZERO;
		TenantContext.setContext(new TenantInfo(username, value));
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

	public static boolean deleteCategory(BigInteger id, Long value, String username)
	{
		boolean bResult = false;
		try {
			TenantContext.setContext(new TenantInfo(username, value));
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

	public static Category getCategory(BigInteger id, Long value, String username)
	{
		Category catObj = null;
		try {
			TenantContext.setContext(new TenantInfo(username, value));
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

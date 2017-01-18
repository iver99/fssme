/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author shangwan
 */
public class CategoryDetailsTest
{

	@Test (groups = {"s1"})
	public void testCategoryDetails()
	{

		CategoryDetails catdetail = new CategoryDetails();
		catdetail.setId(new BigInteger("1000"));
		catdetail.setName("test_name");
		catdetail.setDescription("test categroy details");
		catdetail.setDefaultFolderId(new BigInteger("999"));
		catdetail.setProviderName("Test provider name");
		catdetail.setProviderVersion("Test provider version");
		catdetail.setProviderDiscovery("Test provider discovery");
		catdetail.setProviderAssetRoot("Test_provider_asset_root");
		CategoryDetails.Parameters sp1 = new CategoryDetails.Parameters();
		catdetail.setParameters(sp1);

		Assert.assertNotNull(sp1.getParameter());
		Assert.assertEquals("test categroy details", catdetail.getDescription());
		Assert.assertEquals(1000, catdetail.getId().intValue());
		Assert.assertEquals("test_name", catdetail.getName());
		Assert.assertEquals(999, catdetail.getDefaultFolderId().intValue());
		Assert.assertEquals(sp1, catdetail.getParameters());
		Assert.assertEquals("Test provider name", catdetail.getProviderName());
		Assert.assertEquals("Test provider version", catdetail.getProviderVersion());
		Assert.assertEquals("Test provider discovery", catdetail.getProviderDiscovery());
		Assert.assertEquals("Test_provider_asset_root", catdetail.getProviderAssetRoot());

	}
}

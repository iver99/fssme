/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.savedsearch;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.CategoryDetails;

import org.testng.AssertJUnit;
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
		catdetail.setId(1000);
		catdetail.setDescription("test categroy details");
		catdetail.setDefaultFolderId(999);
		catdetail.setProviderName("Test provider name");
		catdetail.setProviderVersion("Test provider version");
		catdetail.setProviderDiscovery("Test provider discovery");
		catdetail.setProviderAssetRoot("Test_provider_asset_root");
		CategoryDetails.Parameters sp1 = new CategoryDetails.Parameters();
		catdetail.setParameters(sp1);

		AssertJUnit.assertNotNull(sp1.getParameter());
		AssertJUnit.assertEquals("test categroy details", catdetail.getDescription());
		AssertJUnit.assertEquals(1000, catdetail.getId().intValue());
		AssertJUnit.assertEquals(999, catdetail.getDefaultFolderId().intValue());
		AssertJUnit.assertEquals(sp1, catdetail.getParameters());
		AssertJUnit.assertEquals("Test provider name", catdetail.getProviderName());
		AssertJUnit.assertEquals("Test provider version", catdetail.getProviderVersion());
		AssertJUnit.assertEquals("Test provider discovery", catdetail.getProviderDiscovery());
		AssertJUnit.assertEquals("Test_provider_asset_root", catdetail.getProviderAssetRoot());

	}
}

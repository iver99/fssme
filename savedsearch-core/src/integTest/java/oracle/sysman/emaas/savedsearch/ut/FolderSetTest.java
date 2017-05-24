/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.savedsearch.ut;

import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportCategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportSearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategorySet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class FolderSetTest
{

	@Test (groups = {"s1"})
	public void testObjectSet()
	{
		FolderSet t = new FolderSet();
		List<FolderDetails> folderSet = t.getFolderSet();
		Assert.assertNotNull(folderSet);
		t.setFolderSet(folderSet);

		CategorySet c = new CategorySet();
		List<ImportCategoryImpl> categorySet = c.getCategorySet();
		Assert.assertNotNull(categorySet);
		c.setCategorySet(categorySet);

		SearchSet s = new SearchSet();
		List<ImportSearchImpl> search = s.getSearchSet();
		Assert.assertNotNull(search);
		s.setSearchSet(search);

		TenantInfo a = new TenantInfo("admin", 1L);
		a.setTenantInternalId(1L);
		a.setUsername("admin");
		Assert.assertNotNull(a.getTenantInternalId());
		Assert.assertNotNull(a.getUsername());

	}

}

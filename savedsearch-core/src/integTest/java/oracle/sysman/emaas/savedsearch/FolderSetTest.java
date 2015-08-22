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

	@Test
	public void testObjectSet()
	{
		FolderSet t = new FolderSet();
		List<FolderDetails> Folder = t.getFolderSet();
		Assert.assertNotNull(Folder);
		t.setFolderSet(Folder);

		CategorySet c = new CategorySet();
		List<ImportCategoryImpl> Category = c.getCategorySet();
		Assert.assertNotNull(Category);
		c.setCategorySet(Category);

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

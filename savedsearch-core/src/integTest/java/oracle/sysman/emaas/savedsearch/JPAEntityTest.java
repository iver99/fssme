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

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategoryParam;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategoryParamPK;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class JPAEntityTest
{

	@Test
	public void testEntityCategory() throws Exception
	{
		EmAnalyticsCategory cat = new EmAnalyticsCategory();
		cat.setCategoryId(1);
		Date utcNow = DateUtil.getCurrentUTCTime();
		cat.setCreationDate(utcNow);
		cat.setDeleted(0);
		cat.setDescription("Test");
		cat.setDescriptionNlsid("Test");
		cat.setDescriptionSubsystem("Test");
		cat.setEmAnalyticsFolder(null);
		cat.setEmAnalyticsSearches(null);
		cat.setEmPluginId("Test");
		cat.setNameNlsid("Test");
		cat.setNameSubsystem("Test");
		cat.setOwner("Test");
		cat.setProviderAssetRoot("Test");
		cat.setProviderDiscovery("Test");
		cat.setProviderName("Test");
		cat.setProviderVersion("Test");
		EmAnalyticsCategoryParam param = new EmAnalyticsCategoryParam();
		param.setCategoryId(1);
		param.setEmAnalyticsCategory(null);
		param.setName("Test");
		param.setValue("Test");
		EmAnalyticsCategoryParamPK parampk = new EmAnalyticsCategoryParamPK();
		parampk.setCategoryId(1);
		parampk.setName("Test");

		/*Map<EmAnalyticsCategoryParamPK, EmAnalyticsCategoryParam> newParams = new HashMap<EmAnalyticsCategoryParamPK, EmAnalyticsCategoryParam>();
		newParams.put(parampk, param);
		newParams.put(parampk, param);
		cat.setEmAnalyticsCategoryParams(newParams);*/

		Assert.assertNotNull(cat.getCreationDate());
		Assert.assertNotNull(cat.getDeleted());
		Assert.assertNotNull(cat.getDescription());
		Assert.assertNotNull(cat.getDescriptionNlsid());
		Assert.assertNotNull(cat.getDescriptionSubsystem());
		Assert.assertNull(cat.getEmAnalyticsFolder());
		Assert.assertNull(cat.getEmAnalyticsSearches());
		Assert.assertNotNull(cat.getEmPluginId());
		Assert.assertNotNull(cat.getNameNlsid());
		Assert.assertNotNull(cat.getNameSubsystem());
		Assert.assertNotNull(cat.getOwner());
		Assert.assertNotNull(cat.getProviderAssetRoot());
		Assert.assertNotNull(cat.getProviderDiscovery());
		Assert.assertNotNull(cat.getProviderName());
		Assert.assertNotNull(cat.getProviderVersion());

	}

	@Test
	public void testEntityFolder() throws Exception
	{

		EmAnalyticsFolder fld = new EmAnalyticsFolder();
		Date utcNow = DateUtil.getCurrentUTCTime();
		fld.setCreationDate(utcNow);
		fld.setDescription("desc");
		fld.setDescriptionNlsid("desc");
		fld.setDescriptionSubsystem("desc");
		fld.setEmAnalyticsCategories(null);
		fld.setEmAnalyticsFolder(null);
		fld.setEmAnalyticsFolders(null);
		fld.setEmPluginId("null");
		fld.setFolderId(1);
		fld.setName("abc");
		fld.setLastModificationDate(utcNow);
		fld.setUiHidden(new BigDecimal(1));
		String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
		fld.setOwner(currentUser);
		fld.setLastModifiedBy(currentUser);
		Assert.assertNotNull(fld.getCreationDate());
		Assert.assertNotNull(fld.getDescription());
		Assert.assertNotNull(fld.getDescriptionNlsid());
		Assert.assertNotNull(fld.getDescriptionSubsystem());
		Assert.assertNull(fld.getEmAnalyticsCategories());
		Assert.assertNull(fld.getEmAnalyticsFolder());
		Assert.assertNull(fld.getEmAnalyticsFolders());
		Assert.assertNotNull(fld.getEmPluginId());
		Assert.assertNotNull(fld.getFolderId());
		Assert.assertNotNull(fld.getName());
		Assert.assertNotNull(fld.getLastModificationDate());
		Assert.assertNull(fld.getLastModifiedBy());
		Assert.assertNotNull(fld.getUiHidden());

	}

}

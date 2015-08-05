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

/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategory;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategoryParam;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsCategoryParamPK;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsFolder;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsLastAccessPK;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSchemaVerPK;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearch;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearchParam;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSearchParamPK;

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
		param.setEmAnalyticsCategory(cat);
		param.setName("Test");
		param.setValue("Test");

		EmAnalyticsCategoryParam param1 = new EmAnalyticsCategoryParam();
		param1.setCategoryId(1);
		param1.setEmAnalyticsCategory(cat);
		param1.setName("tesT");
		param1.setValue("tesT");

		EmAnalyticsCategoryParamPK parampk = new EmAnalyticsCategoryParamPK();
		parampk.setCategoryId(1);
		parampk.setName("Test");

		EmAnalyticsCategoryParam param2 = new EmAnalyticsCategoryParam();
		cat.getEmAnalyticsCategoryParams().add(param);
		cat.getEmAnalyticsCategoryParams().add(param1);
		cat.getEmAnalyticsCategoryParams().add(param2);
		cat.setEmAnalyticsCategoryParams(null);
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
		Assert.assertNotNull(parampk.getName());
		Assert.assertNotNull(parampk.getCategoryId());
		Assert.assertNotNull(param.getEmAnalyticsCategory());
		Assert.assertNotNull(param.getCategoryId());
		Assert.assertNotNull(param.equals(param1));
		Assert.assertNotNull(param.equals(param2));
		Assert.assertNotNull(param.equals(param));
		Assert.assertNotNull(param.equals(null));

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

	@Test
	public void testEntitySearch() throws Exception
	{
		EmAnalyticsSearch search = new EmAnalyticsSearch();
		Date utcNow = DateUtil.getCurrentUTCTime();
		search.setAccessDate(utcNow);
		search.setCreationDate(utcNow);
		search.setDeleted(1);
		search.setDescription("");
		search.setDescriptionNlsid("");
		search.setDescriptionSubsystem("");
		search.setEmAnalyticsCategory(null);
		search.setEmAnalyticsFolder(null);
		search.setEmPluginId("");
		search.setId(1);
		search.setIsLocked(new BigDecimal(1));
		search.setIsWidget(new Long(1));
		search.setLastModificationDate(utcNow);
		search.setLastModifiedBy("");
		search.setMetadataClob("");
		search.setName("");
		search.setNameNlsid("");
		search.setNameSubsystem("");
		search.setOwner("");
		search.setSearchDisplayStr("");
		search.setSearchGuid(null);

		Assert.assertNotNull(search.getAccessDate());
		Assert.assertNotNull(search.getCreationDate());
		Assert.assertNotNull(search.getDeleted());
		Assert.assertNotNull(search.getDescription());
		Assert.assertNotNull(search.getDescriptionNlsid());
		Assert.assertNotNull(search.getDescriptionSubsystem());
		Assert.assertNull(search.getEmAnalyticsCategory());
		Assert.assertNull(search.getEmAnalyticsFolder());
		Assert.assertNotNull(search.getEmPluginId());
		Assert.assertNotNull(search.getId());
		Assert.assertNotNull(search.getIsLocked());

		Assert.assertNotNull(search.getIsWidget());
		Assert.assertNotNull(search.getLastModificationDate());
		Assert.assertNotNull(search.getLastModifiedBy());
		Assert.assertNotNull(search.getMetadataClob());
		Assert.assertNotNull(search.getName());
		Assert.assertNotNull(search.getNameNlsid());
		Assert.assertNotNull(search.getNameSubsystem());
		Assert.assertNotNull(search.getOwner());
		Assert.assertNotNull(search.getSearchDisplayStr());
		Assert.assertNull(search.getSearchGuid());

		EmAnalyticsSearchParam searchParamEntity = new EmAnalyticsSearchParam();
		searchParamEntity.setEmAnalyticsSearch(search);

		searchParamEntity.setSearchId(search.getId());
		searchParamEntity.setName("Test");
		searchParamEntity.setParamAttributes("test");
		searchParamEntity.setParamType(new BigDecimal(1));
		searchParamEntity.setParamValueClob("test");

		EmAnalyticsSearchParam searchParamEntity1 = new EmAnalyticsSearchParam();
		searchParamEntity1.setEmAnalyticsSearch(search);

		searchParamEntity1.setSearchId(search.getId());
		searchParamEntity1.setName("tesT");
		searchParamEntity1.setParamAttributes("test");
		searchParamEntity1.setParamType(new BigDecimal(1));
		searchParamEntity1.setParamValueClob("test");
		search.getEmAnalyticsSearchParams().add(searchParamEntity1);
		EmAnalyticsSearchParam searchParamEntity2 = new EmAnalyticsSearchParam();
		search.getEmAnalyticsSearchParams().add(searchParamEntity2);
		search.getEmAnalyticsSearchParams().add(searchParamEntity);
		search.getEmAnalyticsSearchParams().add(searchParamEntity);
		Assert.assertNotNull(searchParamEntity1.getSearchId());
		Assert.assertNotNull(searchParamEntity1.getName());
		Assert.assertNotNull(searchParamEntity1.getParamAttributes());
		Assert.assertNotNull(searchParamEntity1.getParamType());
		Assert.assertNotNull(searchParamEntity1.getParamValueClob());
		Assert.assertNull(searchParamEntity1.getParamValueStr());

		Assert.assertNotNull(searchParamEntity.equals(searchParamEntity1));
		Assert.assertNotNull(searchParamEntity.equals(searchParamEntity2));

		Assert.assertNotNull(searchParamEntity.equals(searchParamEntity));

		Assert.assertNotNull(searchParamEntity.equals(null));

	}

	@Test
	public void testLastAccessEntity() throws Exception
	{
		EmAnalyticsLastAccessPK lastPk = new EmAnalyticsLastAccessPK();
		lastPk.setAccessedBy("admin");
		lastPk.setObjectId(1);
		lastPk.setObjectType(1);
		Assert.assertNotNull(lastPk.getAccessedBy());
		Assert.assertNotNull(lastPk.getObjectId());
		Assert.assertNotNull(lastPk.getObjectType());

		EmAnalyticsSearchParamPK sPk = new EmAnalyticsSearchParamPK();
		sPk.setName("admin");
		sPk.setSearchId(1);
		Assert.assertNotNull(sPk.getName());
		Assert.assertNotNull(sPk.getSearchId());
	}

	@Test
	public void testSchemaEntity() throws Exception
	{
		EmAnalyticsSchemaVerPK obj = new EmAnalyticsSchemaVerPK();
		obj.setMajor(1);
		obj.setMinor(1);

		EmAnalyticsSchemaVerPK obj3 = new EmAnalyticsSchemaVerPK();
		obj3.setMajor(1);
		obj3.setMinor(1);
		Assert.assertNotNull(obj.getMajor());
		Assert.assertNotNull(obj.getMinor());
		Set<EmAnalyticsSchemaVerPK> obj1 = new HashSet<EmAnalyticsSchemaVerPK>();
		obj1.add(obj);
		obj1.add(obj3);
		Assert.assertNotNull(obj.equals(obj3));

		EmAnalyticsLastAccessPK p1 = new EmAnalyticsLastAccessPK();
		p1.setAccessedBy("test");
		p1.setObjectId(1);
		p1.setObjectType(1);

		EmAnalyticsLastAccessPK p2 = new EmAnalyticsLastAccessPK();
		p2.setAccessedBy("test2");
		p2.setObjectId(1);
		p2.setObjectType(1);

		EmAnalyticsLastAccessPK p3 = new EmAnalyticsLastAccessPK();
		Set<EmAnalyticsLastAccessPK> oSet = new HashSet<EmAnalyticsLastAccessPK>();
		oSet.add(p1);
		oSet.add(p2);

		Assert.assertNotNull(p1.equals(p2));
		Assert.assertNotNull(p1.equals(p3));

		EmAnalyticsCategoryParamPK catPk = new EmAnalyticsCategoryParamPK();
		catPk.setCategoryId(1);
		catPk.setName("A");

		EmAnalyticsCategoryParamPK catPk1 = new EmAnalyticsCategoryParamPK();
		catPk1.setCategoryId(1);
		catPk1.setName("B");

		EmAnalyticsCategoryParamPK catPk13 = new EmAnalyticsCategoryParamPK();
		Set<EmAnalyticsCategoryParamPK> oSet1 = new HashSet<EmAnalyticsCategoryParamPK>();
		oSet1.add(catPk);
		oSet1.add(catPk1);
		oSet1.add(catPk13);

		Assert.assertNotNull(catPk.equals(catPk1));
		Assert.assertNotNull(catPk.equals(catPk13));

		EmAnalyticsSearchParamPK sr = new EmAnalyticsSearchParamPK();
		sr.setName("A");
		sr.setSearchId(1);

		EmAnalyticsSearchParamPK sr1 = new EmAnalyticsSearchParamPK();
		sr1.setName("a");
		sr1.setSearchId(1);
		EmAnalyticsSearchParamPK sr3 = new EmAnalyticsSearchParamPK();
		Set<EmAnalyticsSearchParamPK> oSet12 = new HashSet<EmAnalyticsSearchParamPK>();
		oSet12.add(sr);
		oSet12.add(sr1);
		oSet12.add(sr3);

		Assert.assertNotNull(sr.equals(sr1));
		Assert.assertNotNull(sr.equals(sr));
		Assert.assertNotNull(sr.equals(sr3));
		Assert.assertNotNull(sr.equals(null));
	}
}

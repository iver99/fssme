/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.utils;

import java.math.BigInteger;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author chehao
 */
public class SyncSavedSearchSqlUtilTest
{
	//test insert
//	@Test(groups = { "s1" })
	public void testConcatCategoryInsert()
	{

		String sql = SyncSavedSearchSqlUtil.concatCategoryInsert(666666L, "testCategory", "testDescription", "testOwner",
				"2016-07-12 08:55:59.647387", "testNameNlsid", "testNameSubsystem", "testDescriptionNlsid",
				"testDescriptionSubsystem", "testEmPluginId", 1L, 0L, "testProviderName", "testProviderVersion",
				"testProviderDiscovery", "testProviderAssetRoot", 1L, "testDashboardIneligible", "2016-07-12 08:55:59.647387");
		String VERIFY_STRING = "INSERT INTO EMS_ANALYTICS_CATEGORY VALUES(666666,'testCategory','testDescription','testOwner',to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff'),'testNameNlsid','testNameSubsystem','testDescriptionNlsid','testDescriptionSubsystem','testEmPluginId',1,0,'testProviderName','testProviderVersion','testProviderDiscovery','testProviderAssetRoot',1,'testDashboardIneligible',to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff'))";
		Assert.assertEquals(sql, VERIFY_STRING);
	}

//	@Test(groups = { "s1" })
	public void testConcatCategoryParamInsert()
	{
		String sql = SyncSavedSearchSqlUtil.concatCategoryParamInsert(666666L, "test", "test", 1L, "2016-07-12 08:55:59.647387",
				"2016-07-12 08:55:59.647387");
		String VERIFY_STRING = "INSERT INTO EMS_ANALYTICS_CATEGORY_PARAMS VALUES(666666,'test','test',1,to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff'))";
		Assert.assertEquals(sql, VERIFY_STRING);
	}

//	@Test(groups = { "s1" })
	public void testConcatCategoryParamUpdate()
	{
		String VERITY_STRING1 = "T.PARAM_VALUE='test'";
		String VERITY_STRING2 = "T.CREATION_DATE=to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff')";
		String VERITY_STRING3 = "T.LAST_MODIFICATION_DATE=to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff')";
		String VERITY_STRING4 = "T.CATEGORY_ID=666666";
		String VERITY_STRING5 = "T.NAME='test'";
		String VERITY_STRING6 = "T.TENANT_ID=1";
		String sql = SyncSavedSearchSqlUtil.concatCategoryParamUpdate(666666L, "test", "test", 1L, "2016-07-12 08:55:59.647387",
				"2016-07-12 08:55:59.647387");
		Assert.assertTrue(sql.contains(VERITY_STRING1), VERITY_STRING1 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING2), VERITY_STRING2 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING3), VERITY_STRING3 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING4), VERITY_STRING4 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING5), VERITY_STRING5 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING6), VERITY_STRING6 + " is NOT found unexpected");
	}

	//update action
//	@Test(groups = { "s1" })
	public void testConcatCategoryUpdate()
	{
		String VERITY_STRING1 = "CATEGORY_ID=666666";
		String VERITY_STRING2 = "testCategory";
		String VERITY_STRING3 = "t.DESCRIPTION='testDescription'";
		String VERITY_STRING4 = "t.OWNER='testOwner'";
		String VERITY_STRING5 = "t.CREATION_DATE=to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff')";
		String VERITY_STRING6 = "t.NAME_NLSID='testNameNlsid'";
		String VERITY_STRING7 = "t.NAME_SUBSYSTEM='testNameSubsystem'";
		String VERITY_STRING8 = "t.DESCRIPTION_NLSID='testDescriptionNlsid'";
		String VERITY_STRING9 = "t.DESCRIPTION_SUBSYSTEM='testDescriptionSubsystem'";
		String VERITY_STRING10 = "t.EM_PLUGIN_ID='testEmPluginId'";
		String VERITY_STRING11 = "t.DEFAULT_FOLDER_ID=1";
		String VERITY_STRING12 = "t.DELETED=0";
		String VERITY_STRING13 = "t.PROVIDER_NAME='testProviderName'";
		String VERITY_STRING14 = "t.PROVIDER_VERSION='testProviderVersion'";
		String VERITY_STRING15 = "t.PROVIDER_DISCOVERY='testProviderDiscovery'";
		String VERITY_STRING16 = "t.PROVIDER_ASSET_ROOT='testProviderAssetRoot'";
		String VERITY_STRING17 = "t.TENANT_ID=1";
		String VERITY_STRING18 = "t.DASHBOARD_INELIGIBLE='testDashboardIneligible'";
		String VERITY_STRING19 = "t.LAST_MODIFICATION_DATE=to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff')";

		String sql = SyncSavedSearchSqlUtil.concatCategoryUpdate(666666L, "testCategory", "testDescription", "testOwner",
				"2016-07-12 08:55:59.647387", "testNameNlsid", "testNameSubsystem", "testDescriptionNlsid",
				"testDescriptionSubsystem", "testEmPluginId", 1L, 0L, "testProviderName", "testProviderVersion",
				"testProviderDiscovery", "testProviderAssetRoot", 1L, "testDashboardIneligible", "2016-07-12 08:55:59.647387");

		Assert.assertTrue(sql.contains(VERITY_STRING1), VERITY_STRING1 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING2), VERITY_STRING2 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING3), VERITY_STRING3 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING4), VERITY_STRING4 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING5), VERITY_STRING5 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING6), VERITY_STRING6 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING7), VERITY_STRING7 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING8), VERITY_STRING8 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING9), VERITY_STRING9 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING10), VERITY_STRING10 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING11), VERITY_STRING11 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING12), VERITY_STRING12 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING13), VERITY_STRING13 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING14), VERITY_STRING14 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING15), VERITY_STRING15 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING16), VERITY_STRING16 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING17), VERITY_STRING17 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING18), VERITY_STRING18 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING19), VERITY_STRING19 + " is NOT found unexpected");

	}

//	@Test(groups = { "s1" })
	public void testConcatFoldersInsert()
	{
		String sql = SyncSavedSearchSqlUtil.concatFolderInsert(666666L, "test", 1L, "test", "2016-07-12 08:55:59.647387", "test",
				"2016-07-12 08:55:59.647387", "test", "test", "test", "test", "test", 1, "test", 1, 0L, 1L);
		String VERIFY_STRING = "INSERT INTO EMS_ANALYTICS_FOLDERS VALUES(666666,'test',1,'test',to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff'),'test',to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff'),'test','test','test','test','test',1,'test',1,0,1)";
		Assert.assertEquals(sql, VERIFY_STRING);
	}

//	@Test(groups = { "s1" })
	public void testConcatFoldersUpdate()
	{
		String VERITY_STRING1 = "t.NAME='test'";
		String VERITY_STRING2 = "t.PARENT_ID=1";
		String VERITY_STRING3 = "t.DESCRIPTION='test'";
		String VERITY_STRING4 = "t.CREATION_DATE=to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff')";
		String VERITY_STRING5 = "t.OWNER='test'";
		String VERITY_STRING6 = "t.LAST_MODIFICATION_DATE=to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff')";
		String VERITY_STRING7 = "t.LAST_MODIFIED_BY='test'";
		String VERITY_STRING8 = "t.NAME_NLSID='test'";
		String VERITY_STRING9 = "t.NAME_SUBSYSTEM='test'";
		String VERITY_STRING10 = "t.DESCRIPTION_NLSID='test'";
		String VERITY_STRING11 = "t.DESCRIPTION_SUBSYSTEM='test'";
		String VERITY_STRING12 = "t.SYSTEM_FOLDER=1";
		String VERITY_STRING13 = "t.EM_PLUGIN_ID='test'";
		String VERITY_STRING14 = "t.UI_HIDDEN=1";
		String VERITY_STRING15 = "t.DELETED=0";
		String VERITY_STRING16 = "t.FOLDER_ID=666666";
		String VERITY_STRING17 = "t.TENANT_ID=1";
		String sql = SyncSavedSearchSqlUtil.concatFolderUpdate(666666L, "test", 1L, "test", "2016-07-12 08:55:59.647387", "test",
				"2016-07-12 08:55:59.647387", "test", "test", "test", "test", "test", 1, "test", 1, 0L, 1L);
		Assert.assertTrue(sql.contains(VERITY_STRING1), VERITY_STRING1 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING2), VERITY_STRING2 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING3), VERITY_STRING3 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING4), VERITY_STRING4 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING5), VERITY_STRING5 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING6), VERITY_STRING6 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING7), VERITY_STRING7 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING8), VERITY_STRING8 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING9), VERITY_STRING9 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING10), VERITY_STRING10 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING11), VERITY_STRING11 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING12), VERITY_STRING12 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING13), VERITY_STRING13 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING14), VERITY_STRING14 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING15), VERITY_STRING15 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING16), VERITY_STRING16 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING17), VERITY_STRING17 + " is NOT found unexpected");
	}

//	@Test(groups = { "s1" })
	public void testConcatLastAccessInsert()
	{
		String sql = SyncSavedSearchSqlUtil.concatLastAccessInsert(666666L, "test", 1L, "2016-07-12 08:55:59.647387", 1L,
				"2016-07-12 08:55:59.647387", "2016-07-12 08:55:59.647387");
		String VERIFY_STRING = "INSERT INTO EMS_ANALYTICS_LAST_ACCESS VALUES(666666,'test',1,to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff'),1,to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff'))";
		Assert.assertEquals(sql, VERIFY_STRING);
	}

//	@Test(groups = { "s1" })
	public void testConcatLastAccessUpdate()
	{
		String VERITY_STRING1 = "t.ACCESS_DATE=to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff')";
		String VERITY_STRING2 = "t.CREATION_DATE=to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff')";
		String VERITY_STRING3 = "t.LAST_MODIFICATION_DATE=to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff')";
		String VERITY_STRING4 = "t.OBJECT_ID=666666";
		String VERITY_STRING5 = "t.ACCESSED_BY='test'";
		String VERITY_STRING6 = "t.OBJECT_TYPE=1";
		String VERITY_STRING7 = "t.TENANT_ID=1";
		String sql = SyncSavedSearchSqlUtil.concatLastAccessUpdate(666666L, "test", 1L, "2016-07-12 08:55:59.647387", 1L,
				"2016-07-12 08:55:59.647387", "2016-07-12 08:55:59.647387");
		Assert.assertTrue(sql.contains(VERITY_STRING1), VERITY_STRING1 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING2), VERITY_STRING2 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING3), VERITY_STRING3 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING4), VERITY_STRING4 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING5), VERITY_STRING5 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING6), VERITY_STRING6 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING7), VERITY_STRING7 + " is NOT found unexpected");
	}

	//@Test(groups = { "s1" })
	public void testConcatSearchInsert()
	{
		String sql = SyncSavedSearchSqlUtil.concatSearchInsert(666666L, "35D93AFFF3112149E053C05DF00A2A5D", "test", "test",
				"2016-07-12 08:55:59.647387", "2016-07-12 08:55:59.647387", "test", "test", 1L, 1L, "test", "test", "test",
				"test", 1, "test", 0, "test", "test", 1, 0L, 1, 1L, "test", "test", "test", "test", "test", "test", "test",
				"test", 1L, 1L, 1L, "test", "test", "test", "test");
		String VERIFY_STRING = "INSERT INTO EMS_ANALYTICS_SEARCH VALUES(666666,'35D93AFFF3112149E053C05DF00A2A5D','test','test',to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff'),'test','test',1,1,'test','test','test','test',1,'test',0,'test','test',1,0,1,1,'test','test','test','test','test','test','test','test',1,1,1,'test','test','test','test')";
		Assert.assertEquals(sql, VERIFY_STRING);
	}

//	@Test(groups = { "s1" })
	public void testConcatSearchParamInsert()
	{
		String sql = SyncSavedSearchSqlUtil.concatSearchParamsInsert(new BigInteger(new Long(666666).toString()), "test", "test",
				1L, "test", "test", 1L, "2016-07-12 08:55:59.647387", "2016-07-12 08:55:59.647387");
		String VERIFY_STRING = "INSERT INTO EMS_ANALYTICS_SEARCH_PARAMS VALUES(666666,'test','test',1,'test','test',1,to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff'),to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff'))";
		Assert.assertEquals(sql, VERIFY_STRING);
	}

//	@Test(groups = { "s1" })
	public void testConcatSearchParamUpdate()
	{
		String VERITY_STRING1 = "t.PARAM_ATTRIBUTES='test'";
		String VERITY_STRING2 = "t.PARAM_TYPE=1";
		String VERITY_STRING3 = "t.PARAM_VALUE_STR='test'";
		String VERITY_STRING4 = "t.PARAM_VALUE_CLOB='test'";
		String VERITY_STRING5 = "t.CREATION_DATE=to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff')";
		String VERITY_STRING6 = "t.LAST_MODIFICATION_DATE=to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff')";
		String VERITY_STRING7 = "t.SEARCH_ID=666666";
		String VERITY_STRING8 = "t.NAME='test'";
		String VERITY_STRING9 = "t.TENANT_ID=1";
		String sql = SyncSavedSearchSqlUtil.concatSearchParamsUpdate(new BigInteger(new Long(666666).toString()), "test", "test",
				1L, "test", "test", 1L, "2016-07-12 08:55:59.647387", "2016-07-12 08:55:59.647387");
		Assert.assertTrue(sql.contains(VERITY_STRING1), VERITY_STRING1 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING2), VERITY_STRING2 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING3), VERITY_STRING3 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING4), VERITY_STRING4 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING5), VERITY_STRING5 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING6), VERITY_STRING6 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING7), VERITY_STRING7 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING8), VERITY_STRING8 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING9), VERITY_STRING9 + " is NOT found unexpected");

	}

//	@Test(groups = { "s1" })
	public void testConcatSearchUpdate()
	{
		String VERITY_STRING1 = "t.SEARCH_GUID='35D93AFFF3112149E053C05DF00A2A5D'";
		String VERITY_STRING2 = "t.NAME='test'";
		String VERITY_STRING3 = "t.OWNER='test'";
		String VERITY_STRING4 = "t.CREATION_DATE=to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff')";
		String VERITY_STRING5 = "t.LAST_MODIFICATION_DATE=to_timestamp('2016-07-12 08:55:59.647387','yyyy-mm-dd hh24:mi:ss.ff')";
		String VERITY_STRING6 = "t.LAST_MODIFIED_BY='test'";
		String VERITY_STRING7 = "t.DESCRIPTION='test'";
		String VERITY_STRING8 = "t.FOLDER_ID=1";
		String VERITY_STRING9 = "t.CATEGORY_ID=1";
		String VERITY_STRING10 = "t.NAME_NLSID='test'";
		String VERITY_STRING11 = "t.NAME_SUBSYSTEM='test'";
		String VERITY_STRING12 = "t.DESCRIPTION_NLSID='test'";
		String VERITY_STRING13 = "t.DESCRIPTION_SUBSYSTEM='test'";
		String VERITY_STRING14 = "t.SYSTEM_SEARCH=1";
		String VERITY_STRING15 = "t.EM_PLUGIN_ID='1'";
		String VERITY_STRING16 = "t.IS_LOCKED=0";
		String VERITY_STRING17 = "t.METADATA_CLOB='test'";
		String VERITY_STRING18 = "t.SEARCH_DISPLAY_STR='test'";
		String VERITY_STRING19 = "t.UI_HIDDEN=1";
		String VERITY_STRING20 = "t.DELETED=0";
		String VERITY_STRING21 = "t.IS_WIDGET=1";
		String VERITY_STRING22 = "t.NAME_WIDGET_SOURCE='test'";
		String VERITY_STRING23 = "t.WIDGET_GROUP_NAME='test'";
		String VERITY_STRING24 = "t.WIDGET_SCREENSHOT_HREF='test'";
		String VERITY_STRING25 = "t.WIDGET_ICON='test'";
		String VERITY_STRING26 = "t.WIDGET_KOC_NAME='test'";
		String VERITY_STRING27 = "t.WIDGET_VIEWMODEL='test'";
		String VERITY_STRING28 = "t.WIDGET_TEMPLATE='test'";
		String VERITY_STRING29 = "t.WIDGET_SUPPORT_TIME_CONTROL='test'";
		String VERITY_STRING30 = "t.WIDGET_LINKED_DASHBOARD=1";
		String VERITY_STRING31 = "t.WIDGET_DEFAULT_WIDTH=1";
		String VERITY_STRING32 = "t.WIDGET_DEFAULT_HEIGHT=1";
		String VERITY_STRING33 = "t.PROVIDER_NAME='test'";
		String VERITY_STRING34 = "t.PROVIDER_VERSION='test'";
		String VERITY_STRING35 = "t.PROVIDER_ASSET_ROOT='test'";
		String VERITY_STRING36 = "t.DASHBOARD_INELIGIBLE='test'";
		String VERITY_STRING37 = "t.SEARCH_ID=666666";
		String VERITY_STRING38 = "t.TENANT_ID=1";
		String sql = SyncSavedSearchSqlUtil.concatSearchUpdate(666666L, "35D93AFFF3112149E053C05DF00A2A5D", "test", "test",
				"2016-07-12 08:55:59.647387", "2016-07-12 08:55:59.647387", "test", "test", 1L, 1L, "test", "test", "test",
				"test", 1, "test", 0, "test", "test", 1, 0L, 1, 1L, "test", "test", "test", "test", "test", "test", "test",
				"test", 1L, 1L, 1L, "test", "test", "test", "test");
		Assert.assertTrue(sql.contains(VERITY_STRING1), VERITY_STRING1 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING2), VERITY_STRING2 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING3), VERITY_STRING3 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING4), VERITY_STRING4 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING5), VERITY_STRING5 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING6), VERITY_STRING6 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING7), VERITY_STRING7 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING8), VERITY_STRING8 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING9), VERITY_STRING9 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING10), VERITY_STRING10 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING11), VERITY_STRING11 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING12), VERITY_STRING12 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING13), VERITY_STRING13 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING14), VERITY_STRING14 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING15), VERITY_STRING15 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING16), VERITY_STRING16 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING17), VERITY_STRING17 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING18), VERITY_STRING18 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING19), VERITY_STRING19 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING20), VERITY_STRING20 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING21), VERITY_STRING21 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING22), VERITY_STRING22 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING23), VERITY_STRING23 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING24), VERITY_STRING24 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING25), VERITY_STRING25 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING26), VERITY_STRING26 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING27), VERITY_STRING27 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING28), VERITY_STRING28 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING29), VERITY_STRING29 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING30), VERITY_STRING30 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING31), VERITY_STRING31 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING32), VERITY_STRING32 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING33), VERITY_STRING33 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING34), VERITY_STRING34 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING35), VERITY_STRING35 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING36), VERITY_STRING36 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING37), VERITY_STRING37 + " is NOT found unexpected");
		Assert.assertTrue(sql.contains(VERITY_STRING38), VERITY_STRING38 + " is NOT found unexpected");
	}

}

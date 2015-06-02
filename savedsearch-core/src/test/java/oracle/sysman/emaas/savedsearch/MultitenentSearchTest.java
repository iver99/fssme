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
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class MultitenentSearchTest extends BaseTest
{

	private static final String TENANT_OPC1 = TestUtils.TENANT_ID_OPC1;
	private static final String TENANT_OPC2 = TestUtils.TENANT_ID_OPC2;
	private static final String TENANT_OPC3 = TestUtils.TENANT_ID_OPC3;

	private static Long opc1 = null;
	private static Long opc2 = null;
	private static Long opc3 = null;
	private static final String TENANT_ID1 = TestUtils.TENANT_ID1;
	private static final String TENANT_ID2 = TestUtils.TENANT_ID2;
	private static final String TENANT_ID3 = TestUtils.TENANT_ID3;

	private static String username1 = null;
	private static String username2 = null;
	private static String username3 = null;

	public static int createCategory()
	{
		int id = 0;

		try {
			CategoryManager fmger = CategoryManager.getInstance();
			Category catObj = new CategoryImpl();
			catObj.setName("TestMultitencyCat");
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

		return id;
	}

	public static int createfolder()
	{
		int id = 0;

		try {
			FolderManager fmger = FolderManager.getInstance();
			Folder fld = new FolderImpl();
			fld.setName("TestMultitencyFld");
			fld.setDescription("TestMultitency Desc");
			fld = fmger.saveFolder(fld);
			id = fld.getId();
		}
		catch (Exception e) {
			//do nothing	
		}
		return id;
	}

	public static int createSearch(Long value, String username)
	{
		int id = 0;
		TenantContext.setContext(new TenantInfo(username, value));
		try {
			SearchManager fmger = SearchManager.getInstance();
			Search search = new SearchImpl();
			search.setName("SearchMultitency");
			search.setDescription("TestMultitency Desc");
			search.setFolderId(MultitenentSearchTest.createfolder());
			search.setCategoryId(MultitenentSearchTest.createCategory());
			search = fmger.saveSearch(search);
			id = search.getId();
		}
		catch (Exception e) {
			//do nothing	
		}
		finally {
			TenantContext.clearContext();
		}
		return id;
	}

	public static boolean deleteCategory(int id)
	{
		boolean bResult = false;
		try {

			CategoryManager fmger = CategoryManager.getInstance();
			fmger.deleteCategory(id, true);
			bResult = true;
		}
		catch (Exception e) {
			bResult = false;
		}

		return bResult;
	}

	public static boolean deleteFolder(int id)
	{
		boolean bResult = false;
		try {

			FolderManager fmger = FolderManager.getInstance();
			fmger.deleteFolder(id, true);
			bResult = true;
		}
		catch (Exception e) {
			bResult = false;
		}

		return bResult;
	}

	public static boolean deleteSearch(int id, Long value, String username)
	{
		boolean bResult = false;
		boolean bResult1 = false;
		boolean bResult2 = false;
		try {
			TenantContext.setContext(new TenantInfo(username, value));
			SearchManager fmger = SearchManager.getInstance();
			Search sr = fmger.getSearch(id);
			int catid = sr.getCategoryId();
			int fldid = sr.getFolderId();
			fmger.deleteSearch(id, true);
			bResult1 = MultitenentSearchTest.deleteFolder(fldid);
			bResult2 = MultitenentSearchTest.deleteCategory(catid);
			bResult = true;
		}
		catch (Exception e) {
			bResult = false;
		}
		finally {
			TenantContext.clearContext();
		}
		return bResult && bResult1 && bResult2;
	}

	public static Search getSearch(int id, Long value, String username)
	{
		Search fld = null;
		try {
			TenantContext.setContext(new TenantInfo(username, value));
			SearchManager fmger = SearchManager.getInstance();
			fld = fmger.getSearch(id);
		}
		catch (Exception e) {
			fld = null;
		}
		finally {
			TenantContext.clearContext();
		}
		return fld;
	}

	public static void main(String args[])
	{
		MultitenentSearchTest.searchTest();
	}

	@Test
	public static void searchTest()
	{
		opc1 = TestUtils.getInternalTenantId(TENANT_OPC1);
		opc2 = TestUtils.getInternalTenantId(TENANT_OPC2);
		opc3 = TestUtils.getInternalTenantId(TENANT_OPC3);

		username1 = TestUtils.getUsername(TENANT_ID1);
		username2 = TestUtils.getUsername(TENANT_ID2);
		username3 = TestUtils.getUsername(TENANT_ID3);

		int id1 = MultitenentSearchTest.createSearch(opc1, username1);
		int id2 = MultitenentSearchTest.createSearch(opc2, username2);
		int id3 = MultitenentSearchTest.createSearch(opc3, username3);
		Assert.assertTrue(id1 > 0);
		Assert.assertTrue(id2 > 0);
		Assert.assertTrue(id3 > 0);

		Search s1 = MultitenentSearchTest.getSearch(id1, opc1, username1);
		Search s2 = MultitenentSearchTest.getSearch(id2, opc2, username2);
		Search s3 = MultitenentSearchTest.getSearch(id3, opc3, username3);

		Assert.assertTrue(s1 != null);
		Assert.assertTrue(s2 != null);
		Assert.assertTrue(s3 != null);

		Assert.assertTrue(MultitenentSearchTest.getSearch(id1, opc2, username2) == null);
		Assert.assertTrue(MultitenentSearchTest.getSearch(id1, opc3, username3) == null);

		Assert.assertTrue(MultitenentSearchTest.getSearch(id2, opc1, username1) == null);
		Assert.assertTrue(MultitenentSearchTest.getSearch(id2, opc3, username3) == null);

		Assert.assertTrue(MultitenentSearchTest.getSearch(id3, opc1, username1) == null);
		Assert.assertTrue(MultitenentSearchTest.getSearch(id3, opc2, username2) == null);

		Assert.assertTrue(MultitenentSearchTest.deleteSearch(id1, opc1, username1) == true);
		Assert.assertTrue(MultitenentSearchTest.deleteSearch(id2, opc2, username2) == true);
		Assert.assertTrue(MultitenentSearchTest.deleteSearch(id3, opc3, username3) == true);
	}

}
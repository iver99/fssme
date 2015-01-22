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
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.UpgradeManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;

/**
 * @author vinjoshi
 */
public class MultitenentSearchTest extends BaseTest
{

	private static final String TENANT_OPC1 = "TENANT_OPC_MT1";
	private static final String TENANT_OPC2 = "TENANT_OPC_MT2";
	private static final String TENANT_OPC3 = "TENANT_OPC_MT3";

	public static int createCategory()
	{
		int id = 0;

		try {
			CategoryManager fmger = CategoryManager.getInstance();
			Category catObj = new CategoryImpl();
			catObj.setName("TestMultitencyCat");
			catObj.setDescription("TestMultitency Desc");
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

	public static int createSearch(String value)
	{
		int id = 0;
		TenantContext.setContext(value);
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

	public static boolean deleteSearch(int id, String value)
	{
		boolean bResult = false;
		boolean bResult1 = false;
		boolean bResult2 = false;
		try {
			TenantContext.setContext(value);
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

	public static Search getSearch(int id, String value)
	{
		Search fld = null;
		try {
			TenantContext.setContext(value);
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

	@BeforeClass
	public static void searchTest()
	{

		MultitenentSearchTest.setup(TENANT_OPC1);
		MultitenentSearchTest.setup(TENANT_OPC2);
		MultitenentSearchTest.setup(TENANT_OPC3);
		int id1 = MultitenentSearchTest.createSearch(TENANT_OPC1);
		int id2 = MultitenentSearchTest.createSearch(TENANT_OPC2);
		int id3 = MultitenentSearchTest.createSearch(TENANT_OPC3);
		Assert.assertTrue(id1 > 0);
		Assert.assertTrue(id2 > 0);
		Assert.assertTrue(id3 > 0);

		Search s1 = MultitenentSearchTest.getSearch(id1, TENANT_OPC1);
		Search s2 = MultitenentSearchTest.getSearch(id2, TENANT_OPC2);
		Search s3 = MultitenentSearchTest.getSearch(id3, TENANT_OPC3);

		Assert.assertTrue(s1 != null);
		Assert.assertTrue(s2 != null);
		Assert.assertTrue(s3 != null);

		Assert.assertTrue(MultitenentSearchTest.getSearch(id1, TENANT_OPC2) == null);
		Assert.assertTrue(MultitenentSearchTest.getSearch(id1, TENANT_OPC3) == null);

		Assert.assertTrue(MultitenentSearchTest.getSearch(id2, TENANT_OPC1) == null);
		Assert.assertTrue(MultitenentSearchTest.getSearch(id2, TENANT_OPC3) == null);

		Assert.assertTrue(MultitenentSearchTest.getSearch(id3, TENANT_OPC1) == null);
		Assert.assertTrue(MultitenentSearchTest.getSearch(id3, TENANT_OPC2) == null);

		Assert.assertTrue(MultitenentSearchTest.deleteSearch(id1, TENANT_OPC1) == true);
		Assert.assertTrue(MultitenentSearchTest.deleteSearch(id2, TENANT_OPC2) == true);
		Assert.assertTrue(MultitenentSearchTest.deleteSearch(id3, TENANT_OPC3) == true);
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

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
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.IdGenerator;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.ZDTContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class SavedSearchDbExceptionTest extends BaseTest
{

	static String HOSTNAME;
	static String portno;
	static String serveruri;
	static String authToken;
	static BigInteger categoryId = BigInteger.ZERO;
	static BigInteger folderId = BigInteger.ZERO;
	static String catName = "";
	static BigInteger folderid1 = BigInteger.ZERO;
	static BigInteger searchId = BigInteger.ZERO;
	static String catName1 = "";

//	@BeforeClass
//	public static void initialization() throws Exception
//	{

//		try {
//			//create a folder to insert search into it
//			TenantContext.setContext(new TenantInfo(TestUtils.getUsername(QAToolUtil.getTenantDetails()
//					.get(QAToolUtil.TENANT_USER_NAME).toString()), TestUtils.getInternalTenantId(QAToolUtil.getTenantDetails()
//					.get(QAToolUtil.TENANT_NAME).toString())));
//			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
//			Folder folder = objFolder.createNewFolder();
//
//			//set the attribute for new folder
//			folder.setName("FolderTest_99");
//			folder.setDescription("testing purpose folder");
//			folderId = objFolder.saveFolder(folder).getId();
//			AssertJUnit.assertFalse(BigInteger.ZERO.equals(folderId));
//
//			CategoryManager objCategory = CategoryManagerImpl.getInstance();
//			Category cat = new CategoryImpl();
//			cat.setName("CategoryTestOne_99");
//			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
//			cat.setOwner(currentUser);
//			cat.setProviderName("ProviderNameTest");
//			cat.setProviderVersion("ProviderVersionTest");
//			cat.setProviderDiscovery("ProviderDiscoveryTest");
//			cat.setProviderAssetRoot("ProviderAssetRootTest");
//			cat.setDefaultFolderId(folderId);
//			cat = objCategory.saveCategory(cat);
//			categoryId = cat.getId();
//			AssertJUnit.assertFalse(BigInteger.ZERO.equals(categoryId));
//			SearchManager objSearch = SearchManager.getInstance();
//			Search search = objSearch.createNewSearch();
//			search.setDescription("testing purpose");
//			search.setName("Dummy Search_99");
//			search.setId(IdGenerator.getIntUUID(ZDTContext.getRequestId()));
//			search.setFolderId(folderId);
//			search.setCategoryId(categoryId);
//
//			Search s2 = objSearch.saveSearch(search);
//			AssertJUnit.assertNotNull(s2);
//			searchId = s2.getId();
//		}
//		catch (Exception e) {
//			Assert.fail(e.getLocalizedMessage());
//		}
//
//	}
//
//	@AfterClass
//	public static void testDelete() throws Exception
//	{
//
//		try {
//			SearchManager objSearch = SearchManager.getInstance();
//			Search search = objSearch.getSearch(searchId);
//			AssertJUnit.assertNotNull(search);
//			objSearch.deleteSearch(search.getId(), true);
//
//			CategoryManager objcategory = CategoryManagerImpl.getInstance();
//			objcategory.deleteCategory(search.getCategoryId(), true);
//
//			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
//			objFolder.deleteFolder(search.getFolderId(), true);
//
//		}
//		catch (Exception e) {
//			Assert.fail(e.getLocalizedMessage());
//		}
//		finally {
//			TenantContext.clearContext();
//		}
//	}
//
//	@Test
//	public void testCrudDelete() throws Exception
//	{
//		boolean bResult = false;
//
//		try {
//			CategoryManager objcategory = CategoryManagerImpl.getInstance();
//			objcategory.deleteCategory(categoryId, true);
//		}
//		catch (Exception e) {
//			bResult = false;
//		}
//		AssertJUnit.assertTrue(bResult == true);
//		bResult = false;
//		try {
//			CategoryManager objCategory = CategoryManagerImpl.getInstance();
//			Category cat = new CategoryImpl();
//			cat.setName("CategoryTestOne_99");
//			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
//			cat.setOwner(currentUser);
//			cat.setProviderName("ProviderNameTest");
//			cat.setProviderVersion("ProviderVersionTest");
//			cat.setProviderDiscovery("ProviderDiscoveryTest");
//			cat.setProviderAssetRoot("ProviderAssetRootTest");
//			cat = objCategory.saveCategory(cat);
//		}
//		catch (Exception e) {
//			bResult = true;
//		}
//		AssertJUnit.assertTrue(bResult == true);
//
//		try {
//			CategoryManager objCategory = CategoryManagerImpl.getInstance();
//			Category cat = new CategoryImpl();
//			cat.setName("CategoryTestOne_99");
//			String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
//			cat.setOwner(currentUser);
//			cat.setProviderName("ProviderNameTest");
//			cat.setProviderVersion("ProviderVersionTest");
//			cat.setProviderDiscovery("ProviderDiscoveryTest");
//			cat.setDefaultFolderId(BigInteger.ONE);
//			cat.setProviderAssetRoot("ProviderAssetRootTest");
//			cat = objCategory.saveCategory(cat);
//		}
//		catch (Exception e) {
//			bResult = true;
//		}
//		AssertJUnit.assertTrue(bResult == true);
//		bResult = false;
//
//		try {
//			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
//			objFolder.deleteFolder(folderId, true);
//		}
//		catch (Exception e) {
//			bResult = true;
//		}
//		AssertJUnit.assertTrue(bResult == true);
//		bResult = false;
//
//		try {
//			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
//			Folder folder = objFolder.createNewFolder();
//			folder.setName("FolderTest_99");
//			folder.setDescription("testing purpose folder");
//			folderId = objFolder.saveFolder(folder).getId();
//			AssertJUnit.assertFalse(BigInteger.ZERO.equals(folderId));
//		}
//		catch (Exception e) {
//			bResult = true;
//		}
//		AssertJUnit.assertTrue(bResult == true);
//		bResult = false;
//		try {
//			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
//			Folder folder = objFolder.createNewFolder();
//			folder.setName("FolderTest_99");
//			folder.setParentId(BigInteger.ONE.negate());
//			folder.setDescription("testing purpose folder");
//			folderId = objFolder.saveFolder(folder).getId();
//			AssertJUnit.assertFalse(BigInteger.ZERO.equals(folderId));
//		}
//		catch (Exception e) {
//			bResult = true;
//		}
//
//		AssertJUnit.assertTrue(bResult == true);
//		bResult = false;
//
//		try {
//
//			SearchManager objSearch = SearchManager.getInstance();
//			Search search = objSearch.createNewSearch();
//			search.setDescription("testing purpose");
//			search.setName("Dummy Search_99");
//
//			search.setFolderId(folderId);
//			search.setCategoryId(categoryId);
//
//			Search s2 = objSearch.saveSearch(search);
//			AssertJUnit.assertNotNull(s2);
//		}
//		catch (Exception e) {
//			bResult = true;
//		}
//		AssertJUnit.assertTrue(bResult == true);
//
//	}

}

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

import java.util.Arrays;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.SearchParameterDetails;
import oracle.sysman.emSDK.emaas.platform.savedsearch.common.ExecutionContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class SavedSearchCommonTest extends BaseTest
{
	private static final String TENANT_ID_OPC1 = TestUtils.TENANT_ID_OPC1;

	@BeforeClass
	public void initTenantDetails()
	{

		TenantContext.setContext(new TenantInfo(TestUtils.getUsername(TestUtils.TENANT_ID1), TestUtils
				.getInternalTenantId(TENANT_ID_OPC1)));

	}

	@AfterClass
	public void removeTenantDetails()
	{
		TenantContext.clearContext();
	}

	@Test
	public void testDeleteFolderWithCategorySearch() throws EMAnalyticsFwkException
	{

		//check foreignkey constraint violated or not
		FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
		Folder folder = new FolderImpl();
		folder.setName("FolderTest" + System.currentTimeMillis());
		folder.setDescription("TestFolderDescription");
		folder.setUiHidden(false);
		folder = objFolder.saveFolder(folder);

		CategoryManager objCategory = CategoryManagerImpl.getInstance();
		Category cat = new CategoryImpl();
		cat.setName("CategoryTest" + System.currentTimeMillis());
		String currentUser = ExecutionContext.getExecutionContext().getCurrentUser();
		cat.setOwner(currentUser);
		cat.setDefaultFolderId(folder.getParentId());
		cat.setProviderName("TestProviderName");
		cat.setProviderVersion("TestProviderVersion");
		cat.setProviderDiscovery("TestProviderDiscovery");
		cat.setProviderAssetRoot("TestProviderAssetRoot");
		cat.setDefaultFolderId(folder.getId());
		cat = objCategory.saveCategory(cat);

		SearchManager objSearch = SearchManager.getInstance();

		//create a new search inside the folder we created
		Search search = objSearch.createNewSearch();
		search.setDescription("testing purpose");
		search.setName("Dummy Search");
		search.setFolderId(folder.getId());
		search.setCategoryId(cat.getId());
		search.setIsWidget(false);
		search = objSearch.saveSearch(search);
		boolean bresult = false;
		try {
			objFolder.deleteFolder(folder.getId(), true);
		}
		catch (EMAnalyticsFwkException e) {
			bresult = true;
		}
		Assert.assertEquals(bresult, true);
		objSearch.deleteSearch(search.getId(), true);
		try {
			objFolder.deleteFolder(folder.getId(), true);

		}
		catch (EMAnalyticsFwkException e) {
			bresult = true;
		}
		Assert.assertEquals(bresult, true);
		objCategory.deleteCategory(cat.getId(), true);
		try {
			bresult = false;
			objFolder.deleteFolder(folder.getId(), true);
		}
		catch (EMAnalyticsFwkException e) {
			bresult = true;
		}
		Assert.assertEquals(bresult, false);

		try {
			objCategory.deleteCategory(cat.getId(), true);
		}
		catch (EMAnalyticsFwkException e) {
			bresult = true;
		}
		Assert.assertEquals(bresult, true);
	}

	@Test
	public void testFolderTest()
	{
		char[] charArray = new char[100];
		Arrays.fill(charArray, 'a');
		boolean bresult = false;
		String str = new String(charArray);
		try {
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			Folder folder = new FolderImpl();
			folder.setName(str);
			folder.setDescription("TestFolderDescription");
			folder.setUiHidden(false);
			folder = objFolder.saveFolder(folder);
		}
		catch (Exception e) {
			bresult = true;
		}
		Assert.assertEquals(bresult, true);
		try {
			bresult = false;
			FolderManagerImpl objFolder = FolderManagerImpl.getInstance();
			Folder folder = new FolderImpl();
			folder.setName("AAA");
			folder.setDescription("TestFolderDescription");
			folder.setUiHidden(false);
			folder = objFolder.saveFolder(folder);

			Folder folder1 = new FolderImpl();
			folder1.setName("testy21");
			folder1.setDescription("TestFolderDescription");
			folder1.setUiHidden(false);
			folder1 = objFolder.saveFolder(folder1);
			folder1 = objFolder.getFolder(folder1.getId());
			folder1.setName("AAA");
			folder1 = objFolder.updateFolder(folder1);
		}
		catch (Exception e) {
			bresult = true;
		}
		Assert.assertEquals(bresult, true);
	}

	@Test
	public void testSearchParameterDetails() throws EMAnalyticsFwkException
	{
		SearchParameterDetails t1 = new SearchParameterDetails();
		t1.setName("test");
		t1.setAttributes("test");
		t1.setType(ParameterType.CLOB);
		t1.setValue("test");
		Assert.assertNotNull(t1.getName());
		Assert.assertNotNull(t1.getType());
		Assert.assertNotNull(t1.getValue());
		Assert.assertNotNull(t1.getAttributes());

		SearchParameterDetails t2 = new SearchParameterDetails();
		t2.setName("test1");
		t2.setAttributes("test");
		t2.setType(ParameterType.CLOB);
		t2.setValue("test");
		Assert.assertFalse(t1.equals(t2));
		Assert.assertFalse(t1.equals("test1"));
		Parameter p1 = new Parameter();
		p1.setName("test1");
		Assert.assertFalse(t1.equals(p1));
		p1.setName("test");
		Assert.assertTrue(t1.equals(p1));
		Assert.assertTrue(t1.equals("test"));
	}
}

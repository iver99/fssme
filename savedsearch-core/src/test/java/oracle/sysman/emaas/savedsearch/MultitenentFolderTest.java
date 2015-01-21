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

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.UpgradeManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;

/**
 * @author vinjoshi
 */
public class MultitenentFolderTest extends BaseTest
{

	private static final String TENANT_OPC1 = "TENANT_OPC_MT1";
	private static final String TENANT_OPC2 = "TENANT_OPC_MT2";
	private static final String TENANT_OPC3 = "TENANT_OPC_MT3";

	public static int createfolder(String value)
	{
		int id = 0;
		TenantContext.setContext(value);
		try {
			FolderManager fmger = FolderManager.getInstance();
			Folder fld = new FolderImpl();
			fld.setName("TestMultitency");
			fld.setDescription("TestMultitency Desc");
			fld = fmger.saveFolder(fld);
			id = fld.getId();
		}
		catch (Exception e) {
			//do nothing	
		}
		finally {
			TenantContext.clearContext();
		}
		return id;
	}

	public static boolean deleteFolder(int id, String value)
	{
		boolean bResult = false;
		try {
			TenantContext.setContext(value);
			FolderManager fmger = FolderManager.getInstance();
			fmger.deleteFolder(id, true);
			bResult = true;
		}
		catch (Exception e) {
			bResult = false;
		}
		finally {
			TenantContext.clearContext();
		}
		return bResult;
	}

	@BeforeClass
	public static void folderTest()
	{
		MultitenentFolderTest.setup(TENANT_OPC1);
		MultitenentFolderTest.setup(TENANT_OPC2);
		MultitenentFolderTest.setup(TENANT_OPC3);

		int id1 = MultitenentFolderTest.createfolder(TENANT_OPC1);
		int id2 = MultitenentFolderTest.createfolder(TENANT_OPC2);
		int id3 = MultitenentFolderTest.createfolder(TENANT_OPC3);
		Assert.assertTrue(id1 > 0);
		Assert.assertTrue(id2 > 0);
		Assert.assertTrue(id3 > 0);

		Assert.assertTrue(MultitenentFolderTest.getFolder(id1, TENANT_OPC1) != null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id2, TENANT_OPC2) != null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id3, TENANT_OPC3) != null);

		Assert.assertTrue(MultitenentFolderTest.getFolder(id1, TENANT_OPC2) == null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id1, TENANT_OPC3) == null);

		Assert.assertTrue(MultitenentFolderTest.getFolder(id2, TENANT_OPC1) == null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id2, TENANT_OPC3) == null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id3, TENANT_OPC1) == null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id3, TENANT_OPC2) == null);

		Assert.assertTrue(MultitenentFolderTest.deleteFolder(id1, TENANT_OPC1) == true);
		Assert.assertTrue(MultitenentFolderTest.deleteFolder(id2, TENANT_OPC2) == true);
		Assert.assertTrue(MultitenentFolderTest.deleteFolder(id3, TENANT_OPC3) == true);
	}

	public static Folder getFolder(int id, String value)
	{
		Folder fld = null;
		try {
			TenantContext.setContext(value);
			FolderManager fmger = FolderManager.getInstance();
			fld = fmger.getFolder(id);
		}
		catch (Exception e) {
			fld = null;
		}
		finally {
			TenantContext.clearContext();
		}
		return fld;
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

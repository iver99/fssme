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
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class MultitenentFolderTest extends BaseTest
{

	private static final String TENANT_OPC1 = TestUtils.TENANT_ID_OPC1;
	private static final String TENANT_OPC2 = TestUtils.TENANT_ID_OPC2;
	private static final String TENANT_OPC3 = TestUtils.TENANT_ID_OPC3;

	private static Long opc1 = null;
	private static Long opc2 = null;
	private static Long opc3 = null;

	public static int createfolder(Long value)
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

	public static boolean deleteFolder(int id, Long value)
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

	@Test
	public static void folderTest()
	{
		opc1 = TestUtils.getInternalTenantId(TENANT_OPC1);
		opc2 = TestUtils.getInternalTenantId(TENANT_OPC2);
		opc3 = TestUtils.getInternalTenantId(TENANT_OPC3);

		int id1 = MultitenentFolderTest.createfolder(opc1);
		int id2 = MultitenentFolderTest.createfolder(opc2);
		int id3 = MultitenentFolderTest.createfolder(opc3);
		Assert.assertTrue(id1 > 0);
		Assert.assertTrue(id2 > 0);
		Assert.assertTrue(id3 > 0);

		Assert.assertTrue(MultitenentFolderTest.getFolder(id1, opc1) != null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id2, opc2) != null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id3, opc3) != null);

		Assert.assertTrue(MultitenentFolderTest.getFolder(id1, opc2) == null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id1, opc3) == null);

		Assert.assertTrue(MultitenentFolderTest.getFolder(id2, opc1) == null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id2, opc3) == null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id3, opc1) == null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id3, opc2) == null);

		Assert.assertTrue(MultitenentFolderTest.deleteFolder(id1, opc1) == true);
		Assert.assertTrue(MultitenentFolderTest.deleteFolder(id2, opc2) == true);
		Assert.assertTrue(MultitenentFolderTest.deleteFolder(id3, opc3) == true);
	}

	public static Folder getFolder(int id, Long value)
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

}

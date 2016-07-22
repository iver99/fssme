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

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.Assert;

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

	private static String username1 = null;
	private static String username2 = null;
	private static String username3 = null;

	private static final String TENANT_ID1 = TestUtils.TENANT_ID1;
	private static final String TENANT_ID2 = TestUtils.TENANT_ID2;
	private static final String TENANT_ID3 = TestUtils.TENANT_ID3;

	public static BigInteger createfolder(Long value, String username)
	{
		BigInteger id = BigInteger.ZERO;
		TenantContext.setContext(new TenantInfo(username, value));
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

	public static boolean deleteFolder(BigInteger id, Long value, String username)
	{
		boolean bResult = false;
		try {
			TenantContext.setContext(new TenantInfo(username, value));
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

	//	@Test
	public static void folderTest()
	{
		opc1 = TestUtils.getInternalTenantId(TENANT_OPC1);
		opc2 = TestUtils.getInternalTenantId(TENANT_OPC2);
		opc3 = TestUtils.getInternalTenantId(TENANT_OPC3);

		username1 = TestUtils.getUsername(TENANT_ID1);
		username2 = TestUtils.getUsername(TENANT_ID2);
		username3 = TestUtils.getUsername(TENANT_ID3);

		BigInteger id1 = MultitenentFolderTest.createfolder(opc1, username1);
		BigInteger id2 = MultitenentFolderTest.createfolder(opc2, username2);
		BigInteger id3 = MultitenentFolderTest.createfolder(opc3, username3);
		Assert.assertTrue(BigInteger.ZERO.compareTo(id1) == -1);
		Assert.assertTrue(BigInteger.ZERO.compareTo(id2) == -1);
		Assert.assertTrue(BigInteger.ZERO.compareTo(id3) == -1);

		Assert.assertTrue(MultitenentFolderTest.getFolder(id1, opc1, username1) != null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id2, opc2, username2) != null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id3, opc3, username3) != null);

		Assert.assertTrue(MultitenentFolderTest.getFolder(id1, opc2, username2) == null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id1, opc3, username3) == null);

		Assert.assertTrue(MultitenentFolderTest.getFolder(id2, opc1, username1) == null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id2, opc3, username3) == null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id3, opc1, username1) == null);
		Assert.assertTrue(MultitenentFolderTest.getFolder(id3, opc2, username2) == null);

		Assert.assertTrue(MultitenentFolderTest.deleteFolder(id1, opc1, username1) == true);
		Assert.assertTrue(MultitenentFolderTest.deleteFolder(id2, opc2, username2) == true);
		Assert.assertTrue(MultitenentFolderTest.deleteFolder(id3, opc3, username3) == true);
	}

	public static Folder getFolder(BigInteger id, Long value, String username)
	{
		Folder fld = null;
		try {
			TenantContext.setContext(new TenantInfo(username, value));
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

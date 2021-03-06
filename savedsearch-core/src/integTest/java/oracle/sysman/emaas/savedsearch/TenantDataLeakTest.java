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

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategoryManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Folder;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;

import org.testng.AssertJUnit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class TenantDataLeakTest extends BaseTest
{
	private static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException sqlEx) {
				// donothing
			}
		}
	}

	private static Connection createConnection() throws ClassNotFoundException, SQLException
	{
		Properties pr = QAToolUtil.getDbProperties();
		Class.forName(pr.getProperty(QAToolUtil.JDBC_PARAM_DRIVER));
		String url = pr.getProperty(QAToolUtil.JDBC_PARAM_URL);
		String user = pr.getProperty(QAToolUtil.JDBC_PARAM_USER);
		String password = pr.getProperty(QAToolUtil.JDBC_PARAM_PASSWORD);
		return DriverManager.getConnection(url, user, password);
	}

	@BeforeClass
	public void beforeClass() throws IOException
	{
//		RegistrationManager.getInstance().initComponent();	// comment out temporary since EMCPSSF-397
	}

	@Test 
	public void tenantLeakTest() throws SQLException, ClassNotFoundException, EMAnalyticsFwkException {
		BigInteger id = new BigInteger("6666");
		Connection conn = null;
		try {

			conn = TenantDataLeakTest.createConnection();
			generateSeedData(conn, 777);
			generateSeedData(conn, 888);
			generateSeedData(conn, 999);

			AssertJUnit.assertTrue(deleteSearch(id, 777, true));
			Search search = getSearch(id, 777);
			AssertJUnit.assertNull(search);
			search = getSearch(id, 888);
			AssertJUnit.assertNotNull(search);
			search = getSearch(id, 999);
			AssertJUnit.assertNotNull(search);

			AssertJUnit.assertTrue(deleteSearch(id, 888, true));
			search = getSearch(id, 888);
			AssertJUnit.assertNull(search);
			search = getSearch(id, 777);
			AssertJUnit.assertNull(search);
			search = getSearch(id, 999);
			AssertJUnit.assertNotNull(search);

			AssertJUnit.assertTrue(deleteSearch(id, 999, true));
			search = getSearch(id, 888);
			AssertJUnit.assertNull(search);
			search = getSearch(id, 777);
			AssertJUnit.assertNull(search);
			search = getSearch(id, 999);
			AssertJUnit.assertNull(search);

			AssertJUnit.assertTrue(deleteCategory(id, 777, true));
			Category category = getCategory(id, 777);
			AssertJUnit.assertNull(category);
			category = getCategory(id, 888);
			AssertJUnit.assertNotNull(category);
			category = getCategory(id, 999);
			AssertJUnit.assertNotNull(category);

			AssertJUnit.assertTrue(deleteCategory(id, 888, true));
			category = getCategory(id, 888);
			AssertJUnit.assertNull(category);
			category = getCategory(id, 777);
			AssertJUnit.assertNull(category);
			category = getCategory(id, 999);
			AssertJUnit.assertNotNull(category);

			AssertJUnit.assertTrue(deleteCategory(id, 999, true));
			category = getCategory(id, 888);
			AssertJUnit.assertNull(category);
			category = getCategory(id, 777);
			AssertJUnit.assertNull(category);
			category = getCategory(id, 999);
			AssertJUnit.assertNull(category);

			AssertJUnit.assertTrue(deleteFolder(id, 777, true));
			Folder folder = getFolder(id, 777);
			AssertJUnit.assertNull(folder);
			folder = getFolder(id, 888);
			AssertJUnit.assertNotNull(folder);
			folder = getFolder(id, 999);
			AssertJUnit.assertNotNull(folder);

			AssertJUnit.assertTrue(deleteFolder(id, 888, true));
			folder = getFolder(id, 888);
			AssertJUnit.assertNull(folder);
			folder = getFolder(id, 777);
			AssertJUnit.assertNull(folder);
			folder = getFolder(id, 999);
			AssertJUnit.assertNotNull(folder);

			AssertJUnit.assertTrue(deleteFolder(id, 999, true));
			folder = getFolder(id, 888);
			AssertJUnit.assertNull(folder);
			folder = getFolder(id, 777);
			AssertJUnit.assertNull(folder);
			folder = getFolder(id, 999);
			AssertJUnit.assertNull(folder);
			AssertJUnit.assertTrue(deleteFolder(BigInteger.ONE, 777, true));
			AssertJUnit.assertTrue(deleteFolder(BigInteger.ONE, 888, true));
			AssertJUnit.assertTrue(deleteFolder(BigInteger.ONE, 999, true));

			deleteSeedData(conn, 777);
			deleteSeedData(conn, 888);
			deleteSeedData(conn, 999);
		}
		finally {
			if(conn != null)
				TenantDataLeakTest.closeConnection(conn);
		}
	}

	@Test
	public void tenantLeakTest_softdelete() throws SQLException, ClassNotFoundException, EMAnalyticsFwkException {
		BigInteger id = new BigInteger("6666");
		Connection conn = null;
		try {

			conn = TenantDataLeakTest.createConnection();
			generateSeedData(conn, 777);
			generateSeedData(conn, 888);
			generateSeedData(conn, 999);

			AssertJUnit.assertTrue(deleteSearch(id, 777, false));
			Search search = getSearch(id, 777);
			AssertJUnit.assertNull(search);
			search = getSearch(id, 888);
			AssertJUnit.assertNotNull(search);
			search = getSearch(id, 999);
			AssertJUnit.assertNotNull(search);

			AssertJUnit.assertTrue(deleteSearch(id, 888, false));
			search = getSearch(id, 888);
			AssertJUnit.assertNull(search);
			search = getSearch(id, 777);
			AssertJUnit.assertNull(search);
			search = getSearch(id, 999);
			AssertJUnit.assertNotNull(search);

			AssertJUnit.assertTrue(deleteSearch(id, 999, false));
			search = getSearch(id, 888);
			AssertJUnit.assertNull(search);
			search = getSearch(id, 777);
			AssertJUnit.assertNull(search);
			search = getSearch(id, 999);
			AssertJUnit.assertNull(search);

			AssertJUnit.assertTrue(deleteCategory(id, 777, false));
			Category category = getCategory(id, 777);
			AssertJUnit.assertNull(category);
			category = getCategory(id, 888);
			AssertJUnit.assertNotNull(category);
			category = getCategory(id, 999);
			AssertJUnit.assertNotNull(category);

			AssertJUnit.assertTrue(deleteCategory(id, 888, false));
			category = getCategory(id, 888);
			AssertJUnit.assertNull(category);
			category = getCategory(id, 777);
			AssertJUnit.assertNull(category);
			category = getCategory(id, 999);
			AssertJUnit.assertNotNull(category);

			AssertJUnit.assertTrue(deleteCategory(id, 999, false));
			category = getCategory(id, 888);
			AssertJUnit.assertNull(category);
			category = getCategory(id, 777);
			AssertJUnit.assertNull(category);
			category = getCategory(id, 999);
			AssertJUnit.assertNull(category);

			AssertJUnit.assertTrue(deleteFolder(id, 777, false));
			Folder folder = getFolder(id, 777);
			AssertJUnit.assertNull(folder);
			folder = getFolder(id, 888);
			AssertJUnit.assertNotNull(folder);
			folder = getFolder(id, 999);
			AssertJUnit.assertNotNull(folder);

			AssertJUnit.assertTrue(deleteFolder(id, 888, false));
			folder = getFolder(id, 888);
			AssertJUnit.assertNull(folder);
			folder = getFolder(id, 777);
			AssertJUnit.assertNull(folder);
			folder = getFolder(id, 999);
			AssertJUnit.assertNotNull(folder);

			AssertJUnit.assertTrue(deleteFolder(id, 999, false));
			folder = getFolder(id, 888);
			AssertJUnit.assertNull(folder);
			folder = getFolder(id, 777);
			AssertJUnit.assertNull(folder);
			folder = getFolder(id, 999);
			AssertJUnit.assertNull(folder);
			AssertJUnit.assertTrue(deleteFolder(BigInteger.ONE, 777, false));
			AssertJUnit.assertTrue(deleteFolder(BigInteger.ONE, 888, false));
			AssertJUnit.assertTrue(deleteFolder(BigInteger.ONE, 999, false));

			deleteSeedData(conn, 777);
			deleteSeedData(conn, 888);
			deleteSeedData(conn, 999);

		}
		finally {
			if(conn != null)
				TenantDataLeakTest.closeConnection(conn);
		}
	}

	private boolean deleteCategory(BigInteger id, long tenantid, boolean permanently) throws EMAnalyticsFwkException {
		boolean bResult = false;
		TenantContext.setContext(new TenantInfo("TESTING", tenantid));
		try {
			CategoryManager objSearch = CategoryManagerImpl.getInstance();
			Category category;
			category = objSearch.getCategory(id);
			AssertJUnit.assertNotNull(category);
			objSearch.deleteCategory(category.getId(), permanently);
			bResult = true;
		}
		finally {
			TenantContext.clearContext();
		}
		return bResult;
	}

	private boolean deleteFolder(BigInteger id, long tenantid, boolean permanently) throws EMAnalyticsFwkException {
		boolean bResult = false;
		TenantContext.setContext(new TenantInfo("TESTING", tenantid));
		try {
			FolderManager objFolder = FolderManagerImpl.getInstance();
			Folder folder;
			folder = objFolder.getFolder(id);
			AssertJUnit.assertNotNull(folder);
			objFolder.deleteFolder(folder.getId(), permanently);
			bResult = true;
		}
		finally {
			TenantContext.clearContext();
		}
		return bResult;
	}

	private boolean deleteSearch(BigInteger id, long tenantid, boolean permanently) throws EMAnalyticsFwkException {
		boolean bResult = false;
		TenantContext.setContext(new TenantInfo("TESTING", tenantid));
		try {
			SearchManager objSearch = SearchManagerImpl.getInstance();
			Search search;
			search = objSearch.getSearch(id);
			AssertJUnit.assertNotNull(search);
			objSearch.deleteSearch(search.getId(), permanently);
			bResult = true;
		}
		finally {
			TenantContext.clearContext();
		}
		return bResult;
	}

	private void deleteSeedData(Connection conn, long id) throws SQLException {
			conn.setAutoCommit(false);
			Statement st = conn.createStatement();
			st.execute(TenantLeakData.getDeleteSql(id));
			conn.commit();
	}

	private void generateSeedData(Connection conn, long id) throws SQLException
	{
		conn.setAutoCommit(false);
		Statement st = conn.createStatement();
		st.execute(TenantLeakData.getSql(id));
		conn.commit();

	}

	private Category getCategory(BigInteger id, long tenantid) throws EMAnalyticsFwkException {
		Category category = null;
		TenantContext.setContext(new TenantInfo("TESTING", tenantid));
		try {
			CategoryManager objSearch = CategoryManagerImpl.getInstance();
			category = objSearch.getCategory(id);
		}
		catch (Exception e) {
			category = null;
		}
		finally {
			TenantContext.clearContext();
		}
		return category;

	}

	private Folder getFolder(BigInteger id, long tenantid) throws EMAnalyticsFwkException {
		Folder folder = null;
		TenantContext.setContext(new TenantInfo("TESTING", tenantid));
		try {
			FolderManager objSearch = FolderManagerImpl.getInstance();

			folder = objSearch.getFolder(id);

		}
		catch (Exception e) {
			folder = null;
		}
		finally {
			TenantContext.clearContext();
		}
		return folder;

	}

	private Search getSearch(BigInteger id, long tenantid) throws EMAnalyticsFwkException {
		Search search = null;
		TenantContext.setContext(new TenantInfo("TESTING", tenantid));
		try {
			SearchManager objSearch = SearchManagerImpl.getInstance();
			search = objSearch.getSearch(id);
		}
		catch (Exception e) {
			search = null;
		}
		finally {
			TenantContext.clearContext();
		}
		return search;

	}

}

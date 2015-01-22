/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.io.IOException;
import java.io.InputStream;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.FileUtils;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SchemaVersion;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.UpgradeManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.VersionManager;

import org.apache.log4j.Logger;

/**
 * @author vinjoshi
 */
public class UpgradeManagerImpl extends UpgradeManager
{
	private static final Logger _logger = Logger.getLogger(UpgradeManagerImpl.class);
	public static final int SSF_CODE_VERSION_MAJOR = 0;
	public static final int SSF_CODE_VERSION_MINOR = 5;

	private static final UpgradeManagerImpl _instance = new UpgradeManagerImpl();

	private static final String SEED_DATA[] = { "emaas_savesearch_seed_data.sql", "emaas_savesearch_seed_data_la.sql",
			"emaas_savesearch_seed_data_ta.sql", "emaas_savesearch_seed_data_apm.sql" };

	private static final String UPGRADE_SCHEMA = "upgrade_schema.sql";

	/*
	 * Get UpgradeManagerImpl singleton instance.
	 * 
	 * @return Instance of UpgradeManagerImpl
	 */
	public static UpgradeManagerImpl getInstance()
	{
		return _instance;
	};

	@Override
	public boolean upgradeData() throws EMAnalyticsFwkException
	{
		boolean bResult = false;

		String data = "";
		InputStream is = null;

		try {
			SchemaVersion schemaVer = VersionManager.getInstance().getSchemaVersion();
			if (SSF_CODE_VERSION_MAJOR == schemaVer.getMajorVersion() && SSF_CODE_VERSION_MINOR == schemaVer.getMinorVersion()) {
				is = UpgradeManagerImpl.class.getClassLoader().getResourceAsStream(UPGRADE_SCHEMA);
				data = data + FileUtils.readFile(is);
				data = data + System.getProperty("line.separator");
				bResult = updateData(data);
			}
			else {

				if (FolderManagerImpl.getInstance().getRootFolder() == null) {

					for (String temp : SEED_DATA) {
						is = ClassLoader.getSystemResourceAsStream(temp);
						if (is == null) {
							is = UpgradeManagerImpl.class.getClassLoader().getResourceAsStream(temp);
						}
						data = data + FileUtils.readFile(is);
						data = data + System.getProperty("line.separator");
					}
					bResult = updateData("BEGIN " + data + "END;");
				}
				else {
					bResult = true;
				}
			}

		}
		catch (Exception e) {
			_logger.error("Upgrade error ", e);
			throw new EMAnalyticsFwkException("An error occurred while upgrading  data",
					EMAnalyticsFwkException.ERR_UPGRADE_DATA, null);
		}
		return bResult;
	}

	private boolean updateData(String data) throws IOException
	{
		EntityManager em = null;
		boolean bResult = false;

		String tenant = TenantContext.getContext();
		try {
			em = PersistenceManager.getInstance().getEntityManager(tenant);
			em.getTransaction().begin();
			String beginstr = "DECLARE " + System.getProperty("line.separator") + " l_tenantid VARCHAR2 (32) :=" + "'" + tenant
					+ "';" + System.getProperty("line.separator");

			Query qry = em.createNativeQuery(beginstr + System.getProperty("line.separator") + data);
			qry.executeUpdate();
			em.getTransaction().commit();
			bResult = true;
		}
		catch (Exception e) {
			_logger.error("Upgrade schema error", e);
			e.printStackTrace();
		}
		finally {
			if (em != null) {
				em.close();
			}
		}
		return bResult;
	}

}

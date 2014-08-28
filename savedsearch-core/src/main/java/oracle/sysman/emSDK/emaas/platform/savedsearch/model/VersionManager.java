/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emaas.platform.savedsearch.entity.EmAnalyticsSchemaVer;

import org.apache.log4j.Logger;

/**
 * @author miao
 */
public class VersionManager
{
	private static final Logger _logger = Logger.getLogger(VersionManager.class);

	private static final VersionManager verMgr = new VersionManager();

	public static VersionManager getInstance()
	{
		return verMgr;
	}

	private SchemaVersion schemaVersion;

	private VersionManager()
	{

	}

	public SchemaVersion getSchemaVersion()
	{
		if (schemaVersion == null) {
			_logger.info("Retrieving schema version... ");
			EntityManager em = null;
			try {
				EntityManagerFactory emf = PersistenceManager.getInstance().getEntityManagerFactory();
				em = emf.createEntityManager();
				List<EmAnalyticsSchemaVer> vers = em.createNamedQuery("EmAnalyticsSchemaVer.findAll").getResultList();
				if (vers != null && vers.size() == 1) {
					EmAnalyticsSchemaVer ver = vers.get(0);
					schemaVersion = new SchemaVersion(ver.getId().getMajor(), ver.getId().getMinor());
				}
				else {
					final String errMsg = "Invalid Schema Version are detected: " + vers;
					_logger.error(errMsg);
					throw new RuntimeException(errMsg);
				}
			}
			finally {
				if (em != null) {
					em.close();
				}
			}

		}
		return schemaVersion;
	}
}
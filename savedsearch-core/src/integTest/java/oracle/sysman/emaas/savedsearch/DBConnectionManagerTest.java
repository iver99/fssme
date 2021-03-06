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

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.DBConnectionManager;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class DBConnectionManagerTest extends BaseTest
{

	@Test 
	public void testDbConnection() throws Exception
	{

		Assert.assertEquals(DBConnectionManager.getInstance().isDatabaseConnectionAvailable(), true, "Connection not available");
	}

}

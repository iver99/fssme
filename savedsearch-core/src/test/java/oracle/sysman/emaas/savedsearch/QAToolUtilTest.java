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

/**
 * @author vinjoshi
 *
 */

import java.util.Properties;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.QAToolUtil;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author vinjoshi
 */
public class QAToolUtilTest
{

	@Test
	public void testDbProperties() throws Exception
	{
		Properties props = QAToolUtil.getDbProperties();
		Assert.assertTrue(props.size() == 4);
	}

	@Test
	public void testSavedSearchDeploymentDet() throws Exception
	{
		String url = QAToolUtil.getSavedSearchDeploymentDet();
		Assert.assertTrue(url != null && url.trim().length() > 0);
	}

	@Test
	public void testTenantDetails() throws Exception
	{
		Properties props = QAToolUtil.getTenantDetails();
		Assert.assertTrue(props.size() == 2);
	}

}

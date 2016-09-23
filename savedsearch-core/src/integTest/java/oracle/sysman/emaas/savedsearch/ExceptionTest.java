/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.savedsearch;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * @author shangwan
 */
public class ExceptionTest
{
	@Test (groups = {"s1"})
	public void testEMAnalyticsFwkJsonException()
	{
		EMAnalyticsFwkException emjsonexception = new EMAnalyticsFwkException("JSON Exception", 70040, null, new Exception());
		AssertJUnit.assertEquals(500, emjsonexception.getStatusCode());
	}

	@Test (groups = {"s1"})
	public void testConstructorEMAnalyticsFwkException()
	{
		//EMAnalyticsFwkException emexception = new EMAnalyticsFwkException(30022, new Exception());

		 new EMAnalyticsFwkException(new Exception());

	}

	@Test (groups = {"s1"})
	public void testStatusCodeEMAnalyticsFwkException()

	{
		EMAnalyticsFwkException emexception = new EMAnalyticsFwkException(30020, new Exception());
		AssertJUnit.assertEquals(500, emexception.getStatusCode());
		emexception = new EMAnalyticsFwkException(30050, new Exception());
		AssertJUnit.assertEquals(500, emexception.getStatusCode());
		emexception = new EMAnalyticsFwkException(20020, new Exception());
		AssertJUnit.assertEquals(500, emexception.getStatusCode());
		emexception = new EMAnalyticsFwkException(20050, new Exception());
		AssertJUnit.assertEquals(500, emexception.getStatusCode());
		emexception = new EMAnalyticsFwkException(40060, new Exception());
		AssertJUnit.assertEquals(500, emexception.getStatusCode());
		emexception = new EMAnalyticsFwkException(40055, new Exception());
		AssertJUnit.assertEquals(500, emexception.getStatusCode());
		emexception = new EMAnalyticsFwkException(40070, new Exception());
		AssertJUnit.assertEquals(500, emexception.getStatusCode());
		emexception = new EMAnalyticsFwkException(40053, new Exception());
		AssertJUnit.assertEquals(404, emexception.getStatusCode());
		emexception = new EMAnalyticsFwkException(40056, new Exception());
		AssertJUnit.assertEquals(400, emexception.getStatusCode());
		emexception = new EMAnalyticsFwkException(40057, new Exception());
		AssertJUnit.assertEquals(404, emexception.getStatusCode());
		emexception = new EMAnalyticsFwkException(10000, new Exception());
		AssertJUnit.assertEquals(500, emexception.getStatusCode());
		emexception = new EMAnalyticsFwkException(10001, new Exception());
		AssertJUnit.assertEquals(500, emexception.getStatusCode());
		emexception = new EMAnalyticsFwkException(10002, new Exception());
		AssertJUnit.assertEquals(500, emexception.getStatusCode());
		emexception = new EMAnalyticsFwkException(10003, new Exception());
		AssertJUnit.assertEquals(500, emexception.getStatusCode());
	}

}

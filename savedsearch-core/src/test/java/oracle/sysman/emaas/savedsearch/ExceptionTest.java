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
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkJsonException;

import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * @author shangwan
 */
public class ExceptionTest
{
	@Test
	public void test_EMAnalyticsFwkJsonException()
	{
		EMAnalyticsFwkJsonException emjsonexception = new EMAnalyticsFwkJsonException("JSON Exception", 70040, new Exception());
		AssertJUnit.assertEquals(500, emjsonexception.getStatusCode());
	}

	@Test
	public void testConstructor_EMAnalyticsFwkException()
	{
		//EMAnalyticsFwkException emexception = new EMAnalyticsFwkException(30022, new Exception());

		EMAnalyticsFwkException emexception = new EMAnalyticsFwkException(new Exception());

	}

	@Test
	public void testStatusCode_EMAnalyticsFwkException()

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
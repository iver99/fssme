/*
 * Copyright (C) 2014 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.savedsearch.ut;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.JSONUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;

import oracle.sysman.emaas.savedsearch.BaseTest;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jettison.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JSONUtilTest extends BaseTest
{
	@Test (groups = {"s1"})
	public void testObjectToJSONObject() throws EMAnalyticsFwkException, JSONException {
		SearchImpl search = new SearchImpl();
		search.setId(new BigInteger("58799942836057747713005328191260486291"));
		search.setCategoryId(new BigInteger("999"));
		search.setFolderId(new BigInteger("999"));
		search.setName("Search for UT");
		search.setQueryStr("*");
		long time = 1406040533048L;
		Date d = new Date(time);
		search.setCreationDate(d);

		List<SearchParameter> params = new ArrayList<SearchParameter>();
		SearchParameter p1 = new SearchParameter();
		p1.setName("Param1");
		p1.setType(ParameterType.STRING);
		p1.setValue("value1");
		params.add(p1);
		search.setParameters(params);
			String output = JSONUtil.objectToJSONString(search, new String[] { "id", "queryStr", "parameters" });
			final String verifyString1 = "\"id\":58799942836057747713005328191260486291";
			final String verifyString2 = "\"queryStr\":\"*\"";
			final String verifyString3 = "\"parameters\":[{\"name\":\"Param1\",\"value\":\"value1\",\"type\":\"STRING\"}]";
			final String verifyString4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
			Assert.assertNotNull(output);
			Assert.assertFalse(output.contains(verifyString1), verifyString1 + " is found unexpected");
			Assert.assertFalse(output.contains(verifyString2), verifyString2 + " is found unexpected");
			Assert.assertFalse(output.contains(verifyString3), verifyString3 + " is found unexpected");
			Assert.assertTrue(output.contains(verifyString4), verifyString4 + " is NOT found as expected");

			String output2 = JSONUtil.objectToJSONString(search, null);
			//			System.out.println(output2);
			Assert.assertNotNull(output2);
			Assert.assertTrue(output2.contains(verifyString1), verifyString1 + " is NOT found as expected");
			Assert.assertTrue(output2.contains(verifyString2), verifyString2 + " is NOT found as expected");
			Assert.assertTrue(output2.contains(verifyString3), verifyString3 + " is NOT found as expected");
			Assert.assertTrue(output2.contains(verifyString4), verifyString4 + " is NOT found as sexpected");

			String output3 = JSONUtil.objectToJSONString(search);
			Assert.assertNotNull(output3);
			Assert.assertTrue(output3.contains(verifyString1), verifyString1 + " is NOT found as expected");
			Assert.assertTrue(output3.contains(verifyString2), verifyString2 + " is NOT found as expected");
			Assert.assertTrue(output3.contains(verifyString3), verifyString3 + " is NOT found as expected");
			Assert.assertTrue(output3.contains(verifyString4), verifyString4 + " is NOT found as sexpected");

			JsonNode jsonObject = JSONUtil.objectToJSONObject(search, new String[] { "id", "queryStr", "parameters" });
			String output4 = jsonObject.toString();
			Assert.assertNotNull(jsonObject);
			Assert.assertFalse(output4.contains(verifyString1), verifyString1 + " is NOT found as expected");
			Assert.assertFalse(output4.contains(verifyString2), verifyString2 + " is NOT found as expected");
			Assert.assertFalse(output4.contains(verifyString3), verifyString3 + " is NOT found as expected");
			Assert.assertTrue(output4.contains(verifyString4), verifyString4 + " is NOT found as sexpected");

			JsonNode jsonObject1 = JSONUtil.objectToJSONObject(search);
			String output5 = jsonObject1.toString();
			Assert.assertNotNull(jsonObject1);
			Assert.assertTrue(output5.contains(verifyString1), verifyString1 + " is NOT found as expected");
			Assert.assertTrue(output5.contains(verifyString2), verifyString2 + " is NOT found as expected");
			Assert.assertTrue(output5.contains(verifyString3), verifyString3 + " is NOT found as expected");
			Assert.assertTrue(output5.contains(verifyString4), verifyString4 + " is NOT found as sexpected");
	}

	@Test(groups = {"s1"})
	public void testIsEmpty() throws Exception {
		Assert.assertTrue(JSONUtil.isEmpty(""));
		Assert.assertTrue(JSONUtil.isEmpty(null));
		Assert.assertFalse(JSONUtil.isEmpty("str"));
	}
}

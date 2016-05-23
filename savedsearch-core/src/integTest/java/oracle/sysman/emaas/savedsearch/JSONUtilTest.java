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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.JSONUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JSONUtilTest extends BaseTest
{
	@Test (groups = {"s1"})
	public void testObjectToJSONObject()
	{
		SearchImpl search = new SearchImpl();
		search.setId(10000);
		search.setCategoryId(999);
		search.setFolderId(999);
		search.setName("Search for UT");
		search.setQueryStr("*");
		long time = 1406040533048L;
		Date d = new Date(time);
		search.setCreatedOn(d);

		List<SearchParameter> params = new ArrayList<SearchParameter>();
		SearchParameter p1 = new SearchParameter();
		p1.setName("Param1");
		p1.setType(ParameterType.STRING);
		p1.setValue("value1");
		params.add(p1);
		search.setParameters(params);
		try {
			String output = JSONUtil.ObjectToJSONString(search, new String[] { "id", "queryStr", "parameters" });
			final String VERIFY_STRING1 = "\"id\":10000";
			final String VERIFY_STRING2 = "\"queryStr\":\"*\"";
			final String VERIFY_STRING3 = "\"parameters\":[{\"name\":\"Param1\",\"value\":\"value1\",\"type\":\"STRING\"}]";
			final String VERIFY_STRING4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
			//System.out.println(output);
			Assert.assertNotNull(output);
			Assert.assertFalse(output.contains(VERIFY_STRING1), VERIFY_STRING1 + " is found unexpected");
			Assert.assertFalse(output.contains(VERIFY_STRING2), VERIFY_STRING2 + " is found unexpected");
			Assert.assertFalse(output.contains(VERIFY_STRING3), VERIFY_STRING3 + " is found unexpected");
			Assert.assertTrue(output.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as expected");

			String output2 = JSONUtil.ObjectToJSONString(search, null);
			//			System.out.println(output2);
			Assert.assertNotNull(output2);
			Assert.assertTrue(output2.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found as expected");
			Assert.assertTrue(output2.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
			Assert.assertTrue(output2.contains(VERIFY_STRING3), VERIFY_STRING3 + " is NOT found as expected");
			Assert.assertTrue(output2.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as sexpected");

			String output3 = JSONUtil.ObjectToJSONString(search);
			Assert.assertNotNull(output3);
			Assert.assertTrue(output3.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found as expected");
			Assert.assertTrue(output3.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
			Assert.assertTrue(output3.contains(VERIFY_STRING3), VERIFY_STRING3 + " is NOT found as expected");
			Assert.assertTrue(output3.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as sexpected");

			JSONObject json_output = JSONUtil.ObjectToJSONObject(search, new String[] { "id", "queryStr", "parameters" });
			String output4 = json_output.toString();
			Assert.assertNotNull(json_output);
			Assert.assertFalse(output4.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found as expected");
			Assert.assertFalse(output4.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
			Assert.assertFalse(output4.contains(VERIFY_STRING3), VERIFY_STRING3 + " is NOT found as expected");
			Assert.assertTrue(output4.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as sexpected");

			JSONObject json_output2 = JSONUtil.ObjectToJSONObject(search);
			String output5 = json_output2.toString();
			Assert.assertNotNull(json_output2);
			Assert.assertTrue(output5.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found as expected");
			Assert.assertTrue(output5.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
			Assert.assertTrue(output5.contains(VERIFY_STRING3), VERIFY_STRING3 + " is NOT found as expected");
			Assert.assertTrue(output5.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as sexpected");

		}
		catch (EMAnalyticsFwkException e) {
			Assert.assertTrue(false, "failed to convert due to: " + e.getMessage());
		}
		catch (JSONException e) {
			Assert.assertTrue(false, "failed to convert due to: " + e.getMessage());
		}
	}

	@Test(groups = {"s1"})
	public void testIsEmpty() throws Exception {
		Assert.assertTrue(JSONUtil.isEmpty(""));
		Assert.assertTrue(JSONUtil.isEmpty(null));
		Assert.assertFalse(JSONUtil.isEmpty("str"));
	}
}

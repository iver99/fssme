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

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkJsonException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class EntityJsonUtilTest extends BaseTest
{
	private static SearchImpl search = new SearchImpl();
	private static FolderImpl folder = new FolderImpl();
	private static CategoryImpl category = new CategoryImpl();
	private static URI uri = null;
	static {
		try {
			uri = new URL("http://slc04pxi.us.oracle.com:7001/savedsearch/v1/").toURI();
		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long time = 1406040533048L;
		Date d = new Date(time);

		search.setId(10000);
		search.setGuid("FEAFC0AE644068B1E043DC72F00AAFC4");
		search.setCategoryId(999);
		search.setFolderId(999);
		search.setName("Search for UT");
		search.setDescription("desc for UT");
		search.setQueryStr("*");
		search.setOwner("SYSMAN");
		search.setLocked(false);
		search.setUiHidden(false);
		search.setCreatedOn(d);
		search.setLastModifiedOn(d);
		search.setLastModifiedBy("SYSMAN");
		search.setLastAccessDate(d);

		List<SearchParameter> params = new ArrayList<SearchParameter>();
		SearchParameter p1 = new SearchParameter();
		p1.setName("Param1");
		p1.setType(ParameterType.STRING);
		p1.setValue("value1");
		params.add(p1);
		search.setParameters(params);

		folder.setId(1000);
		folder.setName("Folder for UT");
		folder.setDescription("desc for UT");
		folder.setCreatedOn(d);
		folder.setOwner("SYSMAN");
		folder.setLastModifiedOn(d);
		folder.setLastModifiedBy("SYSMAN");
		folder.setParentId(1);
		folder.setSystemFolder(false);
		folder.setUiHidden(false);

		category.setId(100);
		category.setName("Category for UT");
		category.setDescription("desc for UT");
		category.setCreatedOn(d);
		category.setDefaultFolderId(1);
		category.setOwner("SYSMAN");
		List<Parameter> catParams = new ArrayList<Parameter>();
		Parameter cp1 = new Parameter();
		cp1.setName("CATEGORY_PARAM_VIEW_TASKFLOW");
		cp1.setType(ParameterType.STRING);
		cp1.setValue("/WEB-INF/core/loganalytics/obssearch/plugins/dashboard-flow-definition.xml#dashboard-flow-definition");
		catParams.add(cp1);
		category.setParameters(catParams);
	}

	@Test
	public void testGetFullCategoryObj() throws JSONException, EMAnalyticsFwkJsonException, MalformedURLException,
			URISyntaxException
	{
		JSONObject fullCategoryObj = EntityJsonUtil.getFullCategoryJsonObj(uri, category);
		String output = fullCategoryObj.toString();
		//		System.out.println(output);

		final String VERIFY_STRING1 = "\"id\":100";
		final String VERIFY_STRING2 = "\"name\":\"Category for UT\"";
		final String VERIFY_STRING3 = "\"description\":\"desc for UT\"";
		final String VERIFY_STRING4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String VERIFY_STRING5 = "\"defaultFolder\":{\"id\":1,\"href\":\"http:\\/\\/slc04pxi.us.oracle.com:7001\\/savedsearch\\/v1\\/folder\\/1\"}";
		final String VERIFY_STRING6 = "\"defaultFolderId\"";
		final String VERIFY_STRING7 = "\"parameters\":[{\"name\":\"CATEGORY_PARAM_VIEW_TASKFLOW\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING3), VERIFY_STRING3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING5), VERIFY_STRING5 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING6), VERIFY_STRING6 + " is found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING7), VERIFY_STRING7 + " is NOT found as expected");
	}

	@Test
	public void testGetFullFolderObj() throws JSONException, EMAnalyticsFwkJsonException, MalformedURLException,
			URISyntaxException
	{
		JSONObject fullFolderObj = EntityJsonUtil.getFullFolderJsonObj(uri, folder);
		String output = fullFolderObj.toString();
		//		System.out.println(output);

		final String VERIFY_STRING1 = "\"id\":1000";
		final String VERIFY_STRING2 = "\"name\":\"Folder for UT\"";
		final String VERIFY_STRING3 = "\"systemFolder\":false";
		final String VERIFY_STRING4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String VERIFY_STRING5 = "\"parentFolder\":{\"id\":1,\"href\":\"http:\\/\\/slc04pxi.us.oracle.com:7001\\/savedsearch\\/v1\\/folder\\/1\"}";
		final String VERIFY_STRING6 = "\"parentFolderId\"";
		final String VERIFY_STRING7 = "\"uiHidden\":false";
		final String VERIFY_STRING9 = "\"owner\":\"SYSMAN\"";
		final String VERIFY_STRING10 = "\"lastModifiedBy\":\"SYSMAN\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING3), VERIFY_STRING3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING5), VERIFY_STRING5 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING6), VERIFY_STRING6 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING7), VERIFY_STRING7 + " is found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING9), VERIFY_STRING9 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING10), VERIFY_STRING10 + " is NOT found as expected");
	}

	@Test
	public void testGetFullSearchJsonObj() throws JSONException, EMAnalyticsFwkJsonException, MalformedURLException,
			URISyntaxException
	{
		JSONObject fullSearchObj = EntityJsonUtil.getFullSearchJsonObj(uri, search);
		String output = fullSearchObj.toString();
		//		System.out.println(output);

		JSONObject fullSearchObjWithFolderPath = EntityJsonUtil.getFullSearchJsonObj(uri, search, new String[] { "parent Folder",
				"Root Folder" });
		String output2 = fullSearchObjWithFolderPath.toString();
		//		System.out.println(output2);

		final String VERIFY_STRING1 = "\"id\":10000";
		final String VERIFY_STRING2 = "\"queryStr\":\"*\"";
		final String VERIFY_STRING3 = "\"parameters\":[{\"name\":\"Param1\",\"value\":\"value1\",\"type\":\"STRING\"}]";
		final String VERIFY_STRING4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String VERIFY_STRING5 = "\"folder\":{\"id\":999,\"href\":\"http:\\/\\/slc04pxi.us.oracle.com:7001\\/savedsearch\\/v1\\/folder\\/999\"}";
		final String VERIFY_STRING6 = "\"folderId\"";
		final String VERIFY_STRING7 = "\"category\":{\"id\":999,\"href\":\"http:\\/\\/slc04pxi.us.oracle.com:7001\\/savedsearch\\/v1\\/category\\/999\"}";
		final String VERIFY_STRING8 = "\"categoryId\"";
		final String VERIFY_STRING9 = "\"locked\":false";
		final String VERIFY_STRING10 = "\"uiHidden\":false";
		final String VERIFY_STRING11 = "\"flattenedFolderPath\":[\"parent Folder\",\"Root Folder\"]";
		final String VERIFY_STRING12 = "\"guid\":";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING3), VERIFY_STRING3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING5), VERIFY_STRING5 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING6), VERIFY_STRING6 + " is found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING7), VERIFY_STRING7 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING8), VERIFY_STRING8 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING9), VERIFY_STRING9 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING10), VERIFY_STRING10 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING11), VERIFY_STRING11 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING12), VERIFY_STRING12 + " is found unexpected");

		Assert.assertTrue(output2.contains(VERIFY_STRING11), VERIFY_STRING11 + " is NOT found as expected");
		Assert.assertFalse(output2.contains(VERIFY_STRING12), VERIFY_STRING12 + " is found unexpected");
	}

	@Test
	public void testGetSimpleCategoryObj() throws JSONException, EMAnalyticsFwkJsonException, MalformedURLException,
			URISyntaxException
	{
		JSONObject fullCategoryObj = EntityJsonUtil.getSimpleCategoryJsonObj(uri, category);
		String output = fullCategoryObj.toString();
		//		System.out.println(output);

		final String VERIFY_STRING1 = "\"id\":100";
		final String VERIFY_STRING2 = "\"name\":\"Category for UT\"";
		final String VERIFY_STRING3 = "\"description\":\"desc for UT\"";
		final String VERIFY_STRING4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String VERIFY_STRING5 = "\"defaultFolder\":{\"id\":1,\"href\":\"http:\\/\\/slc04pxi.us.oracle.com:7001\\/savedsearch\\/v1\\/folder\\/1\"}";
		final String VERIFY_STRING6 = "\"defaultFolderId\"";
		final String VERIFY_STRING7 = "\"parameters\":[{\"name\":\"CATEGORY_PARAM_VIEW_TASKFLOW\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING3), VERIFY_STRING3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING5), VERIFY_STRING5 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING6), VERIFY_STRING6 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING7), VERIFY_STRING7 + " is found unexpected");
	}

	@Test
	public void testGetSimpleFolderJsonObj() throws JSONException, EMAnalyticsFwkJsonException, MalformedURLException,
			URISyntaxException
	{
		JSONObject simpleFolderObj = EntityJsonUtil.getSimpleFolderJsonObj(uri, folder);
		String output = simpleFolderObj.toString();
		//		System.out.println(output);
		JSONObject simpleFolderObjWithType = EntityJsonUtil.getSimpleFolderJsonObj(uri, folder, true);
		String output2 = simpleFolderObjWithType.toString();
		//		System.out.println(output2);

		final String VERIFY_STRING1 = "\"id\":1000";
		final String VERIFY_STRING2 = "\"name\":\"Folder for UT\"";
		final String VERIFY_STRING3 = "\"systemFolder\":false";
		final String VERIFY_STRING4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String VERIFY_STRING5 = "\"parentFolder\":{\"id\":1,\"href\":\"http:\\/\\/slc04pxi.us.oracle.com:7001\\/savedsearch\\/v1\\/folder\\/1\"}";
		final String VERIFY_STRING6 = "\"parentId\"";
		final String VERIFY_STRING7 = "\"uiHidden\":false";
		final String VERIFY_STRING8 = "\"type\":\"folder\"";
		final String VERIFY_STRING9 = "\"owner\":\"SYSMAN\"";
		final String VERIFY_STRING10 = "\"lastModifiedBy\":\"SYSMAN\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING3), VERIFY_STRING3 + " is found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING5), VERIFY_STRING5 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING6), VERIFY_STRING6 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING7), VERIFY_STRING7 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING8), VERIFY_STRING8 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING9), VERIFY_STRING9 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING10), VERIFY_STRING10 + " is found unexpected");

		Assert.assertTrue(output2.contains(VERIFY_STRING8), VERIFY_STRING8 + " is NOT found as expected");
	}

	@Test
	public void testGetSimpleSearchJsonObj() throws JSONException, EMAnalyticsFwkJsonException, MalformedURLException,
			URISyntaxException
	{
		JSONObject simpleSearchObj = EntityJsonUtil.getSimpleSearchJsonObj(uri, search);
		String output = simpleSearchObj.toString();
		//		System.out.println(output);

		JSONObject simpleSearchObjWithFolderPath = EntityJsonUtil.getSimpleSearchJsonObj(uri, search, new String[] {
				"parent Folder", "Root Folder" }, false);
		String output2 = simpleSearchObjWithFolderPath.toString();
		//		System.out.println(output2);

		JSONObject simpleSearchObjWithType = EntityJsonUtil.getSimpleSearchJsonObj(uri, search, null, true);
		String output3 = simpleSearchObjWithType.toString();
		//		System.out.println(output3);

		final String VERIFY_STRING1 = "\"id\":10000";
		final String VERIFY_STRING2 = "\"queryStr\":\"*\"";
		final String VERIFY_STRING3 = "\"parameters\":[{\"name\":\"Param1\",\"value\":\"value1\",\"type\":\"STRING\"}]";
		final String VERIFY_STRING4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String VERIFY_STRING5 = "\"folder\":{\"id\":999,\"href\":\"http:\\/\\/slc04pxi.us.oracle.com:7001\\/savedsearch\\/v1\\/folder\\/999\"}";
		final String VERIFY_STRING6 = "\"folderId\"";
		final String VERIFY_STRING7 = "\"category\":{\"id\":999,\"href\":\"http:\\/\\/slc04pxi.us.oracle.com:7001\\/savedsearch\\/v1\\/category\\/999\"}";
		final String VERIFY_STRING8 = "\"categoryId\"";
		final String VERIFY_STRING9 = "\"locked\":false";
		final String VERIFY_STRING10 = "\"uiHidden\":false";
		final String VERIFY_STRING11 = "\"flattenedFolderPath\":[\"parent Folder\",\"Root Folder\"]";
		final String VERIFY_STRING12 = "\"guid\":";
		final String VERIFY_STRING13 = "\"type\":\"search\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING2), VERIFY_STRING2 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING3), VERIFY_STRING3 + " is found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING5), VERIFY_STRING5 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING6), VERIFY_STRING6 + " is found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING7), VERIFY_STRING7 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING8), VERIFY_STRING8 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING9), VERIFY_STRING9 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING10), VERIFY_STRING10 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING11), VERIFY_STRING11 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING12), VERIFY_STRING12 + " is found unexpected");

		Assert.assertTrue(output2.contains(VERIFY_STRING11), VERIFY_STRING11 + " is NOT found as expected");
		Assert.assertFalse(output2.contains(VERIFY_STRING12), VERIFY_STRING12 + " is found unexpected");
		Assert.assertFalse(output2.contains(VERIFY_STRING13), VERIFY_STRING13 + " is found unexpected");

		Assert.assertTrue(output3.contains(VERIFY_STRING13), VERIFY_STRING13 + " is NOT found as expected");
	}
}

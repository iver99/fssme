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
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
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
	private static SearchImpl widget = new SearchImpl();
	private static FolderImpl folder = new FolderImpl();
	private static CategoryImpl category = new CategoryImpl();
	private static URI uri = null;
	private static String currentUser = "SYSMAN";
	private static final String HOST_PORT = "slc04pxi.us.oracle.com:7001";
	static {
		try {
			uri = new URL("http://" + HOST_PORT + "/savedsearch/v1/").toURI();
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
		search.setOwner(currentUser);
		search.setLocked(false);
		search.setUiHidden(false);
		search.setSystemSearch(true);
		search.setCreatedOn(d);
		search.setLastModifiedOn(d);
		search.setLastModifiedBy(currentUser);
		search.setLastAccessDate(d);

		folder.setId(1000);
		folder.setName("Folder for UT");
		folder.setDescription("desc for UT");
		folder.setCreatedOn(d);
		folder.setOwner(currentUser);
		folder.setLastModifiedOn(d);
		folder.setLastModifiedBy(currentUser);
		folder.setParentId(1);
		folder.setSystemFolder(false);
		folder.setUiHidden(false);

		category.setId(100);
		category.setName("Category for UT");
		category.setDescription("desc for UT");
		category.setCreatedOn(d);
		category.setDefaultFolderId(1);
		category.setOwner(currentUser);
		category.setProviderName("Provider name for UT");
		category.setProviderVersion("Provider version for UT");
		category.setProviderDiscovery("Provider discovery for UT");
		category.setProviderAssetRoot("Provider asset root for UT");
		List<Parameter> catParams = new ArrayList<Parameter>();
		Parameter cp1 = new Parameter();
		cp1.setName("CATEGORY_PARAM_VIEW_TASKFLOW");
		cp1.setType(ParameterType.STRING);
		cp1.setValue("/WEB-INF/core/loganalytics/obssearch/plugins/dashboard-flow-definition.xml#dashboard-flow-definition");
		catParams.add(cp1);
		category.setParameters(catParams);

		List<SearchParameter> params = new ArrayList<SearchParameter>();
		SearchParameter p1 = new SearchParameter();
		p1.setName("Param1");
		p1.setType(ParameterType.STRING);
		p1.setValue("value1");
		params.add(p1);
		search.setParameters(params);

		widget.setId(10001);
		widget.setGuid("FEAFC0AE644068B1E043DC72F00AAFC5");
		widget.setCategoryId(100);
		widget.setFolderId(999);
		widget.setName("Widget for UT");
		widget.setDescription("Widget desc for UT");
		widget.setQueryStr("*");
		widget.setOwner(currentUser);
		widget.setLocked(false);
		widget.setUiHidden(false);
		widget.setSystemSearch(true);
		widget.setCreatedOn(d);
		widget.setLastModifiedOn(d);
		widget.setLastModifiedBy(currentUser);
		widget.setLastAccessDate(d);
		widget.setIsWidget(true);

		List<SearchParameter> widgetParams = new ArrayList<SearchParameter>();
		SearchParameter wp1 = new SearchParameter();
		SearchParameter wp2 = new SearchParameter();
		SearchParameter wp3 = new SearchParameter();
		SearchParameter wp4 = new SearchParameter();
		SearchParameter wp5 = new SearchParameter();
		SearchParameter wp6 = new SearchParameter();
		SearchParameter wp7 = new SearchParameter();
		wp1.setName("WIDGET_VIEWMODEL");
		wp1.setType(ParameterType.STRING);
		wp1.setValue("dependencies/widgets/iFrame/js/widget-iframe");
		wp2.setName("WIDGET_KOC_NAME");
		wp2.setType(ParameterType.STRING);
		wp2.setValue("DF_V1_WIDGET_IFRAME");
		wp3.setName("WIDGET_TEMPLATE");
		wp3.setType(ParameterType.STRING);
		wp3.setValue("dependencies/widgets/iFrame/widget-iframe.html");
		wp4.setName("PROVIDER_VERSION");
		wp4.setType(ParameterType.STRING);
		wp4.setValue("0.1");
		wp5.setName("PROVIDER_NAME");
		wp5.setType(ParameterType.STRING);
		wp5.setValue("DB Analytics");
		wp6.setName("PROVIDER_ASSET_ROOT");
		wp6.setType(ParameterType.STRING);
		wp6.setValue("home");
		wp7.setName("WIDGET_VISUAL");
		wp7.setType(ParameterType.CLOB);
		wp7.setValue("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAL4AAACMCAIAAABNpIRsAAAYKklEQVR4AdxSBRIDIQy8");
		widgetParams.add(wp1);
		widgetParams.add(wp2);
		widgetParams.add(wp3);
		widgetParams.add(wp4);
		widgetParams.add(wp5);
		widgetParams.add(wp6);
		widgetParams.add(wp7);

		widget.setParameters(widgetParams);
	}

	@Test
	public void testGetFullCategoryObj() throws JSONException, EMAnalyticsFwkException, MalformedURLException, URISyntaxException
	{
		JSONObject fullCategoryObj = EntityJsonUtil.getFullCategoryJsonObj(uri, category);
		String output = fullCategoryObj.toString();
		//		System.out.println(output);

		final String VERIFY_STRING1 = "\"id\":100";
		final String VERIFY_STRING2 = "\"name\":\"Category for UT\"";
		final String VERIFY_STRING3 = "\"description\":\"desc for UT\"";
		final String VERIFY_STRING4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String VERIFY_STRING5 = "\"defaultFolder\":{\"id\":1,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/folder\\/1\"}";
		final String VERIFY_STRING6 = "\"defaultFolderId\"";
		final String VERIFY_STRING7 = "\"parameters\":[{\"name\":\"CATEGORY_PARAM_VIEW_TASKFLOW\"";
		final String VERIFY_STRING9 = "\"href\":\"http:\\/\\/" + HOST_PORT + "\\/savedsearch\\/v1\\/category\\/100\"}";
		final String VERIFY_STRING10 = "\"providerName\":\"Provider name for UT\"";
		final String VERIFY_STRING11 = "\"providerVersion\":\"Provider version for UT\"";
		final String VERIFY_STRING12 = "\"providerDiscovery\":\"Provider discovery for UT\"";
		final String VERIFY_STRING13 = "\"providerAssetRoot\":\"Provider asset root for UT\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING3), VERIFY_STRING3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING5), VERIFY_STRING5 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING6), VERIFY_STRING6 + " is found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING7), VERIFY_STRING7 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING9), VERIFY_STRING9 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING10), VERIFY_STRING10 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING11), VERIFY_STRING11 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING12), VERIFY_STRING12 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING13), VERIFY_STRING13 + " is NOT found as expected");
	}

	@Test
	public void testGetFullFolderObj() throws JSONException, EMAnalyticsFwkException, MalformedURLException, URISyntaxException
	{
		JSONObject fullFolderObj = EntityJsonUtil.getFullFolderJsonObj(uri, folder);
		String output = fullFolderObj.toString();
		//		System.out.println(output);

		final String VERIFY_STRING1 = "\"id\":1000";
		final String VERIFY_STRING2 = "\"name\":\"Folder for UT\"";
		final String VERIFY_STRING3 = "\"systemFolder\":false";
		final String VERIFY_STRING4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String VERIFY_STRING5 = "\"parentFolder\":{\"id\":1,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/folder\\/1\"}";
		final String VERIFY_STRING6 = "\"parentFolderId\"";
		final String VERIFY_STRING7 = "\"uiHidden\":false";
		final String VERIFY_STRING8 = "\"systemFolder\":false";
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
		Assert.assertTrue(output.contains(VERIFY_STRING8), VERIFY_STRING8 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING9), VERIFY_STRING9 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING10), VERIFY_STRING10 + " is NOT found as expected");
	}

	@Test
	public void testGetFullSearchJsonObj() throws JSONException, EMAnalyticsFwkException, MalformedURLException,
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
		final String VERIFY_STRING5 = "\"folder\":{\"id\":999,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/folder\\/999\"}";
		final String VERIFY_STRING6 = "\"folderId\"";
		final String VERIFY_STRING7 = "\"category\":{\"id\":999,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/category\\/999\"}";
		final String VERIFY_STRING8 = "\"categoryId\"";
		final String VERIFY_STRING9 = "\"locked\":false";
		final String VERIFY_STRING10 = "\"uiHidden\":false";
		final String VERIFY_STRING11 = "\"flattenedFolderPath\":[\"parent Folder\",\"Root Folder\"]";
		final String VERIFY_STRING12 = "\"guid\":";
		final String VERIFY_STRING13 = "\"systemSearch\":true";
		final String VERIFY_STRING14 = "\"isWidget\":false";

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
		Assert.assertTrue(output.contains(VERIFY_STRING13), VERIFY_STRING13 + " is NOT found as expected");

		Assert.assertTrue(output2.contains(VERIFY_STRING11), VERIFY_STRING11 + " is NOT found as expected");
		Assert.assertFalse(output2.contains(VERIFY_STRING12), VERIFY_STRING12 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING14), VERIFY_STRING14 + " is found unexpected");
	}

	@Test
	public void testGetSimpleCategoryObj() throws JSONException, EMAnalyticsFwkException, MalformedURLException,
			URISyntaxException
	{
		JSONObject simpleCategoryObj = EntityJsonUtil.getSimpleCategoryJsonObj(uri, category);
		String output = simpleCategoryObj.toString();
		//		System.out.println(output);

		final String VERIFY_STRING1 = "\"id\":100";
		final String VERIFY_STRING2 = "\"name\":\"Category for UT\"";
		final String VERIFY_STRING3 = "\"description\":\"desc for UT\"";
		final String VERIFY_STRING4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String VERIFY_STRING5 = "\"defaultFolder\":{\"id\":1,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/folder\\/1\"}";
		final String VERIFY_STRING6 = "\"defaultFolderId\"";
		final String VERIFY_STRING7 = "\"parameters\":[{\"name\":\"CATEGORY_PARAM_VIEW_TASKFLOW\"";
		final String VERIFY_STRING8 = "\"owner\":\"SYSMAN\"";
		final String VERIFY_STRING9 = "\"href\":\"http:\\/\\/" + HOST_PORT + "\\/savedsearch\\/v1\\/category\\/100\"}";
		final String VERIFY_STRING10 = "\"providerName\":\"Provider name for UT\"";
		final String VERIFY_STRING11 = "\"providerVersion\":\"Provider version for UT\"";
		final String VERIFY_STRING12 = "\"providerDiscovery\":\"Provider discovery for UT\"";
		final String VERIFY_STRING13 = "\"providerAssetRoot\":\"Provider asset root for UT\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING3), VERIFY_STRING3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING5), VERIFY_STRING5 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING6), VERIFY_STRING6 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING7), VERIFY_STRING7 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING8), VERIFY_STRING8 + " is found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING9), VERIFY_STRING9 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING10), VERIFY_STRING10 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING11), VERIFY_STRING11 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING12), VERIFY_STRING12 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING13), VERIFY_STRING13 + " is NOT found as expected");
	}

	@Test
	public void testGetSimpleFolderJsonObj() throws JSONException, EMAnalyticsFwkException, MalformedURLException,
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
		final String VERIFY_STRING5 = "\"parentFolder\":{\"id\":1,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/folder\\/1\"}";
		final String VERIFY_STRING6 = "\"parentId\"";
		final String VERIFY_STRING7 = "\"uiHidden\":false";
		final String VERIFY_STRING8 = "\"type\":\"folder\"";
		final String VERIFY_STRING9 = "\"owner\":\"SYSMAN\"";
		final String VERIFY_STRING10 = "\"lastModifiedBy\":\"SYSMAN\"";
		final String VERIFY_STRING11 = "\"systemFolder\":false";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING3), VERIFY_STRING3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING5), VERIFY_STRING5 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING6), VERIFY_STRING6 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING7), VERIFY_STRING7 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING8), VERIFY_STRING8 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING9), VERIFY_STRING9 + " is found unexpected");
		Assert.assertFalse(output.contains(VERIFY_STRING10), VERIFY_STRING10 + " is found unexpected");
		Assert.assertTrue(output.contains(VERIFY_STRING11), VERIFY_STRING11 + " is NOT found as expected");

		Assert.assertTrue(output2.contains(VERIFY_STRING8), VERIFY_STRING8 + " is NOT found as expected");
	}

	@Test
	public void testGetSimpleSearchJsonObj() throws JSONException, EMAnalyticsFwkException, MalformedURLException,
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
		final String VERIFY_STRING5 = "\"folder\":{\"id\":999,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/folder\\/999\"}";
		final String VERIFY_STRING6 = "\"folderId\"";
		final String VERIFY_STRING7 = "\"category\":{\"id\":999,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/category\\/999\"}";
		final String VERIFY_STRING8 = "\"categoryId\"";
		final String VERIFY_STRING9 = "\"locked\":false";
		final String VERIFY_STRING10 = "\"uiHidden\":false";
		final String VERIFY_STRING11 = "\"flattenedFolderPath\":[\"parent Folder\",\"Root Folder\"]";
		final String VERIFY_STRING12 = "\"guid\":";
		final String VERIFY_STRING13 = "\"type\":\"search\"";
		final String VERIFY_STRING14 = "\"systemSearch\":true";
		final String VERIFY_STRING15 = "\"isWidget\":false";

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
		Assert.assertTrue(output.contains(VERIFY_STRING14), VERIFY_STRING14 + " is NOT found as expected");

		Assert.assertTrue(output2.contains(VERIFY_STRING11), VERIFY_STRING11 + " is NOT found as expected");
		Assert.assertFalse(output2.contains(VERIFY_STRING12), VERIFY_STRING12 + " is found unexpected");
		Assert.assertFalse(output2.contains(VERIFY_STRING13), VERIFY_STRING13 + " is found unexpected");

		Assert.assertTrue(output3.contains(VERIFY_STRING13), VERIFY_STRING13 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING15), VERIFY_STRING15 + " is found unexpected");
	}

	@Test
	public void testGetWidgetGroupJsonObj() throws EMAnalyticsFwkException
	{
		JSONObject widgetGroupObj = EntityJsonUtil.getWidgetGroupJsonObj(uri, category);
		String output = widgetGroupObj.toString();

		final String VERIFY_STRING1 = "\"WIDGET_GROUP_ID\":100";
		final String VERIFY_STRING2 = "\"WIDGET_GROUP_NAME\":\"Category for UT\"";
		final String VERIFY_STRING3 = "\"WIDGET_GROUP_DESCRIPTION\":\"desc for UT\"";
		final String VERIFY_STRING4 = "\"PROVIDER_NAME\":\"Provider name for UT\"";
		final String VERIFY_STRING5 = "\"PROVIDER_VERSION\":\"Provider version for UT\"";
		final String VERIFY_STRING6 = "\"PROVIDER_DISCOVERY\":\"Provider discovery for UT\"";
		final String VERIFY_STRING7 = "\"PROVIDER_ASSET_ROOT\":\"Provider asset root for UT\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING3), VERIFY_STRING3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING5), VERIFY_STRING5 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING6), VERIFY_STRING6 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING7), VERIFY_STRING7 + " is NOT found as expected");
	}

	@Test
	public void testGetWidgetJsonObj() throws EMAnalyticsFwkException
	{
		JSONObject widgetObj = EntityJsonUtil.getWidgetJsonObj(uri, widget, category);
		String output = widgetObj.toString();

		final String VERIFY_STRING1 = "\"WIDGET_UNIQUE_ID\":10001";
		final String VERIFY_STRING2 = "\"WIDGET_NAME\":\"Widget for UT\"";
		final String VERIFY_STRING3 = "\"WIDGET_DESCRIPTION\":\"Widget desc for UT\"";
		final String VERIFY_STRING4 = "\"WIDGET_OWNER\":\"SYSMAN\"";
		final String VERIFY_STRING5 = "\"WIDGET_CREATION_TIME\":\"2014-07-22T14:48:53.048Z\"";
		final String VERIFY_STRING6 = "\"WIDGET_SOURCE\":1";
		final String VERIFY_STRING7 = "\"WIDGET_GROUP_NAME\":\"Category for UT\"";
		final String VERIFY_STRING8 = "\"WIDGET_VIEWMODEL\":\"dependencies\\/widgets\\/iFrame\\/js\\/widget-iframe\"";
		final String VERIFY_STRING9 = "\"WIDGET_KOC_NAME\":\"DF_V1_WIDGET_IFRAME\"";
		final String VERIFY_STRING10 = "\"WIDGET_TEMPLATE\":\"dependencies\\/widgets\\/iFrame\\/widget-iframe.html\"";
		final String VERIFY_STRING11 = "\"PROVIDER_VERSION\":\"0.1\"";
		final String VERIFY_STRING12 = "\"PROVIDER_NAME\":\"DB Analytics\"";
		final String VERIFY_STRING13 = "\"PROVIDER_ASSET_ROOT\":\"home\"";
		final String VERIFY_STRING14 = "\"PROVIDER_VERSION\":\"Provider version for UT\"";
		final String VERIFY_STRING15 = "\"PROVIDER_NAME\":\"Provider name for UT\"";
		final String VERIFY_STRING16 = "\"PROVIDER_ASSET_ROOT\":\"Provider asset root for UT\"";
		final String VERIFY_STRING17 = "\"WIDGET_VISUAL\":\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAL4AAACMCAIAAABNpIRsAAAYKklEQVR4AdxSBRIDIQy8\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING2), VERIFY_STRING2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING3), VERIFY_STRING3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING4), VERIFY_STRING4 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING5), VERIFY_STRING5 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING6), VERIFY_STRING6 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING7), VERIFY_STRING7 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING8), VERIFY_STRING8 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING9), VERIFY_STRING9 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING10), VERIFY_STRING10 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING11), VERIFY_STRING11 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING12), VERIFY_STRING12 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING13), VERIFY_STRING13 + " is NOT found as expected");
		Assert.assertFalse(output.contains(VERIFY_STRING17), VERIFY_STRING17 + " is found NOT as expected");

		widget.getParameters().remove(5);
		widget.getParameters().remove(4);
		widget.getParameters().remove(3);
		widgetObj = EntityJsonUtil.getWidgetJsonObj(uri, widget, category);
		Assert.assertNotNull(widgetObj);
		output = widgetObj.toString();
		Assert.assertTrue(output.contains(VERIFY_STRING14), VERIFY_STRING14 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING15), VERIFY_STRING15 + " is NOT found as expected");
		Assert.assertTrue(output.contains(VERIFY_STRING16), VERIFY_STRING16 + " is NOT found as expected");
		widget.getParameters().remove(2);
		widgetObj = EntityJsonUtil.getWidgetJsonObj(uri, widget, category);
		Assert.assertNull(widgetObj);
	}

	@Test
	public void testGetWidgetScreenshotObj() throws EMAnalyticsFwkException
	{
		JSONObject widgetScreenshotObj = EntityJsonUtil
				.getWidgetScreenshotJsonObj("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAL4AAACM...");
		String output = widgetScreenshotObj.toString();

		final String VERIFY_STRING1 = "\"screenShot\":\"data:image\\/png;base64,iVBORw0KGgoAAAANSUhEUgAAAL4AAACM...\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(VERIFY_STRING1), VERIFY_STRING1 + " is NOT found as expected");

		widgetScreenshotObj = EntityJsonUtil.getWidgetScreenshotJsonObj(null);
		output = widgetScreenshotObj.toString();
		Assert.assertEquals(output, "{}");
	}
}

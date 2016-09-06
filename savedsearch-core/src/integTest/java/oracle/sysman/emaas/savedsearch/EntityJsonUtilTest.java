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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
	private static final Logger LOGGER = LogManager.getLogger( EntityJsonUtilTest.class);

	static {
		try {
			uri = new URL("http://" + HOST_PORT + "/savedsearch/v1/").toURI();
		}
		catch (MalformedURLException e) {
			LOGGER.error(e.getLocalizedMessage());
		}
		catch (URISyntaxException e) {
			LOGGER.error(e.getLocalizedMessage());
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
		SearchParameter wp8 = new SearchParameter();
		SearchParameter wp9 = new SearchParameter();
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
		wp8.setName("WIDGET_DEFAULT_WIDTH");
		wp8.setType(ParameterType.STRING);
		wp8.setValue("10");
		wp9.setName("WIDGET_DEFAULT_HEIGHT");
		wp9.setType(ParameterType.STRING);
		wp9.setValue("0");
		widgetParams.add(wp1);
		widgetParams.add(wp2);
		widgetParams.add(wp3);
		widgetParams.add(wp4);
		widgetParams.add(wp5);
		widgetParams.add(wp6);
		widgetParams.add(wp7);
		widgetParams.add(wp8);
		widgetParams.add(wp9);

		widget.setParameters(widgetParams);
	}

	@Test(groups = { "s1" })
	public void testGetFullCategoryObj() throws JSONException, EMAnalyticsFwkException, MalformedURLException, URISyntaxException
	{
		JSONObject fullCategoryObj = EntityJsonUtil.getFullCategoryJsonObj(uri, category);
		String output = fullCategoryObj.toString();

		final String verifyString1 = "\"id\":100";
		final String verifyString2 = "\"name\":\"Category for UT\"";
		final String verifyString3 = "\"description\":\"desc for UT\"";
		final String verifyString4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String verifyString5 = "\"defaultFolder\":{\"id\":1,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/folder\\/1\"}";
		final String verifyString6 = "\"defaultFolderId\"";
		final String verifyString7 = "\"parameters\":[{\"name\":\"CATEGORY_PARAM_VIEW_TASKFLOW\"";
		final String verifyString9 = "\"href\":\"http:\\/\\/" + HOST_PORT + "\\/savedsearch\\/v1\\/category\\/100\"}";
		final String verifyString10 = "\"providerName\":\"Provider name for UT\"";
		final String verifyString11 = "\"providerVersion\":\"Provider version for UT\"";
		final String verifyString12 = "\"providerDiscovery\":\"Provider discovery for UT\"";
		final String verifyString13 = "\"providerAssetRoot\":\"Provider asset root for UT\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(verifyString1), verifyString1 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString2), verifyString2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString3), verifyString3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString4), verifyString4 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString5), verifyString5 + " is NOT found as expected");
		Assert.assertFalse(output.contains(verifyString6), verifyString6 + " is found unexpected");
		Assert.assertTrue(output.contains(verifyString7), verifyString7 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString9), verifyString9 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString10), verifyString10 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString11), verifyString11 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString12), verifyString12 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString13), verifyString13 + " is NOT found as expected");
	}

	@Test(groups = { "s1" })
	public void testGetFullFolderObj() throws JSONException, EMAnalyticsFwkException, MalformedURLException, URISyntaxException
	{
		JSONObject fullFolderObj = EntityJsonUtil.getFullFolderJsonObj(uri, folder);
		String output = fullFolderObj.toString();

		final String verifyString1 = "\"id\":1000";
		final String verifyString2 = "\"name\":\"Folder for UT\"";
		final String verifyString3 = "\"systemFolder\":false";
		final String verifyString4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String verifyString5 = "\"parentFolder\":{\"id\":1,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/folder\\/1\"}";
		final String verifyString6 = "\"parentFolderId\"";
		final String verifyString7 = "\"uiHidden\":false";
		final String verifyString8 = "\"systemFolder\":false";
		final String verifyString9 = "\"owner\":\"SYSMAN\"";
		final String verifyString10 = "\"lastModifiedBy\":\"SYSMAN\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(verifyString1), verifyString1 + " is NOT found unexpected");
		Assert.assertTrue(output.contains(verifyString2), verifyString2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString3), verifyString3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString4), verifyString4 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString5), verifyString5 + " is NOT found as expected");
		Assert.assertFalse(output.contains(verifyString6), verifyString6 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString7), verifyString7 + " is found unexpected");
		Assert.assertTrue(output.contains(verifyString8), verifyString8 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString9), verifyString9 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString10), verifyString10 + " is NOT found as expected");
	}

	@Test(groups = { "s1" })
	public void testGetFullSearchJsonObj() throws JSONException, EMAnalyticsFwkException, MalformedURLException,
	URISyntaxException
	{
		JSONObject fullSearchObj = EntityJsonUtil.getFullSearchJsonObj(uri, search);
		String output = fullSearchObj.toString();

		JSONObject fullSearchObjWithFolderPath = EntityJsonUtil.getFullSearchJsonObj(uri, search, new String[] { "parent Folder",
		"Root Folder" });
		String output2 = fullSearchObjWithFolderPath.toString();

		final String verifyString1 = "\"id\":10000";
		final String verifyString2 = "\"queryStr\":\"*\"";
		final String verifyString3 = "\"parameters\":[{\"name\":\"Param1\",\"value\":\"value1\",\"type\":\"STRING\"}]";
		final String verifyString4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String verifyString5 = "\"folder\":{\"id\":999,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/folder\\/999\"}";
		final String verifyString6 = "\"folderId\"";
		final String verifyString7 = "\"category\":{\"id\":999,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/category\\/999\"}";
		final String verifyString8 = "\"categoryId\"";
		final String verifyString9 = "\"locked\":false";
		final String verifyString10 = "\"uiHidden\":false";
		final String verifyString11 = "\"flattenedFolderPath\":[\"parent Folder\",\"Root Folder\"]";
		final String verifyString12 = "\"guid\":";
		final String verifyString13 = "\"systemSearch\":true";
		final String verifyString14 = "\"isWidget\":false";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(verifyString1), verifyString1 + " is NOT found unexpected");
		Assert.assertTrue(output.contains(verifyString2), verifyString2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString3), verifyString3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString4), verifyString4 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString5), verifyString5 + " is NOT found as expected");
		Assert.assertFalse(output.contains(verifyString6), verifyString6 + " is found unexpected");
		Assert.assertTrue(output.contains(verifyString7), verifyString7 + " is NOT found as expected");
		Assert.assertFalse(output.contains(verifyString8), verifyString8 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString9), verifyString9 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString10), verifyString10 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString11), verifyString11 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString12), verifyString12 + " is found unexpected");
		Assert.assertTrue(output.contains(verifyString13), verifyString13 + " is NOT found as expected");

		Assert.assertTrue(output2.contains(verifyString11), verifyString11 + " is NOT found as expected");
		Assert.assertFalse(output2.contains(verifyString12), verifyString12 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString14), verifyString14 + " is found unexpected");
	}

	@Test(groups = { "s1" })
	public void testGetJsonString() throws EMAnalyticsFwkException {
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("SEARCH_ID", 1000);
		m.put("WIDGET_KOC_NAME", "TestKOC");
		m.put("WIDGET_VIEWMODEL", "TestViewModel");
		m.put("WIDGET_TEMPLATE", "TestTemplate");
		m.put("WIDGET_LINKED_DASHBOARD", "1");
		m.put("WIDGET_DEFAULT_WIDTH", 1);
		m.put("WIDGET_DEFAULT_HEIGHT", 1);
		m.put("DASHBOARD_INELIGIBLE", "1");
		m.put("NAME", "TestName");
		m.put("DESCRIPTION", "TestDesc");
		m.put("OWNER", "Test");
		//m.put("CREATION_DATE", "2016-05-16 05:49:10");
		m.put("CATOGORY_NAME", "TestCateName");
		m.put("PROVIDER_NAME", "TestProviderName");
		m.put("PROVIDER_VERSION", "TestProviderVersion");
		m.put("PROVIDER_ASSET_ROOT", "TestProviderAssetRoot");
		String result = null;
		result = EntityJsonUtil.getJsonString(m, "testScreenshotUrl");
		final String verifyString1 = "\"WIDGET_UNIQUE_ID\":1000";
		final String verifyString2 = "\"WIDGET_KOC_NAME\":\"TestKOC\"";
		final String verifyString3 = "\"WIDGET_VIEWMODEL\":\"TestViewModel\"";
		final String verifyString4 = "\"WIDGET_TEMPLATE\":\"TestTemplate\"";
		final String verifyString5 = "\"WIDGET_LINKED_DASHBOARD\":\"1\"";
		final String verifyString6 = "\"WIDGET_DEFAULT_WIDTH\":\"1\"";
		final String verifyString7 = "\"WIDGET_DEFAULT_HEIGHT\":\"1\"";
		final String verifyString8 = "\"DASHBOARD_INELIGIBLE\":\"1\"";
		final String verifyString9 = "\"WIDGET_NAME\":\"TestName\"";
		final String verifyString10 = "\"WIDGET_DESCRIPTION\":\"TestDesc\"";
		final String verifyString11 = "\"WIDGET_OWNER\":\"Test\"";
		final String verifyString14 = "\"PROVIDER_NAME\":\"TestProviderName\"";
		final String verifyString15 = "\"PROVIDER_VERSION\":\"TestProviderVersion\"";
		final String verifyString16 = "\"PROVIDER_ASSET_ROOT\":\"TestProviderAssetRoot\"";
		Assert.assertNotNull(result);
		Assert.assertTrue(result.contains(verifyString1), verifyString1 + " is NOT found as expected");
		Assert.assertTrue(result.contains(verifyString2), verifyString2 + " is NOT found as expected");
		Assert.assertTrue(result.contains(verifyString3), verifyString3 + " is NOT found as expected");
		Assert.assertTrue(result.contains(verifyString4), verifyString4 + " is NOT found as expected");
		Assert.assertTrue(result.contains(verifyString5), verifyString5 + " is found unexpected");
		Assert.assertTrue(result.contains(verifyString6), verifyString6 + " is found unexpected");
		Assert.assertTrue(result.contains(verifyString7), verifyString7 + " is found unexpected");
		Assert.assertTrue(result.contains(verifyString8), verifyString8 + " is found unexpected");
		Assert.assertTrue(result.contains(verifyString9), verifyString9 + " is NOT found as expected");
		Assert.assertTrue(result.contains(verifyString10), verifyString10 + " is NOT found as expected");
		Assert.assertTrue(result.contains(verifyString11), verifyString11 + " is NOT found as expected");
		Assert.assertTrue(result.contains(verifyString14), verifyString14 + " is NOT found as expected");
		Assert.assertTrue(result.contains(verifyString15), verifyString15 + " is NOT found as expected");
		Assert.assertTrue(result.contains(verifyString16), verifyString16 + " is NOT found as expected");
	}

	@Test(groups = { "s1" })
	public void testGetSimpleCategoryObj() throws JSONException, EMAnalyticsFwkException, MalformedURLException,
	URISyntaxException
	{
		JSONObject simpleCategoryObj = EntityJsonUtil.getSimpleCategoryJsonObj(uri, category);
		String output = simpleCategoryObj.toString();

		final String verifyString1 = "\"id\":100";
		final String verifyString2 = "\"name\":\"Category for UT\"";
		final String verifyString3 = "\"description\":\"desc for UT\"";
		final String verifyString4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String verifyString5 = "\"defaultFolder\":{\"id\":1,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/folder\\/1\"}";
		final String verifyString6 = "\"defaultFolderId\"";
		final String verifyString7 = "\"parameters\":[{\"name\":\"CATEGORY_PARAM_VIEW_TASKFLOW\"";
		final String verifyString8 = "\"owner\":\"SYSMAN\"";
		final String verifyString9 = "\"href\":\"http:\\/\\/" + HOST_PORT + "\\/savedsearch\\/v1\\/category\\/100\"}";
		final String verifyString10 = "\"providerName\":\"Provider name for UT\"";
		final String verifyString11 = "\"providerVersion\":\"Provider version for UT\"";
		final String verifyString12 = "\"providerDiscovery\":\"Provider discovery for UT\"";
		final String verifyString13 = "\"providerAssetRoot\":\"Provider asset root for UT\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(verifyString1), verifyString1 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString2), verifyString2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString3), verifyString3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString4), verifyString4 + " is NOT found as expected");
		Assert.assertFalse(output.contains(verifyString5), verifyString5 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString6), verifyString6 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString7), verifyString7 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString8), verifyString8 + " is found unexpected");
		Assert.assertTrue(output.contains(verifyString9), verifyString9 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString10), verifyString10 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString11), verifyString11 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString12), verifyString12 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString13), verifyString13 + " is NOT found as expected");
	}

	@Test(groups = { "s1" })
	public void testGetSimpleFolderJsonObj() throws JSONException, EMAnalyticsFwkException, MalformedURLException,
	URISyntaxException
	{
		JSONObject simpleFolderObj = EntityJsonUtil.getSimpleFolderJsonObj(uri, folder);
		String output = simpleFolderObj.toString();
		JSONObject simpleFolderObjWithType = EntityJsonUtil.getSimpleFolderJsonObj(uri, folder, true);
		String output2 = simpleFolderObjWithType.toString();

		final String verifyString1 = "\"id\":1000";
		final String verifyString2 = "\"name\":\"Folder for UT\"";
		final String verifyString3 = "\"systemFolder\":false";
		final String verifyString4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String verifyString5 = "\"parentFolder\":{\"id\":1,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/folder\\/1\"}";
		final String verifyString6 = "\"parentId\"";
		final String verifyString7 = "\"uiHidden\":false";
		final String verifyString8 = "\"type\":\"folder\"";
		final String verifyString9 = "\"owner\":\"SYSMAN\"";
		final String verifyString10 = "\"lastModifiedBy\":\"SYSMAN\"";
		final String verifyString11 = "\"systemFolder\":false";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(verifyString1), verifyString1 + " is NOT found unexpected");
		Assert.assertTrue(output.contains(verifyString2), verifyString2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString3), verifyString3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString4), verifyString4 + " is NOT found as expected");
		Assert.assertFalse(output.contains(verifyString5), verifyString5 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString6), verifyString6 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString7), verifyString7 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString8), verifyString8 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString9), verifyString9 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString10), verifyString10 + " is found unexpected");
		Assert.assertTrue(output.contains(verifyString11), verifyString11 + " is NOT found as expected");

		Assert.assertTrue(output2.contains(verifyString8), verifyString8 + " is NOT found as expected");
	}

	@Test(groups = { "s1" })
	public void testGetSimpleSearchJsonObj() throws JSONException, EMAnalyticsFwkException, MalformedURLException,
	URISyntaxException
	{
		JSONObject simpleSearchObj = EntityJsonUtil.getSimpleSearchJsonObj(uri, search);
		String output = simpleSearchObj.toString();

		JSONObject simpleSearchObjWithFolderPath = EntityJsonUtil.getSimpleSearchJsonObj(uri, search, new String[] {
				"parent Folder", "Root Folder" }, false);
		String output2 = simpleSearchObjWithFolderPath.toString();

		JSONObject simpleSearchObjWithType = EntityJsonUtil.getSimpleSearchJsonObj(uri, search, null, true);
		String output3 = simpleSearchObjWithType.toString();

		final String verifyString1 = "\"id\":10000";
		final String verifyString2 = "\"queryStr\":\"*\"";
		final String verifyString3 = "\"parameters\":[{\"name\":\"Param1\",\"value\":\"value1\",\"type\":\"STRING\"}]";
		final String verifyString4 = "\"createdOn\":\"2014-07-22T14:48:53.048Z\"";
		final String verifyString5 = "\"folder\":{\"id\":999,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/folder\\/999\"}";
		final String verifyString6 = "\"folderId\"";
		final String verifyString7 = "\"category\":{\"id\":999,\"href\":\"http:\\/\\/" + HOST_PORT
				+ "\\/savedsearch\\/v1\\/category\\/999\"}";
		final String verifyString8 = "\"categoryId\"";
		final String verifyString9 = "\"locked\":false";
		final String verifyString10 = "\"uiHidden\":false";
		final String verifyString11 = "\"flattenedFolderPath\":[\"parent Folder\",\"Root Folder\"]";
		final String verifyString12 = "\"guid\":";
		final String verifyString13 = "\"type\":\"search\"";
		final String verifyString14 = "\"systemSearch\":true";
		final String verifyString15 = "\"isWidget\":false";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(verifyString1), verifyString1 + " is NOT found unexpected");
		Assert.assertFalse(output.contains(verifyString2), verifyString2 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString3), verifyString3 + " is found unexpected");
		Assert.assertTrue(output.contains(verifyString4), verifyString4 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString5), verifyString5 + " is NOT found as expected");
		Assert.assertFalse(output.contains(verifyString6), verifyString6 + " is found unexpected");
		Assert.assertTrue(output.contains(verifyString7), verifyString7 + " is NOT found as expected");
		Assert.assertFalse(output.contains(verifyString8), verifyString8 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString9), verifyString9 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString10), verifyString10 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString11), verifyString11 + " is found unexpected");
		Assert.assertFalse(output.contains(verifyString12), verifyString12 + " is found unexpected");
		Assert.assertTrue(output.contains(verifyString14), verifyString14 + " is NOT found as expected");

		Assert.assertTrue(output2.contains(verifyString11), verifyString11 + " is NOT found as expected");
		Assert.assertFalse(output2.contains(verifyString12), verifyString12 + " is found unexpected");
		Assert.assertFalse(output2.contains(verifyString13), verifyString13 + " is found unexpected");

		Assert.assertTrue(output3.contains(verifyString13), verifyString13 + " is NOT found as expected");
		Assert.assertFalse(output.contains(verifyString15), verifyString15 + " is found unexpected");
	}

	@Test(groups = { "s1" })
	public void testGetTargetCardJsonObj()
	{
		search.setFolderId(1);
		search.setCategoryId(1);
		try {
			JSONObject jsonObject = EntityJsonUtil.getTargetCardJsonObj(uri, search, null);
			Assert.assertNotNull(jsonObject);
			Assert.assertNotNull(jsonObject.get("folder"));
			Assert.assertNotNull(jsonObject.get("category"));
			Assert.assertEquals(jsonObject.has("folderId"), false);
			Assert.assertEquals(jsonObject.has("categoryId"), false);

		}
		catch (EMAnalyticsFwkException e) {
			e.printStackTrace();
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Test(groups = { "s1" })
	public void testGetWidgetGroupJsonObj() throws EMAnalyticsFwkException
	{
		JSONObject widgetGroupObj = EntityJsonUtil.getWidgetGroupJsonObj(uri, category);
		String output = widgetGroupObj.toString();

		final String verifyString1 = "\"WIDGET_GROUP_ID\":100";
		final String verifyString2 = "\"WIDGET_GROUP_NAME\":\"Category for UT\"";
		final String verifyString3 = "\"WIDGET_GROUP_DESCRIPTION\":\"desc for UT\"";
		final String verifyString4 = "\"PROVIDER_NAME\":\"Provider name for UT\"";
		final String verifyString5 = "\"PROVIDER_VERSION\":\"Provider version for UT\"";
		final String verifyString6 = "\"PROVIDER_DISCOVERY\":\"Provider discovery for UT\"";
		final String verifyString7 = "\"PROVIDER_ASSET_ROOT\":\"Provider asset root for UT\"";

		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(verifyString1), verifyString1 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString2), verifyString2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString3), verifyString3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString4), verifyString4 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString5), verifyString5 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString6), verifyString6 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString7), verifyString7 + " is NOT found as expected");
	}

	@Test(groups = { "s1" })
	public void testGetWidgetJsonObj() throws EMAnalyticsFwkException
	{
		JSONObject widgetObj = EntityJsonUtil.getWidgetJsonObj(widget, category, null);
		String output = widgetObj.toString();
		final String verifyString1 = "\"WIDGET_UNIQUE_ID\":10001";
		final String verifyString2 = "\"WIDGET_NAME\":\"Widget for UT\"";
		final String verifyString3 = "\"WIDGET_DESCRIPTION\":\"Widget desc for UT\"";
		final String verifyString4 = "\"WIDGET_OWNER\":\"SYSMAN\"";
		final String verifyString5 = "\"WIDGET_CREATION_TIME\":\"2014-07-22T14:48:53.048Z\"";
		final String verifyString6 = "\"WIDGET_SOURCE\":1";
		final String verifyString7 = "\"WIDGET_GROUP_NAME\":\"Category for UT\"";
		final String verifyString8 = "\"WIDGET_VIEWMODEL\":\"dependencies\\/widgets\\/iFrame\\/js\\/widget-iframe\"";
		final String verifyString9 = "\"WIDGET_KOC_NAME\":\"DF_V1_WIDGET_IFRAME\"";
		final String verifyString10 = "\"WIDGET_TEMPLATE\":\"dependencies\\/widgets\\/iFrame\\/widget-iframe.html\"";
		final String verifyString11 = "\"PROVIDER_VERSION\":\"0.1\"";
		final String verifyString12 = "\"PROVIDER_NAME\":\"DB Analytics\"";
		final String verifyString13 = "\"PROVIDER_ASSET_ROOT\":\"home\"";
		final String verifyString14 = "\"PROVIDER_VERSION\":\"Provider version for UT\"";
		final String verifyString15 = "\"PROVIDER_NAME\":\"Provider name for UT\"";
		final String verifyString16 = "\"PROVIDER_ASSET_ROOT\":\"Provider asset root for UT\"";
		final String verifyString17 = "\"WIDGET_VISUAL\":\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAL4AAACMCAIAAABNpIRsAAAYKklEQVR4AdxSBRIDIQy8\"";
		final String verifyString18 = "\"WIDGET_DEFAULT_WIDTH\":8";
		final String verifyString19 = "\"WIDGET_DEFAULT_HEIGHT\":1";
		Assert.assertNotNull(output);
		Assert.assertTrue(output.contains(verifyString1), verifyString1 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString2), verifyString2 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString3), verifyString3 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString4), verifyString4 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString5), verifyString5 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString6), verifyString6 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString7), verifyString7 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString8), verifyString8 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString9), verifyString9 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString10), verifyString10 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString11), verifyString11 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString12), verifyString12 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString13), verifyString13 + " is NOT found as expected");
		Assert.assertFalse(output.contains(verifyString17), verifyString17 + " is found NOT as expected");
		Assert.assertTrue(output.contains(verifyString18), verifyString18 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString19), verifyString19 + " is NOT found as expected");

		widget.getParameters().remove(5);
		widget.getParameters().remove(4);
		widget.getParameters().remove(3);
		widgetObj = EntityJsonUtil.getWidgetJsonObj(widget, category, null);
		Assert.assertNotNull(widgetObj);
		output = widgetObj.toString();
		Assert.assertTrue(output.contains(verifyString14), verifyString14 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString15), verifyString15 + " is NOT found as expected");
		Assert.assertTrue(output.contains(verifyString16), verifyString16 + " is NOT found as expected");
		widget.getParameters().remove(2);
		widgetObj = EntityJsonUtil.getWidgetJsonObj(widget, category, null);
		Assert.assertNull(widgetObj);
	}

	//	@Test(groups = { "s1" })
	//	public void testGetWidgetScreenshotObj() throws EMAnalyticsFwkException
	//	{
	//		JSONObject widgetScreenshotObj = EntityJsonUtil
	//				.getWidgetScreenshotJsonObj("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAL4AAACM...");
	//		String output = widgetScreenshotObj.toString();
	//
	//		final String verifyString1 = "\"screenShot\":\"data:image\\/png;base64,iVBORw0KGgoAAAANSUhEUgAAAL4AAACM...\"";
	//
	//		Assert.assertNotNull(output);
	//		Assert.assertTrue(output.contains(verifyString1), verifyString1 + " is NOT found as expected");
	//
	//		widgetScreenshotObj = EntityJsonUtil.getWidgetScreenshotJsonObj(null);
	//		output = widgetScreenshotObj.toString();
	//		Assert.assertEquals(output, "{}");
	//	}
}

/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot.ScreenshotPathGenerator;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Widget;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.WidgetManager;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;

/**
 * @author guochen
 */
public class WidgetManagerImpl extends WidgetManager
{
	private static final Logger logger = LogManager.getLogger(WidgetManagerImpl.class);

	public static final String WIDGET_API_SERVICENAME = "SavedSearch";
	public static final String WIDGET_API_VERSION = "1.0+";
	public static final String WIDGET_API_STATIC_REL = "sso.static/savedsearch.widgets";

	public static final WidgetManagerImpl instance = new WidgetManagerImpl();

	public static WidgetManagerImpl getInstance()
	{
		return instance;
	}

	private WidgetManagerImpl()
	{
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emSDK.emaas.platform.savedsearch.model.WidgetManager#getWidgetJsonStringFromWidgetList(java.util.List)
	 */
	@Override
	public String getWidgetJsonStringFromWidgetList(List<Widget> widgetList) throws EMAnalyticsFwkException
	{
		if (widgetList == null) {
			logger.debug("Json string for widget list is null as input widget list is null");
			return null;
		}
		String message = null;
		JSONArray jsonArray = new JSONArray();
		String tenantName = TenantContext.getContext().gettenantName();
		String widgetAPIUrl = getWidgetAPIUrl(tenantName);
		for (Widget widget : widgetList) {
			String ssUrl = ScreenshotPathGenerator.getInstance().generateScreenshotUrl(widgetAPIUrl, Long.valueOf(widget.getId()),
					widget.getCreatedOn(), widget.getLastModifiedOn());
			JSONObject jsonWidget = EntityJsonUtil.getWidgetJsonObj(widget, widget.getCategory(), ssUrl);
			if (jsonWidget != null) {
				jsonArray.put(jsonWidget);
			}
		}
		message = jsonArray.toString();
		logger.debug("Retrieved widget list json object for tenant {}, the json object is [{}]", tenantName, message);
		return message;
	}

	private String getWidgetAPIUrl(String tenantName)
	{
		Link lnk = RegistryLookupUtil.getServiceExternalLink(WIDGET_API_SERVICENAME, WIDGET_API_VERSION, WIDGET_API_STATIC_REL,
				tenantName);
		String url = lnk == null ? null : lnk.getHref();
		logger.debug("We get widget api url is {}", url);
		return url;
	}

}

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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot.ScreenshotPathGenerator;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EmAnalyticsProcessingException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Widget;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.WidgetManager;
import oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.jpa.JpaEntityManager;
import org.eclipse.persistence.queries.DatabaseQuery;
import org.eclipse.persistence.sessions.DatabaseRecord;
import org.eclipse.persistence.sessions.Session;

/**
 * @author guochen
 */
public class WidgetManagerImpl extends WidgetManager
{
	private static final Logger LOGGER = LogManager.getLogger(WidgetManagerImpl.class);

	public static final String WIDGET_API_SERVICENAME = "SavedSearch";
	public static final String WIDGET_API_VERSION = "1.0+";
	public static final String WIDGET_API_STATIC_REL = "sso.static/savedsearch.widgets";

	private static final String SQL_WIDGET_LIST_BY_PROVIDERS_1 = "SELECT s.SEARCH_ID,s.CREATION_DATE,s.LAST_MODIFICATION_DATE, s.NAME, s.DESCRIPTION, s.OWNER, "
			+ "s.WIDGET_SOURCE, s.WIDGET_GROUP_NAME, s.WIDGET_SCREENSHOT_HREF, s.WIDGET_ICON, s.WIDGET_KOC_NAME, s.WIDGET_VIEWMODEL, s.WIDGET_TEMPLATE, "
			+ "s.WIDGET_SUPPORT_TIME_CONTROL,s.WIDGET_LINKED_DASHBOARD,s.WIDGET_DEFAULT_WIDTH, s.WIDGET_DEFAULT_HEIGHT,s.DASHBOARD_INELIGIBLE,s.PROVIDER_NAME,s.PROVIDER_VERSION, s.PROVIDER_ASSET_ROOT, "
			+ "s.CREATION_DATE, s.LAST_MODIFICATION_DATE, c.NAME as CATOGORY_NAME, c.PROVIDER_NAME as C_PROVIDER_NAME, c.PROVIDER_VERSION as C_PROVIDER_VERSION, c.PROVIDER_ASSET_ROOT as C_PROVIDER_ASSET_ROOT, "
			+ "CASE WHEN s.owner = ? THEN 'true' ELSE 'false' END as WIDGET_EDITABLE "
			+ "FROM EMS_ANALYTICS_SEARCH s, EMS_ANALYTICS_CATEGORY c ";
	private static final String SQL_WIDGET_LIST_BY_PROVIDERS_2 = "WHERE c.provider_name in (";
	private static final String SQL_WIDGET_LIST_BY_PROVIDERS_3 = ") " + "AND s.deleted=0 AND s.IS_WIDGET=1 AND s.TENANT_ID=? ";
	private static final String SQL_WIDGET_LIST_BY_PROVIDERS_4 = "AND s.category_id=c.category_id And c.tenant_id=? ";
	private static final String SQL_WIDGET_LIST_BY_PROVIDERS_5 = "AND c.CATEGORY_ID=? ";
	private static final String SQL_WIDGET_LIST_BY_PROVIDERS_6 = "AND (s.DASHBOARD_INELIGIBLE IS NULL OR s.DASHBOARD_INELIGIBLE <>'1') ORDER BY s.SEARCH_ID ASC ";

	public static final WidgetManagerImpl INSTANCE = new WidgetManagerImpl();

	public static WidgetManagerImpl getInstance()
	{
		return INSTANCE;
	}

	private WidgetManagerImpl()
	{
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emSDK.emaas.platform.savedsearch.model.WidgetManager#getSpelledJsonFromQueryResult(java.util.List)
	 */
	@Override
	public String getSpelledJsonFromQueryResult(List<Map<String, Object>> l) throws EMAnalyticsFwkException
	{
		String tenantName = TenantContext.getContext().gettenantName();
		String widgetAPIUrl = getWidgetAPIUrl(tenantName);
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (Map<String, Object> widget : l) {
			Object widgetId = widget.get("SEARCH_ID");
			String sId = String.valueOf(widgetId);
			Long id = Long.valueOf(sId);
			//generate ssUrl
			String ssUrl = generateSSUrl(widgetAPIUrl, widget, id);
			LOGGER.debug("Screenshot URL is generated for widget id={}, url={}", id, ssUrl);
			String jsonString = EntityJsonUtil.getJsonString(widget, ssUrl);
			sb.append(jsonString);
		}
		//remove the extra comma
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		String message = sb.toString();
		LOGGER.debug("Retrieved widget list json object for tenant {}, the json object is [{}]", tenantName, message);
		return message;
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emSDK.emaas.platform.savedsearch.model.WidgetManager#getWidgetJsonStringFromWidgetList(java.util.List)
	 */
	@Override
	public String getWidgetJsonStringFromWidgetList(List<Widget> widgetList) throws EMAnalyticsFwkException
	{
		if (widgetList == null) {
			LOGGER.debug("Json string for widget list is null as input widget list is null");
			return null;
		}
		String message = null;
		JSONArray jsonArray = new JSONArray();
		String tenantName = TenantContext.getContext().gettenantName();
		String widgetAPIUrl = getWidgetAPIUrl(tenantName);
		for (Widget widget : widgetList) {
			String ssUrl = ScreenshotPathGenerator.getInstance().generateScreenshotUrl(widgetAPIUrl,
					Long.valueOf(widget.getId()), widget.getCreatedOn(), widget.getLastModifiedOn());
			JSONObject jsonWidget = EntityJsonUtil.getWidgetJsonObj(widget, widget.getCategory(), ssUrl);
			if (jsonWidget != null) {
				jsonArray.put(jsonWidget);
			}
		}
		message = jsonArray.toString();
		LOGGER.debug("Retrieved widget list json object for tenant {}, the json object is [{}]", tenantName, message);
		return message;
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emSDK.emaas.platform.savedsearch.model.WidgetManager#getWidgetListByProviderNames(java.util.List, java.lang.String)
	 */
	@Override
	@SuppressWarnings("all")
	public List<Map<String, Object>> getWidgetListByProviderNames(List<String> providerNames, String widgetGroupId)
			throws EMAnalyticsFwkException
			{
		if (providerNames == null || providerNames.isEmpty()) {
			return null;
		}

		StringBuilder sb = new StringBuilder();
		//concat get widgets SQL
		List<Object> paramList = concatWidgetsSQL(providerNames, widgetGroupId, sb);
		EntityManager em = null;
		try {
			em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
			LOGGER.debug("The SQL to query all widget is {}", sb.toString());
			long start = System.currentTimeMillis();
			Query query = em.createNativeQuery(sb.toString());
			query.setHint(QueryHints.RESULT_TYPE, ResultType.Map);
			if (widgetGroupId != null) {
				query.setParameter("widgetGroupId", Long.valueOf(widgetGroupId));
			}
			if (true) {//_logger.isDebugEnabled()) {
				try {
					Session session = em.unwrap(JpaEntityManager.class).getActiveSession();
					DatabaseQuery databaseQuery = ((EJBQueryImpl) query).getDatabaseQuery();
					databaseQuery.prepareCall(session, new DatabaseRecord());
					String sqlString = databaseQuery.getSQLString();
					//				String sqlString = databaseQuery.getTranslatedSQLString(session, new DatabaseRecord());
					LOGGER.debug("The SQL statement to retrieve all widget is: [{}]", sqlString);
				}
				catch (Exception e) {
					LOGGER.error("Error when printing debug sql: ", e);
				}
			}
			initializeQueryParams(query, paramList);

			List<Map<String, Object>> searchList = query.getResultList();
			LOGGER.debug("Querying to get all widgets takes {} ms, and retrieved {} widgets", System.currentTimeMillis() - start,
					searchList == null ? 0 : searchList.size());

			return searchList;
		}
		catch (Exception e) {
			EmAnalyticsProcessingException.processSearchPersistantException(e, null);
			LOGGER.error("Error while retrieving the list of widgets for providerNames : " + providerNames, e);
			throw new EMAnalyticsFwkException("Error while retrieving the list of widgets for providerNames : " + providerNames,
					EMAnalyticsFwkException.ERR_GENERIC, null, e);

		}
		finally {
			if (em != null) {
				em.close();
			}
		}
			}

	private List<Object> concatWidgetsSQL(List<String> providerNames, String widgetGroupId, StringBuilder sb)
	{
		List<Object> paramList = new ArrayList<Object>();
		Long tenantId = TenantContext.getContext().getTenantInternalId();
		String username = TenantContext.getContext().getUsername();
		sb.append(SQL_WIDGET_LIST_BY_PROVIDERS_1);
		paramList.add(username);
		sb.append(SQL_WIDGET_LIST_BY_PROVIDERS_2);
		for (int i = 0; i < providerNames.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("'");
			sb.append(providerNames.get(i));
			sb.append("'");
		}
		sb.append(SQL_WIDGET_LIST_BY_PROVIDERS_3);
		paramList.add(tenantId);
		sb.append(SQL_WIDGET_LIST_BY_PROVIDERS_4);
		paramList.add(tenantId);
		if (widgetGroupId != null) {
			sb.append(SQL_WIDGET_LIST_BY_PROVIDERS_5);
			paramList.add(widgetGroupId);
		}
		sb.append(SQL_WIDGET_LIST_BY_PROVIDERS_6);
		return paramList;
	}

	/*
	 * generate SS url
	 */
	private String generateSSUrl(String widgetAPIUrl, Map<String, Object> widget, Long id)
	{
		Object sCreatedOn = widget.get("CREATION_DATE");
		Object sModifiedOn = widget.get("LAST_MODIFICATION_DATE");
		Date createdOn = sCreatedOn == null ? null : Timestamp.valueOf(String.valueOf(sCreatedOn));
		Date modifiedOn = sCreatedOn == null ? null : Timestamp.valueOf(String.valueOf(sModifiedOn));
		return ScreenshotPathGenerator.getInstance().generateScreenshotUrl(widgetAPIUrl, id, createdOn, modifiedOn);

	}

	private String getWidgetAPIUrl(String tenantName)
	{
		Link lnk = RegistryLookupUtil.getServiceExternalLink(WIDGET_API_SERVICENAME, WIDGET_API_VERSION, WIDGET_API_STATIC_REL,
				tenantName);
		String url = lnk == null ? null : lnk.getHref();
		LOGGER.debug("We get widget api url is {}", url);
		return url;
	}

	private void initializeQueryParams(Query query, List<Object> paramList)
	{
		if (query == null || paramList == null) {
			return;
		}
		for (int i = 0; i < paramList.size(); i++) {
			Object value = paramList.get(i);
			query.setParameter(i + 1, value);
			LOGGER.debug("binding parameter [{}] as [{}]", i + 1, value);
		}
	}

}

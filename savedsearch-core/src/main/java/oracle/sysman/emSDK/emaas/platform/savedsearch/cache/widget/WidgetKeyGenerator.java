/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.widget;

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.DefaultKeyGenerator;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;

/**
 * @author guochen
 */
public class WidgetKeyGenerator extends DefaultKeyGenerator
{
	/**
	 * Generate and constructs a RefreshableWidgetKeys object
	 *
	 * @param tenant
	 * @param widgetGroupId
	 * @param includeDashboardIneligible
	 * @param keys
	 * @return
	 */
	public Object generate(Tenant tenant, String widgetGroupId, Boolean includeDashboardIneligible, String userName)
	{
		return new RefreshableWidgetKeys(tenant, widgetGroupId, includeDashboardIneligible, userName);
	}
}

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

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.DefaultKey;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;

/**
 * @author guochen
 */
public class RefreshableWidgetKeys extends DefaultKey
{
	/**
	 *
	 */
	private static final long serialVersionUID = -940974256820518986L;

	private final String widgetGroupId;
	private final Boolean includeDashboardIneligible;

	public RefreshableWidgetKeys(Tenant tenant, String widgetGroupId, Boolean includeDashboardIneligible, String userName)
	{
		super(tenant, new Object[] { userName });
		this.widgetGroupId = widgetGroupId;
		this.includeDashboardIneligible = includeDashboardIneligible;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (!super.equals(obj)) {
			return false;
		}
		else {
			RefreshableWidgetKeys other = (RefreshableWidgetKeys) obj;
			return (widgetGroupId == null ? other.widgetGroupId == null : widgetGroupId.equals(other.widgetGroupId))
					&& (includeDashboardIneligible == null ? other.includeDashboardIneligible == null
							: includeDashboardIneligible.equals(other.includeDashboardIneligible));
		}
	}

	/**
	 * @return the includeDashboardIneligible
	 */
	public Boolean getIncludeDashboardIneligible()
	{
		return includeDashboardIneligible;
	}

	public String getUserName()
	{
		Object[] params = getParams();
		if (params == null || params.length < 1) {
			return null;
		}
		Object name = params[0];
		if (!(name instanceof String)) {
			return null;
		}
		return (String) params[0];
	}

	/**
	 * @return the widgetGroupId
	 */
	public String getWidgetGroupId()
	{
		return widgetGroupId;
	}

	@Override
	public int hashCode()
	{
		int hash = super.hashCode();
		hash = hash * 31 + (widgetGroupId == null ? 0 : widgetGroupId.hashCode() * 31);
		hash = hash * 31 + (includeDashboardIneligible == null ? 0 : includeDashboardIneligible.hashCode() * 31);
		return hash;
	}
}

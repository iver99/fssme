/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */
 
package oracle.sysman.emaas.platform.savedsearch.model;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
/**
 * @author chehao
 *
 */
public class AnalyticsSearchModel
{
	@JsonProperty("WIDGET_UNIQUE_ID")
	private Long id;
	@JsonProperty("WIDGET_NAME")
	private String name;
	@JsonProperty("WIDGET_DESCRIPTION")
	private String description;
	@JsonProperty("WIDGET_OWNER")
	private String owner;
	@JsonProperty("WIDGET_CREATION_TIME")
	private Date creationDate;
	@JsonProperty("WIDGET_SOURCE")
	private Long widgetSource;
	@JsonProperty("WIDGET_GROUP_NAME")
	private String widgetGroupName;
	@JsonProperty("WIDGET_SCREENSHOT_HREF")
	private String widgetScreenshotHref;
	@JsonProperty("WIDGET_SUPPORT_TIME_CONTROL")
	private String widgetSupportTimeControl;
	@JsonProperty("WIDGET_KOC_NAME")
	private String widgetKocName;
	@JsonProperty("WIDGET_TEMPLATE")
	private String widgetTemplate;
	@JsonProperty("WIDGET_VIEWMODEL")
	private String widgetViewModel;
	@JsonProperty("PROVIDER_NAME")
	private String providerName;
	@JsonProperty("PROVIDER_VERSION")
	private String providerVersion;
	@JsonProperty("PROVIDER_ASSET_ROOT")
	private String providerAssetRoot;
	@JsonProperty("WIDGET_DEFAULT_HEIGHT")
	private Long widgetDefaultHeight;
	@JsonProperty("DASHBOARD_INELIGIBLE")
	private String dashboardIneligible;
	@JsonProperty("WIDGET_LINKED_DASHBOARD")
	private Long widgetLinkedDashboard;
	@JsonProperty("WIDGET_DEFAULT_WIDTH")
	private Long widgetDefaultWidth;
	@JsonProperty("WIDGET_EDITABLE")
	private String widgetEditable;

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return the owner
	 */
	public String getOwner()
	{
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return the widgetSource
	 */
	public Long getWidgetSource()
	{
		return widgetSource;
	}

	/**
	 * @param widgetSource
	 *            the widgetSource to set
	 */
	public void setWidgetSource(Long widgetSource)
	{
		this.widgetSource = widgetSource;
	}

	/**
	 * @return the widgetGroupName
	 */
	public String getWidgetGroupName()
	{
		return widgetGroupName;
	}

	/**
	 * @param widgetGroupName
	 *            the widgetGroupName to set
	 */
	public void setWidgetGroupName(String widgetGroupName)
	{
		this.widgetGroupName = widgetGroupName;
	}

	/**
	 * @return the widgetScreenshotHref
	 */
	public String getWidgetScreenshotHref()
	{
		return widgetScreenshotHref;
	}

	/**
	 * @param widgetScreenshotHref
	 *            the widgetScreenshotHref to set
	 */
	public void setWidgetScreenshotHref(String widgetScreenshotHref)
	{
		this.widgetScreenshotHref = widgetScreenshotHref;
	}

	/**
	 * @return the widgetSupportTimeControl
	 */
	public String getWidgetSupportTimeControl()
	{
		return widgetSupportTimeControl;
	}

	/**
	 * @param widgetSupportTimeControl
	 *            the widgetSupportTimeControl to set
	 */
	public void setWidgetSupportTimeControl(String widgetSupportTimeControl)
	{
		this.widgetSupportTimeControl = widgetSupportTimeControl;
	}

	/**
	 * @return the widgetKocName
	 */
	public String getWidgetKocName()
	{
		return widgetKocName;
	}

	/**
	 * @param widgetKocName
	 *            the widgetKocName to set
	 */
	public void setWidgetKocName(String widgetKocName)
	{
		this.widgetKocName = widgetKocName;
	}

	/**
	 * @return the widgetTemplate
	 */
	public String getWidgetTemplate()
	{
		return widgetTemplate;
	}

	/**
	 * @param widgetTemplate
	 *            the widgetTemplate to set
	 */
	public void setWidgetTemplate(String widgetTemplate)
	{
		this.widgetTemplate = widgetTemplate;
	}

	/**
	 * @return the widgetViewModel
	 */
	public String getWidgetViewModel()
	{
		return widgetViewModel;
	}

	/**
	 * @param widgetViewModel
	 *            the widgetViewModel to set
	 */
	public void setWidgetViewModel(String widgetViewModel)
	{
		this.widgetViewModel = widgetViewModel;
	}

	/**
	 * @return the providerName
	 */
	public String getProviderName()
	{
		return providerName;
	}

	/**
	 * @param providerName
	 *            the providerName to set
	 */
	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	/**
	 * @return the providerVersion
	 */
	public String getProviderVersion()
	{
		return providerVersion;
	}

	/**
	 * @param providerVersion
	 *            the providerVersion to set
	 */
	public void setProviderVersion(String providerVersion)
	{
		this.providerVersion = providerVersion;
	}

	/**
	 * @return the providerAssetRoot
	 */
	public String getProviderAssetRoot()
	{
		return providerAssetRoot;
	}

	/**
	 * @param providerAssetRoot
	 *            the providerAssetRoot to set
	 */
	public void setProviderAssetRoot(String providerAssetRoot)
	{
		this.providerAssetRoot = providerAssetRoot;
	}

	/**
	 * @return the widgetDefaultHeight
	 */
	public Long getWidgetDefaultHeight()
	{
		return widgetDefaultHeight;
	}

	/**
	 * @param widgetDefaultHeight
	 *            the widgetDefaultHeight to set
	 */
	public void setWidgetDefaultHeight(Long widgetDefaultHeight)
	{
		this.widgetDefaultHeight = widgetDefaultHeight;
	}

	/**
	 * @return the dashboardIneligible
	 */
	public String getDashboardIneligible()
	{
		return dashboardIneligible;
	}

	/**
	 * @param dashboardIneligible
	 *            the dashboardIneligible to set
	 */
	public void setDashboardIneligible(String dashboardIneligible)
	{
		this.dashboardIneligible = dashboardIneligible;
	}

	/**
	 * @return the widgetLinkedDashboard
	 */
	public Long getWidgetLinkedDashboard()
	{
		return widgetLinkedDashboard;
	}

	/**
	 * @param widgetLinkedDashboard
	 *            the widgetLinkedDashboard to set
	 */
	public void setWidgetLinkedDashboard(Long widgetLinkedDashboard)
	{
		this.widgetLinkedDashboard = widgetLinkedDashboard;
	}

	/**
	 * @return the widgetDefaultWidth
	 */
	public Long getWidgetDefaultWidth()
	{
		return widgetDefaultWidth;
	}

	/**
	 * @param widgetDefaultWidth
	 *            the widgetDefaultWidth to set
	 */
	public void setWidgetDefaultWidth(Long widgetDefaultWidth)
	{
		this.widgetDefaultWidth = widgetDefaultWidth;
	}

	/**
	 * @return the widgetEditable
	 */
	public String getWidgetEditable()
	{
		return widgetEditable;
	}

	/**
	 * @param widgetEditable
	 *            the widgetEditable to set
	 */
	public void setWidgetEditable(String widgetEditable)
	{
		this.widgetEditable = widgetEditable;
	}


}

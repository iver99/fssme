package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

/* $Header: emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/emSDK/core/emanalytics/api/DashboardComponent.java /st_emgc_pt-13.1mstr/1 2013/12/27 04:37:30 saurgarg Exp $ */

/* Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.*/

/*
   DESCRIPTION
    <short description of component this file declares/defines>

   PRIVATE CLASSES
    <list of private classes defined - with one-line descriptions>

   NOTES
    <other useful comments, qualifications, etc.>

   MODIFIED    (MM/DD/YY)
    saurgarg    11/19/13 - Creation
 */

import java.util.Date;
import java.util.List;

/**
 * The interface <code>DashboardComponent</code> represents a component INSTANCE which can be used in Em Analytics dashboard
 * creation.
 * 
 * @version $Header:
 *          emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/emSDK/core/emanalytics/api/DashboardComponent.java
 *          /st_emgc_pt-13.1mstr/1 2013/12/27 04:37:30 saurgarg Exp $
 * @author saurgarg
 * @since 13.1.0.0
 */
public interface DashboardComponent
{
	/**
	 * returns the unique identofier for the search.
	 * 
	 * @return identifier
	 */
	public Integer getId();

	/**
	 * Returns the name/internal-name for the search
	 * 
	 * @return internal-name
	 */
	public String getName();

	/**
	 * Returns the localized display name for the search (if localized name available, user specified name otherwise).
	 * 
	 * @return localized display name
	 */
	public String getDisplayName();

	/**
	 * Returns the localized description for the search (if localized description available, user specified description
	 * otherwise).
	 * 
	 * @return localized description
	 */
	public String getDescription();

	/**
	 * Returns the id of the category which this search belongs to.
	 * 
	 * @see oracle.sysman.emSDK.core.emanalytics.api.ComponentCategory
	 * @return category
	 */
	public Integer getCategoryId();

	/**
	 * returns the owner of search.
	 * 
	 * @return owner
	 */
	public String getOwner();

	/**
	 * Returns the creation date for search.
	 * 
	 * @return creation date
	 */
	public Date getCreationDate();

	/**
	 * Returns the user who last modified the search.
	 * 
	 * @return last modified by user
	 */
	public String getLastModifiedBy();

	/**
	 * Returns the last modification date.
	 * 
	 * @return last modification date
	 */
	public Date getLastModificationDate();

	//searchables

	/**
	 * Returns the list of named parameters defined for the component INSTANCE.
	 * 
	 * @return list of parameters
	 */
	public List<? extends Parameter> getParameters();

}

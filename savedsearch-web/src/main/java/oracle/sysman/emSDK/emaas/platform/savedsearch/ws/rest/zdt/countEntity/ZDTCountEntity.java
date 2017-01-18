/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.countEntity;

/**
 * @author pingwu
 */
public class ZDTCountEntity
{
	private Long countOfCategory;
	private Long countOfFolders;
	private Long countOfSearch;

	public ZDTCountEntity()
	{

	}

	/**
	 * @param countOfCategory
	 * @param countOfFolders
	 * @param countOfSearch
	 */
	public ZDTCountEntity(Long countOfCategory, Long countOfFolders, Long countOfSearch)
	{
		super();
		this.countOfCategory = countOfCategory;
		this.countOfFolders = countOfFolders;
		this.countOfSearch = countOfSearch;
	}

	/**
	 * @return the countOfCategory
	 */
	public Long getCountOfCategory()
	{
		return countOfCategory;
	}

	/**
	 * @return the countOfFolders
	 */
	public Long getCountOfFolders()
	{
		return countOfFolders;
	}

	/**
	 * @return the countOfSearch
	 */
	public Long getCountOfSearch()
	{
		return countOfSearch;
	}

	/**
	 * @param countOfCategory
	 *            the countOfCategory to set
	 */
	public void setCountOfCategory(Long countOfCategory)
	{
		this.countOfCategory = countOfCategory;
	}

	/**
	 * @param countOfFolders
	 *            the countOfFolders to set
	 */
	public void setCountOfFolders(Long countOfFolders)
	{
		this.countOfFolders = countOfFolders;
	}

	/**
	 * @param countOfSearch
	 *            the countOfSearch to set
	 */
	public void setCountOfSearch(Long countOfSearch)
	{
		this.countOfSearch = countOfSearch;
	}

}

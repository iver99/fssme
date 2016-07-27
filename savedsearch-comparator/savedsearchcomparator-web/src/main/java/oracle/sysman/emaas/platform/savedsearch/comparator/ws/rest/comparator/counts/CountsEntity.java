/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.counts;

/**
 * @author pingwu
 */
public class CountsEntity
{
	private Long countOfCategory;
	private Long countOfFolder;
	private Long countOfSearch;

	public CountsEntity()
	{
	}

	public CountsEntity(Long countOfCategory, Long countOfFolder, Long countOfSearch)
	{
		this.countOfCategory = countOfCategory;
		this.countOfFolder = countOfFolder;
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
	 * @return the countOfFolder
	 */
	public Long getCountOfFolder()
	{
		return countOfFolder;
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
	public void setcountOfCategory(Long countOfCategory)
	{
		this.countOfCategory = countOfCategory;
	}

	/**
	 * @param countOfFolder
	 *            the countOfFolder to set
	 */
	public void setcountOfFolder(Long countOfFolder)
	{
		this.countOfFolder = countOfFolder;
	}

	/**
	 * @param countOfSearch
	 *            the countOfSearch to set
	 */
	public void setcountOfSearch(Long countOfSearch)
	{
		this.countOfSearch = countOfSearch;
	}
}

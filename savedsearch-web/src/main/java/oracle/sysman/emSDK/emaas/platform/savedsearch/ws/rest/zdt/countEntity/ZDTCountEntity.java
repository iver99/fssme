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
//	private Long countOfCategory;
	private Long countOfFolders;
	private Long countOfSearch;
//	private Long countOfCategoryPrams;
	private Long countOfSearchParams;

	public ZDTCountEntity()
	{

	}

	/**
	 * @param countOfFolders
	 * @param countOfSearch
	 */
	public ZDTCountEntity(Long countOfFolders, Long countOfSearch, Long countOfSearchParams)
	{
		super();
		this.countOfFolders = countOfFolders;
		this.countOfSearch = countOfSearch;
		this.countOfSearchParams = countOfSearchParams;
	}

	public Long getCountOfFolders() {
		return countOfFolders;
	}

	public void setCountOfFolders(Long countOfFolders) {
		this.countOfFolders = countOfFolders;
	}

	public Long getCountOfSearch() {
		return countOfSearch;
	}

	public void setCountOfSearch(Long countOfSearch) {
		this.countOfSearch = countOfSearch;
	}

	public Long getCountOfSearchParams() {
		return countOfSearchParams;
	}

	public void setCountOfSearchParams(Long countOfSearchParams) {
		this.countOfSearchParams = countOfSearchParams;
	}	

}

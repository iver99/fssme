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
	private Long countOfFolders;
	private Long countOfSearch;
	private Long countOfCategoryPrams;
	private Long countOfSearchParams;

	public CountsEntity()
	{
	}

	public CountsEntity(Long countOfCategory, Long countOfFolders, Long countOfSearch, Long countOfCategoryPrams, Long countOfSearchParams)
	{
		this.countOfCategory = countOfCategory;
		this.countOfFolders = countOfFolders;
		this.countOfSearch = countOfSearch;
		this.countOfCategoryPrams = countOfCategoryPrams;
		this.countOfSearchParams = countOfSearchParams;
	}

	public Long getCountOfCategory() {
		return countOfCategory;
	}

	public void setCountOfCategory(Long countOfCategory) {
		this.countOfCategory = countOfCategory;
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

	public Long getCountOfCategoryPrams() {
		return countOfCategoryPrams;
	}

	public void setCountOfCategoryPrams(Long countOfCategoryPrams) {
		this.countOfCategoryPrams = countOfCategoryPrams;
	}

	public Long getCountOfSearchParams() {
		return countOfSearchParams;
	}

	public void setCountOfSearchParams(Long countOfSearchParams) {
		this.countOfSearchParams = countOfSearchParams;
	}
	
	

	
}

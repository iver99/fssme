/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author pingwu
 */
public class ZDTTableRowEntity
{

	@JsonProperty("EMS_ANALYTICS_CATEGORY")
	private List<SavedSearchCategoryRowEntity> savedSearchCategory;

	@JsonProperty("EMS_ANALYTICS_CATEGORY_PARAMS")
	private List<SavedSearchCategoryParamRowEntity> savedSearchCategoryParams;

	@JsonProperty("EMS_ANALYTICS_FOLDERS")
	private List<SavedSearchFolderRowEntity> savedSearchFoldersy;

	@JsonProperty("EMS_ANALYTICS_SCHEMA_VER_SSF")
	private List<SavedSearchSchemaVerRowEntity> savedSearchSchemaVer;

	@JsonProperty("EMS_ANALYTICS_SEARCH_PARAMS")
	private List<SavedSearchSearchParamRowEntity> savedSearchSearchParams;

	@JsonProperty("EMS_ANALYTICS_SEARCH")
	private List<SavedSearchSearchRowEntity> savedSearchSearch;
	
	

	public ZDTTableRowEntity(
			List<SavedSearchCategoryRowEntity> savedSearchCategory,
			List<SavedSearchCategoryParamRowEntity> savedSearchCategoryParams,
			List<SavedSearchFolderRowEntity> savedSearchFoldersy,
			List<SavedSearchSchemaVerRowEntity> savedSearchSchemaVer,
			List<SavedSearchSearchParamRowEntity> savedSearchSearchParams,
			List<SavedSearchSearchRowEntity> savedSearchSearch) {
		super();
		this.savedSearchCategory = savedSearchCategory;
		this.savedSearchCategoryParams = savedSearchCategoryParams;
		this.savedSearchFoldersy = savedSearchFoldersy;
		this.savedSearchSchemaVer = savedSearchSchemaVer;
		this.savedSearchSearchParams = savedSearchSearchParams;
		this.savedSearchSearch = savedSearchSearch;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ZDTTableRowEntity other = (ZDTTableRowEntity) obj;
		if (savedSearchCategory == null) {
			if (other.savedSearchCategory != null) {
				return false;
			}
		}
		else if (!savedSearchCategory.equals(other.savedSearchCategory)) {
			return false;
		}
		if (savedSearchCategoryParams == null) {
			if (other.savedSearchCategoryParams != null) {
				return false;
			}
		}
		else if (!savedSearchCategoryParams.equals(other.savedSearchCategoryParams)) {
			return false;
		}
		if (savedSearchFoldersy == null) {
			if (other.savedSearchFoldersy != null) {
				return false;
			}
		}
		else if (!savedSearchFoldersy.equals(other.savedSearchFoldersy)) {
			return false;
		}
		
		if (savedSearchSchemaVer == null) {
			if (other.savedSearchSchemaVer != null) {
				return false;
			}
		}
		else if (!savedSearchSchemaVer.equals(other.savedSearchSchemaVer)) {
			return false;
		}
		if (savedSearchSearch == null) {
			if (other.savedSearchSearch != null) {
				return false;
			}
		}
		else if (!savedSearchSearch.equals(other.savedSearchSearch)) {
			return false;
		}
		if (savedSearchSearchParams == null) {
			if (other.savedSearchSearchParams != null) {
				return false;
			}
		}
		else if (!savedSearchSearchParams.equals(other.savedSearchSearchParams)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the savedSearchCategory
	 */
	public List<SavedSearchCategoryRowEntity> getSavedSearchCategory()
	{
		return savedSearchCategory;
	}

	/**
	 * @return the savedSearchCategoryParams
	 */
	public List<SavedSearchCategoryParamRowEntity> getSavedSearchCategoryParams()
	{
		return savedSearchCategoryParams;
	}

	/**
	 * @return the savedSearchFoldersy
	 */
	public List<SavedSearchFolderRowEntity> getSavedSearchFoldersy()
	{
		return savedSearchFoldersy;
	}

	
	/**
	 * @return the savedSearchSchemaVer
	 */
	public List<SavedSearchSchemaVerRowEntity> getSavedSearchSchemaVer()
	{
		return savedSearchSchemaVer;
	}

	/**
	 * @return the savedSearchSearch
	 */
	public List<SavedSearchSearchRowEntity> getSavedSearchSearch()
	{
		return savedSearchSearch;
	}

	/**
	 * @return the savedSearchSearchParams
	 */
	public List<SavedSearchSearchParamRowEntity> getSavedSearchSearchParams()
	{
		return savedSearchSearchParams;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (savedSearchCategory == null ? 0 : savedSearchCategory.hashCode());
		result = prime * result + (savedSearchCategoryParams == null ? 0 : savedSearchCategoryParams.hashCode());
		result = prime * result + (savedSearchFoldersy == null ? 0 : savedSearchFoldersy.hashCode());
		result = prime * result + (savedSearchSchemaVer == null ? 0 : savedSearchSchemaVer.hashCode());
		result = prime * result + (savedSearchSearch == null ? 0 : savedSearchSearch.hashCode());
		result = prime * result + (savedSearchSearchParams == null ? 0 : savedSearchSearchParams.hashCode());
		return result;
	}

	/**
	 * @param savedSearchCategory
	 *            the savedSearchCategory to set
	 */
	public void setSavedSearchCategory(List<SavedSearchCategoryRowEntity> savedSearchCategory)
	{
		this.savedSearchCategory = savedSearchCategory;
	}

	/**
	 * @param savedSearchCategoryParams
	 *            the savedSearchCategoryParams to set
	 */
	public void setSavedSearchCategoryParams(List<SavedSearchCategoryParamRowEntity> savedSearchCategoryParams)
	{
		this.savedSearchCategoryParams = savedSearchCategoryParams;
	}

	/**
	 * @param savedSearchFoldersy
	 *            the savedSearchFoldersy to set
	 */
	public void setSavedSearchFoldersy(List<SavedSearchFolderRowEntity> savedSearchFoldersy)
	{
		this.savedSearchFoldersy = savedSearchFoldersy;
	}

	/**
	 * @param savedSearchSchemaVer
	 *            the savedSearchSchemaVer to set
	 */
	public void setSavedSearchSchemaVer(List<SavedSearchSchemaVerRowEntity> savedSearchSchemaVer)
	{
		this.savedSearchSchemaVer = savedSearchSchemaVer;
	}

	/**
	 * @param savedSearchSearch
	 *            the savedSearchSearch to set
	 */
	public void setSavedSearchSearch(List<SavedSearchSearchRowEntity> savedSearchSearch)
	{
		this.savedSearchSearch = savedSearchSearch;
	}

	/**
	 * @param savedSearchSearchParams
	 *            the savedSearchSearchParams to set
	 */
	public void setSavedSearchSearchParams(List<SavedSearchSearchParamRowEntity> savedSearchSearchParams)
	{
		this.savedSearchSearchParams = savedSearchSearchParams;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ZDTTableRowEntity [savedSearchCategory=" + savedSearchCategory + ", savedSearchCategoryParams="
				+ savedSearchCategoryParams + ", savedSearchFoldersy=" + savedSearchFoldersy + ", savedSearchSchemaVer=" + savedSearchSchemaVer + ", savedSearchSearchParams="
				+ savedSearchSearchParams + ", savedSearchSearch=" + savedSearchSearch + "]";
	}

}

package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.util.List;

/**
 * The interface <code>Search</code> represents a saved search in EM Analytics.
 *
 * @version $Header:
 *          emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/emSDK/core/emanalytics/api/search/Search.java
 *          /st_emgc_pt-13.1mstr/2 2014/02/03 02:51:00 saurgarg Exp $
 * @author saurgarg
 * @since 13.1.0.0
 */
public interface Search extends SearchSummary
{

	/**
	 * Returns the identifier of the folder which contains the search.
	 *
	 * @see oracle.sysman.emSDK.core.emanalytics.api.Folder
	 * @return id of the folder containing search
	 */
	@Override
	public Integer getFolderId();

	/**
	 * Returns <code>true</code> if the search is a widget, <code>false</code> otherwise.
	 *
	 * @return if the search is a widget
	 */
	public boolean getIsWidget();

	/**
	 * Returns the metadata string for the search.
	 *
	 * @return metadata string for the search
	 */
	public String getMetadata();

	/**
	 * Returns the parameters defined for this search. <code>null</code> if no parameters were defined.
	 *
	 * @see SearchParameter
	 * @return parameters defined for this search
	 */
	public List<SearchParameter> getParameters();

	/**
	 * Returns the query string for the search.
	 *
	 * @return query string
	 */
	public String getQueryStr();

	/**
	 * Returns <code>true</code> if the search is locked for any edits, <code>false</code> otherwise.
	 *
	 * @return if the search is locked for any edits
	 */
	public boolean isLocked();

	/**
	 * Returns <code>true</code> if the search is marked to be hidden from UI.
	 *
	 * @return if the search is marked to be hidden from UI
	 */
	public boolean isUiHidden();

	/**
	 * Sets the category for the search.
	 *
	 * @see oracle.sysman.emSDK.core.emanalytics.api.ComponentCategory
	 * @param categoryId
	 *            identifier of the category
	 */
	public void setCategoryId(Integer categoryId);

	/**
	 * Sets the description for the search.
	 *
	 * @param description
	 *            description
	 */
	public void setDescription(String description);

	/**
	 * Sets the containing folder for the search.
	 *
	 * @see oracle.sysman.emSDK.core.emanalytics.api.Folder
	 * @param folderId
	 *            identifier of the folder
	 */
	public void setFolderId(Integer folderId);

	/**
	 * Set the search object to be marked as a widget.
	 *
	 * @param isWidget
	 *            Flag to mark a search as a widget
	 */
	public void setIsWidget(boolean isWidget);

	public void setLastAccessDate(java.util.Date value);

	/**
	 * Sets the locking behavior for the search. If set to <code>true</code>, search can not be editted by non-owning user.
	 *
	 * @param locked
	 *            locking behavior to be set
	 */
	public void setLocked(boolean locked);

	/**
	 * Sets the metadata string for the search.
	 *
	 * @param metadata
	 *            metadata string
	 */
	public void setMetadata(String metadata);

	/**
	 * Sets the name for the Search.
	 *
	 * @param name
	 *            name
	 */
	public void setName(String name);

	/**
	 * Sets the parameters for the search.
	 *
	 * @see SearchParameter
	 * @param parameters
	 *            parameters to be set
	 */
	public void setParameters(List<SearchParameter> parameters);

	/**
	 * Sets the query string for the search.
	 *
	 * @param queryStr
	 *            query string
	 */
	public void setQueryStr(String queryStr);

	/**
	 * Sets the tags for the search.
	 *
	 * @param tags
	 *            tags
	 */
	public void setTags(String[] tags);

	/**
	 * Sets the search object to be marked as hidden from the UI.
	 *
	 * @param uiHidden
	 *            hidden behavior of search
	 */
	public void setUiHidden(boolean uiHidden);
}

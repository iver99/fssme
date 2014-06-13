package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.util.Date;

/**
 * The interface <code>SearchSummary</code> represents a summarized representation of <code>Search</code>.
 * 
 * @version $Header:
 *          emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/emSDK/core/emanalytics/api/search/SearchSummary
 *          .java /st_emgc_pt-13.1mstr/1 2013/12/27 04:37:30 saurgarg Exp $
 * @author saurgarg
 * @since 13.1.0.0
 */
public interface SearchSummary
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
	 * Returns the identifier of the containing forlder.
	 * 
	 * @return containing folder id
	 */
	public Integer getFolderId();

	/**
	 * Returns the owner of search.
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

	/**
	 * Returns the tags for search.
	 * 
	 * @return tags
	 */
	public String[] getTags();
}

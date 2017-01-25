package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importData;

import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchParamRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchRowEntity;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 
 * @author pingwu
 *
 */
public class ImportRowEntity {
	
	@JsonProperty("EMS_ANALYTICS_SEARCH_PARAMS")
	private List<SavedSearchSearchParamRowEntity> savedSearchSearchParams;

	@JsonProperty("EMS_ANALYTICS_SEARCH")
	private List<SavedSearchSearchRowEntity> savedSearchSearch;

	public ImportRowEntity(
			List<SavedSearchSearchParamRowEntity> savedSearchSearchParams,
			List<SavedSearchSearchRowEntity> savedSearchSearch) {
		super();
		this.savedSearchSearchParams = savedSearchSearchParams;
		this.savedSearchSearch = savedSearchSearch;
	}

	public ImportRowEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public List<SavedSearchSearchParamRowEntity> getSavedSearchSearchParams() {
		return savedSearchSearchParams;
	}

	public List<SavedSearchSearchRowEntity> getSavedSearchSearch() {
		return savedSearchSearch;
	}

	public void setSavedSearchSearchParams(
			List<SavedSearchSearchParamRowEntity> savedSearchSearchParams) {
		this.savedSearchSearchParams = savedSearchSearchParams;
	}

	public void setSavedSearchSearch(
			List<SavedSearchSearchRowEntity> savedSearchSearch) {
		this.savedSearchSearch = savedSearchSearch;
	}
	
	
	

}

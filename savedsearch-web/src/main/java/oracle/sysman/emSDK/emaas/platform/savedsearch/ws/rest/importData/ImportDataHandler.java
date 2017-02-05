package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importData;

import java.math.BigInteger;
import java.util.List;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchParamRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.zdt.rowsEntity.SavedSearchSearchRowEntity;
import oracle.sysman.emSDK.emaas.platform.savedsearch.zdt.DataManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ImportDataHandler {
	
	private static final Logger LOGGER = LogManager.getLogger(ImportDataHandler.class);
	
	public void saveImportedData(ImportRowEntity rowEntity) {
		if (rowEntity != null) {
			List<SavedSearchSearchParamRowEntity> searchParamsRows = rowEntity.getSavedSearchSearchParams();
			List<SavedSearchSearchRowEntity> searchRows = rowEntity.getSavedSearchSearch();
			saveSearchTableRows(searchRows);
			saveSearchParamsTableRows(searchParamsRows);
		} else {
			LOGGER.debug("imported row entity is null; No need to do anything");
		}
	}
	
	private void saveSearchParamsTableRows(List<SavedSearchSearchParamRowEntity> rows)
	{
		if (rows == null) {
			LOGGER.debug("input is null,no need to save");
			return;
		}
		LOGGER.debug("Save data to table EMS_ANALYTICS_SEARCH_PARAMS");
		SearchManager searchManager = SearchManager.getInstance();
		for (SavedSearchSearchParamRowEntity e : rows) {
			searchManager.saveSearchParamData(e.getSearchId(), e.getName(), e.getParamAttributes(),
					e.getParamType(), e.getParamValueStr(), e.getParamValueClob(), e.getTenantId(), e.getCreationDate(),
					e.getLastModificationDate(),e.getDeleted());
		}
		LOGGER.debug("Finished to save table EMS_ANALYTICS__SEARCH_PARAMS table");
	}

	private void saveSearchTableRows(List<SavedSearchSearchRowEntity> rows)
	{
		if (rows == null) {
			LOGGER.debug("input is null,no sync action is needed");
			return;
		}
		LOGGER.debug("Save data to table EMS_ANALYTICS_SEARCH");
		SearchManager searchManager = SearchManager.getInstance();
		for (SavedSearchSearchRowEntity e : rows) {
			searchManager.saveSearchData(e.getSearchId(), e.getName(), e.getOwner(),
					e.getCreationDate(), e.getLastModificationDate(), e.getLastModifiedBy(), e.getDescription(), e.getFolderId(),
					e.getCategoryId(),e.getSystemSearch(),e.getIsLocked(), e.getMetadataClob(),
					e.getSearchDisplayStr(), e.getUiHidden(), e.getDeleted(), e.getIsWidget(), e.getTenantId(),
					e.getNameWidgetSource(), e.getWidgetGroupName(), e.getWidgetScreenshotHref(), e.getWidgetIcon(),
					e.getWidgetKocName(), e.getWidgetViewModel(), e.getWidgetTemplate(), e.getWidgetSupportTimeControl(),
					e.getWidgetLinkedDashboard(), e.getWidgetDefaulWidth(), e.getWidgetDefaultHeight(),
					e.getDashboardIneligible(), e.getProviderName(), e.getProviderVersion(), e.getProviderAssetRoot());

		}
		
		LOGGER.debug("Finished to save table EMS_ANALYTICS__SEARCH table");
	}

}

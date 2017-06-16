package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.thread;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsDatabaseUnavailException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataRetriever;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataStorer;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * Created by xiadai on 2017/6/14.
 */
public class OobRefreshRunnable extends MetadataRefreshRunnable{
    private static final Logger LOGGER = LogManager.getLogger(OobRefreshRunnable.class);
    @Override
    public void run(){
        LOGGER.error("I'm running");
        if(serviceName == null || serviceName.isEmpty()) {
            LOGGER.error("OobRefreshRunnable: there is no service name!");
            return;
        }
        List<SearchImpl> oobWidgetList;
        LOGGER.error("Get Widget List From integrator.");
        try {
            if (!DependencyStatus.getInstance().isDatabaseUp()) {
                throw new EMAnalyticsDatabaseUnavailException();
            }
            oobWidgetList = new MetaDataRetriever().getOobWidgetListByServiceName(serviceName);
            LOGGER.error("Store Widget List.");
            TenantContext.setContext(new TenantInfo(SearchManagerImpl.DEFAULT_CURRENT_USER, SearchManagerImpl.DEFAULT_TENANT_ID));
            MetaDataStorer.storeOobWidget(oobWidgetList);
            LOGGER.error("Refresh the OOB Widgets successfully.");
        } catch (EMAnalyticsFwkException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }
}

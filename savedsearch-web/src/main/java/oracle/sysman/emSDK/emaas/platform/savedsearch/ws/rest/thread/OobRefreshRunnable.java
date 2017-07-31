package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.thread;

import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsDatabaseUnavailException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataRetriever;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataStorer;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by xiadai on 2017/6/14.
 */
public class OobRefreshRunnable extends MetadataRefreshRunnable{
    private static final Logger LOGGER = LogManager.getLogger(OobRefreshRunnable.class);
    
    @Override
    public void run(){
        if(serviceName == null || serviceName.isEmpty()) {
            LOGGER.error("OobRefreshRunnable: there is no service name!");
            return;
        }
        List<SearchImpl> oobWidgetList;
        try {
            if (!DependencyStatus.getInstance().isDatabaseUp()) {
                throw new EMAnalyticsDatabaseUnavailException();
            }
            int loopNum = 0;  // how many times SSF has already tried to fetch the OOB
            while(!isServiceAvailable(serviceName, "1.0+") && (loopNum < MAX_LOOP_NUM)) {
                try {
                    LOGGER.warn("{} time failed to fecth OOB widgets from {}", loopNum++, serviceName);
                    Thread.sleep(INTERVAL_TIME); // 60s
                } catch (InterruptedException e) {
                    LOGGER.error(e.getLocalizedMessage(), e);
                }
            }
            MetaDataRetriever metaDataRetriever = new MetaDataRetriever();
            oobWidgetList = metaDataRetriever.getOobWidgetListByServiceName(serviceName);
            TenantContext.setContext(new TenantInfo(SearchManagerImpl.DEFAULT_CURRENT_USER, SearchManagerImpl.DEFAULT_TENANT_ID));
            MetaDataStorer.storeOobWidget(oobWidgetList);
        } catch (EMAnalyticsFwkException e) {
            LOGGER.error(e.getLocalizedMessage());
        }
    }
    
}

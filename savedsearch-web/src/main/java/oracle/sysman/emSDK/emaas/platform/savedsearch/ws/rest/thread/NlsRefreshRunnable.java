package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.thread;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsDatabaseUnavailException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emaas.platform.savedsearch.entity.EmsResourceBundle;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataRetriever;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataStorer;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiadai on 2017/6/14.
 */
public class NlsRefreshRunnable extends MetadataRefreshRunnable{
    private static final Logger LOGGER = LogManager.getLogger(NlsRefreshRunnable.class);
    @Override
    public void run(){
        LOGGER.error("Thread NlsRefresh running");
        if(serviceName == null || serviceName.isEmpty()) {
            LOGGER.error("NlsRefreshRunnable: there is no service name!");
            return;
        }
        List<EmsResourceBundle> emsResourceBundles;
        try {
            if (!DependencyStatus.getInstance().isDatabaseUp()) {
                throw new EMAnalyticsDatabaseUnavailException();
            }
            emsResourceBundles = new MetaDataRetriever().getResourceBundleByService(serviceName);
            MetaDataStorer.storeResourceBundle(emsResourceBundles);
        } catch (EMAnalyticsFwkException e) {
             LOGGER.error(e.getLocalizedMessage());
        }
    }
}

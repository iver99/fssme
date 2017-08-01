package oracle.sysman.emaas.platform.savedsearch.services;

import java.util.Arrays;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emSDK.emaas.platform.savedsearch.restnotify.OOBWidgetExpiredNotification;
import oracle.sysman.emaas.platform.savedsearch.entity.EmsResourceBundle;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataRetriever;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataStorer;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import weblogic.application.ApplicationLifecycleEvent;


public class MetaDataManager implements ApplicationServiceManager {
    private static final Logger LOGGER = LogManager.getLogger(MetaDataManager.class);
    private static final List<String> oobProvider = Arrays.asList(MetaDataRetriever.SERVICENAME_APM,
            MetaDataRetriever.SERVICENAME_ITA, MetaDataRetriever.SERVICENAME_LA, MetaDataRetriever.SERVICENAME_ORCHESTRATION,
            MetaDataRetriever.SERVICENAME_SECURITY_ANALYTICS, MetaDataRetriever.SERVICENAME_UDE, MetaDataRetriever.SERVICENAME_EVENT);
    @Override
    public String getName() {
        return "MetaData Services";
    }

    @Override
    public void postStart(ApplicationLifecycleEvent evt) throws Exception {
        LOGGER.debug("[Calling] MetaDataManager.post start");
        MetaDataRetriever metaDataRetriever = new MetaDataRetriever();
        List<SearchImpl> oobWidgetList;
        List<EmsResourceBundle> emsResourceBundles;
        for(String serviceName : oobProvider){
            LOGGER.info("Start to load oob widget metadata.");
            try {
                oobWidgetList =  metaDataRetriever.getOobWidgetListByServiceName(serviceName);
                LOGGER.debug("Store Widget List.");
                TenantContext.setContext(new TenantInfo(SearchManagerImpl.DEFAULT_CURRENT_USER, SearchManagerImpl.DEFAULT_TENANT_ID));
                MetaDataStorer.storeOobWidget(oobWidgetList);
                LOGGER.debug("Refresh the OOB Widgets successfully.");
            } catch (EMAnalyticsFwkException e) {
                LOGGER.error(e.getLocalizedMessage());
            }

            LOGGER.info("Start to load nls metadata");
            try {
                emsResourceBundles = metaDataRetriever.getResourceBundleByService(serviceName);
                LOGGER.debug("Store the nls meta data list");
                MetaDataStorer.storeResourceBundle(emsResourceBundles);
                LOGGER.debug("Refresh the nls metadata successfully.");
            } catch (EMAnalyticsFwkException e) {
                LOGGER.error(e.getLocalizedMessage());
            }
        }
        
        // notify DashboardAPI to clear the cache of OOB Widgets
        new OOBWidgetExpiredNotification().notify("SavedSearch");
    }

    @Override
    public void postStop(ApplicationLifecycleEvent evt) throws Exception {

    }

    @Override
    public void preStart(ApplicationLifecycleEvent evt) throws Exception {

    }

    @Override
    public void preStop(ApplicationLifecycleEvent evt) throws Exception {

    }
}

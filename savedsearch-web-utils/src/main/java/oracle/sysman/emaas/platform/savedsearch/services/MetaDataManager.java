package oracle.sysman.emaas.platform.savedsearch.services;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataRetriever;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataStorer;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weblogic.application.ApplicationLifecycleEvent;

import java.util.Arrays;
import java.util.List;


public class MetaDataManager implements ApplicationServiceManager {
    private static final Logger LOGGER = LogManager.getLogger(MetaDataManager.class);
    private static final List<String> oobProvider = Arrays.asList(MetaDataRetriever.SERVICENAME_APM,
            MetaDataRetriever.SERVICENAME_ITA, MetaDataRetriever.SERVICENAME_LA, MetaDataRetriever.SERVICENAME_ORCHESTRATION,
            MetaDataRetriever.SERVICENAME_SECURITY_ANALYTICS, MetaDataRetriever.SERVICENAME_UDE);
    @Override
    public String getName() {
        return "MetaData Services";
    }

    @Override
    public void postStart(ApplicationLifecycleEvent evt) throws Exception {
        LOGGER.debug("[Calling] MetaDataManager.post start");
        MetaDataRetriever metaDataRetriever = new MetaDataRetriever();
        List<SearchImpl> oobWidgetList;
//        List<EmsResourceBundle> emsResourceBundles;
        for(String serviceName : oobProvider){
            LOGGER.debug("Start to load oob widget metadata.");
            try {
                oobWidgetList =  metaDataRetriever.getOobWidgetListByServiceName(serviceName);
                LOGGER.debug("Store Widget List.");
                TenantContext.setContext(new TenantInfo(SearchManagerImpl.DEFAULT_CURRENT_USER, SearchManagerImpl.DEFAULT_TENANT_ID));
                MetaDataStorer.storeOobWidget(oobWidgetList);
                LOGGER.debug("Refresh the OOB Widgets successfully.");
            } catch (EMAnalyticsFwkException e) {
                LOGGER.error(e.getLocalizedMessage());
            }

//            LOGGER.error("Start to load nls metadata");
//            try {
//                emsResourceBundles = metaDataRetriever.getResourceBundleByService(serviceName);
//                LOGGER.error("Store the nls meta data list");
//                MetaDataStorer.storeResourceBundle(emsResourceBundles);
//                LOGGER.error("Refresh the nls metadata successfully.");
//            } catch (EMAnalyticsFwkException e) {
//                LOGGER.error(e.getLocalizedMessage());
//            }
        }
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

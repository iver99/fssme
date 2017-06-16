package oracle.sysman.emaas.platform.savedsearch.metadata;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ResourceBundleManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emaas.platform.savedsearch.entity.EmsResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by xiadai on 2017/5/4.
 */
public class MetaDataStorer {
    private static final Logger LOGGER = LogManager.getLogger(MetaDataStorer.class);

    public static void storeOobWidget(List<SearchImpl> oobWidgetList) throws EMAnalyticsFwkException {
        LOGGER.debug("Calling MetaStorer.storeOobWidget");
        if(oobWidgetList == null || oobWidgetList.isEmpty()){
            LOGGER.error("The oobWidget is empty");
            return;
        }
        SearchManager searchManager = SearchManager.getInstance();
        BigInteger categoryId = oobWidgetList.get(0).getCategoryId();

        try {
            List<BigInteger> searchIds = searchManager.getSearchIdsByCategory(categoryId);
            searchManager.cleanSearchesPermanentlyById(searchIds);
        } catch (EMAnalyticsFwkException e) {
            LOGGER.error("Fail into error while deleting the oob widget by categoryId: {}, {}", categoryId, e.getLocalizedMessage());
            throw e;
        }

        try{
            LOGGER.debug("Save oob widget for category {}", categoryId);
            for(Search search : oobWidgetList){
                searchManager.saveOobSearch(search);
            }
        } catch (EMAnalyticsFwkException e) {
            LOGGER.error("Fail into error while saving the oob widget by categoryId: {}, {}", categoryId, e.getLocalizedMessage());
            throw e;
        }


    }

    public static void storeResourceBundle(List<EmsResourceBundle> emsResourceBundles) {
        LOGGER.error("Calling MetaDataStorer.storeResourceBundle");
        if(emsResourceBundles == null || emsResourceBundles.isEmpty()) {
            LOGGER.error("the resource bundle is empty");
            return;
        }

        ResourceBundleManager resourceBundleManager = ResourceBundleManager.getInstance();
        String serviceName = emsResourceBundles.get(0).getServiceName();

        try {
            resourceBundleManager.cleanResourceBundleByServiceName(serviceName);
        } catch (EMAnalyticsFwkException e) {
            LOGGER.error("Fall into error while cleaning resource bundle by service name {}",e.getLocalizedMessage());
        }
        LOGGER.error("start to persist the resource bundle");
        try {
            for (EmsResourceBundle emsResourceBundle : emsResourceBundles) {
                resourceBundleManager.persistResourceBundle(emsResourceBundle);
            }
        }catch (EMAnalyticsFwkException e){
            LOGGER.error("Fall into error while saving resource bundle by service name {}",e.getLocalizedMessage());
        }
    }
}

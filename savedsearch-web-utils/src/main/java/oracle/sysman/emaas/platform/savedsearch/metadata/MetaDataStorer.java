package oracle.sysman.emaas.platform.savedsearch.metadata;

import java.math.BigInteger;
import java.util.List;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ResourceBundleManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emaas.platform.savedsearch.entity.EmsResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by xiadai on 2017/5/4.
 */
public class MetaDataStorer {
    private static final Logger LOGGER = LogManager.getLogger(MetaDataStorer.class);

    public static void storeOobWidget(List<SearchImpl> oobWidgetList) throws EMAnalyticsFwkException {
        LOGGER.debug("Calling MetaStorer.storeOobWidget");
        if(oobWidgetList == null || oobWidgetList.isEmpty()){
            LOGGER.warn("The oobWidget is empty");
            return;
        }
        SearchManager searchManager = SearchManager.getInstance();
        BigInteger categoryId = oobWidgetList.get(0).getCategoryId();

        try {
            List<BigInteger> searchIds = searchManager.getSearchIdsByCategory(categoryId);
            searchManager.storeOobWidget(searchIds, oobWidgetList);
        } catch (EMAnalyticsFwkException e) {
            LOGGER.error("Fail into error while deleting the oob widget by categoryId: {}, {}", categoryId, e.getLocalizedMessage());
            throw e;
        }


    }

    public static void storeResourceBundle(List<EmsResourceBundle> emsResourceBundles) {
        LOGGER.debug("Calling MetaDataStorer.storeResourceBundle");
        if(emsResourceBundles == null || emsResourceBundles.isEmpty()) {
            LOGGER.warn("the resource bundle is empty");
            return;
        }

        ResourceBundleManager resourceBundleManager = ResourceBundleManager.getInstance();
        String serviceName = emsResourceBundles.get(0).getServiceName();

        try {
            resourceBundleManager.storeResourceBundle(serviceName,emsResourceBundles);
        } catch (EMAnalyticsFwkException e) {
            LOGGER.error("Fall into error while cleaning resource bundle by service name {}",e.getLocalizedMessage());
        }
    }
}

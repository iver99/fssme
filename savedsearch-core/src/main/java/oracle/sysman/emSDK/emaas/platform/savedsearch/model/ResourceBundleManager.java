package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ResourceBundleManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emaas.platform.savedsearch.entity.EmsResourceBundle;

import java.util.List;

/**
 * Created by xiadai on 2017/6/14.
 */
public abstract class ResourceBundleManager {
    public static ResourceBundleManager getInstance(){return ResourceBundleManagerImpl.getInstance();}
    public abstract void storeResourceBundle(String serviceName, List<EmsResourceBundle> emsResourceBundles) throws EMAnalyticsFwkException;
}

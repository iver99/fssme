package oracle.sysman.emSDK.emaas.platform.savedsearch.exception.cache;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

/**
 * Created by chehao on 2016/11/14.
 */
public class CacheGroupNotFoundException extends CacheException{

    private static final String DASHBOARD_CACHE_GROUP_NOT_FOUND_ERROR = "DASHBOARD_CACHE_GROUP_NOT_FOUND_ERROR";

    public CacheGroupNotFoundException()
    {
        super(EMAnalyticsFwkException.DASHBOARD_CACHE_GROUP_NOT_FOUND_ERROR_CODE, DASHBOARD_CACHE_GROUP_NOT_FOUND_ERROR);
    }
}

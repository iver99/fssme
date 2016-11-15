package oracle.sysman.emSDK.emaas.platform.savedsearch.exception.cache;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

/**
 * Created by chehao on 2016/11/15.
 */
public class CacheGroupNameEmptyException extends CacheException {

    private static final String DASHBOARD_CACHE_GROUP_NAME_EMPTY_ERROR = "DASHBOARD_CACHE_GROUP_NAME_EMPTY_ERROR";

    public CacheGroupNameEmptyException()
    {
        super(EMAnalyticsFwkException.DASHBOARD_CACHE_GROUP_NAME_EMPTY_ERROR_CODE, DASHBOARD_CACHE_GROUP_NAME_EMPTY_ERROR);
    }
}

package oracle.sysman.emSDK.emaas.platform.savedsearch.exception.cache;

import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

/**
 * Created by chehao on 2017/4/5 17:20.
 */
public class CacheInconsistencyException  extends CacheException{

    private static final String CACHE_INCONSISTENCY_ERROR = "CACHE_INCONSISTENCY_ERROR";

    public CacheInconsistencyException()
    {
        super(EMAnalyticsFwkException.DASHBOARD_CACHE_INCONSISTENCY_ERROR_CODE, CACHE_INCONSISTENCY_ERROR);
    }
}

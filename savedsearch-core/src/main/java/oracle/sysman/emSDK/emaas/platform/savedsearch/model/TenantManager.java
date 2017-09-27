package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.TenantManagerImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;

public abstract class TenantManager {
    
    public static TenantManager getInstance() {
        return TenantManagerImpl.getInstance();
    }
    
    public abstract void cleanTenant(Long internalTenantId) throws EMAnalyticsFwkException;
}

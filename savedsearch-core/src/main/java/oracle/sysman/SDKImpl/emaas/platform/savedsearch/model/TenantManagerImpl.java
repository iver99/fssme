package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TenantManagerImpl extends TenantManager {
    private static final Logger LOGGER = LogManager.getLogger(TenantManagerImpl.class);
    public static final TenantManagerImpl TENANT_MANAGER = new TenantManagerImpl();
    
    public static TenantManagerImpl getInstance() {
        return TENANT_MANAGER;
    }
    
    public void cleanTenant(Long internalTenantId) throws EMAnalyticsFwkException {
        LOGGER.info("Start cleanTenant : {}", internalTenantId);
        EntityManager em = null;
        EntityTransaction entityTransaction = null;
        try {
            em = PersistenceManager.getInstance().getEntityManager(TenantContext.getContext());
            entityTransaction = em.getTransaction();
            if (!entityTransaction.isActive()) {
                entityTransaction.begin();
            }
            int deletedLastAccessCount = em.createNativeQuery("delete from ems_analytics_last_access where tenant_id = ?1")
                    .setParameter(1, internalTenantId).executeUpdate();
            int deletedSearchParamsCount = em
                    .createNativeQuery("delete from ems_analytics_search_params where tenant_id = ?1")
                    .setParameter(1, internalTenantId).executeUpdate();
            int deletedSearchCount = em.createNativeQuery("delete from ems_analytics_search where tenant_id = ?1")
                    .setParameter(1, internalTenantId).executeUpdate();
            int deletedCategoryParamsCount = em
                    .createNativeQuery("delete from ems_analytics_category_params where tenant_id = ?1")
                    .setParameter(1, internalTenantId).executeUpdate();
            int deletedCategoryCount = em.createNativeQuery("delete from ems_analytics_category where tenant_id = ?1")
                    .setParameter(1, internalTenantId).executeUpdate();
            int deletedFolderCount = em.createNativeQuery("delete from ems_analytics_folders where tenant_id = ?1")
                    .setParameter(1, internalTenantId).executeUpdate();
            entityTransaction.commit();
            LOGGER.info("End cleanTenant : {} last access, {} search params, {} searches, {} category params, {} categories "
                    + "and {} folders have been deleted!", deletedLastAccessCount, deletedSearchParamsCount, deletedSearchCount,
                    deletedCategoryParamsCount, deletedCategoryCount, deletedFolderCount);
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }

            LOGGER.error("Fail to delete user data of tenant {} because: ", internalTenantId, e.getMessage());
            throw new EMAnalyticsFwkException("Fail to delete user data of tenant " + internalTenantId,
                    EMAnalyticsFwkException.ERR_CLEAN_TENANT, null);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}

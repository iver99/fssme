package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ResourceBundleManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantInfo;
import oracle.sysman.emaas.platform.savedsearch.entity.EmsResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by xiadai on 2017/6/14.
 */
public class ResourceBundleManagerImpl extends ResourceBundleManager{
    private static final Logger LOGGER = LogManager.getLogger(ResourceBundleManagerImpl.class);
    public static ResourceBundleManagerImpl RESOURCE_BUNDLE_MANAGER = new ResourceBundleManagerImpl();

    public static ResourceBundleManagerImpl getInstance(){ return RESOURCE_BUNDLE_MANAGER; }

    @Override
    public void storeResourceBundle(String serviceName, List<EmsResourceBundle> emsResourceBundles) throws EMAnalyticsFwkException {
        LOGGER.debug("Calling ResourceBundleManagerImpl.cleanResourceBundleByServiceName");
        EntityManager entityManager = null;
        try {
            entityManager = PersistenceManager.getInstance().getEntityManager(new TenantInfo(SearchManagerImpl.DEFAULT_CURRENT_USER, SearchManagerImpl.DEFAULT_TENANT_ID));
            if (!entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().begin();
            }
            cleanResourceBundleByServiceName(serviceName, entityManager);
            for (EmsResourceBundle emsResourceBundle : emsResourceBundles) {
                 persistResourceBundle(emsResourceBundle, entityManager);
            }
        } catch (Exception e) {
            LOGGER.error("Fall into error while cleaning resource bundle by service name ", e);
            throw new EMAnalyticsFwkException("Fall into error while cleaning resource bundle by service name",
                    EMAnalyticsFwkException.ERR_GENERIC, null, e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }
    private void cleanResourceBundleByServiceName(String serviceName, EntityManager entityManager) throws EMAnalyticsFwkException {
        LOGGER.debug("Calling ResourceBundleManagerImpl.cleanResourceBundleByServiceName");
        if (serviceName == null || serviceName.length() == 0) {
            LOGGER.error("the service name is null.");
            return;
        }
        try {
            LOGGER.error("Cleaning resource bundle by servicename {}", serviceName);
            entityManager.createNamedQuery("EmsResourceBundle.deleteByServiceName").setParameter("serviceName", serviceName).executeUpdate();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            LOGGER.error("Fall into error while cleaning resource bundle by service name ", e);
            throw new EMAnalyticsFwkException("Fall into error while cleaning resource bundle by service name",
                    EMAnalyticsFwkException.ERR_GENERIC, null, e);
        }
    }

    private void persistResourceBundle(EmsResourceBundle resourceBundle, EntityManager entityManager) throws EMAnalyticsFwkException {
        LOGGER.debug("Calling ResourceBundleManagerImpl.persistResourceBundle");
        if (resourceBundle == null) {
            return;
        }
        try {
            LOGGER.error("Inserting resource bundle " );
            entityManager.persist(resourceBundle);
        } catch (PersistenceException pe) {
            LOGGER.error("Persistence error while saving the resource bundle for service name {}" + resourceBundle.getServiceName(), pe);
            throw new EMAnalyticsFwkException("Error while saving the resource bundle for service name : " + resourceBundle.getServiceName(),
                    EMAnalyticsFwkException.ERR_CREATE_SEARCH, null, pe);
        }
    }
}

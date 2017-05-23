package oracle.sysman.emaas.platform.savedsearch.services;

import oracle.sysman.emaas.platform.emcpdf.cache.support.lru.LRUCacheManager;
import oracle.sysman.emaas.platform.emcpdf.cache.support.screenshot.LRUScreenshotCacheManager;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.CacheConfig;
import oracle.sysman.emaas.platform.savedsearch.wls.lifecycle.ApplicationServiceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import weblogic.application.ApplicationLifecycleEvent;

/**
 * Created by chehao on 2017/5/10 9:53.
 */
public class CacheServiceManager implements ApplicationServiceManager {

    private static final Logger LOGGER = LogManager.getLogger(CacheServiceManager.class);

    @Override
    public String getName() {
        return "Saved Search cache Service";
    }

    @Override
    public void postStart(ApplicationLifecycleEvent evt) throws Exception {
        LOGGER.info("Post starting cache service...");
        LRUCacheManager.getInstance();
        LRUScreenshotCacheManager.getInstance();
        LOGGER.info("Post starting cache service finished...");
    }

    @Override
    public void postStop(ApplicationLifecycleEvent evt) throws Exception {
        //Do nothing
    }

    @Override
    public void preStart(ApplicationLifecycleEvent evt) throws Exception {
        //Do nothing
    }

    @Override
    public void preStop(ApplicationLifecycleEvent evt) throws Exception {
        LOGGER.info("Pre-stopping cache");
        LRUCacheManager.getInstance().close();
        LRUScreenshotCacheManager.getInstance().close();
        LOGGER.info("Pre-stopped cache");
    }
}

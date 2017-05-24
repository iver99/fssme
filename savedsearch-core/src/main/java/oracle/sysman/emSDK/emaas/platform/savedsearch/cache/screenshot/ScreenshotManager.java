/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot;

import java.math.BigInteger;
import java.util.Date;

import oracle.sysman.emaas.platform.emcpdf.cache.api.ICacheManager;
import oracle.sysman.emaas.platform.emcpdf.cache.support.CacheManagers;
import oracle.sysman.emaas.platform.emcpdf.cache.tool.*;
import oracle.sysman.emaas.platform.emcpdf.cache.util.CacheConstants;
import oracle.sysman.emaas.platform.emcpdf.cache.util.ScreenshotPathGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jersey.core.util.Base64;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;

/**
 * @author guochen
 */
public class ScreenshotManager
{
	private static final Logger LOGGER = LogManager.getLogger(ScreenshotManager.class);

	private static ScreenshotManager instance = new ScreenshotManager();

	public static ScreenshotManager getInstance()
	{
		return instance;
	}


	public ScreenshotElement getScreenshotFromCache(Tenant tenant, BigInteger widgetId, String fileName) throws Exception
	{
		ICacheManager scm= CacheManagers.getInstance().build(CacheConstants.LRU_SCREENSHOT_MANAGER);
		Object cacheKey = DefaultKeyGenerator.getInstance().generate(tenant,new Keys(widgetId));
		if (widgetId == null || BigInteger.ZERO.compareTo(widgetId) >= 0) {
			LOGGER.error("Unexpected widget id to get screenshot from cache for tenant={}, widget id={}, fileName={}", tenant,
					widgetId, fileName);
			return null;
		}
		if (StringUtil.isEmpty(fileName)) {
			LOGGER.error("Unexpected empty screenshot file name for tenant={}, widget id={}", tenant, widgetId);
			return null;
		}
		ScreenshotElement se = (ScreenshotElement) scm.getCache(CacheConstants.CACHES_SCREENSHOT_CACHE).get(cacheKey);
		if (se == null) {
			LOGGER.debug("Retrieved null screenshot element from cache for tenant={}, widget id={}, fileName={}", tenant,
					widgetId, fileName);
			return null;
		}
		LOGGER.debug("Retrieved cacheable tenant:{},widgetId:{},fileName:{} from screenshot cache, the cached file name is: {}",
				tenant, widgetId, fileName, se.getFileName());
		return se;
	}

	public ScreenshotElement storeBase64ScreenshotToCache(Tenant tenant, BigInteger widgetId, Date creation, Date modification,
			String screenshot)
	{
		ICacheManager scm= CacheManagers.getInstance().build(CacheConstants.LRU_SCREENSHOT_MANAGER);
		Object cacheKey = DefaultKeyGenerator.getInstance().generate(tenant,new Keys(widgetId));
		if (screenshot == null) {
			return null;
		}
		String fileName = ScreenshotPathGenerator.getInstance().generateFileName(widgetId, creation, modification);
		byte[] decoded = null;
		if (screenshot.startsWith(CacheConstants.SCREENSHOT_BASE64_PNG_PREFIX)) {
			decoded = Base64.decode(screenshot.substring(CacheConstants.SCREENSHOT_BASE64_PNG_PREFIX.length()));
		}
		else if (screenshot.startsWith(CacheConstants.SCREENSHOT_BASE64_JPG_PREFIX)) {
			decoded = Base64.decode(screenshot.substring(CacheConstants.SCREENSHOT_BASE64_JPG_PREFIX.length()));
		}
		else {
			LOGGER.debug("Failed to retrieve screenshot decoded bytes as the previs isn't supported");
			return null;
		}
		Binary bin = new Binary(decoded);
		ScreenshotElement se = new ScreenshotElement(fileName, bin);
		scm.getCache(CacheConstants.CACHES_SCREENSHOT_CACHE).put(cacheKey,se);
		LOGGER.debug(
				"Cacheable with tenant:{} ,widgetId:{} ,creation:{} , modification:{} ,screenshot:{} is put into screenshot cache",
				tenant, widgetId, creation, modification, screenshot);
		return se;
	}

	public ScreenshotElement storeBase64ScreenshotToCache(Tenant tenant, BigInteger dashboardId, ScreenshotData ssd)
	{
		if (ssd == null) {
			return null;
		}
		return storeBase64ScreenshotToCache(tenant, dashboardId, ssd.getCreationDate(), ssd.getModificationDate(),
				ssd.getScreenshot());
	}
}

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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jersey.core.util.Base64;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Keys;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.Tenant;

/**
 * @author guochen
 */
public class ScreenshotCacheManager
{
	private static final Logger logger = LogManager.getLogger(ScreenshotCacheManager.class);

	private static ScreenshotCacheManager instance = new ScreenshotCacheManager();

	public static ScreenshotCacheManager getInstance()
	{
		return instance;
	}

	private final CacheManager cm;

	private ScreenshotCacheManager()
	{
		cm = CacheManager.getInstance();
	}

	public ScreenshotElement getScreenshotFromCache(Tenant tenant, BigInteger widgetId, String fileName) throws Exception
	{
		if (widgetId == null || BigInteger.ZERO.compareTo(widgetId) >= 0) {
			logger.error("Unexpected widget id to get screenshot from cache for tenant={}, widget id={}, fileName={}", tenant,
					widgetId, fileName);
			return null;
		}
		if (StringUtil.isEmpty(fileName)) {
			logger.error("Unexpected empty screenshot file name for tenant={}, widget id={}", tenant, widgetId);
			return null;
		}
		ScreenshotElement se = (ScreenshotElement) cm.getCacheable(tenant, CacheManager.CACHES_SCREENSHOT_CACHE,
				new Keys(widgetId));
		if (se == null) {
			logger.debug("Retrieved null screenshot element from cache for tenant={}, widget id={}, fileName={}", tenant,
					widgetId, fileName);
			return null;
		}
		logger.debug("Retrieved cacheable tenant:{},widgetId:{},fileName:{} from screenshot cache, the cached file name is: {}",
				tenant, widgetId, fileName, se.getFileName());
		return se;
	}

	public ScreenshotElement storeBase64ScreenshotToCache(Tenant tenant, BigInteger widgetId, Date creation, Date modification,
			String screenshot)
	{
		if (screenshot == null) {
			return null;
		}
		String fileName = ScreenshotPathGenerator.getInstance().generateFileName(widgetId, creation, modification);
		byte[] decoded = null;
		if (screenshot.startsWith(ScreenshotConstant.SCREENSHOT_BASE64_PNG_PREFIX)) {
			decoded = Base64.decode(screenshot.substring(ScreenshotConstant.SCREENSHOT_BASE64_PNG_PREFIX.length()));
		}
		else if (screenshot.startsWith(ScreenshotConstant.SCREENSHOT_BASE64_JPG_PREFIX)) {
			decoded = Base64.decode(screenshot.substring(ScreenshotConstant.SCREENSHOT_BASE64_JPG_PREFIX.length()));
		}
		else {
			logger.debug("Failed to retrieve screenshot decoded bytes as the previs isn't supported");
			return null;
		}
		Binary bin = new Binary(decoded);
		ScreenshotElement se = new ScreenshotElement(fileName, bin);
		cm.putCacheable(tenant, CacheManager.CACHES_SCREENSHOT_CACHE, new Keys(widgetId), se);
		logger.debug(
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

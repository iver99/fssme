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

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jersey.core.util.Base64;
import com.tangosol.util.Binary;

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
	private static final String SCREENSHOT_BASE64_PREFIX = "data:image/png;base64,";

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

	public ScreenshotElement getScreenshotFromCache(Tenant tenant, Long widgetId, String fileName) throws Exception
	{
		if (widgetId == null || widgetId <= 0) {
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
			logger.error("Retrieved null screenshot element from cache for tenant={}, widget id={}, fileName={}", tenant,
					widgetId, fileName);
			return null;
		}
		return se;
	}

	public ScreenshotElement storeBase64ScreenshotToCache(Tenant tenant, Long widgetId, Date creation, Date modification,
			String screenshot)
	{
		if (screenshot == null) {
			return null;
		}
		String fileName = ScreenshotPathGenerator.getInstance().generateFileName(widgetId, creation, modification);
		final byte[] decoded = Base64.decode(screenshot.substring(SCREENSHOT_BASE64_PREFIX.length()));
		Binary bin = new Binary(decoded);
		ScreenshotElement se = new ScreenshotElement(fileName, bin);
		cm.putCacheable(tenant, CacheManager.CACHES_SCREENSHOT_CACHE, new Keys(widgetId), se);
		return se;
	}

	public ScreenshotElement storeBase64ScreenshotToCache(Tenant tenant, Long widgetId, ScreenshotData ssd)
	{
		if (ssd == null) {
			return null;
		}
		return storeBase64ScreenshotToCache(tenant, widgetId, ssd.getCreationDate(), ssd.getModificationDate(),
				ssd.getScreenshot());
	}
}

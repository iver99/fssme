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

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;

/**
 * @author guochen
 */
public class ScreenshotPathGenerator
{
	private static final Logger logger = LogManager.getLogger(ScreenshotPathGenerator.class);
	private static String DEFAULT_SCREENSHOT_EXT = ".png";
	private static String SERVICE_VERSION_PROPERTY = "serviceVersion";

	private static ScreenshotPathGenerator instance = new ScreenshotPathGenerator();

	public static ScreenshotPathGenerator getInstance()
	{
		return instance;
	}

	private String versionString = null;

	private ScreenshotPathGenerator()
	{
		initialize();
	}

	public String generateFileName(Long widgetId, Date creation, Date modification)
	{
		if (widgetId == null) {
			logger.error("Unexpected null widget id to generate screenshot file name: widget id={}, creation={}, modification={}",
					widgetId, creation, modification);
			return null;
		}
		StringBuilder sb = new StringBuilder();
		if (modification != null) {
			sb.append(modification.getTime());
		}
		else if (creation != null) {
			sb.append(creation.getTime());
		}
		else {
			logger.error("Unexpected widget with null values for both creation and modification date");
			return null;
		}
		sb.append("_");
		sb.append(widgetId);
		sb.append(DEFAULT_SCREENSHOT_EXT);
		return sb.toString();
	}

	public String generateScreenshotUrl(String baseUrl, Long widgetId, Date creation, Date modification)
	{
		if (StringUtil.isEmpty(baseUrl)) {
			logger.error("Unexpected null/empty base url to generate screenshot URL");
			return null;
		}
		if (StringUtil.isEmpty(versionString)) {
			logger.error("Unexpected null/empty versionString to generate screenshot URL");
			return null;
		}
		String fileName = generateFileName(widgetId, creation, modification);
		if (fileName == null) {
			return null;
		}
		String url = baseUrl + (baseUrl.endsWith("/") ? "" : "/") + widgetId + "/screenshot/" + versionString + "/images/"
				+ fileName;
		logger.debug("The screenshot URL is {} for specified baseUrl={}, widgetId={}, creation={}, modification={}", baseUrl,
				widgetId, creation, modification);
		return url;
	}

	private void initialize()
	{
		Properties properties = new Properties();
		InputStream is = null;
		try {
			is = ScreenshotPathGenerator.class.getResourceAsStream("/savedsearch.properties");
			properties.load(is);
			versionString = (String) properties.get(SERVICE_VERSION_PROPERTY);
			logger.debug("Initialize the screenshot path version string to {}", versionString);
		}
		catch (IOException e) {
			logger.error(e);
		}
		finally {
			if (is != null) {
				try {
					is.close();
				}
				catch (IOException e) {
					logger.error(e);
				}
			}
		}
	}
}
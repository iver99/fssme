/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.util;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * @author guobaochen
 */
public class LogUtil
{
	/**
	 * Class that represents the interaction log context, containing tenant id, service invoked, and service invocation direction
	 *
	 * @author guobaochen
	 */
	public static class InteractionLogContext
	{
		private String tenantId;
		private String serviceInvoked;
		private String direction;

		public InteractionLogContext(String tenantId, String serviceInvoked, String direction)
		{
			this.tenantId = tenantId;
			this.serviceInvoked = serviceInvoked;
			this.direction = direction;
		}

		public String getDirection()
		{
			return direction;
		}

		public String getServiceInvoked()
		{
			return serviceInvoked;
		}

		public String getTenantId()
		{
			return tenantId;
		}

		public void setDirection(String direction)
		{
			this.direction = direction;
		}

		public void setServiceInvoked(String serviceInvoked)
		{
			this.serviceInvoked = serviceInvoked;
		}

		public void setTenantId(String tenantId)
		{
			this.tenantId = tenantId;
		}
	}

	/**
	 * Direction for interaction log
	 *
	 * @author guobaochen
	 */
	public static enum InteractionLogDirection
	{
		/**
		 * for all incoming service requests
		 */
		IN,
		/**
		 * for all outbound service request
		 */
		OUT,
		/**
		 * for scenarios that service request not specified
		 */
		NA
	}

	private static final Logger logger = LogManager.getLogger(LogUtil.class);
	public static final String LOG_PROP_TENANTID = "tenantId";
	public static final String LOG_VALUE_NA = "N/A";
	public static final String INTERACTION_LOG_PROP_SERVICE_INVOKED = "serviceInvoked";

	public static final String INTERACTION_LOG_PROP_DIRECTION = "direction";

	private static final String INTERACTION_LOG_NAME = "oracle.sysman.emaas.platform.savedsearch.interaction.log";

	private static final String LOGGER_PROP_UPDATE_TIME = "SSF_UPDATE_TIME";

	/**
	 * Clear the saved search interaction log context
	 */
	public static void clearInteractionLogContext()
	{
		ThreadContext.remove(LOG_PROP_TENANTID);
		ThreadContext.remove(INTERACTION_LOG_PROP_SERVICE_INVOKED);
		ThreadContext.remove(INTERACTION_LOG_PROP_DIRECTION);
	}

	/**
	 * Returns the Saved Search interaction log
	 *
	 * @return
	 */
	public static final Logger getInteractionLogger()
	{
		return LogManager.getLogger(INTERACTION_LOG_NAME);
	}

	/**
	 * Retrieve the long timestamp for update time for specified logger
	 *
	 * @param cfg
	 * @param lc
	 * @return
	 */
	public static Long getLoggerUpdateTime(Configuration cfg, LoggerConfig lc)
	{
		Map<String, String> cfgProps = cfg.getProperties();
		if (cfgProps == null) {
			return null;
		}
		String time = cfgProps.get(LOGGER_PROP_UPDATE_TIME + lc.getName());
		if (time == null) {
			return null;
		}
		return Long.valueOf(time);
	}

	/**
	 * Initialize all loggers update time
	 *
	 * @return
	 */
	public static void initializeLoggersUpdateTime()
	{
		Long timestamp = DateUtil.getCurrentUTCTime().getTime();
		LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
		Configuration cfg = loggerContext.getConfiguration();
		Map<String, LoggerConfig> loggers = cfg.getLoggers();
		for (LoggerConfig lc : loggers.values()) {
			LogUtil.setLoggerUpdateTime(cfg, lc, timestamp);
			loggerContext.updateLoggers();
		}
	}

	/**
	 * Initialize saved search interaction log context
	 *
	 * @param serviceInvoked
	 * @param direction
	 */
	public static InteractionLogContext setInteractionLogThreadContext(String tenantId, String serviceInvoked,
			InteractionLogDirection direction)
	{
		InteractionLogContext old = new InteractionLogContext(ThreadContext.get(LOG_PROP_TENANTID),
				ThreadContext.get(INTERACTION_LOG_PROP_SERVICE_INVOKED), ThreadContext.get(INTERACTION_LOG_PROP_DIRECTION));
		if (tenantId == null && serviceInvoked == null && direction == null) {
			return old;
		}
		if (tenantId == null || tenantId.equals("")) {
			logger.debug("Initialize interaction log context: tenantId is null or empty");
			tenantId = LOG_VALUE_NA;
		}
		if (serviceInvoked == null || serviceInvoked.equals("")) {
			logger.debug("Initialize interaction log context: serviceInvoked is null or empty");
			serviceInvoked = "Service invoked: N/A";
		}
		if (direction == null) {
			logger.debug("Initialize interaction log context: direction is null");
			direction = InteractionLogDirection.NA;
		}
		ThreadContext.put(LOG_PROP_TENANTID, tenantId);
		ThreadContext.put(INTERACTION_LOG_PROP_SERVICE_INVOKED, serviceInvoked);
		if (InteractionLogDirection.IN.equals(direction)) {
			ThreadContext.put(INTERACTION_LOG_PROP_DIRECTION, "IN");
		}
		else if (InteractionLogDirection.OUT.equals(direction)) {
			ThreadContext.put(INTERACTION_LOG_PROP_DIRECTION, "OUT");
		}
		else {
			ThreadContext.put(INTERACTION_LOG_PROP_DIRECTION, "N/A");
		}
		return old;
	}

	/**
	 * Sets the update timestamp for specified logger
	 *
	 * @param cfg
	 * @param lc
	 * @param timestamp
	 */
	public static void setLoggerUpdateTime(Configuration cfg, LoggerConfig lc, Long timestamp)
	{
		if (timestamp == null) {
			return;
		}
		Map<String, String> cfgProps = cfg.getProperties();
		cfgProps.put(LOGGER_PROP_UPDATE_TIME + lc.getName(), timestamp.toString());
	}
}

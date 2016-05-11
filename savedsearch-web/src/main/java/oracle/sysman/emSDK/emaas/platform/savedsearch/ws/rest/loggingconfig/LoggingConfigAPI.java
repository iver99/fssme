/*
 * Copyright (C) 2015 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.loggingconfig;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.StringUtil;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author guobaochen
 */
@Path("/_logging/configs")
public class LoggingConfigAPI
{
	private static final Logger logger = LogManager.getLogger(LoggingConfigAPI.class);

	/**
	 * Change the log4j2 logging level for ROOT logger<br>
	 * <br>
	 * URL: <font color="blue">http://&lthost-name&gt:&lt;port number&gt;/savedsearch/v1/_logging/configs</font><br>
	 * The string "_logging/configs" in the URL signifies update operation on logging level for ROOT logger
	 *
	 * @since 0.1
	 * @param inputJson
	 *            JSON string which contains new log4j2 logging level to be updated<br>
	 *            Input Sample:<br>
	 *            <font color="DarkCyan"> {<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; "level": "DEBUG"<br>
	 *            }</font><br>
	 * @return returns the updated logging level for the logger<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan"> {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "loggerName": "",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "currentLoggingLevel": "DEBUG",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "lastUpdatedEpoch": "1445408589707"<br>
	 *         }</font><br>
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeRootLoggerLevel(JSONObject inputJson)
	{
		return changeSpecificLoggerLevel("", inputJson);

	}

	/**
	 * Change the log4j2 logging level for specific logger<br>
	 * <br>
	 * URL: <font color="blue">http://&lthost-name&gt:&lt;port
	 * number&gt;/savedsearch/v1/_logging/configs/&lt;loggerName&gt;</font><br>
	 * The string "_logging/configs/&lt;loggerName&gt;" in the URL signifies update operation on logging level for logger
	 * specified by loggerName.
	 *
	 * @since 0.1
	 * @param loggerName
	 *            the name of a logger for which the logging level is to be updated.
	 * @param inputJson
	 *            JSON string which contains new log4j2 logging level to be updated<br>
	 *            Input Sample:<br>
	 *            <font color="DarkCyan"> {<br>
	 *            &nbsp;&nbsp;&nbsp;&nbsp; "level": "DEBUG"<br>
	 *            }</font><br>
	 * @return returns the updated logging level for the logger<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan"> {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "loggerName": "oracle.sysman.emaas.platform.savedsearch",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "currentLoggingLevel": "DEBUG",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "lastUpdatedEpoch": "1445408589707"<br>
	 *         }</font><br>
	 */
	@PUT
	@Path("{loggerName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeSpecificLoggerLevel(@PathParam("loggerName") String loggerName, JSONObject inputJson)
	{
		UpdatedLoggerLevel input = null;
		try {
			input = getJsonUtil().fromJson(inputJson.toString(), UpdatedLoggerLevel.class);
			if (input == null || StringUtil.isEmpty(input.getLevel())) {
				String errorMessage = "Specified new level: \"" + input.getLevel() + "\" for logger \"" + loggerName
						+ "\" does not exist";
				return Response.status(Status.SERVICE_UNAVAILABLE).entity(JsonUtil.buildNormalMapper().toJson(errorMessage))
						.build();
			}
			Level level = Level.getLevel(input.getLevel().toUpperCase());
			if (level == null) {
				String errorMessage = "Specified new level: \"" + input.getLevel() + "\" for logger \"" + loggerName
						+ "\" does not exist";
				return Response.status(Status.SERVICE_UNAVAILABLE).entity(JsonUtil.buildNormalMapper().toJson(errorMessage))
						.build();
			}
			LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
			Configuration cfg = ctx.getConfiguration();
			Map<String, LoggerConfig> loggers = cfg.getLoggers();
			LoggingItem li = null;
			for (LoggerConfig lc : loggers.values()) {
				if (lc.getName().equalsIgnoreCase(loggerName)) {
					lc.setLevel(level);
					Calendar cal = Calendar.getInstance(Locale.US);
					long localNow = System.currentTimeMillis();
					long offset = cal.getTimeZone().getOffset(localNow);
					Long timestamp = localNow - offset;
					LogUtil.setLoggerUpdateTime(cfg, lc, timestamp);
					ctx.updateLoggers();
					li = new LoggingItem(lc, timestamp);
					logger.info("Level for logger \"{}\" has been updated to \"{}\" by user \"{}\" from REST interface",
							loggerName, input.getLevel(), TenantContext.getContext().getUsername());
					break;
				}
			}
			if (li == null) {
				String errorMessage = "Specified logger: " + loggerName + " does not exist";
				return Response.status(Status.SERVICE_UNAVAILABLE).entity(JsonUtil.buildNormalMapper().toJson(errorMessage))
						.build();
			}
			return Response.ok(getJsonUtil().toJson(li)).build();
		}
		catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
			return Response.status(Status.SERVICE_UNAVAILABLE)
					.entity(JsonUtil.buildNormalMapper().toJson(e.getLocalizedMessage())).build();
		}
	}

	/**
	 * Retrieves the log4j2 logging level for all loggers<br>
	 * <br>
	 * URL: <font color="blue">http://&lthost-name&gt:&lt;port number&gt;/savedsearch/v1/_logging/configs</font><br>
	 * The string "_logging/configs" in the URL signifies getting operation on logging level for all loggers
	 *
	 * @since 0.1
	 * @return returns the logging level for all the loggers<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan"> {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; total: 7,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; items: [{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "loggerName":
	 *         "oracle.sysman.emaas.platform.savedsearch.interaction.log",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "currentLoggingLevel": "INFO",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastUpdatedEpoch": "1445408589707"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; },{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "loggerName": "oracle.sysman.emaas.platform.savedsearch.services",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "currentLoggingLevel": "INFO",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastUpdatedEpoch": "1445408589707"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }, {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "loggerName": "oracle.sysman.emaas.platform.savedsearch.utils",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "currentLoggingLevel": "INFO",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastUpdatedEpoch": "1445408589707"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }, {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "loggerName":
	 *         "oracle.sysman.emaas.platform.savedsearch.wls.lifecycle",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "currentLoggingLevel": "INFO",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastUpdatedEpoch": "1445408589707"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }, {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "loggerName":
	 *         "oracle.sysman.emaas.platform.savedsearch.wls.management",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "currentLoggingLevel": "INFO",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastUpdatedEpoch": "1445408589707"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }, {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "loggerName": "oracle.sysman.emaas.platform.savedsearch",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "currentLoggingLevel": "INFO",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastUpdatedEpoch": "1445408589707"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }, {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "loggerName": "",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "currentLoggingLevel": "INFO",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "lastUpdatedEpoch": "1445408589707"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }]<br>
	 *         }</font><br>
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLoggerLevels()
	{
		logger.info("User \"{}\" is getting all logger levels", TenantContext.getContext().getUsername());
		LoggerContext loggerContext = (LoggerContext) LogManager.getContext(false);
		Configuration cfg = loggerContext.getConfiguration();
		Map<String, LoggerConfig> loggers = cfg.getLoggers();
		LoggingItems items = new LoggingItems();
		for (LoggerConfig logger : loggers.values()) {
			Long timestamp = LogUtil.getLoggerUpdateTime(cfg, logger);
			items.addLoggerConfig(logger, timestamp);
		}
		return Response.ok(getJsonUtil().toJson(items)).build();
	}

	private JsonUtil getJsonUtil()
	{
		return JsonUtil.buildNormalMapper();
	}
}

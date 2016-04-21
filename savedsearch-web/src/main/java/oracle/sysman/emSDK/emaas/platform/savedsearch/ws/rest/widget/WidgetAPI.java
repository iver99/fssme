package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.widget;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sun.jersey.core.util.Base64;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.StringUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.screenshot.ScreenshotData;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Widget;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.WidgetManager;

/**
 * Saved Search Service
 *
 * @since 0.1
 */
@Path("/widgets")
public class WidgetAPI
{
	private static final Logger _logger = LogManager.getLogger(WidgetAPI.class);

	private static final String SCREENSHOT_BASE64_PNG_PREFIX = "data:image/png;base64,";

	private static final String SCREENSHOT_BASE64_JPG_PREFIX = "data:image/jpeg;base64,";

	@Context
	UriInfo uri;

	/**
	 * Get list of all widgets or list widgets by given widget group id<br>
	 * <br>
	 * URL: <font color="blue">http://&lthost-name&gt:&lt;port number&gt;/savedsearch/v1/widgets</font><br>
	 * The string "widgets" in the URL signifies read operation on all widgets. <br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/widgets?widgetGroupId=&lt;widget group
	 * Id&gt;</font><br>
	 * The string "widgets?widgetGroupId=&lt;widget group Id&gt;" in the URL signifies read operation on widgets with given widget
	 * group Id.<br>
	 * URL: <font color="blue">http://&lt;host-name&gt;:&lt;port
	 * number&gt;/savedsearch/v1/widgets?includeDashboardIneligible=&lt;true or false&gt;</font><br>
	 * The string "widgets?includeDashboardIneligible=&lt;true or false&gt;" in the URL signifies read operation on widgets,
	 * includeDashboardIneligible specifies whether to return Dashboard Ineligible widgets or not.<br>
	 *
	 * @since 0.1
	 * @return List of widgets<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">[<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_UNIQUE_ID": 10240,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_NAME": "DB_Analytics_Home",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_DESCRIPTION": "DB Analytics Home Page",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_OWNER": "SYSMAN",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_CREATION_TIME": "2015-01-06T11:02:36.971Z",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_SOURCE": 1,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_GROUP_NAME": "Demo Analytics",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_VIEWMODEL":
	 *         "dependencies/widgets/iFrame/js/widget-iframe",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_KOC_NAME": "DF_V1_WIDGET_IFRAME",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_TEMPLATE":
	 *         "dependencies/widgets/iFrame/widget-iframe.html",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_SUPPORT_TIME_CONTROL": "0",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_LINKED_DASHBOARD": "1",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_DEFAULT_WIDTH": 6,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_DEFAULT_HEIGHT": 2,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "PROVIDER_VERSION": "0.1",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "PROVIDER_NAME": "DB Analytics",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "PROVIDER_ASSET_ROOT": "home"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }<br>
	 *         ]</font><br>
	 *         <br>
	 *         Response Code:<br>
	 *         <table border="1">
	 *         <tr>
	 *         <th>Status code</th>
	 *         <th>Status</th>
	 *         <th>Description</th>
	 *         </tr>
	 *         <tr>
	 *         <td>200</td>
	 *         <td>OK</td>
	 *         <td>Get list of all widgets or list widgets by given widget group id successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>500</td>
	 *         <td>Internal Server Error</td>
	 *         <td>&nbsp;</td>
	 *         </tr>
	 *         </table>
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllWidgets(@Context UriInfo uri, @HeaderParam(value = "OAM_REMOTE_USER") String userTenant,
			@QueryParam("widgetGroupId") String widgetGroupId,
			@QueryParam("includeDashboardIneligible") boolean includeDashboardIneligible)
	{
		LogUtil.getInteractionLogger().info(
				"Service calling to (GET) /savedsearch/v1/widgets?widgetGroupId={}&includeDashboardIneligible={}", widgetGroupId,
				includeDashboardIneligible);
		String message = null;
		int statusCode = 200;

		try {
			String query = uri.getRequestUri().getQuery();
			int groupId = widgetGroupId != null ? Integer.parseInt(widgetGroupId) : 0;
			if (groupId < 0) {
				throw new NumberFormatException();
			}

			if (query != null) {
				String[] param = query.split("&");
				Response resp = null;
				if (param.length >= 2) {
					resp = checkQueryParam(param[0]);
					if (resp != null) {
						return resp;
					}
					resp = checkQueryParam(param[1]);
					if (resp != null) {
						return resp;
					}
				}
				else if (param.length > 0) {
					resp = checkQueryParam(param[0]);
					if (resp != null) {
						return resp;
					}
				}
			}

			message = getAllWidgetsFromCache(widgetGroupId, includeDashboardIneligible);
		}

		catch (NumberFormatException e) {
			return Response.status(400).entity("Id should be a positive number and not an alphanumeric").build();
		}
		catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Failed to get widgets, statusCode:" + statusCode + " ,err:" + message, e);
		}
		catch (Exception e) {
			message = e.getMessage();
			statusCode = 500;
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Unknow error when retrieving widgets, statusCode:" + statusCode + " ,err:" + message, e);
		}
		return Response.status(statusCode).entity(message).build();
	}

	/**
	 * Get base64 encoded widget screen shot string by given widget id<br>
	 * <br>
	 * URL: <font color="blue">http://&lthost-name&gt:&lt;port number&gt;/savedsearch/v1/widgets/&lt;widget
	 * Id&gt/screenshot</font><br>
	 * The string "widgets/&lt;widget Id&gt/screenshot" in the URL signifies read operation on widget screen shot with given
	 * widget Id.
	 *
	 * @since 0.1
	 * @return Screen shot of the identified widget<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">{<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_VISUAL":
	 *         "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAL4AAACMCAIAAABNpIRsAAAz3UlE..." }</font><br>
	 *         <br>
	 *         Response Code:<br>
	 *         <table border="1">
	 *         <tr>
	 *         <th>Status code</th>
	 *         <th>Status</th>
	 *         <th>Description</th>
	 *         </tr>
	 *         <tr>
	 *         <td>200</td>
	 *         <td>OK</td>
	 *         <td>Get screen shot of the widget by id successfully</td>
	 *         </tr>
	 *         <tr>
	 *         <td>500</td>
	 *         <td>Internal Server Error</td>
	 *         <td>&nbsp;</td>
	 *         </tr>
	 *         </table>
	 */
	@GET
	@Path("{id: [1-9][0-9]*}/screenshot/{serviceVersion}/images/{fileName}")
	public Response getWidgetScreenshotById(@PathParam("id") long widgetId, @PathParam("serviceVersion") String serviceVersion,
			@PathParam("fileName") String fileName)
	{
		String message = null;
		int statusCode = 200;

		CacheControl cc = new CacheControl();
		cc.setMaxAge(2592000);

		try {
			SearchManager searchMan = SearchManager.getInstance();
			final ScreenshotData ss = searchMan.getWidgetScreenshotById(widgetId);
			if (ss == null || ss.getScreenshot() == null) { // searchManagerImpl ensures an non-null return value. put check for later possible checks
				_logger.error("Does not retrieved base64 screenshot data. return 404 then");
				return Response.status(Status.NOT_FOUND).build();
			}

			_logger.debug("Retrieved screenshot data from persistence layer, stored to cache, and build response now.");
			return Response.ok(new StreamingOutput() {
				/* (non-Javadoc)
				 * @see javax.ws.rs.core.StreamingOutput#write(java.io.OutputStream)
				 */
				@Override
				public void write(OutputStream os) throws IOException, WebApplicationException
				{

					byte[] decoded = null;
					if (ss.getScreenshot().startsWith(SCREENSHOT_BASE64_PNG_PREFIX)) {
						decoded = Base64.decode(ss.getScreenshot().substring(SCREENSHOT_BASE64_PNG_PREFIX.length()));
					}
					else if (ss.getScreenshot().startsWith(SCREENSHOT_BASE64_JPG_PREFIX)) {
						decoded = Base64.decode(ss.getScreenshot().substring(SCREENSHOT_BASE64_JPG_PREFIX.length()));
					}
					os.write(decoded);
					os.flush();
					os.close();
				}

			}).cacheControl(cc).type("image/png").build();
		}
		catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Failed to get widget screen shot, statusCode:" + statusCode + " ,err:" + message, e);
		}
		catch (Exception e) {
			message = e.getMessage();
			statusCode = 500;
			_logger.error(
					(TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
							+ "Unknow error when retrieving widget screen shot, statusCode:" + statusCode + " ,err:" + message,
					e);
		}
		return Response.status(statusCode).entity(message).type(MediaType.APPLICATION_JSON).build();
	}

	private Response checkQueryParam(String param) throws NumberFormatException
	{
		final String PARAM_WIDGET_GROUP_ID = "widgetGroupId";
		final String PARAM_INCLUDE_DSB_INELIGIBLE = "includeDashboardIneligible";
		final String ERROR_MSG_INVALID_PARAM = "Please give query parameter by one of " + PARAM_WIDGET_GROUP_ID + ", "
				+ PARAM_INCLUDE_DSB_INELIGIBLE;
		String[] input = param.split("=");
		String key = input[0];
		if (!key.equals(PARAM_WIDGET_GROUP_ID) && !key.equals(PARAM_INCLUDE_DSB_INELIGIBLE)) {
			return Response.status(400).entity(ERROR_MSG_INVALID_PARAM).build();
		}
		else {
			if (input.length >= 2) {
				String value = input[1];
				if (value != null && PARAM_WIDGET_GROUP_ID.equals(key)) {
					int groupId = Integer.parseInt(value);
					if (groupId < 0) {
						throw new NumberFormatException();
					}
				}
				else if (value != null && PARAM_INCLUDE_DSB_INELIGIBLE.equals(key)) {
					if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
						return Response.status(400).entity("Please specify " + key + " true or false").build();
					}
				}
			}
			else {
				return Response.status(400).entity("Please give the value for " + key).build();
			}
		}
		return null;
	}

	private String getAllWidgetsFromCache(String widgetGroupId, boolean includeDashboardIneligible)
			throws EMAnalyticsFwkException, IOException
	{
		List<String> providers = TenantSubscriptionUtil
				.getTenantSubscribedServiceProviders(TenantContext.getContext().gettenantName());
		_logger.debug("Retrieved subscribed providers {} for tenant {}",
				StringUtil.arrayToCommaDelimitedString(providers.toArray()), TenantContext.getContext().gettenantName());
		List<Widget> widgetList = SearchManager.getInstance().getWidgetListByProviderNames(includeDashboardIneligible, providers,
				widgetGroupId);
		String message = WidgetManager.getInstance().getWidgetJsonStringFromWidgetList(widgetList);

		if (!includeDashboardIneligible && StringUtil.isEmpty(widgetGroupId)) {
			_logger.debug("Storing widget list to cache");
		}
		else {
			_logger.debug("Not store to cache for includeDashboardIneligible={}, widgetGroupId={}", includeDashboardIneligible,
					widgetGroupId);
		}
		return message;
	}

	//	private boolean isWidgetHiddenInWidgetSelector(Widget widget, boolean includeDashboardIneligible)
	//	{
	//		boolean hiddenInWidgetSelector = false;
	//		if (!includeDashboardIneligible) {
	//			List<SearchParameter> params = widget.getParameters();
	//			if (params != null && params.size() > 0) {
	//				for (SearchParameter param : params) {
	//					if (PARAM_NAME_DASHBOARD_INELIGIBLE.equals(param.getName()) && "1".equals(param.getValue())) {
	//						hiddenInWidgetSelector = true;
	//						break;
	//					}
	//				}
	//			}
	//		}
	//		return hiddenInWidgetSelector;
	//	}
}

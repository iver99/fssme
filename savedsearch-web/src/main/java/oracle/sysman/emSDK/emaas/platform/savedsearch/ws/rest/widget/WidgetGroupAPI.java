package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.widget;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.TenantSubscriptionUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 * Saved Search Service
 *
 * @since 0.1
 */
@Path("/widgetgroups")
public class WidgetGroupAPI
{
	private static final Logger _logger = LogManager.getLogger(WidgetGroupAPI.class);
	@Context
	UriInfo uri;

	/**
	 * Get list of all existed widget groups<br>
	 * <br>
	 * URL: <font color="blue">http://&lthost-name&gt:&lt;port number&gt;/savedsearch/v1/widgetgroups</font><br>
	 * The string "widgetgroups" in the URL signifies read operation on widget group. URL: <font
	 * color="blue">http://&lt;host-name&gt;:&lt;port number&gt;/savedsearch/v1/widgetgroups?includeDashboardIneligible=&lt;true
	 * or false&gt;</font><br>
	 * The string "widgetgroups?includeDashboardIneligible=&lt;true or false&gt;" in the URL signifies read operation on widget
	 * groups, includeDashboardIneligible specifies whether to return Dashboard Ineligible widget groups or not.<br>
	 *
	 * @since 0.1
	 * @return Lists all the existed widget groups<br>
	 *         Response Sample:<br>
	 *         <font color="DarkCyan">[<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; {<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_GROUP_ID": 1,<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_GROUP_NAME": "Log Analytics",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "WIDGET_GROUP_DESCRIPTION": "Search Category for Log Analytics" <br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "PROVIDER_NAME": "Log Analytics"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "PROVIDER_VERSION": "1.0"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "PROVIDER_DISCOVERY": "discovery"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "PROVIDER_ASSET_ROOT": "asset"<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp; }<br>
	 *         ]</font><br>
	 * <br>
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
	 *         <td>List all existed widget groups successfully</td>
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
	public Response getAllWidgetGroups(@HeaderParam(value = "X-REMOTE-USER") String userTenant,
			@QueryParam("includeDashboardIneligible") boolean includeDashboardIneligible)

	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/widgetgroups");
		String message = null;
		int statusCode = 200;

		try {
			String query = uri.getRequestUri().getQuery();

			if (query != null) {
				String[] param = query.split("&");
				if (param.length > 0) {
					Response resp = checkQueryParam(param[0]);
					if (resp != null) {
						return resp;
					}
				}
			}

			JSONArray jsonArray = new JSONArray();
			List<Category> catList = TenantSubscriptionUtil.getTenantSubscribedCategories(
					userTenant.substring(0, userTenant.indexOf(".")), includeDashboardIneligible);
			for (Category category : catList) {
				if (!"home".equalsIgnoreCase(category.getProviderAssetRoot())) {
					JSONObject jsonWidgetGroup = EntityJsonUtil.getWidgetGroupJsonObj(uri.getBaseUri(), category);
					jsonArray.put(jsonWidgetGroup);
				}
			}
			message = jsonArray.toString();
		}
		catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Failed to get all widget groups, statusCode:" + statusCode + " ,err:" + message, e);
		}
		catch (Exception e) {
			message = e.getMessage();
			statusCode = 500;
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Unknow error when retrieving all widget groups, statusCode:" + statusCode + " ,err:" + message, e);
		}
		return Response.status(statusCode).entity(message).build();
	}

	private Response checkQueryParam(String param)
	{
		final String PARAM_INCLUDE_DSB_INELIGIBLE = "includeDashboardIneligible";
		final String ERROR_MSG_INVALID_PARAM = "Please give one and only one query parameter of " + PARAM_INCLUDE_DSB_INELIGIBLE;
		String[] input = param.split("=");
		String key = input[0];
		if (!key.equals(PARAM_INCLUDE_DSB_INELIGIBLE)) {
			return Response.status(400).entity(ERROR_MSG_INVALID_PARAM).build();
		}
		else {
			if (input.length >= 2) {
				String value = input[1];
				if (value != null && PARAM_INCLUDE_DSB_INELIGIBLE.equals(key)) {
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
}

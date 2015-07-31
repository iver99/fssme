package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.widget;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.TenantSubscriptionUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

/**
 * Saved Search Service
 *
 * @since 0.1
 */
@Path("/widgets")
public class WidgetAPI
{
	private static final Logger _logger = LogManager.getLogger(WidgetAPI.class);
	@Context
	UriInfo uri;

	/**
	 * Get list of all existed widgets<br>
	 * <br>
	 * URL: <font color="blue">http://&lthost-name&gt:&lt;port number&gt;/savedsearch/v1/widgets</font><br>
	 * The string "widgets" in the URL signifies read operation on widget.
	 *
	 * @since 0.1
	 * @return Lists all the existed widgets<br>
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
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "PROVIDER_VERSION": "0.1",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "PROVIDER_NAME": "DB Analytics",<br>
	 *         &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "PROVIDER_ASSET_ROOT": "home"<br>
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
	 *         <td>List all existed widgets successfully</td>
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
	public Response getAllWidgets(@HeaderParam(value = "OAM_REMOTE_USER") String userTenant)
	{
		LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/widgets");
		String message = null;
		int statusCode = 200;

		try {
			JSONArray jsonArray = new JSONArray();
			List<Category> catList = TenantSubscriptionUtil.getTenantSubscribedCategories(userTenant.substring(0,
					userTenant.indexOf(".")));
			SearchManager searchMan = SearchManager.getInstance();
			for (Category category : catList) {
				List<Search> searchList = new ArrayList<Search>();
				searchList = searchMan.getWidgetListByCategoryId(category.getId().longValue());
				for (Search search : searchList) {
					JSONObject jsonWidget = EntityJsonUtil.getWidgetJsonObj(uri.getBaseUri(), search, category);
					if (jsonWidget != null) {
						jsonArray.put(jsonWidget);
					}
				}
			}
			message = jsonArray.toString();
		}

		catch (EMAnalyticsFwkException e) {
			message = e.getMessage();
			statusCode = e.getStatusCode();
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Failed to get all widgets, statusCode:" + statusCode + " ,err:" + message, e);
		}
		catch (Exception e) {
			message = e.getMessage();
			statusCode = 500;
			_logger.error((TenantContext.getContext() != null ? TenantContext.getContext().toString() : "")
					+ "Unknow error when retrieving all widgets, statusCode:" + statusCode + " ,err:" + message, e);
		}
		return Response.status(statusCode).entity(message).build();
	}
}

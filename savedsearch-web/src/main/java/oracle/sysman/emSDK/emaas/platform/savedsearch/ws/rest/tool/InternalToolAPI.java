package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.tool;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsDatabaseUnavailException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.metadata.MetadataRefreshAPI;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by xiadai on 2017/6/6.
 */
@Path("tool")
public class InternalToolAPI {
    private static final Logger LOGGER = LogManager.getLogger(MetadataRefreshAPI.class);

    @GET
    @Path("widget/{widgetName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getWidgetByName(@PathParam("widgetName") String widgetName){
        LogUtil.getInteractionLogger().info("Service calling to (PUT) /savedsearch/v1/widget/{}", widgetName);
        String message;
        int statusCode = 200;
        ArrayNode result = new ObjectMapper().createArrayNode();
        if(widgetName.length() < 1 || widgetName == null) return Response.status(400).entity("BAD REQUEST PROVIDE THE WIDGET NAME PLEASE").build();
        SearchManager searchManager  = SearchManager.getInstance();
        try{
            if (!DependencyStatus.getInstance().isDatabaseUp()) {
                throw new EMAnalyticsDatabaseUnavailException();
            }
            List<Search> searches = searchManager.getWidgetByName(widgetName);
            if(searches.isEmpty()) return Response.status(statusCode).entity("NO ENTITY WITH NAME " +widgetName + " FOUND").build();

            for(Search search : searches){
                ObjectNode objectNode = EntityJsonUtil.getWidgetJsonObject(search);
                result.add(objectNode);
            }
            message = result.toString();

        }catch (EMAnalyticsFwkException e){
            statusCode = 505;
            message = e.getMessage().toUpperCase();
            LOGGER.error("Fall into error while getting widget by name,  {}", widgetName);
        }
        return Response.status(statusCode).entity(message).build();

    }
}

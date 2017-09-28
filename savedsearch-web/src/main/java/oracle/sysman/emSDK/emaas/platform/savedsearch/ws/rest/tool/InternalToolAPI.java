package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.tool;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsDatabaseUnavailException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.metadata.MetadataRefreshAPI;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.BasicServiceMalfunctionException;
import oracle.sysman.emSDK.emaas.platform.tenantmanager.model.tenant.TenantIdProcessor;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;

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
        }catch(Exception e){
            statusCode = 505;
            message = "Fall into error while getting widget by name, " + widgetName;
            LOGGER.error(e);
        }
        return Response.status(statusCode).entity(message).build();

    }
    
    @DELETE
    @Path("offboard/{tenantName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTenant(@PathParam("tenantName") String tenantName){
        LogUtil.getInteractionLogger().info("Service calling to (DELETE) /savedsearch/v1/offboard/{}", tenantName);
        int statusCode = 200;
        StringBuilder message = new StringBuilder();
        if (tenantName == null || tenantName.isEmpty()) {
            message.append("{\"errorMsg\":\"BAD REQUEST. PLEASE PROVIDE THE TENANT NAME.\"}");
            return Response.status(400).entity(message.toString()).build();
        }
        
        try {
            if (!DependencyStatus.getInstance().isDatabaseUp()) {
                throw new EMAnalyticsDatabaseUnavailException();
            }
            
            Long internalTenantId = TenantIdProcessor.getInternalTenantIdFromOpcTenantId(tenantName);
            LOGGER.info("Get internal tenant id {} for opc tenant id {}", internalTenantId, tenantName);
            if(internalTenantId == null) {
                throw new BasicServiceMalfunctionException("Tenant id does not exist.", "SavedSearch");
            }
            
            TenantManager tenantManager  = TenantManager.getInstance();
            tenantManager.cleanTenant(internalTenantId);
            message.append(tenantName).append(" has been deleted!");
        } catch (BasicServiceMalfunctionException basicEx) {
            statusCode = 404;
            message.append("{\"errorMsg\":\"Tenant Id [").append(tenantName).append("] does not exist.\"}");
            LOGGER.error("Tenant Id {} does not exist: {}", tenantName, basicEx.getMessage());
        } catch (EMAnalyticsFwkException e) {
            statusCode = 500;
            message.append("{\"errorMsg\":\"Fall into error while deleting tenant [").append(tenantName).append("] because: ")
                    .append(e.getMessage().toUpperCase()).append("\"}");
            LOGGER.error("Fall into error while deleting tenant [{}] because: {}", tenantName, e.getMessage());
        } catch(Exception ex) {
            statusCode = 500;
            message.append("{\"errorMsg\":\"Fall into error while deleting tenant [").append(tenantName).append("] because: ")
                    .append(ex.getMessage().toUpperCase()).append("\"}");
            LOGGER.error("Fall into error while deleting tenant [{}] because: {}", tenantName, ex.getMessage());
        }
        return Response.status(statusCode).entity(message.toString()).build();
    }
}

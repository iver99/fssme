package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.metadata;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsDatabaseUnavailException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataRetriever;
import oracle.sysman.emaas.platform.savedsearch.metadata.MetaDataStorer;
import oracle.sysman.emaas.platform.savedsearch.services.DependencyStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jettison.json.JSONArray;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


/**
 * Created by xiadai on 2017/5/3.
 */
@Path("refresh")
public class MetadataRefreshAPI {
    private static final Logger LOGGER = LogManager.getLogger(MetadataRefreshAPI.class);

    @PUT
    @Path("oob/{serviceName}")
    public Response refreshOOB(@PathParam("serviceName") String serviceName) {
        LogUtil.getInteractionLogger().info("Service calling to (PUT) /savedsearch/v1/refresh/oob/{}", serviceName);
        String returnMessage = "Refresh the OOB Widgets successfully.";
        int statusCode = 200;
        List<SearchImpl> oobWidgetList;
        LOGGER.debug("Get Widget List From integrator.");
        try {
            if (!DependencyStatus.getInstance().isDatabaseUp()) {
                throw new EMAnalyticsDatabaseUnavailException();
            }
             oobWidgetList = new MetaDataRetriever().getOobWidgetListByServiceName(serviceName);
             LOGGER.debug("Store Widget List.");
             MetaDataStorer.storeOobWidget(oobWidgetList);
        } catch (EMAnalyticsFwkException e) {
             statusCode = 500;
             returnMessage = e.getLocalizedMessage();
        }

        return Response.status(statusCode).entity(returnMessage).build();
    }


}


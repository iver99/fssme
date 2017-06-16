package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.metadata;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.SearchImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.EntityJsonUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsDatabaseUnavailException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.thread.MetadataRefreshRunnable;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.thread.NlsRefreshRunnable;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.thread.OobRefreshRunnable;
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
        MetadataRefreshRunnable oobRunnable = new OobRefreshRunnable();
        oobRunnable.setServiceName(serviceName);
        Thread thread = new Thread(oobRunnable, "Refresh " + serviceName + " OOB.");
        thread.start();
        return Response.ok().build();
    }

    @PUT
    @Path("nls/{serviceName}")
    public Response refreshNLS(@PathParam("serviceName") String serviceName) {
        LOGGER.error("Starting a new thread for fresh {} resource bundles.", serviceName);
        MetadataRefreshRunnable nlsRunnable = new NlsRefreshRunnable();
        nlsRunnable.setServiceName(serviceName);
        Thread thread = new Thread(nlsRunnable, "Refresh " + serviceName + " resource bundles.");
        thread.start();
        return Response.ok().build();
    }


}


package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.metadata;

import java.util.concurrent.ExecutorService;

import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.thread.MetadataRefreshRunnable;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.thread.NlsRefreshRunnable;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.thread.OobRefreshRunnable;
import oracle.sysman.emaas.platform.savedsearch.threadpool.ParallelThreadPool;


/**
 * Created by xiadai on 2017/5/3.
 */
@Path("refresh")
public class MetadataRefreshAPI {
    
    @PUT
    @Path("oob/{serviceName}")
    public Response refreshOOB(@PathParam("serviceName") String serviceName) {
        LogUtil.getInteractionLogger().info("Service calling to (PUT) /savedsearch/v1/refresh/oob/{}", serviceName);
        MetadataRefreshRunnable oobRunnable = new OobRefreshRunnable();
        oobRunnable.setServiceName(serviceName);
        ExecutorService pool = ParallelThreadPool.getThreadPool();
        pool.submit(oobRunnable);
        return Response.ok().build();
    }

    @PUT
    @Path("nls/{serviceName}")
    public Response refreshNLS(@PathParam("serviceName") String serviceName) {
        LogUtil.getInteractionLogger().info("Service calling to (PUT) /savedsearch/v1/refresh/nls/{}", serviceName);
        MetadataRefreshRunnable nlsRunnable = new NlsRefreshRunnable();
        nlsRunnable.setServiceName(serviceName);
        ExecutorService pool = ParallelThreadPool.getThreadPool();
        pool.submit(nlsRunnable);
        return Response.ok().build();
    }
}


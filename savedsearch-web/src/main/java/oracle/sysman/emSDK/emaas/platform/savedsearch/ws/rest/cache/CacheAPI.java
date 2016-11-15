package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.cache;

import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.CacheManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru.CacheFactory;
import oracle.sysman.emSDK.emaas.platform.savedsearch.cache.lru.CacheUnit;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.cache.CacheException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.cache.CacheGroupNameEmptyException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.cache.CacheGroupNotFoundException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by chehao on 2016/11/10.
 * emcpssf409 introduce internal API to change server side cache settings
 */
@Path("/cache")
public class CacheAPI {
    private static final Logger LOGGER = LogManager.getLogger(CacheAPI.class);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCacheGroups()
    {
        LOGGER.info("Service to call [GET] /v1/cache");
        ConcurrentHashMap<String, CacheUnit> cacheUnitMap = CacheFactory.getCacheUnitMap();
        List<CacheUnit> cacheUnitList = new ArrayList<CacheUnit>();
        Iterator<Map.Entry<String,CacheUnit>> iterator= cacheUnitMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, CacheUnit> entry = iterator.next();
            cacheUnitList.add(entry.getValue());
        }
        return Response.status(Response.Status.OK).entity( JsonUtil.buildNormalMapper().toJson(cacheUnitList)).build();

    }

    @GET
    @Path("/{cacheGroupName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCacheGroups(@PathParam("cacheGroupName") String cacheGroupName){
        LOGGER.info("Service to call [GET] /v1/cache/{}",cacheGroupName);
        try{
            if(cacheGroupName == null || "".equals(cacheGroupName)){
                throw new CacheGroupNameEmptyException();
            }
            ConcurrentHashMap<String,CacheUnit> cacheUnitMap= CacheFactory.getCacheUnitMap();
            CacheUnit cu=cacheUnitMap.get(cacheGroupName);
            if(cu == null){
                throw new CacheGroupNotFoundException();
            }
            return Response.status(Response.Status.OK).entity( JsonUtil.buildNormalMapper().toJson(cu)).build();
        }catch(CacheException e){
            LOGGER.error(e.getLocalizedMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Cannot find given cache group!\"}").build();
        }
    }

    /**
     * this action will eliminate all cache groups
     * @return
     */
    @PUT
    @Path("clearCache")
    @Produces(MediaType.APPLICATION_JSON)
    public Response clearAllCacheGroup(){
        LOGGER.info("Service to call [PUT] /v1/cache/clearCache");
        //clear all cache group
        CacheFactory.clearAllCacheGroup();
        ConcurrentHashMap<String, CacheUnit> cacheUnitMap = CacheFactory.getCacheUnitMap();
        List<CacheUnit> cacheUnitList = new ArrayList<CacheUnit>();
        Iterator<Map.Entry<String,CacheUnit>> iterator= cacheUnitMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, CacheUnit> entry = iterator.next();
            cacheUnitList.add(entry.getValue());
        }
        return Response.status(Response.Status.OK).entity(JsonUtil.buildNormalMapper().toJson(cacheUnitList)).build();
    }


    @PUT
    @Path("clearCache/{cacheGroupName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response clearCacheGroup(@PathParam("cacheGroupName") String cacheGroupName){
        LOGGER.info("Service to call [PUT] /v1/cache/clearCache/{}",cacheGroupName);
        try{
            if(cacheGroupName == null || "".equals(cacheGroupName)){
                throw new CacheGroupNameEmptyException();
            }
            CacheFactory.getCacheUnitMap().get(cacheGroupName).clearCache();
            ConcurrentHashMap<String,CacheUnit> cacheUnitMap= CacheFactory.getCacheUnitMap();
            CacheUnit cu=cacheUnitMap.get(cacheGroupName);
            if(cu == null){
                throw new CacheGroupNotFoundException();
            }
            return Response.status(Response.Status.OK).entity(JsonUtil.buildNormalMapper().toJson(cu)).build();
        }catch(CacheException e){
            LOGGER.error(e.getLocalizedMessage(), e);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"msg\":\"Cannot find given cache group!\"}").build();
        }
    }

    @PUT
    @Path("enable")
    @Produces(MediaType.APPLICATION_JSON)
    public Response enableCache() {
        CacheManager.getInstance().enableCacheManager();
        LOGGER.info("Cache Manager is turing ON!");
        Response resp = Response.status(Response.Status.OK).build();
        return resp;
    }

    @PUT
    @Path("disable")
    @Produces(MediaType.APPLICATION_JSON)
    public Response disableCache() {
        CacheManager.getInstance().disableCacheManager();
        LOGGER.info("Cache Manager is turing OFF!");
        Response resp = Response.status(Response.Status.OK).build();
        return resp;
    }

}

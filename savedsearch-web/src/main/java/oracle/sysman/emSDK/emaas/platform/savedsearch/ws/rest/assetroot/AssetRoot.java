package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.assetroot;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.LogUtil;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.RegistryLookupUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.exception.EMAnalyticsFwkException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.*;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JsonUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.StringUtil;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by xiadai on 2016/7/8.
 */
@Path("assetroot")
public class AssetRoot {

    @Context
    UriInfo uri;

    @GET
    @Path("{id: [0-9]*}")
    public Response getAssetRoot(@PathParam("id") long searchid){
        LogUtil.getInteractionLogger().info("Service calling to (GET) /savedsearch/v1/assetroot/{}",
                searchid);
        SearchManager searchManager = SearchManager.getInstance();
        CategoryManager categoryManager = CategoryManager.getInstance();
        String serviceName;
        String version;
        String rel;
        try{
            Search search = searchManager.getSearch(searchid);
            if(search.getCategoryId()==null){
                return Response.status(Response.Status.NOT_FOUND).entity("Search with id "+searchid
                        +"not found ").build();
            }
            Category category = categoryManager.getCategory(search.getCategoryId());
            serviceName = category.getProviderName();
            version = category.getProviderVersion();
            rel = category.getProviderAssetRoot();
            if(StringUtil.isEmpty(serviceName)||StringUtil.isEmpty(version)|StringUtil.isEmpty(rel)){
                return Response.status(Response.Status.NOT_FOUND).entity("Category not found").build();
            }
            if("1.0".equals(version)){
                version = version+"+";
            }
            oracle.sysman.emSDK.emaas.platform.servicemanager.registry.info.Link link = RegistryLookupUtil.getServiceExternalLink(serviceName,version,rel, TenantContext.getContext().gettenantName());
            if(link == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Failed:"+serviceName+","+version+","+rel+","+TenantContext.getContext().gettenantName()).build();
            }
            link = RegistryLookupUtil.replaceWithVanityUrl(link, TenantContext.getContext().gettenantName(),serviceName);
            if(link == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("URL cannot be splited").build();
            }
           return Response.status(Response.Status.OK).entity(JsonUtil.buildNormalMapper().toJson(link)).build();
        }catch (EMAnalyticsFwkException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }

}

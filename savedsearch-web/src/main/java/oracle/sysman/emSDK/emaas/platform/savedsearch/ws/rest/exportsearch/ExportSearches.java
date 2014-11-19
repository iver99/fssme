package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exportsearch;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBContext;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ExportSearchSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchManager;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch.ImportSearchSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.XMLUtil;

import org.apache.log4j.Logger;

@Path("exportsearches")
public class ExportSearches {

	private static final Logger _logger = Logger.getLogger(ExportSearchSet.class);
	
	private final String resourcePath = "oracle/sysman/emSDK/emaas/platform/savedsearch/ws/rest/importsearch/search.xsd";
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GET		
	@Produces("application/xml")
	public Response getallSearchByCategoyId(@QueryParam("categoryId") long  categoryId)
	{
		int statusCode = 200;
		Response res = null;
		String msg="";
		String data ="";
		if (categoryId <=0) {
			return res = Response.status(Status.BAD_REQUEST).entity("Please specify valid category id").build();
		}
		res = Response.ok().build();
		try
		{
			SearchManager mgr = SearchManager.getInstance();
			List<Search> searchList = mgr.getSearchListByCategoryId(categoryId);
			ExportSearchSet exList = new ExportSearchSet();
			ArrayList tmp = new ArrayList(searchList);			
			exList.setSearchSet(tmp);
			JAXBContext context = JAXBContext.newInstance(ExportSearchSet.class);
			InputStream stream = ImportSearchSet.class.getClassLoader().getResourceAsStream(resourcePath);
			data= JAXBUtil.marshal(context, stream, exList);	
			data=processData(data);
			res = Response.status(Status.INTERNAL_SERVER_ERROR).entity(data).build();
		}
		catch (Exception e) {
			_logger.error("Failed to export folders (2)", e);
			msg = "An internal error has occurred  while exporting searches ";			
			res = Response.status(Status.INTERNAL_SERVER_ERROR).entity(msg).build();
			e.printStackTrace();
			return res;
		}
		return Response.status(statusCode).entity(data).build();	
	}
	
	private String processData(String data) throws Exception
	{
		String tmpData = "";		
		tmpData= XMLUtil.changeTagName(data,getRenameElementList());
		tmpData = XMLUtil.removeElement(tmpData,"Search" ,getRemovedElements());
		return tmpData;
	}
	
	private Map <String,String> getRenameElementList()
	{
		Map <String,String> map = new HashMap<String,String>();		
		map.put("search" ,"Search");
		map.put("id" ,"Id");
		map.put("folderId" ,"FolderId");
		map.put("categoryId" ,"CategoryId");
		map.put("description" ,"Description");
		map.put("locked" ,"Locked");
		map.put("uiHidden" ,"UiHidden");
		map.put("name" ,"Name");
		map.put("type" ,"Type");
		map.put("value" ,"Value");
		map.put("metadata" ,"Metadata");
		map.put("queryStr" ,"QueryStr");	
		return map;
	}
	
	private String[] getRemovedElements()
	{
		String elementName [] = {"guid" ,"owner", "createdOn",  "lastModifiedOn", "lastAccessDate", "lastModifiedBy", "systemSearch"};
		return elementName;
	}
	
	
	
	
}

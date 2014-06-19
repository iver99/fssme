package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.importsearch;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.CategoryManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportCategoryImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategorySet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;

@Path("importcategories")
public class ImportCategorySet {
	
	private final String resourcePath="oracle/sysman/emSDK/emaas/platform/savedsearch/ws/rest/importsearch/category.xsd";
	@POST	
	@Produces({ MediaType.APPLICATION_JSON })	
	@Consumes("application/xml") 
	public Response importsCategories(String  xml) {		
		Response res=null;
		if(xml!=null && xml.length()==0)
			return res =Response.status(Status.BAD_REQUEST).entity("Please specify input with valid format").build();
		 res = Response.ok().build();
		String msg = "";
		try {
			InputStream stream=  ImportFolderSet.class.getClassLoader().getResourceAsStream(resourcePath);
			StringBuffer xmlStr = new StringBuffer( xml);
			StringReader sReader =new StringReader( xmlStr.toString() ) ;					 
			CategorySet categories = (CategorySet)JAXBUtil.unmarshal( sReader,stream,JAXBUtil.getJAXBContext(CategorySet.class));
			List <ImportCategoryImpl> list = categories.getCategorySet();
			if(list.size()==0)
		    	return res =Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
			CategoryManagerImpl mgr = (CategoryManagerImpl) CategoryManagerImpl.getInstance();
			List<ImportCategoryImpl> addedList =mgr.saveMultipleCategories(list);
			 JSONArray  jsonArray = new JSONArray();
			    for(ImportCategoryImpl categoryImpl : addedList)
			    {
			    	JSONObject jObj = new JSONObject();
			    	jObj.put("id", categoryImpl.getId());
			    	jObj.put("name", categoryImpl.getName());
			    	jsonArray.put(jObj);
			    }
				res = Response.status(Status.OK).entity(jsonArray).build();					
		}catch (ImportException e) {
			msg = e.getMessage();
			e.printStackTrace();			
			res =Response.status(Status.BAD_REQUEST).entity(msg).build();			
		}catch (Exception e) {
			 msg="An internal error has occurred" ;
			 res =Response.status(Status.INTERNAL_SERVER_ERROR)
					 .entity(msg).build();	
		}
		return res;
	}
}

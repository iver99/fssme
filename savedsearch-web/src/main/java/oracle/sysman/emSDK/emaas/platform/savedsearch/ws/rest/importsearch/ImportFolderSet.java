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
import javax.xml.bind.JAXBContext;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.FolderManagerImpl;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportSearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.exception.ImportException;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;


@Path("importfolders")
public class ImportFolderSet {
	private final String resourcePath="oracle/sysman/emSDK/emaas/platform/savedsearch/ws/rest/importsearch/folder.xsd";
	
	@POST	
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/xml") 
	public Response importsFolders(String  xml) {		
		Response res=null;
		if(xml!=null && xml.length()==0)
			return res =Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
		 res = Response.ok().build();
		String msg = "";
		try {	 
			    JAXBContext jaxbContext =JAXBUtil.getJAXBContext(FolderSet.class);
			    InputStream stream=  ImportFolderSet.class.getClassLoader().getResourceAsStream(resourcePath);
			    StringBuffer xmlStr = new StringBuffer( xml);
				StringReader sReader =new StringReader( xmlStr.toString() ) ;
			    FolderSet folders = (FolderSet)JAXBUtil.unmarshal( sReader,stream,jaxbContext);			    
			    List<FolderImpl>  list =   folders.getFolderSet();
			    if(list.size()==0)
			    	return res =Response.status(Status.BAD_REQUEST).entity(JAXBUtil.VALID_ERR_MESSAGE).build();
			    List<FolderImpl> addedList= FolderManagerImpl.getInstance().saveMultipleFolders(list);
			    JSONArray  jsonArray = new JSONArray();
			    for(FolderImpl folderImpl : addedList)
			    {
			    	JSONObject jObj = new JSONObject();
			    	jObj.put("id", folderImpl.getId());
			    	jObj.put("name", folderImpl.getName());
			    	jsonArray.put(jObj);
			    }
				res = Response.status(Status.OK).entity(jsonArray).build();							
		}catch (ImportException  e) {			
			msg = e.getMessage();
			e.printStackTrace();			
			res =Response.status(Status.BAD_REQUEST).entity(msg).build();
		}catch (Exception e) {
			 msg="An internal error has occurred  while importing folder " ;
			 res =Response.status(Status.INTERNAL_SERVER_ERROR).entity(msg).build();
			 e.printStackTrace();
		}
		return res;
	}
		  
}


/*FolderSet fSet = new FolderSet();
fSet.setFolderSet(rejectedList);				
result=result+JAXBUtil.marshal(jaxbContext,ImportFolderSet.class.getClassLoader().getResourceAsStream(resourcePath), fSet);*/
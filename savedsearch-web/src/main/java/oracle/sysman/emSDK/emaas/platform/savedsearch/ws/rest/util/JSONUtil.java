package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.exception.EMAnalyticsWSException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.annotate.JsonFilter;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;



public class JSONUtil {

		
	public static String ObjectToJSONString(Object object) throws  EMAnalyticsWSException
	{
		return ObjectToJSONString(object, null);
	}
	public static String ObjectToJSONString(Object object, String[] requiredFieldItems) throws EMAnalyticsWSException
	{		
		try{
			
			OutputStream os = new ByteArrayOutputStream();
			ObjectWriter writer ;
			ObjectMapper mapper = new ObjectMapper();
			mapper.setSerializationInclusion(Inclusion.NON_NULL);
			if(requiredFieldItems != null)
			{	
				FilterProvider filters = new SimpleFilterProvider().addFilter("select_prop_by_name",SimpleBeanPropertyFilter.filterOutAllExcept(requiredFieldItems));  
			   	writer = mapper.writer(filters);
			}
			writer = mapper.writer() ;
			
			writer.writeValue(os, object);
			 return os.toString() ;
		}catch(Exception e){
			throw new EMAnalyticsWSException("Error converting to JSON string",EMAnalyticsWSException.JSON_OBJECT_TO_JSON_EXCEPTION,e);
		}
		 
	}
	
	@SuppressWarnings("unchecked")
	public static Object JSONToObject(JSONObject inputJsonObj, Class classname) throws EMAnalyticsWSException
	{	
		try{
		Object obj=null;
		ObjectMapper mapper = new ObjectMapper();
		obj = mapper.readValue(inputJsonObj.toString(), classname);
		return classname.cast(obj);}
		catch(Exception e){
			throw new EMAnalyticsWSException("error converting JSONObject to Object",EMAnalyticsWSException.JSON_JSON_TO_OBJECT,e);
		}
	}

	public static JSONObject ObjectToJSONObject(Object object) throws JSONException,EMAnalyticsWSException
	{
		return ObjectToJSONObject(object,null) ;
		
	}
	public static JSONObject ObjectToJSONObject(Object object, String[] requiredFiledItems) throws JSONException,EMAnalyticsWSException
	{
		
		return new JSONObject(ObjectToJSONString(object,requiredFiledItems) );
	}
	
	public static String getDate(Long timeValue){
		Date date = new Date(timeValue);
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        return fmt.format(date);
	}
		
	@JsonFilter("select_prop_by_name")  
	class PropertyFilterMixIn {} 
    
	
}

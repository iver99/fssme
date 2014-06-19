package oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util;



import java.util.HashMap;
import java.util.Map;
public class XSDErrorUtil {
	
	
	private static final Map<String, String> errorMap;
    static {          
    	 errorMap =  new HashMap<String, String>();    	 
    	 errorMap.put("cvc-complex-type.2.1","The element must not have content according to schema definition.");
    	 errorMap.put("cvc-complex-type.2.4.a","The element which is not described in schema is trying to be used.");
    	 errorMap.put("cvc-complex-type.2.4.b","Child elements are missing from the element.");
    	 errorMap.put("cvc-datatype-valid.1.2.1"," Please specify correct field value for the element."); 
    	 errorMap.put("cvc-type.3.1.3","The value '' of element '' is not valid.");
    }
    private static String [] fldfields ={"id","name","description","parentId","uiHidden","value","type","folderId","categoryId","metadata","queryStr","locked","uiHidden"};
    
    public static String getErrorMessage(String pattern)
    {
    	String result="";
    	String tempResult="";
    	for (Map.Entry<String, String> entry : errorMap.entrySet()) {
    		if(pattern!=null && pattern.contains(entry.getKey())){    			 
    		  for(String sMsg : fldfields){
    			if(pattern.contains(sMsg))
    			{
    				tempResult=entry.getValue();
    			  result =result + tempResult  + System.getProperty("line.separator");	
    			}
    		  }    		  
    		  break;
    		}
    	}
    	return result;    	
    }
}
	
	            
    
	
	
	
	



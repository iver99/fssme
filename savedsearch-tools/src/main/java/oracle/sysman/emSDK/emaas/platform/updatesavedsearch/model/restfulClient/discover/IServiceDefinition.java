package oracle.sysman.emSDK.emaas.platform.updatesavedsearch.model.restfulClient.discover;

import java.util.Map;
import java.util.Properties;

/**
 * Interface to define how a RESTful service could be discovered
 */
public interface IServiceDefinition
{
    /**
     * Returns the name for a service
     * @return
     */
    String getGetName();
    
    /**
     * Returns the version for a service
     * @return
     */
    String getVersion();
    
    /**
     * Returns the characteristics for a service
     * @return
     */
    Map<String, String> getCharacteristics();
    
    /**
     * Return all the properties loaded from servicemanager.properties
     * @return
     */
    Properties getSMProps();
}

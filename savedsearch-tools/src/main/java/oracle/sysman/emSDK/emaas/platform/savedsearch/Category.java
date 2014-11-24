package oracle.sysman.emSDK.emaas.platform.savedsearch;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Category extends Entity
{
   
    private String name;


    public Category()
    {
        super();
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

   
    
}

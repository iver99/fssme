package oracle.sysman.emSDK.emaas.platform.savedsearch;

import javax.xml.bind.annotation.XmlElement;

public class SearchParamEntity
{
    // IMPORTANT: keep the fields in this class the same with what's defined in 
    // saved search restful specification for search parameter: https://confluence.oraclecorp.com/confluence/display/EMS/Saved+Search+Framework+Web+Service+Specifications
    private String name;
    private String value;
    private String attributes;
    private String type;
    
    public SearchParamEntity()
    {
        super();
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @XmlElement(name = "Name")
    public String getName()
    {
        return name;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    @XmlElement(name = "Value")
    public String getValue()
    {
        return value;
    }

    public void setAttributes(String attributes)
    {
        this.attributes = attributes;
    }

    @XmlElement(name = "Attributes" )
    public String getAttributes()
    {
        return attributes;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @XmlElement(name = "Type")
    public String getType()
    {
        return type;
    }
}

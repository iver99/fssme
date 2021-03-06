package oracle.sysman.emSDK.emaas.platform.updatesavedsearch.entity;

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

	@XmlElement(name = "Attributes")
	public String getAttributes()
	{
		return attributes;
	}

	@XmlElement(name = "Name")
	public String getName()
	{
		return name;
	}

	@XmlElement(name = "Type")
	public String getType()
	{
		return type;
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

	public void setName(String name)
	{
		this.name = name;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch;


import javax.xml.bind.annotation.XmlElement;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.ParameterType;


public class SearchParameterDetails 
{
	@XmlElement(name="Attributes")
	private String attributes;

	/**
	 * Sets the attributes for the parameter //TODO: fixup when more symantics is available
	 * 
	 * @param attributes
	 *            , attributes for the parameter
	 *            
	 *            
	 *            
	 */
	
	@XmlElement(name="Name")
	private String name;
	@XmlElement(name="Value")
	private String value;
	@XmlElement(name="Type")
	private ParameterType type;

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof Parameter && ((Parameter) obj).getName().equals(name)) {
			return true;
		}
		if (obj instanceof String && ((String) obj).equals(name)) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the parameter name.
	 * 
	 * @return parameter name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Returns the parameter type.
	 * 
	 * @see ParameterType
	 * @return parameter type
	 */
	public ParameterType getType()
	{
		return type;
	}

	/**
	 * Returns the value of the parameter (<code>null</code> if it has no value).
	 * 
	 * @return value for the parameter
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Sets the name for the parameter.
	 * 
	 * @param name
	 *            parameter name
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * Sets the type of parameter.
	 * 
	 * @see ParameterType
	 * @param type
	 *            parameter type
	 */
	public void setType(ParameterType type)
	{
		this.type = type;
	}

	/**
	 * Sets the value for the parameter (<code>null</code> if it has no value).
	 * 
	 * @param value
	 *            value for the parameter
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
	public void setAttributes(String attributes)
	{
		this.attributes = attributes;
	}

	/**
	 * Returns the attributes of the parameter.
	 * 
	 * @return attributes of the parameter
	 */
	public String getAttributes()
	{
		return attributes;
	}

}

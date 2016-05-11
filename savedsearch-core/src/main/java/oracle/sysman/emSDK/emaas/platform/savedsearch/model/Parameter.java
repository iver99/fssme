package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "name", "value", "type" })
public class Parameter implements Serializable
{
	private static final long serialVersionUID = 527730391145665376L;

	private String name;
	private String value;
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
}

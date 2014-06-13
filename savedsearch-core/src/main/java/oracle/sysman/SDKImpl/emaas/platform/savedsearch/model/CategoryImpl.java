package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;

@XmlRootElement
@XmlType(propOrder = { "id", "name", "description", "defaultFolderId", "parameters" })
public class CategoryImpl implements Category
{
	private Integer id;
	private String name;
	private String description;
	private Integer defaultFolderId;
	private List<Parameter> parameters;

	@Override
	public Integer getDefaultFolderId()
	{
		return defaultFolderId;
	}

	@Override
	public String getDescription()
	{
		return description;
	}

	@Override
	public Integer getId()
	{
		return id;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public List<Parameter> getParameters()
	{
		return parameters;
	}

	@Override
	public void setDefaultFolderId(Integer id)
	{
		defaultFolderId = id;

	}

	@Override
	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	@XmlElement(name = "parameter")
	@XmlElementWrapper(name = "parameters")
	public void setParameters(List<Parameter> parameters)
	{
		this.parameters = parameters;
	}
}

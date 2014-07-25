package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;

@XmlRootElement
@XmlType(propOrder = { "id", "name", "description", "owner", "createdOn", "defaultFolderId", "parameters" })
public class CategoryImpl implements Category
{
	private Integer id;
	private String name;
	private String description;
	private Integer defaultFolderId;
	private List<Parameter> parameters;
	private String owner;
	private Date creationDate;

	/* (non-Javadoc)
	 * @see oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category#getCreationDate()
	 */
	@Override
	public Date getCreatedOn()
	{
		return creationDate;
	}

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

	/* (non-Javadoc)
	 * @see oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category#getOwner()
	 */
	@Override
	public String getOwner()
	{
		return owner;
	}

	@Override
	public List<Parameter> getParameters()
	{
		return parameters;
	}

	/* (non-Javadoc)
	 * @see oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category#setCreationDate(java.util.Date)
	 */
	@Override
	public void setCreatedOn(Date date)
	{
		creationDate = date;

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

	/* (non-Javadoc)
	 * @see oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category#setOwner(java.lang.String)
	 */
	@Override
	public void setOwner(String owner)
	{
		this.owner = owner;

	}

	@Override
	@XmlElement(name = "parameter")
	@XmlElementWrapper(name = "parameters")
	public void setParameters(List<Parameter> parameters)
	{
		this.parameters = parameters;
	}
}

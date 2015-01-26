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
@XmlType(propOrder = { "id", "name", "description", "owner", "createdOn", "defaultFolderId", "providerName", "providerVersion",
		"providerDiscovery", "providerAssetRoot", "parameters" })
public class CategoryImpl implements Category
{
	private Integer id;
	private String name;
	private String description;
	private Integer defaultFolderId;
	private List<Parameter> parameters;
	private String owner;
	private Date creationDate;
	private String providerName;
	private String providerVersion;
	private String providerDiscovery;
	private String providerAssetRoot;

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

	@Override
	public String getProviderAssetRoot()
	{
		return providerAssetRoot;
	}

	@Override
	public String getProviderDiscovery()
	{
		return providerDiscovery;
	}

	@Override
	public String getProviderName()
	{
		return providerName;
	}

	@Override
	public String getProviderVersion()
	{
		return providerVersion;
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

	@Override
	public void setProviderAssetRoot(String providerAssetRoot)
	{
		this.providerAssetRoot = providerAssetRoot;
	}

	@Override
	public void setProviderDiscovery(String providerDiscovery)
	{
		this.providerDiscovery = providerDiscovery;
	}

	@Override
	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	@Override
	public void setProviderVersion(String providerVersion)
	{
		this.providerVersion = providerVersion;
	}
}

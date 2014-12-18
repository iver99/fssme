package oracle.sysman.emSDK.emaas.platform.updatesavedsearch;

import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.entity.Entity;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category extends Entity
{

	private String name;

	public Category()
	{
		super();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}

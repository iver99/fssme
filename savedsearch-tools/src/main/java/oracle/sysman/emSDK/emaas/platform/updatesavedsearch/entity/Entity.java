package oracle.sysman.emSDK.emaas.platform.updatesavedsearch.entity;

import javax.xml.bind.annotation.XmlElement;

public abstract class Entity
{
	private String id;

	public Entity()
	{
		super();
	}

	@XmlElement(name = "Id")
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}

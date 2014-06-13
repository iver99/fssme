package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Category")
public class ImportCategoryImpl extends CategoryImpl
{

	private Object obj;

	@XmlElements(value = { @XmlElement(name = "Folder", type = FolderImpl.class),
			@XmlElement(name = "defaultFolderId", type = Integer.class) })
	public Object getFolder()
	{
		return obj;
	}

	public void setFolder(Object o)
	{
		obj = o;
	}

}

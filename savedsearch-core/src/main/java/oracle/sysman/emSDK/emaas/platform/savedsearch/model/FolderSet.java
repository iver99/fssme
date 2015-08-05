package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;

@XmlRootElement(name = "FolderSet")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = {})
public class FolderSet
{

	private List<FolderDetails> Folder = null;

	@XmlElement(name = "Folder")
	// @XmlElementWrapper(name = "FolderSet")    
	public List<FolderDetails> getFolderSet()
	{
		if (Folder == null) {
			Folder = new ArrayList<FolderDetails>();
		}
		return Folder;
	}

	public void setFolderSet(List<FolderDetails> d)
	{
		Folder = d;
	}
}
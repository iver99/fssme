package oracle.sysman.emSDK.emaas.platform.updatesavedsearch;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import oracle.sysman.emSDK.emaas.platform.updatesavedsearch.entity.SearchEntity;

@XmlRootElement(name = "SearchSet")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class ExportSearchSet
{

	private List<SearchEntity> search = null;

	@XmlElement(name = "Search")
	public List<SearchEntity> getSearchSet()
	{
		if (search == null) {
			search = new ArrayList<SearchEntity>();
		}
		return search;
	}

	public void setSearchSet(List<SearchEntity> d)
	{
		search = d;
	}
}
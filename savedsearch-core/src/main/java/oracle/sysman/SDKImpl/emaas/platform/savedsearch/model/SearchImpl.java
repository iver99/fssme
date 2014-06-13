package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;

@XmlRootElement
@XmlType(propOrder = { "id", "name", "description", "guid", "folderId", "categoryId", "owner", "creationDate", "lastModifiedBy",
		"lastModificationDate", "metadata", "queryStr", "locked", "uiHidden", "parameters" })
public class SearchImpl extends SearchSummaryImpl implements Search
{

	protected List<SearchParameter> parameters;
	protected String metadata;
	protected String queryStr;
	protected boolean locked;
	protected boolean uiHidden;

	@Override
	public String getMetadata()
	{
		return metadata;
	}

	@Override
	@XmlElement(name = "SearchParameter")
	@XmlElementWrapper(name = "SearchParameters")
	public List<SearchParameter> getParameters()
	{
		return parameters;
	}

	@Override
	public String getQueryStr()
	{
		return queryStr;
	}

	@Override
	public boolean isLocked()
	{
		return locked;
	}

	@Override
	public boolean isUiHidden()
	{
		return uiHidden;
	}

	@Override
	public void setLocked(boolean locked)
	{
		this.locked = locked;
	}

	@Override
	public void setMetadata(String metadata)
	{
		this.metadata = metadata;
	}

	@Override
	public void setParameters(List<SearchParameter> parameters)
	{
		this.parameters = parameters;
	}

	@Override
	public void setQueryStr(String queryStr)
	{
		this.queryStr = queryStr;
	}

	@Override
	public void setUiHidden(boolean uiHidden)
	{
		this.uiHidden = uiHidden;
	}
}

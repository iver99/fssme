package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;

@XmlRootElement
@XmlType(propOrder = { "id", "name", "description", "guid", "folderId", "categoryId", "owner", "createdOn", "lastModifiedOn",
		"lastAccessDate", "lastModifiedBy", "metadata", "queryStr", "systemSearch", "locked", "isWidget", "uiHidden",
		"parameters" })
public class SearchImpl extends SearchSummaryImpl implements Search
{
	private static final long serialVersionUID = -4486286542760671627L;

	protected List<SearchParameter> parameters;
	protected String metadata;
	protected String queryStr;
	protected boolean locked;
	protected boolean uiHidden;
	protected boolean isWidget;
	protected boolean isEditable;

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}

	@Override
	public boolean getIsWidget()
	{
		return isWidget;
	}

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
	public void setIsWidget(boolean isWidget)
	{
		this.isWidget = isWidget;
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

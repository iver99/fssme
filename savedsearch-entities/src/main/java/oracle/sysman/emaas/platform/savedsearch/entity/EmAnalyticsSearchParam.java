package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * The persistent class for the EMS_ANALYTICS_SEARCH_PARAMS database table.
 */

@Entity
@Multitenant
@IdClass(EmAnalyticsSearchParamPK.class)
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = "tenant", length = 32, primaryKey = true)
@Table(name = "EMS_ANALYTICS_SEARCH_PARAMS")
public class EmAnalyticsSearchParam implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SEARCH_ID", insertable = false, updatable = false, nullable = false)
	private long searchId;

	@Id
	private String name;

	@Column(name = "PARAM_ATTRIBUTES")
	private String paramAttributes;

	@Column(name = "PARAM_TYPE")
	private BigDecimal paramType;

	@Lob()
	@Column(name = "PARAM_VALUE_CLOB")
	private String paramValueClob;

	@Column(name = "PARAM_VALUE_STR")
	private String paramValueStr;

	@ManyToOne(optional = false)
	@JoinColumns({ @JoinColumn(name = "SEARCH_ID", referencedColumnName = "SEARCH_ID"),
			@JoinColumn(name = "TENANT_ID", referencedColumnName = "TENANT_ID", insertable = false, updatable = false) })
	private EmAnalyticsSearch emAnalyticsSearch;

	public EmAnalyticsSearchParam()
	{
	}

	public EmAnalyticsSearch getEmAnalyticsSearch()
	{
		return emAnalyticsSearch;
	}

	public String getName()
	{
		return name;
	}

	//bi-directional many-to-one association to EmAnalyticsSearch

	public String getParamAttributes()
	{
		return paramAttributes;
	}

	public BigDecimal getParamType()
	{
		return paramType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

	public String getParamValueClob()
	{
		return paramValueClob;
	}

	@Lob
	@Basic(fetch = FetchType.EAGER)
	public String getParamValueStr()
	{
		return paramValueStr;
	}

	public long getSearchId()
	{
		return searchId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	*/
	public void setEmAnalyticsSearch(EmAnalyticsSearch emAnalyticsSearch)
	{
		this.emAnalyticsSearch = emAnalyticsSearch;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setParamAttributes(String paramAttributes)
	{
		this.paramAttributes = paramAttributes;
	}

	public void setParamType(BigDecimal paramType)
	{
		this.paramType = paramType;
	}

	public void setParamValueClob(String paramValueClob)
	{
		this.paramValueClob = paramValueClob;
	}

	public void setParamValueStr(String paramValueStr)
	{
		this.paramValueStr = paramValueStr;
	}

	public void setSearchId(long searchId)
	{
		this.searchId = searchId;
	}

}
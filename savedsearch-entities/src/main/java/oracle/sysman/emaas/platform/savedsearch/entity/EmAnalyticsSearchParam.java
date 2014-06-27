package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;

/**
 * The persistent class for the EMS_ANALYTICS_SEARCH_PARAMS database table.
 */

@Entity
@Table(name = "EMS_ANALYTICS_SEARCH_PARAMS")
public class EmAnalyticsSearchParam implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmAnalyticsSearchParamPK id;

	@Column(name = "PARAM_ATTRIBUTES")
	private String paramAttributes;

	@Column(name = "PARAM_TYPE")
	private BigDecimal paramType;

	@Lob()
	@Column(name = "PARAM_VALUE_CLOB")
	private String paramValueClob;

	@Column(name = "PARAM_VALUE_STR")
	private String paramValueStr;

	//bi-directional many-to-one association to EmAnalyticsSearch

	@ManyToOne(optional = false)
	@JoinColumn(name = "SEARCH_ID", referencedColumnName = "SEARCH_ID")
	private EmAnalyticsSearch emAnalyticsSearch;

	public EmAnalyticsSearchParam()
	{
	}

	public EmAnalyticsSearchParamPK getId()
	{
		return this.id;
	}

	public void setId(EmAnalyticsSearchParamPK id)
	{
		this.id = id;
	}

	public String getParamAttributes()
	{
		return this.paramAttributes;
	}

	public void setParamAttributes(String paramAttributes)
	{
		this.paramAttributes = paramAttributes;
	}

	public BigDecimal getParamType()
	{
		return this.paramType;
	}

	public void setParamType(BigDecimal paramType)
	{
		this.paramType = paramType;
	}

	public String getParamValueClob()
	{
		return this.paramValueClob;
	}

	public void setParamValueClob(String paramValueClob)
	{
		this.paramValueClob = paramValueClob;
	}

	@Lob
	@Basic(fetch = FetchType.EAGER)
	public String getParamValueStr()
	{
		return this.paramValueStr;
	}

	public void setParamValueStr(String paramValueStr)
	{
		this.paramValueStr = paramValueStr;
	}

	public EmAnalyticsSearch getEmAnalyticsSearch()
	{
		return this.emAnalyticsSearch;
	}

	public void setEmAnalyticsSearch(EmAnalyticsSearch emAnalyticsSearch)
	{
		this.emAnalyticsSearch = emAnalyticsSearch;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((paramAttributes == null) ? 0 : paramAttributes.hashCode());
		result = prime * result + ((paramType == null) ? 0 : paramType.hashCode());
		result = prime * result + ((paramValueClob == null) ? 0 : paramValueClob.hashCode());
		result = prime * result + ((paramValueStr == null) ? 0 : paramValueStr.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EmAnalyticsSearchParam)) {
			return false;
		}
		EmAnalyticsSearchParam other = (EmAnalyticsSearchParam) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else if (!id.equals(other.id)) {
			return false;
		}
		if (paramAttributes == null) {
			if (other.paramAttributes != null) {
				return false;
			}
		}
		else if (!paramAttributes.equals(other.paramAttributes)) {
			return false;
		}
		if (paramType == null) {
			if (other.paramType != null) {
				return false;
			}
		}
		else if (!paramType.equals(other.paramType)) {
			return false;
		}
		if (paramValueClob == null) {
			if (other.paramValueClob != null) {
				return false;
			}
		}
		else if (!paramValueClob.equals(other.paramValueClob)) {
			return false;
		}
		if (paramValueStr == null) {
			if (other.paramValueStr != null) {
				return false;
			}
		}
		else if (!paramValueStr.equals(other.paramValueStr)) {
			return false;
		}
		return true;
	}

}
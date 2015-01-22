package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = "ssftenant.id", length = 32)
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

	public EmAnalyticsSearch getEmAnalyticsSearch()
	{
		return emAnalyticsSearch;
	}

	public EmAnalyticsSearchParamPK getId()
	{
		return id;
	}

	public String getParamAttributes()
	{
		return paramAttributes;
	}

	public BigDecimal getParamType()
	{
		return paramType;
	}

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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		result = prime * result + (paramAttributes == null ? 0 : paramAttributes.hashCode());
		result = prime * result + (paramType == null ? 0 : paramType.hashCode());
		result = prime * result + (paramValueClob == null ? 0 : paramValueClob.hashCode());
		result = prime * result + (paramValueStr == null ? 0 : paramValueStr.hashCode());
		return result;
	}

	public void setEmAnalyticsSearch(EmAnalyticsSearch emAnalyticsSearch)
	{
		this.emAnalyticsSearch = emAnalyticsSearch;
	}

	public void setId(EmAnalyticsSearchParamPK id)
	{
		this.id = id;
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

}
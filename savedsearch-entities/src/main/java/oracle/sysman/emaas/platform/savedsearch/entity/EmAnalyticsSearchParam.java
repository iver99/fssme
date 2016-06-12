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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * The persistent class for the EMS_ANALYTICS_SEARCH_PARAMS database table.
 */

@Entity
@Multitenant(includeCriteria = false)
@IdClass(EmAnalyticsSearchParamPK.class)
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = "tenant", length = 32, primaryKey = true)
@Table(name = "EMS_ANALYTICS_SEARCH_PARAMS")
@NamedQueries({
	@NamedQuery(name = "SearchParam.getParamByName", query = "SELECT e FROM EmAnalyticsSearchParam e where e.searchId = :searchId AND e.name = :name "),
})
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

	@Id
	@Column(name = "TENANT_ID", insertable = false, updatable = false)
	private Long tenantId;

	@ManyToOne(optional = false)
	@JoinColumns({ @JoinColumn(name = "SEARCH_ID", referencedColumnName = "SEARCH_ID"),
			@JoinColumn(name = "TENANT_ID", referencedColumnName = "TENANT_ID", insertable = false, updatable = false) })
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
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!name.equals(other.name)) {
			return false;
		}
		if (other.searchId != searchId) {
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

	public String getName()
	{
		return name;
	}

	public String getParamAttributes()
	{
		return paramAttributes;
	}

	//bi-directional many-to-one association to EmAnalyticsSearch

	public BigDecimal getParamType()
	{
		return paramType;
	}

	public String getParamValueClob()
	{
		return paramValueClob;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

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

	/**
	 * @return the tenantId
	 */
	public Long getTenantId()
	{
		return tenantId;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (searchId ^ searchId >>> 32);
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (paramAttributes == null ? 0 : paramAttributes.hashCode());
		result = prime * result + (paramType == null ? 0 : paramType.hashCode());
		result = prime * result + (paramValueClob == null ? 0 : paramValueClob.hashCode());
		result = prime * result + (paramValueStr == null ? 0 : paramValueStr.hashCode());
		return result;
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

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(Long tenantId)
	{
		this.tenantId = tenantId;
	}

}
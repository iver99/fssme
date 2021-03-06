package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;
/**
 * The persistent class for the EMS_ANALYTICS_SEARCH_PARAMS database table.
 */

@Entity
@IdClass(EmAnalyticsSearchParamPK.class)
@Table(name = "EMS_ANALYTICS_SEARCH_PARAMS")
@AdditionalCriteria("this.tenantId = :tenant or this.tenantId = -11")
@NamedQueries({
    @NamedQuery(name = "SearchParam.getSearchListByParam", query = "SELECT e.emAnalyticsSearch FROM EmAnalyticsSearchParam e where e.name = :name AND e.paramValueStr = :value"),
    @NamedQuery(name = "SearchParam.getParamByName", query = "SELECT e FROM EmAnalyticsSearchParam e where e.searchId = :searchId AND e.name = :name "),
    @NamedQuery(name = "SearchParam.deleteParamsBySearchIds", query = "DELETE FROM EmAnalyticsSearchParam e where e.searchId in :searchIds")
})
public class EmAnalyticsSearchParam implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SEARCH_ID", insertable = false, updatable = false, nullable = false)
	private BigInteger searchId;
	
	@Column(name = "DELETED", nullable = false, length = 1)
	private Boolean deleted;

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
	@Column(name = "TENANT_ID", updatable = false)
	private Long tenantId;

	@ManyToOne(optional = false)
	@JoinColumns({ @JoinColumn(name = "SEARCH_ID", referencedColumnName = "SEARCH_ID"),
			@JoinColumn(name = "TENANT_ID", referencedColumnName = "TENANT_ID", insertable = false, updatable = false) })
	private EmAnalyticsSearch emAnalyticsSearch;

	public EmAnalyticsSearchParam()
	{
		this.deleted = false;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFICATION_DATE")
	private Date lastModificationDate;

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
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
	
	

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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

	public BigInteger getSearchId()
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
		result = prime * result + (searchId == null ? 0 : (int) (searchId.intValue() ^ searchId.intValue() >>> 32));
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

	public void setSearchId(BigInteger searchId)
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
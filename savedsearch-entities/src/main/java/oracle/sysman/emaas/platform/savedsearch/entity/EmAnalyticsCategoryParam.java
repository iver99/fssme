package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.*;

import org.eclipse.persistence.annotations.AdditionalCriteria;
import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.QueryRedirectors;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * The persistent class for the EMS_ANALYTICS_CATEGORY_PARAMS database table.
 */
@Entity
@Multitenant(includeCriteria = false)
@IdClass(EmAnalyticsCategoryParamPK.class)
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = "tenant", length = 32, primaryKey = true)
@Table(name = "EMS_ANALYTICS_CATEGORY_PARAMS")
public class EmAnalyticsCategoryParam extends EmBaseEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CATEGORY_ID", insertable = false, updatable = false, nullable = false)
	private BigInteger categoryId;

	@Id
	private String name;

	@Column(name = "PARAM_VALUE")
	private String value;

	@Id
	@Column(name = "TENANT_ID", insertable = false, updatable = false)
	private Long tenantId;
	
	@Column(name = "DELETED", nullable = false, length = 1)
	private Boolean deleted;

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

	//bi-directional many-to-one association to EmAnalyticsCategory
	@ManyToOne(optional = false)
	@JoinColumns({ @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID"),
			@JoinColumn(name = "TENANT_ID", referencedColumnName = "TENANT_ID", insertable = false, updatable = false) })
	private EmAnalyticsCategory emAnalyticsCategory;

	public EmAnalyticsCategoryParam()
	{
		this.deleted = false;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EmAnalyticsCategoryParam)) {
			return false;
		}
		EmAnalyticsCategoryParam other = (EmAnalyticsCategoryParam) obj;
		if (categoryId != other.categoryId) {
			return false;

		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		}
		else if (!name.equals(other.name)) {
			return false;
		}

		if (value == null) {
			if (other.value != null) {
				return false;
			}
		}
		else if (!value.equals(other.value)) {
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

	public BigInteger getCategoryId()
	{
		return categoryId;
	}

	/*public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}*/
	public EmAnalyticsCategory getEmAnalyticsCategory()
	{
		return emAnalyticsCategory;
	}

	public String getName()
	{
		return name;
	}

	//@Column(name="NAME")
	//private String name;

	/**
	 * @return the tenantId
	 */
	public Long getTenantId()
	{
		return tenantId;
	}

	public String getValue()
	{
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;

		result = prime * result + (int) (categoryId.intValue() ^ categoryId.intValue() >>> 32);
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
	}

	public void setCategoryId(BigInteger categoryId)
	{
		this.categoryId = categoryId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */

	public void setEmAnalyticsCategory(EmAnalyticsCategory emAnalyticsCategory)
	{
		this.emAnalyticsCategory = emAnalyticsCategory;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @param tenantId
	 *            the tenantId to set
	 */
	public void setTenantId(Long tenantId)
	{
		this.tenantId = tenantId;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
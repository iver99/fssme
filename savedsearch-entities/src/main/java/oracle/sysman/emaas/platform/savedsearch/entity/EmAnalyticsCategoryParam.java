package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * The persistent class for the EMS_ANALYTICS_CATEGORY_PARAMS database table.
 */
@Entity
@Multitenant
@IdClass(EmAnalyticsCategoryParamPK.class)
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = "tenant", length = 32, primaryKey = true)
@Table(name = "EMS_ANALYTICS_CATEGORY_PARAMS")
public class EmAnalyticsCategoryParam implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CATEGORY_ID", insertable = false, updatable = false, nullable = false)
	private long categoryId;

	@Id
	private String name;

	@Column(name = "PARAM_VALUE")
	private String value;

	//bi-directional many-to-one association to EmAnalyticsCategory
	@ManyToOne(optional = false)
	@JoinColumns({ @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID"),
			@JoinColumn(name = "TENANT_ID", referencedColumnName = "TENANT_ID", insertable = false, updatable = false) })
	private EmAnalyticsCategory emAnalyticsCategory;

	public EmAnalyticsCategoryParam()
	{
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

	public long getCategoryId()
	{
		return categoryId;
	}

	//@Column(name="NAME")
	//private String name;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

	public String getValue()
	{
		return value;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;

		result = prime * result + (int) (categoryId ^ categoryId >>> 32);
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (value == null ? 0 : value.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */

	public void setCategoryId(long categoryId)
	{
		this.categoryId = categoryId;
	}

	public void setEmAnalyticsCategory(EmAnalyticsCategory emAnalyticsCategory)
	{
		this.emAnalyticsCategory = emAnalyticsCategory;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
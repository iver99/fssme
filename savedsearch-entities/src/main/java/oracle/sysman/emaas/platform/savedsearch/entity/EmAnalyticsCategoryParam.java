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

	public long getCategoryId()
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

	//@Column(name="NAME")
	//private String name;

	public String getName()
	{
		return name;
	}

	public String getValue()
	{
		return value;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */

	public void setCategoryId(long categoryId)
	{
		this.categoryId = categoryId;
	}

	public void setEmAnalyticsCategory(EmAnalyticsCategory emAnalyticsCategory)
	{
		this.emAnalyticsCategory = emAnalyticsCategory;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */

	public void setName(String name)
	{
		this.name = name;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
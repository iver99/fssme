package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The persistent class for the EM_ANALYTICS_CATEGORY_PARAMS database table.
 */
@Entity
@Table(name = "EM_ANALYTICS_CATEGORY_PARAMS")
public class EmAnalyticsCategoryParam implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmAnalyticsCategoryParamPK id;

	@Column(name = "PARAM_VALUE")
	private String value;

	//@Column(name="NAME")
	//private String name;

	//bi-directional many-to-one association to EmAnalyticsCategory
	@ManyToOne(optional = false)
	@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID")
	private EmAnalyticsCategory emAnalyticsCategory;

	public EmAnalyticsCategoryParam()
	{
	}

	public EmAnalyticsCategoryParamPK getId()
	{
		return this.id;
	}

	public void setId(EmAnalyticsCategoryParamPK id)
	{
		this.id = id;
	}

	public String getValue()
	{
		return this.value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	/*public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}*/
	public EmAnalyticsCategory getEmAnalyticsCategory()
	{
		return this.emAnalyticsCategory;
	}

	public void setEmAnalyticsCategory(EmAnalyticsCategory emAnalyticsCategory)
	{
		this.emAnalyticsCategory = emAnalyticsCategory;
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
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		if (!(obj instanceof EmAnalyticsCategoryParam)) {
			return false;
		}
		EmAnalyticsCategoryParam other = (EmAnalyticsCategoryParam) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		}
		else if (!id.equals(other.id)) {
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

}
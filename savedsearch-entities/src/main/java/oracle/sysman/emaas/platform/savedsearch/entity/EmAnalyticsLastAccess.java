package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the EMS_ANALYTICS_LAST_ACCESS database table.
 */

@Entity
@Table(name = "EMS_ANALYTICS_LAST_ACCESS")
@NamedQuery(name = "EmsAnalyticsLastAccess.findAll", query = "SELECT e FROM EmAnalyticsLastAccess e")
public class EmAnalyticsLastAccess implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmAnalyticsLastAccessPK id;

	@Column(name = "ACCESS_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date accessDate;

	public EmAnalyticsLastAccess()
	{
	}

	public Date getAccessDate()
	{
		return accessDate;
	}

	@Transient
	public String getAccessedBy()
	{
		return id.getAccessedBy();
	}

	public EmAnalyticsLastAccessPK getId()
	{
		return id;
	}

	@Transient
	public long getObjectId()
	{
		return id.getObjectId();
	}

	@Transient
	public long getObjectType()
	{
		return id.getObjectType();
	}

	public void setAccessDate(Date accessDate)
	{
		this.accessDate = accessDate;
	}

	public void setId(EmAnalyticsLastAccessPK id)
	{
		this.id = id;
	}

}
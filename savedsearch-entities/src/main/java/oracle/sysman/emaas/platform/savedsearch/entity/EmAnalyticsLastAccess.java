package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the EMS_ANALYTICS_LAST_ACCESS database table.
 */

@Entity
@Table(name = "EMS_ANALYTICS_LAST_ACCESS")
public class EmAnalyticsLastAccess implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final int LAST_ACCESS_TYPE_SEARCH = 2;

	@EmbeddedId
	private EmAnalyticsLastAccessPK id;

	@Column(name = "ACCESS_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date accessDate;

	public EmAnalyticsLastAccess()
	{
	}

	public EmAnalyticsLastAccess(long objectId, String accessedBy, long objectType)
	{
		id = new EmAnalyticsLastAccessPK();
		id.setObjectId(objectId);
		id.setAccessedBy(accessedBy);
		id.setObjectType(objectType);
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
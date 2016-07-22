package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * The persistent class for the EMS_ANALYTICS_LAST_ACCESS database table.
 */

@Entity
@Multitenant
@IdClass(EmAnalyticsLastAccessPK.class)
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = "tenant", length = 32, primaryKey = true)
@Table(name = "EMS_ANALYTICS_LAST_ACCESS")
@Inheritance
@DiscriminatorColumn(name = "OBJECT_TYPE")
public class EmAnalyticsLastAccess extends EmBaseEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final long LAST_ACCESS_TYPE_SEARCH = 2L;

	@Id
	@Column(name = "OBJECT_ID")
	private BigInteger objectId;

	@Id
	@Column(name = "ACCESSED_BY")
	private String accessedBy;

	@Id
	@Column(name = "OBJECT_TYPE")
	private long objectType;

	@Column(name = "ACCESS_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date accessDate;

	public EmAnalyticsLastAccess()
	{
	}

	public EmAnalyticsLastAccess(BigInteger objectId, String accessedBy, long objectType)
	{

		this.objectId = objectId;
		this.accessedBy = accessedBy;
		this.objectType = objectType;
	}

	public Date getAccessDate()
	{
		return accessDate;
	}

	@Transient
	public String getAccessedBy()
	{
		return accessedBy;
	}

	@Transient
	public BigInteger getObjectId()
	{
		return objectId;
	}

	@Transient
	public long getObjectType()
	{
		return objectType;
	}

	public void setAccessDate(Date accessDate)
	{
		this.accessDate = accessDate;
	}

}
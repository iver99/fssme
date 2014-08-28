package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the EMS_ANALYTICS_SCHEMA_VER database table.
 */
@Embeddable
public class EmAnalyticsSchemaVerPK implements Serializable
{
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private long major;

	private long minor;

	public EmAnalyticsSchemaVerPK()
	{
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other) {
			return true;
		}
		if (!(other instanceof EmAnalyticsSchemaVerPK)) {
			return false;
		}
		EmAnalyticsSchemaVerPK castOther = (EmAnalyticsSchemaVerPK) other;
		return major == castOther.major && minor == castOther.minor;
	}

	public long getMajor()
	{
		return major;
	}

	public long getMinor()
	{
		return minor;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + (int) (major ^ major >>> 32);
		hash = hash * prime + (int) (minor ^ minor >>> 32);

		return hash;
	}

	public void setMajor(long major)
	{
		this.major = major;
	}

	public void setMinor(long minor)
	{
		this.minor = minor;
	}
}
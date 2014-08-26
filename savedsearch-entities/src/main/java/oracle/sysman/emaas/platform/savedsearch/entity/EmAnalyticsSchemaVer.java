package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.ReadOnly;

/**
 * The persistent class for the EMS_ANALYTICS_SCHEMA_VER database table.
 */
@ReadOnly
@Entity
@Table(name = "EMS_ANALYTICS_SCHEMA_VER_SSF")
@NamedQuery(name = "EmAnalyticsSchemaVer.findAll", query = "SELECT e FROM EmAnalyticsSchemaVer e")
public class EmAnalyticsSchemaVer implements Serializable
{
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EmAnalyticsSchemaVerPK id;

	public EmAnalyticsSchemaVer()
	{
	}

	public EmAnalyticsSchemaVerPK getId()
	{
		return id;
	}

	public void setId(EmAnalyticsSchemaVerPK id)
	{
		this.id = id;
	}

}
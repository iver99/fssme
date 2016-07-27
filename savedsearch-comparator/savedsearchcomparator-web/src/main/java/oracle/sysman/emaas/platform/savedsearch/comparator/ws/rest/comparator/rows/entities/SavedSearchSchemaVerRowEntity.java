/*
 * Copyright (C) 2016 Oracle
 * All rights reserved.
 *
 * $$File: $$
 * $$DateTime: $$
 * $$Author: $$
 * $$Revision: $$
 */

package oracle.sysman.emaas.platform.savedsearch.comparator.ws.rest.comparator.rows.entities;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author pingwu
 */
public class SavedSearchSchemaVerRowEntity implements RowEntity
{

	@JsonProperty("MAJOR")
	private Long major;

	@JsonProperty("MINOR")
	private Long minor;

	@JsonProperty("CREATION_DATE")
	private String creationDate;

	@JsonProperty("LAST_MODIFICATION_DATE")
	private String lastModificationDate;

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
		if (getClass() != obj.getClass()) {
			return false;
		}
		SavedSearchSchemaVerRowEntity other = (SavedSearchSchemaVerRowEntity) obj;
		if (creationDate == null) {
			if (other.creationDate != null) {
				return false;
			}
		}
		else if (!creationDate.equals(other.creationDate)) {
			return false;
		}
		if (lastModificationDate == null) {
			if (other.lastModificationDate != null) {
				return false;
			}
		}
		else if (!lastModificationDate.equals(other.lastModificationDate)) {
			return false;
		}
		if (major == null) {
			if (other.major != null) {
				return false;
			}
		}
		else if (!major.equals(other.major)) {
			return false;
		}
		if (minor == null) {
			if (other.minor != null) {
				return false;
			}
		}
		else if (!minor.equals(other.minor)) {
			return false;
		}
		return true;
	}

	/**
	 * @return the creationDate
	 */
	public String getCreationDate()
	{
		return creationDate;
	}

	/**
	 * @return the lastModificationDate
	 */
	public String getLastModificationDate()
	{
		return lastModificationDate;
	}

	/**
	 * @return the major
	 */
	public Long getMajor()
	{
		return major;
	}

	/**
	 * @return the minor
	 */
	public Long getMinor()
	{
		return minor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (creationDate == null ? 0 : creationDate.hashCode());
		result = prime * result + (lastModificationDate == null ? 0 : lastModificationDate.hashCode());
		result = prime * result + (major == null ? 0 : major.hashCode());
		result = prime * result + (minor == null ? 0 : minor.hashCode());
		return result;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(String creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @param lastModificationDate
	 *            the lastModificationDate to set
	 */
	public void setLastModificationDate(String lastModificationDate)
	{
		this.lastModificationDate = lastModificationDate;
	}

	/**
	 * @param major
	 *            the major to set
	 */
	public void setMajor(Long major)
	{
		this.major = major;
	}

	/**
	 * @param minor
	 *            the minor to set
	 */
	public void setMinor(Long minor)
	{
		this.minor = minor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "SavedSearchSchemaVerRowEntity [major=" + major + ", minor=" + minor + ", creationDate=" + creationDate
				+ ", lastModificationDate=" + lastModificationDate + "]";
	}

}

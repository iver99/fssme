package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.annotations.PrivateOwned;

/**
 * The persistent class for the EMS_ANALYTICS_SEARCH database table.
 */
@Entity
@Table(name = "EMS_ANALYTICS_SEARCH")
@SecondaryTable(name = "EMS_ANALYTICS_LAST_ACCESS", pkJoinColumns = { @PrimaryKeyJoinColumn(name = "OBJECT_ID", referencedColumnName = "SEARCH_ID") })
@NamedQueries({
		@NamedQuery(name = "Search.getSearchById", query = "SELECT e FROM EmAnalyticsSearch e where e.id = :id  AND e.deleted =0 "),
		@NamedQuery(name = "Search.getSearchListByFolder", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsFolder = :folder  AND e.deleted =0 "),
		@NamedQuery(name = "Search.getSearchListByCategory", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsCategory = :category  AND e.deleted =0 "),
		@NamedQuery(name = "Search.getSearchCountByFolder", query = "SELECT count(e) FROM EmAnalyticsSearch e where e.emAnalyticsFolder = :folder  AND e.deleted =0"),
		@NamedQuery(name = "Search.getSearchDetails", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsFolder = :folder and e.name = :searchName  AND e.emAnalyticsCategory = :category AND e.deleted =0"),
		@NamedQuery(name = "Search.getSearchByName", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsFolder = :folder and e.name = :searchName  AND e.deleted =0") })
@SequenceGenerator(name = "EMS_ANALYTICS_SEARCH_SEQ", sequenceName = "EMS_ANALYTICS_SEARCH_SEQ", allocationSize = 1)
public class EmAnalyticsSearch implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SEARCH_ID")
	@GeneratedValue(generator = "EMS_ANALYTICS_SEARCH_SEQ", strategy = GenerationType.SEQUENCE)
	private long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE", insertable = false)
	private Date creationDate;

	private String description;

	@Column(name = "DESCRIPTION_NLSID")
	private String descriptionNlsid;

	@Column(name = "DESCRIPTION_SUBSYSTEM")
	private String descriptionSubsystem;

	@Column(name = "EM_PLUGIN_ID")
	private String emPluginId;

	@Column(name = "IS_LOCKED")
	private BigDecimal isLocked;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFICATION_DATE", insertable = false)
	private Date lastModificationDate;

	@Column(name = "LAST_MODIFIED_BY")
	private String lastModifiedBy;

	@Lob()
	@Column(name = "METADATA_CLOB")
	private String metadataClob;

	private String name;

	@Column(name = "NAME_NLSID")
	private String nameNlsid;

	@Column(name = "NAME_SUBSYSTEM")
	private String nameSubsystem;

	private String owner;

	@Column(name = "SEARCH_DISPLAY_STR")
	private String searchDisplayStr;

	@Column(name = "SEARCH_GUID", columnDefinition = "RAW(16) DEFAULT SYS_GUID()", insertable = false)
	private byte[] searchGuid;

	@Column(name = "SYSTEM_SEARCH")
	private BigDecimal systemSearch;

	@Column(name = "UI_HIDDEN")
	private BigDecimal uiHidden;

	@Column(name = "DELETED")
	private long deleted;

	//bi-directional many-to-one association to EmAnalyticsCategory
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private EmAnalyticsCategory emAnalyticsCategory;

	//bi-directional many-to-one association to EmAnalyticsFolder
	@ManyToOne
	@JoinColumn(name = "FOLDER_ID")
	private EmAnalyticsFolder emAnalyticsFolder;

	//bi-directional many-to-one association to EmAnalyticsSearchParam

	@OneToMany(mappedBy = "emAnalyticsSearch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrivateOwned
	private Set<EmAnalyticsSearchParam> emAnalyticsSearchParams;

	@Column(table = "EMS_ANALYTICS_LAST_ACCESS", name = "OBJECT_ID")
	private long objectId;

	@Column(table = "EMS_ANALYTICS_LAST_ACCESS", name = "ACCESSED_BY")
	private String accessedBy;

	@Column(table = "EMS_ANALYTICS_LAST_ACCESS", name = "OBJECT_TYPE")
	private long objectType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(table = "EMS_ANALYTICS_LAST_ACCESS", name = "ACCESS_DATE")
	private Date accessDate;

	public EmAnalyticsSearch()
	{
	}

	public Date getAccessDate()
	{
		return accessDate;
	}

	public String getAccessedBy()
	{
		return accessedBy;
	}

	public Date getCreationDate()
	{
		return creationDate;
	}

	public long getDeleted()
	{
		return deleted;
	}

	public String getDescription()
	{
		return description;
	}

	public String getDescriptionNlsid()
	{
		return descriptionNlsid;
	}

	public String getDescriptionSubsystem()
	{
		return descriptionSubsystem;
	}

	public EmAnalyticsCategory getEmAnalyticsCategory()
	{
		return emAnalyticsCategory;
	}

	public EmAnalyticsFolder getEmAnalyticsFolder()
	{
		return emAnalyticsFolder;
	}

	public Set<EmAnalyticsSearchParam> getEmAnalyticsSearchParams()
	{
		if (emAnalyticsSearchParams == null) {
			emAnalyticsSearchParams = new HashSet<EmAnalyticsSearchParam>();
		}
		return emAnalyticsSearchParams;
	}

	public String getEmPluginId()
	{
		return emPluginId;
	}

	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}

	public BigDecimal getIsLocked()
	{
		return isLocked;
	}

	public Date getLastModificationDate()
	{
		return lastModificationDate;
	}

	public String getLastModifiedBy()
	{
		return lastModifiedBy;
	}

	public String getMetadataClob()
	{
		return metadataClob;
	}

	public String getName()
	{
		return name;
	}

	public String getNameNlsid()
	{
		return nameNlsid;
	}

	public String getNameSubsystem()
	{
		return nameSubsystem;
	}

	public long getObjectId()
	{
		return objectId;
	}

	public long getObjectType()
	{
		return objectType;
	}

	public String getOwner()
	{
		return owner;
	}

	public String getSearchDisplayStr()
	{
		return searchDisplayStr;
	}

	public byte[] getSearchGuid()
	{
		return searchGuid;
	}

	public BigDecimal getSystemSearch()
	{
		return systemSearch;
	}

	public BigDecimal getUiHidden()
	{
		return uiHidden;
	}

	public void setAccessDate(Date accessDate)
	{
		this.accessDate = accessDate;
	}

	public void setAccessedBy(String accessedBy)
	{
		this.accessedBy = accessedBy;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public void setDeleted(long deleted)
	{
		this.deleted = deleted;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public void setDescriptionNlsid(String descriptionNlsid)
	{
		this.descriptionNlsid = descriptionNlsid;
	}

	public void setDescriptionSubsystem(String descriptionSubsystem)
	{
		this.descriptionSubsystem = descriptionSubsystem;
	}

	public void setEmAnalyticsCategory(EmAnalyticsCategory emAnalyticsCategory)
	{
		this.emAnalyticsCategory = emAnalyticsCategory;
	}

	public void setEmAnalyticsFolder(EmAnalyticsFolder emAnalyticsFolder)
	{
		this.emAnalyticsFolder = emAnalyticsFolder;
	}

	public void setEmAnalyticsSearchParams(Set<EmAnalyticsSearchParam> emAnalyticsSearchParams)
	{
		this.emAnalyticsSearchParams = emAnalyticsSearchParams;
	}

	public void setEmPluginId(String emPluginId)
	{
		this.emPluginId = emPluginId;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}

	public void setIsLocked(BigDecimal isLocked)
	{
		this.isLocked = isLocked;
	}

	public void setLastModificationDate(Date lastModificationDate)
	{
		this.lastModificationDate = lastModificationDate;
	}

	public void setLastModifiedBy(String lastModifiedBy)
	{
		this.lastModifiedBy = lastModifiedBy;
	}

	public void setMetadataClob(String metadataClob)
	{
		this.metadataClob = metadataClob;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setNameNlsid(String nameNlsid)
	{
		this.nameNlsid = nameNlsid;
	}

	public void setNameSubsystem(String nameSubsystem)
	{
		this.nameSubsystem = nameSubsystem;
	}

	public void setObjectId(long objectId)
	{
		this.objectId = objectId;
	}

	public void setObjectType(long objectType)
	{
		this.objectType = objectType;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public void setSearchDisplayStr(String searchDisplayStr)
	{
		this.searchDisplayStr = searchDisplayStr;
	}

	public void setSearchGuid(byte[] searchGuid)
	{
		this.searchGuid = searchGuid;
	}

	public void setSystemSearch(BigDecimal systemSearch)
	{
		this.systemSearch = systemSearch;
	}

	public void setUiHidden(BigDecimal uiHidden)
	{
		this.uiHidden = uiHidden;
	}

}
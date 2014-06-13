package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.eclipse.persistence.annotations.PrivateOwned;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the EM_ANALYTICS_SEARCH database table.
 */
@Entity
@Table(name = "EM_ANALYTICS_SEARCH")
@NamedQueries({
		@NamedQuery(name = "Search.getSearchListByFolder", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsFolder = :folder"),
		@NamedQuery(name = "Search.getSearchListByCategory", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsCategory = :category"),
		@NamedQuery(name = "Search.getSearchCountByFolder", query = "SELECT count(e) FROM EmAnalyticsSearch e where e.emAnalyticsFolder = :folder"),
		@NamedQuery(name = "Search.getSearchByName", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsFolder = :folder and e.name = :searchName")

})
@SequenceGenerator(name = "EM_ANALYTICS_SEARCH_SEQ", sequenceName = "EM_ANALYTICS_SEARCH_SEQ", allocationSize = 1)
public class EmAnalyticsSearch implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SEARCH_ID")
	@GeneratedValue(generator = "EM_ANALYTICS_SEARCH_SEQ", strategy = GenerationType.SEQUENCE)
	private long id;

	@Temporal(TemporalType.DATE)
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

	@Temporal(TemporalType.DATE)
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

	public EmAnalyticsSearch()
	{
	}

	public Date getCreationDate()
	{
		return this.creationDate;
	}

	public void setCreationDate(Date creationDate)
	{
		this.creationDate = creationDate;
	}

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescriptionNlsid()
	{
		return this.descriptionNlsid;
	}

	public void setDescriptionNlsid(String descriptionNlsid)
	{
		this.descriptionNlsid = descriptionNlsid;
	}

	public String getDescriptionSubsystem()
	{
		return this.descriptionSubsystem;
	}

	public void setDescriptionSubsystem(String descriptionSubsystem)
	{
		this.descriptionSubsystem = descriptionSubsystem;
	}

	public String getEmPluginId()
	{
		return this.emPluginId;
	}

	public void setEmPluginId(String emPluginId)
	{
		this.emPluginId = emPluginId;
	}

	public BigDecimal getIsLocked()
	{
		return this.isLocked;
	}

	public void setIsLocked(BigDecimal isLocked)
	{
		this.isLocked = isLocked;
	}

	public Date getLastModificationDate()
	{
		return this.lastModificationDate;
	}

	public void setLastModificationDate(Date lastModificationDate)
	{
		this.lastModificationDate = lastModificationDate;
	}

	public String getLastModifiedBy()
	{
		return this.lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy)
	{
		this.lastModifiedBy = lastModifiedBy;
	}

	public String getMetadataClob()
	{
		return this.metadataClob;
	}

	public void setMetadataClob(String metadataClob)
	{
		this.metadataClob = metadataClob;
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getNameNlsid()
	{
		return this.nameNlsid;
	}

	public void setNameNlsid(String nameNlsid)
	{
		this.nameNlsid = nameNlsid;
	}

	public String getNameSubsystem()
	{
		return this.nameSubsystem;
	}

	public void setNameSubsystem(String nameSubsystem)
	{
		this.nameSubsystem = nameSubsystem;
	}

	public String getOwner()
	{
		return this.owner;
	}

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public String getSearchDisplayStr()
	{
		return this.searchDisplayStr;
	}

	public void setSearchDisplayStr(String searchDisplayStr)
	{
		this.searchDisplayStr = searchDisplayStr;
	}

	public byte[] getSearchGuid()
	{
		return this.searchGuid;
	}

	public void setSearchGuid(byte[] searchGuid)
	{
		this.searchGuid = searchGuid;
	}

	public BigDecimal getSystemSearch()
	{
		return this.systemSearch;
	}

	public void setSystemSearch(BigDecimal systemSearch)
	{
		this.systemSearch = systemSearch;
	}

	public BigDecimal getUiHidden()
	{
		return this.uiHidden;
	}

	public void setUiHidden(BigDecimal uiHidden)
	{
		this.uiHidden = uiHidden;
	}

	public EmAnalyticsCategory getEmAnalyticsCategory()
	{
		return this.emAnalyticsCategory;
	}

	public void setEmAnalyticsCategory(EmAnalyticsCategory emAnalyticsCategory)
	{
		this.emAnalyticsCategory = emAnalyticsCategory;
	}

	public EmAnalyticsFolder getEmAnalyticsFolder()
	{
		return this.emAnalyticsFolder;
	}

	public void setEmAnalyticsFolder(EmAnalyticsFolder emAnalyticsFolder)
	{
		this.emAnalyticsFolder = emAnalyticsFolder;
	}

	public Set<EmAnalyticsSearchParam> getEmAnalyticsSearchParams()
	{
		if (emAnalyticsSearchParams == null)
			emAnalyticsSearchParams = new HashSet<EmAnalyticsSearchParam>();
		return this.emAnalyticsSearchParams;
	}

	public void setEmAnalyticsSearchParams(Set<EmAnalyticsSearchParam> emAnalyticsSearchParams)
	{
		this.emAnalyticsSearchParams = emAnalyticsSearchParams;
	}

	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}

}
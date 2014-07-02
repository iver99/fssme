package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

/**
 * The persistent class for the EMS_ANALYTICS_FOLDERS database table.
 */
@Entity
@Table(name = "EMS_ANALYTICS_FOLDERS")
@NamedQueries({
		@NamedQuery(name = "Folder.getSubFolder", query = "Select o from EmAnalyticsFolder o where o.emAnalyticsFolder= "
				+ ":parentFolder"),
		@NamedQuery(name = "Folder.getRootFolders", query = "Select o from EmAnalyticsFolder o where o.emAnalyticsFolder is null"),
		@NamedQuery(name = "Folder.getSubFolderByName", query = "Select o from EmAnalyticsFolder o where o.emAnalyticsFolder= :parentFolder AND o.name=:foldername"),
		@NamedQuery(name = "Folder.getRootFolderByName", query = "Select o from EmAnalyticsFolder o where o.emAnalyticsFolder is null AND o.name=:foldername") })
@SequenceGenerator(name = "EMS_ANALYTICS_FOLDERS_SEQ", sequenceName = "EMS_ANALYTICS_FOLDERS_SEQ", allocationSize = 1)
public class EmAnalyticsFolder implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FOLDER_ID")
	@GeneratedValue(generator = "EMS_ANALYTICS_FOLDERS_SEQ", strategy = GenerationType.SEQUENCE)
	private long folderId;

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

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFICATION_DATE", insertable = false)
	private Date lastModificationDate;

	@Column(name = "LAST_MODIFIED_BY")
	private String lastModifiedBy;

	private String name;

	@Column(name = "NAME_NLSID")
	private String nameNlsid;

	@Column(name = "NAME_SUBSYSTEM")
	private String nameSubsystem;

	private String owner;

	@Column(name = "SYSTEM_FOLDER")
	private BigDecimal systemFolder;

	@Column(name = "UI_HIDDEN")
	private BigDecimal uiHidden;

	//bi-directional many-to-one association to EmAnalyticsCategory
	@OneToMany(mappedBy = "emAnalyticsFolder")
	private Set<EmAnalyticsCategory> emAnalyticsCategories;

	//bi-directional many-to-one association to EmAnalyticsFolder
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private EmAnalyticsFolder emAnalyticsFolder;

	//bi-directional many-to-one association to EmAnalyticsFolder
	@OneToMany(mappedBy = "emAnalyticsFolder", fetch = FetchType.EAGER)
	private Set<EmAnalyticsFolder> emAnalyticsFolders;

	//bi-directional many-to-one association to EmAnalyticsSearch
	@OneToMany(mappedBy = "emAnalyticsFolder", fetch = FetchType.EAGER)
	private Set<EmAnalyticsSearch> emAnalyticsSearches;

	public EmAnalyticsFolder()
	{
	}

	public long getFolderId()
	{
		return this.folderId;
	}

	public void setFolderId(long folderId)
	{
		this.folderId = folderId;
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

	public BigDecimal getSystemFolder()
	{
		return this.systemFolder;
	}

	public void setSystemFolder(BigDecimal systemFolder)
	{
		this.systemFolder = systemFolder;
	}

	public BigDecimal getUiHidden()
	{
		return this.uiHidden;
	}

	public void setUiHidden(BigDecimal uiHidden)
	{
		this.uiHidden = uiHidden;
	}

	public Set<EmAnalyticsCategory> getEmAnalyticsCategories()
	{
		return this.emAnalyticsCategories;
	}

	public void setEmAnalyticsCategories(Set<EmAnalyticsCategory> emAnalyticsCategories)
	{
		this.emAnalyticsCategories = emAnalyticsCategories;
	}

	public EmAnalyticsFolder getEmAnalyticsFolder()
	{
		return this.emAnalyticsFolder;
	}

	public void setEmAnalyticsFolder(EmAnalyticsFolder emAnalyticsFolder)
	{
		this.emAnalyticsFolder = emAnalyticsFolder;
	}

	public Set<EmAnalyticsFolder> getEmAnalyticsFolders()
	{
		return this.emAnalyticsFolders;
	}

	public void setEmAnalyticsFolders(Set<EmAnalyticsFolder> emAnalyticsFolders)
	{
		this.emAnalyticsFolders = emAnalyticsFolders;
	}

	public Set<EmAnalyticsSearch> getEmAnalyticsSearches()
	{
		return this.emAnalyticsSearches;
	}

	public void setEmAnalyticsSearches(Set<EmAnalyticsSearch> emAnalyticsSearches)
	{
		this.emAnalyticsSearches = emAnalyticsSearches;
	}

}
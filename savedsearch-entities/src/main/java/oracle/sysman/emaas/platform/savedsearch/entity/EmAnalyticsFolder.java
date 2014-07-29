package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the EMS_ANALYTICS_FOLDERS database table.
 */
@Entity
@Table(name = "EMS_ANALYTICS_FOLDERS")
@NamedQueries({
		@NamedQuery(name = "Folder.getFolderById", query = "Select o from EmAnalyticsFolder o where  o.folderId= :id AND O.deleted =0"),
		@NamedQuery(name = "Folder.getSubFolder", query = "Select o from EmAnalyticsFolder o where o.emAnalyticsFolder= "
				+ ":parentFolder" + " AND o.deleted=0"),
		@NamedQuery(name = "Folder.getRootFolders", query = "Select o from EmAnalyticsFolder o where o.emAnalyticsFolder is null AND o.deleted=0"),
		@NamedQuery(name = "Folder.getSubFolderByName", query = "Select o from EmAnalyticsFolder o where o.emAnalyticsFolder= :parentFolder AND o.name=:foldername AND O.deleted =0"),
		@NamedQuery(name = "Folder.getRootFolderByName", query = "Select o from EmAnalyticsFolder o where o.emAnalyticsFolder is null AND o.name=:foldername AND O.deleted =0") })
@SequenceGenerator(name = "EMS_ANALYTICS_FOLDERS_SEQ", sequenceName = "EMS_ANALYTICS_FOLDERS_SEQ", allocationSize = 1)
public class EmAnalyticsFolder implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FOLDER_ID")
	@GeneratedValue(generator = "EMS_ANALYTICS_FOLDERS_SEQ", strategy = GenerationType.SEQUENCE)
	private long folderId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATION_DATE")
	private Date creationDate;

	private String description;

	@Column(name = "DESCRIPTION_NLSID")
	private String descriptionNlsid;

	@Column(name = "DESCRIPTION_SUBSYSTEM")
	private String descriptionSubsystem;

	@Column(name = "EM_PLUGIN_ID")
	private String emPluginId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFICATION_DATE")
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

	@Column(name = "DELETED")
	private long deleted;

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

	public Set<EmAnalyticsCategory> getEmAnalyticsCategories()
	{
		return emAnalyticsCategories;
	}

	public EmAnalyticsFolder getEmAnalyticsFolder()
	{
		return emAnalyticsFolder;
	}

	public Set<EmAnalyticsFolder> getEmAnalyticsFolders()
	{
		return emAnalyticsFolders;
	}

	public Set<EmAnalyticsSearch> getEmAnalyticsSearches()
	{
		return emAnalyticsSearches;
	}

	public String getEmPluginId()
	{
		return emPluginId;
	}

	public long getFolderId()
	{
		return folderId;
	}

	public Date getLastModificationDate()
	{
		return lastModificationDate;
	}

	public String getLastModifiedBy()
	{
		return lastModifiedBy;
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

	public String getOwner()
	{
		return owner;
	}

	public BigDecimal getSystemFolder()
	{
		return systemFolder;
	}

	public BigDecimal getUiHidden()
	{
		return uiHidden;
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

	public void setEmAnalyticsCategories(Set<EmAnalyticsCategory> emAnalyticsCategories)
	{
		this.emAnalyticsCategories = emAnalyticsCategories;
	}

	public void setEmAnalyticsFolder(EmAnalyticsFolder emAnalyticsFolder)
	{
		this.emAnalyticsFolder = emAnalyticsFolder;
	}

	public void setEmAnalyticsFolders(Set<EmAnalyticsFolder> emAnalyticsFolders)
	{
		this.emAnalyticsFolders = emAnalyticsFolders;
	}

	public void setEmAnalyticsSearches(Set<EmAnalyticsSearch> emAnalyticsSearches)
	{
		this.emAnalyticsSearches = emAnalyticsSearches;
	}

	public void setEmPluginId(String emPluginId)
	{
		this.emPluginId = emPluginId;
	}

	public void setFolderId(long folderId)
	{
		this.folderId = folderId;
	}

	public void setLastModificationDate(Date lastModificationDate)
	{
		this.lastModificationDate = lastModificationDate;
	}

	public void setLastModifiedBy(String lastModifiedBy)
	{
		this.lastModifiedBy = lastModifiedBy;
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

	public void setOwner(String owner)
	{
		this.owner = owner;
	}

	public void setSystemFolder(BigDecimal systemFolder)
	{
		this.systemFolder = systemFolder;
	}

	public void setUiHidden(BigDecimal uiHidden)
	{
		this.uiHidden = uiHidden;
	}

}
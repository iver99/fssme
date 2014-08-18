package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.eclipse.persistence.annotations.PrivateOwned;

/**
 * The persistent class for the EMS_ANALYTICS_CATEGORY database table.
 */
@Entity
@Table(name = "EMS_ANALYTICS_CATEGORY")
@NamedQueries({
		@NamedQuery(name = "Category.getCategoryById", query = "SELECT e FROM EmAnalyticsCategory e Where e.categoryId = :id  AND e.deleted =0 "),
		@NamedQuery(name = "Category.getCategoryByFolder", query = "SELECT e FROM EmAnalyticsCategory e Where e.emAnalyticsFolder = :id  AND e.deleted =0 "),
		@NamedQuery(name = "Category.getAllCategory", query = "SELECT e FROM EmAnalyticsCategory e Where e.deleted =0 "),
		@NamedQuery(name = "Category.getCategoryByName", query = "SELECT e FROM EmAnalyticsCategory e where e.name = "
				+ ":categoryName" + " AND e.deleted = 0 ") })
@SequenceGenerator(name = "EMS_ANALYTICS_CATEGORY_SEQ", sequenceName = "EMS_ANALYTICS_CATEGORY_SEQ", allocationSize = 1)
public class EmAnalyticsCategory implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CATEGORY_ID")
	@GeneratedValue(generator = "EMS_ANALYTICS_CATEGORY_SEQ", strategy = GenerationType.SEQUENCE)
	private long categoryId;

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

	private String name;

	@Column(name = "NAME_NLSID")
	private String nameNlsid;

	@Column(name = "NAME_SUBSYSTEM")
	private String nameSubsystem;

	@Column(name = "OWNER")
	private String owner;

	//bi-directional many-to-one association to EmAnalyticsFolder
	@ManyToOne
	@JoinColumn(name = "DEFAULT_FOLDER_ID")
	private EmAnalyticsFolder emAnalyticsFolder;

	@Column(name = "DELETED")
	private long deleted;

	//bi-directional many-to-one association to EmAnalyticsCategoryParam

	@OneToMany(mappedBy = "emAnalyticsCategory", cascade = CascadeType.ALL)
	@PrivateOwned
	private Set<EmAnalyticsCategoryParam> emAnalyticsCategoryParams;

	//bi-directional many-to-one association to EmAnalyticsSearch
	@OneToMany(mappedBy = "emAnalyticsCategory")
	private Set<EmAnalyticsSearch> emAnalyticsSearches;

	public EmAnalyticsCategory()
	{
	}

	public long getCategoryId()
	{
		return categoryId;
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

	public Set<EmAnalyticsCategoryParam> getEmAnalyticsCategoryParams()
	{
		if (emAnalyticsCategoryParams == null) {
			emAnalyticsCategoryParams = new HashSet<EmAnalyticsCategoryParam>();
		}
		return emAnalyticsCategoryParams;
	}

	public EmAnalyticsFolder getEmAnalyticsFolder()
	{
		return emAnalyticsFolder;
	}

	public Set<EmAnalyticsSearch> getEmAnalyticsSearches()
	{
		return emAnalyticsSearches;
	}

	public String getEmPluginId()
	{
		return emPluginId;
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

	public void setCategoryId(long categoryId)
	{
		this.categoryId = categoryId;
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

	public void setEmAnalyticsCategoryParams(Set<EmAnalyticsCategoryParam> emAnalyticsCategoryParams)
	{
		this.emAnalyticsCategoryParams = emAnalyticsCategoryParams;
	}

	public void setEmAnalyticsFolder(EmAnalyticsFolder emAnalyticsFolder)
	{
		this.emAnalyticsFolder = emAnalyticsFolder;
	}

	public void setEmAnalyticsSearches(Set<EmAnalyticsSearch> emAnalyticsSearches)
	{
		this.emAnalyticsSearches = emAnalyticsSearches;
	}

	public void setEmPluginId(String emPluginId)
	{
		this.emPluginId = emPluginId;
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

}
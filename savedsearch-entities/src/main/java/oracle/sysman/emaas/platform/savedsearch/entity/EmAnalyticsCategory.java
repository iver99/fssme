package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import org.eclipse.persistence.annotations.PrivateOwned;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
				+ ":categoryName" + " AND e.deleted = 0 " ) })
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
	private BigDecimal deleted;

	//bi-directional many-to-one association to EmAnalyticsCategoryParam

	@OneToMany(mappedBy = "emAnalyticsCategory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
		return this.categoryId;
	}

	public void setCategoryId(long categoryId)
	{
		this.categoryId = categoryId;
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

	public EmAnalyticsFolder getEmAnalyticsFolder()
	{
		return this.emAnalyticsFolder;
	}

	public void setEmAnalyticsFolder(EmAnalyticsFolder emAnalyticsFolder)
	{
		this.emAnalyticsFolder = emAnalyticsFolder;
	}
	
	public BigDecimal getDeleted()
	{
		return this.deleted;
	}

	public void setDeleted(BigDecimal deleted) 
        {
		this.deleted = deleted;
	}

	public Set<EmAnalyticsCategoryParam> getEmAnalyticsCategoryParams()
	{
		if (emAnalyticsCategoryParams == null)
			emAnalyticsCategoryParams = new HashSet<EmAnalyticsCategoryParam>();
		return this.emAnalyticsCategoryParams;
	}

	public void setEmAnalyticsCategoryParams(Set<EmAnalyticsCategoryParam> emAnalyticsCategoryParams)
	{
		this.emAnalyticsCategoryParams = emAnalyticsCategoryParams;
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
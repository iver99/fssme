package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.PrivateOwned;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * The persistent class for the EMS_ANALYTICS_CATEGORY database table.
 */
@Entity
@Multitenant
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = "tenant", length = 32, primaryKey = true)
@Table(name = "EMS_ANALYTICS_CATEGORY")
@NamedQueries({
		@NamedQuery(name = "Category.getCategoryById", query = "SELECT e FROM EmAnalyticsCategory e Where e.categoryId = :id  AND e.deleted =0  AND e.owner in ('ORACLE',:userName)"),
		@NamedQuery(name = "Category.getCategoryByFolder", query = "SELECT e FROM EmAnalyticsCategory e Where e.emAnalyticsFolder = :id  AND e.deleted =0  AND e.owner in ('ORACLE',:userName)"),
		@NamedQuery(name = "Category.getAllCategory", query = "SELECT e FROM EmAnalyticsCategory e Where e.deleted =0  AND e.owner in ('ORACLE',:userName)"),
		@NamedQuery(name = "Category.getCategoryByName", query = "SELECT e FROM EmAnalyticsCategory e where e.name = "
				+ ":categoryName" + " AND e.deleted = 0  AND e.owner in ('ORACLE',:userName)"),
		@NamedQuery(name = "Category.getCategoryByNameForTenant", query = "SELECT e FROM EmAnalyticsCategory e where e.name = "
				+ ":categoryName" + " AND e.deleted = 0 ") })
//@SequenceGenerator(name = "EMS_ANALYTICS_CATEGORY_SEQ", sequenceName = "EMS_ANALYTICS_CATEGORY_SEQ", allocationSize = 1)
public class EmAnalyticsCategory extends EmBaseEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CATEGORY_ID")
//	@GeneratedValue(generator = "EMS_ANALYTICS_CATEGORY_SEQ", strategy = GenerationType.SEQUENCE)
	private BigInteger categoryId;

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
	@JoinColumns({ @JoinColumn(name = "DEFAULT_FOLDER_ID", referencedColumnName = "FOLDER_ID"),
			@JoinColumn(name = "TENANT_ID", referencedColumnName = "TENANT_ID", insertable = false, updatable = false) })
	private EmAnalyticsFolder emAnalyticsFolder;

	@Column(name = "DELETED")
	private BigInteger deleted;

	@Column(name = "PROVIDER_NAME")
	private String providerName;

	@Column(name = "PROVIDER_VERSION")
	private String providerVersion;

	@Column(name = "PROVIDER_DISCOVERY")
	private String providerDiscovery;

	@Column(name = "PROVIDER_ASSET_ROOT")
	private String providerAssetRoot;

	@OneToMany(mappedBy = "emAnalyticsCategory", cascade = CascadeType.ALL)
	@PrivateOwned
	private Set<EmAnalyticsCategoryParam> emAnalyticsCategoryParams;

	//bi-directional many-to-one association to EmAnalyticsSearch
	@OneToMany(mappedBy = "emAnalyticsCategory")
	private Set<EmAnalyticsSearch> emAnalyticsSearches;

	//bi-directional many-to-one association to EmAnalyticsCategoryParam

	public EmAnalyticsCategory()
	{
	}

	public BigInteger getCategoryId()
	{
		return categoryId;
	}

	public BigInteger getDeleted()
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

	public String getProviderAssetRoot()
	{
		return providerAssetRoot;
	}

	public String getProviderDiscovery()
	{
		return providerDiscovery;
	}

	public String getProviderName()
	{
		return providerName;
	}

	public String getProviderVersion()
	{
		return providerVersion;
	}

	public void setCategoryId(BigInteger categoryId)
	{
		this.categoryId = categoryId;
	}

	public void setDeleted(BigInteger deleted)
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

	public void setProviderAssetRoot(String providerAssetRoot)
	{
		this.providerAssetRoot = providerAssetRoot;
	}

	public void setProviderDiscovery(String providerDiscovery)
	{
		this.providerDiscovery = providerDiscovery;
	}

	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	public void setProviderVersion(String providerVersion)
	{
		this.providerVersion = providerVersion;
	}

}
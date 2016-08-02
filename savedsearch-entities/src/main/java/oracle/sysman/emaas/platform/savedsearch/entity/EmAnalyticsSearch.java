package oracle.sysman.emaas.platform.savedsearch.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.eclipse.persistence.annotations.Multitenant;
import org.eclipse.persistence.annotations.PrivateOwned;
import org.eclipse.persistence.annotations.TenantDiscriminatorColumn;

/**
 * The persistent class for the EMS_ANALYTICS_SEARCH database table.
 */
@Entity
@Multitenant
@TenantDiscriminatorColumn(name = "TENANT_ID", contextProperty = "tenant", length = 32, primaryKey = true)
@Table(name = "EMS_ANALYTICS_SEARCH")
@NamedQueries({
		@NamedQuery(name = "Search.getSearchListByFolder", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsFolder.folderId = :folderId  AND e.deleted =0  AND (e.owner in (:userName) OR e.systemSearch =1) "),
		@NamedQuery(name = "Search.getSearchListByFolderForTenant", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsFolder.folderId = :folderId  AND e.deleted =0 "),
		@NamedQuery(name = "Search.getSearchListByCategory", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsCategory.categoryId = :categoryId  AND e.deleted =0  AND (e.owner in (:userName) OR e.systemSearch =1) "),
		@NamedQuery(name = "Search.getSystemSearchListByCategory", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsCategory.categoryId = :categoryId  AND e.deleted =0  AND (e.owner in ('ORACLE') OR e.systemSearch =1) "),
		@NamedQuery(name = "Search.getSearchListByCategoryForTenant", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsCategory.categoryId = :categoryId  AND e.deleted =0 "),
		@NamedQuery(name = "Search.getSearchCountByFolder", query = "SELECT count(e) FROM EmAnalyticsSearch e where e.emAnalyticsFolder = :folder  AND e.deleted =0 AND (e.owner in (:userName) OR e.systemSearch =1) "),
		@NamedQuery(name = "Search.getSearchByName", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsFolder.folderId = :folderId and e.name = :searchName  AND e.deleted =0 AND (e.owner in (:userName) OR e.systemSearch =1)"),
		@NamedQuery(name = "Search.getWidgetListByCategory", query = "SELECT e FROM EmAnalyticsSearch e where e.emAnalyticsCategory.categoryId = :categoryId  AND e.deleted =0 AND e.isWidget = 1 AND (e.owner in (:userName) OR e.systemSearch =1) "),
		@NamedQuery(name = "Search.getSearchListByTargetType", query = "SELECT e FROM EmAnalyticsSearch e WHERE e.name like :searchName AND (e.owner in (:userName) OR e.systemSearch =1) AND e.deleted=0") })
//@SequenceGenerator(name = "EMS_ANALYTICS_SEARCH_SEQ", sequenceName = "EMS_ANALYTICS_SEARCH_SEQ", allocationSize = 1)
public class EmAnalyticsSearch extends EmBaseEntity implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SEARCH_ID")
//	@GeneratedValue(generator = "EMS_ANALYTICS_SEARCH_SEQ", strategy = GenerationType.SEQUENCE)
	private BigInteger id;

	private String description;

	@Column(name = "DESCRIPTION_NLSID")
	private String descriptionNlsid;

	@Column(name = "DESCRIPTION_SUBSYSTEM")
	private String descriptionSubsystem;

	@Column(name = "EM_PLUGIN_ID")
	private String emPluginId;

	@Column(name = "IS_LOCKED")
	private BigDecimal isLocked;

	@Column(name = "LAST_MODIFIED_BY")
	private String lastModifiedBy;

	@Lob()
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "METADATA_CLOB")
	private String metadataClob;

	private String name;

	@Column(name = "NAME_NLSID")
	private String nameNlsid;

	@Column(name = "NAME_SUBSYSTEM")
	private String nameSubsystem;

	@Column(name = "OWNER")
	private String owner;

	@Lob()
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "SEARCH_DISPLAY_STR")
	private String searchDisplayStr;

	@Column(name = "SEARCH_GUID", columnDefinition = "RAW(16) DEFAULT SYS_GUID()", insertable = false)
	private byte[] searchGuid;

	@Column(name = "SYSTEM_SEARCH")
	private BigDecimal systemSearch;

	@Column(name = "UI_HIDDEN")
	private BigDecimal uiHidden;

	@Column(name = "DELETED")
	private BigInteger deleted;

	@Column(name = "IS_WIDGET")
	private long isWidget;

	@Column(name = "NAME_WIDGET_SOURCE")
	private String NAME_WIDGET_SOURCE;

	@Column(name = "WIDGET_GROUP_NAME")
	private String WIDGET_GROUP_NAME;

	@Column(name = "WIDGET_SCREENSHOT_HREF")
	private String WIDGET_SCREENSHOT_HREF;

	@Column(name = "WIDGET_ICON")
	private String WIDGET_ICON;

	@Column(name = "WIDGET_KOC_NAME")
	private String WIDGET_KOC_NAME;

	@Column(name = "WIDGET_VIEWMODEL")
	private String WIDGET_VIEWMODEL;

	@Column(name = "WIDGET_TEMPLATE")
	private String WIDGET_TEMPLATE;

	@Column(name = "WIDGET_SUPPORT_TIME_CONTROL")
	private String WIDGET_SUPPORT_TIME_CONTROL;

	@Column(name = "WIDGET_LINKED_DASHBOARD")
	private long WIDGET_LINKED_DASHBOARD;

	@Column(name = "WIDGET_DEFAULT_WIDTH")
	private long WIDGET_DEFAULT_WIDTH;

	@Column(name = "WIDGET_DEFAULT_HEIGHT")
	private long WIDGET_DEFAULT_HEIGHT;

	@Column(name = "DASHBOARD_INELIGIBLE")
	private String DASHBOARD_INELIGIBLE;

	@Column(name = "PROVIDER_NAME")
	private String PROVIDER_NAME;

	@Column(name = "PROVIDER_VERSION")
	private String PROVIDER_VERSION;

	@Column(name = "PROVIDER_ASSET_ROOT")
	private String PROVIDER_ASSET_ROOT;

	//bi-directional many-to-one association to EmAnalyticsCategory
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID"),
			@JoinColumn(name = "TENANT_ID", referencedColumnName = "TENANT_ID", insertable = false, updatable = false) })
	//	@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID")
	private EmAnalyticsCategory emAnalyticsCategory;

	//bi-directional many-to-one association to EmAnalyticsFolder
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "FOLDER_ID", referencedColumnName = "FOLDER_ID"),
			@JoinColumn(name = "TENANT_ID", referencedColumnName = "TENANT_ID", insertable = false, updatable = false) })
	private EmAnalyticsFolder emAnalyticsFolder;

	//bi-directional many-to-one association to EmAnalyticsSearchParam

	@OneToMany(mappedBy = "emAnalyticsSearch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@PrivateOwned
	private Set<EmAnalyticsSearchParam> emAnalyticsSearchParams;

	@OneToOne(mappedBy = "emAnalyticsSearch", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private EmAnalyticsSearchLastAccess lastAccess;

	@Column(name = "TENANT_ID", insertable = false, updatable = false)
	long tenant;

	/*@Column(table = "EMS_ANALYTICS_LAST_ACCESS", name = "OBJECT_ID")
	private long objectId;

	@Column(table = "EMS_ANALYTICS_LAST_ACCESS", name = "ACCESSED_BY")
	private String accessedBy;

	@Column(table = "EMS_ANALYTICS_LAST_ACCESS", name = "OBJECT_TYPE")
	private long objectType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(table = "EMS_ANALYTICS_LAST_ACCESS", name = "ACCESS_DATE")
	private Date accessDate;*/

	public EmAnalyticsSearch()
	{
	}

	public Date getAccessDate()
	{
		if (lastAccess == null) {
			return null;
		}
		return lastAccess.getAccessDate();
	}

	public String getAccessedBy()
	{
		if (lastAccess == null) {
			return null;
		}
		return lastAccess.getAccessedBy();
	}

	/**
	 * @return the dASHBOARD_INELIGIBLE
	 */
	public String getDASHBOARD_INELIGIBLE()
	{
		return DASHBOARD_INELIGIBLE;
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
	public BigInteger getId()
	{
		return id;
	}

	public BigDecimal getIsLocked()
	{
		return isLocked;
	}

	public long getIsWidget()
	{
		return isWidget;
	}

	/**
	 * @return the lastAccess
	 */
	public EmAnalyticsLastAccess getLastAccess()
	{
		return lastAccess;
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

	/**
	 * @return the nAME_WIDGET_SOURCE
	 */
	public String getNAME_WIDGET_SOURCE()
	{
		return NAME_WIDGET_SOURCE;
	}

	public String getNameNlsid()
	{
		return nameNlsid;
	}

	public String getNameSubsystem()
	{
		return nameSubsystem;
	}

	public BigInteger getObjectId()
	{
		if (lastAccess == null) {
			return null;
		}
		return lastAccess.getObjectId();
	}

	public Long getObjectType()
	{
		if (lastAccess == null) {
			return null;
		}
		return lastAccess.getObjectType();
	}

	public String getOwner()
	{
		return owner;
	}

	/**
	 * @return the pROVIDER_ASSET_ROOT
	 */
	public String getPROVIDER_ASSET_ROOT()
	{
		return PROVIDER_ASSET_ROOT;
	}

	/**
	 * @return the pROVIDER_NAME
	 */
	public String getPROVIDER_NAME()
	{
		return PROVIDER_NAME;
	}

	//	public void setAccessedBy(String accessedBy)
	//	{
	//	}

	/**
	 * @return the pROVIDER_VERSION
	 */
	public String getPROVIDER_VERSION()
	{
		return PROVIDER_VERSION;
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

	/**
	 * @return the wIDGET_DEFAULT_HEIGHT
	 */
	public long getWIDGET_DEFAULT_HEIGHT()
	{
		return WIDGET_DEFAULT_HEIGHT;
	}

	/**
	 * @return the wIDGET_DEFAULT_WIDTH
	 */
	public long getWIDGET_DEFAULT_WIDTH()
	{
		return WIDGET_DEFAULT_WIDTH;
	}

	/**
	 * @return the wIDGET_GROUP_NAME
	 */
	public String getWIDGET_GROUP_NAME()
	{
		return WIDGET_GROUP_NAME;
	}

	/**
	 * @return the wIDGET_ICON
	 */
	public String getWIDGET_ICON()
	{
		return WIDGET_ICON;
	}

	/**
	 * @return the wIDGET_KOC_NAME
	 */
	public String getWIDGET_KOC_NAME()
	{
		return WIDGET_KOC_NAME;
	}

	/**
	 * @return the wIDGET_LINKED_DASHBOARD
	 */
	public long getWIDGET_LINKED_DASHBOARD()
	{
		return WIDGET_LINKED_DASHBOARD;
	}

	/**
	 * @return the wIDGET_SCREENSHOT_HREF
	 */
	public String getWIDGET_SCREENSHOT_HREF()
	{
		return WIDGET_SCREENSHOT_HREF;
	}

	//	/**
	//	 * @param lastAccess
	//	 *            the lastAccess to set
	//	 */
	//	public void setLastAccess(EmAnalyticsLastAccess lastAccess)
	//	{
	//		this.lastAccess = lastAccess;
	//	}

	/**
	 * @return the wIDGET_SUPPORT_TIME_CONTROL
	 */
	public String getWIDGET_SUPPORT_TIME_CONTROL()
	{
		return WIDGET_SUPPORT_TIME_CONTROL;
	}

	/**
	 * @return the wIDGET_TEMPLATE
	 */
	public String getWIDGET_TEMPLATE()
	{
		return WIDGET_TEMPLATE;
	}

	/**
	 * @return the wIDGET_VIEWMODEL
	 */
	public String getWIDGET_VIEWMODEL()
	{
		return WIDGET_VIEWMODEL;
	}

	public void setAccessDate(Date accessDate)
	{
		if (lastAccess == null) {
			lastAccess = new EmAnalyticsSearchLastAccess(getId(), getOwner());
			lastAccess.setEmAnalyticsSearch(this);
			lastAccess.setCreationDate(getCreationDate());
			lastAccess.setLastModificationDate(getLastModificationDate());
		}
		lastAccess.setAccessDate(accessDate);
	}

	/**
	 * @param dASHBOARD_INELIGIBLE
	 *            the dASHBOARD_INELIGIBLE to set
	 */
	public void setDASHBOARD_INELIGIBLE(String dASHBOARD_INELIGIBLE)
	{
		DASHBOARD_INELIGIBLE = dASHBOARD_INELIGIBLE;
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
	public void setId(BigInteger id)
	{
		this.id = id;
	}

	public void setIsLocked(BigDecimal isLocked)
	{
		this.isLocked = isLocked;
	}

	public void setIsWidget(long isWidget)
	{
		this.isWidget = isWidget;
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

	/**
	 * @param nAME_WIDGET_SOURCE
	 *            the nAME_WIDGET_SOURCE to set
	 */
	public void setNAME_WIDGET_SOURCE(String nAME_WIDGET_SOURCE)
	{
		NAME_WIDGET_SOURCE = nAME_WIDGET_SOURCE;
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

	/**
	 * @param pROVIDER_ASSET_ROOT
	 *            the pROVIDER_ASSET_ROOT to set
	 */
	public void setPROVIDER_ASSET_ROOT(String pROVIDER_ASSET_ROOT)
	{
		PROVIDER_ASSET_ROOT = pROVIDER_ASSET_ROOT;
	}

	/**
	 * @param pROVIDER_NAME
	 *            the pROVIDER_NAME to set
	 */
	public void setPROVIDER_NAME(String pROVIDER_NAME)
	{
		PROVIDER_NAME = pROVIDER_NAME;
	}

	/**
	 * @param pROVIDER_VERSION
	 *            the pROVIDER_VERSION to set
	 */
	public void setPROVIDER_VERSION(String pROVIDER_VERSION)
	{
		PROVIDER_VERSION = pROVIDER_VERSION;
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

	/**
	 * @param wIDGET_DEFAULT_HEIGHT
	 *            the wIDGET_DEFAULT_HEIGHT to set
	 */
	public void setWIDGET_DEFAULT_HEIGHT(long wIDGET_DEFAULT_HEIGHT)
	{
		WIDGET_DEFAULT_HEIGHT = wIDGET_DEFAULT_HEIGHT;
	}

	/**
	 * @param wIDGET_DEFAULT_WIDTH
	 *            the wIDGET_DEFAULT_WIDTH to set
	 */
	public void setWIDGET_DEFAULT_WIDTH(long wIDGET_DEFAULT_WIDTH)
	{
		WIDGET_DEFAULT_WIDTH = wIDGET_DEFAULT_WIDTH;
	}

	/**
	 * @param wIDGET_GROUP_NAME
	 *            the wIDGET_GROUP_NAME to set
	 */
	public void setWIDGET_GROUP_NAME(String wIDGET_GROUP_NAME)
	{
		WIDGET_GROUP_NAME = wIDGET_GROUP_NAME;
	}

	/**
	 * @param wIDGET_ICON
	 *            the wIDGET_ICON to set
	 */
	public void setWIDGET_ICON(String wIDGET_ICON)
	{
		WIDGET_ICON = wIDGET_ICON;
	}

	/**
	 * @param wIDGET_KOC_NAME
	 *            the wIDGET_KOC_NAME to set
	 */
	public void setWIDGET_KOC_NAME(String wIDGET_KOC_NAME)
	{
		WIDGET_KOC_NAME = wIDGET_KOC_NAME;
	}

	/**
	 * @param wIDGET_LINKED_DASHBOARD
	 *            the wIDGET_LINKED_DASHBOARD to set
	 */
	public void setWIDGET_LINKED_DASHBOARD(long wIDGET_LINKED_DASHBOARD)
	{
		WIDGET_LINKED_DASHBOARD = wIDGET_LINKED_DASHBOARD;
	}

	/**
	 * @param wIDGET_SCREENSHOT_HREF
	 *            the wIDGET_SCREENSHOT_HREF to set
	 */
	public void setWIDGET_SCREENSHOT_HREF(String wIDGET_SCREENSHOT_HREF)
	{
		WIDGET_SCREENSHOT_HREF = wIDGET_SCREENSHOT_HREF;
	}

	/**
	 * @param wIDGET_SUPPORT_TIME_CONTROL
	 *            the wIDGET_SUPPORT_TIME_CONTROL to set
	 */
	public void setWIDGET_SUPPORT_TIME_CONTROL(String wIDGET_SUPPORT_TIME_CONTROL)
	{
		WIDGET_SUPPORT_TIME_CONTROL = wIDGET_SUPPORT_TIME_CONTROL;
	}

	/**
	 * @param wIDGET_TEMPLATE
	 *            the wIDGET_TEMPLATE to set
	 */
	public void setWIDGET_TEMPLATE(String wIDGET_TEMPLATE)
	{
		WIDGET_TEMPLATE = wIDGET_TEMPLATE;
	}

	/**
	 * @param wIDGET_VIEWMODEL
	 *            the wIDGET_VIEWMODEL to set
	 */
	public void setWIDGET_VIEWMODEL(String wIDGET_VIEWMODEL)
	{
		WIDGET_VIEWMODEL = wIDGET_VIEWMODEL;
	}

}

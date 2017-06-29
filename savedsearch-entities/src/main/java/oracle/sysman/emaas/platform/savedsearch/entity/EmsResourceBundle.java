package oracle.sysman.emaas.platform.savedsearch.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiadai on 2017/6/14.
 */
@Entity
@IdClass(EmsResourceBundlePK.class)
@Table(name = "EMS_ANALYTICS_RESOURCE_BUNDLE")
@NamedQueries({
        @NamedQuery(name = "EmsResourceBundle.deleteByServiceName", query = "delete from EmsResourceBundle t where t.serviceName = :serviceName"),
        @NamedQuery(name = "EmsResourceBundle.loadModifyTimeByLangContry", query = "select t.lastModificationDate from EmsResourceBundle t where t.languageCode = :languageCode and t.countryCode = :countryCode and t.serviceName = :serviceName order by t.lastModificationDate desc"),
        @NamedQuery(name = "EmsResourceBundle.loadByLangContry", query = "select t from EmsResourceBundle t where t.languageCode = :languageCode and t.countryCode = :countryCode and t.serviceName = :serviceName order by t.lastModificationDate desc")
})
public class EmsResourceBundle implements Serializable {
    private static final long serialVersionUID = -3794500021876489604L;
    @Id
    @Column(name = "LANGUAGE_CODE")
    private String languageCode;
    @Id
    @Column(name = "COUNTRY_CODE")
    private String countryCode;
    @Id
    @Column(name = "SERVICE_NAME")
    private String serviceName;
    @Column(name = "SERVICE_VERSION")
    private String serviceVersion;
    @Lob
    @Basic(fetch = FetchType.EAGER)
    @Column(name = "PROPERTIES_FILE", columnDefinition = "CLOB NULL")
    private String propertiesFile;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_MODIFICATION_DATE")
    private Date lastModificationDate;

    /**
     * @return the languageCode
     */
    public String getLanguageCode()
    {
        return languageCode;
    }
    /**
     * @param languageCode the languageCode to set
     */
    public void setLanguageCode(String languageCode)
    {
        this.languageCode = languageCode;
    }
    /**
     * @return the countryCode
     */
    public String getCountryCode()
    {
        return countryCode;
    }
    /**
     * @param countryCode the countryCode to set
     */
    public void setCountryCode(String countryCode)
    {
        this.countryCode = countryCode;
    }
    /**
     * @return the serviceName
     */
    public String getServiceName()
    {
        return serviceName;
    }
    /**
     * @param serviceName the serviceName to set
     */
    public void setServiceName(String serviceName)
    {
        this.serviceName = serviceName;
    }
    /**
     * @return the serviceVersion
     */
    public String getServiceVersion()
    {
        return serviceVersion;
    }
    /**
     * @param serviceVersion the serviceVersion to set
     */
    public void setServiceVersion(String serviceVersion)
    {
        this.serviceVersion = serviceVersion;
    }
    /**
     * @return the propertiesFile
     */
    public String getPropertiesFile()
    {
        return propertiesFile;
    }
    /**
     * @param propertiesFile the propertiesFile to set
     */
    public void setPropertiesFile(String propertiesFile)
    {
        this.propertiesFile = propertiesFile;
    }
    /**
     * @return the lastModificationDate
     */
    public Date getLastModificationDate()
    {
        return lastModificationDate;
    }
    /**
     * @param lastModificationDate the lastModificationDate to set
     */
    public void setLastModificationDate(Date lastModificationDate)
    {
        this.lastModificationDate = lastModificationDate;
    }
}
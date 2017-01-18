package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;

/**
 * <p>
 * Java class for categoryDetails complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="categoryDetails">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="name">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="defaultFolderId" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="parameters" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="parameter" type="{}parameter" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "categoryDetails", propOrder = {

})
public class CategoryDetails
{

	/**
	 * <p>
	 * Java class for anonymous complex type.
	 * <p>
	 * The following schema fragment specifies the expected content contained within this class.
	 * 
	 * <pre>
	 * &lt;complexType>
	 *   &lt;complexContent>
	 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
	 *       &lt;sequence>
	 *         &lt;element name="parameter" type="{}parameter" maxOccurs="unbounded" minOccurs="0"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "parameter" })
	public static class Parameters
	{

		protected List<Parameter> parameter;

		/**
		 * Gets the value of the parameter property.
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the
		 * returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the
		 * parameter property.
		 * <p>
		 * For example, to add a new item, do as follows:
		 * 
		 * <pre>
		 * getParameter().add(newItem);
		 * </pre>
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link Parameter }
		 */
		public List<Parameter> getParameter()
		{
			if (parameter == null) {
				parameter = new ArrayList<Parameter>();
			}
			return parameter;
		}

	}

	@XmlElement(name = "Id")
	protected BigInteger id;
	@XmlElement(required = true, name = "Name")
	protected String name;
	@XmlElement(name = "Description")
	protected String description;
	@XmlElement(name = "ProviderName")
	protected String providerName;
	@XmlElement(name = "ProviderVersion")
	protected String providerVersion;
	@XmlElement(name = "ProviderDiscovery")
	protected String providerDiscovery;
	@XmlElement(name = "ProviderAssetRoot")
	protected String providerAssetRoot;
	@XmlElement(name = "DefaultFolderId")
	protected BigInteger defaultFolderId;

	@XmlElement(name = "Parameters")
	protected CategoryDetails.Parameters parameters;

	/**
	 * Gets the value of the defaultFolderId property.
	 * 
	 * @return possible object is {@link Integer }
	 */
	public BigInteger getDefaultFolderId()
	{
		return defaultFolderId;
	}

	/**
	 * Gets the value of the description property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link Integer }
	 */
	public BigInteger getId()
	{
		return id;
	}

	/**
	 * Gets the value of the name property.
	 * 
	 * @return possible object is {@link String }
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Gets the value of the parameters property.
	 * 
	 * @return possible object is {@link CategoryDetails.Parameters }
	 */
	public CategoryDetails.Parameters getParameters()
	{
		return parameters;
	}

	/**
	 * Gets the value of the provider asset root property.
	 *
	 * @return possible object is {@link String }
	 */
	public String getProviderAssetRoot()
	{
		return providerAssetRoot;
	}

	/**
	 * Gets the value of the provider discovery property.
	 *
	 * @return possible object is {@link String }
	 */
	public String getProviderDiscovery()
	{
		return providerDiscovery;
	}

	/**
	 * Gets the value of the provider name property.
	 *
	 * @return possible object is {@link String }
	 */
	public String getProviderName()
	{
		return providerName;
	}

	/**
	 * Gets the value of the provider version property.
	 *
	 * @return possible object is {@link String }
	 */
	public String getProviderVersion()
	{
		return providerVersion;
	}

	/**
	 * Sets the value of the defaultFolderId property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 */
	public void setDefaultFolderId(BigInteger value)
	{
		defaultFolderId = value;
	}

	/**
	 * Sets the value of the description property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setDescription(String value)
	{
		description = value;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 */
	public void setId(BigInteger value)
	{
		id = value;
	}

	/**
	 * Sets the value of the name property.
	 * 
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setName(String value)
	{
		name = value;
	}

	/**
	 * Sets the value of the parameters property.
	 * 
	 * @param value
	 *            allowed object is {@link CategoryDetails.Parameters }
	 */
	public void setParameters(CategoryDetails.Parameters value)
	{
		parameters = value;
	}

	/**
	 * Sets the value of the provider asset root property.
	 *
	 * @param providerAssetRoot
	 *            allowed object is {@link String }
	 */
	public void setProviderAssetRoot(String providerAssetRoot)
	{
		this.providerAssetRoot = providerAssetRoot;
	}

	/**
	 * Sets the value of the provider discovery property.
	 *
	 * @param providerDiscovery
	 *            allowed object is {@link String }
	 */
	public void setProviderDiscovery(String providerDiscovery)
	{
		this.providerDiscovery = providerDiscovery;
	}

	/**
	 * Sets the value of the provider name property.
	 *
	 * @param providerName
	 *            allowed object is {@link String }
	 */
	public void setProviderName(String providerName)
	{
		this.providerName = providerName;
	}

	/**
	 * Sets the value of the provider version property.
	 *
	 * @param providerVersion
	 *            allowed object is {@link String }
	 */
	public void setProviderVersion(String providerVersion)
	{
		this.providerVersion = providerVersion;
	}

}

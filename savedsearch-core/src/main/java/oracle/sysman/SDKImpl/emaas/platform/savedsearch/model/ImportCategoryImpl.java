package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ParameterDetails;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Category;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;

/**
 * <p>
 * Java class for importCategoryImpl complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="importCategoryImpl">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;all>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="Name">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="providerName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="providerVersion">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="providerDiscovery" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="providerAssetRoot">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Parameters" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Parameter" type="{}ParameterDetails" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}folderDet"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "importCategoryImpl", propOrder = {

})
public class ImportCategoryImpl
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
	 *         &lt;element name="Parameter" type="{}ParameterDetails" maxOccurs="unbounded" minOccurs="0"/>
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

		@XmlElement(name = "Parameter")
		protected List<ParameterDetails> parameter;

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
		 * Objects of the following type(s) are allowed in the list {@link ParameterDetails }
		 */
		public List<ParameterDetails> getParameter()
		{
			if (parameter == null) {
				parameter = new ArrayList<ParameterDetails>();
			}
			return parameter;
		}

	}

	@XmlElement(name = "Id")
	protected Integer id;
	@XmlElement(name = "Name", required = true)
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
	@XmlElement(name = "Parameters")
	protected ImportCategoryImpl.Parameters parameters;

	@XmlElementRef(name = "folderDet", type = JAXBElement.class)
	protected JAXBElement<?> folderDet;

	public Category getCategoryDetails()
	{

		CategoryImpl objCatImp = null;
		objCatImp = new CategoryImpl();
		objCatImp.setName(getName());
		objCatImp.setDescription(getDescription());
		objCatImp.setProviderName(getProviderName());
		objCatImp.setProviderVersion(getProviderVersion());
		objCatImp.setProviderDiscovery(getProviderDiscovery());
		objCatImp.setProviderAssetRoot(getProviderAssetRoot());
		List<Parameter> paraList = new ArrayList<Parameter>();
		if (getParameters() != null) {
			for (ParameterDetails objtmp : getParameters().getParameter()) {
				Parameter tmp = new Parameter();
				tmp.setName(objtmp.getName());
				tmp.setValue(objtmp.getValue());
				paraList.add(tmp);
			}
			objCatImp.setParameters(paraList);
		}

		return objCatImp;
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
	 * Gets the value of the folderDet property.
	 * 
	 * @return possible object is {@link JAXBElement }{@code <}{@link Object }{@code >} {@link JAXBElement }{@code <}{@link Integer }
	 *         {@code >} {@link JAXBElement }{@code <}{@link FolderDetails }{@code >}
	 */
	public JAXBElement<?> getFolderDet()
	{
		return folderDet;
	}

	public Object getFolderDetails()
	{
		if (folderDet == null) {
			 return null;
		 }
		Object obj = folderDet.getValue();
		FolderImpl fld = new FolderImpl();
		if (obj instanceof Integer) {
			 return obj;
		 }
		 else {
			FolderDetails fldDetails = (FolderDetails) obj;
			fld.setName(fldDetails.getName());
			fld.setDescription(fldDetails.getDescription());
			fld.setParentId(fldDetails.getParentId() == null ? 1 : fldDetails.getParentId());
			fld.setUiHidden(fldDetails.isUiHidden());
			return fld;
		}
	}

	/**
	 * Gets the value of the id property.
	 * 
	 * @return possible object is {@link Integer }
	 */
	public Integer getId()
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
	 * @return possible object is {@link ImportCategoryImpl.Parameters }
	 */
	public ImportCategoryImpl.Parameters getParameters()
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
	 * Sets the value of the folderDet property.
	 * 
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link Object }{@code >} {@link JAXBElement }{@code <}
	 *            {@link Integer }{@code >} {@link JAXBElement }{@code <}{@link FolderDetails }{@code >}
	 */
	public void setFolderDet(JAXBElement<?> value)
	{
		folderDet = value;
	}

	/**
	 * Sets the value of the id property.
	 * 
	 * @param value
	 *            allowed object is {@link Integer }
	 */
	public void setId(Integer value)
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
	 *            allowed object is {@link ImportCategoryImpl.Parameters }
	 */
	public void setParameters(ImportCategoryImpl.Parameters value)
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

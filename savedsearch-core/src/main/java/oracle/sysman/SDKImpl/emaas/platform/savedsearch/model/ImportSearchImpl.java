package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.CategoryDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.FolderDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.SearchParameterDetails;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.IdGenerator;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.ZDTContext;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Search;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchParameter;

/**
 * <p>
 * Java class for importSearchImpl complex type.
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="importSearchImpl">
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
 *         &lt;element name="metadata" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="queryStr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="locked" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="uiHidden" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="isWidget" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="SearchParameters" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SearchParameter" type="{}searchParameter" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element ref="{}categoryDet"/>
 *         &lt;element ref="{}folderDet"/>
 *       &lt;/all>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "importSearchImpl", propOrder = {

})
public class ImportSearchImpl
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
	 *         &lt;element name="SearchParameter" type="{}searchParameter" maxOccurs="unbounded" minOccurs="0"/>
	 *       &lt;/sequence>
	 *     &lt;/restriction>
	 *   &lt;/complexContent>
	 * &lt;/complexType>
	 * </pre>
	 */
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "searchParameter" })
	public static class SearchParameters
	{

		@XmlElement(name = "SearchParameter")
		protected List<SearchParameterDetails> searchParameter;

		/**
		 * Gets the value of the searchParameter property.
		 * <p>
		 * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to the
		 * returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for the
		 * searchParameter property.
		 * <p>
		 * For example, to add a new item, do as follows:
		 *
		 * <pre>
		 * getSearchParameter().add(newItem);
		 * </pre>
		 * <p>
		 * Objects of the following type(s) are allowed in the list {@link SearchParameter }
		 */
		public List<SearchParameterDetails> getSearchParameter()
		{
			if (searchParameter == null) {
				searchParameter = new ArrayList<SearchParameterDetails>();
			}
			return searchParameter;
		}

	}

	@XmlElement(name = "Id")
	protected BigInteger id;
	@XmlElement(required = true, name = "Name")
	protected String name;
	@XmlElement(name = "Description")
	protected String description;
	@XmlElement(name = "Metadata")
	protected String metadata;
	@XmlElement(name = "QueryStr")
	protected String queryStr;
	@XmlElement(name = "Locked")
	protected boolean locked;
	@XmlElement(name = "UiHidden")
	protected boolean uiHidden;
	@XmlElement(name = "IsWidget")
	protected boolean isWidget;
	@XmlElement(name = "SearchParameters")
	protected ImportSearchImpl.SearchParameters SearchParameters;
	@XmlElementRef(name = "categoryDet", type = JAXBElement.class)
	protected JAXBElement<?> categoryDet;

	@XmlElementRef(name = "folderDet", type = JAXBElement.class)
	protected JAXBElement<?> folderDet;

	/**
	 * Gets the value of the categoryDet property.
	 *
	 * @return possible object is {@link JAXBElement }{@code <}{@link Object }{@code >} {@link JAXBElement }{@code <}{@link Integer }
	 *         {@code >} {@link JAXBElement }{@code <}{@link CategoryDetails }{@code >}
	 */
	public JAXBElement<?> getCategoryDet()
	{
		return categoryDet;
	}

	public Object getCategoryDetails()
	{
		Object obj = categoryDet.getValue();
		CategoryImpl objCatImp = null;
		if (obj instanceof BigInteger) {
			return categoryDet.getValue();
		}
		else {
			CategoryDetails catDet = (CategoryDetails) obj;
			objCatImp = new CategoryImpl();
			objCatImp.setId(catDet.getId() == null ? IdGenerator.getIntUUID(ZDTContext.getRequestId()) : catDet.getId());
			objCatImp.setName(catDet.getName());
			objCatImp.setDefaultFolderId(catDet.getDefaultFolderId());
			objCatImp.setDescription(catDet.getDescription());
			objCatImp.setProviderName(catDet.getProviderName());
			objCatImp.setProviderVersion(catDet.getProviderVersion());
			objCatImp.setProviderDiscovery(catDet.getProviderDiscovery());
			objCatImp.setProviderAssetRoot(catDet.getProviderAssetRoot());
			if (catDet.getParameters() != null) {
				objCatImp.setParameters(catDet.getParameters().getParameter());
			}
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
	 * @return possible object is {@link JAXBElement }{@code <}{@link FolderImpl }{@code >} {@link JAXBElement }{@code <}
	 *         {@link Object }{@code >} {@link JAXBElement }{@code <}{@link Integer }{@code >}
	 */
	public JAXBElement<?> getFolderDet()
	{
		return folderDet;
	}

	public Object getFolderDetails()
	{
		Object obj = folderDet.getValue();
		FolderImpl fld = new FolderImpl();
		if (obj instanceof BigInteger) {
			return obj;
		}
		else {
			FolderDetails fldDetails = (FolderDetails) obj;
			fld.setId(fldDetails.getId() == null ? IdGenerator.getIntUUID(ZDTContext.getRequestId()) : fldDetails.getId());
			fld.setName(fldDetails.getName());
			fld.setDescription(fldDetails.getDescription());
			fld.setParentId(fldDetails.getParentId() == null ? BigInteger.ONE : fldDetails.getParentId());
			fld.setUiHidden(fldDetails.isUiHidden());
			return fld;
		}
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
	 * Gets the value of the isWidget property.
	 *
	 * @return possible object is {@link Boolean }
	 */
	public boolean getIsWidget()
	{
		return isWidget;
	}

	/**
	 * Gets the value of the metadata property.
	 *
	 * @return possible object is {@link String }
	 */
	public String getMetadata()
	{
		return metadata;
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
	 * Gets the value of the queryStr property.
	 *
	 * @return possible object is {@link String }
	 */
	public String getQueryStr()
	{
		return queryStr;
	}

	public Search getSearch()
	{
		SearchImpl search = new SearchImpl();
		search.setId(getId());
		search.setName(getName());
		search.setDescription(getDescription());
		search.setMetadata(getMetadata());
		search.setQueryStr(getQueryStr());
		search.setLocked(isLocked());
		search.setUiHidden(isUiHidden());
		search.setIsWidget(getIsWidget());
		List<SearchParameter> lstSearch = new ArrayList<SearchParameter>();
		if (getSearchParameters() != null) {

			for (SearchParameterDetails tmpDetails : getSearchParameters().getSearchParameter()) {
				SearchParameter tmp = new SearchParameter();
				tmp.setName(tmpDetails.getName());
				tmp.setAttributes(tmpDetails.getAttributes());
				tmp.setType(tmpDetails.getType());
				tmp.setValue(tmpDetails.getValue());
				lstSearch.add(tmp);
			}
		}
		search.setParameters(lstSearch);
		return search;
	}

	/**
	 * Gets the value of the searchParameters property.
	 *
	 * @return possible object is {@link ImportSearchImpl.SearchParameters }
	 */
	public ImportSearchImpl.SearchParameters getSearchParameters()
	{
		return SearchParameters;
	}

	/**
	 * Gets the value of the locked property.
	 *
	 * @return possible object is {@link Boolean }
	 */
	public boolean isLocked()
	{
		return locked;
	}

	/**
	 * Gets the value of the uiHidden property.
	 *
	 * @return possible object is {@link Boolean }
	 */
	public boolean isUiHidden()
	{
		return uiHidden;
	}

	/**
	 * Sets the value of the categoryDet property.
	 *
	 * @param value
	 *            allowed object is {@link JAXBElement }{@code <}{@link Object }{@code >} {@link JAXBElement }{@code <}
	 *            {@link Integer }{@code >} {@link JAXBElement }{@code <}{@link CategoryDetails }{@code >}
	 */
	public void setCategoryDet(JAXBElement<?> value)
	{
		categoryDet = value;
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
	 *            allowed object is {@link JAXBElement }{@code <}{@link FolderImpl }{@code >} {@link JAXBElement }{@code <}
	 *            {@link Object }{@code >} {@link JAXBElement }{@code <}{@link Integer }{@code >}
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
	public void setId(BigInteger value)
	{
		id = value;
	}

	/**
	 * Sets the value of the isWidget property.
	 *
	 * @param isWidget
	 *            allowed object is {@link Boolean }
	 */
	public void setIsWidget(boolean isWidget)
	{
		this.isWidget = isWidget;
	}

	/**
	 * Sets the value of the locked property.
	 *
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setLocked(boolean value)
	{
		locked = value;
	}

	/**
	 * Sets the value of the metadata property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setMetadata(String value)
	{
		metadata = value;
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
	 * Sets the value of the queryStr property.
	 *
	 * @param value
	 *            allowed object is {@link String }
	 */
	public void setQueryStr(String value)
	{
		queryStr = value;
	}

	/**
	 * Sets the value of the searchParameters property.
	 *
	 * @param value
	 *            allowed object is {@link ImportSearchImpl.SearchParameters }
	 */
	public void setSearchParameters(ImportSearchImpl.SearchParameters value)
	{
		SearchParameters = value;
	}

	/**
	 * Sets the value of the uiHidden property.
	 *
	 * @param value
	 *            allowed object is {@link Boolean }
	 */
	public void setUiHidden(boolean value)
	{
		uiHidden = value;
	}

}

package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch;


import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.ImportSearchImpl;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.CategorySet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.FolderSet;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.Parameter;
import oracle.sysman.emSDK.emaas.platform.savedsearch.model.SearchSet;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the sr2 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Category_QNAME = new QName("", "Category");
    private final static QName _Folder_QNAME = new QName("", "Folder");
    private final static QName _CategoryId_QNAME = new QName("", "CategoryId");
    private final static QName _CategoryDet_QNAME = new QName("", "categoryDet");
    private final static QName _FolderDet_QNAME = new QName("", "folderDet");
    private final static QName _SearchSet_QNAME = new QName("", "SearchSet");
    private final static QName _FolderId_QNAME = new QName("", "FolderId");
    private final static QName _FolderSet_QNAME = new QName("", "FolderSet");
    private final static QName _CategorySet_QNAME = new QName("", "CategorySet");
    private final static QName _DefaultFolderId_QNAME = new QName("", "DefaultFolderId");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: sr2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ImportSearchImpl }
     * 
     */
    public ImportSearchImpl createImportSearchImpl() {
        return new ImportSearchImpl();
    }

    /**
     * Create an instance of {@link CategoryDetails }
     * 
     */
    public CategoryDetails createCategoryDetails() {
        return new CategoryDetails();
    }

    /**
     * Create an instance of {@link FolderImpl }
     * 
     */
    public FolderDetails createFolderDetails() {
        return new FolderDetails();
    }

    /**
     * Create an instance of {@link SearchSet }
     * 
     */
    public SearchSet createSearchSet() {
        return new SearchSet();
    }

    /**
     * Create an instance of {@link SearchParameter }
     * 
     */
    public SearchParameterDetails createSearchParameterDetails() {
        return new SearchParameterDetails();
    }

    /**
     * Create an instance of {@link Parameter }
     * 
     */
    public Parameter createParameter() {
        return new Parameter();
    }

    /**
     * Create an instance of {@link ImportSearchImpl.SearchParameters }
     * 
     */
    public ImportSearchImpl.SearchParameters createImportSearchImplSearchParameters() {
        return new ImportSearchImpl.SearchParameters();
    }

    /**
     * Create an instance of {@link CategoryDetails.Parameters }
     * 
     */
    public CategoryDetails.Parameters createCategoryDetailsParameters() {
        return new CategoryDetails.Parameters();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CategoryDetails }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Category", substitutionHeadNamespace = "", substitutionHeadName = "categoryDet")
    public JAXBElement<CategoryDetails> createCategory(CategoryDetails value) {
        return new JAXBElement<CategoryDetails>(_Category_QNAME, CategoryDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FolderImpl }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Folder", substitutionHeadNamespace = "", substitutionHeadName = "folderDet")
    public JAXBElement<FolderDetails> createFolder(FolderDetails value) {
        return new JAXBElement<FolderDetails>(_Folder_QNAME, FolderDetails.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CategoryId", substitutionHeadNamespace = "", substitutionHeadName = "categoryDet")
    public JAXBElement<Integer> createCategoryId(Integer value) {
        return new JAXBElement<Integer>(_CategoryId_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "categoryDet")
    public JAXBElement<Object> createCategoryDet(Object value) {
        return new JAXBElement<Object>(_CategoryDet_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "folderDet")
    public JAXBElement<Object> createFolderDet(Object value) {
        return new JAXBElement<Object>(_FolderDet_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchSet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "SearchSet")
    public JAXBElement<SearchSet> createSearchSet(SearchSet value) {
        return new JAXBElement<SearchSet>(_SearchSet_QNAME, SearchSet.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FolderId", substitutionHeadNamespace = "", substitutionHeadName = "folderDet")
    public JAXBElement<Integer> createFolderId(Integer value) {
        return new JAXBElement<Integer>(_FolderId_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    /**
     * Create an instance of {@link FolderSet }
     * 
     */

public FolderSet createFolderSet() {
        return new FolderSet();
    }


/**
     * Create an instance of {@link JAXBElement }{@code <}{@link FolderSet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "FolderSet")
    public JAXBElement<FolderSet> createFolderSet(FolderSet value) {
        return new JAXBElement<FolderSet>(_FolderSet_QNAME, FolderSet.class, null, value);
    }
    
    /**
     * Create an instance of {@link CategorySet }
     * 
     */

    public CategorySet createCategorySet() {
        return new CategorySet();
    }


/**
     * Create an instance of {@link JAXBElement }{@code <}{@link CategorySet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "CategorySet")
    public JAXBElement<CategorySet> createCategorySet(CategorySet value) {
        return new JAXBElement<CategorySet>(_CategorySet_QNAME, CategorySet.class, null, value);
    }
    
    
    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DefaultFolderId", substitutionHeadNamespace = "", substitutionHeadName = "folderDet")
    public JAXBElement<Integer> createDefaultFolderId(Integer value) {
        return new JAXBElement<Integer>(_DefaultFolderId_QNAME, Integer.class, null, value);
    }

}

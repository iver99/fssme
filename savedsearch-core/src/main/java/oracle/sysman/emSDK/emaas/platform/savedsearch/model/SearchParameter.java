package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

import javax.xml.bind.annotation.XmlType;

/**
 * Class <code>SearchParameter</code> represents a named parameter in the search object.
 * 
 * @see oracle.sysman.emSDK.core.emanalytics.api.Parameter
 * @version $Header:
 *          emcore/jsrc/sdkcore/SdkcoreEmanalyticsPublicModel/src/oracle/sysman/emSDK/core/emanalytics/api/search/SearchParameter
 *          .java /st_emgc_pt-13.1mstr/1 2013/12/27 04:37:33 saurgarg Exp $
 * @author saurgarg
 * @since 13.1.0.0
 */
@XmlType(propOrder = { "attributes" })
public class SearchParameter extends Parameter
{
	private String attributes;

	/**
	 * Sets the attributes for the parameter //TODO: fixup when more symantics is available
	 * 
	 * @param attributes
	 *            , attributes for the parameter
	 */
	public void setAttributes(String attributes)
	{
		this.attributes = attributes;
	}

	/**
	 * Returns the attributes of the parameter.
	 * 
	 * @return attributes of the parameter
	 */
	public String getAttributes()
	{
		return attributes;
	}

}

package oracle.sysman.emSDK.emaas.platform.savedsearch.test.util;

import javax.xml.bind.JAXBException;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ObjectFactory;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;

import org.testng.annotations.Test;

public class JAXBUtilTest
{
	static final JAXBUtil jaxb = new JAXBUtil();

	@Test(expectedExceptions = { Exception.class, JAXBException.class })
	public void getJAXBContext() throws Exception, JAXBException
	{

		JAXBUtil.getJAXBContext(ObjectFactory.class);

	}
}
package oracle.sysman.emSDK.emaas.platform.savedsearch.test.util;

import javax.xml.bind.JAXBException;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.model.importsearch.ObjectFactory;
import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.JAXBUtil;

import org.testng.annotations.Test;

public class JAXBUtilTest
{

	@Test(expectedExceptions = { Exception.class, JAXBException.class },groups = {"s1"})
	public void getJAXBContext() throws JAXBException
	{

		JAXBUtil.getJAXBContext(ObjectFactory.class);

	}
}

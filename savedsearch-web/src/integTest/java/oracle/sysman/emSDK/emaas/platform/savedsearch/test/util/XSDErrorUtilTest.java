package oracle.sysman.emSDK.emaas.platform.savedsearch.test.util;

import oracle.sysman.emSDK.emaas.platform.savedsearch.ws.rest.util.XSDErrorUtil;

import org.testng.Assert;
import org.testng.annotations.Test;

public class XSDErrorUtilTest
{

	@Test
	public void getErrorMessage()
	{

		Assert.assertEquals(XSDErrorUtil.getErrorMessage("cvc-complex-type.2.1").trim(),
				"The element must not have content according to schema definition.".trim());
		Assert.assertEquals(XSDErrorUtil.getErrorMessage("cvc-complex-type.2.1").trim(),
				"The element must not have content according to schema definition.");
		Assert.assertEquals("The element which is not described in schema is trying to be used.",
				XSDErrorUtil.getErrorMessage("cvc-complex-type.2.4.a").trim());
		Assert.assertEquals("Child elements are missing from the element.", XSDErrorUtil
				.getErrorMessage("cvc-complex-type.2.4.b").trim());
		Assert.assertEquals("Please specify correct field value for the element.".trim().replaceAll(" ", "") + "\n"
				+ "  Please specify correct field value for the element.".replaceAll(" ", ""),
				XSDErrorUtil.getErrorMessage("cvc-datatype-valid.1.2.1").trim().replaceAll(" ", "").replaceAll("/n", ""));
		Assert.assertEquals("The value '' of element '' is not valid.", XSDErrorUtil.getErrorMessage("cvc-type.3.1.3").trim());

	}
}

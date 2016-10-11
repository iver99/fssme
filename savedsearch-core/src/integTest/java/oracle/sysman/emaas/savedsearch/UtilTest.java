package oracle.sysman.emaas.savedsearch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;

import org.testng.Assert;
import org.testng.annotations.Test;

public class UtilTest extends BaseTest
{
	@Test (groups = {"s1"})
	public void testDateUtil()
	{
		TimeZone localTZ = TimeZone.getDefault();
		TimeZone utcTZ = TimeZone.getTimeZone("UTC");
		long localNow = new Date().getTime();
		long utcNow = DateUtil.getGatewayTime().getTime();
		long offset = localNow - utcNow;
		//		System.out.println(offset / 1000 / 60);
		boolean isLocalUTC = localTZ.equals(utcTZ);
		if (isLocalUTC) {
			Assert.assertTrue(Math.abs(offset) < 1000, "Invalid UTC time: offset=" + offset);
		}
		else {
			Assert.assertTrue(Math.abs(offset) >= 1 * 60 * 60 * 1000, "Invalid UTC time: offset=" + offset);
		}

		SimpleDateFormat sdf = DateUtil.getDateFormatter();
		long time = 1406040533048L;
		Date d = new Date(time);
		Assert.assertNotNull(sdf, "Date formatter is NULL");
		final String EXPECTED = "2014-07-22T14:48:53.048Z";
		String actual = sdf.format(d);
		Assert.assertEquals(actual, EXPECTED);
	}
}

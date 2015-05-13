package oracle.sysman.emaas.savedsearch;

import java.util.TimeZone;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.PersistenceManager;
import oracle.sysman.SDKImpl.emaas.platform.savedsearch.util.DateUtil;

/**
 * Each test needs to inherit from this
 *
 * @author miao
 */
public abstract class BaseTest
{
	protected static PersistenceManager pm;
	static {
		System.setProperty(PersistenceManager.TESTENV_PROP, "true");
		DateUtil.setTimeZone(TimeZone.getTimeZone("UTC"));
	}
}

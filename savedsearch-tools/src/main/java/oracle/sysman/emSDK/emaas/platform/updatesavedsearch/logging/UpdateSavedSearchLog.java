package oracle.sysman.emSDK.emaas.platform.updatesavedsearch.logging;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class UpdateSavedSearchLog
{

	public static Logger getLogger(Class o)
	{
		UpdateSavedSearchLog.configureLogger();
		Logger log = Logger.getLogger(o.getName());
		return log;
	}

	public static Logger getLogger(Object o)
	{
		UpdateSavedSearchLog.configureLogger();
		Logger log = Logger.getLogger(o.getClass().getName());
		return log;
	}

	private static void configureLogger()
	{
		InputStream stream = ClassLoader.getSystemResourceAsStream("log4j_ssf.properties");
		Properties props = new Properties();
		try {
			props.load(stream);
		}
		catch (IOException e1) {
			System.out.println(e1);
		}
		PropertyConfigurator.configure(props);
	}

}

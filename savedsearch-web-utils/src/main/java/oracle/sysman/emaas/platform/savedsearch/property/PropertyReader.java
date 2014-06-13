package oracle.sysman.emaas.platform.savedsearch.property;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader
{

	private static final boolean RUNNING_IN_CONTAINER = getRunningInContainer();
	public static final String CONFIG_DIR = "/opt/ORCLemaas/Applications/SavedSearchService/init/";//getInstallDir() + "config";

	public static final String SERVICE_PROPS = "servicemanager.properties";

	public static Properties loadProperty(String filename) throws IOException
	{
		Properties prop = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream(CONFIG_DIR + File.separator + filename);
			prop.load(input);

		}
		catch (IOException ex) {
			ex.printStackTrace();
		}
		finally {
			if (input != null) {
				try {
					input.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return prop;

	}

	public static final String getInstallDir()
	{
		if (RUNNING_IN_CONTAINER) {
			return System.getProperty("user.dir") + File.separator + ".." + File.separator;
		}
		else {
			return System.getProperty("java.home") + File.separator + ".." + File.separator + "..";
		}
	}

	public static final boolean getRunningInContainer()
	{
		final String JNDI_INITIAL_CONTEXT = System.getProperty("java.naming.factory.initial");
		return JNDI_INITIAL_CONTEXT != null
				&& JNDI_INITIAL_CONTEXT.startsWith("weblogic")
				&& (System.getProperty("weblogic.home") != null || System.getProperty("wls.home") != null || System
						.getProperty("weblogic.management.startmode") != null);
	}
}

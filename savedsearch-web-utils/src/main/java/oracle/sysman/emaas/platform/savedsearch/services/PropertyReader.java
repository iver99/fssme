package oracle.sysman.emaas.platform.savedsearch.services;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader
{

	public static Properties loadProperty(String filename)
	{
		Properties prop = new Properties();
		InputStream input = null;
		try {

			input = new FileInputStream(filename);
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

}

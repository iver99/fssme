package oracle.sysman.SDKImpl.emaas.platform.savedsearch.model;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SearchManagerTestMockup
{
	private static final String CONNECTION_PROPS_FILE = "TestNG.properties";

	public static void incrementSeq()
	{
		Connection conn = null;
		Statement stmt = null;
		String sql = "SELECT EMS_ANALYTICS_SEARCH_SEQ.NEXTVAL FROM DUAL";

		try {
			conn = SearchManagerTestMockup.createConnection();
			stmt = conn.createStatement();
			stmt.execute(sql);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if (stmt != null) {
				try {
					stmt.close();
				}
				catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static Connection createConnection() throws ClassNotFoundException, SQLException
	{
		Properties pr = SearchManagerTestMockup.loadProperties(CONNECTION_PROPS_FILE);
		Class.forName(pr.getProperty("javax.persistence.jdbc.driver"));
		String url = pr.getProperty("javax.persistence.jdbc.url");
		String user = pr.getProperty("javax.persistence.jdbc.user");
		String password = pr.getProperty("javax.persistence.jdbc.password");
		return DriverManager.getConnection(url, user, password);
	}

	private static Properties loadProperties(String testPropsFile)
	{
		Properties connectionProps = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(testPropsFile);
			connectionProps.load(input);
			return connectionProps;
		}
		catch (Exception ex) {
			ex.printStackTrace();

		}
		return connectionProps;

	}
}

package oracle.sysman.emaas.savedsearch;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.QAToolUtil;

public class SearchManagerTestMockup
{
	private static final String CONNECTION_PROPS_FILE = "TestNG.properties";

	public static void executeRepeatedly(int threadNum, final int repeatsPerThread, final Runnable r,
			final Runnable postThreadRun, final Runnable postAllRun) throws InterruptedException
	{
		ExecutorService exec = Executors.newFixedThreadPool(threadNum);
		final double[] threadAverageDuration = new double[threadNum];
		for (int i = 0; i < threadNum; i++) {
			final int threadIndex = i;
			exec.execute(new Runnable() {
				@Override
				public void run()
				{
					try {
						long start = System.currentTimeMillis();
						for (int j = 0; j < repeatsPerThread; j++) {
							r.run();
							//System.out.println("Duration for operation is " + duration + "ms");
						}
						long totalDuration = System.currentTimeMillis() - start;
						threadAverageDuration[threadIndex] = totalDuration / repeatsPerThread;
						System.out.println("Average duration for each operation in current thread is "
								+ threadAverageDuration[threadIndex] + "ms");
						if (postThreadRun != null) {
							postThreadRun.run();
						}
					}
					catch (SecurityException e) {
						e.printStackTrace();
					}
					catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			});
		}
		exec.shutdown();
		exec.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
		double average = 0;
		for (int i = 0; i < threadNum; i++) {
			average += threadAverageDuration[i];
		}
		average /= threadNum;
		System.out.println("Average duration is: " + average + " ms");
		if (postAllRun != null) {
			postAllRun.run();
		}
	}

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
		if (System.getenv("T_WORK") != null) {
			pr = QAToolUtil.getDbProperties();
		}
		Class.forName(pr.getProperty(QAToolUtil.JDBC_PARAM_DRIVER_VALUE));
		String url = pr.getProperty(QAToolUtil.JDBC_PARAM_URL);
		String user = pr.getProperty(QAToolUtil.JDBC_PARAM_USER);
		String password = pr.getProperty(QAToolUtil.JDBC_PARAM_PASSWORD);
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

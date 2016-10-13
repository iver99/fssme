package oracle.sysman.emaas.savedsearch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import oracle.sysman.SDKImpl.emaas.platform.savedsearch.persistence.QAToolUtil;

public class SearchManagerTestMockup
{

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
		if (postAllRun != null) {
			postAllRun.run();
		}
	}

	private static Connection createConnection() throws ClassNotFoundException, SQLException
	{
		Properties pr = new Properties();
		//if (System.getenv("T_WORK") != null) {
		pr = QAToolUtil.getDbProperties();
		//}
		Class.forName(pr.getProperty(QAToolUtil.JDBC_PARAM_DRIVER_VALUE));
		String url = pr.getProperty(QAToolUtil.JDBC_PARAM_URL);
		String user = pr.getProperty(QAToolUtil.JDBC_PARAM_USER);
		String password = pr.getProperty(QAToolUtil.JDBC_PARAM_PASSWORD);
		return DriverManager.getConnection(url, user, password);
	}

	/*private static Properties loadProperties(String testPropsFile)
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

	}*/
}

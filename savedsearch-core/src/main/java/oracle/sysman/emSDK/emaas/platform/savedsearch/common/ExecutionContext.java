package oracle.sysman.emSDK.emaas.platform.savedsearch.common;

public class ExecutionContext
{
	private static ExecutionContext instance = new ExecutionContext();

	public static ExecutionContext getExecutionContext()
	{
		return instance;
	}

	private ExecutionContext()
	{
	}

	public String getCurrentUser()
	{
		// TODO: to be updated once user/role for EMaaS is supported
		return "SYSMAN";
	}

}

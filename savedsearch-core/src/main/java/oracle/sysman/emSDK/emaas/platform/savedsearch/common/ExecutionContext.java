package oracle.sysman.emSDK.emaas.platform.savedsearch.common;

import oracle.sysman.emSDK.emaas.platform.savedsearch.model.TenantContext;

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
		String name = null;
		if (TenantContext.getContext() != null) {
			name = TenantContext.getContext().getUsername();
		}
		return name;
	}

}

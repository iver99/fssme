package oracle.sysman.emSDK.emaas.platform.savedsearch.model;

public enum ParameterType
{
	STRING
	{
		@Override
		public int getIntValue()
		{
			return 1;
		}
	},
	CLOB
	{
		@Override
		public int getIntValue()
		{
			return 2;
		}
	};

	public static ParameterType fromIntValue(int value)
	{
		if (value == 1) {
			return STRING;
		}
		if (value == 2) {
			return CLOB;
		}
		return null;
	}

	public abstract int getIntValue();
}

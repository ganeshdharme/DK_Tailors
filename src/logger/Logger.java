package logger;

public class Logger
{
	private static boolean myIsLogsEnabled = false;

	public static void SetEnable(boolean b)
	{
		myIsLogsEnabled = b;
	}

	public static void Log(String log)
	{
		if (!myIsLogsEnabled)
			return;

		Exception exception = new Exception();
		String callerFileName = exception.getStackTrace()[1].getFileName();
		int callerLineNumber = exception.getStackTrace()[1].getLineNumber();

		String output = callerFileName + "(" + callerLineNumber + "): ";
		output += log;
		System.out.println(output);
	}

}

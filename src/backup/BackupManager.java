package backup;

public class BackupManager
{

	private static BackupManager myInstance = null;

	private BackupManager()
	{
	}

	public void Init()
	{
	}

	public BackupManager GetInstance()
	{
		if (myInstance == null)
			myInstance = new BackupManager();

		return myInstance;
	}

}

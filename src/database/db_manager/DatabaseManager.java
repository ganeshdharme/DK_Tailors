package database.db_manager;

import database.Database;
import parser.sqlConnection.DB_ConnectionSettingsParser;

public class DatabaseManager
{

	private Database myDatabase;
	private DB_ConnectionSettingsParser mySettingsParser;

	private static DatabaseManager myInstance = null;

	private DatabaseManager()
	{
		myDatabase = null;
		mySettingsParser = new DB_ConnectionSettingsParser(
				"res/settings/connectionLocal.info"); /*
														 * res/settings/
														 * connectionLocal.info
														 */
	}

	public void CreateDatabase()
	{
		String username = mySettingsParser.GetAttributeValue("username");
		String password = mySettingsParser.GetAttributeValue("password");
		String databaseName = mySettingsParser.GetAttributeValue("database");
		int port = Integer.parseInt(mySettingsParser.GetAttributeValue("port"));

		myDatabase = new Database(databaseName);
		myDatabase.OpenConnection(port, username, password);
	}

	public void DeleteDatabase(Database db)
	{
	}

	public Database GetDatabase()
	{
		return myDatabase;
	}

	public static DatabaseManager GetInstance()
	{
		if (myInstance == null)
			myInstance = new DatabaseManager();

		return myInstance;
	}
}

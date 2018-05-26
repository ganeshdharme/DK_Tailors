package application;

import database.db_manager.DatabaseManager;
import database.table.DB_TableManager;
import form.manager.FormManager;
import javafx.application.Application;
import javafx.stage.Stage;
import license.LicenseManager;
import logger.Logger;

public class App extends Application
{
	private Stage myStage;

	public App()
	{
	}

	public App(Stage stage)
	{
		myStage = stage;
	}

	private void Start()
	{
		SaveManager.GetInstance().Init();

		// Initialize the database system
		DatabaseManager.GetInstance().CreateDatabase();
		DB_TableManager.GetInstance().Init();

		FormManager.GetInstance().Init();
	}

	private boolean CheckLicense()
	{
		// Initialize the license manager
		LicenseManager.GetInstance().Init();
		return LicenseManager.GetInstance().CheckLicense();
	}

	@Override
	public void start(Stage primaryStage)
	{
		App app = new App(primaryStage);

		if (app.CheckLicense())
		{
			app.Start();
		}
		else
		{
			System.exit(0);
		}
	}

	public static void main(String[] args)
	{

		for (int i = 0; i < args.length; i++)
		{
			if (args[i].equals("-log"))
			{
				Logger.SetEnable(true);
			}
		}

		launch(args);
	}

}

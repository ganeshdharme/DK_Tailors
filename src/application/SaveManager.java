package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import logger.Logger;
import parser.sqlConnection.DB_ConnectionSettingsAttribute;

// Singleton
public class SaveManager
{
	private static SaveManager myInstance = null;
	private File myHomePageImageFile = null;

	private String myHomePageImagePath;

	public SaveManager()
	{
	}

	public void Init()
	{
		myHomePageImageFile = new File("image.save");
		try
		{
			if (myHomePageImageFile.exists())
			{
				FileReader reader = new FileReader(myHomePageImageFile);
				BufferedReader bufferedReader = new BufferedReader(reader);

				String line = "";
				while ((line = bufferedReader.readLine()) != null)
				{
					myHomePageImagePath = line;
				}

				bufferedReader.close();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	public void SetHomePageImagePath(String homePageImagePath)
	{
		myHomePageImagePath = homePageImagePath;

		Logger.Log("Home Page Image Path: " + homePageImagePath);

		// write to file
		try
		{
			FileWriter fw = new FileWriter(myHomePageImageFile);
			fw.write(myHomePageImagePath);
			fw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public boolean DoesImageFileExist()
	{
		return myHomePageImageFile.exists();
	}

	public String GetImagePath()
	{
		return myHomePageImagePath;
	}

	public File GetImageFile()
	{
		return myHomePageImageFile;
	}

	public static SaveManager GetInstance()
	{
		if (myInstance == null)
			myInstance = new SaveManager();

		return myInstance;
	}
}

package license;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JOptionPane;

public class LicenseManager
{

	private static LicenseManager myInstance = null;
	private String myValidLicenseKey = null;
	private String myLicenseFileName = null;

	private LicenseManager()
	{
	}

	public void Init()
	{
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int date = calendar.get(Calendar.DATE);
		int hour = calendar.get(Calendar.HOUR) + 1;
		myValidLicenseKey = GenerateKey(year, month, date, hour);

		myLicenseFileName = GetLicenseFileName();
	}

	private String GenerateKey(int year, int month, int date, int hour)
	{
		String str1 = Integer.toString((int) Math.abs((Math.sin((year * month * date * hour)) * 9999999)));
		String str2 = Integer.toString((int) Math.abs((Math.cos((year * month * date * hour)) * 9999999)));
		String result = str1 + str2;
		return result;
	}

	public boolean CheckLicense()
	{
		// check if license file exists
		if (CheckIfLicenseFileExists())
			return true;

		String inputKey = JOptionPane.showInputDialog(null, "Enter License Key: ");
		if ((inputKey == null) || (inputKey == ""))
			return false;

		if (myValidLicenseKey.equals(inputKey))
		{
			JOptionPane.showMessageDialog(null, "Valid License Key!");
			GenerateLicenseFile();
			return true;
		}
		JOptionPane.showMessageDialog(null, "Invalid License Key!");
		return false;
	}

	private boolean CheckIfLicenseFileExists()
	{
		File file = new File(myLicenseFileName);
		if (!file.exists())
			return false;

		WriteDateToLicenseFile();
		return true;
	}

	private void GenerateLicenseFile()
	{
		File file = new File(myLicenseFileName);
		try
		{
			file.createNewFile();
			WriteDateToLicenseFile();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void WriteDateToLicenseFile()
	{
		File file = new File(myLicenseFileName);
		try
		{
			FileWriter writer = new FileWriter(file);
			Date date = Calendar.getInstance().getTime();
			writer.write("Last Session: " + date.toString());
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	private String GetLicenseFileName()
	{
		String licenseFileName = "";
		String result = "";
		try
		{
			Process p = Runtime.getRuntime().exec("wmic baseboard get manufacturer");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

			String line = "";
			while ((line = input.readLine()) != null)
			{
				result += line;
			}
		}
		catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Something went wrong with License Manager!");
		}

		String temp = "";
		for (int i = 0; i < result.length(); i++)
		{
			char c = result.charAt(i);
			if (c != ' ')
				temp += c;
		}

		licenseFileName = temp.substring("SerialNumber".length(), temp.length());

		return licenseFileName;
	}

	public static LicenseManager GetInstance()
	{
		if (myInstance == null)
			myInstance = new LicenseManager();

		return myInstance;
	}

}

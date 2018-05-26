package parser.sqlConnection;

public class DB_ConnectionSettingsAttribute
{

	private String myKey;
	private String myValue;

	public DB_ConnectionSettingsAttribute(String key, String value)
	{
		myKey = key;
		myValue = value;
	}

	public String GetKey()
	{
		return myKey;
	}

	public String GetValue()
	{
		return myValue;
	}

	public void SetValue(String value)
	{
		myValue = value;
	}

	public String ToString()
	{
		return (myKey + ":" + myValue);
	}

}

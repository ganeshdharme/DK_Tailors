package database.table;

import java.util.HashMap;

import database.table.customerBalance.DBTable_CustomerBalance;
import database.table.customerDetails.DBTable_CustomerDetails;
import database.table.customerMeasurements.DBTable_CustomerMeasurements;
import database.table.customerMeasurements.pantMeasurements.DBTable_PantMeasurements;
import database.table.customerMeasurements.shirtMeasurements.DBTable_ShirtMeasurements;
import database.table.customerTransaction.DBTable_CustomerTransaction;
import logger.Logger;

public class DB_TableManager
{

	private HashMap<String, DBTable> myTables;
	private static DB_TableManager myInstance = null;

	private DB_TableManager()
	{
	}

	public void Init()
	{
		myTables = new HashMap<String, DBTable>();
		AddTable(new DBTable_CustomerDetails(DB_TableNames.CUSTOMER_DETAILS));
		AddTable(new DBTable_CustomerMeasurements(DB_TableNames.CUSTOMER_MEASUREMENTS));
		AddTable(new DBTable_CustomerBalance(DB_TableNames.CUSTOMER_BALANCE));
		AddTable(new DBTable_CustomerTransaction(DB_TableNames.CUSTOMER_TRANSACTIONS));
		AddTable(new DBTable_ShirtMeasurements(DB_TableNames.SHIRT_MEASUREMENTS));
		AddTable(new DBTable_PantMeasurements(DB_TableNames.PANT_MEASUREMENTS));

		LoadAllRecords();
	}

	private void LoadAllRecords()
	{
		boolean success = false;
		success |= GetTable(DB_TableNames.CUSTOMER_DETAILS).LoadAllRecords();
		success |= GetTable(DB_TableNames.CUSTOMER_MEASUREMENTS).LoadAllRecords();
		success |= GetTable(DB_TableNames.CUSTOMER_BALANCE).LoadAllRecords();
		success |= GetTable(DB_TableNames.CUSTOMER_TRANSACTIONS).LoadAllRecords();
		success |= GetTable(DB_TableNames.SHIRT_MEASUREMENTS).LoadAllRecords();
		success |= GetTable(DB_TableNames.PANT_MEASUREMENTS).LoadAllRecords();
		assert success;
	}

	public void AddTable(DBTable table)
	{
		if (!myTables.containsValue(table))
		{
			myTables.put(table.GetName(), table);
			return;
		}

		assert false : ("Table " + table.GetName() + " already exists!");
	}

	public void DeleteTable(String name)
	{
		if (myTables.containsKey(name))
		{
			DBTable table = myTables.remove(name);
			table.DeleteTable();
			return;
		}

		Logger.Log("Unable to delete table: " + name + " (no table exists by this name!)");
	}

	public DBTable GetTable(String name)
	{
		if (myTables.containsKey(name))
			return myTables.get(name);

		assert false;
		return null;
	}

	public static DB_TableManager GetInstance()
	{
		if (myInstance == null)
			myInstance = new DB_TableManager();

		return myInstance;
	}

}

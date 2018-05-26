package database.table.customerMeasurements.shirtMeasurements;

import java.sql.ResultSet;
import java.util.HashMap;

import database.table.DBTable;
import form.jfx.main.CustomerDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBTable_ShirtMeasurements extends DBTable
{

	private HashMap<Integer, ObservableList<ShirtMeasurement>> myHashMap_ShirtMeasurements;

	public DBTable_ShirtMeasurements(String name)
	{
		super(name);
	}

	protected void Init()
	{
		AddAttribute("MeasurementID int primary key not null");
		AddAttribute("CustomerID int not null");
		AddAttribute("Height varchar(255)");
		AddAttribute("Chest varchar(255)");
		AddAttribute("Front varchar(255)");
		AddAttribute("Shoulder varchar(255)");
		AddAttribute("Shai varchar(255)");
		AddAttribute("Collar varchar(255)");
		AddAttribute("Cup varchar(255)");
		AddAttribute("Status varchar(255)");

		myHashMap_ShirtMeasurements = new HashMap<Integer, ObservableList<ShirtMeasurement>>();
	}

	public boolean LoadAllRecords()
	{
		try
		{
			ResultSet rs = GetRecords(null, 0, 0);
			ObservableList<ShirtMeasurement> allRecords = GetAllMeasurementRecordsFromResultSet(rs);
			for (ShirtMeasurement detail : allRecords)
			{
				Integer id = detail.GetCustomerID().get();
				if (!myHashMap_ShirtMeasurements.containsKey(id))
				{
					// create new list
					ObservableList<ShirtMeasurement> records = FXCollections.observableArrayList();
					records.add(detail);
					myHashMap_ShirtMeasurements.put(id, records);
				}
				else
				{
					ObservableList<ShirtMeasurement> records = myHashMap_ShirtMeasurements.get(id);
					records.add(detail);
				}
			}
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public ObservableList<ShirtMeasurement> GetAllMeasurementRecordsFromResultSet(ResultSet rs)
	{
		try
		{
			ObservableList<ShirtMeasurement> list = FXCollections.observableArrayList();
			while (rs != null && rs.next())
			{
				ShirtMeasurement sm = new ShirtMeasurement();
				sm.SetCustomerID(rs.getInt("CustomerID"));
				sm.SetMeasurementID(rs.getInt("MeasurementID"));
				sm.SetHeight(rs.getString("Height"));
				sm.SetChest(rs.getString("Chest"));
				sm.SetCup(rs.getString("Cup"));
				sm.SetFront(rs.getString("Front"));
				sm.SetShai(rs.getString("Shai"));
				sm.SetShoulder(rs.getString("Shoulder"));
				sm.SetCollar(rs.getString("Collar"));
				sm.SetStatus(rs.getString("Status"));
				list.add(sm);
			}

			return list;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public boolean UpdateRecord(ShirtMeasurement measurementToUpdate)
	{
		String height = measurementToUpdate.GetHeight().get();
		String chest = measurementToUpdate.GetChest().get();
		String front = measurementToUpdate.GetFront().get();
		String shoulder = measurementToUpdate.GetShoulder().get();
		String shai = measurementToUpdate.GetShai().get();
		String collar = measurementToUpdate.GetCollar().get();
		String cup = measurementToUpdate.GetCup().get();

		String condition = "MeasurementID = " + measurementToUpdate.GetMeasurementID().get();
		boolean success = super.UpdateAttribute("Height", height, condition);
		success |= super.UpdateAttribute("Chest", chest, condition);
		success |= super.UpdateAttribute("Front", front, condition);
		success |= super.UpdateAttribute("Shoulder", shoulder, condition);
		success |= super.UpdateAttribute("Shai", shai, condition);
		success |= super.UpdateAttribute("Collar", collar, condition);
		success |= super.UpdateAttribute("Cup", cup, condition);

		return success;
	}

	public boolean Insert(ShirtMeasurement sm)
	{
		int measurementID = sm.GetMeasurementID().get();
		int customerID = sm.GetCustomerID().get();
		String height = sm.GetHeight().get();
		String chest = sm.GetChest().get();
		String front = sm.GetFront().get();
		String shoulder = sm.GetShoulder().get();
		String shai = sm.GetShai().get();
		String collar = sm.GetCollar().get();
		String cup = sm.GetCup().get();
		String status = sm.GetStatus().get();

		return Insert(measurementID, customerID, height, chest, front, shoulder, shai, collar, cup, status);
	}

	public boolean Insert(int measurementID, int customerID, String height, String chest, String front, String shoulder,
			String shai, String collar, String cup, String status)
	{
		boolean success = super.Insert(
				GetInsertionQuery(measurementID, customerID, height, chest, front, shoulder, shai, collar, cup, status));
		assert success;

		ResultSet rs = super.ExecuteQuery("SELECT * FROM ShirtMeasurements ORDER BY MeasurementId DESC LIMIT 1;");
		ObservableList<ShirtMeasurement> measurementDetail = GetAllMeasurementRecordsFromResultSet(rs);
		ObservableList<ShirtMeasurement> records = myHashMap_ShirtMeasurements.get(customerID);
		if (records == null)
		{
			records = FXCollections.observableArrayList();
			myHashMap_ShirtMeasurements.put(customerID, records);
		}
		records.add(measurementDetail.get(0));

		return success;
	}

	public boolean DeleteRecord(ShirtMeasurement measurementToDelete)
	{
		if (measurementToDelete == null)
			return false;

		Integer customerID = measurementToDelete.GetCustomerID().get();

		String condition = "MeasurementID = " + measurementToDelete.GetMeasurementID().get();
		boolean success = super.DeleteRecord(condition);
		assert success;

		ObservableList<ShirtMeasurement> records = myHashMap_ShirtMeasurements.get(customerID);
		records.remove(measurementToDelete);
		return success;
	}

	public ObservableList<ShirtMeasurement> GetMeasurementDetailsList(CustomerDetail customerDetail)
	{
		int id = customerDetail.GetId().get();
		return myHashMap_ShirtMeasurements.get(id);
	}

	private String GetInsertionQuery(int measurementID, int customerID, String height, String chest, String front,
			String shoulder, String shai, String collar, String cup, String status)
	{
		height = "'" + height + "'";
		chest = "'" + chest + "'";
		front = "'" + front + "'";
		shoulder = "'" + shoulder + "'";
		shai = "'" + shai + "'";
		collar = "'" + collar + "'";
		cup = "'" + cup + "'";
		status = "'" + status + "'";

		String sql = "insert into " + myName
				+ " (MeasurementID, CustomerID, Height, Chest, Front, Shoulder, Shai, Collar, Cup, Status) values ( "
				+ measurementID + ", " + customerID + ", " + height + ", " + chest + ", " + front + ", " + shoulder
				+ ", " + shai + ", " + collar + ", " + cup + ", " + status + " );";

		return sql;
	}

}

package database.table.customerMeasurements.pantMeasurements;

import java.sql.ResultSet;
import java.util.HashMap;

import database.table.DBTable;
import database.table.customerMeasurements.shirtMeasurements.ShirtMeasurement;
import form.jfx.main.CustomerDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBTable_PantMeasurements extends DBTable
{

	private HashMap<Integer, ObservableList<PantMeasurement>> myHashMap_PantMeasurements;

	public DBTable_PantMeasurements(String name)
	{
		super(name);
	}

	protected void Init()
	{
		AddAttribute("MeasurementID int primary key not null");
		AddAttribute("CustomerID int not null");
		AddAttribute("Height varchar(255)");
		AddAttribute("Waist varchar(255)");
		AddAttribute("Seat varchar(255)");
		AddAttribute("Thigh varchar(255)");
		AddAttribute("Knee varchar(255)");
		AddAttribute("Bottom varchar(255)");
		AddAttribute("Status varchar(255)");

		myHashMap_PantMeasurements = new HashMap<Integer, ObservableList<PantMeasurement>>();
	}

	public boolean LoadAllRecords()
	{
		try
		{
			ResultSet rs = GetRecords(null, 0, 0);
			ObservableList<PantMeasurement> allRecords = GetAllMeasurementRecordsFromResultSet(rs);
			for (PantMeasurement detail : allRecords)
			{
				Integer id = detail.GetCustomerID().get();
				if (!myHashMap_PantMeasurements.containsKey(id))
				{
					// create new list
					ObservableList<PantMeasurement> records = FXCollections.observableArrayList();
					records.add(detail);
					myHashMap_PantMeasurements.put(id, records);
				}
				else
				{
					ObservableList<PantMeasurement> records = myHashMap_PantMeasurements.get(id);
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

	public ObservableList<PantMeasurement> GetAllMeasurementRecordsFromResultSet(ResultSet rs)
	{
		try
		{
			ObservableList<PantMeasurement> list = FXCollections.observableArrayList();
			while (rs != null && rs.next())
			{
				PantMeasurement sm = new PantMeasurement();
				sm.SetCustomerID(rs.getInt("CustomerID"));
				sm.SetMeasurementID(rs.getInt("MeasurementID"));
				sm.SetHeight(rs.getString("Height"));
				sm.SetWaist(rs.getString("Waist"));
				sm.SetSeat(rs.getString("Seat"));
				sm.SetThigh(rs.getString("Thigh"));
				sm.SetKnee(rs.getString("Knee"));
				sm.SetBottom(rs.getString("Bottom"));
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

	public boolean UpdateRecord(PantMeasurement measurementToUpdate)
	{
		String height = measurementToUpdate.GetHeight().get();
		String waist = measurementToUpdate.GetWaist().get();
		String seat = measurementToUpdate.GetSeat().get();
		String thigh = measurementToUpdate.GetThigh().get();
		String knee = measurementToUpdate.GetKnee().get();
		String bottom = measurementToUpdate.GetBottom().get();

		String condition = "MeasurementID = " + measurementToUpdate.GetMeasurementID().get();
		boolean success = super.UpdateAttribute("Height", height, condition);
		success |= super.UpdateAttribute("Waist", waist, condition);
		success |= super.UpdateAttribute("Seat", seat, condition);
		success |= super.UpdateAttribute("Thigh", thigh, condition);
		success |= super.UpdateAttribute("Knee", knee, condition);
		success |= super.UpdateAttribute("Bottom", bottom, condition);

		return success;
	}

	public boolean Insert(PantMeasurement pm)
	{
		int measurementID = pm.GetMeasurementID().get();
		int customerID = pm.GetCustomerID().get();
		String height = pm.GetHeight().get();
		String waist = pm.GetWaist().get();
		String seat = pm.GetSeat().get();
		String thigh = pm.GetThigh().get();
		String knee = pm.GetKnee().get();
		String bottom = pm.GetBottom().get();
		String status = pm.GetStatus().get();

		return Insert(measurementID, customerID, height, waist, seat, thigh, knee, bottom, status);
	}

	public boolean Insert(int measurementID, int customerID, String height, String waist, String seat, String thigh,
			String knee, String bottom, String status)
	{
		boolean success = super.Insert(
				GetInsertionQuery(measurementID, customerID, height, waist, seat, thigh, knee, bottom, status));
		assert success;

		ResultSet rs = super.ExecuteQuery("SELECT * FROM PantMeasurements ORDER BY MeasurementId DESC LIMIT 1;");
		ObservableList<PantMeasurement> measurementDetail = GetAllMeasurementRecordsFromResultSet(rs);
		ObservableList<PantMeasurement> records = myHashMap_PantMeasurements.get(customerID);
		if (records == null)
		{
			records = FXCollections.observableArrayList();
			myHashMap_PantMeasurements.put(customerID, records);
		}
		records.add(measurementDetail.get(0));

		return success;
	}

	public boolean DeleteRecord(PantMeasurement measurementToDelete)
	{
		if (measurementToDelete == null)
			return false;

		Integer customerID = measurementToDelete.GetCustomerID().get();

		String condition = "MeasurementID = " + measurementToDelete.GetMeasurementID().get();
		boolean success = super.DeleteRecord(condition);
		assert success;

		ObservableList<PantMeasurement> records = myHashMap_PantMeasurements.get(customerID);
		records.remove(measurementToDelete);
		return success;
	}

	public ObservableList<PantMeasurement> GetMeasurementDetailsList(CustomerDetail customerDetail)
	{
		int id = customerDetail.GetId().get();
		return myHashMap_PantMeasurements.get(id);
	}

	private String GetInsertionQuery(int measurementID, int customerID, String height, String waist, String seat,
			String thigh, String knee, String bottom, String status)
	{
		height = "'" + height + "'";
		waist = "'" + waist + "'";
		seat = "'" + seat + "'";
		thigh = "'" + thigh + "'";
		knee = "'" + knee + "'";
		bottom = "'" + bottom + "'";
		status = "'" + status + "'";

		String sql = "insert into " + myName
				+ " (MeasurementID, CustomerID, Height, Waist, Seat, Thigh, Knee, Bottom,Status) values ( " + measurementID
				+ ", " + customerID + ", " + height + ", " + waist + ", " + seat + ", " + thigh + ", " + knee + ", "
				+ bottom + "," + status + " );";

		return sql;
	}

}

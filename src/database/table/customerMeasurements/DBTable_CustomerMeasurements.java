package database.table.customerMeasurements;

import java.sql.ResultSet;
import java.util.HashMap;

import database.table.DBTable;
import form.jfx.main.CustomerDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBTable_CustomerMeasurements extends DBTable
{
	private HashMap<Integer, ObservableList<MeasurementDetail>> myHashMap_CustomerMeasurements;

	public DBTable_CustomerMeasurements(String name)
	{
		super(name);
	}

	protected void Init()
	{
		AddAttribute("MeasurementID int primary key auto_increment not null");
		AddAttribute("CustomerID int not null");
		AddAttribute("Item varchar(255)");
		AddAttribute("Type varchar(255)");
		AddAttribute("SubType varchar(255)");
		AddAttribute("Measurement varchar(255)");
		AddAttribute("Amount int");
		AddAttribute("Quantity int");
		AddAttribute("Status varchar(255) default 'Pending'");
		AddAttribute("Date date not null");

		myHashMap_CustomerMeasurements = new HashMap<Integer, ObservableList<MeasurementDetail>>();
	}

	public boolean LoadAllRecords()
	{
		try
		{
			ResultSet rs = GetRecords(null, 0, 0);
			ObservableList<MeasurementDetail> allRecords = GetAllMeasurementRecordsFromResultSet(rs);
			for (MeasurementDetail detail : allRecords)
			{
				Integer id = detail.GetCustomerId().get();
				if (!myHashMap_CustomerMeasurements.containsKey(id))
				{
					// create new list
					ObservableList<MeasurementDetail> records = FXCollections.observableArrayList();
					records.add(detail);
					myHashMap_CustomerMeasurements.put(id, records);
				}
				else
				{
					ObservableList<MeasurementDetail> records = myHashMap_CustomerMeasurements.get(id);
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

	public ObservableList<MeasurementDetail> GetAllMeasurementRecordsFromResultSet(ResultSet rs)
	{
		try
		{
			ObservableList<MeasurementDetail> customer = FXCollections.observableArrayList();
			while (rs != null && rs.next())
			{
				MeasurementDetail md = new MeasurementDetail();
				md.SetCustomerId(rs.getInt("CustomerID"));
				md.SetMeasurementId(rs.getInt("MeasurementID"));
				md.SetSelectItem(rs.getString("Item"));
				md.SetSelectType(rs.getString("Type"));
				md.SetSelectSubType(rs.getString("SubType"));
				md.SetQuantity(rs.getInt("Quantity"));
				md.SetPricePerItem(rs.getInt("Amount"));
				md.SetMeasurement(rs.getString("Measurement"));
				md.SetStatus(rs.getString("Status"));
				md.SetDate(rs.getDate("Date"));
				customer.add(md);
			}

			return customer;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public boolean UpdateRecord(MeasurementDetail measurementtoupdate)
	{
		String item = measurementtoupdate.GetSelectItem().get();
		String type = measurementtoupdate.GetSelectType().get();
		String subtype = measurementtoupdate.GetSelectSubType().get();
		int quantity = measurementtoupdate.GetQuantity().get();
		int priceperItem = measurementtoupdate.GetPricePerItem().get();
		String measurement = measurementtoupdate.GetMeasurement().get();
		String status = measurementtoupdate.GetStatus().get();

		// int totalamount = measurementtoupdate.getTotalPrice().get();
		String condition = "MeasurementID = " + measurementtoupdate.GetMeasurementId().get();
		boolean success = super.UpdateAttribute("Item", item, condition);
		success |= super.UpdateAttribute("Type", type, condition);
		success |= super.UpdateAttribute("SubType", subtype, condition);
		success |= super.UpdateAttribute("Measurement", measurement, condition);
		success |= super.UpdateAttribute("Amount", priceperItem, condition);
		success |= super.UpdateAttribute("Quantity", quantity, condition);
		success |= super.UpdateAttribute("Status", status, condition);

		return success;
	}

	public boolean Insert(int customerId, String item, String type, String subType, String measurement, int amount,
			int quantity)
	{
		boolean success = super.Insert(
				GetInsertionQuery(customerId, item, type, subType, measurement, amount, quantity));
		assert success;

		ResultSet rs = super.ExecuteQuery("SELECT * FROM CustomerMeasurements ORDER BY MeasurementId DESC LIMIT 1;");
		ObservableList<MeasurementDetail> measurementDetail = GetAllMeasurementRecordsFromResultSet(rs);
		ObservableList<MeasurementDetail> records = myHashMap_CustomerMeasurements.get(customerId);
		if (records == null)
		{
			records = FXCollections.observableArrayList();
			myHashMap_CustomerMeasurements.put(customerId, records);
		}
		records.add(measurementDetail.get(0));

		return success;
	}
public int GetLastMeasurementId()
{
	ResultSet rs = super.ExecuteQuery("SELECT * FROM CustomerMeasurements ORDER BY MeasurementId DESC LIMIT 1;");
	ObservableList<MeasurementDetail> measurementDetail = GetAllMeasurementRecordsFromResultSet(rs);
	MeasurementDetail md = measurementDetail.get(0);
	return md.GetMeasurementId().get();
}
	public boolean DeleteRecord(CustomerDetail customerDetail)
	{
		if (customerDetail == null)
			return false;

		int customerId = customerDetail.GetId().get();
		String condition = "MeasurementID = " + customerId;

		boolean success = super.DeleteRecord(condition);
		assert success;

		ObservableList<MeasurementDetail> records = myHashMap_CustomerMeasurements.get(customerId);
		if (records == null)
			return false;

		records.clear();
		myHashMap_CustomerMeasurements.remove(customerId);
		return success;
	}

	public boolean DeleteRecord(MeasurementDetail measurementToDelete)
	{
		if (measurementToDelete == null)
			return false;

		Integer customerId = measurementToDelete.GetCustomerId().get();

		String condition = "MeasurementID = " + measurementToDelete.GetMeasurementId().get();
		boolean success = super.DeleteRecord(condition);
		assert success;

		ObservableList<MeasurementDetail> records = myHashMap_CustomerMeasurements.get(customerId);
		records.remove(measurementToDelete);
		return success;
	}

	public ObservableList<MeasurementDetail> GetMeasurementDetailsList(CustomerDetail customerDetail)
	{
		int id = customerDetail.GetId().get();
		return myHashMap_CustomerMeasurements.get(id);
	}

	private String GetInsertionQuery(int customerId, String item, String type, String subType, String measurement,
			int pricePerItem, int quantity)
	{
		item = "'" + item + "'";
		type = "'" + type + "'";
		subType = "'" + subType + "'";
		measurement = "'" + measurement + "'";

		String sql = "insert into " + myName
				+ " (CustomerId, Item, Type, SubType, Measurement, Amount, Quantity, Date) values ( " + customerId
				+ ", " + item + ", " + type + ", " + subType + ", " + measurement + ", " + pricePerItem + ", "
				+ quantity + ", " + "curdate()" + " );";

		return sql;
	}

}

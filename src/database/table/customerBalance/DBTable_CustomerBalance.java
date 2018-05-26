package database.table.customerBalance;

import java.sql.ResultSet;

import database.table.DBTable;
import form.jfx.main.CustomerDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBTable_CustomerBalance extends DBTable
{

	ObservableList<BalanceDetails> myList_BalanceDetails;

	public DBTable_CustomerBalance(String name)
	{
		super(name);
	}

	protected void Init()
	{
		AddAttribute("CustomerId int not null");
		AddAttribute("PendingAmount int");
		AddAttribute("TotalAmount int");
	}

	public boolean LoadAllRecords()
	{
		ResultSet rs = GetRecords(null, 0, 0);
		myList_BalanceDetails = GetBalanceDetailsFromResultSet(rs);
		return true;
	}

	public boolean UpdateAttribute(BalanceDetails detail)
	{
		String condition = "CustomerId = " + detail.GetCustomerId();
		boolean success = super.UpdateAttribute("PendingAmount", detail.GetPendingAmount(), condition);
		success |= super.UpdateAttribute("TotalAmount", detail.GetTotalAmount(), condition);
		assert success;

		return success;
	}

	private ObservableList<BalanceDetails> GetBalanceDetailsFromResultSet(ResultSet rs)
	{
		ObservableList<BalanceDetails> records = FXCollections.observableArrayList();
		int pendingAmount, totalAmount, customerId;
		try
		{
			while (rs.next())
			{
				pendingAmount = rs.getInt("PendingAmount");
				totalAmount = rs.getInt("TotalAmount");
				customerId = rs.getInt("CustomerId");
				BalanceDetails bal = new BalanceDetails(customerId, pendingAmount, totalAmount);
				records.add(bal);
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		return records;
	}

	public boolean DeleteRecord(CustomerDetail customerDetail)
	{
		return DeleteRecord(GetBalanceDetails(customerDetail));
	}

	public boolean DeleteRecord(BalanceDetails balanceDetail)
	{
		if (balanceDetail == null)
			return false;

		String condition = "CustomerId = " + balanceDetail.GetCustomerId();
		boolean success = super.DeleteRecord(condition);
		assert success;

		myList_BalanceDetails.remove(balanceDetail);
		return success;
	}

	public boolean Insert(int customerId, int pendingAmount, int totalAmount)
	{
		boolean success = super.Insert(GetInsertionString(customerId, pendingAmount, totalAmount));
		assert success;

		ResultSet rs = super.ExecuteQuery("SELECT * FROM CustomerBalance ORDER BY CustomerId DESC LIMIT 1;");
		ObservableList<BalanceDetails> recentlyAddedBalance = GetBalanceDetailsFromResultSet(rs);
		myList_BalanceDetails.add(recentlyAddedBalance.get(0));
		return success;
	}

	public BalanceDetails GetBalanceDetails(CustomerDetail customerDetail)
	{
		for (BalanceDetails currentbalance : myList_BalanceDetails)
			if (customerDetail.GetId().get() == currentbalance.GetCustomerId())
				return currentbalance;

		return null;
	}

	private String GetInsertionString(int customerId, int pendingAmount, int totalAmount)
	{
		String sql = "Insert into " + myName + " (CustomerId, PendingAmount, TotalAmount) values ( " + customerId + ", "
				+ pendingAmount + ", " + totalAmount + " );";
		return sql;
	}

}

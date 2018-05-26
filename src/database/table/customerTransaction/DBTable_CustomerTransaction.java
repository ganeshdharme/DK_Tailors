package database.table.customerTransaction;

import java.sql.ResultSet;
import java.util.HashMap;

import database.table.DBTable;
import form.jfx.main.CustomerDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBTable_CustomerTransaction extends DBTable
{
	private HashMap<Integer, ObservableList<TransactionDetail>> myHashMap_CustomerTransactions;

	public DBTable_CustomerTransaction(String name)
	{
		super(name);
	}

	protected void Init()
	{
		AddAttribute("TransactionID int primary key auto_increment not null");
		AddAttribute("CustomerID int not null");
		AddAttribute("Amount int not null");
		AddAttribute("Status varchar(255) not null");
		AddAttribute("Date date not null");

		myHashMap_CustomerTransactions = new HashMap<Integer, ObservableList<TransactionDetail>>();
	}

	public boolean LoadAllRecords()
	{
		try
		{
			ResultSet rs = GetRecords(null, 0, 0);
			ObservableList<TransactionDetail> allRecords = GetAllTransactionRecordsFromResultSet(rs);
			for (TransactionDetail detail : allRecords)
			{
				Integer id = detail.GetCustomerID().get();
				if (!myHashMap_CustomerTransactions.containsKey(id))
				{
					// create new list
					ObservableList<TransactionDetail> records = FXCollections.observableArrayList();
					records.add(detail);
					myHashMap_CustomerTransactions.put(id, records);
				}
				else
				{
					ObservableList<TransactionDetail> records = myHashMap_CustomerTransactions.get(id);
					records.add(detail);
				}
			}
			System.out.println("myHashMap_CustomerTransactions.size(): " + myHashMap_CustomerTransactions.size());
			return true;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public ObservableList<TransactionDetail> GetAllTransactionRecordsFromResultSet(ResultSet rs)
	{
		try
		{
			ObservableList<TransactionDetail> transactions = FXCollections.observableArrayList();
			while (rs != null && rs.next())
			{
				TransactionDetail td = new TransactionDetail();
				td.SetCustomerID(rs.getInt("CustomerID"));
				td.SetTransactionID(rs.getInt("TransactionID"));
				td.SetDate(rs.getDate("Date"));
				td.SetAmount(rs.getInt("Amount"));
				td.SetStatus(rs.getString("Status"));
				transactions.add(td);
			}

			return transactions;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public boolean UpdateRecord(TransactionDetail transactionToUpdate)
	{
		assert false : "We should not be using this function!";
		return false;
	}

	public boolean Insert(TransactionDetail td)
	{
		int customerId = td.GetCustomerID().get();
		int amount = td.GetAmount().get();
		String status = td.GetStatus().get();
		return Insert(customerId, amount, status);
	}

	public boolean Insert(int customerId, int amount, String status)
	{
		boolean success = super.Insert(GetInsertionQuery(customerId, amount, status));
		assert success;

		ResultSet rs = super.ExecuteQuery("SELECT * FROM CustomerTransactions ORDER BY TransactionID DESC LIMIT 1;");
		ObservableList<TransactionDetail> TransactionDetail = GetAllTransactionRecordsFromResultSet(rs);
		ObservableList<TransactionDetail> records = myHashMap_CustomerTransactions.get(customerId);
		if (records == null)
		{
			records = FXCollections.observableArrayList();
			myHashMap_CustomerTransactions.put(customerId, records);
		}
		records.add(TransactionDetail.get(0));

		return success;
	}

	public boolean DeleteRecord(CustomerDetail customerDetail)
	{
		if (customerDetail == null)
			return false;

		int customerId = customerDetail.GetId().get();
		String condition = "TransactionID = " + customerId;

		boolean success = super.DeleteRecord(condition);
		assert success;

		ObservableList<TransactionDetail> records = myHashMap_CustomerTransactions.get(customerId);
		if (records == null)
			return false;

		records.clear();
		myHashMap_CustomerTransactions.remove(customerId);
		return success;
	}

	public boolean DeleteRecord(TransactionDetail transactionToDelete)
	{
		if (transactionToDelete == null)
			return false;

		Integer customerId = transactionToDelete.GetCustomerID().get();

		String condition = "TransactionID = " + transactionToDelete.GetTransactionID().get();
		boolean success = super.DeleteRecord(condition);
		assert success;

		ObservableList<TransactionDetail> records = myHashMap_CustomerTransactions.get(customerId);
		records.remove(transactionToDelete);
		return success;
	}

	public ObservableList<TransactionDetail> GetTransactionDetailsList(CustomerDetail customerDetail)
	{
		int id = customerDetail.GetId().get();
		return myHashMap_CustomerTransactions.get(id);
	}

	private String GetInsertionQuery(int customerId, int amount, String status)
	{
		status = "'" + status + "'";
		String sql = "insert into " + myName + " (CustomerId, Amount, Status, Date) values ( " + customerId + ", "
				+ amount + ", " + status + ", " + "curdate()" + " );";

		return sql;
	}

}

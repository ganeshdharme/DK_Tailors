package database.table.customerDetails;

import java.sql.ResultSet;

import database.table.DBTable;
import form.jfx.main.CustomerDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBTable_CustomerDetails extends DBTable
{
	private ObservableList<CustomerDetail> myList_CustomerDetails;

	public DBTable_CustomerDetails(String name)
	{
		super(name);
	}

	protected void Init()
	{
		AddAttribute("CustomerId int primary key auto_increment not null");
		AddAttribute("FirstName varchar(255)");
		AddAttribute("LastName varchar(255)");
		AddAttribute("MobNo bigint(11)");
		AddAttribute("Email varchar(255)");
		AddAttribute("Age int");
		AddAttribute("Gender varchar(255)");
		AddAttribute("Address varchar(255)");
	}

	public boolean Insert(String fname, String lname, long mobNo, String email, int age, String gender, String address)
	{
		boolean success = super.Insert(GetInsertionString(fname, lname, mobNo, email, age, gender, address));
		ResultSet rs = super.ExecuteQuery("SELECT * FROM customerDetails ORDER BY CustomerId DESC LIMIT 1;");
		ObservableList<CustomerDetail> customer = GetCustomerListFromResultSet(rs);
		myList_CustomerDetails.add(customer.get(0));
		return success;
	}

	public boolean DeleteRecord(CustomerDetail customertodelete)
	{
		String customerId = "" + customertodelete.GetId().get();
		String condition = "CustomerId = " + customerId;

		boolean success = super.DeleteRecord(condition);
		assert success;

		myList_CustomerDetails.remove(customertodelete);
		return success;
	}

	public boolean UpdateRecord(CustomerDetail customertoupdate)
	{
		String fname = customertoupdate.GetFirstName().get();
		String lname = customertoupdate.GetLastName().get();
		long mobNo = customertoupdate.GetMobileNo().get();
		String email = customertoupdate.GetEmail().get();
		int age = customertoupdate.GetAge().get();
		String gender = customertoupdate.GetGender().get();
		String address = customertoupdate.GetAddress().get();
		String condition = "CustomerId = " + customertoupdate.GetId().get();

		boolean success = super.UpdateAttribute("FirstName", fname, condition);
		assert success;

		success |= super.UpdateAttribute("LastName", lname, condition);
		success |= super.UpdateAttribute("MobNo", mobNo, condition);
		success |= super.UpdateAttribute("Email", email, condition);
		success |= super.UpdateAttribute("Age", age, condition);
		success |= super.UpdateAttribute("Gender", gender, condition);
		success |= super.UpdateAttribute("Address", address, condition);
		return success;
	}

	public boolean LoadAllRecords()
	{
		try
		{
			ResultSet rs = GetRecords(null, 0, 0);
			myList_CustomerDetails = GetCustomerListFromResultSet(rs);
			return (myList_CustomerDetails != null);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public ObservableList<CustomerDetail> GetCustomerListFromResultSet(ResultSet rs)
	{
		try
		{
			ObservableList<CustomerDetail> list_customer = FXCollections.observableArrayList();
			while (rs.next())
			{
				CustomerDetail cs = new CustomerDetail();
				cs.SetId(rs.getInt("CustomerId"));
				cs.SetFirstName(rs.getString("FirstName"));
				cs.SetLastName(rs.getString("LastName"));
				cs.SetMobileNo(rs.getLong("MobNo"));
				cs.SetEmail(rs.getString("Email"));
				cs.SetAge(rs.getInt("Age"));
				cs.SetGender(rs.getString("Gender"));
				cs.SetAddress(rs.getString("Address"));
				list_customer.add(cs);
			}

			return list_customer;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public ObservableList<CustomerDetail> GetCustomerDetailsWithKey(String query)
	{
		ObservableList<CustomerDetail> customerList = FXCollections.observableArrayList();
		for (CustomerDetail cd : myList_CustomerDetails)
		{
			String mobileno = "" + cd.GetMobileNo().get();
			boolean stringMatches = cd.GetFirstName().get().toLowerCase().startsWith(query.toLowerCase())
					|| cd.GetLastName().get().toLowerCase().startsWith(query.toLowerCase())
					|| mobileno.startsWith(query);
			if (stringMatches)
				customerList.add(cd);
		}
		return customerList;
	}

	public CustomerDetail GetCustomerDetailWithId(int id)
	{
		for (CustomerDetail customer : myList_CustomerDetails)
		{
			int customerID = customer.GetId().get();
			if (customerID == id)
				return customer;
		}

		return null;
	}

	private String GetInsertionString(String fname, String lname, long mobNo, String email, int age, String gender,
			String address)
	{
		fname = "'" + fname + "'";
		lname = "'" + lname + "'";
		address = "'" + address + "'";
		email = "'" + email + "'";
		gender = "'" + gender + "'";

		String sql = "insert into " + myName + " (FirstName, LastName, MobNo, Email, Age, Gender, Address) values ( "
				+ fname + ", " + lname + ", " + mobNo + ", " + email + ", " + age + ", " + gender + ", " + address
				+ " );";

		return sql;
	}

	public ObservableList<CustomerDetail> GetCustomerDetailsList()
	{
		return myList_CustomerDetails;
	}

	public CustomerDetail GetLastAddedCustomer()
	{
		int size = myList_CustomerDetails.size();
		return myList_CustomerDetails.get(size - 1);
	}

}

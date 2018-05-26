package database.table.customerTransaction;

import java.sql.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TransactionDetail
{
	private IntegerProperty myTransactionID;
	private IntegerProperty myCustomerID;
	private IntegerProperty myAmount;
	private Date myDate;
	private StringProperty myDateInString;
	private StringProperty myStatus;

	public TransactionDetail()
	{
		myTransactionID = new SimpleIntegerProperty();
		myCustomerID = new SimpleIntegerProperty();
		myAmount = new SimpleIntegerProperty();
		myDateInString = new SimpleStringProperty();
		myStatus = new SimpleStringProperty();
	}

	public IntegerProperty GetTransactionID()
	{
		return myTransactionID;
	}

	public void SetTransactionID(int id)
	{
		myTransactionID.set(id);
	}

	public IntegerProperty GetCustomerID()
	{
		return myCustomerID;
	}

	public void SetCustomerID(int id)
	{
		myCustomerID.set(id);
	}

	public IntegerProperty GetAmount()
	{
		return myAmount;
	}

	public void SetAmount(int amount)
	{
		myAmount.set(amount);
	}

	public StringProperty GetDateInString()
	{
		return myDateInString;
	}

	public Date GetDate()
	{
		return myDate;
	}

	public void SetDate(Date date)
	{
		myDate = date;
		myDateInString.set(myDate.toString());
	}

	public StringProperty GetStatus()
	{
		return myStatus;
	}

	public void SetStatus(String status)
	{
		myStatus.set(status);
	}

}

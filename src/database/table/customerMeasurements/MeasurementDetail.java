package database.table.customerMeasurements;

import java.sql.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MeasurementDetail
{
	private IntegerProperty myMeasurementId;
	private IntegerProperty myCustomerId;
	private StringProperty mySelectItem;
	private StringProperty mySelectType;
	private StringProperty mySelectSubType;
	private IntegerProperty myQuantity;
	private IntegerProperty myPricePerItem;
	private StringProperty myMeasurement;
	private IntegerProperty myTotalPrice;
	private StringProperty myStatus;
	private Date myDate;

	public MeasurementDetail()
	{
		mySelectItem = new SimpleStringProperty();
		mySelectType = new SimpleStringProperty();
		mySelectSubType = new SimpleStringProperty();
		myQuantity = new SimpleIntegerProperty();
		myPricePerItem = new SimpleIntegerProperty();
		myMeasurement = new SimpleStringProperty();
		myTotalPrice = new SimpleIntegerProperty();
		myCustomerId = new SimpleIntegerProperty();
		myMeasurementId = new SimpleIntegerProperty();
		myStatus = new SimpleStringProperty();
	}

	public StringProperty GetSelectItem()
	{
		return mySelectItem;
	}

	public void SetSelectItem(String mySelectItem)
	{
		this.mySelectItem.set(mySelectItem);
	}

	public StringProperty GetSelectType()
	{
		return mySelectType;
	}

	public void SetSelectType(String mySelectType)
	{
		this.mySelectType.set(mySelectType);
	}

	public StringProperty GetSelectSubType()
	{
		return mySelectSubType;
	}

	public void SetSelectSubType(String mySelectSubType)
	{
		this.mySelectSubType.set(mySelectSubType);
	}

	public IntegerProperty GetMeasurementId()
	{
		return myMeasurementId;
	}

	public IntegerProperty GetCustomerId()
	{
		return myCustomerId;
	}

	public IntegerProperty GetPricePerItem()
	{
		return myPricePerItem;
	}

	public void SetPricePerItem(int myPricePerItem)
	{
		this.myPricePerItem.set(myPricePerItem);
	}

	public StringProperty GetMeasurement()
	{
		return myMeasurement;
	}

	public IntegerProperty GetQuantity()
	{
		return myQuantity;
	}

	public void SetQuantity(int myQuantity)
	{
		this.myQuantity.set(myQuantity);
	}

	public IntegerProperty GetTotalPrice()
	{
		int myTotalPrice = GetQuantity().get() * GetPricePerItem().get();
		this.myTotalPrice.set(myTotalPrice);
		return this.myTotalPrice;
	}

	public void SetMeasurement(String myMeasurement)
	{
		this.myMeasurement.set(myMeasurement);
	}

	public void SetMeasurementId(int id)
	{
		myMeasurementId.set(id);
	}

	public void SetCustomerId(int id)
	{
		myCustomerId.set(id);
	}

	public void SetDate(Date date)
	{
		myDate = date;
	}

	public Date GetDate()
	{
		return myDate;
	}

	public void SetStatus(String status)
	{
		myStatus.set(status);
	}

	public StringProperty GetStatus()
	{
		return myStatus;
	}

}

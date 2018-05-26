package database.table.customerMeasurements.shirtMeasurements;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ShirtMeasurement
{
	private IntegerProperty myMeasurementID;
	private IntegerProperty myCustomerID;
	private StringProperty myHeight;
	private StringProperty myChest;
	private StringProperty myFront;
	private StringProperty myShoulder;
	private StringProperty myShai;
	private StringProperty myCollar;
	private StringProperty myCup;
	private StringProperty myStatus;

	public ShirtMeasurement()
	{
		myMeasurementID = new SimpleIntegerProperty();
		myCustomerID = new SimpleIntegerProperty();
		myHeight = new SimpleStringProperty();
		myChest = new SimpleStringProperty();
		myFront = new SimpleStringProperty();
		myShoulder = new SimpleStringProperty();
		myShai = new SimpleStringProperty();
		myCollar = new SimpleStringProperty();
		myCup = new SimpleStringProperty();
		myStatus = new SimpleStringProperty();
	}
	public IntegerProperty GetMeasurementID()
	{
		return myMeasurementID;
	}

	public void SetMeasurementID(int id)
	{
		myMeasurementID.set(id);
	}

	public IntegerProperty GetCustomerID()
	{
		return myCustomerID;
	}

	public void SetCustomerID(int id)
	{
		myCustomerID.set(id);
	}

	public StringProperty GetHeight()
	{
		return myHeight;
	}

	public void SetHeight(String height)
	{
		myHeight.set(height);
	}

	public StringProperty GetChest()
	{
		return myHeight;
	}

	public void SetChest(String chest)
	{
		myChest.set(chest);
	}

	public StringProperty GetFront()
	{
		return myFront;
	}

	public void SetFront(String front)
	{
		myFront.set(front);
	}

	public StringProperty GetShoulder()
	{
		return myShoulder;
	}

	public void SetShoulder(String shoulder)
	{
		myShoulder.set(shoulder);
	}

	public StringProperty GetShai()
	{
		return myShai;
	}

	public void SetShai(String shai)
	{
		myShai.set(shai);
	}

	public StringProperty GetCollar()
	{
		return myCollar;
	}

	public void SetCollar(String collar)
	{
		myCollar.set(collar);
	}

	public StringProperty GetCup()
	{
		return myCup;
	}

	public void SetCup(String cup)
	{
		myCup.set(cup);
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

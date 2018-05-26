package database.table.customerMeasurements.pantMeasurements;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PantMeasurement
{
	private IntegerProperty myMeasurementID;
	private IntegerProperty myCustomerID;
	private StringProperty myHeight;
	private StringProperty myWaist;
	private StringProperty myThigh;
	private StringProperty myKnee;
	private StringProperty mySeat;
	private StringProperty myBottom;
	private StringProperty myStatus;

	public PantMeasurement()
	{
		myMeasurementID = new SimpleIntegerProperty();
		myCustomerID = new SimpleIntegerProperty();
		myHeight = new SimpleStringProperty();
		myWaist = new SimpleStringProperty();
		myThigh = new SimpleStringProperty();
		myKnee = new SimpleStringProperty();
		mySeat = new SimpleStringProperty();
		myBottom  = new SimpleStringProperty();
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

	public StringProperty GetWaist()
	{
		return myHeight;
	}

	public void SetWaist(String Waist)
	{
		myWaist.set(Waist);
	}

	public StringProperty GetThigh()
	{
		return myThigh;
	}

	public void SetThigh(String Thigh)
	{
		myThigh.set(Thigh);
	}

	public StringProperty GetKnee()
	{
		return myKnee;
	}

	public void SetKnee(String Knee)
	{
		myKnee.set(Knee);
	}

	public StringProperty GetSeat()
	{
		return mySeat;
	}

	public void SetSeat(String Seat)
	{
		mySeat.set(Seat);
	}

	public StringProperty GetBottom()
	{
		return myBottom;
	}

	public void SetBottom(String Bottom)
	{
		myBottom.set(Bottom);
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

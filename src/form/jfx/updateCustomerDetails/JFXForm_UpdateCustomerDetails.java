package form.jfx.updateCustomerDetails;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import database.table.DB_TableManager;
import database.table.DB_TableNames;
import database.table.customerDetails.DBTable_CustomerDetails;
import form.jfx.JFXForm;
import form.jfx.main.CustomerDetail;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;

public class JFXForm_UpdateCustomerDetails extends JFXForm implements Initializable
{

	private CustomerDetail myCustomerDetail;

	@FXML private JFXTextField myTextField_Firstname;

	@FXML private JFXTextField myTextField_Lastname;

	@FXML private JFXTextField myTextField_MobNo;

	@FXML private JFXTextField myTextField_Address;

	@FXML private JFXTextField myTextField_Email;

	@FXML private JFXTextField myTextField_Age;

	@FXML private JFXButton myButton_Update;

	@FXML private JFXRadioButton myRadioButton_Male;

	@FXML private JFXRadioButton myRadioButton_Female;

	@FXML private Label myLabel_CustomerName;

	public JFXForm_UpdateCustomerDetails()
	{
	}

	public JFXForm_UpdateCustomerDetails(String title, String path)
	{
		super(title, path);
	}

	@FXML
	void OnButtonClicked_Update(ActionEvent event)
	{
		if (ValidateCustomerEntry())
			UpdateCustomer();
		else
			ShowAlert("FAILED", "Updated Failed!");
	}

	@FXML
	private void UpdateFields()
	{
		try
		{
			myTextField_Firstname.setText(myCustomerDetail.GetFirstName().get());
			myTextField_Lastname.setText(myCustomerDetail.GetLastName().get());
			myTextField_MobNo.setText(Long.toString(myCustomerDetail.GetMobileNo().get()));
			myTextField_Age.setText(Integer.toString(myCustomerDetail.GetAge().get()));
			myTextField_Address.setText("" + myCustomerDetail.GetAddress().get());
			myTextField_Email.setText("" + myCustomerDetail.GetEmail().get());
			String Gender = "" + myCustomerDetail.GetGender().get();

			if (Gender.equals("Male"))
				myRadioButton_Male.setSelected(true);
			else
				myRadioButton_Female.setSelected(true);

			myLabel_CustomerName
					.setText(myCustomerDetail.GetFirstName().get() + " " + myCustomerDetail.GetLastName().get());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
	}

	private void UpdateCustomer()
	{
		// store customer details
		String fname = myTextField_Firstname.getText();
		String lname = myTextField_Lastname.getText();
		long mobNo = Long.parseLong(myTextField_MobNo.getText());
		String email = myTextField_Email.getText();
		int age = Integer.parseInt(myTextField_Age.getText());
		String gender;

		if (myRadioButton_Male.isSelected())
			gender = "Male";
		else
			gender = "Female";

		String address = myTextField_Address.getText();

		myCustomerDetail.SetFirstName(fname);
		myCustomerDetail.SetLastName(lname);
		myCustomerDetail.SetMobileNo(mobNo);
		myCustomerDetail.SetEmail(email);
		myCustomerDetail.SetAge(age);
		myCustomerDetail.SetGender(gender);
		myCustomerDetail.SetAddress(address);

		DBTable_CustomerDetails table = (DBTable_CustomerDetails) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_DETAILS);
		boolean success = table.UpdateRecord(myCustomerDetail);

		String message = "";
		if (success)
			message = "Customer Details Updated Successfully!";
		else
			message = "Failed to update Customer details!";

		ShowAlert("INFORMATION!", message);
	}

	private boolean ValidateCustomerEntry()
	{
		// validate if the fields are empty
		String fname = myTextField_Firstname.getText().trim();
		String lname = myTextField_Lastname.getText().trim();

		if (fname.isEmpty() || lname.isEmpty() || myTextField_MobNo.getText().trim().isEmpty())
		{
			ShowAlert("ERROR!", "Some of the necessary fields are empty!");
			return false;
		}

		// validate mobile number
		long mobNo;
		try
		{
			mobNo = Long.parseLong(myTextField_MobNo.getText().trim());
		}
		catch (NumberFormatException e)
		{
			ShowAlert("ERROR!", "Mobile number should be a numerical value!");
			return false;
		}

		if (String.valueOf(mobNo).length() != 10)
		{
			ShowAlert("ERROR!", "Mobile number should be 10 digit long!");
			return false;
		}

		// validate age
		try
		{
			Integer.parseInt(myTextField_Age.getText());
		}
		catch (Exception e)
		{
			ShowAlert("ERROR!", "Invalid Age!");
			return false;
		}

		// validate email address
		String email = myTextField_Email.getText().trim();
		if (!email.isEmpty())
		{
			if (!email.contains("@"))
			{
				ShowAlert("ERROR!", "Email Address is Invalid!");
				return false;
			}
			else
			{
				String subString = email.substring(email.indexOf("@"));
				if (!subString.contains("."))
				{
					ShowAlert("ERROR!", "Email Address is Invalid!");
					return false;
				}
			}
		}

		if (!(myRadioButton_Male.isSelected() || myRadioButton_Female.isSelected()))
		{
			ShowAlert("ERROR!", "Select Gender!");
			return false;
		}

		return true;
	}

	public void SetCustomer(CustomerDetail customerDetail)
	{
		myCustomerDetail = customerDetail;
		UpdateFields();
	}

	public CustomerDetail GetCustomer()
	{
		return myCustomerDetail;
	}

	private void ResetAll()
	{
		myTextField_Firstname.setText("");
		myTextField_Lastname.setText("");
		myTextField_MobNo.setText("");
		myTextField_Address.setText("");
		myTextField_Email.setText("");
		myTextField_Age.setText("");
		myRadioButton_Male.setSelected(false);
		myRadioButton_Female.setSelected(false);
	}

	public FormType GetType()
	{
		return FormType.UPDATE_CUSTOMER_DETAILS;
	}

	private void ShowAlert(String header, String message)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(header);
		alert.setHeaderText(message);
		alert.showAndWait();
	}

}

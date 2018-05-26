package form.jfx.updateCustomerMeasurements;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import database.table.DB_TableManager;
import database.table.DB_TableNames;
import database.table.customerMeasurements.DBTable_CustomerMeasurements;
import database.table.customerMeasurements.MeasurementDetail;
import form.jfx.JFXForm;
import form.jfx.main.CustomerDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class JFXForm_UpdateMeasurement extends JFXForm implements Initializable
{

	private MeasurementDetail myMeasurementDetail;
	private CustomerDetail myCustomerDetail;

	@FXML private Label myLabel_CustomerName;

	@FXML private ComboBox<String> myComboBox_Item;

	@FXML private ComboBox<String> myComboBox_Type;

	@FXML private JFXTextField myTextField_SubType;

	@FXML private JFXTextArea myTextArea_Measurement;

	@FXML private JFXTextField myTextField_Quantity;

	@FXML private JFXTextField myTextField_PricePerItem;

	@FXML private JFXButton myButton_UpdateMeasurement;

	private ObservableList<String> myList_Item;
	private ObservableList<String> myList_Type;

	public JFXForm_UpdateMeasurement()
	{
	}

	public JFXForm_UpdateMeasurement(String title, String path)
	{
		super(title, path);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		InitItems();
		InitTypes();
	}

	private void InitItems()
	{
		myList_Item = FXCollections.observableArrayList();

		AddItem("NO");
		AddItem("Naadi Chain Vijaar");
		AddItem("Naadi Button Vijaar");
		AddItem("Manela");
		AddItem("Nehru Shirt");
		AddItem("Tin Button Shirt");
		AddItem("Banel");
		AddItem("Plate Pant");
		AddItem("Narrow Pant");
		AddItem("Belt Vijaar");
		AddItem("Open");

		myComboBox_Item.setItems(myList_Item);
	}

	private void AddItem(String itemName)
	{
		myList_Item.add(itemName);
	}

	private void InitTypes()
	{
		myList_Type = FXCollections.observableArrayList();

		AddType("NO");
		AddType("Bagal Khisa");
		AddType("Bagal Khisa - 2");
		AddType("Aatun Khisa");
		AddType("Mobile Khisa");
		AddType("Bigger Plate");
		AddType("Mothe Cup");
		AddType("Patti Collar");
		AddType("Chirva Pant");
		AddType("Steel Button");
		AddType("Bottom Chain");

		myComboBox_Type.setItems(myList_Type);
	}

	private void AddType(String typeName)
	{
		myList_Type.add(typeName);
	}

	public void InitializeFields()
	{
		myTextField_SubType.setText(myMeasurementDetail.GetSelectSubType().get());
		myTextField_PricePerItem.setText("" + myMeasurementDetail.GetPricePerItem().get());
		myTextArea_Measurement.setText(myMeasurementDetail.GetMeasurement().get());
		myTextField_Quantity.setText("" + myMeasurementDetail.GetQuantity().get());
		myComboBox_Item.setValue(myMeasurementDetail.GetSelectItem().get());
		myComboBox_Type.setValue(myMeasurementDetail.GetSelectType().get());

		String fullname = myCustomerDetail.GetFirstName().get() + " " + myCustomerDetail.GetLastName().get();
		myLabel_CustomerName.setText(fullname);
	}

	private boolean ValidateMeasurements()
	{
		String item = myComboBox_Item.getValue();
		String type = myComboBox_Type.getValue();
		/*
		 * String subType = myTextField_SubType.getText().trim(); String
		 * measurement = myTextArea_Measurement.getText().trim();
		 */

		if ((item == null) || (type == null) /*
												 * || subType.isEmpty() ||
												 * measurement.isEmpty()
												 */)
		{
			ShowAlert("ERROR!", "Some fo the fields are empty!");
			return false;
		}

		try
		{
			if (!myTextField_Quantity.getText().trim().isEmpty())
				Integer.parseInt(myTextField_Quantity.getText().trim());
			if (!myTextField_PricePerItem.getText().trim().isEmpty())
				Integer.parseInt(myTextField_PricePerItem.getText().trim());
		}
		catch (NumberFormatException e)
		{
			ShowAlert("ERROR!", "Quantity and Amount should be Integers!");
			return false;
		}

		return true;
	}

	private void ResetMeasurementFields()
	{
		myComboBox_Item.getSelectionModel().clearSelection();
		myComboBox_Type.getSelectionModel().clearSelection();
		myTextField_SubType.clear();
		myTextArea_Measurement.clear();
		myTextField_Quantity.clear();
		myTextField_PricePerItem.clear();
	}

	private boolean UpdateMeasurementInDatabase()
	{
		String item = myComboBox_Item.getValue();
		String type = myComboBox_Type.getValue();
		String subType = myTextField_SubType.getText();
		String measurement = myTextArea_Measurement.getText();
		String str_amount = myTextField_PricePerItem.getText();
		String str_quantity = myTextField_Quantity.getText();
		int amount = Integer.parseInt(str_amount);
		int quantity = Integer.parseInt(str_quantity);

		myMeasurementDetail.SetSelectItem(item);
		myMeasurementDetail.SetSelectType(type);
		myMeasurementDetail.SetSelectSubType(subType);
		myMeasurementDetail.SetMeasurement(measurement);
		myMeasurementDetail.SetQuantity(quantity);
		myMeasurementDetail.SetPricePerItem(amount);

		// update into database
		DBTable_CustomerMeasurements cm_table = (DBTable_CustomerMeasurements) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_MEASUREMENTS);
		boolean success = cm_table.UpdateRecord(myMeasurementDetail);
		assert success;

		/*
		 * DBTable_CustomerBalance cb_table = (DBTable_CustomerBalance)
		 * DB_TableManager.GetInstance()
		 * .GetTable(DB_TableNames.CUSTOMER_BALANCE);
		 * 
		 * BalanceDetails balanceDetails =
		 * cb_table.GetBalanceDetails(myCustomerDetail); int newPendingAmount =
		 * (quantity * amount); int newTotalAmount = (quantity * amount);
		 * balanceDetails.SetPendingAmount(newPendingAmount);
		 * balanceDetails.SetTotalAmount(newTotalAmount);
		 * 
		 * success |= cb_table.UpdateAttribute(balanceDetails); assert success;
		 */

		String message = "";
		if (success)
			message = "Measurement Updated!";
		else
			message = "Failed to update measurement!";

		ShowAlert("INFORMATION!", message);
		return success;
	}

	@FXML
	void OnButtonClicked_UpdateMeasurement(ActionEvent event)
	{
		if (ValidateMeasurements())
		{
			UpdateMeasurementInDatabase();
			// ResetMeasurementFields();
		}
	}

	public void SetMeasurementDetail(MeasurementDetail detail)
	{
		myMeasurementDetail = detail;
	}

	public void SetCustomerDetail(CustomerDetail detail)
	{
		myCustomerDetail = detail;
	}

	public FormType GetType()
	{
		return FormType.UPDATE_CUSTOMER_MEASUREMENTS;
	}

	private void ShowAlert(String header, String message)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(header);
		alert.setHeaderText(message);
		alert.showAndWait();
	}

}

package form.jfx.customerMeasurement;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import database.table.DB_TableManager;
import database.table.DB_TableNames;
import database.table.customerBalance.BalanceDetails;
import database.table.customerBalance.DBTable_CustomerBalance;
import database.table.customerMeasurements.DBTable_CustomerMeasurements;
import database.table.customerMeasurements.MeasurementDetail;
import database.table.customerMeasurements.pantMeasurements.DBTable_PantMeasurements;
import database.table.customerMeasurements.pantMeasurements.PantMeasurement;
import database.table.customerMeasurements.shirtMeasurements.DBTable_ShirtMeasurements;
import database.table.customerMeasurements.shirtMeasurements.ShirtMeasurement;
import database.table.customerTransaction.DBTable_CustomerTransaction;
import database.table.customerTransaction.TransactionDetail;
import form.jfx.JFXForm;
import form.jfx.main.CustomerDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logger.Logger;

public class JFXForm_NewMeasurement extends JFXForm implements Initializable {
	private CustomerDetail myCustomerDetail;

	@FXML
	private ComboBox<String> mycbType;
	@FXML
	private ComboBox<String> mycbItem;
	@FXML
	private TextField mytfSubType;

	@FXML
	private Label myLabel_CustomerName;

	@FXML
	private TextField mytfQuantity;
	@FXML
	private TextField mytfPrice;

	@FXML
	private TextArea mytaMeasurement;

	@FXML
	private TableView<MeasurementDetail> myMeasurementTableView;
	@FXML
	private TableColumn<MeasurementDetail, String> myTcItem;
	@FXML
	private TableColumn<MeasurementDetail, String> myTcType;

	@FXML
	private TableColumn<MeasurementDetail, String> myTcSubType;
	@FXML
	private TableColumn<MeasurementDetail, Integer> myTcQuantity;

	@FXML
	private TableColumn<MeasurementDetail, Integer> myTcPricePerItem;

	@FXML
	private TableColumn<MeasurementDetail, Integer> myTcTotalPrice;

	@FXML
	public Button mybtnAddItem;
	@FXML
	private Label mylblTotal;
	@FXML
	private Label mylblPending;
	@FXML
	private Button mybtnDelete;

	@FXML
	private TextField mytfAmoutPaid;

	@FXML
	public Button mybtnSave;
	@FXML
	public Button mybtnClose;

	@FXML
	private AnchorPane myAnchorPane_PantDetails;
	@FXML
	private JFXTextField myTextField_Pant_Height;
	@FXML
	private JFXTextField myTextField_Pant_Waist;
	@FXML
	private JFXTextField myTextField_Pant_Seat;
	@FXML
	private JFXTextField myTextField_Pant_Thigh;
	@FXML
	private JFXTextField myTextField_Pant_Knee;
	@FXML
	private JFXTextField myTextField_Pant_Bottom;

	@FXML
	private AnchorPane myAnchorPane_ShirtDetails;
	@FXML
	private JFXTextField myTextField_Shirt_Height;
	@FXML
	private JFXTextField myTextField_Shirt_Chest;
	@FXML
	private JFXTextField myTextField_Shirt_Front;
	@FXML
	private JFXTextField myTextField_Shirt_Shoulder;
	@FXML
	private JFXTextField myTextField_Shirt_Shai;
	@FXML
	private JFXTextField myTextField_Shirt_Collar;
	@FXML
	private JFXTextField myTextField_Shirt_Cup;

	private ObservableList<String> myList_Item;
	private ObservableList<String> myList_Type;
	private ObservableList<MeasurementDetail> myMeasurementList;
	private ObservableList<ShirtMeasurement> myShirtMeasurementList;
	private ObservableList<PantMeasurement> myPantMeasurementList;

	public JFXForm_NewMeasurement() {
	}

	public JFXForm_NewMeasurement(String title, String fxmlPath) {
		super(title, fxmlPath);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		InitItems();
		InitTypes();
		InitMeasurementList();

		myAnchorPane_PantDetails.setVisible(false);
		myAnchorPane_ShirtDetails.setVisible(false);
	}

	private void InitMeasurementList() {
		myMeasurementList = FXCollections.observableArrayList();
		myShirtMeasurementList = FXCollections.observableArrayList();
		myPantMeasurementList = FXCollections.observableArrayList();
	}

	private void InitItems() {
		myList_Item = FXCollections.observableArrayList();

		AddItem("Shirt");
		AddItem("Pant");

		SetItems(mycbItem, myList_Item);
	}

	private void AddItem(String itemName) {
		myList_Item.add(itemName);
	}

	private void InitTypes() {
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

		SetItems(mycbType, myList_Type);
	}

	private void AddType(String typeName) {
		myList_Type.add(typeName);
	}

	private boolean ValidateMeasurements() {
		String item = mycbItem.getValue();
		String type = mycbType.getValue();

		if ((item == null) || (type == null)) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("WARNING!");
			alert.setHeaderText("Item and Type must be selected!");
			alert.setContentText("Please Enter all the necessary fields!");
			alert.showAndWait();
			return false;
		}

		try {
			if (!mytfQuantity.getText().isEmpty())
				Integer.parseInt(mytfQuantity.getText());
			if (!mytfPrice.getText().isEmpty())
				Integer.parseInt(mytfPrice.getText());
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("WARNING!");
			alert.setHeaderText("Quantity and Amount should be Integers!");
			alert.setContentText("Please enter amount and quantity as Integers!");
			alert.showAndWait();
			System.out.println("Quantity and Amount should be Integers!");
			return false;
		}

		return true;
	}

	private void ResetMeasurementFields() {
		mycbItem.getSelectionModel().clearSelection();
		mycbType.getSelectionModel().clearSelection();
		mytfSubType.clear();
		mytaMeasurement.clear();
		mytfQuantity.clear();
		mytfPrice.clear();

		myTextField_Shirt_Height.clear();
		myTextField_Shirt_Chest.clear();
		myTextField_Shirt_Front.clear();
		myTextField_Shirt_Shoulder.clear();
		myTextField_Shirt_Shai.clear();
		myTextField_Shirt_Collar.clear();
		myTextField_Shirt_Cup.clear();

		myTextField_Pant_Height.clear();
		myTextField_Pant_Waist.clear();
		myTextField_Pant_Seat.clear();
		myTextField_Pant_Thigh.clear();
		myTextField_Pant_Knee.clear();
		myTextField_Pant_Bottom.clear();

	}

	private void AddItem() {
		String item = mycbItem.getValue();
		String type = mycbType.getValue();
		String subType = mytfSubType.getText();
		String measurement = mytaMeasurement.getText();
		String str_amount = mytfPrice.getText().trim();
		String str_quantity = mytfQuantity.getText().trim();
		int amount = 0;
		if (!str_amount.isEmpty())
			amount = Integer.parseInt(str_amount);
		int quantity = 0;
		if (!str_quantity.isEmpty())
			quantity = Integer.parseInt(str_quantity);
		MeasurementDetail md = new MeasurementDetail();
		md.SetSelectItem(item);
		md.SetSelectType(type);
		md.SetSelectSubType(subType);
		md.SetMeasurement(measurement);
		md.SetQuantity(quantity);
		md.SetPricePerItem(amount);
		myMeasurementList.add(md);

		if (myAnchorPane_ShirtDetails.isVisible()) {
			String ShirtHeight = myTextField_Shirt_Height.getText().trim();
			String Chest = myTextField_Shirt_Chest.getText().trim();
			String Front = myTextField_Shirt_Front.getText().trim();
			String shoulder = myTextField_Shirt_Shoulder.getText().trim();
			String Shai = myTextField_Shirt_Shai.getText().trim();
			String collar = myTextField_Shirt_Collar.getText().trim();
			String Cup = myTextField_Shirt_Cup.getText().trim();
			ShirtMeasurement sm = new ShirtMeasurement();
			sm.SetHeight(ShirtHeight);
			sm.SetChest(Chest);
			sm.SetFront(Front);
			sm.SetShoulder(shoulder);
			sm.SetShai(Shai);
			sm.SetCollar(collar);
			sm.SetCup(Cup);
			myShirtMeasurementList.add(sm);
		} else {
			String PantHeight = myTextField_Pant_Height.getText().trim();
			String waist = myTextField_Pant_Waist.getText().trim();
			String seat = myTextField_Pant_Seat.getText().trim();
			String thigh = myTextField_Pant_Thigh.getText().trim();
			String knee = myTextField_Pant_Knee.getText().trim();
			String bottom = myTextField_Pant_Bottom.getText().trim();

			PantMeasurement pm = new PantMeasurement();
			pm.SetHeight(PantHeight);
			pm.SetWaist(waist);
			pm.SetSeat(seat);
			pm.SetThigh(thigh);
			pm.SetKnee(knee);
			pm.SetBottom(bottom);
			// pm.SetStatus(status);
			myPantMeasurementList.add(pm);
		}

	}

	@FXML
	private void OnComboBoxChanged_Item(ActionEvent event) {
		if (mycbItem.getSelectionModel().getSelectedItem().equals("Shirt")) {
			myAnchorPane_ShirtDetails.setVisible(true);
			myAnchorPane_PantDetails.setVisible(false);
		} else {
			myAnchorPane_ShirtDetails.setVisible(false);
			myAnchorPane_PantDetails.setVisible(true);
		}
	}

	@FXML
	private void OnButtonClicked_AddItem() {
		if (!ValidateMeasurements()) {
			return;
		}

		AddItem();
		UpdateTable();

		ResetMeasurementFields();
	}

	private boolean UpdateMeasurementInDatabase() {
		DBTable_CustomerMeasurements table = (DBTable_CustomerMeasurements) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_MEASUREMENTS);
		DBTable_ShirtMeasurements shirttable = (DBTable_ShirtMeasurements) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.SHIRT_MEASUREMENTS);
		DBTable_PantMeasurements panttable = (DBTable_PantMeasurements) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.PANT_MEASUREMENTS);

		boolean success = true;
		int shirtcount=0,pantcount=0;
		for (int i = 0; i < myMeasurementList.size(); i++) {
			MeasurementDetail detail = myMeasurementList.get(i);

			int customerId = myCustomerDetail.GetId().get();
			String item = detail.GetSelectItem().get();
			String type = detail.GetSelectType().get();
			String subType = detail.GetSelectSubType().get();
			String measurement = detail.GetMeasurement().get();
			int quantity = detail.GetQuantity().get();
			int pricePerItem = detail.GetPricePerItem().get();
			String status = "Pending";
			success = table.Insert(customerId, item, type, subType, measurement, pricePerItem, quantity);
			detail.SetMeasurementId(table.GetLastMeasurementId());
			assert success : "Unable to insert measurements in table: " + table.GetName();
			if(item.equals("Shirt"))
			{
				ShirtMeasurement sm = myShirtMeasurementList.get(shirtcount);
				shirtcount++;
				int measurementId = detail.GetMeasurementId().get();
				String height = sm.GetHeight().get();
				String chest = sm.GetChest().get();
				String front = sm.GetFront().get();
				String shoulder = sm.GetShoulder().get();
				String shai = sm.GetShai().get();
				String collar = sm.GetCollar().get();
				String cup = sm.GetCup().get();
				success = shirttable.Insert(measurementId, customerId, height, chest, front, shoulder, shai, collar, cup,status);
				assert success : "Unable to insert Shirt measurements in table: " + table.GetName();

			}
			else
			{
				PantMeasurement pm = myPantMeasurementList.get(pantcount);
				pantcount++;
				int measuremeId = detail.GetMeasurementId().get();
				String height = pm.GetHeight().get();
				String waist = pm.GetWaist().get();
				String seat = pm.GetSeat().get();
				String thigh = pm.GetThigh().get();
				String knee = pm.GetKnee().get();
				String bottom = pm.GetBottom().get();
				success = panttable.Insert(measuremeId, customerId, height, waist, seat, thigh, knee, bottom, status);
				assert success : "Unable to Insert Pant measurements in table: " + table.GetName();

			}
		}

		return success;
	}

	private boolean UpdateBalanceInDatabase() {
		DBTable_CustomerBalance cb_table = (DBTable_CustomerBalance) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_BALANCE);
		DBTable_CustomerTransaction ct_table = (DBTable_CustomerTransaction) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_TRANSACTIONS);

		BalanceDetails details = cb_table.GetBalanceDetails(myCustomerDetail);

		int paidAmount = 0;
		try {
			String amountPaid = mytfAmoutPaid.getText().trim();
			if (!amountPaid.isEmpty() && !(amountPaid.equals(""))) {
				Logger.Log("mytfAmoutPaid.getText(): " + amountPaid);
				paidAmount = Integer.parseInt(amountPaid);
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("ERROR!");
			alert.setHeaderText("Enter Valid PAID Amount!");
			alert.setContentText(null);
			alert.showAndWait();
			return false;
		}
		int totalAmount = Integer.parseInt(mylblTotal.getText());

		boolean result = true;
		int customerId = myCustomerDetail.GetId().get();
		if (details == null) // does not exist in the CustomerBalanceTable
		{
			int pendingAmount = totalAmount - paidAmount;
			result = cb_table.Insert(customerId, pendingAmount, totalAmount);

			// add transaction detail to the database
			TransactionDetail td = new TransactionDetail();
			td.SetAmount(paidAmount);
			td.SetStatus("Received");
			td.SetCustomerID(myCustomerDetail.GetId().get());
			ct_table.Insert(td);

			assert result : "Unable to add to CustomerBalance";
			return result;
		}

		// customer exists in the CustomerBalance table
		int pendingAmount = totalAmount - paidAmount;
		Logger.Log("Pending Amount: " + pendingAmount);
		Logger.Log(
				"Existing -> (pending: " + details.GetPendingAmount() + ")(total: " + details.GetTotalAmount() + ")");

		int newPendingAmount = pendingAmount + details.GetPendingAmount();
		int newTotalAmount = totalAmount + details.GetTotalAmount();
		details.SetPendingAmount(newPendingAmount);
		details.SetTotalAmount(newTotalAmount);
		result &= cb_table.UpdateAttribute(details);

		// add transaction detail to the database
		TransactionDetail td = new TransactionDetail();
		td.SetAmount(paidAmount);
		td.SetStatus("Received");
		td.SetCustomerID(myCustomerDetail.GetId().get());
		ct_table.Insert(td);

		return result;
	}

	boolean ValidateOnSaveButtonClicked() {
		return true;
		/*
		 * try { Integer.parseInt(mytfAmoutPaid.getText()); } catch (Exception
		 * e) { Alert alert = new Alert(AlertType.INFORMATION);
		 * alert.setTitle("ERROR!");
		 * alert.setHeaderText("Enter Valid PAID Amount!");
		 * alert.setContentText(null); alert.showAndWait(); return false; }
		 * return true;
		 */
	}

	@FXML
	private void onSavebtnPressed() {
		if (!ValidateOnSaveButtonClicked())
			return;

		{
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("WARNING!");
			alert.setHeaderText("Are you sure you want to save the Measurements?");
			Optional<ButtonType> alertResult = alert.showAndWait();
			if (alertResult.isPresent() && alertResult.get() == ButtonType.CANCEL)
				return;
		}

		boolean result = UpdateMeasurementInDatabase();
		assert result;
		result &= UpdateBalanceInDatabase();
		assert result;

		String header = "";
		String message = "";
		if (result) {
			header = "SUCCESS!";
			message = "Items Added!";
		} else {
			header = "FAILURE!";
			message = "Failed to add Items!";
		}

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(header);
		alert.setHeaderText(message);
		alert.showAndWait();

		if (!result)
			return;

		ResetAll();
	}

	@FXML
	private void OnAmountPaidChanged() {
		try {
			int paidAmount = Integer.parseInt(mytfAmoutPaid.getText());
			int totalAmount = CalculateTotalPrice();
			String pendingAmount = Integer.toString(totalAmount - paidAmount);
			UpdatePendingAmount(pendingAmount);
		} catch (NumberFormatException nfe) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setHeaderText("Enter Valid Amount!");
			alert.showAndWait();
		}
	}

	private void UpdatePendingAmount(String amount) {
		mylblPending.setText(amount);
	}

	private void ResetAll() {
		ResetMeasurementFields();

		myMeasurementList.clear();
		mylblTotal.setText("-");
		mylblPending.setText("-");
		mytfAmoutPaid.setText(" ");
	}

	private void UpdateTable() {
		myMeasurementTableView.setItems(myMeasurementList);

		myTcItem.setCellValueFactory(cellData -> cellData.getValue().GetSelectItem());
		myTcType.setCellValueFactory(cellData -> cellData.getValue().GetSelectType());
		myTcSubType.setCellValueFactory(cellData -> cellData.getValue().GetSelectSubType());
		myTcQuantity.setCellValueFactory(cellData -> cellData.getValue().GetQuantity().asObject());
		myTcPricePerItem.setCellValueFactory(cellData -> cellData.getValue().GetPricePerItem().asObject());
		myTcTotalPrice.setCellValueFactory(cellData -> cellData.getValue().GetTotalPrice().asObject());

		// set total amount
		mylblTotal.setText(Integer.toString(CalculateTotalPrice()));
	}

	private int CalculateTotalPrice() {
		int totalPrice = 0;
		for (MeasurementDetail md : myMeasurementList)
			totalPrice += md.GetTotalPrice().get();

		return totalPrice;
	}

	@FXML
	private void onClosebtnPressed() {
		Stage stage = (Stage) mybtnClose.getScene().getWindow();
		stage.close();
	}

	private void DeleteItem() {
		MeasurementDetail detail = myMeasurementTableView.getSelectionModel().getSelectedItem();
		 int position = myMeasurementTableView.getSelectionModel().getSelectedIndex();
		 int shirtcount = 0,pantcount = 0;
		 for(int i=0;i<position;i++)
		 {
			 MeasurementDetail temp = myMeasurementList.get(i);
			 String item = temp.GetSelectItem().get();
			 if(item.equals("Shirt"))
			 {
				 shirtcount++;
			 }
			 else
			 {
				 pantcount++;
			 }
		 }
		 if(detail.GetSelectItem().get().equals("Shirt"))
		 {
			 myShirtMeasurementList.remove(shirtcount);
		 }
		 else
		 {
			 myPantMeasurementList.remove(pantcount);
		 }
		myMeasurementList.remove(detail);


	}

	@FXML
	private void OnDeleteButtonPressed() {
		// check entries in table
		if (myMeasurementList.isEmpty()) {
			return;
		}

		int selectedIndex = myMeasurementTableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex < 0) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setHeaderText("Select an item to delete!");
			alert.showAndWait();
			return;
		}

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("WARNING!");
		alert.setHeaderText("Are you sure you want to delete the Measurement?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.CANCEL)
			return;

		DeleteItem();
		UpdateTable();
	}

	public void SetCustomerDetail(CustomerDetail customerDetail) {
		myCustomerDetail = customerDetail;
	}

	public void UpdateFields() {
		String fullName = myCustomerDetail.GetFirstName().get() + " " + myCustomerDetail.GetLastName().get();
		myLabel_CustomerName.setText(fullName);
	}

	private void SetItems(ComboBox<String> comboBox, ObservableList<String> list) {
		comboBox.setItems(list);
	}

	public FormType GetType() {
		return FormType.ADD_CUSTOMER_MEASUREMENT;
	}

}

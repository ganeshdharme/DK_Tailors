package form.jfx.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;

import application.SaveManager;
import database.table.DB_TableManager;
import database.table.DB_TableNames;
import database.table.customerBalance.BalanceDetails;
import database.table.customerBalance.DBTable_CustomerBalance;
import database.table.customerDetails.DBTable_CustomerDetails;
import database.table.customerMeasurements.DBTable_CustomerMeasurements;
import database.table.customerMeasurements.MeasurementDetail;
import database.table.customerTransaction.DBTable_CustomerTransaction;
import database.table.customerTransaction.TransactionDetail;
import form.jfx.JFXForm;
import form.jfx.customerMeasurement.JFXForm_NewMeasurement;
import form.jfx.updateCustomerDetails.JFXForm_UpdateCustomerDetails;
import form.jfx.updateCustomerMeasurements.JFXForm_UpdateMeasurement;
import form.jfx.viewMeasurements.JFXForm_ViewMeasurements;
import form.jfx.viewTransactions.JFXForm_ViewTransactions;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;

public class JFXForm_Main extends JFXForm implements Initializable
{

	/////////////////////////////////////////////

	@FXML private DBTable_CustomerDetails myDBTable_CustomerDetails;
	@FXML private DBTable_CustomerMeasurements myDBTable_CustomerMeasurement;
	@FXML private DBTable_CustomerBalance myDBTable_CustomerBalance;
	@FXML private DBTable_CustomerTransaction myDBTable_CustomerTransaction;

	@FXML private AnchorPane myAnchorPane_Bottom;

	@FXML private JFXButton myButton_Home;

	@FXML private JFXButton myButton_OldCustomers;

	@FXML private JFXButton myButton_Search;

	@FXML private AnchorPane myAnchorPane_Center;

	@FXML private AnchorPane myAnchorPane_NewCustomer;

	@FXML private ToggleGroup radioButtonGroup;

	@FXML private JFXButton myButton_AddCustomer;

	@FXML private StackPane myStackPane_OldCustomers;

	@FXML private AnchorPane myAnchorPane_Search;

	@FXML private Label myLabel_Email;

	@FXML private Label myLabel_Tailor;

	@FXML private Label myLabel_Address;

	@FXML private Button myButton_DeleteCustomer;

	@FXML private Button myButton_UpdateMeasurement;

	@FXML private Button myButton_DeleteMeasurement;

	@FXML private JFXButton myButtonOldCustomer_Delete;

	@FXML private JFXButton myButtonOldCustomer_Update;

	@FXML private Label myAnchorPane_Top;

	public JFXForm_Main()
	{
	}

	public JFXForm_Main(String title, String path)
	{
		super(title, path);
	}

	/************************ HOME *************************/

	@FXML private AnchorPane myAnchorPane_Home;
	@FXML private JFXButton myButton_Home_ChangeImage;
	@FXML private ImageView myImageView_Home;
	@FXML private AnchorPane myAnchorPane_Home_ImageView;
	@FXML private StackPane myStackPane_ImageView_Home;

	private void InitHomePanel()
	{
		if (!SaveManager.GetInstance().DoesImageFileExist())
			return;

		BufferedImage bufferedImage = null;
		try
		{
			bufferedImage = ImageIO.read(new File(SaveManager.GetInstance().GetImagePath()));
		}
		catch (IOException e)
		{
			return;
		}
		Image image;
		if (bufferedImage == null)
			return;

		image = SwingFXUtils.toFXImage(bufferedImage, null);
		myImageView_Home.setImage(image);
		myImageView_Home.fitWidthProperty().bind(myAnchorPane_Home_ImageView.widthProperty());
		myImageView_Home.fitHeightProperty().bind(myAnchorPane_Home_ImageView.heightProperty());
	}

	@FXML
	private void OnButtonClicked_Home(ActionEvent event)
	{
		myAnchorPane_NewCustomer.setVisible(false);
		myStackPane_OldCustomers.setVisible(false);
		myAnchorPane_Search.setVisible(false);
		myAnchorPane_Home.setVisible(true);
	}

	@FXML
	private void OnButtonClicked_Home_ChangeImage(ActionEvent event)
	{
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("JPG (Joint Photographic Group)", "*.jpg"),
				new FileChooser.ExtensionFilter("JPEG (Joint Photographic Experts Group)", "*.jpeg"),
				new FileChooser.ExtensionFilter("PNG (Portable Network Graphics)", "*.png"));

		fileChooser.setTitle("Choose a Image File");

		File file = null;
		try
		{
			file = fileChooser.showOpenDialog(null);
		}
		catch (Exception e)
		{
			ShowAlert("ERROR!", "Select a .png or .jpg file!");
			return;
		}

		BufferedImage bufferedImage = null;
		if (file != null)
		{
			System.out.println(file);
			try
			{
				bufferedImage = ImageIO.read(file);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			Image image;
			image = SwingFXUtils.toFXImage(bufferedImage, null);
			myImageView_Home.setImage(image);
			myImageView_Home.fitWidthProperty().bind(myAnchorPane_Home_ImageView.widthProperty());
			myImageView_Home.fitHeightProperty().bind(myAnchorPane_Home_ImageView.heightProperty());

			// save image file path
			SaveManager.GetInstance().SetHomePageImagePath(file.getAbsolutePath());
		}
		else
		{
			ShowAlert("ERROR!", "Unalbe to read the image File!");
		}
	}

	/*****************************************************/

	/**************** NEW_CUSTOMER ************************/
	@FXML private TextField myTextField_NewCustomer_Firstname;
	@FXML private TextField myTextField_NewCustomer_Lastname;
	@FXML private TextField myTextField_NewCustomer_MobNo;
	@FXML private TextField myTextField_NewCustomer_Address;
	@FXML private TextField myTextField_NewCustomer_Email;
	@FXML private TextField myTextField_NewCustomer_Age;

	@FXML private JFXRadioButton myRadioButton_NewCustomer_Male;
	@FXML private JFXRadioButton myRadioButton_NewCustomer_Female;
	@FXML private JFXButton myButton_NewCustomer;
	@FXML private ImageView myImageView_NewCustomer;
	@FXML private AnchorPane myAnchorPane_NewCustomer_ImageView;

	private void InitNewCustomerPanel()
	{
		myImageView_NewCustomer.fitWidthProperty().bind(myAnchorPane_NewCustomer_ImageView.widthProperty());
		myImageView_NewCustomer.fitHeightProperty().bind(myAnchorPane_NewCustomer_ImageView.heightProperty());
	}

	private boolean ValidateCustomerEntry()
	{
		// validate if the fields are empty
		String fname = myTextField_NewCustomer_Firstname.getText();
		String lname = myTextField_NewCustomer_Lastname.getText();

		if (fname.isEmpty() || lname.isEmpty() || myTextField_NewCustomer_MobNo.getText().isEmpty())
		{
			ShowAlert("ERROR!", "Some of the necessary fields are empty!");
			return false;
		}

		// validate mobile number
		long mobNo;
		try
		{
			mobNo = Long.parseLong(myTextField_NewCustomer_MobNo.getText());
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

		if (!myTextField_NewCustomer_Age.getText().trim().isEmpty())
		{
			try
			{
				Integer.parseInt(myTextField_NewCustomer_Age.getText());
			}
			catch (Exception e)
			{
				ShowAlert("ERROR", "Invalid Age!");
				return false;
			}
		}

		// validate email address
		String email = myTextField_NewCustomer_Email.getText();
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
		if (!(myRadioButton_NewCustomer_Male.isSelected() || myRadioButton_NewCustomer_Female.isSelected()))
		{
			ShowAlert("ERROR!", "Select Gender!");
			return false;
		}

		return true;
	}

	private CustomerDetail AddCustomer()
	{
		// store customer details
		DBTable_CustomerDetails customerDetailsTable = (DBTable_CustomerDetails) (DB_TableManager.GetInstance()
				.GetTable("CustomerDetails"));

		String fname = myTextField_NewCustomer_Firstname.getText();
		String lname = myTextField_NewCustomer_Lastname.getText();
		long mobNo = Long.parseLong(myTextField_NewCustomer_MobNo.getText());
		String email = myTextField_NewCustomer_Email.getText();

		int age = 0;
		if (!myTextField_NewCustomer_Age.getText().trim().isEmpty())
			age = Integer.parseInt(myTextField_NewCustomer_Age.getText());

		String gender;

		if (myRadioButton_NewCustomer_Male.isSelected())
			gender = "Male";
		else
			gender = "Female";

		String address = myTextField_NewCustomer_Address.getText();
		boolean success = customerDetailsTable.Insert(fname, lname, mobNo, email, age, gender, address);
		assert success : "Unable to add new Customer";

		CustomerDetail customerDetail = null;
		String message = "";
		String header = "";
		if (success)
		{
			header = "SUCCESS!";
			message = "New Customer Added!";
		}
		else
		{
			header = "FAILURE!";
			message = "Failed to add new Customer!";
		}

		myTextField_NewCustomer_Firstname.setText("");
		myTextField_NewCustomer_Lastname.setText("");
		myTextField_NewCustomer_MobNo.setText("");
		myTextField_NewCustomer_Address.setText("");
		myTextField_NewCustomer_Email.setText("");
		myTextField_NewCustomer_Age.setText("");
		myRadioButton_NewCustomer_Male.setSelected(false);
		myRadioButton_NewCustomer_Female.setSelected(false);

		ShowAlert(header, message);

		// add default Balance Details
		CustomerDetail customerDetails = customerDetailsTable.GetLastAddedCustomer();
		DBTable_CustomerBalance cb_table = (DBTable_CustomerBalance) (DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_BALANCE));
		boolean result = cb_table.Insert(customerDetails.GetId().get(), 0, 0);
		assert result;

		return customerDetails;
	}

	@FXML
	void OnButtonClicked_NewCustomer_AddCustomer(ActionEvent event)
	{
		if (ValidateCustomerEntry())
		{
			CustomerDetail customerDetail = AddCustomer();

			JFXForm_NewMeasurement controller = GetForm(FormType.ADD_CUSTOMER_MEASUREMENT).GetLoader().getController();
			controller.SetCustomerDetail(customerDetail);
			controller.UpdateFields();
			ShowForm(FormType.ADD_CUSTOMER_MEASUREMENT);
		}
	}

	/******************************************************/

	/******************* OLD_CUSTOMERS ********************/
	@FXML private JFXTextField myTextField_OldCustomers_Search;
	@FXML private Button myButton_OldCustomers_Update;
	@FXML private Button myButton_OldCustomers_Delete;
	@FXML private Button myButton_OldCustomers_AddMeasurement;
	@FXML private Button myButton_OldCustomers_ViewTransactions;
	@FXML private TableView<CustomerDetail> myTableView_OldCustomers;
	@FXML private TableColumn<CustomerDetail, String> myTableColumn_OldCustomers_FirstName;
	@FXML private TableColumn<CustomerDetail, String> myTableColumn_OldCustomers_LastName;
	@FXML private TableColumn<CustomerDetail, Long> myTableColumn_OldCustomers_MobNo;
	@FXML private TableColumn<CustomerDetail, String> myTableColumn_OldCustomers_Email;
	@FXML private TableColumn<CustomerDetail, String> myTableColumn_OldCustomers_Address;
	@FXML private TableColumn<CustomerDetail, Integer> myTableColumn_OldCustomers_Age;
	@FXML private TableColumn<CustomerDetail, String> myTableColumn_OldCustomers_Gender;

	private void InitOldCustomersPanel()
	{
		myTableColumn_OldCustomers_FirstName.setCellValueFactory(cellData -> cellData.getValue().GetFirstName());
		myTableColumn_OldCustomers_LastName.setCellValueFactory(cellData -> cellData.getValue().GetLastName());
		myTableColumn_OldCustomers_MobNo.setCellValueFactory(cellData -> cellData.getValue().GetMobileNo().asObject());
		myTableColumn_OldCustomers_Email.setCellValueFactory(cellData -> cellData.getValue().GetEmail());
		myTableColumn_OldCustomers_Age.setCellValueFactory(cellData -> cellData.getValue().GetAge().asObject());
		myTableColumn_OldCustomers_Address.setCellValueFactory(cellData -> cellData.getValue().GetAddress());
		myTableColumn_OldCustomers_Gender.setCellValueFactory(cellData -> cellData.getValue().GetGender());

		ObservableList<CustomerDetail> customerDetails = myDBTable_CustomerDetails.GetCustomerDetailsList();
		PopulateCustomerDetailTable(myTableView_OldCustomers, customerDetails);
	}

	private void PopulateCustomerDetailTable(TableView<CustomerDetail> tableView,
			ObservableList<CustomerDetail> customer)
	{
		tableView.setItems(customer);
	}

	private void PopulateMeasurementTable(TableView<MeasurementDetail> tableView,
			ObservableList<MeasurementDetail> measurementList)
	{
		tableView.setItems(measurementList);
	}

	private CustomerDetail GetSelectedCustomer(TableView<CustomerDetail> tableView)
	{
		return tableView.getSelectionModel().getSelectedItem();
	}

	@FXML
	private void OnButtonClicked_OldCustomers_ViewTransactions()
	{
		System.out.println("View Transaction");

		CustomerDetail customer = myTableView_OldCustomers.getSelectionModel().getSelectedItem();
		if (customer == null)
		{
			ShowAlert("WARNING", "Select a customer!");
			return;
		}
		JFXForm_ViewTransactions controller = GetForm(FormType.VIEW_TRANSACTIONS).GetLoader().getController();
		controller.SetCustomerDetail(customer);
		controller.UpdateFields();

		ShowForm(FormType.VIEW_TRANSACTIONS);
	}

	@FXML
	private void OnTextFieldChanged_OldCustomers_Search(/* ActionEvent event */)
	{
		myTableColumn_OldCustomers_FirstName.setCellValueFactory(cellData -> cellData.getValue().GetFirstName());
		myTableColumn_OldCustomers_LastName.setCellValueFactory(cellData -> cellData.getValue().GetLastName());
		myTableColumn_OldCustomers_MobNo.setCellValueFactory(cellData -> cellData.getValue().GetMobileNo().asObject());
		myTableColumn_OldCustomers_Email.setCellValueFactory(cellData -> cellData.getValue().GetEmail());
		myTableColumn_OldCustomers_Age.setCellValueFactory(cellData -> cellData.getValue().GetAge().asObject());
		myTableColumn_OldCustomers_MobNo.setCellValueFactory(cellData -> cellData.getValue().GetMobileNo().asObject());
		myTableColumn_OldCustomers_Gender.setCellValueFactory(cellData -> cellData.getValue().GetGender());

		String key = myTextField_OldCustomers_Search.getText().trim();
		if (!(key.equals("")))
		{
			ObservableList<CustomerDetail> CustomerDetails = myDBTable_CustomerDetails.GetCustomerDetailsWithKey(key);
			myTableView_OldCustomers.setItems(CustomerDetails);
		}
		else
		{
			ObservableList<CustomerDetail> CustomerDetails = myDBTable_CustomerDetails.GetCustomerDetailsList();
			myTableView_OldCustomers.setItems(CustomerDetails);
		}
	}

	@FXML
	private void OnButtonClicked_OldCustomers_Update(ActionEvent event)
	{
		UpdateCustomerDetailsTable(myTableView_OldCustomers);
	}

	@FXML
	private void OnMouseClicked_OldCustomers_TableView(MouseEvent event)
	{
		if (event.getClickCount() == 2)
		{
			CustomerDetail customer = myTableView_OldCustomers.getSelectionModel().getSelectedItem();
			JFXForm_ViewMeasurements controller = GetForm(FormType.VIEW_MEASUREMENT).GetLoader().getController();
			controller.SetCustomerDetail(customer);
			controller.UpdateFields();

			ShowForm(FormType.VIEW_MEASUREMENT);
		}
	}

	@FXML
	private void OnButtonClicked_OldCustomers_Delete(ActionEvent event)
	{
		CustomerDetail customer = myTableView_OldCustomers.getSelectionModel().getSelectedItem();

		if (customer == null)
		{
			ShowAlert("WARNING", "Select a customer to Delete!");
			return;
		}

		String name = customer.GetFirstName().get();
		String message = "Are you sure you want to delete customer? : " + name;

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("ALERT!");
		alert.setHeaderText(message);
		Optional<ButtonType> alertResult = alert.showAndWait();
		if (alertResult.isPresent() && alertResult.get() == ButtonType.CANCEL)
			return;

		DeleteCustomerFromDatabase(customer);
	}

	@FXML
	private void OnButtonClicked_OldCustomers_AddMeasurement(ActionEvent event)
	{
		// pass customer details
		CustomerDetail customerDetail = myTableView_OldCustomers.getSelectionModel().getSelectedItem();
		if (customerDetail == null)
		{
			ShowAlert("WARNING!", "Select a customer to ADD MEASUREMENT to!");
			return;
		}

		JFXForm_NewMeasurement controller = GetForm(FormType.ADD_CUSTOMER_MEASUREMENT).GetLoader().getController();
		controller.SetCustomerDetail(customerDetail);
		controller.UpdateFields();

		// open add measurement form
		ShowForm(FormType.ADD_CUSTOMER_MEASUREMENT);
	}

	private boolean DeleteCustomerFromDatabase(CustomerDetail customer)
	{
		myDBTable_CustomerDetails.DeleteRecord(customer);
		myDBTable_CustomerBalance.DeleteRecord(customer);
		myDBTable_CustomerMeasurement.DeleteRecord(customer);

		return true;
	}

	/*******************************************************/

	/*********************** SEARCH ************************/

	@FXML private Label myLabel_Search_Email;
	@FXML private Label myLabel_Search_Address;
	@FXML private Label myLabel_Search_Age;
	@FXML private Label myLabel_Search_Gender;
	@FXML private Label myLabel_Search_PendingAmount;
	@FXML private Label myLabel_Search_TotalAmount;
	@FXML private Label myLabel_Search_PaidAmount;

	@FXML private JFXTextField myTextField_Search;
	@FXML private JFXButton myButton_Search_UpdateCustomerDetails;
	@FXML private JFXButton myButton_Search_AddPendingAmount;
	@FXML private JFXButton myButton_Search_ReceiveAmount;
	@FXML private JFXButton myButton_Search_DeleteCustomer;
	@FXML private JFXButton myButton_Search_UpdateMeasurement;
	@FXML private JFXButton myButton_Search_DeleteMeasurement;
	@FXML private JFXButton myButton_Search_AddMeasurement;

	@FXML private TableView<CustomerDetail> myTableView_Search_CustomerDetails;
	@FXML private TableColumn<CustomerDetail, Integer> myTableColumn_Search_CustomerId;
	@FXML private TableColumn<CustomerDetail, String> myTableColumn_Search_FirstName;
	@FXML private TableColumn<CustomerDetail, String> myTableColumn_Search_LastName;
	@FXML private TableColumn<CustomerDetail, Long> myTableColumn_Search_MobNo;

	@FXML private TableView<MeasurementDetail> myTableView_Search_CustomerMeasurements;
	@FXML private TableColumn<MeasurementDetail, String> myTableColumn_Search_Measurement_Item;
	@FXML private TableColumn<MeasurementDetail, String> myTableColumn_Search_Measurement_Type;
	@FXML private TableColumn<MeasurementDetail, String> myTableColumn_Search_Measurement_SubType;
	@FXML private TableColumn<MeasurementDetail, String> myTableColumn_Search_Measurement_Measurement;
	@FXML private TableColumn<MeasurementDetail, Integer> myTableColumn_Search_Measurement_Quantity;
	@FXML private TableColumn<MeasurementDetail, Integer> myTableColumn_Search_Measurement_PricePerItem;
	@FXML private TableColumn<MeasurementDetail, Integer> myTableColumn_Search_Measurement_Total;
	@FXML private TableColumn<MeasurementDetail, String> myTableColumn_Search_Measurement_Status;

	private void InitSearchPanel()
	{
		// customer details table
		myTableColumn_Search_CustomerId.setCellValueFactory(cellData -> cellData.getValue().GetId().asObject());
		myTableColumn_Search_FirstName.setCellValueFactory(cellData -> cellData.getValue().GetFirstName());
		myTableColumn_Search_LastName.setCellValueFactory(cellData -> cellData.getValue().GetLastName());
		myTableColumn_Search_MobNo.setCellValueFactory(cellData -> cellData.getValue().GetMobileNo().asObject());
		ObservableList<CustomerDetail> customerDetails = myDBTable_CustomerDetails.GetCustomerDetailsList();
		PopulateCustomerDetailTable(myTableView_Search_CustomerDetails, customerDetails);

		// customer Measurement Table
		myTableColumn_Search_Measurement_Item.setCellValueFactory(cellData -> cellData.getValue().GetSelectItem());
		myTableColumn_Search_Measurement_Type.setCellValueFactory(cellData -> cellData.getValue().GetSelectType());
		myTableColumn_Search_Measurement_SubType
				.setCellValueFactory(cellData -> cellData.getValue().GetSelectSubType());
		myTableColumn_Search_Measurement_Quantity
				.setCellValueFactory(cellData -> cellData.getValue().GetQuantity().asObject());
		myTableColumn_Search_Measurement_PricePerItem
				.setCellValueFactory(cellData -> cellData.getValue().GetPricePerItem().asObject());
		myTableColumn_Search_Measurement_Measurement
				.setCellValueFactory(cellData -> cellData.getValue().GetMeasurement());
		myTableColumn_Search_Measurement_Total
				.setCellValueFactory(cellData -> cellData.getValue().GetTotalPrice().asObject());
		myTableColumn_Search_Measurement_Status.setCellValueFactory(cellData -> cellData.getValue().GetStatus());

		PopulateMeasurementTable(myTableView_Search_CustomerMeasurements, null);
	}

	@FXML
	private void OnClicked_Search_TableView_CustomerMeasurement(MouseEvent event)
	{
		if (event.getClickCount() == 2)
		{
			CustomerDetail customerDetail = myTableView_Search_CustomerDetails.getSelectionModel().getSelectedItem();
			MeasurementDetail measurementDetail = myTableView_Search_CustomerMeasurements.getSelectionModel()
					.getSelectedItem();
			if (measurementDetail == null)
			{
				ShowAlert("WARNING!", "Select a measurement to update!");
				return;
			}

			// pass customer details and measurement details
			JFXForm_UpdateMeasurement controller = GetForm(FormType.UPDATE_CUSTOMER_MEASUREMENTS).GetLoader()
					.getController();
			controller.SetCustomerDetail(customerDetail);
			controller.SetMeasurementDetail(measurementDetail);
			controller.InitializeFields();

			ShowForm(FormType.UPDATE_CUSTOMER_MEASUREMENTS);
		}
	}

	@FXML
	private void OnClicked_Search_CustomerDetails()
	{
		CustomerDetail customerDetail = myTableView_Search_CustomerDetails.getSelectionModel().getSelectedItem();

		if (customerDetail == null)
			return;

		myLabel_Search_Address.setText(customerDetail.GetAddress().get());
		myLabel_Search_Age.setText(Integer.toString(customerDetail.GetAge().get()));
		myLabel_Search_Email.setText(customerDetail.GetEmail().get());
		myLabel_Search_Gender.setText(customerDetail.GetGender().get());

		BalanceDetails balance = myDBTable_CustomerBalance.GetBalanceDetails(customerDetail);
		String pendingAmount = "0";
		String totalAmount = "0";
		String paidAmount = "0";

		if (balance != null)
		{
			pendingAmount = Integer.toString(balance.GetPendingAmount());
			totalAmount = Integer.toString(balance.GetTotalAmount());
			paidAmount = Integer.toString((balance.GetTotalAmount() - balance.GetPendingAmount()));
		}
		myLabel_Search_PendingAmount.setText(pendingAmount);
		myLabel_Search_TotalAmount.setText(totalAmount);
		myLabel_Search_PaidAmount.setText(paidAmount);

		UpdateMeasurementTable(customerDetail);
	}

	private void UpdateMeasurementTable(CustomerDetail customerDetail)
	{
		try
		{
			ObservableList<MeasurementDetail> measurementDetails = myDBTable_CustomerMeasurement
					.GetMeasurementDetailsList(customerDetail);
			PopulateMeasurementTable(myTableView_Search_CustomerMeasurements, measurementDetails);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@FXML
	private void OnButtonClicked_Search_AddPendingAmount(ActionEvent event)
	{
		CustomerDetail customerDetail = myTableView_Search_CustomerDetails.getSelectionModel().getSelectedItem();
		if (customerDetail == null)
		{
			ShowAlert("WARNING!", "Select a customer to RECEIVE AMOUNT from!");
			return;
		}
		BalanceDetails balance = myDBTable_CustomerBalance.GetBalanceDetails(customerDetail);

		TextInputDialog dial = new TextInputDialog("Amount");
		dial.setTitle("Enter Pending Amount to ADD: ");
		dial.setHeaderText(null);
		dial.setContentText(null);
		Optional<String> result = dial.showAndWait();

		String inputKey = "";
		if (result.isPresent())
			inputKey = result.get();
		else
			return;

		int amount = 0;
		try
		{
			amount = Integer.parseInt(inputKey);
		}
		catch (Exception e)
		{
			ShowAlert("ERROR!", "Enter Valid Amount");
			return;
		}

		if (amount == 0)
			return;

		int pending = balance.GetPendingAmount() + amount;
		int total = balance.GetTotalAmount() + amount;
		balance.SetPendingAmount(pending);
		balance.SetTotalAmount(total);
		myDBTable_CustomerBalance.UpdateAttribute(balance);

		myLabel_Search_TotalAmount.setText(Integer.toString(total));
		myLabel_Search_PendingAmount.setText(Integer.toString(pending));
		int paidAmount = balance.GetTotalAmount() - balance.GetPendingAmount();
		myLabel_Search_PaidAmount.setText(Integer.toString(paidAmount));

		TransactionDetail td = new TransactionDetail();
		td.SetAmount(amount);
		td.SetStatus("Pending");
		td.SetCustomerID(customerDetail.GetId().get());
		myDBTable_CustomerTransaction.Insert(td);
	}

	@FXML
	private void OnButtonClicked_Search_ReceiveAmount(ActionEvent event)
	{
		CustomerDetail customerDetail = myTableView_Search_CustomerDetails.getSelectionModel().getSelectedItem();
		if (customerDetail == null)
		{
			ShowAlert("WARNING!", "Select a customer to RECEIVE AMOUNT from!");
			return;
		}
		BalanceDetails balance = myDBTable_CustomerBalance.GetBalanceDetails(customerDetail);

		TextInputDialog dial = new TextInputDialog("Amount");
		dial.setTitle("Enter Amount: ");
		dial.setHeaderText(null);
		dial.setContentText(null);
		Optional<String> result = dial.showAndWait();

		String inputKey = "";
		if (result.isPresent())
			inputKey = result.get();
		else
			return;

		int amount = 0;
		try
		{
			amount = Integer.parseInt(inputKey);
		}
		catch (Exception e)
		{
			ShowAlert("ERROR!", "Enter Valid Amount");
			return;
		}

		if (amount == 0)
			return;

		int pending = balance.GetPendingAmount() - amount;
		balance.SetPendingAmount(pending);
		myDBTable_CustomerBalance.UpdateAttribute(balance);

		myLabel_Search_PendingAmount.setText(Integer.toString(pending));
		myLabel_Search_PaidAmount.setText(Integer.toString(balance.GetTotalAmount() - balance.GetPendingAmount()));

		TransactionDetail td = new TransactionDetail();
		td.SetAmount(amount);
		td.SetStatus("Received");
		td.SetCustomerID(customerDetail.GetId().get());
		myDBTable_CustomerTransaction.Insert(td);
	}

	@FXML
	private void OnButtonClicked_Search_UpdateCustomerDetails(ActionEvent event)
	{
		UpdateCustomerDetailsTable(myTableView_Search_CustomerDetails);
	}

	@FXML
	private void OnButtonClicked_Search_DeleteCustomer(ActionEvent event)
	{
		CustomerDetail customer = myTableView_Search_CustomerDetails.getSelectionModel().getSelectedItem();
		if (customer == null)
		{
			ShowAlert("WARNING!", "Select a customer to DELETE!");
			return;
		}
		String name = customer.GetFirstName().get() + " " + customer.GetLastName().get();
		String message = "Are you sure you want to delete customer? : " + name;

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("CONFIRMATION!");
		alert.setHeaderText(message);
		Optional<ButtonType> alertResult = alert.showAndWait();
		if (alertResult.isPresent() && alertResult.get() == ButtonType.CANCEL)
			return;

		DeleteCustomerFromDatabase(customer);
	}

	@FXML
	private void OnButtonClicked_Search_DeleteMeasurement(ActionEvent event)
	{
		DBTable_CustomerMeasurements cm_table = (DBTable_CustomerMeasurements) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_MEASUREMENTS);
		MeasurementDetail detail = myTableView_Search_CustomerMeasurements.getSelectionModel().getSelectedItem();

		if (detail == null)
		{
			ShowAlert("WARNING!", "Select a measurement to delete!");
			return;
		}

		String message = "Are you sure you want to delete measurement : " + detail.GetSelectItem().get();

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("CONFIRMATION!");
		alert.setHeaderText(message);
		Optional<ButtonType> alertResult = alert.showAndWait();
		if (alertResult.isPresent() && alertResult.get() == ButtonType.CANCEL)
			return;

		// delete from customerMeasurement table
		cm_table.DeleteRecord(detail);

		ShowAlert("WARNING!", "You have deleted a measurement. \n Please adjust the Balance this customer!");
	}

	@FXML
	private void OnButtonClicked_Search_AddMeasurement(ActionEvent event)
	{
		// pass customer details
		CustomerDetail customerDetail = myTableView_Search_CustomerDetails.getSelectionModel().getSelectedItem();
		if (customerDetail == null)
		{
			ShowAlert("WARNING!", "Select a customer to ADD MEASUREMENT to!");
			return;
		}

		JFXForm_NewMeasurement controller = GetForm(FormType.ADD_CUSTOMER_MEASUREMENT).GetLoader().getController();
		controller.SetCustomerDetail(customerDetail);

		// open add measurement form
		ShowForm(FormType.ADD_CUSTOMER_MEASUREMENT);
	}

	@FXML
	private void OnButtonClicked_Search_UpdateMeasurement(ActionEvent event)
	{
		CustomerDetail customerDetail = myTableView_Search_CustomerDetails.getSelectionModel().getSelectedItem();
		MeasurementDetail measurementDetail = myTableView_Search_CustomerMeasurements.getSelectionModel()
				.getSelectedItem();
		if (measurementDetail == null)
		{
			ShowAlert("WARNING!", "Select a measurement to update!");
			return;
		}

		// pass customer details and measurement details
		JFXForm_UpdateMeasurement controller = GetForm(FormType.UPDATE_CUSTOMER_MEASUREMENTS).GetLoader()
				.getController();
		controller.SetCustomerDetail(customerDetail);
		controller.SetMeasurementDetail(measurementDetail);
		controller.InitializeFields();

		ShowForm(FormType.UPDATE_CUSTOMER_MEASUREMENTS);
	}

	/*************************************************************************/

	@FXML
	void OnButtonClicked_NewCustomer(ActionEvent event)
	{
		myAnchorPane_NewCustomer.setVisible(true);
		myStackPane_OldCustomers.setVisible(false);
		myAnchorPane_Search.setVisible(false);
		myAnchorPane_Home.setVisible(false);
	}

	@FXML
	void OnButtonClicked_OldCustomers(ActionEvent event)
	{
		myStackPane_OldCustomers.setVisible(true);
		myAnchorPane_NewCustomer.setVisible(false);
		myAnchorPane_Search.setVisible(false);
		myAnchorPane_Home.setVisible(false);
	}

	@FXML
	void OnButtonClicked_Search(ActionEvent event)
	{
		myAnchorPane_Search.setVisible(true);
		myAnchorPane_NewCustomer.setVisible(false);
		myStackPane_OldCustomers.setVisible(false);
		myAnchorPane_Home.setVisible(false);
	}

	@FXML
	void OnButtonClicked_UpdateCustomerDetails(ActionEvent event)
	{
	}

	@FXML
	void OnButtonClicked_UpdateMeasurement(ActionEvent event)
	{
	}

	@FXML
	void OnTextFieldChanged_Search(/* ActionEvent event */)
	{
		myTableColumn_Search_FirstName.setCellValueFactory(cellData -> cellData.getValue().GetFirstName());
		myTableColumn_Search_LastName.setCellValueFactory(cellData -> cellData.getValue().GetLastName());
		myTableColumn_Search_MobNo.setCellValueFactory(cellData -> cellData.getValue().GetMobileNo().asObject());
		String key = myTextField_Search.getText().trim();
		if (!(key.equals("")))
		{
			ObservableList<CustomerDetail> CustomerDetails = myDBTable_CustomerDetails.GetCustomerDetailsWithKey(key);
			myTableView_Search_CustomerDetails.setItems(CustomerDetails);
		}
		else
		{
			ObservableList<CustomerDetail> CustomerDetails = myDBTable_CustomerDetails.GetCustomerDetailsList();
			myTableView_Search_CustomerDetails.setItems(CustomerDetails);
		}
	}

	private void UpdateCustomerDetailsTable(TableView<CustomerDetail> tableView)
	{
		CustomerDetail customerDetail = GetSelectedCustomer(tableView);
		if (customerDetail == null)
		{
			ShowAlert("WARNING!", "Select Customer to update!");
			return;
		}
		JFXForm_UpdateCustomerDetails controller = GetForm(FormType.UPDATE_CUSTOMER_DETAILS).GetLoader()
				.getController();
		controller.SetCustomer(customerDetail);

		ShowForm(FormType.UPDATE_CUSTOMER_DETAILS);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		myAnchorPane_NewCustomer.setVisible(false);
		myStackPane_OldCustomers.setVisible(false);
		myAnchorPane_Search.setVisible(false);
		myAnchorPane_Home.setVisible(true);

		InitializeDatabaseTables();
		InitHomePanel();
		InitNewCustomerPanel();
		InitOldCustomersPanel();
		InitSearchPanel();
	}

	private void InitializeDatabaseTables()
	{
		myDBTable_CustomerDetails = (DBTable_CustomerDetails) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_DETAILS);
		myDBTable_CustomerMeasurement = (DBTable_CustomerMeasurements) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_MEASUREMENTS);
		myDBTable_CustomerBalance = (DBTable_CustomerBalance) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_BALANCE);
		myDBTable_CustomerTransaction = (DBTable_CustomerTransaction) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_TRANSACTIONS);

	}

	private void ShowAlert(String header, String message)
	{
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(header);
		alert.setHeaderText(message);
		alert.showAndWait();
	}

}

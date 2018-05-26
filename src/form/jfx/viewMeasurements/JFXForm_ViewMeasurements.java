package form.jfx.viewMeasurements;

import java.net.URL;
import java.util.ResourceBundle;

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
import form.jfx.JFXForm;
import form.jfx.main.CustomerDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;

public class JFXForm_ViewMeasurements extends JFXForm implements Initializable
{

	private DBTable_CustomerMeasurements myDBTable_CustomerMeasurement;
	private DBTable_CustomerBalance myDBTable_CustomerBalance;
	private DBTable_PantMeasurements myDBTable_PantMeasurements;
	private DBTable_ShirtMeasurements myDBTable_ShirtMeasurements;

	@FXML private TableView<MeasurementDetail> myTableView_MeasurementsDetails;
	@FXML private TableColumn<MeasurementDetail, String> myTableViewColumn__Measurements_Item;
	@FXML private TableColumn<MeasurementDetail, String> myTableViewColumn__Measurements_Type;
	@FXML private TableColumn<MeasurementDetail, String> myTableViewColumn_Measurements_SubType;
	@FXML private TableColumn<MeasurementDetail, String> myTableViewColumn_Measurements_Measurement;
	@FXML private TableColumn<MeasurementDetail, String> myTableViewColumn_Measurements_Status;


	@FXML private TableView<ShirtMeasurement> myTableView_ShirtMeasurements;
	@FXML private TableColumn<ShirtMeasurement, String> myTableViewColumn__Shirt_Measurements_Height;
	@FXML private TableColumn<ShirtMeasurement, String> myTableViewColumn__Shirt_Measurements_Chest;
	@FXML private TableColumn<ShirtMeasurement, String> myTableViewColumn__Shirt_Measurements_Front;
	@FXML private TableColumn<ShirtMeasurement, String> myTableViewColumn__Shirt_Measurements_Shoulder;
	@FXML private TableColumn<ShirtMeasurement, String> myTableViewColumn__Shirt_Measurements_Shai;
	@FXML private TableColumn<ShirtMeasurement, String> myTableViewColumn__Shirt_Measurements_Collar;
	@FXML private TableColumn<ShirtMeasurement, String> myTableViewColumn__Shirt_Measurements_Cup;


	@FXML private TableView<PantMeasurement> myTableView_PantMeasurements;
	@FXML private TableColumn<PantMeasurement, String> myTableViewColumn__Pant_Measurements_Height;
	@FXML private TableColumn<PantMeasurement, String> myTableViewColumn__Pant_Measurements_Waist;
	@FXML private TableColumn<PantMeasurement, String> myTableViewColumn__Pant_Measurements_Seat;
	@FXML private TableColumn<PantMeasurement, String> myTableViewColumn__Pant_Measurements_Thigh;
	@FXML private TableColumn<PantMeasurement, String> myTableViewColumn__Pant_Measurements_Knee;
	@FXML private TableColumn<PantMeasurement, String> myTableViewColumn__Pant_Measurements_Bottom;


	@FXML private Label myLabel_PersonalDetails;
	@FXML private Label myLabel_PendingAmount;

	private ObservableList<String> myList_MeasurementDetail_Status;
	private CustomerDetail myCustomerDetail;

	public JFXForm_ViewMeasurements()
	{
	}

	public JFXForm_ViewMeasurements(String title, String fxmlPath)
	{
		super(title, fxmlPath);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		InitTable();
		InitShirtTable();
		InitPantTable();
		myList_MeasurementDetail_Status = FXCollections.observableArrayList();
		myList_MeasurementDetail_Status.add("Completed");
		myList_MeasurementDetail_Status.add("Pending");
		myTableViewColumn__Measurements_Item.setCellValueFactory(cellData -> cellData.getValue().GetSelectItem());
		myTableViewColumn__Measurements_Type.setCellValueFactory(cellData -> cellData.getValue().GetSelectType());
		myTableViewColumn_Measurements_SubType.setCellValueFactory(cellData -> cellData.getValue().GetSelectSubType());
		myTableViewColumn_Measurements_Measurement
				.setCellValueFactory(cellData -> cellData.getValue().GetMeasurement());
		myTableViewColumn_Measurements_Status.setCellValueFactory(cellData -> cellData.getValue().GetStatus());
		myTableViewColumn_Measurements_Status
				.setCellFactory(ComboBoxTableCell.forTableColumn(myList_MeasurementDetail_Status));

		myTableViewColumn_Measurements_Status
				.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<MeasurementDetail, String>>()
				{
					@Override
					public void handle(CellEditEvent<MeasurementDetail, String> event)
					{
						UpdateStatus(event);
					}
				});

		PopulateMeasurementTable(myTableView_MeasurementsDetails, null);
		PopulateShirtTable(myTableView_ShirtMeasurements, null);
		PopulatePantTable(myTableView_PantMeasurements, null);
	}


	private void InitShirtTable()
	{
		myTableViewColumn__Shirt_Measurements_Height.setCellValueFactory(cellData -> cellData.getValue().GetHeight());
		myTableViewColumn__Shirt_Measurements_Chest.setCellValueFactory(cellData -> cellData.getValue().GetChest());
		myTableViewColumn__Shirt_Measurements_Front.setCellValueFactory(cellData -> cellData.getValue().GetFront());
		myTableViewColumn__Shirt_Measurements_Shoulder.setCellValueFactory(cellData -> cellData.getValue().GetShoulder());
		myTableViewColumn__Shirt_Measurements_Shai.setCellValueFactory(cellData -> cellData.getValue().GetShai());
		myTableViewColumn__Shirt_Measurements_Collar.setCellValueFactory(cellData -> cellData.getValue().GetCollar());
		myTableViewColumn__Shirt_Measurements_Cup.setCellValueFactory(cellData -> cellData.getValue().GetCup());

	}

	private void InitPantTable()
	{
		myTableViewColumn__Pant_Measurements_Height.setCellValueFactory(cellData -> cellData.getValue().GetHeight());
		myTableViewColumn__Pant_Measurements_Waist.setCellValueFactory(cellData -> cellData.getValue().GetWaist());
		myTableViewColumn__Pant_Measurements_Seat.setCellValueFactory(cellData -> cellData.getValue().GetSeat());
		myTableViewColumn__Pant_Measurements_Thigh.setCellValueFactory(cellData -> cellData.getValue().GetThigh());
		myTableViewColumn__Pant_Measurements_Knee.setCellValueFactory(cellData -> cellData.getValue().GetKnee());
		myTableViewColumn__Pant_Measurements_Bottom.setCellValueFactory(cellData -> cellData.getValue().GetBottom());

	}
	@FXML
	private void OnChanged_Status(ActionEvent e)
	{
	}

	private void UpdateStatus(CellEditEvent<MeasurementDetail, String> event)
	{
		MeasurementDetail detail = event.getRowValue();
		String newStatus = event.getNewValue();

		if (detail.GetStatus().equals(newStatus))
			return;

		detail.SetStatus(newStatus);
		myDBTable_CustomerMeasurement.UpdateRecord(detail);
	}

	private void InitTable()
	{
		myDBTable_CustomerMeasurement = (DBTable_CustomerMeasurements) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_MEASUREMENTS);

		myDBTable_CustomerBalance = (DBTable_CustomerBalance) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_BALANCE);
		myDBTable_PantMeasurements = (DBTable_PantMeasurements) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.PANT_MEASUREMENTS);
		myDBTable_ShirtMeasurements = (DBTable_ShirtMeasurements) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.SHIRT_MEASUREMENTS);



	}

	public void SetCustomerDetail(CustomerDetail customerDetail)
	{
		myCustomerDetail = customerDetail;
	}

	public void UpdateFields()
	{
		ObservableList<MeasurementDetail> measurementDetails = myDBTable_CustomerMeasurement
				.GetMeasurementDetailsList(myCustomerDetail);
		PopulateMeasurementTable(myTableView_MeasurementsDetails, measurementDetails);

		ObservableList<PantMeasurement> pantMeasurementDetails = myDBTable_PantMeasurements.GetMeasurementDetailsList(myCustomerDetail);
		PopulatePantTable(myTableView_PantMeasurements,pantMeasurementDetails);

		ObservableList<ShirtMeasurement> shirtMeasurementDetails = myDBTable_ShirtMeasurements.GetMeasurementDetailsList(myCustomerDetail);
		PopulateShirtTable(myTableView_ShirtMeasurements,shirtMeasurementDetails);


		String fullname = myCustomerDetail.GetFirstName().get() + " " + myCustomerDetail.GetLastName().get();
		String mobNo = Long.toString(myCustomerDetail.GetMobileNo().get());
		String personalDetails = fullname + " (" + mobNo + ")";

		BalanceDetails balanceDetail = myDBTable_CustomerBalance.GetBalanceDetails(myCustomerDetail);
		String pendingAmount = "0";
		try
		{
			pendingAmount = Integer.toString(balanceDetail.GetPendingAmount());
		}
		catch (Exception e)
		{
			pendingAmount = "0";
		}

		pendingAmount = "Pending Amount : " + pendingAmount;

		myLabel_PersonalDetails.setText(personalDetails);
		myLabel_PendingAmount.setText(pendingAmount);

	}

	private void PopulateMeasurementTable(TableView<MeasurementDetail> tableView,
			ObservableList<MeasurementDetail> measurementList)
	{
		tableView.setItems(measurementList);
	}

	private void PopulateShirtTable(TableView<ShirtMeasurement> tableView,ObservableList<ShirtMeasurement> shirtMeasurementList)
	{
		tableView.setItems(shirtMeasurementList);
	}
	private void PopulatePantTable(TableView<PantMeasurement> tableView,ObservableList<PantMeasurement> pantMeasurementList)
	{
		tableView.setItems(pantMeasurementList);
	}

	public FormType GetType()
	{
		return FormType.VIEW_MEASUREMENT;
	}

}

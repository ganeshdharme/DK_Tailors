package form.jfx.viewTransactions;

import java.net.URL;
import java.util.ResourceBundle;

import database.table.DB_TableManager;
import database.table.DB_TableNames;
import database.table.customerBalance.BalanceDetails;
import database.table.customerBalance.DBTable_CustomerBalance;
import database.table.customerMeasurements.DBTable_CustomerMeasurements;
import database.table.customerMeasurements.MeasurementDetail;
import database.table.customerTransaction.DBTable_CustomerTransaction;
import database.table.customerTransaction.TransactionDetail;
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

public class JFXForm_ViewTransactions extends JFXForm implements Initializable
{

	private DBTable_CustomerMeasurements myDBTable_CustomerMeasurement;
	private DBTable_CustomerBalance myDBTable_CustomerBalance;
	private DBTable_CustomerTransaction myDBTable_CustomerTransaction;

	@FXML private Label myLabel_PersonalDetails;
	@FXML private Label myLabel_PendingAmount;
	@FXML private TableView<TransactionDetail> myTableView_TransactionsDetails;
	@FXML private TableColumn<TransactionDetail, String> myTableViewColumn_Transaction_Date;
	@FXML private TableColumn<TransactionDetail, String> myTableViewColumn_Transaction_Status;
	@FXML private TableColumn<TransactionDetail, Integer> myTableViewColumn_Transaction_Amount;

	// @FXML private TableView<MeasurementDetail>
	// myTableView_MeasurementsDetails;
	// @FXML private TableColumn<MeasurementDetail, String>
	// myTableViewColumn__Measurements_Item;
	// @FXML private TableColumn<MeasurementDetail, String>
	// myTableViewColumn__Measurements_Type;
	// @FXML private TableColumn<MeasurementDetail, String>
	// myTableViewColumn_Measurements_SubType;
	// @FXML private TableColumn<MeasurementDetail, String>
	// myTableViewColumn_Measurements_Measurement;
	// @FXML private TableColumn<MeasurementDetail, String>
	// myTableViewColumn_Measurements_Status;

	private ObservableList<String> myList_MeasurementDetail_Status;
	private CustomerDetail myCustomerDetail;

	public JFXForm_ViewTransactions()
	{
	}

	public JFXForm_ViewTransactions(String title, String fxmlPath)
	{
		super(title, fxmlPath);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		InitTable();

		myTableViewColumn_Transaction_Date.setCellValueFactory(cellData -> cellData.getValue().GetDateInString());
		myTableViewColumn_Transaction_Status.setCellValueFactory(cellData -> cellData.getValue().GetStatus());
		myTableViewColumn_Transaction_Amount
				.setCellValueFactory(cellData -> cellData.getValue().GetAmount().asObject());

		// myList_MeasurementDetail_Status =
		// FXCollections.observableArrayList();
		// myList_MeasurementDetail_Status.add("Completed");
		// myList_MeasurementDetail_Status.add("Pending");
		// myTableViewColumn__Measurements_Item.setCellValueFactory(cellData ->
		// cellData.getValue().GetSelectItem());
		// myTableViewColumn__Measurements_Type.setCellValueFactory(cellData ->
		// cellData.getValue().GetSelectType());
		// myTableViewColumn_Measurements_SubType.setCellValueFactory(cellData
		// -> cellData.getValue().GetSelectSubType());
		// myTableViewColumn_Measurements_Measurement
		// .setCellValueFactory(cellData ->
		// cellData.getValue().GetMeasurement());
		// myTableViewColumn_Measurements_Status.setCellValueFactory(cellData ->
		// cellData.getValue().GetStatus());
		// myTableViewColumn_Measurements_Status
		// .setCellFactory(ComboBoxTableCell.forTableColumn(myList_MeasurementDetail_Status));

		// myTableViewColumn_Measurements_Status
		// .setOnEditCommit(new
		// EventHandler<TableColumn.CellEditEvent<MeasurementDetail, String>>()
		// {
		// @Override
		// public void handle(CellEditEvent<MeasurementDetail, String> event)
		// {
		// UpdateStatus(event);
		// }
		// });

		// PopulateMeasurementTable(myTableView_MeasurementsDetails, null);
		PopulateTransactionTable(myTableView_TransactionsDetails, null);
	}

	@FXML
	private void OnChanged_Status(ActionEvent e)
	{
	}

	// private void UpdateStatus(CellEditEvent<MeasurementDetail, String> event)
	// {
	// MeasurementDetail detail = event.getRowValue();
	// String newStatus = event.getNewValue();
	//
	// if (detail.GetStatus().equals(newStatus))
	// return;
	//
	// detail.SetStatus(newStatus);
	// myDBTable_CustomerMeasurement.UpdateRecord(detail);
	// }

	private void InitTable()
	{
		myDBTable_CustomerTransaction = (DBTable_CustomerTransaction) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_TRANSACTIONS);

		myDBTable_CustomerMeasurement = (DBTable_CustomerMeasurements) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_MEASUREMENTS);

		myDBTable_CustomerBalance = (DBTable_CustomerBalance) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_BALANCE);
	}

	public void SetCustomerDetail(CustomerDetail customerDetail)
	{
		myCustomerDetail = customerDetail;
	}

	public void UpdateFields()
	{
		ObservableList<TransactionDetail> transactionDetails = myDBTable_CustomerTransaction
				.GetTransactionDetailsList(myCustomerDetail);

		PopulateTransactionTable(myTableView_TransactionsDetails, transactionDetails);

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

	private void PopulateTransactionTable(TableView<TransactionDetail> tableView,
			ObservableList<TransactionDetail> transactionList)
	{
		tableView.setItems(transactionList);
	}

	private void PopulateMeasurementTable(TableView<MeasurementDetail> tableView,
			ObservableList<MeasurementDetail> measurementList)
	{
		tableView.setItems(measurementList);
	}

	public FormType GetType()
	{
		return FormType.VIEW_TRANSACTIONS;
	}

}

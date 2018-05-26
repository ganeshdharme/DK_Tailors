package form.jfx.main.oldCustomer;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import database.Database;
import database.db_manager.DatabaseManager;
import database.table.DB_TableManager;
import database.table.DB_TableNames;
import database.table.customerBalance.DBTable_CustomerBalance;
import database.table.customerDetails.CustomerDetails;
import database.table.customerDetails.DBTable_CustomerDetails;
import database.table.customerMeasurements.DBTable_CustomerMeasurements;
import form.jfx.main.CustomerDetail;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class JFXMLController_OldCustomer implements Initializable
{
	@FXML private StackPane acContent;

	@FXML private TableView<CustomerDetail> myCustomerTableView;
	@FXML private TableColumn<CustomerDetail, Integer> myTcId;
	@FXML private TableColumn<CustomerDetail, String> myTcFirstName;

	@FXML private TableColumn<CustomerDetail, String> myTcLastName;
	@FXML private TableColumn<CustomerDetail, Long> myTcMobileNo;

	@FXML private TableColumn<CustomerDetail, String> myTcEmail;

	@FXML private TableColumn<CustomerDetail, Integer> myTcAge;
	@FXML private TableColumn<CustomerDetail, String> myTcGender;
	@FXML private TableColumn<CustomerDetail, String> myTcAddress;

	@FXML private TextField mySearchTxt;

	@FXML private Button myAddNewCustomer;
	@FXML private Button myUpdateCustomer;
	@FXML private Button myDeleteCustomer;
	@FXML private Button btnRefresh;

	public ObservableList<CustomerDetail> GetAllCustomerDetail() throws ClassNotFoundException
	{
		// String sql = "select * from CustomerDetail";
		try
		{
			Database db = DatabaseManager.GetInstance().GetDatabase();
			ResultSet rs = db.ExecuteQuery("select * from CustomerDetails;");
			// DbUtils.resultSetToTableModel(rs);

			ObservableList<CustomerDetail> customerList = GetAllCustomerRecords(rs);
			return customerList;
		}

		catch (Exception ex)
		{

		}
		return null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		try
		{
			myTcId.setCellValueFactory(cellData -> cellData.getValue().GetId().asObject());
			myTcFirstName.setCellValueFactory(cellData -> cellData.getValue().GetFirstName());
			myTcLastName.setCellValueFactory(cellData -> cellData.getValue().GetLastName());
			myTcMobileNo.setCellValueFactory(cellData -> cellData.getValue().GetMobileNo().asObject());
			myTcEmail.setCellValueFactory(cellData -> cellData.getValue().GetEmail());
			myTcAge.setCellValueFactory(cellData -> cellData.getValue().GetAge().asObject());
			myTcGender.setCellValueFactory(cellData -> cellData.getValue().GetGender());
			myTcAddress.setCellValueFactory(cellData -> cellData.getValue().GetAddress());
			ObservableList<CustomerDetail> CustomerDetails = GetAllCustomerDetail();
			PopulateTable(CustomerDetails);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

	}

	private ObservableList<CustomerDetail> GetAllCustomerRecords(ResultSet rs)
	{
		try
		{

			ObservableList<CustomerDetail> customer = FXCollections.observableArrayList();
			while (rs.next())
			{
				CustomerDetail cs = new CustomerDetail();
				cs.SetId(rs.getInt("CustomerId"));
				cs.SetFirstName(rs.getString("FirstName"));
				cs.SetLastName(rs.getString("LastName"));
				cs.SetMobileNo(rs.getLong("MobNo"));
				cs.SetEmail(rs.getString("Email"));
				cs.SetAge(rs.getInt("Age"));
				cs.SetGender(rs.getString("Gender"));
				cs.SetAddress(rs.getString("Address"));
				customer.add(cs);
			}

			return customer;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return null;
	}

	public void PopulateTable(ObservableList<CustomerDetail> customer)
	{
		myCustomerTableView.setItems(customer);
	}

	public ObservableList<CustomerDetail> GetSpecificCustomerDetail(String query) throws ClassNotFoundException
	{
		// String sql = "select * from CustomerDetail";
		try
		{
			Database db = DatabaseManager.GetInstance().GetDatabase();
			String sql = "select * from CustomerDetails where FirstName like '%" + query + "%' or LastName like '%"
					+ query + "%' or MobNo like '" + query + "%' ;";
			ResultSet rs = db.ExecuteQuery(sql);
			ObservableList<CustomerDetail> customerList = GetAllCustomerRecords(rs);
			return customerList;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}

	}

	@FXML
	private void OnKeyPressed() throws ClassNotFoundException
	{
		myTcId.setCellValueFactory(cellData -> cellData.getValue().GetId().asObject());
		myTcFirstName.setCellValueFactory(cellData -> cellData.getValue().GetFirstName());
		myTcLastName.setCellValueFactory(cellData -> cellData.getValue().GetLastName());
		myTcMobileNo.setCellValueFactory(cellData -> cellData.getValue().GetMobileNo().asObject());
		myTcEmail.setCellValueFactory(cellData -> cellData.getValue().GetEmail());
		myTcAge.setCellValueFactory(cellData -> cellData.getValue().GetAge().asObject());
		myTcGender.setCellValueFactory(cellData -> cellData.getValue().GetGender());
		myTcAddress.setCellValueFactory(cellData -> cellData.getValue().GetAddress());
		String key = mySearchTxt.getText();
		// key = key + event.getText();
		if (!(key.equals("")))
		{
			ObservableList<CustomerDetail> CustomerDetails = GetSpecificCustomerDetail(key);
			PopulateTable(CustomerDetails);
		}
		else
		{
			ObservableList<CustomerDetail> CustomerDetails = GetAllCustomerDetail();
			PopulateTable(CustomerDetails);
		}
	}

	@FXML
	private void OnMousePressed(MouseEvent event)
	{
		// StringProperty message =
		// myCustomerTableView.getSelectionModel().getSelectedItem().GetFirstName();
		// JOptionPane.showMessageDialog(null, message.get());
	}

	@FXML
	private void OnClickRefreshbtn()
	{
		try
		{
			ObservableList<CustomerDetail> CustomerDetails = GetAllCustomerDetail();
			PopulateTable(CustomerDetails);
		}
		catch (Exception ex)
		{

		}
	}

	@FXML
	private void OnClickAddCustomer()
	{
		try
		{
			FXMLLoader fXMLLoader = new FXMLLoader();
			fXMLLoader
					.load(getClass().getResource("/form/jfx/main/newCustomer/JFXMLForm_NewCustomer.fxml").openStream());

			AnchorPane acPane = fXMLLoader.getRoot();
			acPane.getStylesheets()
					.add(getClass().getResource("../../../../../res/style/MainStyle.css").toExternalForm());
			acContent.getChildren().clear();
			acContent.getChildren().add(acPane);

		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}

	@FXML
	private void OnClickDeleteCustomer()
	{
		CustomerDetail customer = myCustomerTableView.getSelectionModel().getSelectedItem();
		String name = customer.GetFirstName().get();
		String message = "Are you sure you want to delete customer : " + name;
		int choice = JOptionPane.showConfirmDialog(null, message);
		switch (choice)
		{
		case JOptionPane.YES_OPTION:
			DeleteCustomer(customer);
			break;
		case JOptionPane.NO_OPTION:
		case JOptionPane.CANCEL_OPTION:
			break;
		}
	}

	private boolean DeleteCustomer(CustomerDetail customer)
	{

		String condition = "CustomerId = " + customer.GetId().get();

		// delete form CustomerDetails
		DBTable_CustomerDetails table_cd = (DBTable_CustomerDetails) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_DETAILS);
		boolean success = table_cd.DeleteRecord(condition);
		assert success : "Unable to delete from table: " + table_cd.GetName();

		// delete form CustomerMeasurements
		DBTable_CustomerMeasurements table_cm = (DBTable_CustomerMeasurements) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_MEASUREMENTS);
		success = table_cm.DeleteRecord(condition);
		assert success : "Unable to delete from table: " + table_cd.GetName();

		// delete form CustomerBalance
		DBTable_CustomerBalance table_cb = (DBTable_CustomerBalance) DB_TableManager.GetInstance()
				.GetTable(DB_TableNames.CUSTOMER_BALANCE);
		success = table_cb.DeleteRecord(condition);
		assert success : "Unable to delete from table: " + table_cd.GetName();

		return true;
	}

	@FXML
	private void OnClickUpdateCustomer()
	{

	}

}

package form.manager;

import java.util.HashMap;

import form.jfx.JFXForm;
import form.jfx.JFXForm.FormType;
import form.jfx.customerMeasurement.JFXForm_NewMeasurement;
import form.jfx.main.JFXForm_Main;
import form.jfx.updateCustomerDetails.JFXForm_UpdateCustomerDetails;
import form.jfx.updateCustomerMeasurements.JFXForm_UpdateMeasurement;
import form.jfx.viewMeasurements.JFXForm_ViewMeasurements;
import form.jfx.viewTransactions.JFXForm_ViewTransactions;
import javafx.stage.Stage;

// singleton
public class FormManager
{
	private HashMap<FormType, JFXForm> myForms;

	private Stage myPrimaryStage;
	private static FormManager myInstance = null;

	public FormManager()
	{
	}

	public void Init()
	{
		InitializeForms();
	}

	private void InitializeForms()
	{
		myForms = new HashMap<FormType, JFXForm>();
		JFXForm form = new JFXForm_Main("RK Tailors", "/form/jfx/main/JFXML_Main.fxml");
		form.SetMaximized(false);
		form.Show();

		AddForm(new JFXForm_NewMeasurement("Add New Measurement",
				"/form/jfx/customerMeasurement/JFXMLForm_NewMeasurement.fxml").SetResizable(false));

		AddForm(new JFXForm_UpdateCustomerDetails("Update Customer Details",
				"/form/jfx/updateCustomerDetails/JFXML_UpdateCustomerDetails.fxml").SetResizable(false));

		AddForm(new JFXForm_UpdateMeasurement("Update Measurements",
				"/form/jfx/updateCustomerMeasurements/JFXML_UpdateMeasurement.fxml").SetResizable(false));

		AddForm(new JFXForm_ViewMeasurements("View Measurements",
				"/form/jfx/viewMeasurements/JFXML_ViewMeasurements.fxml").SetResizable(false));

		AddForm(new JFXForm_ViewTransactions("View Transactions",
				"/form/jfx/viewTransactions/JFXML_ViewTransactions.fxml").SetResizable(false));
	}

	public void AddForm(JFXForm newForm)
	{
		FormType type = newForm.GetType();
		myForms.put(type, newForm);
	}

	public void ShowForm(FormType type)
	{
		myForms.get(type).Show();
	}

	public void HideForm(FormType type)
	{
		myForms.get(type).Hide();
	}

	public void SetPrimaryStage(Stage primaryStage)
	{
		myPrimaryStage = primaryStage;
	}

	public Stage GetPrimaryStage()
	{
		return myPrimaryStage;
	}

	public JFXForm GetForm(FormType type)
	{
		return myForms.get(type);
	}

	public static FormManager GetInstance()
	{
		if (myInstance == null)
			myInstance = new FormManager();

		return myInstance;
	}

}

package form.jfx;

import java.io.IOException;

import form.manager.FormManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class JFXForm
{

	public enum FormType
	{
		MAIN_FORM, VIEW_MEASUREMENT, ADD_CUSTOMER_MEASUREMENT, UPDATE_CUSTOMER_DETAILS, UPDATE_CUSTOMER_MEASUREMENTS, VIEW_TRANSACTIONS, INVALID,
	}

	protected Stage myStage;
	protected Parent myParent;
	protected Scene myScene;
	protected String myFXMLPath;
	protected String myTitle;
	protected FXMLLoader myLoader;

	public JFXForm()
	{
	}

	public JFXForm(String title, String fxmlPath)
	{
		myTitle = title;
		myFXMLPath = fxmlPath;

		try
		{
			myLoader = new FXMLLoader();
			myLoader.setLocation(getClass().getResource(fxmlPath));
			myLoader.load();
			myParent = myLoader.getRoot();
			myScene = new Scene(myParent);
			myScene.setFill(new Color(0, 0, 0, 0));
			myStage = new Stage();
			myStage.setTitle(myTitle);
			myStage.setScene(myScene);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	protected JFXForm GetForm(FormType type)
	{
		return FormManager.GetInstance().GetForm(type);
	}

	public void SetFullScreen(boolean b)
	{
		myStage.setFullScreen(b);
	}

	protected void ShowForm(FormType type)
	{
		FormManager.GetInstance().ShowForm(type);
	}

	protected void HideForm(FormType type)
	{
		FormManager.GetInstance().HideForm(type);
	}

	public void Show()
	{
		myStage.show();
	}

	public void Hide()
	{
		myStage.hide();
	}

	public FormType GetType()
	{
		return FormType.INVALID;
	}

	public String GetTitle()
	{
		return myTitle;
	}

	public void SetMaximized(boolean b)
	{
		myStage.setMaximized(b);
	}

	public JFXForm SetResizable(boolean resizable)
	{
		myStage.setResizable(resizable);
		return this;
	}

	public JFXForm SetAlwaysOnTop(boolean alwayssOnTop)
	{
		myStage.setAlwaysOnTop(alwayssOnTop);
		return this;
	}

	public FXMLLoader GetLoader()
	{
		return myLoader;
	}

	public String GetFXMLPath()
	{
		return myFXMLPath;
	}
}

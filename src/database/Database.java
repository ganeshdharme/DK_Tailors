package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import logger.Logger;

public class Database
{
	private String myName;
	private Connection myConnection;

	public Database(String name)
	{
		myName = name;
	}

	public ResultSet ExecuteQuery(String sql)
	{
		ResultSet rs = null;
		try
		{
			Statement stmt = myConnection.createStatement();
			rs = stmt.executeQuery(sql);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		Logger.Log(sql);
		return rs;
	}

	public boolean ExecuteUpdate(String sql)
	{
		try
		{
			Statement stmt = myConnection.createStatement();
			stmt.executeUpdate(sql);
		}
		catch (SQLException e)
		{
			Logger.Log(sql);
			e.printStackTrace();
			return false;
		}

		Logger.Log(sql);
		return true;
	}

	public boolean OpenConnection(int port, String username, String password)
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			myConnection = DriverManager.getConnection("jdbc:mysql://localhost:" + port, username, password);
			Logger.Log("Connection started on PORT: " + port + ", USERNAME: " + username + ", PASSWORD: " + password);

			CreateDatabase();
		}
		catch (Exception e)
		{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR!");
			alert.setHeaderText(e.getMessage());
			alert.showAndWait();
		}

		return false;
	}

	public void CloseConnection()
	{
		try
		{
			myConnection.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private void CreateDatabase()
	{
		String sql = "create database if not exists " + myName + ";";
		ExecuteUpdate(sql);
		sql = "use " + myName + ";";
		ExecuteUpdate(sql);
	}

	public Connection GetConnection()
	{
		return myConnection;
	}

	public String GetName()
	{
		return myName;
	}

}

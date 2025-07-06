package com.jump.capstone.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionManager {
	//ADD IN DATABASE INFOMATION TO THIS
    private static final String URL = "";
	private static final String USERNAME = "";
	private static final String PASSWORD = "";

    private static Connection connection = null;

    private static void makeConnection() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.cj.jdbc.Driver");
		
		connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		if (connection == null) {
			makeConnection();
		}

		return connection;
	}

	public static void closeConnection() throws SQLException{
		if (connection != null){
			connection.close();
		}
		
	}
}

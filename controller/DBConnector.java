package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnector {
	private static final String DATABASE_NAME = "TestDB";
	private static final String JDBC_DRIVER_CLASS_STRING = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String CONNECTION_URL = "jdbc:sqlserver://localhost;"
			+ "database=" + DATABASE_NAME + ";"
			+ "integratedSecurity=true;"; 
	
	public static Connection getConnection() {
		try {
			Class.forName(JDBC_DRIVER_CLASS_STRING);
			Connection connection = DriverManager.getConnection(CONNECTION_URL);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

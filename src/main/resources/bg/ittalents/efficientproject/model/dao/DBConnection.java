package bg.ittalents.efficientproject.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	final String DB_IP = "127.0.0.1";
	final String DB_PORT = "3306";
	final String DB_DBNAME = "efficientproject";
	final String DB_USER = "root";
	final String DB_PASS = "root";
	
	private static DBConnection instance;
	private Connection con;
	
	private DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found or failed to load. Check your libraries");
			e.printStackTrace();
		}
		
		try {
			con = DriverManager.getConnection("jdbc:mysql://" + DB_IP + ":" + DB_PORT + "/" + DB_DBNAME, DB_USER, DB_PASS);
		} catch (SQLException e) {
			System.out.println("ops!");
			e.printStackTrace();
		}
	}
	
	public Connection getCon() {
		return con;
	}
	
	public void closeConnection() {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	static synchronized DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}
	
		return instance;
	}
}


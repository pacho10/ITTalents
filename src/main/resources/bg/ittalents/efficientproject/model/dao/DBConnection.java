package bg.ittalents.efficientproject.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static final String DB_PASS = "root";
	private static final String DB_USER = "root";
	private static final String DB_NAME = "efficientproject";
	private static final String DB_SSL = "useSSL=false";
	private static final String DB_PORT = "3306";
	private static final String DB_HOST = "127.0.0.1";

	private static DBConnection instance = null;
	private Connection con;

	DBConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			this.con = DriverManager.getConnection(
					"jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?" + DB_SSL, DB_USER, DB_PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static DBConnection getInstance() {
		synchronized (DBConnection.class) {
			if (instance == null) {
				instance = new DBConnection();
			}
			return instance;
		}
	}

	public Connection getCon() {
		return con;
	}
}


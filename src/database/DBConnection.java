package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;

public class DBConnection {
	
	private static final String HOST = "FILLIN HOST";	//192.168.68.? when using Fritzbox and DHCP on raspberry in offline mode
	private static final String USERNAME = "FILLIN USERNAME";
	private static final String PASSWORD = "FILLIN PASSWORD";
	private static final String DATABASE = "FILLIN DATABASE";
	private static Connection conn = null;
	
	static {
		loadDriver();
		connectDB();
	}
	
	private static void loadDriver() {
		try {
			Driver.class.newInstance();
			System.out.println("Driver loaded.");
		} catch (Exception e) {
			System.out.println("Exception at driver loading:\n");
			e.printStackTrace();
		}
	}
	
	private static void connectDB() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + DATABASE + "?useSSL=false", USERNAME, PASSWORD);
			System.out.println("Connected to DB: " + DATABASE + ".");
		} catch (SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		}
	}

	public static Connection getConnection() {
		return conn;
	}
}

package com.excilys.mars2020.cdb.persistance;
import java.sql.*;
import java.sql.DriverManager;

/**
 * access class to Mysql database
 * @author remi
 *
 */
public class MysqlConnection implements AutoCloseable{
	private static Connection connect;
	private String url= "jdbc:mysql://localhost/computer-database-db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String userName = "customer";
	private String password = "cust1234";
	
	public MysqlConnection() {
		try {
			Class.forName(driver);
			connect = DriverManager.getConnection(url, userName, password);
		}
		catch (Exception sqle) {
			sqle.printStackTrace();
		}
	}
	
	public Connection getConnect () {
		return connect;
	}
	
	/**
	 * Close the session explicitly
	 * @throws SQLException
	 */
	public synchronized void close() throws SQLException {
		if (connect != null) {
			connect.close();
		}
	}
	
	/**
	 * Called when GC destroy the object,
	 * ensure proper disconnection
	 */
	protected void finalize() throws SQLException {
		this.close();
	}
	
	
}

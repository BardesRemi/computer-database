package com.excilys.Mars2020.cdb.persistance;
import java.sql.*;
import java.sql.DriverManager;

/**
 * Singleton access class to Mysql database
 * @author remi
 *
 */
public class MysqlConnection implements AutoCloseable{
	private Connection connect;
	private static MysqlConnection db;
	private String url= "jdbc:mysql://localhost/";
	private String dbName = "computer-database-db";
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String userName = "customer";
	private String password = "cust1234";
	
	private MysqlConnection() {
		try {
			Class.forName(driver);
			this.connect = DriverManager.getConnection(url+dbName, userName, password);
		}
		catch (Exception sqle) {
			sqle.printStackTrace();
		}
	}
	
	public static synchronized MysqlConnection getDbConnection() {
		if(db==null) {
			db = new MysqlConnection();
		}
		return db;
	}
	
	public Connection getConnect () {
		return this.connect;
	}
	
	/**
	 * Close the session explicitly
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		if(connect != null) {
			connect.close();
			connect = null;
			System.out.println("Connection close !");
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

package com.excilys.mars2020.cdb.persistance;
import java.sql.*;
import java.sql.DriverManager;

/**
 * access class to Mysql database
 * @author remi
 *
 */
public class DbConnection{
	
	public static Connection getConnect () throws SQLException{
		return HikariConnection.getHikariConnection().getConnect();
	}
	
	
}

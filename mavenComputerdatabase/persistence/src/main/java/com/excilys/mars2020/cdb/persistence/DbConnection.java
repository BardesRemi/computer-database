package com.excilys.mars2020.cdb.persistence;
import java.sql.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * access class to Mysql database
 * @author remi
 *
 */
public class DbConnection{
	
	@SuppressWarnings("unused")
	private final static Logger LOGGER = LoggerFactory.getLogger(DbConnection.class);
	
	public static Connection getConnect () throws SQLException{
		return HikariConnection.getHikariConnection().getConnect();
	}
	
	
}

package com.excilys.mars2020.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

public class HikariConnection implements AutoCloseable{
	
	@SuppressWarnings("unused")
	private final static Logger LOGGER = LoggerFactory.getLogger(HikariConnection.class);
	
	private static HikariConnection hikConnect;
	
	private HikariDataSource ds;
	
	private Connection connect;
	
	public static synchronized HikariConnection getHikariConnection() {
		if (hikConnect == null) {
			hikConnect = new HikariConnection();
		}
		return hikConnect;
	}

	public Connection getConnect() throws SQLException {
		return ds.getConnection();
	}
	
	@Override
	public synchronized void close() throws SQLException {
		if (connect != null) {
			connect.close();
		}
	}
	
	protected void finalize() throws SQLException {
		this.close();
	}
}

package com.excilys.mars2020.cdb.persistance;

import java.sql.Connection;
import java.sql.SQLException;

import com.excilys.mars2020.cdb.spring.SpringConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnection implements AutoCloseable{
	
	private static HikariConnection hikConnect;
	
	private HikariDataSource ds;
	
	private Connection connect;
	
	public HikariConnection() {
		try {
			ds = SpringConfig.getContext().getBean(HikariDataSource.class);
			connect = ds.getConnection();
		}
		catch (Exception sqle) {
			sqle.printStackTrace();
		}
	}
	
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

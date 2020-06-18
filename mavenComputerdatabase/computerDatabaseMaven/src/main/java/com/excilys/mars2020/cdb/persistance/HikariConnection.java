package com.excilys.mars2020.cdb.persistance;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnection implements AutoCloseable{
	
	private static HikariConnection hikConnect;
	
	private String configFile = "/db.properties";
	
	private HikariConfig config = new HikariConfig(configFile);
	private HikariDataSource ds = new HikariDataSource(config);
	
	private Connection connect;
	
	public HikariConnection() {
		try {
			//Class.forName(driver);
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
		// TODO Auto-generated method stub
		if (connect != null) {
			connect.close();
		}
	}
	
	protected void finalize() throws SQLException {
		this.close();
	}
}

package com.excilys.mars2020.cdb.persistance;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariConnection implements AutoCloseable{
	
	private static HikariConnection hikConnect;
	
	//private String configFile = "src/main/resources/db.properties";
	
	private static HikariConfig config = new HikariConfig();
	static {
		config.setJdbcUrl( "jdbc:mysql://localhost/computer-database-db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC" );
	    config.setUsername( "customer" );
	    config.setPassword( "cust1234" );
	    config.setDriverClassName("com.mysql.cj.jdbc.Driver");
	    config.addDataSourceProperty( "cachePrepStmts" , "true" );
	    config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
	    config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
	}
	private HikariDataSource ds = new HikariDataSource(config);
	private String driver = "com.mysql.cj.jdbc.Driver";
	
	private static Connection connect;
	
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

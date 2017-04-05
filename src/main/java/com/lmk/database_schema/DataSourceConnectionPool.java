package com.lmk.database_schema;


import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class DataSourceConnectionPool {

	private BasicDataSource dataSource;
	private String driverClassName = null;  
	private String url = null;  
	private String username = null;  
	private String password = null;  
      
	private int initialSize = 3;  
	private int minIdle = 2;  
	private int maxIdle = 5;  
	private int maxWait = 1000;  
	private int maxActive = 50;
	
	private boolean removeAbandoned = true;
	private int removeAbandonedTimeout = 10;
	private boolean logAbandoned = true;
	public DataSourceConnectionPool(String driverClassName,String url,String username,String password) {
		// TODO Auto-generated constructor stub
		this.driverClassName = driverClassName;
		this.url = url;
		this.username = username;
		this.password = password;
	}
	public Connection  getConnection() throws  SQLException {  
        if (dataSource == null) {     
            initDataSource();     
        }     
        Connection conn = null;     
        if (dataSource != null) {     
            conn = dataSource.getConnection();     
        }     
        return conn;     
    }
	private void initDataSource() {
		// TODO Auto-generated method stub
		BasicDataSource bds = new BasicDataSource();  
        
        bds.setUrl(url);  
        bds.setDriverClassName(driverClassName);  
        bds.setUsername(username);  
        bds.setPassword(password);  
        bds.setInitialSize(initialSize);  
        bds.setMaxActive(maxActive);  
        bds.setMinIdle(minIdle);  
        bds.setMaxIdle(maxIdle);  
        bds.setMaxWait(maxWait);  
        bds.setLogAbandoned(logAbandoned);
        bds.setRemoveAbandoned(removeAbandoned);
        bds.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        
        dataSource = bds;  
        
	}  
	
	public void close(){
		try {
			dataSource.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

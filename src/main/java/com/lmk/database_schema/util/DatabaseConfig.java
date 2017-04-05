package com.lmk.database_schema.util;

public class DatabaseConfig {
	
	public static String getDriverClass(String type){
		if(type.toLowerCase().trim().equals("mysql")){
			return "com.mysql.jdbc.Driver";
		}
		if (type.toLowerCase().trim().equals("sqlserver")){
			return "com.microsoft.jdbc.sqlserver.SQLServerDriver";
		}
		return null;
	}
	
	public static String getURLPerfix(String type){
		if(type.toLowerCase().trim().equals("mysql")){
			return "jdbc:mysql://";
		}
		if (type.toLowerCase().trim().equals("sqlserver")){
			return "jdbc:microsoft:sqlserver://";
		}
		return null;
	}
	
}

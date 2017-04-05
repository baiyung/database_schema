package com.lmk.database_schema;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class DatabaseSchemaTest {

	private DatabaseSchema schema = null;
	@Before
	public void before(){
		schema = new DatabaseSchema("mysql", "111.207.243.70", "3606", "knowledge_base","root" , "cYz#P@ss%w0rd$868");
	}
	
	@Test
	public void testGetTableNames() {
		Set<String> tableNames = null;
		try {
			tableNames = schema.getTableNames();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String name : tableNames) {
			System.out.println(name+"|");
		}
	}
//	@Test
//	public void testGetTableSchema() {
//		String tableName = "first_category";
//		try {
//			tableNames = schema.getTableSchema(tableName)
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		for (String name : tableNames) {
//			System.out.println(name+"|");
//		}
//	}

}

package com.lmk.database_schema;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.lmk.database_schema.schema.SchemaField;
import com.lmk.database_schema.schema.TableSchema;
import com.lmk.database_schema.util.DatabaseConfig;

public class DatabaseSchema {
	
	private DataSourceConnectionPool pool = null;
	private List<TableSchema> listTableSchema =new ArrayList<TableSchema>();
	private Map<String,TableSchema> mapTableSchema = new HashMap<String, TableSchema>();
	
	
	public DatabaseSchema(String type,String IPHost,String port,String DBName,String username, String password) {
		// TODO Auto-generated constructor stub
		String driverClassName = DatabaseConfig.getDriverClass(type);
		String url = new StringBuffer(DatabaseConfig.getURLPerfix(type)).append(IPHost).append(":").append(port).append("/")
				.append(DBName).toString();
		pool = new DataSourceConnectionPool(driverClassName, url, username, password);
	}

	/**
	 * 获得所有表的结构
	 * @return
	 * @throws SQLException
	 */
	public List<TableSchema> getAllTableSchema() throws SQLException{
		if (listTableSchema.size()<1){
			Connection connection = pool.getConnection();
			fillTableSchema(connection);
			connection.close();
		}
		
		return listTableSchema;
	}
	/**
	 * 获得所有表的名称
	 * @return
	 * @throws SQLException
	 */
	public Set<String> getTableNames() throws SQLException{
		if (mapTableSchema.size()<1){
			Connection connection = pool.getConnection();
			fillTableSchema(connection);
			connection.close();
		}
		return mapTableSchema.keySet();
	}
	/**
	 * 获得某个表的结构
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public TableSchema getTableSchema(String tableName) throws SQLException{
		if (mapTableSchema.size()<1){
			Connection connection = pool.getConnection();
			fillTableSchema(connection);
			connection.close();
		}
		return mapTableSchema.get(tableName);
	}
	
	private void  fillTableSchema(Connection connection) throws SQLException{
		ResultSet rs = null;
		DatabaseMetaData dbmd = connection.getMetaData();
		
		String[] types = { "TABLE" };  
        rs = dbmd.getTables(null, null, "%", types);  
        while (rs.next()) {  
            String tableName = rs.getString("TABLE_NAME");  //表名  
            String tableType = rs.getString("TABLE_TYPE");  //表类型  
            String remarks = rs.getString("REMARKS");       //表备注  
            TableSchema tableSchema = new TableSchema(tableName, tableType, remarks);
            
            fillFieldSchema(dbmd,tableSchema);//填充表字段
            
            
            listTableSchema.add(tableSchema);
            mapTableSchema.put(tableName, tableSchema);
        }  
        return;
		
	}

	public String getTableDataDemo(String tableName,int limit ,int offset) throws SQLException{
		Connection connection = pool.getConnection();
		StringBuilder stringsql = new StringBuilder("select * from ");
		stringsql.append(tableName);
		stringsql.append(" limit ? offset ?");
		PreparedStatement ps = connection.prepareStatement(stringsql.toString()); 
		ps.setInt(1,limit); ps.setInt(2, offset);
		 
		ResultSet rs = ps.executeQuery(); 
		return ResultSetToJsonString(rs);
	}
	
	
	private JsonArray ResultSetToJsonArray(ResultSet rs) throws SQLException {
        JsonObject element = null;
        JsonArray ja = new JsonArray();
        ResultSetMetaData rsmd = null;
        String columnName, columnValue = null;
        try {
            rsmd = rs.getMetaData();
            while (rs.next()) {
                element = new JsonObject();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    columnName = rsmd.getColumnName(i + 1);
                    columnValue = rs.getString(columnName);
                    element.addProperty(columnName, columnValue);
                }
                ja.add(element);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
			rs.close();
		}
        return ja;
    }

	private  JsonObject ResultSetToJsonObject(ResultSet rs) throws SQLException {
        JsonObject element = null;
        JsonArray ja = new JsonArray();
        JsonObject jo = new JsonObject();
        ResultSetMetaData rsmd = null;
        String columnName, columnValue = null;
        try {
            rsmd = rs.getMetaData();
            while (rs.next()) {
                element = new JsonObject();
                for (int i = 0; i < rsmd.getColumnCount(); i++) {
                    columnName = rsmd.getColumnName(i + 1);
                    columnValue = rs.getString(columnName);
                    element.addProperty(columnName, columnValue);
                }
                ja.add(element);
            }
            jo.add("result", ja);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
			rs.close();
		}
        return jo;
    }

	private  String ResultSetToJsonString(ResultSet rs) {
        try {
			return ResultSetToJsonObject(rs).toString();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "Wrong Json";
    }
	
	
//	private String ConvertResultSetToJson(ResultSet resultSet){
//		// json数组  
//		   JSONArray array = new JsonArray();  
//		    
//		   // 获取列数  
//		   ResultSetMetaData metaData = resultSet.getMetaData();  
//		   int columnCount = metaData.getColumnCount();  
//		    
//		   // 遍历ResultSet中的每条数据  
//		    while (resultSet.next()) {  
//		        JsonObject jsonObj = new JsonObject();  
//		         
//		        // 遍历每一列  
//		        for (int i = 1; i <= columnCount; i++) {  
//		            String columnName =metaData.getColumnLabel(i);  
//		            String value = resultSet.getString(columnName);  
//		            jsonObj.put(columnName, value);  
//		        }   
//		        array.put(jsonObj);   
//		    }  
//		    
//		   return array.toString();  
//	}
//	
	private void fillFieldSchema(DatabaseMetaData dbmd,TableSchema tableSchema) throws SQLException {
		ResultSet rs = null;
		List<SchemaField> listSchemaField = new ArrayList<SchemaField>();
		
		/**
		 * 获取可在指定类别中使用的表列的描述。
		 * 方法原型:ResultSet getColumns(String catalog,String schemaPattern,String tableNamePattern,String columnNamePattern)
		 * catalog - 表所在的类别名称;""表示获取没有类别的列,null表示获取所有类别的列。
		 * schema - 表所在的模式名称(oracle中对应于Tablespace);""表示获取没有模式的列,null标识获取所有模式的列; 可包含单字符通配符("_"),或多字符通配符("%");
		 * tableNamePattern - 表名称;可包含单字符通配符("_"),或多字符通配符("%");
		 * columnNamePattern - 列名称; ""表示获取列名为""的列(当然获取不到);null表示获取所有的列;可包含单字符通配符("_"),或多字符通配符("%");
		 */
		rs =dbmd.getColumns(null, null, tableSchema.getTableName(), null);
		
		while(rs.next()){
			String tableCat = rs.getString("TABLE_CAT");  //表类别（可能为空）                  
            String tableSchemaName = rs.getString("TABLE_SCHEM");  //表模式（可能为空）,在oracle中获取的是命名空间,其它数据库未知     
            String tableName_ = rs.getString("TABLE_NAME");  //表名  
            String columnName = rs.getString("COLUMN_NAME");  //列名  
            int dataType = rs.getInt("DATA_TYPE");     //对应的java.sql.Types的SQL类型(列类型ID)     
            String dataTypeName = rs.getString("TYPE_NAME");  //java.sql.Types类型名称(列类型名称)
            int columnSize = rs.getInt("COLUMN_SIZE");  //列大小  
            int decimalDigits = rs.getInt("DECIMAL_DIGITS");  //小数位数 
            int numPrecRadix = rs.getInt("NUM_PREC_RADIX");  //基数（通常是10或2） --未知
            /**
             *  0 (columnNoNulls) - 该列不允许为空
             *  1 (columnNullable) - 该列允许为空
             *  2 (columnNullableUnknown) - 不确定该列是否为空
             */
            int nullAble = rs.getInt("NULLABLE");  //是否允许为null  
            String remarks = rs.getString("REMARKS");  //列描述  
            String columnDef = rs.getString("COLUMN_DEF");  //默认值  
            int charOctetLength = rs.getInt("CHAR_OCTET_LENGTH");    // 对于 char 类型，该长度是列中的最大字节数 
            int ordinalPosition = rs.getInt("ORDINAL_POSITION");   //表中列的索引（从1开始）  
            /** 
             * ISO规则用来确定某一列的是否可为空(等同于NULLABLE的值:[ 0:'YES'; 1:'NO'; 2:''; ])
             * YES -- 该列可以有空值; 
             * NO -- 该列不能为空;
             * 空字符串--- 不知道该列是否可为空
             */  
            String isNullAble = rs.getString("IS_NULLABLE");  
              
            /** 
             * 指示此列是否是自动递增 
             * YES -- 该列是自动递增的
             * NO -- 该列不是自动递增
             * 空字串--- 不能确定该列是否自动递增
             */  
            //String isAutoincrement = rs.getString("IS_AUTOINCREMENT");   //该参数测试报错    
            SchemaField field = new SchemaField(columnName, dataTypeName);
            listSchemaField.add(field);
            
            System.out.println(tableCat + " - " + tableSchemaName + " - " + tableName_ + " - " + columnName + 
            		" - " + dataType + " - " + dataTypeName + " - " + columnSize + " - " + decimalDigits + " - " 
            		+ numPrecRadix + " - " + nullAble + " - " + remarks + " - " + columnDef + " - " + charOctetLength
            		+ " - " + ordinalPosition + " - " + isNullAble ); 
            
		}
		
		tableSchema.setListFieldDetail(listSchemaField);
	}

	public void close(){
		pool.close();
	}
}

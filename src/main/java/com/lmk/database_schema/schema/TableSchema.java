package com.lmk.database_schema.schema;

import java.util.List;

public class TableSchema {
	private String tableName;
    private String tableType;
    private String remark;
    
    private List<SchemaField> listFieldDetail;
    public TableSchema(String tableName,String tableType,String remark) {
		// TODO Auto-generated constructor stub
    	this.tableName = tableName;
    	this.tableType = tableType;
    	this.remark = remark;
	}
    public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



	

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String TableName) {
        this.tableName = TableName;
    }


    public List<SchemaField> getListFieldDetail() {
        return listFieldDetail;
    }

    public void setListFieldDetail(List<SchemaField> ListFieldDetail) {
        this.listFieldDetail = ListFieldDetail;
    }

  
    
    public void printDetail(){
        
    }
}

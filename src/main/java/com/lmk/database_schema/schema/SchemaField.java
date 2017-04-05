package com.lmk.database_schema.schema;

public class SchemaField {
	private String ColumnName;
    private String Type;
    private String DefaultType;
    private int DisplaySize;
    private int Precision;
    private boolean IsAutoIncrement;
    private boolean IsNullable;
    
    public SchemaField(String columnName,String type) {
		// TODO Auto-generated constructor stub
    	this.ColumnName = columnName;
    	this.Type = type;
	}

    public String getColumnName() {
        return ColumnName;
    }

    public void setColumnName(String ColumnName) {
        this.ColumnName = ColumnName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getDefaultType() {
        return DefaultType;
    }

    public void setDefaultType(String DefaultType) {
        this.DefaultType = DefaultType;
    }


    public int getDisplaySize() {
        return DisplaySize;
    }

    public void setDisplaySize(int DisplaySize) {
        this.DisplaySize = DisplaySize;
    }

    public int getPrecision() {
        return Precision;
    }

    public void setPrecision(int Precision) {
        this.Precision = Precision;
    }

    public boolean isIsAutoIncrement() {
        return IsAutoIncrement;
    }

    public void setIsAutoIncrement(boolean IsAutoIncrement) {
        this.IsAutoIncrement = IsAutoIncrement;
    }

    public boolean isIsNullable() {
        return IsNullable;
    }

    public void setIsNullable(boolean IsNullable) {
        this.IsNullable = IsNullable;
    }
}

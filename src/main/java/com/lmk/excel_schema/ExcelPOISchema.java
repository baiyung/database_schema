package com.lmk.excel_schema;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Before;
import org.junit.Test;

import com.lmk.excel_schema.exceltojson.convert.ExcelToJsonConverter;
import com.lmk.excel_schema.exceltojson.convert.ExcelToJsonConverterConfig;
import com.lmk.excel_schema.exceltojson.pojo.ExcelWorkbook;
import com.lmk.excel_schema.exceltojson.pojo.ExcelWorksheet;
public class ExcelPOISchema {

	private String file_path;
	
	private Workbook wb = null;
	
	public ExcelPOISchema() {
		// TODO Auto-generated constructor stub
	}
	
	public ExcelPOISchema(String file_path) throws FileNotFoundException, IOException {
		this.file_path = file_path;
		InputStream inputStream = new FileInputStream(file_path);
		if (isExcel2003(file_path))  
			  {  
			     wb = new HSSFWorkbook(inputStream);  
			  }  
			  else  
			 {  
			    wb = new XSSFWorkbook(inputStream);  
			 }  
	}
	
	public int getNumOfSheet(){
		return wb.getNumberOfSheets();
	}
	
	
	
	public String getJsonDemo(String tableName,int rowLimit) throws InvalidFormatException, IOException{
		ExcelToJsonConverterConfig config = new ExcelToJsonConverterConfig();
		config.setSourceFile(file_path);
		config.setPretty(true);
		config.setRowLimit(rowLimit);
		ExcelWorkbook book = ExcelToJsonConverter.convert(config);
		String json = null;
		for (ExcelWorksheet sheet : book.getSheets()) {
			
			if(sheet.getName().toLowerCase().trim().equals(tableName)){
				json = sheet.toJson(config.isPretty());
			}
		}
		return json;
		
	}
	
	public List<String> getSheetNameList(){
		
		List<String> sheetNameList = new ArrayList<String>();
		for(int i =0;i< wb.getNumberOfSheets();i++){
			Sheet sheet = wb.getSheetAt(i);
			sheetNameList.add(sheet.getSheetName());
			
		}
		
		return sheetNameList;
	}
	
	private boolean isExcel2003(String filePath)  
	 {  
	   
	     return filePath.matches("^.+\\.(?i)(xls)$");  
	 
	 }  
}

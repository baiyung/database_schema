package com.lmk.excel_schema;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;

public class ExcelPOISchemaTest extends ExcelPOISchema {

	private String file_path = "E:\\实验室\\项目\\商站宝\\开发文档\\商品服务详情接口.xlsx";
	
	ExcelPOISchema et = null;
	
	@Before
	public void before() throws FileNotFoundException, IOException{
		et = new ExcelPOISchema(file_path);
	}
	
	@Test
	public void testGetNumOfSheet() {
		System.out.println(et.getNumOfSheet());
	}

	@Test
	public void testGetSheetNameList() {
		System.out.println(et.getSheetNameList().toString());
	}

	@Test
	public void testGetJsonDemo(){
		try {
			System.out.println(et.getJsonDemo("服务详情",3));
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

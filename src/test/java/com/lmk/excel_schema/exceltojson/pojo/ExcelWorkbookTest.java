package com.lmk.excel_schema.exceltojson.pojo;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Before;
import org.junit.Test;

import com.lmk.excel_schema.exceltojson.convert.ExcelToJsonConverter;
import com.lmk.excel_schema.exceltojson.convert.ExcelToJsonConverterConfig;

public class ExcelWorkbookTest {
	ExcelToJsonConverterConfig config = null;
	@Before
	public void before(){
		config = new ExcelToJsonConverterConfig();
	}
	
	@Test
	public void testToJson() throws InvalidFormatException, IOException {
		config.setSourceFile("E:\\实验室\\项目\\商站宝\\开发文档\\商品服务详情接口.xlsx");
		ExcelWorkbook book = ExcelToJsonConverter.convert(config);
		String json = book.toJson(config.isPretty());
		System.out.println(json);
	}

}

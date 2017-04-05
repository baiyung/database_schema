package com.lmk.csv_schema;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVFile {

	
	private String filePath;
	private String delimiter;
	private int headerIndex;
	
	
	public CSVFile(String filePath,String delimiter) {
		// TODO Auto-generated constructor stub
		this.delimiter =delimiter;
		this.filePath = filePath;
	}
	
	public void setHeaderIndex(int headerIndex){
		this.headerIndex = headerIndex;
	}
	
	public List<List<String>> getRecords(int start ,int end) {
		List<List<String>> result = new ArrayList<List<String>>();
		 
		List<String> list = null;
		
        FileReader fileReader = null;
        CSVParser csvFileParser = null;
        //创建CSVFormat（header mapping）
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withDelimiter(delimiter.charAt(0));
//	        		.withHeader(FILE_HEADER);
        try {
            //初始化FileReader object
            fileReader = new FileReader(filePath);
            //初始化 CSVParser object
            csvFileParser = new CSVParser(fileReader, csvFileFormat);
            //CSV文件records
            List<CSVRecord> csvRecords = csvFileParser.getRecords(); 
            // 
            for (int i = start; i < end; i++) {
                CSVRecord record = csvRecords.get(i);
                list = new ArrayList<String>();
                Iterator<String> iterator = record.iterator();
                //创建用户对象填入数据
                while (iterator.hasNext()){
                	list.add(iterator.next());
                }
                
                result.add(list);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvFileParser.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        return result;
    }

	
	
}

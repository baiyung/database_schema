package com.lmk.excel_schema.exceltojson;


import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.lmk.excel_schema.exceltojson.convert.ExcelToJsonConverter;
import com.lmk.excel_schema.exceltojson.convert.ExcelToJsonConverterConfig;
import com.lmk.excel_schema.exceltojson.pojo.ExcelWorkbook;

/**
 * Hello world!
 * excel-to-json

Command line utility to convert excel files (all of them) to json. Uses Apache POI (https://poi.apache.org) to convert files. I use this tool within php, since there are only implementations which are (a) too much memory consuming, (b) slow, or (c) don't support all required excel formats.

usage

java -jar excel-to-json.jar -s sourcefile [options...]

-s,--source <arg>   The source file which should be converted into json.
-?,--help           This help text.
-df,--dateFormat    The template to use for fomatting dates into strings.
-l,--rowLimit <arg>      Limit the max number of rows to read.
-n,--maxSheets <arg>     Limit the max number of sheets to read.
-o,--rowOffset <arg>     Set the offset for begin to read.
-empty              Include rows with no data in it.
-percent            Parse percent values as floats.
-pretty             To render output as pretty formatted json.

output

{
  "fileName" : "/var/foo/bar/source.xls",
  "sheets" : [ {
    "name" : "Sheet 1",
    "data" : [ [ "foo", "bar", "baz" ], [ "foo", "bar" ]],
    "maxCols" : 3,
    "maxRows" : 2
  }, {
    "name" : "Sheet 2",
    "data" : [ [ "foo", "bar" ], [ "foo", "bar" ], [ "foo" ]],
    "maxCols" : 1,
    "maxRows" : 3
  } ]
}
 *
 */
public class App {
	
	public static void main( String[] args ) throws Exception
    {
		Options options = new Options();
		options.addOption("s", "source", true, "The source file which should be converted into json.");
		options.addOption("df", "dateFormat", true, "The template to use for fomatting dates into strings.");
		options.addOption("?", "help", true, "This help text.");
		options.addOption("n", "maxSheets", true, "Limit the max number of sheets to read.");
		options.addOption("l", "rowLimit", true, "Limit the max number of rows to read.");
		options.addOption("o", "rowOffset", true, "Set the offset for begin to read.");
		options.addOption(new Option("percent", "Parse percent values as floats."));
		options.addOption(new Option("empty", "Include rows with no data in it."));
		options.addOption(new Option("pretty", "To render output as pretty formatted json."));
		options.addOption(new Option("fillColumns", "To fill rows with null values until they all have the same size."));
		
		CommandLineParser parser = new BasicParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch(ParseException e) {
			help(options);
			return; 
		}
		
		if(cmd.hasOption("?")) {
			help(options);
			return;
		}
		
		ExcelToJsonConverterConfig config = ExcelToJsonConverterConfig.create(cmd);
		String valid = config.valid();
		if(valid!=null) {
			System.out.println(valid);
			help(options);
			return;
		}
		
		ExcelWorkbook book = ExcelToJsonConverter.convert(config);
		String json = book.toJson(config.isPretty());
		System.out.println(json);
    }
	
	private static void help(Options options) {
		HelpFormatter formater = new HelpFormatter();
		formater.printHelp("java -jar excel-to-json.jar", options);
	}
}

package com.excilys.cdb.verifyers;

public class MysqlStringEscape {

	   public static String escapeStringForMySQL(String s) {
	        return s.replaceAll("\\", "\\\\")
	                .replaceAll("\b","\\b")
	                .replaceAll("\n","\\n")
	                .replaceAll("\r", "\\r")
	                .replaceAll("\t", "\\t")
	                .replaceAll("\\x1A", "\\Z")
	                .replaceAll("\\x00", "\\0")
	                .replaceAll("'", "\\'")
	                .replaceAll("\"", "\\\"");
	    }
	
}

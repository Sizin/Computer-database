package com.excilys.cdb.persistence;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestClass {
	String message = "Hello World";	
	String comparedTo = "Hello World";
	
	
    @Test
    public void testPrint() {
    	assertEquals(message,comparedTo);
    }
}

package com.excilys.cdb.app;

import org.apache.log4j.BasicConfigurator;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.excilys.cdb.persistence.ComputerCRUDTest;
import com.excilys.cdb.persistence.TestClass;

public class TestRunner {
	
	public static void main(String[] args) {
		BasicConfigurator.configure();
		Result result = JUnitCore.runClasses(ComputerCRUDTest.class);
	      for (Failure failure : result.getFailures()) {
	          failure.toString();
	       }
	      System.out.println("No failures");

	}
	
}
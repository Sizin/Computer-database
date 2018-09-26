package com.excilys.cdb.app;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import com.excilys.cdb.persistence.ComputerCRUDTest;
import com.excilys.cdb.persistence.TestClass;

public class TestRunner {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestClass.class);
	      for (Failure failure : result.getFailures()) {
	          System.out.println("okokok");
	       }
	      System.out.println("okokok");
	}
	
}

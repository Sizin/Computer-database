package com.excilys.cdb.app;
import java.sql.*;

import com.excilys.cdb.exceptions.InputException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * @author Sizin
 *
 */
public class Main {
	
	public static void main(String[] args) throws InputException {
		/* NOT NEEDED SINCE JDBC 4.0
			// 1. Loading Driver (Mysql here)
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}  
		*/
		
		Cli clientInterface = new Cli();
		
		try {
			clientInterface.start();
		}catch(SQLException e) {
			System.out.println(e);
		}

		
	}  
		

}

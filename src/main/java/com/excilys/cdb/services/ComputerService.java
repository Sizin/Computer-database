package com.excilys.cdb.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.app.menus.UpdateComputerMenu;
import com.excilys.cdb.exceptions.DateFormatException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerBuilder;
import com.excilys.cdb.persistence.ComputerDao;

import com.excilys.cdb.verifyers.LocalDateCheck;
import com.excilys.cdb.verifyers.MysqlStringEscape;

/**
 * @author Sizin
 * Computer Service Singleton class
 *
 */
public class ComputerService{

	private static ComputerService computerService 	= null;
	private static ComputerDao computerDao = null;
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	
	/**
	 * ComputerService default constructor
	 */
	private ComputerService() {
		this.computerDao 		= ComputerDao.getInstance();
	}
	
	/**
	 * Instantiate a ComputerService object if it is not instantiated 
	 * 
	 * 
	 * @return An instance of CoputerService
	 */
	public static ComputerService getInstance() {
		if(computerService == null ) {
			computerService = new ComputerService();
		}
		return computerService;
	}

	public static int getComputerCount() {
		int count = computerDao.getComputerCount();
		return count;
	}
	
	
	/**
	 * Diplay all the computers
	 *  
	 * @return a List of computers
	 */
	public static List<Computer> showComputers(int limit, int range) {
		List<Computer> computers = computerDao.getAllComputers(limit, range);
		return computers;
	}
	

	/**
	 * Displays one computer
	 * 
	 * @param id computer Id
	 * @return
	 */
	public static Optional<Computer> showComputer(int id) {		
		Computer computer = computerDao.getComputerDetails(id);
		return Optional.ofNullable(computer);
	}

	/**
	 * Creates a new computer
	 * 
	 * @param name String, the name of the computer
	 * @param introduced String (to be converted in Localdate), the introduced date
	 * @param discontinued String (to be converted in Localdate), the discontinued date date
	 * @return 
	 * @return newId long, Id of the new inserted row
	 */
	public static long createComputer(String name, String introduced, String discontinued) {
		
		System.out.println(name);
		System.out.println(introduced);
		System.out.println(discontinued);

		boolean returnToMenu = false;
		LocalDate introducedDate = null;
		LocalDate discontinuedDate = null;
		try {
			introducedDate = LocalDate.parse(introduced, formatter);
			discontinuedDate = LocalDate.parse(discontinued, formatter);
		}catch(DateTimeParseException e ) {
			System.out.println(e);
		}
		
		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		Computer computer = computerBuilder.setName(name).setIntroducedDate(introducedDate).setDiscontinuedDate(discontinuedDate).build();
		long newId = computerDao.insertComputer(computer);	
		if(newId == 0) {
			System.out.println("Error occured, please try again");
		}else {
			System.out.println("Insertion successfull, ID : " + newId);
		}
		return newId;

	}
	
	/**
	 * @throws DateFormatException 
	 * Updates one computer
	 * 
	 * @param id int id of the offer
	 * @param columnName String the column name
	 * @param val String value to update with
	 * @throws  
	 */
	public static void updateComputer(int id, String columnName, String val) throws DateFormatException {
		if (columnName == UpdateComputerMenu.INTRODUCED_DATE.getColumnToUpdate() || columnName == UpdateComputerMenu.DISCONTINUED_DATE.getColumnToUpdate()) {
			if(LocalDateCheck.isValidFormat(val)) {
				computerDao.updateComputer(id, columnName, val);
			}else {
				throw new DateFormatException();
			}
		}else if(columnName == UpdateComputerMenu.NAME.getColumnToUpdate()) {
			val = MysqlStringEscape.escapeStringForMySQL(val);
			System.out.println(val);
			computerDao.updateComputer(id, columnName, val);
		}
	}
	
	/**
	 * Deletes one computer
	 */
	public static void deleteComputer(int id) {
		computerDao.deleteComputer(id);
	}
}

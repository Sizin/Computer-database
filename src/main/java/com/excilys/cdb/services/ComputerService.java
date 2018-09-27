package com.excilys.cdb.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.excilys.cdb.app.menus.UpdateComputerMenu;
import com.excilys.cdb.exceptions.ComputerNameException;
import com.excilys.cdb.exceptions.DateFormatException;
import com.excilys.cdb.exceptions.DateRangeException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerBuilder;
import com.excilys.cdb.persistence.ComputerDao;

import com.excilys.cdb.verifyers.LocalDateCheck;
import com.excilys.cdb.verifyers.MysqlStringEscape;

/**
 * @author Sizin Computer Service Singleton class
 *
 */
public class ComputerService {

	private static ComputerService computerService = null;
	private static ComputerDao computerDao = null;

	private static Logger logger = Logger.getLogger(ComputerService.class);
	
	/**
	 * ComputerService default constructor
	 */
	private ComputerService() {
		this.computerDao = ComputerDao.getInstance();
	}

	/**
	 * Instantiate a ComputerService object if it is not instantiated
	 * 
	 * 
	 * @return An instance of CoputerService
	 */
	public static ComputerService getInstance() {
		if (computerService == null) {
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
	 * @param computerName String - Name of the computer, should be at least 2 characters
	 * @param introducedString String - Introduced date in String format, should be 'YYYY-MM-DD'
	 * @param discontinuedString String - Discontinued date in String format, should be 'YYYY-MM-DD'
	 * 
	 * @return
	 * @throws DateFormatException Thrown if provided date format is not valid
 	 * @throws DateRangeException Thrown if provided discontinued date is smaller than introduced date
	 * @throws ComputerNameException
	 */
	public static long createComputer(String computerName, String introducedString, String discontinuedString)
			throws DateFormatException, DateRangeException, ComputerNameException {
		LocalDate introducedDate = null;
		LocalDate discontinuedDate = null;

		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		// Checks computer name string format
		if(computerName != null && computerName != "") {
			computerBuilder.setName(computerName);
		}else {
			throw new ComputerNameException();
		}
		// Checks introducedString format, if valid then converts it to LocalDate
 		if (LocalDateCheck.validDate(introducedString)) { 
			introducedDate = LocalDate.parse(introducedString);
			computerBuilder.setIntroducedDate(introducedDate);
		}
 		// Checks discontinuedString format, if valid then converts it to LocalDate
		if (LocalDateCheck.validDate(discontinuedString)) { 
			discontinuedDate = LocalDate.parse(discontinuedString);
			// Checks if discontinued date is greater or equal to introduced date
			if (introducedDate != null && LocalDateCheck.isGreaterDate(introducedDate, discontinuedDate)) {
				computerBuilder.setDiscontinuedDate(discontinuedDate);
			} else if(introducedDate == null && discontinuedDate != null){	
				throw new DateRangeException();
			}
		}

		Computer computer = computerBuilder.build();
		long newId = computerDao.insertComputer(computer);
		
		if (newId == 0) {
			System.out.println("Error occured, please try again");
		} else {
			System.out.println("Insertion successfull, ID : " + newId);
		}

		return newId;
	}

	public static long assignCompanyToComputer(long computerId, long companyId) {
		long updatedComputerId = 0;
		updatedComputerId = computerDao.addCompany(computerId, companyId);
		return updatedComputerId;
	}

	/**
	 * @throws DateFormatException Updates one computer
	 * 
	 * @param id int id of the offer @param columnName String the column name @param
	 * val String value to update with @throws
	 */
	public static void updateComputer(int id, String columnName, String val) throws DateFormatException {
		if (columnName == UpdateComputerMenu.INTRODUCED_DATE.getColumnToUpdate()
				|| columnName == UpdateComputerMenu.DISCONTINUED_DATE.getColumnToUpdate()) {
			if (LocalDateCheck.isValidFormat(val)) {
				computerDao.updateComputer(id, columnName, val);
			} else {
				throw new DateFormatException();
			}
		} else if (columnName == UpdateComputerMenu.NAME.getColumnToUpdate()) {
			val = MysqlStringEscape.escapeStringForMySQL(val);
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

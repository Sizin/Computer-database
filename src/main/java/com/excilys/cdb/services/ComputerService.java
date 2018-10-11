package com.excilys.cdb.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.excilys.cdb.app.menus.UpdateComputerMenu;
import com.excilys.cdb.exceptions.ComputerNameException;
import com.excilys.cdb.exceptions.DateFormatException;
import com.excilys.cdb.exceptions.DateRangeException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDao;
import com.excilys.cdb.validators.DateValidator;
import com.excilys.cdb.validators.MysqlStringEscape;

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

	public int getComputerCount() {
		int count = computerDao.getComputerCount();
		return count;
	}

	/**
	 * Diplay all the computers
	 * 
	 * @return a List of computers
	 */
	public List<Computer> showComputers(int limit, int range) {
		List<Computer> computers = computerDao.getAllComputers(limit, range);
		return computers;
	}
	
	public List<Computer> searchComputer(int limit, int range, String word){
		List<Computer> computers = computerDao.search(limit, range, word);
		return computers;
	}

	/**
	 * Displays one computer
	 * 
	 * @param id computer Id
	 * @return
	 */
	public Computer getOneComputer(int id) {
		Computer computer = computerDao.getOne(id);
		return computer;
	}

	public long insertComputer(Computer computer) {
		long newId = computerDao.add(computer);
		return newId;
	}
	
	
	public void assignCompanyToComputer(long computerId, Company company) {
		computerDao.addCompany(computerId, company.getId());
	}

	/**
	 * @throws DateFormatException Updates one computer
	 * 
	 * @param id int id of the offer @param columnName String the column name @param
	 * val String value to update with @throws
	 */
	public void updateComputer(int id, String columnName, String val) throws DateFormatException {
		if (columnName == UpdateComputerMenu.INTRODUCED_DATE.getColumnToUpdate()
				|| columnName == UpdateComputerMenu.DISCONTINUED_DATE.getColumnToUpdate()) {
			if (DateValidator.isValidFormat(val)) {
				computerDao.updateComputer(id, columnName, val);
			} else {
				throw new DateFormatException();
			}
		} else if (columnName == UpdateComputerMenu.NAME.getColumnToUpdate()) {
			val = MysqlStringEscape.escapeStringForMySQL(val);
			computerDao.updateComputer(id, columnName, val);
		}
	}
	
	public void updateComputer(Computer computer) {
		computerDao.update(computer);
	}


	/**
	 * Deletes one computer
	 */
	public void deleteComputer(Computer computer) {
		computerDao.deleteComputer(computer);
	}
	
	public void deleteComputer(List<Computer> listComputer) {
		for(Computer computer : listComputer) {
			computerDao.deleteComputer(computer);
		}
	}
	
	
	
}

package com.excilys.cdb.cliservices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.app.menus.UpdateComputerMenu;
import com.excilys.cdb.exceptions.DateFormatException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDao;
import com.excilys.cdb.validators.ComputerValidator;
import com.excilys.cdb.validators.DateValidator;

/**
 * @author Sizin Computer Service Singleton class
 *
 */
@Service
public class ComputerService {
	
	@Autowired
	private ComputerDao computerDao;
	
	@Autowired
	private DateValidator dateValidator;
	
	@Autowired
	private ComputerValidator computerValidator;

	private static Logger logger = LoggerFactory.getLogger(ComputerService.class);
	
	public int getComputerCount() {
		int count = computerDao.getComputerCount();
		return count;
	}
	
	public int getComputerCount(String search) {
		int count = computerDao.getComputerCount(search);
		return count;
	}
	

	public List<Computer> showComputers(int limit, int range) {
		List<Computer> computers = computerDao.getAllComputers(limit, range, null);
		return computers;
	}
	
	/**
	 * Diplay all the computers
	 * 
	 * @return a List of computers
	 */
	public List<Computer> showComputers(int limit, int range, String search) {
		List<Computer> computers = computerDao.getAllComputers(limit, range, search);
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

	public void insertComputer(Computer computer) {
		computerDao.add(computer);
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
			if (dateValidator.isValidFormat(val)) {
				computerDao.updateComputer(id, columnName, val);
			} else {
				throw new DateFormatException();
			}
		} else if (columnName == UpdateComputerMenu.NAME.getColumnToUpdate()) {
			val = computerValidator.escapeStringForMySQL(val);
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

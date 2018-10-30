package com.excilys.cdb.app;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.cdb.app.menus.Menu;
import com.excilys.cdb.app.menus.UpdateComputerMenu;
import com.excilys.cdb.config.ClientInterfaceConfig;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exceptions.ComputerNameException;
import com.excilys.cdb.exceptions.DateFormatException;
import com.excilys.cdb.exceptions.DateRangeException;
import com.excilys.cdb.exceptions.InputException;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.*;

import com.excilys.cdb.services.*;
import com.excilys.cdb.validators.ComputerValidator;
import com.excilys.cdb.validators.DateValidator;

/**
 * @author Sizin
 *
 */
@Component
public class Cli {

	public Scanner scanner = new Scanner(System.in);
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private ComputerValidator computerValidator;
	@Autowired
	private DateValidator dateValidator;

	
	private final Logger logger = LoggerFactory.getLogger("Cli");
	
	
	/**
	 * Entry point of the Command Line Interface
	 * 
	 * @param args
	 * @throws SQLException
	 * @throws InputException
	 */
	public static void main(String[] args) throws SQLException, InputException {
		ApplicationContext context = new AnnotationConfigApplicationContext(ClientInterfaceConfig.class);
		Cli clientInterface = context.getBean(Cli.class);
		clientInterface.start();
	}  
	
	/**
	 * Command line interface displaying options to the user
	 * 
	 * @throws SQLException
	 * @throws InputException
	 */
	public void start() throws SQLException, InputException {

		boolean goOn = true;
		Scanner sc = new Scanner(System.in);

		do {
			Menu.getOptions();

			String n = "0";

			n = sc.nextLine();
			
			Menu choice = Menu.values()[Integer.valueOf(n)];
			
			switch (choice) {
			case EXIT:
				goOn = false;
				break;
			case DISPLAY_COMPUTERS:
				showComputers();
				break;
			case DISPLAY_COMPANIES:
				showCompanies();
				break;
			case COMPUTER_DETAIL:
				showComputer();
				break;
			case ADD_COMPUTER:
				createComputer();
				break;
			case UPDATE_COMPUTER:
				updateComputer();
				break;
			case DELETE_COMPUTER:
				deleteComputer();
				break;
			case DELETE_COMPANY:
				deleteCompany();
				break;
			default:
				throw new InputException();
			}
		} while (goOn);

		sc.close();
		System.out.println("Exiting the program...");
	}

	public void showComputers() {
		List<Computer> computers = computerService.showComputers(10,10);
		for (Computer computer : computers) {
			System.out.println(computer.toString());
		}
	}

	public void showCompanies() {
		List<Company> companies = companyService.showCompanies();
		for (Company company : companies) {
			System.out.println(company.toString());
		}
	}

	public void showComputer() {
		System.out.println("-> Enter an ID ");
		int id = scanner.nextInt();
		
		Computer computer = computerService.getOneComputer(id);
		System.out.println(computer);
	}

	public void deleteComputer() {
		System.out.println("-> Enter a computer ID to delete");
		int id = Integer.parseInt(scanner.nextLine());
	}
	
	public void deleteCompany() {
		System.out.println("-> Enter a company ID to delete");		
		
		String idString  = scanner.nextLine();
		
		CompanyDto companyDto = new CompanyDto();
		companyDto.setId(idString);

		Company company = companyMapper.toCompany(companyDto);
		
		
//		long id = Long.parseLong(scanner.nextLine());
		
//		Company company = new Company(id);
		companyService.deleteCompany(company);
		

	}

	

	public void createComputer() {
		String introduced;
		String discontinued;

		// Get name
		System.out.println("-> Enter a computer name (use \"\" if spaces)");
		String name = scanner.nextLine();
		name = Pattern.quote(name);

		boolean typeIntroduced = true;
		do {
			// Get introduced date
			System.out.println("-> Enter the introduction date (YYYY-MM-DD) or press Enter if you want to skip ");
			introduced = scanner.nextLine();

			if(!introduced.isEmpty()) {
				typeIntroduced = false;
			}
			
			
		} while (typeIntroduced);

		boolean typeDiscontinued = true;
		do {
			// Get introduced date
			System.out.println("-> Enter the discontinued date (YYYY-MM-DD) or press Enter if you want to skip");
			discontinued = scanner.nextLine();
			if(!discontinued.isEmpty()) {
				typeDiscontinued = false;
			}
			
		} while (typeDiscontinued);

		try {
			ComputerDto computerDto = new ComputerDto();
			computerValidator.validateName(name);
			computerDto.setName(name);			
			
			if(dateValidator.validDate(introduced)) {
				computerDto.setIntroduced(introduced);
				if(dateValidator.validDate(discontinued)) {
					if(computerValidator.validateDates(introduced, discontinued)) {
						computerDto.setDiscontinued(discontinued);
					}
				}
			}

			Computer computer = computerMapper.toComputer(computerDto);
			computerService.insertComputer(computer);	
		}catch(ComputerNameException cpne) {
			logger.error("Computer name contains invalid characters '[~#@*+%{}<>\\\\[\\\\]|\\\"\\\\_^]'", cpne);
		}catch(DateFormatException dfe) {
			logger.error("Date formats should be YYYY-MM-DD", dfe);
		}catch(DateRangeException dre) {
			logger.error(" Discontinued Data >= Introduced Date (null if Introduced date is null)", dre);
		}

	}
	
	public void updateComputer() {
		String columnName;
		String value;
		boolean goOn = true;
		do {
			System.out.println("-> Enter a computer ID to update");
			int id = Integer.parseInt(scanner.nextLine());
			do {

				UpdateComputerMenu.getOptions();

				String c = "0";

				if (scanner.hasNextLine()) {
					c = scanner.nextLine();
				}

				UpdateComputerMenu choice = UpdateComputerMenu.values()[Integer.valueOf(c)];

				switch (choice) {
					case EXIT:
						goOn = false;
						break;
					case NAME:
						columnName = "name";

						UpdateComputerMenu.NAME.getOrder();
						
						value = scanner.nextLine();
									
						break;
					case INTRODUCED_DATE:
						
						try {
							UpdateComputerMenu.INTRODUCED_DATE.getOrder();
							value = scanner.nextLine();
							computerService.updateComputer(id, UpdateComputerMenu.INTRODUCED_DATE.getColumnToUpdate(), value);
							
						} catch (DateFormatException e) {}

						break;
					case DISCONTINUED_DATE:
						
						try {
							columnName = "discontinued";

							UpdateComputerMenu.DISCONTINUED_DATE.getOrder();
						
							value = scanner.nextLine();

							computerService.updateComputer(id, UpdateComputerMenu.DISCONTINUED_DATE.getColumnToUpdate(), value);
						} catch (DateFormatException e) {}

						break;
					default:
						System.out.println("You didn't chose an available option");
				}

			} while (goOn);
		} while (goOn);
		
	}
	
}

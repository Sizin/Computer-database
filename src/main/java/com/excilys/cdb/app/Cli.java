package com.excilys.cdb.app;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import com.excilys.cdb.app.menus.Menu;
import com.excilys.cdb.app.menus.UpdateComputerMenu;
import com.excilys.cdb.exceptions.DateFormatException;
import com.excilys.cdb.exceptions.InputException;
import com.excilys.cdb.model.*;

import com.excilys.cdb.services.*;

/**
 * @author Sizin
 *
 */
public class Cli {

	public Scanner scanner;

	private ComputerService computerService;
	private  CompanyService companyService;

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	
	public Cli() {

		this.scanner = new Scanner(System.in);

		ComputerService computerService = ComputerService.getInstance();

		CompanyService companyService = CompanyService.getInstance();

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
			case DISPLAY_COMPUTERS:
				goOn = false;
				break;
			case DISPLAY_COMPANIES:
				showComputers();
				break;
			case COMPUTER_DETAIL:
				showCompanies();
				break;
			case ADD_COMPUTER:
				showComputer();
				break;
			case UPDATE_COMPUTER:
				createComputer();
				break;
			case DELETE_COMPUTER:
				updateComputer();
				break;
			case EXIT:
				deleteComputer();
				break;
			default:
				throw new InputException();
			}
		} while (goOn);

		sc.close();
		System.out.println("Exiting the program...");
	}

	public void showComputers() {
		List<Computer> computers = ComputerService.showComputers(10,10);
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
		
		Optional<Computer> computer = computerService.showComputer(id);
		System.out.println(computer);
	}

	public void deleteComputer() {
		System.out.println("-> Enter a computer ID to delete");
		int id = Integer.parseInt(scanner.nextLine());
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

		computerService.createComputer(name, introduced, discontinued);

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

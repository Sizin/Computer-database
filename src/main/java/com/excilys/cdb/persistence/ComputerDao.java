package com.excilys.cdb.persistence;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerBuilder;

public class ComputerDao {
	
	private static ComputerDao computerDao = null;
	
	private final static Logger logger = Logger.getLogger("CompanyDao");
	
	private ConnectionManager connection;
	private ConnectionTestManager connectionTest;
	
	private boolean runOnTestDb = false;
	
	private static final String GET = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE id=?";
	private static final String GET_ALL = "SELECT compu.id, compu.name, compu.introduced, compu.discontinued, compu.company_id, compa.name as company_name FROM computer compu LEFT JOIN company compa ON compu.id = compa.id ";
	private static final String GET_COMPUTER_AND_COMPANY = "SELECT compu.id, compa.name as company_name FROM computer compu LEFT JOIN company compa ON compu.company_id WHERE compu.id=? AND compa.id = ?;";
	private static final String GET_COUNT = "SELECT COUNT(id) as count FROM computer";
	private static final String GET_COUNT_SEARCH = "SELECT COUNT(id) as count FROM computer WHERE name LIKE ?";
	private static final String ADD = "INSERT INTO computer (name, introduced, discontinued) VALUES(?, ?, ?);";
	private static final String ADD_COMPANY = "UPDATE computer SET company_id= ? WHERE id=?";
	private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?";
	private static final String UPDATE_COLUMN = "UPDATE computer SET [*column*] = ? WHERE id=?;";
	private static final String DELETE = "DELETE FROM computer WHERE id=?;";
	private static final String DELETE_USING_COMPANY = "DELETE FROM computer WHERE company_id = ? ";
	private static final String SEARCH_LIKE = "SELECT compu.id, compu.name, compu.introduced, compu.discontinued, compu.company_id, compa.name as company_name FROM computer compu LEFT JOIN company compa ON compu.id = compa.id  WHERE compu.name LIKE ? ORDER BY compu.name ASC ";
	
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	
	private ComputerDao(boolean runOnTestDb) {
		if(runOnTestDb) {
			this.connectionTest = ConnectionTestManager.CONNECTION;
			this.runOnTestDb = true;
		}else {
			this.connection = ConnectionManager.CONNECTION;
			this.runOnTestDb = false;
		}
	}
	
	public static ComputerDao getInstance(boolean runOnTestDb) {
		if(computerDao == null && !runOnTestDb) {
			computerDao = new ComputerDao(false);
		}else if(computerDao == null && runOnTestDb) {
			computerDao = new ComputerDao(true);
		}
		return computerDao;
	}
	
	
	public int getComputerCount() {
		int rows = 0;
		try (Connection con = ( !runOnTestDb ? connection.getConnection() : connectionTest.getConnection()) ){
			Statement getCountStmt = con.createStatement();
			ResultSet getCountRs = getCountStmt.executeQuery(GET_COUNT);
			if(getCountRs.next()) {
				rows = getCountRs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		
		return rows;
	}
	
	public int getComputerCount(String search) {
		int rows = 0;
		
		try (Connection con = connection.getConnection()){
			
			PreparedStatement getCountSearchpStmt = con.prepareStatement(GET_COUNT_SEARCH);
			getCountSearchpStmt.setString(1, search);
			ResultSet getCountSearchRs = getCountSearchpStmt.executeQuery();
			
			if(getCountSearchRs.next()) {
				rows = getCountSearchRs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		
		return rows;
	}
	
	/**
	 * Retrieves all the computers from the computer table
	 * 
	 * @return List of computers
	 */
	public List<Computer> getAllComputers(int offset, int range, String search) {
		List<Computer> computers = new ArrayList<Computer>();
		
		try (Connection con = ( !runOnTestDb ? connection.getConnection() : connectionTest.getConnection()) ){
			ComputerBuilder computerBuilder = new ComputerBuilder();
			PreparedStatement getAllComputersStmt;
			
			if(search == null) {
				if(offset != 0 && range != 0) {
					getAllComputersStmt = con.prepareStatement(GET_ALL + "LIMIT ?, ?");
					
					int page = (offset == 1) ? offset : offset*range;
					
					getAllComputersStmt.setInt(1,page);
					getAllComputersStmt.setInt(2,range);
				}else {
					getAllComputersStmt = con.prepareStatement(GET_ALL);
				}
			}else {
				if(offset != 0 && range != 0) {
					getAllComputersStmt = con.prepareStatement(SEARCH_LIKE + " LIMIT ?, ?");
					
					getAllComputersStmt.setString(1, "%"+search+"%");
					
					int page = (offset == 1) ? offset : offset*range;
					
					getAllComputersStmt.setInt(2,page);
					getAllComputersStmt.setInt(3,range);
				}else {
					getAllComputersStmt = con.prepareStatement(SEARCH_LIKE);
					getAllComputersStmt.setString(1, search);
				}
			}

			ResultSet getAllComputersRs	= getAllComputersStmt.executeQuery();

			while(getAllComputersRs.next()) {
								
				String introducedString  = getAllComputersRs.getString("introduced");
				String discontinuedString = getAllComputersRs.getString("discontinued");
								
				computerBuilder.setId(getAllComputersRs.getLong("id"));
				computerBuilder.setName(getAllComputersRs.getString("name"));

				// Checking if introduced date is not null
				if (introducedString != null) {
					LocalDate introducedDate = LocalDate.parse(introducedString, formatter);
					computerBuilder.setIntroducedDate(introducedDate);
				}else {
					computerBuilder.setIntroducedDate(null);
				}
								
				// Checking if discontinued date is not null
				if(discontinuedString != null) {
					LocalDate discontinuedDate = LocalDate.parse(discontinuedString, formatter );
					computerBuilder.setDiscontinuedDate(discontinuedDate);
				}else {
					computerBuilder.setDiscontinuedDate(null);
				}
				
				Computer computer = computerBuilder.build();
				
				int companyId = getAllComputersRs.getInt("company_id");

				if (companyId != 0) {
						String companyName = getAllComputersRs.getString("company_name");
						Company company = new Company(companyId, companyName);
						computer.setCompany(company);

				}
				computers.add(computer);
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}  
		return computers;

	}

	public long addCompany(long computerId, long companyId) {
		long updatedComputerId = 0;
		
		try (Connection con = connection.getConnection()){
			PreparedStatement addCompanyPStmt = con.prepareStatement(ADD_COMPANY, Statement.RETURN_GENERATED_KEYS);
			if(companyId == 0) {
				addCompanyPStmt.setNull(1, java.sql.Types.INTEGER);
			}else {
				addCompanyPStmt.setLong(1, companyId);
			}
			

			addCompanyPStmt.setLong(2, computerId);
			addCompanyPStmt.executeUpdate();
			
			ResultSet addCompanyRs = addCompanyPStmt.getGeneratedKeys();
			
			if(addCompanyRs.next()) {
				updatedComputerId = addCompanyRs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return updatedComputerId;
	}
	
	/**
	 * Retrieves one row from computer table given a computer ID
	 * 
	 * @param id Id of the computer
	 * @return The corresponding computer
	 */
	public Computer getOne(int id) {

		Computer computer = null;
				
		try (Connection con = ( !runOnTestDb ? connection.getConnection() : connectionTest.getConnection()) ){
			ComputerBuilder computerBuilder = new ComputerBuilder();
			
			PreparedStatement getComputerPStmt = con.prepareStatement(GET);
			getComputerPStmt.setInt(1, id);
			ResultSet getComputerRs = getComputerPStmt.executeQuery();
			if(getComputerRs.next()) {
				computerBuilder.setId(getComputerRs.getInt("id"));
				computerBuilder.setName(getComputerRs.getString("name"));
				
				// Checking if introduced date is not null
				String introducedString  = getComputerRs.getString("introduced");
				if(introducedString != null) {
					LocalDate introducedDate = LocalDate.parse(introducedString, formatter);
					computerBuilder.setIntroducedDate(introducedDate);
				}
				// Checking if discontinued date is not null
				String discontinuedString = getComputerRs.getString("discontinued");
				if (discontinuedString != null) {
					LocalDate discontinuedDate = LocalDate.parse(discontinuedString, formatter);
					computerBuilder.setDiscontinuedDate(discontinuedDate);
				}
				
				computer = computerBuilder.build();
				
				int companyId = getComputerRs.getInt("company_id");
				if (companyId != 0) {
					PreparedStatement getComputerAndCompanyPStmt = con.prepareStatement(GET_COMPUTER_AND_COMPANY);
					getComputerAndCompanyPStmt.setInt(1, id);
					getComputerAndCompanyPStmt.setInt(2, companyId);
					ResultSet getComputerCompanyRs = getComputerAndCompanyPStmt.executeQuery();
					
					if(getComputerCompanyRs.next()) {
						String companyName = getComputerCompanyRs.getString("company_name");

						Company company = new Company(companyId, companyName);
						computer.setCompany(company);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return computer;
	}
	
	/**
	 * Inserts a new computer row in the computer table
	 * 
	 * @param computer object, Computer object to insert
	 * @return the id of the new inserted row
	 */
	public long add(Computer computer) {
		long id = 0;

		try (Connection con = ( !runOnTestDb ? connection.getConnection() : connectionTest.getConnection())){			
			// In order to get the last inserted ID we have to specify it here
			PreparedStatement pStmt = con.prepareStatement(ADD, Statement.RETURN_GENERATED_KEYS);
			
			pStmt.setString(1, computer.getName());
			
			if(computer.getIntroduced() == null) {
				pStmt.setNull(2, java.sql.Types.DATE);
			}else {
				pStmt.setString(2, computer.getIntroduced().toString());
			}
			
			if(computer.getDiscontinued() == null) {
				pStmt.setNull(3, java.sql.Types.DATE);
			}else {
				pStmt.setString(3, computer.getIntroduced().toString());
			}
			
			pStmt.executeUpdate();
			// Getting ID of the new row 
            ResultSet rs = pStmt.getGeneratedKeys();
            
            if(rs.next()) {
            	id = rs.getInt(1);
            }
            
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id; 
	}

	/**
	 * Updates one computer from 
	 * 
	 * @param id long, id of the computer to update
	 * @param column String, column name to update
	 * @param value String, value to update the column with
	 * @return id long, the Id of the updated row
	 */
	public long updateComputer(int id, String column,  String value) {
		
		try (Connection con = connection.getConnection()){
			//As we cant supply identifyers as PreparedStatemnt bind parameters we use a str replace to set the right column name
			// In order to get the last inserted ID we have to specify it here
			PreparedStatement pStmt = con.prepareStatement(UPDATE_COLUMN.replace("[*column*]", "`" + column +"`"), Statement.RETURN_GENERATED_KEYS);
			// Value to update with
			pStmt.setString(1, value);
			// Id of the computer to update
			pStmt.setInt(2, id);
			pStmt.executeUpdate();

			ResultSet rs = pStmt.getGeneratedKeys();
			if(rs.next()) {
				id = rs.getInt(1);
			}
			
		}catch(SQLException e ) {
			e.printStackTrace();
		}		
				
		return id;
	}
	
	/**
	 * Updates one computer from 
	 * 
	 * @param id long, id of the computer to update
	 * @param column String, column name to update
	 * @param value String, value to update the column with
	 * @return id long, the Id of the updated row
	 */
	public long update(Computer computer) {
		int id = 0;
		
		try (Connection con =  connection.getConnection()){
			
			//As we cant supply identifyers as PreparedStatemnt bind parameters we use a str replace to set the right column name
			// In order to get the last inserted ID we have to specify it here
			PreparedStatement pStmt = con.prepareStatement(UPDATE ,Statement.RETURN_GENERATED_KEYS);
			
			// Id of the computer to update
			pStmt.setString(1, computer.getName());
			
			if(computer.getIntroduced() == null) {
				pStmt.setNull(2, java.sql.Types.DATE);
			}else {
				pStmt.setString(2, computer.getIntroduced().toString());
			}

			if(computer.getDiscontinued() == null) {
				pStmt.setNull(3, java.sql.Types.DATE);
			}else {
				pStmt.setString(3, computer.getDiscontinued().toString());
			}

			if(computer.getId() != 0 ) {
				
				pStmt.setLong(4, computer.getId());
				
				pStmt.executeUpdate();

				ResultSet rs = pStmt.getGeneratedKeys();
				if(rs.next()) {
					id = rs.getInt(1);
				}
			}

		}catch(SQLException e ) {
			e.printStackTrace();
		}		
				
		return id;
		
	}
	
	

	/**
	 * Deletes one computer from the computer table given an ID
	 * 
	 * @param id Id of the computer to delete
	 */
	public void deleteComputer(Computer computer) {
		try (Connection con =  connection.getConnection()){
			PreparedStatement pStmt = con.prepareStatement(DELETE);
			pStmt.setLong(1, computer.getId());
			pStmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public static void deleteComputerUsingCompany(Long id, Connection con){
		try {
			PreparedStatement deleteUsingCompanyPStmt = con.prepareStatement(DELETE_USING_COMPANY );
			deleteUsingCompanyPStmt.setLong(1, id);
			deleteUsingCompanyPStmt.executeUpdate();
		}catch(SQLException e) {
			logger.error("Error deleting computer during delete company transaction", e);
		}

		
	}
	
	
}

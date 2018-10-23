package com.excilys.cdb.persistence;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.config.SpringJdbcConfig;
import com.excilys.cdb.model.Computer;

@Repository
public class ComputerDao {


	private final static Logger logger = LoggerFactory.getLogger("CompanyDao");
	@Autowired
	private ConnectionManager connection;
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	private ComputerRowMapper computerRowMapper;
	
	

	private static final String GET = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.id = company.id WHERE computer.id=?";
	private static final String GET_ALL = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.id = company.id ";
	private static final String GET_COUNT = "SELECT COUNT(id) as count FROM computer";
	private static final String GET_COUNT_SEARCH = "SELECT COUNT(id) as count FROM computer WHERE name LIKE ?";
	private static final String ADD = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES(?, ?, ?, ?);";
	private static final String ADD_COMPANY = "UPDATE computer SET company_id= ? WHERE id=?";
	private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ? WHERE id = ?";
	private static final String UPDATE_COLUMN = "UPDATE computer SET [*column*] = ? WHERE id=?;";
	private static final String DELETE = "DELETE FROM computer WHERE id=?;";
	private static final String DELETE_USING_COMPANY = "DELETE FROM computer WHERE company_id = ? ";
	private static final String SEARCH_LIKE = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name FROM computer LEFT JOIN company ON computer.id = company.id  WHERE computer.name LIKE ? ORDER BY computer.name ASC ";

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	
	public int getComputerCount() {
		return jdbcTemplate.queryForObject(GET_COUNT, Integer.class);
	}
	
	public int getComputerCount(String search) {
		int nbComputer;
		
		if (search == null) {
			nbComputer = jdbcTemplate.queryForObject(GET_COUNT, Integer.class);
		} else {
			nbComputer = jdbcTemplate.queryForObject(GET_COUNT_SEARCH, new Object[] {new StringBuilder("%" + search + "%").toString()},	(resultSet, rowNum) -> resultSet.getInt(1));
		}
		
		return nbComputer;
		
	}
	
	/**
	 * Retrieves all the computers from the computer table
	 * 
	 * @return List of computers
	 */
	public List<Computer> getAllComputers(int offset, int range, String search) {

		if (search == null) {
			if (offset != 0 && range != 0) {
				int page = (offset == 1) ? offset : offset * range;
				return jdbcTemplate.query(GET_ALL + "LIMIT ?, ?", new Object[] {page, range}, computerRowMapper);
			} else {
				return jdbcTemplate.query(GET_ALL, computerRowMapper);
			}
		} else {
			if (offset != 0 && range != 0) {
				int page = (offset == 1) ? offset : offset * range;
				return jdbcTemplate.query(SEARCH_LIKE + "LIMIT ?, ?", new Object[] {search, page, range}, computerRowMapper);
			} else {
				return jdbcTemplate.query(SEARCH_LIKE, new Object[] {search}, computerRowMapper);
			}
		}
	}
	
	public void addCompany(long computerId, long companyId) {
		if (companyId >= 0) {
			jdbcTemplate.update(ADD_COMPANY, new Object[] {companyId, computerId});
		}
	}
	
	/**
	 * Inserts a new computer row in the computer table
	 * 
	 * @param computer object, Computer object to insert
	 * @return the id of the new inserted row
	 */
	public void add(Computer computer) {
		jdbcTemplate.update(ADD, computer.getName(), computer.getIntroduced() == null ? null : computer.getIntroduced().toString(), computer.getDiscontinued() == null ? null : computer.getDiscontinued().toString() , computer.getCompany() == null ? null : computer.getCompany().getId());		
	}
	
	/**
	 * Updates one computer from
	 * 
	 * @param id     long, id of the computer to update
	 * @param column String, column name to update
	 * @param value  String, value to update the column with
	 * @return id long, the Id of the updated row
	 */
	public void update(Computer computer) {
		jdbcTemplate.update(UPDATE, computer.getName(), computer.getIntroduced() == null ? null : computer.getIntroduced().toString(), computer.getDiscontinued() == null ? null : computer.getDiscontinued().toString(), computer.getId());
	}
	
	/**
	 * Deletes one computer from the computer table given an ID
	 * 
	 * @param id Id of the computer to delete
	 */
	public void deleteComputer(Computer computer) {
		jdbcTemplate.update(DELETE, new Object[] {computer.getId()});
	}
	
	
	/**
	 * Retrieves one row from computer table given a computer ID
	 * 
	 * @param id Id of the computer
	 * @return The corresponding computer
	 */
	public Computer getOne(int id) {
		return (Computer)jdbcTemplate.queryForObject(GET, new Object[] {id} , computerRowMapper);
	}

	/**
	 * Updates one computer from
	 * 
	 * @param id     long, id of the computer to update
	 * @param column String, column name to update
	 * @param value  String, value to update the column with
	 * @return id long, the Id of the updated row
	 */
	public long updateComputer(int id, String column, String value) {

		try (Connection con = connection.getConnection()) {
			// As we cant supply identifyers as PreparedStatemnt bind parameters we use a
			// str replace to set the right column name
			// In order to get the last inserted ID we have to specify it here
			PreparedStatement pStmt = con.prepareStatement(UPDATE_COLUMN.replace("[*column*]", "`" + column + "`"),
					Statement.RETURN_GENERATED_KEYS);
			// Value to update with
			pStmt.setString(1, value);
			// Id of the computer to update
			pStmt.setInt(2, id);
			pStmt.executeUpdate();

			ResultSet rs = pStmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return id;
	}

}

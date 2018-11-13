package com.excilys.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.ComputerBuilder;

@Component
public class ComputerRowMapper implements RowMapper<Computer>{

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	
	@Override
	public Computer mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		Long id = resultSet.getLong("computer.id");
		computerBuilder.setId(id);
	
		String name = resultSet.getString("computer.name");
		computerBuilder.setName(name);

		LocalDate introducedDate = null;
		if(resultSet.getString("computer.introduced") != null) {
			introducedDate = LocalDate.parse(resultSet.getString("computer.introduced"), formatter);
		}
		computerBuilder.setIntroducedDate(introducedDate);
		
		
		LocalDate discontinuedDate = null;
		if(resultSet.getString("computer.discontinued") != null) {
			discontinuedDate = LocalDate.parse(resultSet.getString("computer.discontinued"), formatter);
		}
		computerBuilder.setDiscontinuedDate(discontinuedDate);
		

		Long companyId = resultSet.getLong("computer.company_id");
		String companyName = resultSet.getString("company.name");
		
		if(companyId != null && companyName != null) {
			computerBuilder.setCompany(new Company(companyId, companyName));
		}
		
//		if(companyId != null) {
//			computerBuilder.setCompany(new Company(companyId, companyName));
//		}
		

		return computerBuilder.build();
	}
}

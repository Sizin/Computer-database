package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerBuilder;



public class ComputerMapper {

	private static ComputerMapper computerMapper;
	private static CompanyMapper companyMapper;
	
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private ComputerMapper(){
		companyMapper = companyMapper.getInstance();
	}

	public static ComputerMapper getInstance() {
		if(computerMapper == null) {
			computerMapper = new ComputerMapper();
		}
		return computerMapper;	
	}
	
	public Computer toComputer(ComputerDto computerDto) {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		
		if(computerDto.getId() != null) {
			computerBuilder.setId(Long.parseLong(computerDto.getId()));
		}
		
		if(computerDto.getName() != null) {
			computerBuilder.setName(computerDto.getName());
		}
		
		if(computerDto.getIntroduced() != null && computerDto.getIntroduced() != "") {
			computerBuilder.setIntroducedDate(LocalDate.parse(computerDto.getIntroduced(), formatter));
		
			if(computerDto.getDiscontinued() != null && computerDto.getDiscontinued() != "") {
				computerBuilder.setDiscontinuedDate(LocalDate.parse(computerDto.getDiscontinued(), formatter));
			}
		}
		
		if(computerDto.getCompanyDto() != null) {
			computerBuilder.setCompany(companyMapper.toCompany(computerDto.getCompanyDto()));
		}

		Computer computer = computerBuilder.build();
		return computer;	
	}
	
	public ComputerDto toComputerDto(Computer computer) {
		ComputerDto computerDto = new ComputerDto();
		
		
		if(computer.getId() != 0) {
			computerDto.setId(String.valueOf(computer.getId()));
		}
		
		if(computer.getName() != null) {
			computerDto.setName(computer.getName());
		}
		
		if(computer.getIntroduced() != null) {
			computerDto.setIntroduced(computer.getIntroduced().toString());
		}
		
		if(computer.getDiscontinued() != null) {
			computerDto.setDiscontinued(computer.getDiscontinued().toString());
		}
		
		return computerDto;
		
	}
	
	
	
	
}

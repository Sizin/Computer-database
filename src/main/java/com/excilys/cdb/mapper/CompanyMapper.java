package com.excilys.cdb.mapper;

import java.util.ArrayList;
import java.util.List;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.CompanyBuilder;
import com.excilys.cdb.model.ComputerBuilder;

public class CompanyMapper {

	private static CompanyMapper companyMapper;
	
	private CompanyMapper() {
	}
	
	public static CompanyMapper getInstance() {
		if(companyMapper == null) {
			companyMapper = new CompanyMapper();
		}
		return companyMapper;
	}
	
	public Company toCompany(CompanyDto companyDto) {
		CompanyBuilder companyBuilder = new CompanyBuilder();

		
		if (companyDto.getId() != null) {
			companyBuilder.setId(Long.parseLong(companyDto.getId()));
		}
		
		if(companyDto.getName() != null) {
			companyBuilder.setName(companyDto.getName());
		}
		
		return companyBuilder.build();
	}
	
	public CompanyDto toCompanyDto(Company company) {
		return null;
	}
	
	public List<Company> toCompanies(List<CompanyDto> companiesDto){
		List<Company> companies = new ArrayList<Company>();
		
		for (CompanyDto companyDto : companiesDto) {
			companies.add(toCompany(companyDto));
		}
		return companies;
		
		
	}
	
	
}

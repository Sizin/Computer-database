package com.excilys.cdb.binding;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.CompanyDto;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.CompanyBuilder;

@Component
public class CompanyMapper {

//	private CompanyMapper companyMapper;
	
	private CompanyMapper() {
	}
	
//	public static CompanyMapper getInstance() {
//		if(companyMapper == null) {
//			companyMapper = new CompanyMapper();
//		}
//		return companyMapper;
//	}
	
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
		CompanyDto companyDto = new CompanyDto();
		
		if(company.getId() > 0) {
			companyDto.setId(String.valueOf(company.getId()));
		}
		
		if(company.getName() != null) {
			companyDto.setName(company.getName());
		}
		
		return companyDto;
	}
	
	public List<Company> toCompanies(List<CompanyDto> companiesDto){
		List<Company> companies = new ArrayList<Company>();
		
		for (CompanyDto companyDto : companiesDto) {
			companies.add(toCompany(companyDto));
		}
		return companies;
		
		
	}
	
	
}
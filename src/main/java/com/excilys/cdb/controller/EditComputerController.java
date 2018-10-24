package com.excilys.cdb.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exceptions.ComputerNameException;
import com.excilys.cdb.exceptions.DateFormatException;
import com.excilys.cdb.exceptions.DateRangeException;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.validators.ComputerValidator;

@Controller
@RequestMapping
public class EditComputerController {
	final static Logger logger = LoggerFactory.getLogger(AddComputerController.class);
	
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private ComputerValidator computerValidator;
	
	@GetMapping("/editComputer")
	public String getEditComputer(@RequestParam Map<String,String> requestParams, Model model) {
		List<Company> companies = companyService.showCompanies();
		
		if(requestParams.get("computerId") != null && requestParams.get("computerId") != "") {
			int computerId = Integer.parseInt(requestParams.get("computerId"));
			Computer computer = computerService.getOneComputer(computerId);
			ComputerDto computerDto = computerMapper.toComputerDto(computer);
			
			model.addAttribute("computer", computerDto);
			model.addAttribute("companies", companies);
			return "editComputer";
		}else {
			return "500";
		}
	}
	
	@PostMapping("/editComputer")
	public String postEditComputer(@RequestParam Map<String, String> requestParams, Model model) {
		String computerIdString = requestParams.get("computerId");
		if(computerIdString != null && computerIdString != "") {
			Computer computer = new Computer();
			ComputerDto computerDto = new ComputerDto();
			
			String computerName = requestParams.get("computerName");
			String computerIntroduced = requestParams.get("introduced");
			String computerDiscontinued = requestParams.get("discontinued");
			String companyIdString = requestParams.get("companyId");
			
			int numberOfComputer = computerService.getComputerCount();
			
			try {
				computerValidator.validateId(computerIdString, numberOfComputer);
				computerValidator.validateName(computerName);
				computerValidator.validateDates(computerIntroduced, computerDiscontinued);
				
				computerDto.setId(computerIdString);
				computerDto.setName(computerName);
				computerDto.setIntroduced(computerIntroduced);
				computerDto.setDiscontinued(computerDiscontinued);
			
				if (requestParams.get("companyId") != null && requestParams.get("companyId") != "") {
					int companyId = Integer.parseInt(requestParams.get("companyId"));
					int nbCompany = companyService.getComputerCount();
					
					if(companyId > 0 && companyId < nbCompany) {
						Company company = new Company(companyId);
						company = companyService.getCompany(company);
						computerDto.setCompany(companyMapper.toCompanyDto(company));
					}
				}
				
				computer = computerMapper.toComputer(computerDto);
				computerService.updateComputer(computer);				
			} catch (ParseException e) {
				logger.debug("Computer Id is incorredt : 0 < id < "+ numberOfComputer);
			} catch (ComputerNameException e) {
				logger.debug("Computer name format is invalid");
			} catch (DateFormatException e) {
				logger.debug("Date formats should be YYYY-MM-DD");
			} catch (DateRangeException e) {
				logger.debug(" Discontinued Data >= Introduced Date (null if Introduced date is null)");
			}
		}else {
			return "500";
		}
		return "redirect:/Dashboard";
	}
}

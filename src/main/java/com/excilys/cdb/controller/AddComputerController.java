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
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.excilys.cdb.validators.DateValidator;

@Controller
@RequestMapping
public class AddComputerController {
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
	@Autowired
	private DateValidator dateValidator;

	@GetMapping("/addComputer")
	public String getAddComputer(@RequestParam Map<String, String> requestParams, Model model) {
		List<Company> companies = new ArrayList<Company>();
		companies = companyService.showCompanies();
		model.addAttribute("companies", companies);
		model.addAttribute("computer", new Computer());
		return "addComputer";
	}

	@PostMapping("/addComputer")
	public String postAddComputer(@ModelAttribute("computer")Computer computer, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			return "500";
		}
		computerService.insertComputer(computer);
		return "redirect:/Dashboard";
	}
	
//	@PostMapping("/addComputer")
//	public String postAddComputer(@RequestParam Map<String, String> requestParams) {
//
//		String computerName = requestParams.get("computerName");
//		String introducedString = requestParams.get("introduced");
//		String discontinuedString = requestParams.get("discontinued");
//
//		ComputerDto computerDto = new ComputerDto();
//
//		try {
//			computerValidator.validateName(computerName);
//			computerDto.setName(computerName);
//
//			if (dateValidator.validDate(introducedString)) {
//				computerDto.setIntroduced(introducedString);
//				if (dateValidator.validDate(discontinuedString)) {
//					if (computerValidator.validateDates(introducedString, discontinuedString)) {
//						computerDto.setDiscontinued(discontinuedString);
//					}
//				}
//			}
//
//			if (requestParams.get("companyId") != null && requestParams.get("companyId") != "") {
//				int companyId = Integer.parseInt(requestParams.get("companyId"));
//				int nbCompany = companyService.getComputerCount();
//				
//				if(companyId > 0 && companyId < nbCompany) {
//					Company company = new Company(companyId);
//					company = companyService.getCompany(company);
//					computerDto.setCompany(companyMapper.toCompanyDto(company));
//				}
//			}
//			Computer computer = computerMapper.toComputer(computerDto);
//			computerService.insertComputer(computer);
//		} catch (ComputerNameException cpne) {
//			logger.error("Computer name contains invalid characters '[~#@*+%{}<>\\\\[\\\\]|\\\"\\\\_^]'", cpne);
//		} catch (DateFormatException dfe) {
//			logger.error("Date formats should be YYYY-MM-DD", dfe);
//		} catch (DateRangeException dre) {
//			logger.error(" Discontinued Data >= Introduced Date (null if Introduced date is null)", dre);
//		}
//
//		return "redirect:/Dashboard";
//	}	
	
}

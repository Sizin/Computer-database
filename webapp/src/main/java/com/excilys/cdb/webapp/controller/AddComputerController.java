package com.excilys.cdb.webapp.controller;

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


//import com.excilys.cdb.binding.CompanyMapper;
//import com.excilys.cdb.binding.ComputerMapper;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.service.hibernateService.HibernateCompanyService;
import com.excilys.cdb.service.hibernateService.HibernateComputerService;


@Controller
@RequestMapping
public class AddComputerController {
	final static Logger logger = LoggerFactory.getLogger(AddComputerController.class);

	@Autowired
	private HibernateComputerService hcomputerService;
	
	@Autowired
	private HibernateCompanyService hcompanyService;
	

//	@Autowired
//	private ComputerMapper computerMapper;
//	@Autowired
//	private CompanyMapper companyMapper;
//	@Autowired
//	private ComputerValidator computerValidator;
//	@Autowired
//	private DateValidator dateValidator;

	@GetMapping("/addComputer")
	public String getAddComputer(@RequestParam Map<String, String> requestParams, Model model) {
		List<Company> companies = new ArrayList<Company>();
		companies = hcompanyService.getAll();
		model.addAttribute("companies", companies);
		model.addAttribute("computer", new Computer());
		return "addComputer";
	}

	@PostMapping("/addComputer")
	public String postAddComputer(@ModelAttribute("computer")Computer computer, BindingResult result, ModelMap model) {
		if(result.hasErrors()) {
			return "500";
		}
		
		if(computer.getCompany().getId() == 0) {
			computer.setCompany(null);
		}
		
		hcomputerService.insertComputer(computer);
		return "redirect:/Dashboard";
	}
	
}

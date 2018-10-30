package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Locale;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerPagination;
import com.excilys.cdb.services.ComputerService;

@Controller
//@RequestMapping("/Dashboard")
@RequestMapping
public class DashboardController {

	@Autowired
	private ComputerService computerService;
	
	public ComputerPagination computerPagination = new ComputerPagination();
		


//	@GetMapping
	@GetMapping("/Dashboard")
	public String getDashboard(Locale locale, @RequestParam Map<String,String> requestParams, Model model) {
		
		String search = requestParams.get("search");
		computerPagination.setSearchedWord(search);
		computerPagination.setNumberOfComputer(computerService.getComputerCount(search));
		
		List<Computer> computers = new ArrayList<Computer>();
		
		if (requestParams.get("range") != null) {
			computerPagination.setResultPerPage(Integer.parseInt(requestParams.get("range")));
		}else {
			computerPagination.setResultPerPage(10);
		}
		if (requestParams.get("page") != null) {
			computerPagination.setCurrentPage(Integer.parseInt(requestParams.get("page")));
		}
		
		if (search != "" && search != null) {
			// Setting the searched word in the Pagination class cause it impacts it directly
			computerPagination.setSearchedWord(search);
			computerPagination.setNumberOfComputer(computerService.getComputerCount(search));
			computerPagination.setPages();
			computers = computerService.showComputers(computerPagination.getCurrentPage(),	computerPagination.getResultPerPage(), search);

		}else if(computerPagination.getSearchedWord() != null && computerPagination.getSearchedWord() != "") {
			computerPagination.setNumberOfComputer(computerService.getComputerCount(search));
			computerPagination.setPages();
			computers = computerService.showComputers(computerPagination.getCurrentPage(),	computerPagination.getResultPerPage(), computerPagination.getSearchedWord());
		}else {
			computerPagination.setSearchedWord(null);
			computerPagination.setNumberOfComputer(computerService.getComputerCount());
			computerPagination.setPages();
			computers = computerService.showComputers(computerPagination.getCurrentPage(),	computerPagination.getResultPerPage(), computerPagination.getSearchedWord());
		}		
		
		model.addAttribute("listComputer", computers);
		model.addAttribute("resultPerPage", computerPagination.getResultPerPage());
		model.addAttribute("currentPage", computerPagination.getCurrentPage());
		model.addAttribute("currentEndPage", computerPagination.getCurrentEndPage());
		model.addAttribute("currentStartPage", computerPagination.getCurrentStartPage());
		model.addAttribute("arrowDisplay", computerPagination.getArrowDisplay());
		model.addAttribute("numberOfPage", computerPagination.getNbPage());
		model.addAttribute("numberOfComputer", computerPagination.getNumberOfComputer());
		return "dashboard";
	}
	
	@PostMapping("/Dashboard")
	public String postDashboard(@RequestParam Map<String,String> requestParams){
		String search = requestParams.get("search");
		computerPagination.setSearchedWord(search);
		return "dashboard";
	}
	
//	@RequestMapping(value="/**",method = {RequestMethod.GET, RequestMethod.POST})
//	public String getAnythingelse(){
//		return "404";
//	}
	

}

package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.ComputerService;

@Controller
@RequestMapping
public class DeleteComputerController {

	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerMapper computerMapper;
	
	@PostMapping("/DeleteComputer")
	public String postDashboard(@RequestParam Map<String,String> requestParams){
		String selection = requestParams.get("selection");
		
		if(selection != null && selection != "") {
			String[] computerSelection = selection.split(",");
			
			List<Computer> computersToDelete  = new ArrayList<Computer>();
			
			for(String id : computerSelection) {
				ComputerDto computerDto = new ComputerDto();
				computerDto.setId(id);
				Computer computer = computerMapper.toComputer(computerDto);
				computersToDelete.add(computer);
			}
			computerService.deleteComputer(computersToDelete);
			return "redirect:/Dashboard";
		}else {
			return "500";
		}
	}
	
	
}

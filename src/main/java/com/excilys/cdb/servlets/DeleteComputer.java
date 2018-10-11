package com.excilys.cdb.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.ComputerMapper;

import java.util.*;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/DeleteComputer")
public class DeleteComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
	private static ComputerMapper computerMapper;
	private static ComputerService computerService;
   
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteComputer() {
        super();
        this.computerMapper = ComputerMapper.getInstance();
        this.computerService = ComputerService.getInstance();
  
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String selection = request.getParameter("selection");
		String[] arraySelection = selection.split(",");
		
		List<Computer> computersToDelete  = new ArrayList<Computer>();
		
		
		for(String id : arraySelection) {
			ComputerDto computerDto = new ComputerDto();
			computerDto.setId(id);
			Computer computer = computerMapper.toComputer(computerDto);
			computersToDelete.add(computer);
		}
				
		
		computerService.deleteComputer(computersToDelete);
		
		
		
		
		RequestDispatcher rd = request.getRequestDispatcher("Dashboard");
		rd.forward(request,response);
	}

}

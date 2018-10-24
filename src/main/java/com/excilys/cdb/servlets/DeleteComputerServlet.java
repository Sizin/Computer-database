package com.excilys.cdb.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.ComputerMapper;

import java.util.*;

/**
 * Servlet implementation class DeleteComputer
 */
@WebServlet("/DeleteComputer-servlet")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ComputerService computerService;
	@Autowired
	private ComputerMapper computerMapper;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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

		
		if(selection != null && selection != "") {

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
		}else {
			request.getRequestDispatcher("/WEB-INF/views/500.jsp").forward(request, response);
		}

		

	}

}

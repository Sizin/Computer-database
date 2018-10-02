package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;

import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/editComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger;

	private static CompanyService companyService;
	private static ComputerService computerService;

	private static CompanyMapper companyMapper;
	private static ComputerMapper computerMapper;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditComputer() {
		super();
		companyService = CompanyService.getInstance();
		computerService = ComputerService.getInstance();
		companyMapper = CompanyMapper.getInstance();
        computerMapper = ComputerMapper.getInstance();
		
		logger = Logger.getLogger(EditComputer.class);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int computerId = Integer.parseInt(request.getParameter("computerId"));

		ComputerDto computerDto = computerMapper.toComputerDto(computerService.getOneComputer(computerId));

		request.setAttribute("computer", computerDto);
		
		this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("doGet");
		doGet(request, response);
	}

}

package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerPagination;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(Dashboard.class);

	private CompanyService companyService;
	private ComputerService computerService;
	private ComputerPagination computerPagination;

	/**
	 * static
	 * 
	 * @see HttpServlet#HttpServlet()
	 */
	public Dashboard() {
		super();
		this.companyService = CompanyService.getInstance();
		this.computerService = ComputerService.getInstance();
		this.computerPagination = ComputerPagination.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String search = request.getParameter("search");

		List<Computer> computers = new ArrayList<Computer>();

		if (request.getParameter("range") != null) {
			computerPagination.setResultPerPage(Integer.parseInt(request.getParameter("range")));
		}
		if (request.getParameter("page") != null) {
			computerPagination.setCurrentPage(Integer.parseInt(request.getParameter("page")));
		}

		if (search != "" && search != null) {
			logger.info("User search");
			// Setting the searched word in the Pagination class cause it impacts it directly
			computerPagination.setSearchedWord(search);
			computerPagination.setPages();
			computers = computerService.showComputers(computerPagination.getCurrentPage(),	computerPagination.getResultPerPage(), search);
			
		}else if(computerPagination.getSearchedWord() != null && computerPagination.getSearchedWord() != "") {
			computerPagination.setPages();
			computers = computerService.showComputers(computerPagination.getCurrentPage(),	computerPagination.getResultPerPage(), computerPagination.getSearchedWord());
		}else {
			computerPagination.setSearchedWord("");
			computerPagination.setPages();
			computers = computerService.showComputers(computerPagination.getCurrentPage(),	computerPagination.getResultPerPage(), computerPagination.getSearchedWord());
		}

		request.setAttribute("listComputer", computers);
		request.setAttribute("resultPerPage", computerPagination.getResultPerPage());
		request.setAttribute("currentPage", computerPagination.getCurrentPage());
		request.setAttribute("currentEndPage", computerPagination.getCurrentEndPage());
		request.setAttribute("currentStartPage", computerPagination.getCurrentStartPage());
		request.setAttribute("arrowDisplay", computerPagination.getArrowDisplay());
		request.setAttribute("numberOfPage", computerPagination.getNbPage());
		request.setAttribute("numberOfComputer", computerPagination.getNumberOfComputer());
		request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String word = request.getParameter("search");
		request.setAttribute("search", word);
		doGet(request, response);
	}

}

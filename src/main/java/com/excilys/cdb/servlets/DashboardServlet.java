package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerPagination;
import com.excilys.cdb.services.ComputerService;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/Dashboard-servlet")
public class DashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(DashboardServlet.class);

	@Autowired
	private ComputerService computerService;

	public ComputerPagination computerPagination = new ComputerPagination();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String search = request.getParameter("search");
		

		computerPagination.setSearchedWord(search);
		computerPagination.setNumberOfComputer(computerService.getComputerCount(search));
		List<Computer> computers = new ArrayList<Computer>();
		
		if (request.getParameter("range") != null) {
			computerPagination.setResultPerPage(Integer.parseInt(request.getParameter("range")));
		}else {
			computerPagination.setResultPerPage(10);
		}
		if (request.getParameter("page") != null) {
			computerPagination.setCurrentPage(Integer.parseInt(request.getParameter("page")));
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
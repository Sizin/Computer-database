package com.excilys.cdb.servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.SynthSpinnerUI;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private  CompanyService companyService;
	private  ComputerService computerService;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
        super();
        this.companyService = CompanyService.getInstance();
        this.computerService = ComputerService.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Computer> computers = new ArrayList<Computer>();
			
		int numberOfComputer = computerService.getComputerCount();
		System.out.println(numberOfComputer);
		int pageRange = request.getParameter("range") == null ? 10 : Integer.parseInt(request.getParameter("range")); 
		int pageNumber = request.getParameter("page") == null ? 0 : Integer.parseInt(request.getParameter("page"));
		int numberOfPage = numberOfComputer/pageRange;

		computers = computerService.showComputers(pageNumber,pageRange);
		
		request.setAttribute("computers", computers);
		request.setAttribute("range", pageRange);
		request.setAttribute("page", pageNumber);
		request.setAttribute("numberOfPage", numberOfPage);
		request.setAttribute("numberOfComputer", numberOfComputer);
		request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

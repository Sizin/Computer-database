package com.excilys.cdb.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.exceptions.ComputerNameException;
import com.excilys.cdb.exceptions.DateFormatException;
import com.excilys.cdb.exceptions.DateRangeException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerBuilder;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.verifyers.LocalDateCheck;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	

	private  CompanyService companyService;
	private  ComputerService computerService;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
        this.companyService = CompanyService.getInstance();
        this.computerService = ComputerService.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies = new ArrayList<Company>();
		
		companies = companyService.showCompanies();
		request.setAttribute("companies", companies);
				
		ServletContext sc = getServletContext();
		sc.getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
			
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Getting POST data
		String computerName = request.getParameter("computerName");
		String introducedString = request.getParameter("introduced");
		String discontinuedString = request.getParameter("discontinued");
		String companyIdString = request.getParameter("companyId");
		
		long newComputerId = 0;
		

		// First inserting computer
		try {
			newComputerId = computerService.createComputer(computerName, introducedString, discontinuedString);	
		} catch (DateFormatException | DateRangeException | ComputerNameException e) {
			e.printStackTrace();
		}
		
		
		if(companyIdString != null && newComputerId != 0) {
			long companyId = Integer.parseInt(companyIdString);
			Optional<Company> company = companyService.getCompany(companyId);
			if(company.isPresent()) {
				newComputerId = computerService.assignCompanyToComputer(newComputerId, companyId);
			}
		}
	
		RequestDispatcher rd = request.getRequestDispatcher("Dashboard");
		rd.forward(request,response);
//		doGet(request, response);
	}
	

}

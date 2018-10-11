package com.excilys.cdb.servlets;

import java.io.IOException;
import java.text.ParseException;
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

import org.apache.log4j.Logger;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exceptions.ComputerNameException;
import com.excilys.cdb.exceptions.DateFormatException;
import com.excilys.cdb.exceptions.DateRangeException;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.validators.CompanyValidator;
import com.excilys.cdb.validators.ComputerValidator;
import com.excilys.cdb.validators.DateValidator;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/addComputer")
public class AddComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger logger = Logger.getLogger(Dashboard.class);


	private static CompanyService companyService;
	private static ComputerService computerService;

	private static CompanyMapper companyMapper;
	private static ComputerMapper computerMapper;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
        this.companyService = CompanyService.getInstance();
        this.computerService = ComputerService.getInstance();
        this.companyMapper = CompanyMapper.getInstance();
        this.computerMapper = ComputerMapper.getInstance();
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
		
		// Creating Computer DTO object
		ComputerDto computerDto = new ComputerDto();
		
		try {
			ComputerValidator.validateName(computerName);
			computerDto.setName(computerName);			
			
			if(DateValidator.validDate(introducedString)) {
				computerDto.setIntroduced(introducedString);
				if(DateValidator.validDate(discontinuedString)) {
					if(ComputerValidator.validateDates(introducedString, discontinuedString)) {
						computerDto.setDiscontinued(discontinuedString);
					}
				}
			}

			Computer computer = computerMapper.toComputer(computerDto);
			newComputerId = computerService.insertComputer(computer);	
		}catch(ComputerNameException cpne) {
			logger.error("Computer name contains invalid characters '[~#@*+%{}<>\\\\[\\\\]|\\\"\\\\_^]'", cpne);
		}catch(DateFormatException dfe) {
			logger.error("Date formats should be YYYY-MM-DD", dfe);
		}catch(DateRangeException dre) {
			logger.error(" Discontinued Data >= Introduced Date (null if Introduced date is null)", dre);
		}
				
		
		//- Assigning Company to computer -\\
		//Creating Company DTO object
		CompanyDto companyDto = new CompanyDto();
		
		// Getting the number or company in DB for preventing ID out of range
		int nbCompany = companyService.getComputerCount();
		
		if(companyIdString != null) {
			try {
				CompanyValidator.validateId(companyIdString, nbCompany);
				companyDto.setId(companyIdString);

				Company company = companyService.getCompany(companyMapper.toCompany(companyDto));
				computerService.assignCompanyToComputer(newComputerId, company);
				
			}catch(ParseException e) {
				logger.error("Company Id is incorredt : 0 < id < "+ nbCompany, e);
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("Dashboard");
		rd.forward(request,response);
//		doGet(request, response);
		
		
	}
	

}

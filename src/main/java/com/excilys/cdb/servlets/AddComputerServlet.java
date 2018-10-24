package com.excilys.cdb.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

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
@WebServlet("/addComputer-servlet")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	final static Logger logger = LoggerFactory.getLogger(DashboardServlet.class);

	@Autowired
	private CompanyService companyService;
	@Autowired
	private ComputerService computerService;
	@Autowired
	private CompanyMapper companyMapper;
	@Autowired
	private ComputerMapper computerMapper;
	@Autowired
	private ComputerValidator computerValidator;
	@Autowired
	private DateValidator dateValidator;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
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
			computerValidator.validateName(computerName);
			computerDto.setName(computerName);			
			
			if(dateValidator.validDate(introducedString)) {
				computerDto.setIntroduced(introducedString);
				if(dateValidator.validDate(discontinuedString)) {
					if(computerValidator.validateDates(introducedString, discontinuedString)) {
						computerDto.setDiscontinued(discontinuedString);
					}
				}
			}

			Computer computer = computerMapper.toComputer(computerDto);
			computerService.insertComputer(computer);	
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
		
		
	}
	

}

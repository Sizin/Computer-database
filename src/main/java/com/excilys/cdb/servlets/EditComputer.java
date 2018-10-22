package com.excilys.cdb.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.RequestDispatcher;
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

import com.excilys.cdb.services.ComputerService;
import com.excilys.cdb.validators.CompanyValidator;
import com.excilys.cdb.validators.ComputerValidator;
import com.excilys.cdb.services.CompanyService;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exceptions.ComputerNameException;
import com.excilys.cdb.exceptions.DateFormatException;
import com.excilys.cdb.exceptions.DateRangeException;
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
	private final Logger logger = LoggerFactory.getLogger(EditComputer.class);
	
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
		logger.info("Get edit computer page");
		List<Company> companies = companyService.showCompanies();

		if (request.getParameter("computerId") != null) {

			int computerId = Integer.parseInt(request.getParameter("computerId"));
			
			Computer computer = computerService.getOneComputer(computerId);

			ComputerDto computerDto = computerMapper.toComputerDto(computer);

			request.setAttribute("computer", computerDto);
			request.setAttribute("companies", companies);

			this.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
		} else {
			response.sendRedirect("Dashboard");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		logger.info("Post edited computer");

		String computerIdString = request.getParameter("computerId");

		if(computerIdString != null) {
			Computer computer = new Computer();
			ComputerDto computerDto = new ComputerDto();

			String computerName = request.getParameter("computerName");
			String computerIntroduced = request.getParameter("introduced");
			String computerDiscontinued = request.getParameter("discontinued");
			String companyIdString = request.getParameter("companyId");
				
			int numberOfComputer = computerService.getComputerCount();
		
			try {
				computerValidator.validateId(computerIdString, numberOfComputer);
				computerValidator.validateName(computerName);
				computerValidator.validateDates(computerIntroduced, computerDiscontinued);
				
				computerDto.setId(computerIdString);
				computerDto.setName(computerName);
				computerDto.setIntroduced(computerIntroduced);
				computerDto.setDiscontinued(computerDiscontinued);
			
				computer = computerMapper.toComputer(computerDto);
				computerService.updateComputer(computer);
				
				
				
			} catch (ParseException e) {
				logger.debug("Computer Id is incorredt : 0 < id < "+ numberOfComputer);
			} catch (ComputerNameException e) {
				logger.debug("Computer name format is invalid");
			} catch (DateFormatException e) {
				logger.debug("Date formats should be YYYY-MM-DD");
			} catch (DateRangeException e) {
				logger.debug(" Discontinued Data >= Introduced Date (null if Introduced date is null)");
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
					computerService.assignCompanyToComputer(computer.getId(), company);
					
				}catch(ParseException e) {
					logger.debug("Company Id is incorredt : 0 < id < "+ nbCompany);
				}
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("Dashboard");
		rd.forward(request, response);
	}

}

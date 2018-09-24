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
import com.excilys.cdb.services.ComputerService;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ComputerService cs = ComputerService.getInstance();

		List<Computer> computers = new ArrayList<Computer>();
			
		int numberOfComputer = cs.getComputerCount();
		int pageRange = request.getParameter("range") == null ? 10 : Integer.parseInt(request.getParameter("range")); 
		int pageNumber = request.getParameter("page") == null ? 0 : Integer.parseInt(request.getParameter("page"));
		int numberOfPage = numberOfComputer/pageRange;

		computers = cs.showComputers(pageNumber,pageRange);
		
		request.setAttribute("computers", computers);
		request.setAttribute("range", pageRange);
		request.setAttribute("page", pageNumber);
		request.setAttribute("numberOfPage", numberOfPage);
		request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

package com.excilys.cdb.model;

import com.excilys.cdb.services.ComputerService;

public class ComputerPagination {

	private ComputerService computerService;
	
	private static ComputerPagination pagination;
	
	protected int numberOfComputer;
	protected int resultPerPage;
	protected int nbPage;
	protected int currentPage;
	protected int currentStartPage;
	protected int currentEndPage;
	
	protected String arrowDisplay;
	
	private ComputerPagination() {
		computerService = ComputerService.getInstance();
		numberOfComputer = computerService.getComputerCount();
		resultPerPage = 10;
		nbPage = numberOfComputer/resultPerPage;
		currentPage = 1;
		currentStartPage = 1;
		currentEndPage = 5;
		arrowDisplay="rightOnly";
	}
	
	public static ComputerPagination getInstance() {
		if(pagination == null) {
			pagination = new ComputerPagination();
		}
		return pagination;
	}
	
	public void setPages() {

		if(nbPage > 5 ) {
			//  If we're at first page range (1-5)
			if(currentPage < 5) {
				//The start pages is one
				currentStartPage = 1;
				//As we are the first range the end range will be five
				currentEndPage = 5;	
				// We are at the start range
				arrowDisplay = "rightOnly";
				
			
				
			}else if(currentPage >= 5 && currentPage < (nbPage - 5)) {
				
				currentStartPage = currentPage - 2;
				currentEndPage = currentPage + 2;
				arrowDisplay = "all";
				
				
			}else if ( currentPage >= (nbPage - 5) ) {
				currentStartPage = nbPage - 5;
				currentEndPage = nbPage;
				arrowDisplay = "leftOnly";
			}
			
			
				
		}else if (nbPage <= 5) {
			
			currentStartPage = 1;
			currentEndPage = nbPage;
			arrowDisplay = "rightOnly";
			
		}
		
	}
	
	
	public ComputerService getComputerService() {
		return computerService;
	}

	public void setComputerService(ComputerService computerService) {
		this.computerService = computerService;
	}

	public static ComputerPagination getPagination() {
		return pagination;
	}

	public static void setPagination(ComputerPagination pagination) {
		ComputerPagination.pagination = pagination;
	}

	public int getNumberOfComputer() {
		return numberOfComputer;
	}

	public void setNumberOfComputer(int numberOfComputer) {
		this.numberOfComputer = numberOfComputer;
	}

	public int getResultPerPage() {
		return resultPerPage;
	}

	public void setResultPerPage(int resultPerPage) {
		this.resultPerPage = resultPerPage;
		this.nbPage = numberOfComputer/resultPerPage;
	}

	public int getNbPage() {
		return nbPage;
	}

	public void setNbPage(int nbPage) {
		this.nbPage = nbPage;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentStartPage() {
		return currentStartPage;
	}

	public void setCurrentStartPage(int currentStartPage) {
		this.currentStartPage = currentStartPage;
	}

	public int getCurrentEndPage() {
		return currentEndPage;
	}

	public void setCurrentEndPage(int currentEndPage) {
		this.currentEndPage = currentEndPage;
	}

	public String getArrowDisplay() {
		return arrowDisplay;
	}

	public void setArrowDisplay(String arrowDisplay) {
		this.arrowDisplay = arrowDisplay;
	}

	
	
}

package com.excilys.cdb.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.cdb.services.HibernateComputerService;

@Component
public class ComputerPagination {
	
	protected int numberOfComputer;
	protected int resultPerPage;
	protected int nbPage;
	protected int currentPage;
	protected int currentStartPage;
	protected int currentEndPage;
	
	protected String arrowDisplay;
	
	protected String searchedWord;

	public ComputerPagination() {
		resultPerPage = 10;
		currentPage = 1;
		currentStartPage = 1;
		currentEndPage = 5;
		arrowDisplay = "rightOnly";
	}
	
	
	public void setPages() {

		nbPage = numberOfComputer/resultPerPage;
		
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
			arrowDisplay = "none";
			
		}
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

	public String getSearchedWord() {
		return searchedWord;
	}

	public void setSearchedWord(String searchedWord) {
		this.searchedWord = searchedWord;
	}

	
	
}

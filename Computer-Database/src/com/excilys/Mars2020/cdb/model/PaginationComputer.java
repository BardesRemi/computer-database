package com.excilys.Mars2020.cdb.model;

import com.excilys.Mars2020.cdb.persistance.ComputerDAO;

/**
 * Manage pagination system
 * @author remi
 *
 */
public class PaginationComputer extends Pagination {
	
	public PaginationComputer() {
		this.actualPageNb = 0;
		this.pageSize = 15;
		this.maxPages = ComputerDAO.getComputerDAO().countAllComputer() / this.pageSize;
	}
	
	public void displayPage() {
		this.displayPageContent(ComputerDAO.getComputerDAO().getPageComputersRequest(this));
	}
}

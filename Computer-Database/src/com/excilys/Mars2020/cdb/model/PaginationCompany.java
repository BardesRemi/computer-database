package com.excilys.Mars2020.cdb.model;

import com.excilys.Mars2020.cdb.persistance.CompanyDAO;

public class PaginationCompany extends Pagination {
	
	public PaginationCompany() {
		this.actualPageNb = 0;
		this.pageSize = 15;
		this.maxPages = CompanyDAO.getCompanyDAO().countAllCompanies() / this.pageSize;
	}
	
	public void displayPage() {
		this.displayPageContent(CompanyDAO.getCompanyDAO().getPageCompaniesRequest(this));
	}
}

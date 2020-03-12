package com.excilys.Mars2020.cdb.service;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.Mars2020.cdb.model.Company;
import com.excilys.Mars2020.cdb.model.Pagination;
import com.excilys.Mars2020.cdb.persistance.CompanyDAO;

/**
 * Class used as intermediate between CompanyDAO and Ui
 * Display and checking integrity of users requests
 * @author remi
 *
 */
public class CompanyService {
		
	CompanyDAO compdao;
	
	public CompanyService() {
		this.compdao = CompanyDAO.getCompanyDAO();
	}
	
	/**
	 * check if the given company is in the DB
	 * @param comp
	 * @return true if comp E DB, false if not
	 */
	public static boolean companyInDb(Company comp) {
		Optional<Company> checkingComp = CompanyDAO.getCompanyDAO().getOneCompanyRequest(comp.getId());
		if(checkingComp.isEmpty()) { return false; }
		else { 
			if(comp.getName().isEmpty() || checkingComp.get().getName() == comp.getName()) {return true;}
			else {return false; }
		}
	}
	
	/**
	 * 
	 * @return All the companies in the db
	 */
	public ArrayList<Company> getAllCompanies() {
		return this.compdao.getAllCompaniesRequest();
	}
	
	/**
	 * 
	 * @param page
	 * @return Computers in the page
	 */
	public ArrayList<Company> getPageCompanies(Pagination page){
		return compdao.getPageCompaniesRequest(page);
	}
	
	/**
	 * 
	 * @return the number of company in the database
	 */
	public int getCountCompanies() {
		return compdao.countAllCompanies();
	}
}

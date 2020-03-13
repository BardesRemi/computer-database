package com.excilys.mars2020.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.Pagination;
import com.excilys.mars2020.cdb.persistance.CompanyDAO;

/**
 * Class used as intermediate between CompanyDAO and Ui
 * Display and checking integrity of users requests
 * @author remi
 *
 */
public class CompanyService {
		
	private CompanyDAO compdao;
	
	public CompanyService() {
		this.compdao = CompanyDAO.getCompanyDAO();
	}
	
	/**
	 * check if the given company is in the DB
	 * @param comp
	 * @return true if comp E DB, false if not
	 */
	public static boolean companyInDb(Company comp) {
		Optional<Company> checkingComp = CompanyDAO.getCompanyDAO().getOneCompanyRequest(comp.getCompId());
		if (!checkingComp.isEmpty()) {
			return false; 
		}
		else { 
			return (comp.getName().isEmpty() || checkingComp.get().getName() == comp.getName());
		}
	}
	
	/**
	 * 
	 * @return All the companies in the db
	 */
	public List<Company> getAllCompanies() {
		return this.compdao.getAllCompaniesRequest();
	}
	
	/**
	 * 
	 * @param page
	 * @return Computers in the page
	 */
	public List<Company> getPageCompanies(Pagination page){
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

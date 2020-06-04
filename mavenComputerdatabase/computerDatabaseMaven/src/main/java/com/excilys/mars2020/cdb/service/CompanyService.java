package com.excilys.mars2020.cdb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.excilys.mars2020.cdb.mapper.Mapper;
import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.CompanyDTO;
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
	
	public CompanyService(CompanyDAO compDAO) {
		this.compdao = compDAO;
	}
	
	/**
	 * check if the given company is in the DB
	 * @param comp
	 * @return true if comp E DB, false if not
	 */
	public boolean companyInDb(Company comp) {
		Optional<Company> checkingComp = compdao.getOneCompanyRequest(comp.getCompId());
		if (checkingComp.isEmpty()) {
			return false; 
		}
		else { 
			return (comp.getName()==null || comp.getName().isEmpty() || checkingComp.get().getName() == comp.getName());
		}
	}
	
	/**
	 * 
	 * @return All the companies in the db
	 */
	public List<CompanyDTO> getAllCompanies() {
		return this.compdao.getAllCompaniesRequest().stream().map(company -> Mapper.companyToCompanyDTO(company)).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param page
	 * @return Companies in the page
	 */
	public List<CompanyDTO> getPageCompanies(Pagination page){
		return compdao.getPageCompaniesRequest(page).stream().map(company -> Mapper.companyToCompanyDTO(company)).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @return the number of company in the database
	 */
	public int getCountCompanies() {
		return compdao.countAllCompanies();
	}
}

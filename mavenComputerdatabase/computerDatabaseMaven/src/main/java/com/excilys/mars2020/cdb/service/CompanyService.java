package com.excilys.mars2020.cdb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.mars2020.cdb.mapper.CompanyMapper;
import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.CompanyDTO;
import com.excilys.mars2020.cdb.model.Pagination;
import com.excilys.mars2020.cdb.persistence.CompanyDAO;

/**
 * Class used as intermediate between CompanyDAO and Ui
 * Display and checking integrity of users requests
 * @author remi
 *
 */
@Service
public class CompanyService {

	@Autowired
	private CompanyDAO compdao;
	
	@Autowired
	private CompanyMapper compMapper;
	
	public CompanyService() {}
	
	/**
	 * check if the given company is in the DB
	 * @param comp
	 * @return true if comp E DB, false if not
	 */
	public boolean companyInDb(CompanyDTO compDTO) {
		try {
			Company comp = compMapper.companyDTOToCompany(compDTO).get();
			Optional<Company> checkingComp = compdao.getOneCompanyRequest(comp.getCompId());
			if (checkingComp.isEmpty()) {
				return false;
			}
			else { 
				return (comp.getName()==null || comp.getName().isEmpty() || checkingComp.get().getName().equals(comp.getName()));
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * retrieve the company in the db as an optional with id=id
	 * @param id
	 * @return optional of the company requested
	 */
	public CompanyDTO getCompanyById(long id) {
		return compMapper.companyToCompanyDTO(this.compdao.getOneCompanyRequest(id).get());
	}
	
	/**
	 * 
	 * @return All the companies in the db
	 */
	public List<CompanyDTO> getAllCompanies() {
		return this.compdao.getAllCompaniesRequest().stream().map(company -> compMapper.companyToCompanyDTO(company)).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param page
	 * @return Companies in the page
	 */
	public List<CompanyDTO> getPageCompanies(Pagination page){
		return compdao.getPageCompaniesRequest(page).stream().map(company -> compMapper.companyToCompanyDTO(company)).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @return the number of company in the database
	 */
	public int getCountCompanies() {
		return compdao.countAllCompanies();
	}
	
	public int deleteCompany(long id) {
		return compdao.deleteCompany(id);
	}
}

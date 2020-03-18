package com.excilys.mars2020.cdb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.excilys.mars2020.cdb.exceptions.ParseExceptions;
import com.excilys.mars2020.cdb.mapper.Mapper;
import com.excilys.mars2020.cdb.model.Computer;
import com.excilys.mars2020.cdb.model.ComputerDTO;
import com.excilys.mars2020.cdb.model.Pagination;
import com.excilys.mars2020.cdb.persistance.ComputerDAO;

/**
 * Class used as intermediate between ComputerDAO and Ui
 * Display and checking integrity of users requests
 * @author remi
 *
 */
public class ComputerService {
	
	private ComputerDAO pcdao;
	
	public ComputerService() {
		this.pcdao = ComputerDAO.getComputerDAO();
	}

	/**
	 * 
	 * @return All the computers in the db
	 */
	public List<ComputerDTO> getAllComputers() {
		return pcdao.getAllComputersRequest().stream().map(computer -> Mapper.computerToComputerDTO(computer)).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param page
	 * @return Computers in the page
	 */
	public List<ComputerDTO> getPageComputers(Pagination page){
		return pcdao.getPageComputersRequest(page).stream().map(computer -> Mapper.computerToComputerDTO(computer)).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @return number of Computer in the database
	 */
	public int getCountComputers() {
		return pcdao.countAllComputer();
	}
	
	/**
	 * Search and retrieve a specific PC with id = idSearched in the db
	 * @param idSearched
	 * @return the Optional containing (or not) the requested PC
	 */
	public Optional<Computer> getOneComputer(int idSearched) {
		return pcdao.getOneComputers(idSearched);
	}
	
	
	public int addNewComputer (ComputerDTO pcDTO) throws ParseExceptions {
		Computer pcFinal = Mapper.ComputerDTOToComputer(pcDTO);
		if(pcFinal.getIntroduced() != null && (pcFinal.getDiscontinued()==null || pcFinal.getIntroduced().compareTo(pcFinal.getDiscontinued()) > 0)) {
			return -1;
		}
		if(pcFinal.getcompany() != null && !CompanyService.companyInDb(pcFinal.getcompany())) {
			return -3;
		}
		return pcdao.insertNewComputer(pcFinal);
	}
	
	public int updateComputer(ComputerDTO pcDTO) throws ParseExceptions {
		Computer pcFinal = Mapper.ComputerDTOToComputer(pcDTO);
		Optional<Computer> optCheckingPc = pcdao.getOneComputers(pcFinal.getPcId());
		if(optCheckingPc.isEmpty()) {
			return -1;
		}
		Computer checkingPc = optCheckingPc.get();
		if(pcFinal.getIntroduced() != null && (pcFinal.getDiscontinued()==null || pcFinal.getIntroduced().compareTo(pcFinal.getDiscontinued()) > 0)) {
			return -3;
		}
		if(checkingPc.getIntroduced() != null && (pcFinal.getDiscontinued()!=null || checkingPc.getIntroduced().compareTo(pcFinal.getDiscontinued()) > 0)) {
			return -4;
		}
		if(pcFinal.getIntroduced() != null && (checkingPc.getDiscontinued()!=null || pcFinal.getIntroduced().compareTo(checkingPc.getDiscontinued()) > 0)) {
			return -4;
		}
		if(!CompanyService.companyInDb(pcFinal.getcompany())) {
			return -6; 
		}
		return pcdao.updateComputer(pcFinal);
	}
	
	/**
	 * Delete a row in computer where id=id
	 * @param id
	 * @return 0< if something went wrong, 0 if nothing deleted, 1 if row correctly deleted
	 */
	public int deleteComputer(int id) {
		return pcdao.deleteComputer(id);
	}
	
}

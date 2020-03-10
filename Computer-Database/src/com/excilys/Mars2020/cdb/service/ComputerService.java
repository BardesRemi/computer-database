package com.excilys.Mars2020.cdb.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import com.excilys.Mars2020.cdb.mapper.DateAPI;
import com.excilys.Mars2020.cdb.model.Company;
import com.excilys.Mars2020.cdb.model.Computer;
import com.excilys.Mars2020.cdb.persistance.CompanyDAO;
import com.excilys.Mars2020.cdb.persistance.ComputerDAO;

/**
 * Class used as intermediate between ComputerDAO and Ui
 * Display and checking integrity of users requests
 * @author remi
 *
 */
public class ComputerService {
	
	ComputerDAO pcdao;
	
	public ComputerService() {
		this.pcdao = ComputerDAO.getComputerDAO();
	}

	/**
	 * 
	 * @return All the computers in the db
	 */
	public ArrayList<Computer> getAllComputers() {
		return pcdao.getAllComputersRequest();
	}
	
	/**
	 * Search and retrieve a specific PC with id = idSearched in the db
	 * @param idSearched
	 * @return the Optional containing (or not) the requested PC
	 */
	public Optional<Computer> getOneComputer(int idSearched) {
		return pcdao.getOneComputers(idSearched);
	}
	
	/**
	 * Add a new Computer in the db if given params are valid ones :
	 * 		-> name shouldn't be empty
	 * 		-> introducedD <= discontinuedD
	 * 		-> (compName, compId) should be null or match a compani in the db 
	 * @param name
	 * @param introducedD
	 * @param discontinuedD
	 * @param compName
	 * @param compId
	 * @return the Id associated to the PC automaticaly by the db, or 0<= if a problem occured
	 */
	public int addNewComputer (String name, String introducedD, String discontinuedD, String compName, String compId) {
		if(name.isEmpty()) { return 0;}
		LocalDate intro = DateAPI.stringToLocalDate(introducedD);
		LocalDate discont = DateAPI.stringToLocalDate(discontinuedD);
		if(intro != null && (discont==null || intro.compareTo(discont) > 0)) { return -1;}
		if(!compName.isEmpty() && compId.isEmpty()) { return -2; }
		if(compName.isEmpty() && compId.isEmpty()) {
			Computer pc = new Computer.Builder(name).introduced(intro).discontinued(discont).build();
			return pcdao.insertNewComputer(pc);
		}
		else {
			int companyId = Integer.valueOf(compId);
			Company tempComp = new Company(compName, companyId);
			if(!CompanyService.companyInDb(tempComp)) { return -3; }
			Computer pc = new Computer.Builder(name).introduced(intro).discontinued(discont).company(tempComp).build();
			return pcdao.insertNewComputer(pc);
		}
	}
	
	/**
	 * Update the PC with id=id in the db, only if modifications are valid ones :
	 * 		-> id Should correspond to 1 PC in the db
	 * 		-> name shouldn't be empty
	 * 		-> introducedD <= discontinuedD
	 * 		-> Old(introducedD) <= discontinuedD
	 * 		-> introducedD <= Old(discontinuedD)
	 * 		-> (compName, compId) should be null or match a company in the db 
	 * @param name
	 * @param id
	 * @param introducedD
	 * @param discontinuedD
	 * @param compName
	 * @param compId
	 */
	public int updateComputer(String name, String id, String introducedD, String discontinuedD, String compName, String compId) {
		if(id.isEmpty()) {return 0;}
		int idInt = Integer.valueOf(id);
		Optional<Computer> optCheckingPc = pcdao.getOneComputers(idInt);
		if(optCheckingPc.isEmpty()) {return -1;}
		Computer checkingPc = optCheckingPc.get();
		if(name.isEmpty()) {return -2;}
		LocalDate intro = DateAPI.stringToLocalDate(introducedD);
		LocalDate discont = DateAPI.stringToLocalDate(discontinuedD);
		if(intro != null && (discont!=null || intro.compareTo(discont) > 0)) { return -3;}
		if(checkingPc.getIntroduced() != null && (discont!=null || checkingPc.getIntroduced().compareTo(discont) > 0)) {return -4;}
		if(intro != null && (checkingPc.getDiscontinued()!=null || intro.compareTo(checkingPc.getDiscontinued()) > 0)) {return -4;}
		if(!compName.isEmpty() && compId.isEmpty()) { return -5; }
		if(compName.isEmpty() && compId.isEmpty()) {
			Computer pc = new Computer.Builder(name).id(idInt).introduced(intro).discontinued(discont).build();
			return pcdao.insertNewComputer(pc);
		}
		else {
			int companyId = Integer.valueOf(compId);
			Company tempComp = new Company(compName, companyId);
			if(!CompanyService.companyInDb(tempComp)) { return -6; }
			Computer pc = new Computer.Builder(name).id(idInt).introduced(intro).discontinued(discont).company(tempComp).build();
			return pcdao.updateComputer(pc);
		}
		
		
	}
	
	
}

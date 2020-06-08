package com.excilys.mars2020.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.excilys.mars2020.cdb.exceptions.LogicalExceptions;
import com.excilys.mars2020.cdb.exceptions.LogicalProblem;
import com.excilys.mars2020.cdb.exceptions.ParseExceptions;
import com.excilys.mars2020.cdb.mapper.Mapper;
import com.excilys.mars2020.cdb.model.Computer;
import com.excilys.mars2020.cdb.model.ComputerDTO;
import com.excilys.mars2020.cdb.model.Pagination;
import com.excilys.mars2020.cdb.persistance.ComputerDAO;
import com.excilys.mars2020.cdb.validations.LogicalChecker;

/**
 * Class used as intermediate between ComputerDAO and Ui
 * Display and checking integrity of users requests
 * @author remi
 *
 */
public class ComputerService {
	
	private ComputerDAO pcdao;
	
	public ComputerService(ComputerDAO pcDAO) {
		this.pcdao = pcDAO;
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
	
	
	public int addNewComputer (ComputerDTO pcDTO) throws ParseExceptions, LogicalExceptions {
		Computer computer = Mapper.ComputerDTOToComputer(pcDTO);
		this.checkAddNewComputer(computer);
		return pcdao.insertNewComputer(computer);
	}
	
	private void checkAddNewComputer (Computer computer) throws LogicalExceptions{
		ArrayList<Optional<LogicalProblem>> problems = new ArrayList<Optional<LogicalProblem>>();
		problems.add(LogicalChecker.dateValidationChecking(computer.getIntroduced(), computer.getDiscontinued()));
		if(computer.getcompany() != null) {
			problems.add(LogicalChecker.companyIsUnknownChecking(computer.getcompany()));
		}
		LogicalExceptions except = new LogicalExceptions(problems.stream().filter(problem -> problem.isPresent())
																		  .map(pb -> pb.get())
																		  .collect(Collectors.toList()));
		if(!except.getExceptions().isEmpty()) {
			throw except;
		}
	}
	
	public int updateComputer(ComputerDTO pcDTO) throws ParseExceptions, LogicalExceptions {
		Computer computer = Mapper.ComputerDTOToComputer(pcDTO);
		this.checkUpdateComputer(computer);
		return pcdao.updateComputer(computer);
	}
	
	private void checkUpdateComputer (Computer computer) throws LogicalExceptions {
		ArrayList<Optional<LogicalProblem>> problems = new ArrayList<Optional<LogicalProblem>>();
		Optional<Computer> optCheckingPc = pcdao.getOneComputers(computer.getPcId());
		problems.add(LogicalChecker.idGivenChecking(computer, pcdao));
		problems.add(LogicalChecker.dateValidationChecking(computer.getIntroduced(), computer.getDiscontinued()));
		if(problems.isEmpty()) {
			Computer oldComputer = optCheckingPc.get();
			problems.add(LogicalChecker.dateValidationChecking(computer.getIntroduced(), oldComputer.getDiscontinued()));
			problems.add(LogicalChecker.dateValidationChecking(oldComputer.getIntroduced(), computer.getDiscontinued()));
		}
		problems.add(LogicalChecker.companyIsUnknownChecking(computer.getcompany()));
		LogicalExceptions except = new LogicalExceptions(problems.stream().filter(problem -> problem.isPresent())
				  											     .map(pb -> pb.get())
				  											     .collect(Collectors.toList()));
		if(!except.getExceptions().isEmpty()) {
				throw except;
		}
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

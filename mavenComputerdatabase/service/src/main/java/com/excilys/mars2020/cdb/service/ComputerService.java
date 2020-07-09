package com.excilys.mars2020.cdb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.mars2020.cdb.dto.ComputerDTO;
import com.excilys.mars2020.cdb.exceptions.LogicalExceptions;
import com.excilys.mars2020.cdb.exceptions.LogicalProblem;
import com.excilys.mars2020.cdb.exceptions.ParseExceptions;
import com.excilys.mars2020.cdb.mapper.ComputerMapper;
import com.excilys.mars2020.cdb.model.Computer;
import com.excilys.mars2020.cdb.model.Pagination;
import com.excilys.mars2020.cdb.persistence.ComputerDAO;
import com.excilys.mars2020.cdb.persistence.OrderByPossibilities;
import com.excilys.mars2020.cdb.validations.LogicalChecker;

/**
 * Class used as intermediate between ComputerDAO and Ui
 * Display and checking integrity of users requests
 * @author remi
 *
 */
@Service
public class ComputerService {
	
	@Autowired
	private ComputerDAO pcdao;
	
	@Autowired
	private ComputerMapper pcMapper;
	
	@Autowired
	private LogicalChecker logicCheck;
	
	
	public ComputerService(ComputerDAO pcDAO) { }

	/**
	 * 
	 * @return All the computers in the db
	 */
	public List<ComputerDTO> getAllComputers() {
		return pcdao.getAllComputersRequest().stream().map(computer -> pcMapper.computerToComputerDTO(computer)).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @param page
	 * @return Computers in the page
	 */
	public List<ComputerDTO> getPageComputers(Pagination page, OrderByPossibilities order){
		return pcdao.getPageComputersRequest(page, order).stream().map(computer -> pcMapper.computerToComputerDTO(computer)).collect(Collectors.toList());
	}
	
	/**
	 * 
	 * @return number of Computer in the database
	 */
	public long getCountComputers() {
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
	
	/**
	 * Search and retrieve all the PC with name or CompanyName = givenName 
	 * @param name
	 * @return the list containing the requested PCs as DTO
	 */
	public List<ComputerDTO> getComputersByName(String name){
		return pcdao.searchComputersByName(name).stream().map(computer -> pcMapper.computerToComputerDTO(computer)).collect(Collectors.toList());
	}
	
	public List<Computer> getComputerByCompanyId(long id){
		return pcdao.getComputerByCompanyId(id);
	}
	
	public int addNewComputer (ComputerDTO pcDTO) throws ParseExceptions, LogicalExceptions {
		Computer computer = pcMapper.ComputerDTOToComputer(pcDTO);
		this.checkAddNewComputer(computer);
		return pcdao.insertNewComputer(computer);
	}
	
	private void checkAddNewComputer (Computer computer) throws LogicalExceptions{
		ArrayList<Optional<LogicalProblem>> problems = new ArrayList<Optional<LogicalProblem>>();
		problems.add(logicCheck.dateValidationChecking(computer.getIntroduced(), computer.getDiscontinued()));
		if(computer.getcompany() != null) {
			problems.add(logicCheck.companyIsUnknownChecking(computer.getcompany()));
		}
		LogicalExceptions except = new LogicalExceptions(problems.stream().filter(problem -> problem.isPresent())
																		  .map(pb -> pb.get())
																		  .collect(Collectors.toList()));
		if(!except.getExceptions().isEmpty()) {
			throw except;
		}
	}
	
	public int updateComputer(ComputerDTO pcDTO) throws ParseExceptions, LogicalExceptions {
		Computer computer = pcMapper.ComputerDTOToComputer(pcDTO);
		this.checkUpdateComputer(computer);
		return pcdao.updateComputer(computer);
	}
	
	private void checkUpdateComputer (Computer computer) throws LogicalExceptions {
		ArrayList<Optional<LogicalProblem>> problems = new ArrayList<Optional<LogicalProblem>>();
		Optional<Computer> optCheckingPc = pcdao.getOneComputers(computer.getPcId());
		problems.add(logicCheck.idGivenChecking(computer, pcdao));
		problems.add(logicCheck.dateValidationChecking(computer.getIntroduced(), computer.getDiscontinued()));
		if(problems.isEmpty()) {
			Computer oldComputer = optCheckingPc.get();
			problems.add(logicCheck.dateValidationChecking(computer.getIntroduced(), oldComputer.getDiscontinued()));
			problems.add(logicCheck.dateValidationChecking(oldComputer.getIntroduced(), computer.getDiscontinued()));
		}
		problems.add(logicCheck.companyIsUnknownChecking(computer.getcompany()));
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

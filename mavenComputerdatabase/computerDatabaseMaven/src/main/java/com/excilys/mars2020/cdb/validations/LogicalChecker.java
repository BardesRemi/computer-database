package com.excilys.mars2020.cdb.validations;

import java.time.LocalDate;
import java.util.Optional;

import com.excilys.mars2020.cdb.exceptions.LogicalProblem;
import com.excilys.mars2020.cdb.mapper.Mapper;
import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.Computer;
import com.excilys.mars2020.cdb.persistance.CompanyDAO;
import com.excilys.mars2020.cdb.persistance.ComputerDAO;
import com.excilys.mars2020.cdb.service.CompanyService;

/**
 * 
 * @author remi
 *
 */
public class LogicalChecker {
	
	public static Optional<LogicalProblem> idGivenChecking (Computer computer, ComputerDAO pcdao){
		if(computer.getPcId() == 0l) {
			return Optional.of(LogicalProblem.createNoIdGivenProblem("No Id given for this computer : " + computer.toString()));
		}
		Optional<Computer> optCheckingPc = pcdao.getOneComputers(computer.getPcId());
		if(optCheckingPc.isEmpty()) {
			return Optional.of(LogicalProblem.createInvalidIdGivenProblem("No PC found in the DB with id : " + computer.getPcId()));
		}
		return Optional.empty();
	}

	public static Optional<LogicalProblem> dateValidationChecking (LocalDate intro, LocalDate discont){
		if(intro == null || discont == null) {
			return Optional.empty();
		}
		if(intro.isAfter(discont)) {
			return Optional.of(LogicalProblem.createInvalidDatesProblem(intro.toString() + " is before " + discont.toString()));
		}
		return Optional.empty();
	}
	
	public static Optional<LogicalProblem> companyIsUnknownChecking (Company company){
		CompanyService compServ = new CompanyService(CompanyDAO.getCompanyDAO());
		if(compServ.companyInDb(Mapper.companyToCompanyDTO(company))) {
			return Optional.empty();
		}
		return Optional.of(LogicalProblem.createUnknownCompanyProblem("given company : " + company.getName() + " isn't in the DB !"));
	}
	
}

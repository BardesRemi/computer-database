package com.excilys.mars2020.cdb.mapper;

import java.time.LocalDate;
import java.util.Optional;

import com.excilys.mars2020.cdb.exceptions.ParseExceptions;
import com.excilys.mars2020.cdb.exceptions.ParseProblem;
import com.excilys.mars2020.cdb.exceptions.ParseTypePb;
import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.CompanyDTO;
import com.excilys.mars2020.cdb.model.Computer;
import com.excilys.mars2020.cdb.model.ComputerDTO;

/**
 * 
 * @author remi
 *
 */
public class Mapper {

	
	public static Optional<Long> stringToLong (String number){
		if(number.isEmpty()) {
			return Optional.of(0l);
		}
		try {
			return Optional.of(Long.valueOf(number));
		}
		catch (NumberFormatException nbFormE){
			return Optional.empty();
		}
	}
	
	public static Optional<Company> companyDTOToCompany (CompanyDTO compDTO) throws ParseExceptions{
		if(compDTO.getCompId().isEmpty()) {
			return Optional.empty();
		}
		Optional<Long> idGot = Mapper.stringToLong(compDTO.getCompId());
		if(idGot.isEmpty()) {
			throw new ParseExceptions(ParseProblem.createNotALongProblem("Given company id isn't a long !"));
		}
		return Optional.of(new Company.Builder().compId(idGot.get()).name(compDTO.getName()).build());
	}
	
	public static CompanyDTO companyToCompanyDTO (Company company) {
		return new CompanyDTO.Builder().compId(String.valueOf(company.getCompId())).name(company.getName()).build();
	}
	
	public static Computer ComputerDTOToComputer (ComputerDTO pcDTO) throws ParseExceptions{
		ParseExceptions parseExcept = new ParseExceptions();
		System.out.println(pcDTO);
		Optional<Long> pcIdGot = Mapper.stringToLong(pcDTO.getPcId());
		System.out.println(pcIdGot.orElse(null));
		Optional<LocalDate> introDateGot = DateAPI.stringToLocalDate(pcDTO.getIntroduced());
		Optional<LocalDate> discontinuedDateGot = DateAPI.stringToLocalDate(pcDTO.getDiscontinued());
		CompanyDTO compDTO = new CompanyDTO.Builder().compId(pcDTO.getCompanyId()).name(pcDTO.getCompanyName()).build();
		Optional<Company> comp = computerDTOToComputerTesting(pcDTO, parseExcept, pcIdGot, introDateGot, discontinuedDateGot, compDTO);
		if(!parseExcept.getExceptions().isEmpty()) {
			throw parseExcept;
		}
		return new Computer.Builder(pcDTO.getName())
				   .pcId(pcIdGot.orElse(null))
				   .introduced(introDateGot.orElse(null))
				   .discontinued(discontinuedDateGot.orElse(null))
				   .company(comp.orElse(null))
				   .build();
	}

	private static Optional<Company> computerDTOToComputerTesting(ComputerDTO pcDTO, ParseExceptions parseExcept, Optional<Long> pcIdGot,
			Optional<LocalDate> introDateGot, Optional<LocalDate> discontinuedDateGot, CompanyDTO compDTO)
			throws ParseExceptions {
		if(pcDTO.getName().isEmpty()) {
			parseExcept.add(ParseProblem.createNoNameProblem("A computer should have a name !"));
		}
		if(pcIdGot.isEmpty()) {
			parseExcept.add(ParseProblem.createNotALongProblem("Given id isn't a long !"));
		}
		if(introDateGot.isEmpty() && !pcDTO.getIntroduced().isEmpty()) {
			parseExcept.add(ParseProblem.createNotDateProblem("Given Introduced date isn't a valid format !"));
		}
		if(discontinuedDateGot.isEmpty() && !pcDTO.getDiscontinued().isEmpty()) {
			parseExcept.add(ParseProblem.createNotDateProblem("Given Discontinued date isn't a valid format !"));
		}
		try {
			return Mapper.companyDTOToCompany(compDTO);
		}
		catch (ParseExceptions companyParseExcept) {
			companyParseExcept.getExceptions().stream().forEach(parseExcept::add);
			throw parseExcept;
		}
	}
	
	public static ComputerDTO computerToComputerDTO (Computer computer) {
		return new ComputerDTO.Builder(computer.getName())
							  .pcId(String.valueOf(computer.getPcId()))
							  .introduced(String.valueOf(computer.getIntroduced()))
							  .discontinued(String.valueOf(computer.getDiscontinued()))
							  .companyId(String.valueOf(computer.getcompany().getCompId()))
							  .companyName(computer.getcompany().getName())
							  .build();
	}
}

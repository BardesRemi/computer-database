package com.excilys.mars2020.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
		if(number == null || number.isEmpty()) {
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
		if(compDTO == null) {
			return Optional.empty();
		}
		if(compDTO.getCompId() == null) {
			return Optional.empty();
		}
		Optional<Long> idGot = Mapper.stringToLong(compDTO.getCompId());
		if(idGot.isEmpty()) {
			throw new ParseExceptions(ParseProblem.createNotALongProblem("Given company id isn't a long !"));
		}
		return Optional.of(new Company.Builder().compId(idGot.get()).name(compDTO.getName()).build());
	}
	
	public static CompanyDTO companyToCompanyDTO (Company company) {
		if(company == null) {
			return null;
		}
		return new CompanyDTO.Builder().compId(String.valueOf(company.getCompId())).name(company.getName()).build();
	}
	
	public static Computer ComputerDTOToComputer (ComputerDTO pcDTO) throws ParseExceptions{
		Optional<Long> pcIdGot = Mapper.stringToLong(pcDTO.getPcId());
		Optional<LocalDate> introDateGot = DateMapper.stringToLocalDate(pcDTO.getIntroduced());
		Optional<LocalDate> discontinuedDateGot = DateMapper.stringToLocalDate(pcDTO.getDiscontinued());
		CompanyDTO compDTO = pcDTO.getCompanyDTO();
		Optional<Company> comp = companyDTOToCompany(compDTO);
		return new Computer.Builder(pcDTO.getName())
				   .pcId(pcIdGot.orElse(null))
				   .introduced(introDateGot.orElse(null))
				   .discontinued(discontinuedDateGot.orElse(null))
				   .company(comp.orElse(null))
				   .build();
	}
	
	public static ComputerDTO computerToComputerDTO (Computer computer) {
		return new ComputerDTO.Builder(computer.getName())
							  .pcId(String.valueOf(computer.getPcId()))
							  .introduced(computer.getIntroduced()==null ? null : computer.getIntroduced().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
							  .discontinued(computer.getDiscontinued()==null ? null : computer.getDiscontinued().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
							  .company(Mapper.companyToCompanyDTO(computer.getcompany()))
							  .build();
	}
}

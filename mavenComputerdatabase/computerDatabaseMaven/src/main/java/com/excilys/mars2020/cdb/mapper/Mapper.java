package com.excilys.mars2020.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.mars2020.cdb.exceptions.ParseExceptions;
import com.excilys.mars2020.cdb.exceptions.ParseProblem;
import com.excilys.mars2020.cdb.exceptions.ParseTypePb;
import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.CompanyDTO;
import com.excilys.mars2020.cdb.model.Computer;
import com.excilys.mars2020.cdb.model.ComputerDTO;
import com.excilys.mars2020.cdb.spring.SpringConfig;

/**
 * 
 * @author remi
 *
 */

@Component
public class Mapper {
	
	@Autowired
	private DateMapper dateMapper;
	
	public Optional<Long> stringToLong (String number){
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
	
	public Optional<Company> companyDTOToCompany (CompanyDTO compDTO) throws ParseExceptions{
		if(compDTO == null) {
			return Optional.empty();
		}
		if(compDTO.getCompId() == null) {
			return Optional.empty();
		}
		Optional<Long> idGot = this.stringToLong(compDTO.getCompId());
		if(idGot.isEmpty()) {
			throw new ParseExceptions(ParseProblem.createNotALongProblem("Given company id isn't a long !"));
		}
		return Optional.of(new Company.Builder().compId(idGot.get()).name(compDTO.getName()).build());
	}
	
	public CompanyDTO companyToCompanyDTO (Company company) {
		if(company == null) {
			return null;
		}
		return new CompanyDTO.Builder().compId(String.valueOf(company.getCompId())).name(company.getName()).build();
	}
	
	public Computer ComputerDTOToComputer (ComputerDTO pcDTO) throws ParseExceptions{
		Optional<Long> pcIdGot = this.stringToLong(pcDTO.getPcId());
		Optional<LocalDate> introDateGot = dateMapper.stringToLocalDate(pcDTO.getIntroduced());
		Optional<LocalDate> discontinuedDateGot = dateMapper.stringToLocalDate(pcDTO.getDiscontinued());
		CompanyDTO compDTO = pcDTO.getCompany();
		Optional<Company> comp = companyDTOToCompany(compDTO);
		return new Computer.Builder(pcDTO.getName())
				   .pcId(pcIdGot.orElse(null))
				   .introduced(introDateGot.orElse(null))
				   .discontinued(discontinuedDateGot.orElse(null))
				   .company(comp.orElse(null))
				   .build();
	}
	
	public ComputerDTO computerToComputerDTO (Computer computer) {
		return new ComputerDTO.Builder(computer.getName())
							  .pcId(String.valueOf(computer.getPcId()))
							  .introduced(computer.getIntroduced()==null ? null : computer.getIntroduced().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
							  .discontinued(computer.getDiscontinued()==null ? null : computer.getDiscontinued().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
							  .company(this.companyToCompanyDTO(computer.getcompany()))
							  .build();
	}
}

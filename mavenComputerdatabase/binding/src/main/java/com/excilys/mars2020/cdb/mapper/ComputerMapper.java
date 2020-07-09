package com.excilys.mars2020.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.mars2020.cdb.dto.CompanyDTO;
import com.excilys.mars2020.cdb.dto.ComputerDTO;
import com.excilys.mars2020.cdb.exceptions.ParseExceptions;
import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.Computer;

@Component
public class ComputerMapper {
	
	@Autowired
	CompanyMapper compMap;
	
	@Autowired
	Mapper mapper;
	
	@Autowired
	DateMapper dateMap;
	
	public Computer ComputerDTOToComputer (ComputerDTO pcDTO) throws ParseExceptions{
		Optional<Long> pcIdGot = mapper.stringToLong(pcDTO.getPcId());
		Optional<LocalDate> introDateGot = dateMap.stringToLocalDate(pcDTO.getIntroduced());
		Optional<LocalDate> discontinuedDateGot = dateMap.stringToLocalDate(pcDTO.getDiscontinued());
		CompanyDTO compDTO = pcDTO.getCompany();
		Optional<Company> comp = compMap.companyDTOToCompany(compDTO);
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
							  .company(compMap.companyToCompanyDTO(computer.getcompany()))
							  .build();
	}
}

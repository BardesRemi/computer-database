package com.excilys.mars2020.cdb.mapper;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.mars2020.cdb.dto.CompanyDTO;
import com.excilys.mars2020.cdb.exceptions.ParseExceptions;
import com.excilys.mars2020.cdb.exceptions.ParseProblem;
import com.excilys.mars2020.cdb.model.Company;

@Component
public class CompanyMapper {
	
	@Autowired
	Mapper mapper;

	public Optional<Company> companyDTOToCompany (CompanyDTO compDTO) throws ParseExceptions{
		if(compDTO == null) {
			return Optional.empty();
		}
		if(compDTO.getCompId() == null) {
			return Optional.empty();
		}
		Optional<Long> idGot = mapper.stringToLong(compDTO.getCompId());
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
}

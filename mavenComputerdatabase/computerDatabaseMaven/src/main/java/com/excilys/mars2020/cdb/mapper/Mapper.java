package com.excilys.mars2020.cdb.mapper;

import com.excilys.mars2020.cdb.exceptions.NameIsEmptyException;
import com.excilys.mars2020.cdb.exceptions.ParseExceptions;
import com.excilys.mars2020.cdb.model.Company;
import com.excilys.mars2020.cdb.model.CompanyDTO;
import com.excilys.mars2020.cdb.model.Computer;
import com.excilys.mars2020.cdb.model.ComputerDTO;

public class Mapper {

	
	public static long stringToLong (String number) throws NumberFormatException{
		return Long.valueOf(number);
	}
	
	public static Company CompanyDTOToCompany (CompanyDTO compDTO) throws NumberFormatException{
		return new Company.Builder().compId(Mapper.stringToLong(compDTO.getCompId())).name(compDTO.getName()).build();
	}
	
	public static Computer ComputerDTOToComputer (ComputerDTO pcDTO) throws ParseExceptions{
		ParseExceptions parseExcept = new ParseExceptions();
		if(pcDTO.getName().isEmpty()) {
			parseExcept.add(new NameIsEmptyException());
		}
		CompanyDTO compDTO = new CompanyDTO.Builder().compId(pcDTO.getCompanyId()).name(pcDTO.getCompanyName()).build();
		return new Computer.Builder(pcDTO.getName())
						   .pcId()
						   .build();
	}
}

package com.excilys.mars2020.cdb.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

/**
 * 
 * @author remi
 *
 */

@Component
public class Mapper {
	
	Mapper(){};
	
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
}

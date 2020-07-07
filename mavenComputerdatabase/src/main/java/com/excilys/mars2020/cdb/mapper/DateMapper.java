package com.excilys.mars2020.cdb.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class DateMapper {
	
	DateMapper(){};
	
	/**
	 * Convert a Timestamp into the corresponding LocalDate
	 * @param tmsp a timestamp
	 * @return optional with a LocalDate inside
	 */
	public Optional<LocalDate> timestampToLocalDate(Timestamp tmsp) {
		if (tmsp == null) {
			return Optional.empty();
		}
		else { 
			return Optional.of(tmsp.toLocalDateTime().toLocalDate());
		}
	}
	
	/**
	 * Convert a LocalDate into the corresponding Timestamp
	 * @param ldate a LocalDate
	 * @return Optional with a timestamp inside
	 */
	public Optional<Timestamp> localDateToTimestamp (LocalDate ldate) {
		if(ldate == null) {
			return Optional.empty();
		}
		return Optional.of(Timestamp.valueOf(ldate.atStartOfDay()));
	}
	
	/**
	 * Convert a String to LocalDate depending of it format
	 * @param sdate a string in form "dd/MM/yyyy" or "yyyy-mm-dd"
	 * @return Optional with the corresponding date in LocalDate format
	 */
	public Optional<LocalDate> stringToLocalDate(String sdate) {
		List<DateTimeFormatter> knownPatterns = new ArrayList<DateTimeFormatter>();
		knownPatterns.add(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		knownPatterns.add(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		if (sdate == null || sdate.isEmpty()) {
			return Optional.empty();
		}
		for(DateTimeFormatter pattern : knownPatterns) {
			try {
				return Optional.of(LocalDate.parse(sdate, pattern));
			}
			catch(Exception except) { }
		}
		//if reached it means no format was found
		return Optional.empty();
	}
}

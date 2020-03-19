package com.excilys.mars2020.cdb.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class DateMapper {

	/**
	 * Convert a Timestamp into the corresponding LocalDate
	 * @param tmsp a timestamp
	 * @return optional with a LocalDate inside
	 */
	public static Optional<LocalDate> timestampToLocalDate(Timestamp tmsp) {
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
	public static Optional<Timestamp> localDateToTimestamp (LocalDate ldate) {
		if(ldate == null) {
			return Optional.empty();
		}
		return Optional.of(Timestamp.valueOf(ldate.atStartOfDay()));
	}
	
	/**
	 * Convert a String in "dd/MM/yyyy" format to LocalDate
	 * @param sdate a string in form "dd/MM/yyyy"
	 * @return Optional with the corresponding date in LocalDate format
	 */
	public static Optional<LocalDate> stringToLocalDate(String sdate) {
		if (sdate.isEmpty()) {
			return Optional.empty();
		}
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			return Optional.of(LocalDate.parse(sdate, formatter));
		}
		catch(Exception except) {
			return Optional.empty();
		}
		
	}
}

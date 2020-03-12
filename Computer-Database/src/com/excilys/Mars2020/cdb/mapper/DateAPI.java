package com.excilys.Mars2020.cdb.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAPI {

	/**
	 * Convert a Timestamp into the corresponding LocalDate (and conserve null case)
	 * @param tmsp
	 * @return tmsp given as LocalDate or null if tmsp=null
	 */
	public static LocalDate timestampToLocalDate(Timestamp tmsp) {
		if(tmsp == null) { return null;} //en reflexion avec les types optionnals
		else { return tmsp.toLocalDateTime().toLocalDate(); }
	}
	
	/**
	 * Convert a LocalDate into the corresponding Timestamp
	 * @param ldate
	 * @return Timestamp
	 */
	public static Timestamp localDateToTimestamp (LocalDate ldate) {
		return Timestamp.valueOf(ldate.atStartOfDay());
	}
	
	/**
	 * Convert a String in "d/MM/yyyy" format to LocalDate
	 * @param sdate
	 * @return the corresponding date in LocalDate format or Null
	 */
	public static LocalDate stringToLocalDate(String sdate) {
		if(sdate.isEmpty()) { return null; }
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
		return LocalDate.parse(sdate, formatter);
	}
}

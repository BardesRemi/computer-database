package com.excilys.mars2020.cdb.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class used for "multiple" Exception at once
 * @author remi
 *
 */
public class ParseProblem {
	
	private String origin;
	private ParseTypePb type;
	
	private ParseProblem(String origin, ParseTypePb type) {
		this.origin = origin;
		this.type = type;
	}
	
	public static ParseProblem createNoNameProblem (String origin) {
		return new ParseProblem (origin, ParseTypePb.NO_NAME);
	}
	
	public static ParseProblem createNotDateProblem (String origin) {
		return new ParseProblem (origin, ParseTypePb.IS_NOT_A_DATE);
	}
	
	public static ParseProblem createNotALongProblem (String origin) {
		return new ParseProblem (origin, ParseTypePb.IS_NOT_A_LONG);
	}
	
	@Override
	public String toString () {
		return this.origin;
	}
	
	
	public void toLog() {
		Logger logger = LoggerFactory.getLogger(ParseProblem.class);
	    logger.info(this.type.getPbText());

	}
}

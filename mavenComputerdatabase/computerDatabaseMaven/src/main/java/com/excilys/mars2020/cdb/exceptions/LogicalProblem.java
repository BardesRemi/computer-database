package com.excilys.mars2020.cdb.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author remi
 *
 */
public class LogicalProblem {

	private String origin;
	private LogicalTypePb type;
	
	private LogicalProblem(String origin, LogicalTypePb type) {
		this.origin = origin;
		this.type = type;
	}
	
	public static LogicalProblem createInvalidDatesProblem (String origin) {
		return new LogicalProblem (origin, LogicalTypePb.INVALID_DATES);
	}
	
	public static LogicalProblem createUnknownCompanyProblem (String origin) {
		return new LogicalProblem (origin, LogicalTypePb.UNKNOWN_COMPANY);
	}
	
	public static LogicalProblem createNoIdGivenProblem (String origin) {
		return new LogicalProblem (origin, LogicalTypePb.NO_ID_GIVEN);
	}
	
	public static LogicalProblem createInvalidIdGivenProblem (String origin) {
		return new LogicalProblem (origin, LogicalTypePb.INVALID_ID_GIVEN);
	}
	
	@Override
	public String toString () {
		return this.origin;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LogicalProblem other = (LogicalProblem) obj;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public void toLog() {
		Logger logger = LoggerFactory.getLogger(ParseProblem.class);
	    logger.info(this.type.getPbText());

	}
}

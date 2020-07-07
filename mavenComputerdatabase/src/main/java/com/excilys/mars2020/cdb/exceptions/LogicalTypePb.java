package com.excilys.mars2020.cdb.exceptions;

/**
 * 
 * @author remi
 *
 */
public enum LogicalTypePb {
	INVALID_DATES ("Dates are invalide"),
	UNKNOWN_COMPANY ("The company is unknown"),
	NO_ID_GIVEN ("No given ID"),
	INVALID_ID_GIVEN ("The ID given should in the Database");
	
	private final String pbText;
	
	private LogicalTypePb (String str) {
		this.pbText = str;
	}
	
	public String getPbText() {
		return this.pbText;
	}
}

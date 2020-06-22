package com.excilys.mars2020.cdb.exceptions;

/**
 * 
 * @author remi
 *
 */
public enum ParseTypePb {
	NO_NAME ("No Name"),
	IS_NOT_A_LONG ("Not a valid long"),
	IS_NOT_A_DATE ("Not a valid Date");
	
	private final String pbText;
	
	private ParseTypePb (String str) {
		this.pbText = str;
	}
	
	public String getPbText() {
		return this.pbText;
	}
}

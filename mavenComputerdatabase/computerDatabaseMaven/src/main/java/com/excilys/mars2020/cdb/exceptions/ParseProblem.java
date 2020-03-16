package com.excilys.mars2020.cdb.exceptions;

public class ParseProblem {
	
	private String origin;
	private ParseTypePb type;
	
	private ParseProblem(String origin, ParseTypePb type) {
		this.origin = origin;
		this.type = type;
	}
	
	public ParseProblem createNoNameProblem (String origin) {
		return new ParseProblem (origin, ParseTypePb.NO_NAME);
	}
	
	public ParseProblem createNotDateProblem (String origin) {
		return new ParseProblem (origin, ParseTypePb.IS_NOT_A_DATE);
	}
	
	public ParseProblem createNotALongProblem (String origin) {
		return new ParseProblem (origin, ParseTypePb.IS_NOT_A_LONG);
	}
	
	@Override
	public String toString () {
		return this.origin + " " + this.type.getPbText();
	}
	
}

package com.excilys.mars2020.cdb.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Exception class for parse errors
 * @author remi
 *
 */
public class ParseExceptions extends Exception {
	
	private List<ParseProblem> allProblems;
	
	public ParseExceptions() {
		allProblems = new ArrayList<ParseProblem>();
	}
	
	public ParseExceptions(ParseProblem except) {
		allProblems = new ArrayList<ParseProblem>();
		allProblems.add(except);
	}
	
	public void add(ParseProblem prob) {
		allProblems.add(prob);
	}
	
	public List<ParseProblem> getExceptions(){
		return allProblems;
	}
}

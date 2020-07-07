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
	
	public ParseExceptions(List<ParseProblem> excepts) {
		allProblems = excepts;
	}
	
	public void add(ParseProblem prob) {
		allProblems.add(prob);
	}
	
	public List<ParseProblem> getExceptions(){
		return allProblems;
	}
	
	/**
	 * @return all the different messages of all exceptions
	 */
	@Override
	public String getMessage () {
		return allProblems.stream().map( except -> except.toString() ).reduce("", (result, except) -> result + " \n" + except);
	}
}

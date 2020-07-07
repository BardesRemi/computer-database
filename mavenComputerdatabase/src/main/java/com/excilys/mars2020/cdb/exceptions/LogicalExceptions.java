package com.excilys.mars2020.cdb.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author remi
 *
 */
public class LogicalExceptions extends Exception {
	
	private List<LogicalProblem> allProblems;
	
	public LogicalExceptions() {
		allProblems = new ArrayList<LogicalProblem>();
	}
	
	public LogicalExceptions(LogicalProblem except) {
		allProblems = new ArrayList<LogicalProblem>();
		allProblems.add(except);
	}
	
	public LogicalExceptions(List<LogicalProblem> excepts) {
		allProblems = excepts;
	}
	
	public void add(LogicalProblem prob) {
		allProblems.add(prob);
	}
	
	public List<LogicalProblem> getExceptions(){
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

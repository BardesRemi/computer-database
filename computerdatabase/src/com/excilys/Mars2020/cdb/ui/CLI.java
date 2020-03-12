package com.excilys.Mars2020.cdb.ui;

import java.util.List;
import java.util.Optional;

import com.excilys.Mars2020.cdb.model.Computer;
/**
 * Class managing all the displays and user interactions
 * @author remi
 *
 */
public class CLI {
	
	public CLI () {}
	
	/**
	 * Generic version of DisplayArrayList
	 * @param <T> the type of the data being displayed (need to have .toString() method)
	 * @param array
	 */
	public static <T> void displayList(List<T> array) {
		for (T o : array) {
			System.out.println(o.toString());
		}
	}
	
	/**
	 * Display the result of a specific request for 1 PC
	 * @param pcOpt
	 * @param idSearched
	 */
	public void displayComputerOption(Optional<Computer> pcOpt, long idSearched) {
		if (pcOpt.isEmpty()) {
			System.out.format("no PC with id = %d founded \n", idSearched);
		}
		else {
			System.out.println(pcOpt.get().toString());
		}
	}
	
	/**
	 * Display the result of an insertion of a specified PC
	 * and eventually why the insertion wasn't made
	 * @param result
	 */
	public void displayInsertComputer(int result) {
		switch (result) {
		case 0:
			System.out.println("The computer name shouldn't be empty");
			break;
		case -1:
			System.out.println("Given dates aren't logical, please check them");
			break;
		case -2:
			System.out.println("The Company Id is needed if you give a name to the company");
			break;
		case -3:
			System.out.println("Given Company isn't in the Database, please check it");
			break;
		default:
			System.out.println("new Computer added to the database with id : " + result);
			break;
		}
	}
	
	/**
	 * Display the result of an update of a specified PC
	 * and eventually why the update wasn't made.
	 * @param result
	 */
	public void displayUpdateComputer(int result) {
		switch(result) {
		case 0:
			System.out.println("Given Computer Id shouldn't be empty !");
			break;
		case -1:
			System.out.println("No Computer matching the requested one in the BD, please add it before updating");
			break;
		case -2:
			System.out.println("The computer name shouldn't be empty");
			break;
		case -3:
			System.out.println("Given dates aren't logical, please check them");
			break;
		case -4:
			System.out.println("Given dates aren't logical with old dates, please check them");
			break;
		case -5:
			System.out.println("The Company Id is needed if you give a name to the company");
			break;
		case -6:
			System.out.println("Given Company isn't in the Database, please check it");
			break;
		default:
			System.out.println("Update Succesfull !");
			break;
			
		}
	}
	
	/**
	 * Display the result of a delete
	 * @param result
	 */
	public void displayDeleteComputer(int result) {
		switch(result) {
		case 0:
			System.out.println("No PC corresponding to the specified ID, no row deleted");
			break;
		default:
			System.out.println("Delete done correctly");
			break;
			
		}
	}
}

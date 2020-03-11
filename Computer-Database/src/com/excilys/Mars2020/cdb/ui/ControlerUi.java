package com.excilys.Mars2020.cdb.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.excilys.Mars2020.cdb.service.CompanyService;
import com.excilys.Mars2020.cdb.service.ComputerService;

/**
 * Class managing all users interactions and running the program
 * @author remi
 *
 */
public class ControlerUi {
	
	private CompanyService compServ;
	private ComputerService pcServ;
	private CLI view;
	private final Scanner scanner;
	
	private final String startingText = "\n What do you want ? Please choose : \n"
											 + " -> 1- See all the companies in the DB \n"
											 + " -> 2- See all the computers in the DB \n"
											 + " -> 3- See one specific computer in the DB \n"
											 + " -> 4- Add a new computer in the DB \n"
											 + " -> 5- Update a specific computer in the DB \n"
											 + " -> 6- Delete a specific computer in the DB \n"
											 + " -> 7- Close this program \n \n";
	private final String endingText = "\n Do you want to make an other action ? (y or n) \n";
	
	public ControlerUi () {
		this.compServ =  new CompanyService();
		this.pcServ = new ComputerService();
		this.view = new CLI();
		this.scanner = new Scanner(System.in);
	}
	
	/**
	 * Starting point of the Ui prog in CLI
	 */
	public void startProgramme (){
		
		try{
			System.out.print(this.startingText);
			int action = Integer.parseInt(scanner.nextLine());
			if(action>0 && action<7) {
				System.out.println("You choose the " + action);
				this.actionSelecter(action);
				this.endAction();
			}
			else if(action==7) {this.closeProgram();}
			else {
				System.out.println("Wrong entry, please use a proposed answer");
			}
		}
		catch(InputMismatchException e) {
			System.out.println("Wrong entry, please use a proposed answer");
		}
		finally {
			this.scanner.close();
		}
	}
	
	/**
	 * Launch the appropriate method 
	 * @param action
	 */
	private void actionSelecter(int action) {
		switch(action) {
		case 1:
			this.displayAllCompany();
			break;
		case 2:
			this.displayAllComputer();
			break;
		case 3:
			this.searchAndDisplayOneComputer();
			break;
		case 4:
			this.uiAddNewComputer();
			break;
		case 5:
			this.uiUpdateComputer();
			break;
		case 6:
			this.uiDeleteComputer();
			break;
		default:
			//shouldn't happened
		}
	}
	/**
	 * Display all the company in the DB
	 */
	private void displayAllCompany() {
		this.view.displayAll(this.compServ.getAllCompanies());
	}
	
	/**
	 * Display all the computer in the DB
	 */
	private void displayAllComputer() {
		this.view.displayAll(this.pcServ.getAllComputers());
	}
	
	/**
	 * Manage user interaction to find and display a specified computer
	 */
	private void searchAndDisplayOneComputer() throws InputMismatchException{
		System.out.println("You want to search a specific computer, please enter a non decimal number as identifier for it : ");
		int id = this.scanner.nextInt();
		this.view.displayComputerOption(this.pcServ.getOneComputer(id), id);
	}
	
	/**
	 * Manage user interaction to create a new computer
	 */
	private void uiAddNewComputer() throws InputMismatchException{
		System.out.println("You want to add a computer, please enter the caracteristics like the following example : ");
		System.out.println("name-dd/MM/yyyy-dd/MM/yyyy-compName-compId");
		String line = this.scanner.nextLine();
		String[] caracs = line.split("-",5);
		this.view.displayInsertComputer(this.pcServ.addNewComputer(caracs[0], caracs[1], caracs[2], caracs[3], caracs[4]));
	}
	
	/**
	 * manage user interaction to update a specific computer
	 */
	private void uiUpdateComputer() throws InputMismatchException{
		System.out.println("You want to update a computer, please enter the caracteristics like the following example : ");
		System.out.println("name-id-dd/MM/yyyy-dd/MM/yyyy-compName-compId");
		String line = this.scanner.nextLine();
		String[] caracs = line.split("-",6);
		this.view.displayInsertComputer(this.pcServ.updateComputer(caracs[0], caracs[1], caracs[2], caracs[3], caracs[4], caracs[5]));
	}
	
	/**
	 * manage user interaction to delete a specific computer
	 */
	private void uiDeleteComputer() throws InputMismatchException{
		System.out.println("You want to delete a specific computer, please enter a non decimal number as identifier for it : ");
		int id = scanner.nextInt();
		this.view.displayDeleteComputer(this.pcServ.deleteComputer(id));
	}
	
	/**
	 * Ending point of the programme
	 * Can recall start if asked
	 */
	private void endAction(){
		System.out.print(this.endingText);
		String answer = scanner.nextLine();
		if(answer.contentEquals("y")) { this.startProgramme();}
		else if(answer.contentEquals("n")){ this.closeProgram();}
		else {
			System.out.println("invalid answer, default restart");
			this.startProgramme();
		}
	}
	
	/**
	 * Ending the program
	 */
	private void closeProgram() {
		System.out.println("Goodbye !");
	}
}

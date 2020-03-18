package com.excilys.mars2020.cdb.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.excilys.mars2020.cdb.exceptions.ParseExceptions;
import com.excilys.mars2020.cdb.model.ComputerDTO;
import com.excilys.mars2020.cdb.model.Pagination;
import com.excilys.mars2020.cdb.service.CompanyService;
import com.excilys.mars2020.cdb.service.ComputerService;
import com.excilys.mars2020.cdb.ui.CLI;

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
	
	private static final String STARTING_TEXT = "\n What do you want ? Please choose : \n"
											 + " -> 1- See all the companies in the DB \n"
											 + " -> 2- See all the computers in the DB \n"
											 + " -> 3- See one specific computer in the DB \n"
											 + " -> 4- Add a new computer in the DB \n"
											 + " -> 5- Update a specific computer in the DB \n"
											 + " -> 6- Delete a specific computer in the DB \n"
											 + " -> 7- Close this program \n \n";
	private static final String ENDING_TEXT = "\n Do you want to make an other action ? (y or n) \n";
	
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
			System.out.print(STARTING_TEXT);
			int action = Integer.parseInt(scanner.nextLine());
			if (action > 0 && action < 7) {
				System.out.println("You choose the " + action);
				this.actionSelecter(action);
				this.endAction();
			}
			else if (action == 7) {
				this.closeProgram();
			}
			else {
				System.out.println("Wrong entry, please use a proposed answer");
			}
		} catch (InputMismatchException inMismatche) {
			System.out.println("Wrong entry, please use a proposed answer");
		} finally {
			this.scanner.close();
		}
	}
	
	/**
	 * Launch the appropriate method 
	 * @param action
	 */
	private void actionSelecter(int action) {
		switch (action) {
		case 1:
			this.displayCompanies();
			break;
		case 2:
			this.displayComputers();
			break;
		case 3:
			this.searchAndDisplayOneComputer();
			break;
		case 4:
			try {
				this.uiAddNewComputer();
			} catch (InputMismatchException | ParseExceptions except) {
				view.displayProblems(except);
				this.actionSelecter(action);
				except.printStackTrace();
			}
			break;
		case 5:
			try {
				this.uiUpdateComputer();
			} catch (InputMismatchException | ParseExceptions except) {
				view.displayProblems(except);
				this.actionSelecter(action);
				except.printStackTrace();
			}
			break;
		case 6:
			this.uiDeleteComputer();
			break;
		default:
			//shouldn't happened
		}
	}
	/**
	 * Display all the company in the DB with user interactions
	 */
	private void displayCompanies() {
		boolean end = false;
		Pagination currPage = new Pagination(compServ.getCountCompanies());
		while (!end) {
			CLI.displayList(this.compServ.getPageCompanies(currPage));
			end = displayPage(currPage, end);
		}
	}
	
	/**
	 * Display all the computer in the DB with user interaction
	 */
	private void displayComputers() {
		boolean end = false;
		Pagination currPage = new Pagination(pcServ.getCountComputers());
		while (!end) {
			CLI.displayList(this.pcServ.getPageComputers(currPage));
			end = displayPage(currPage, end);
		}
	}

	/**
	 * intermediaite method. Used when displaying a page
	 * @param currPage current page to display and interact with
	 * @param end stop point
	 * @return end
	 */
	private boolean displayPage(Pagination currPage, boolean end) {
		System.out.println("*------------------------------------------------------------------*");
		System.out.format("page %d / %d | next page : u | prev page : d | quit : q \n", currPage.getActualPageNb(), currPage.getMaxPages());
		String line = this.scanner.nextLine();
		switch (line) {
		case "u" :
			currPage.nextPage();
			break;
		case "d" :
			currPage.PrevPage();
			break;
		case "q" :
			end = true;
		default :
			System.out.println("Wrong entry, please use a proposed answer");
		}
		return end;
	}
	
	/**
	 * Manage user interaction to find and display a specified computer
	 */
	private void searchAndDisplayOneComputer() throws InputMismatchException{
		System.out.println("You want to search a specific computer, please enter a non decimal number as identifier for it : ");
		int id = Integer.valueOf(this.scanner.nextLine());
		this.view.displayComputerOption(this.pcServ.getOneComputer(id), id);
	}
	
	/**
	 * Manage user interaction to create a new computer
	 */
	private void uiAddNewComputer() throws InputMismatchException, ParseExceptions{
		System.out.println("You want to add a computer, please enter the caracteristics like the following example : ");
		System.out.println("name-dd/MM/yyyy-dd/MM/yyyy-compName-compId");
		String line = this.scanner.nextLine();
		String[] caracs = line.split("-",5);
		ComputerDTO pcDTO = new ComputerDTO.Builder(caracs[0]).pcId("").introduced(caracs[1]).discontinued(caracs[2]).companyName(caracs[3]).companyId(caracs[4]).build();
		this.view.displayInsertComputer(this.pcServ.addNewComputer(pcDTO));
	}
	
	/**
	 * manage user interaction to update a specific computer
	 */
	private void uiUpdateComputer() throws InputMismatchException, ParseExceptions{
		System.out.println("You want to update a computer, please enter the caracteristics like the following example : ");
		System.out.println("name-id-dd/MM/yyyy-dd/MM/yyyy-compName-compId");
		String line = this.scanner.nextLine();
		String[] caracs = line.split("-",6);
		ComputerDTO pcDTO = new ComputerDTO.Builder(caracs[0]).pcId(caracs[1]).introduced(caracs[2]).discontinued(caracs[3]).companyName(caracs[4]).companyId(caracs[5]).build();
		this.view.displayInsertComputer(this.pcServ.updateComputer(pcDTO));
	}
	
	/**
	 * manage user interaction to delete a specific computer
	 */
	private void uiDeleteComputer() throws InputMismatchException{
		System.out.println("You want to delete a specific computer, please enter a non decimal number as identifier for it : ");
		int id = Integer.valueOf(scanner.nextLine());
		this.view.displayDeleteComputer(this.pcServ.deleteComputer(id));
	}
	
	/**
	 * Ending point of the programme
	 * Can recall start if asked
	 */
	private void endAction(){
		System.out.print(ENDING_TEXT);
		String answer = scanner.nextLine();
		switch (answer) {
		case "y":
			this.startProgramme();
			break;
		case "n":
			this.closeProgram();
			break;
		default :
			System.out.println("invalid answer, default restart");
			this.startProgramme();
			break;
		}
	}
	
	/**
	 * Ending the program
	 */
	private void closeProgram() {
		System.out.println("Goodbye !");
	}
}

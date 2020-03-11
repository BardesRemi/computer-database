package com.excilys.Mars2020.cdb.ui;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.Mars2020.cdb.model.*;
import com.excilys.Mars2020.cdb.persistance.*;
import com.excilys.Mars2020.cdb.service.*;

public class MainClass {

	public static void main(String[] args)  {
		
		CompanyService compServ = new CompanyService();
		ComputerService pcServ = new ComputerService();
		
		System.out.println("*-----------------------------------------------*");
		CLI.displayAll(compServ.getAllCompanies());
		System.out.println("*-----------------------------------------------*");
		CLI.displayAll(pcServ.getAllComputers());
		System.out.println("*-----------------------------------------------*");
		CLI.displayComputerOption(pcServ.getOneComputer(569), 569);
		System.out.println("*-----------------------------------------------*");
		String name = "TestingCreate";
		String introducedD = "";
		String discontinuedD = "";
		String compName = "";
		String compId = "";
		CLI.displayInsertComputer(pcServ.addNewComputer(name, introducedD, discontinuedD, compName, compId));
		System.out.println("*-----------------------------------------------*");
		CLI.displayUpdateComputer(pcServ.updateComputer(name, "588", introducedD, discontinuedD, compName, compId));
		System.out.println("*-----------------------------------------------*");
		CLI.displayDeleteComputer(pcServ.deleteComputer("588"));
		System.out.println("*-----------------------------------------------*");
	}
	
}

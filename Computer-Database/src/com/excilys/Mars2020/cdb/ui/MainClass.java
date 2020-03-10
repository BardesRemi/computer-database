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
		CLI.displayUpdateComputer(pcServ.updateComputer(name, "581", introducedD, discontinuedD, compName, compId));
		/*
		pcTesting.setcompany(new Company("RCA", 3));
		pcTesting.setId(idCreated);
		int updateRes = ComputerDAO.getComputerDAO().updateComputer(pcTesting);
		System.out.println("Mise a jour du PC : " + pcTesting + " dans la bd avec le résultat : " + updateRes);
		Optional<Computer> pcFromQueryTest2 = ComputerDAO.getComputerDAO().getOneComputers(idCreated);
		if(pcFromQueryTest2.isEmpty()) { System.out.format("aucun PC correspondant à %d trouvé", idCreated);}
		else {System.out.println(pcFromQueryTest2.get());}
		System.out.println("*-----------------------------------------------*");
		
		int deleteRes = ComputerDAO.getComputerDAO().deleteComputer(idCreated);
		System.out.println("Suppression du PC : " + pcTesting + " dans la bd avec le resultat : " + deleteRes);
		Optional<Computer> pcFromQueryTest3 = ComputerDAO.getComputerDAO().getOneComputers(idCreated);
		if(pcFromQueryTest3.isEmpty()) { System.out.format("aucun PC correspondant à %d trouvé", idCreated);}
		else {System.out.println(pcFromQueryTest3.get());}
		System.out.println("*-----------------------------------------------*");
		*/
	}
	
}

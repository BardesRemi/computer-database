package com.excilys.Mars2020.cdb.ui;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.Mars2020.cdb.model.*;
import com.excilys.Mars2020.cdb.persistance.*;

public class MainClass {

	public static void main(String[] args)  {
		
		ArrayList<Company> compsFromQuery = CompanyDAO.getCompanyDAO().getAllCompanies();
		for(Company c : compsFromQuery) {
			System.out.println(c);
		}
		System.out.println("*-----------------------------------------------*");
		
		ArrayList<Computer> pcsFromQuery = ComputerDAO.getComputerDAO().getAllComputers();
		for(Computer c : pcsFromQuery){
			System.out.println(c);
		}
		System.out.println("*-----------------------------------------------*");
		
		int idSearched = 569;
		Optional<Computer> pcFromQuery = ComputerDAO.getComputerDAO().getOneComputers(idSearched);
		if(pcFromQuery.isEmpty()) { System.out.format("aucun PC correspondant à %d trouvé", idSearched);}
		else {System.out.println(pcFromQuery.get());}
		System.out.println("*-----------------------------------------------*");
		
		String name = "TestingCreate";
		Computer pcTesting = new Computer.Builder(name).build();
		int idCreated = ComputerDAO.getComputerDAO().addNewComputer(pcTesting);
		System.out.println(pcTesting);
		System.out.format("Un PC nommé : " + name + " à été ajouté a la DB avec l'id : " + idCreated + "\n");
		System.out.println(pcTesting);
		Optional<Computer> pcFromQueryTest1 = ComputerDAO.getComputerDAO().getOneComputers(idCreated);
		if(pcFromQueryTest1.isEmpty()) { System.out.format("aucun PC correspondant à %d trouvé", idCreated);}
		else {System.out.println(pcFromQueryTest1.get());}
		System.out.println("*-----------------------------------------------*");
		
		pcTesting.setcompany(new Company("RCA", 3));
		int updateRes = ComputerDAO.getComputerDAO().updateComputer(idCreated, pcTesting);
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
		
	}
	
}

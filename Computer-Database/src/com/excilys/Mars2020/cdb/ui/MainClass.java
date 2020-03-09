package com.excilys.Mars2020.cdb.ui;

import java.util.ArrayList;
import java.util.Optional;

import com.excilys.Mars2020.cdb.model.*;
import com.excilys.Mars2020.cdb.persistance.*;

public class MainClass {

	public static void main(String[] args)  {
		/*ArrayList<Computer> tab = ComputerDAO.getAllComputers();
		for(Computer c : tab){
			System.out.println(c);
		}*/
		
		int idSearched = 569;
		Optional<Computer> pcFromQuery = ComputerDAO.getOneComputers(idSearched);
		if(pcFromQuery.isEmpty()) { System.out.format("aucun PC correspondant à %d trouvé", idSearched);}
		else {System.out.println(pcFromQuery.get());}
	}
	
}

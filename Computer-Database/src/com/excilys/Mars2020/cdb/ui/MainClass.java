package com.excilys.Mars2020.cdb.ui;

import java.util.ArrayList;

import com.excilys.Mars2020.cdb.model.*;
import com.excilys.Mars2020.cdb.persistance.*;

public class MainClass {

	public static void main(String[] args)  {
		MysqlConnection db = MysqlConnection.getDbConnection();
		if(db!=null) {
			System.out.println("Connection done !");
		}
		ArrayList<Computer> tab = ComputerDAO.getAllComputers();
		System.out.println(tab);
		for(Computer c : tab){
			System.out.println(c);
		}
	}
	
}

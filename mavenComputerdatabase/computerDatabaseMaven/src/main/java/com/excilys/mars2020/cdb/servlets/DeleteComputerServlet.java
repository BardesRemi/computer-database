package com.excilys.mars2020.cdb.servlets;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.excilys.mars2020.cdb.persistance.ComputerDAO;
import com.excilys.mars2020.cdb.service.ComputerService;

@WebServlet("/DeleteComputerServlet")
public class DeleteComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 9191954545L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NumberFormatException{
		doGet(req, resp);
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NumberFormatException{
		
		//getting my service to interact with the DB
		ComputerService pcService = new ComputerService(ComputerDAO.getComputerDAO());
		
		//getting datas
		String listToDelete = req.getParameter("selection");
		
		Arrays.asList(listToDelete.split(",")).stream().map(id -> pcService.deleteComputer(Integer.parseInt(id))).forEach(System.out::println);
		
		RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp");
		rd.forward(req, resp);
		
	}
	
}

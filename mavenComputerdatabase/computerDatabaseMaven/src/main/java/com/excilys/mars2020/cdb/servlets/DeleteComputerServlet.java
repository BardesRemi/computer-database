package com.excilys.mars2020.cdb.servlets;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import com.excilys.mars2020.cdb.persistance.ComputerDAO;
import com.excilys.mars2020.cdb.service.CompanyService;
import com.excilys.mars2020.cdb.service.ComputerService;
import com.excilys.mars2020.cdb.spring.SpringConfig;

@WebServlet("/DeleteComputerServlet")
public class DeleteComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 9191954545L;
	
	private AnnotationConfigApplicationContext appContext = SpringConfig.getContext();
	
	private ComputerService pcService = appContext.getBean(ComputerService.class);
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NumberFormatException{
		doGet(req, resp);
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NumberFormatException{
		
		//getting datas
		String listToDelete = req.getParameter("selection");
		
		Arrays.asList(listToDelete.split(",")).stream().map(id -> pcService.deleteComputer(Integer.parseInt(id))).forEach(System.out::println);
		
		RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp");
		rd.forward(req, resp);
		
	}
	
}

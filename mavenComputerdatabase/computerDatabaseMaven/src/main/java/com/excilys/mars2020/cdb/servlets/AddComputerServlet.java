package com.excilys.mars2020.cdb.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import com.excilys.mars2020.cdb.exceptions.LogicalExceptions;
import com.excilys.mars2020.cdb.exceptions.ParseExceptions;
import com.excilys.mars2020.cdb.mapper.DateMapper;
import com.excilys.mars2020.cdb.mapper.Mapper;
import com.excilys.mars2020.cdb.model.CompanyDTO;
import com.excilys.mars2020.cdb.model.ComputerDTO;
import com.excilys.mars2020.cdb.persistance.CompanyDAO;
import com.excilys.mars2020.cdb.persistance.ComputerDAO;
import com.excilys.mars2020.cdb.service.CompanyService;
import com.excilys.mars2020.cdb.service.ComputerService;
import com.excilys.mars2020.cdb.spring.SpringConfig;

@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 987654321L;
	
	private static AnnotationConfigApplicationContext appContext = SpringConfig.getContext();
	
	private ComputerService pcService = appContext.getBean(ComputerService.class);
	private CompanyService compService = appContext.getBean(CompanyService.class);
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		//no verification need to be done, done before in front
		String pcName = req.getParameter("computerName");
		String introducedDate = req.getParameter("introduced");
		String discontinuedDate = req.getParameter("discontinued");
		String companyId = req.getParameter("companyId");
		CompanyDTO company = null;
		if(companyId != null && !companyId.equals("noCompName")) {
			company = compService.getCompanyById(Mapper.stringToLong(companyId).get());
		}
		ComputerDTO pcToSave = new ComputerDTO.Builder(pcName).introduced(introducedDate).discontinued(discontinuedDate).company(company).build();
		try {
			pcService.addNewComputer(pcToSave);
		} catch (ParseExceptions e) {
			//shouldn't happened if front verification are done correctly
			e.printStackTrace();
		} catch (LogicalExceptions e) {
			//shouldn't happened if front verification are done correctly
			e.printStackTrace();
		}
		doGet(req, resp);
	}
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		List<CompanyDTO> compList = compService.getAllCompanies();
		req.setAttribute("compList", compList);
		
		RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp");
		rd.forward(req, resp);
	}
}

package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.mars2020.cdb.persistance.CompanyDAO;
import com.excilys.mars2020.cdb.persistance.ComputerDAO;
import com.excilys.mars2020.cdb.service.CompanyService;
import com.excilys.mars2020.cdb.service.ComputerService;

@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 987654321L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//do nothing atm
	}
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//getting my service to interact with the DB
		ComputerService pcService = new ComputerService(ComputerDAO.getComputerDAO());
		CompanyService compService = new CompanyService(CompanyDAO.getCompanyDAO());
		
		RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/WEB-INF/addComputer.jsp");
		rd.forward(req, resp);
	}
}

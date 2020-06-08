package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.mars2020.cdb.service.ComputerService;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 123456789L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		doGet(req, resp);
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		try {
			
			//getting my service to interact with the DB
			ComputerService pcService = new ComputerService();
			
			//fetching all the data we need
			System.out.println("pouet");
			req.setAttribute("pcList", pcService.getAllComputers());
			req.setAttribute("pcCount", pcService.getCountComputers());
			
			RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp");
			rd.forward(req, resp);
		
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}

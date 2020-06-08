package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.mars2020.cdb.model.ComputerDTO;
import com.excilys.mars2020.cdb.model.Pagination;
import com.excilys.mars2020.cdb.persistance.ComputerDAO;
import com.excilys.mars2020.cdb.service.ComputerService;

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 123456789L;
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NumberFormatException{
		doGet(req, resp);
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, NumberFormatException{
		try {
			
			//getting my service to interact with the DB
			ComputerService pcService = new ComputerService(ComputerDAO.getComputerDAO());
			
			//getting back datas if some already exists, if not setting those datas
			Object countObject = req.getAttribute("pcCount");
			int count = 0;
			if(countObject == null) {
				count = pcService.getCountComputers();
				req.setAttribute("pcCount", count);
			} else {
				count = (int)countObject;
			}
			
			int currPage = Integer.parseInt(req.getParameter("currPage"));
			if(currPage < 0 || currPage > 5) {
				currPage = 0;
			}
			req.setAttribute("currPage", currPage);
			
			req.setAttribute("pcList", pcService.getPageComputers(new Pagination.Builder(count).maxPages(5).actualPangeNb(currPage).build()));
			
			RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp");
			rd.forward(req, resp);
		
		} catch (NumberFormatException e) {
			throw e;
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
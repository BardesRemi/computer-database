package com.excilys.mars2020.cdb.servlets;

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
import com.excilys.mars2020.cdb.persistance.OrderByPossibilities;
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
			String getCurrPage = req.getParameter("currPage");
			int currPage = 0;
			if(getCurrPage != null) {
				currPage = (int) Math.round(Double.parseDouble(getCurrPage));
			}
			
			String getPageSize = req.getParameter("pageSize");
			int pageSize = 10;
			if(getPageSize != null && !getPageSize.isEmpty()) {
				pageSize = Integer.parseInt(getPageSize);
			}
			
			Pagination page;
			List<ComputerDTO> pcList;
			
			String searchName = (String) req.getParameter("search");
			int count = 0;
			//All the PCs
			if(searchName == null) {
				count = pcService.getCountComputers();
				page = new Pagination.Builder(count).pageSize(pageSize).actualPangeNb(currPage).build();
				String orderBy = (String) req.getParameter("sort");
				req.setAttribute("sort", orderBy);
				if (orderBy == null) {
					pcList = pcService.getPageComputers(page, OrderByPossibilities.ID_UP);
				} else {
					switch (orderBy) {
					case "companyUp":
						pcList = pcService.getPageComputers(page, OrderByPossibilities.COMPANY_UP);
						break;
					case "pcUp":
						pcList = pcService.getPageComputers(page, OrderByPossibilities.PC_UP);
						break;
					default:
						pcList = pcService.getPageComputers(page, OrderByPossibilities.ID_UP);
						break;
					}
				}
			}
			// Searching with a specified name
			else {
				pcList = pcService.getComputersByName(searchName.replace("%", "\\%"));
				count = pcList.size();
				page = new Pagination.Builder(count).maxPages(1).pageSize(count).actualPangeNb(0).build();
			}
			
			req.setAttribute("pcCount", count);
			req.setAttribute("page", page);
			req.setAttribute("pcList", pcList);
			
			RequestDispatcher rd = req.getServletContext().getRequestDispatcher("/WEB-INF/dashboard.jsp");
			rd.forward(req, resp);
		
		} catch (NumberFormatException e) {
			throw e;
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
}

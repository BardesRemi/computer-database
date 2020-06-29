package com.excilys.mars2020.cdb.Controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.mars2020.cdb.model.ComputerDTO;
import com.excilys.mars2020.cdb.model.Pagination;
import com.excilys.mars2020.cdb.persistance.OrderByPossibilities;
import com.excilys.mars2020.cdb.service.ComputerService;

@Controller
public class DashboardController {
	
	@Autowired
	ComputerService pcService;
	
	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	
	@GetMapping("/dashboard")
	public ModelAndView initPage(@RequestParam(name="currPage", required=false, defaultValue="0") String currPage,
						 @RequestParam(name="pageSize", required=false, defaultValue="10") String pageSize,
						 @RequestParam(name="search", required=false, defaultValue="noSearch") String search,
						 @RequestParam(name="sort", required=false, defaultValue="idUp") String sort) {
		ModelAndView model = new ModelAndView("dashboard");
		Pagination page;
		List<ComputerDTO> pcList;
		try {
			int pageS = (int) Math.round(Double.parseDouble(pageSize));
			int currP = Integer.parseInt(currPage);
			
			
			int count = 0;
			if(search.equals("noSearch")) {
				count = pcService.getCountComputers();
				page = new Pagination.Builder(count).pageSize(pageS).actualPangeNb(currP).build();
				switch (sort) {
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
			else {
				pcList = pcService.getComputersByName(search.replace("%", "\\%"));
				count = pcList.size();
				page = new Pagination.Builder(count).maxPages(1).pageSize(count).actualPangeNb(0).build();
			}
			
			model.addObject("pageSize", pageS);
			model.addObject("currPage", currP);
			model.addObject("sort", sort);
			model.addObject("pcCount", count);
			model.addObject("page", page);
			model.addObject("pcList", pcList);
			
			return model;
		} catch(NumberFormatException e) {
			logger.info("Number Format exception from {}", DashboardController.class.getSimpleName());
			return model;
		}
	}
}

package com.excilys.mars2020.cdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.mars2020.cdb.exceptions.LogicalExceptions;
import com.excilys.mars2020.cdb.exceptions.ParseExceptions;
import com.excilys.mars2020.cdb.mapper.Mapper;
import com.excilys.mars2020.cdb.model.CompanyDTO;
import com.excilys.mars2020.cdb.model.ComputerDTO;
import com.excilys.mars2020.cdb.service.CompanyService;
import com.excilys.mars2020.cdb.service.ComputerService;

@Controller
public class AddComputerController {
	

	@Autowired
	private ComputerService pcService;
	@Autowired
	private CompanyService compService;
	@Autowired
	private Mapper mapper;
	
	@GetMapping("/addComputer")
	public ModelAndView initPage(@RequestParam(name="computerName", required=false, defaultValue= "") String pcName,
								 @RequestParam(name="introduced", required=false, defaultValue="") String introducedDate,
								 @RequestParam(name="discontinued", required=false, defaultValue="") String discontinuedDate,
								 @RequestParam(name="companyId", required=false, defaultValue="noCompName") String companyId){
		CompanyDTO company = null;
		if(companyId != null && !companyId.equals("noCompName")) {
			company = compService.getCompanyById(mapper.stringToLong(companyId).get());
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
		
		List<CompanyDTO> compList = compService.getAllCompanies();
		return new ModelAndView("addComputer").addObject("compList", compList);
		
	}
}

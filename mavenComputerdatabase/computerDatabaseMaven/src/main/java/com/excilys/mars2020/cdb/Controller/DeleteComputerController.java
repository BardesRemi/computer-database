package com.excilys.mars2020.cdb.Controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.mars2020.cdb.service.ComputerService;

@Controller
public class DeleteComputerController {
	
	@Autowired
	private ComputerService pcService;
	
	
	@GetMapping("/deleteComputer")
	public ModelAndView initPage(@RequestParam(name="selection", required=true) String listToDelete) {
		
		Arrays.asList(listToDelete.split(",")).stream().map(id -> pcService.deleteComputer(Integer.parseInt(id))).forEach(System.out::println);
		return new ModelAndView("dashboard");
	}
	
}

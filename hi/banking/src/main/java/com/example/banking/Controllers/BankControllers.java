package com.example.banking.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.banking.Entity.BankEntity;
import com.example.banking.Services.DBServices;

@RestController
public class BankControllers {
	
	@GetMapping(path = "/index")
	public ModelAndView getLoginPage()
	{
		String ViewName = "index";
		return new ModelAndView(ViewName);
	}
	
	@GetMapping(path = "/CreateAccountpage")
	public ModelAndView getCreateAccountPage()
	{
		String viewName = "CreateAccountpage";
		return new ModelAndView(viewName);
	}
	
	@Autowired
	DBServices dbservice;
	
	@PostMapping(path = "/CreateAccountpage" )
	public ModelAndView submitCreateAccount(@ModelAttribute BankEntity bankentity)
	{
		dbservice.createAccount(bankentity);
		
		RedirectView rdview = new RedirectView("/index");
		return new ModelAndView(rdview);
	}
}

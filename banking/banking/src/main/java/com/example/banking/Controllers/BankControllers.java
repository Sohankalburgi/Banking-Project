package com.example.banking.Controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.banking.Entity.BankEntity;
import com.example.banking.Entity.InternalBankBalance;
import com.example.banking.Services.DBServices;
import com.example.banking.Services.InternalBankservices;
import com.example.banking.classed.OthersAccountdetails;


import ch.qos.logback.core.model.Model;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import org.thymeleaf.model.IModel;

@RestController
public class BankControllers {
	
	@Autowired
	DBServices dbservice;
	
	@Autowired
	InternalBankservices internalservice;
	

	@GetMapping(path = "/index")
	public ModelAndView getLoginPage()
	{
			String viewName = "index";
			return new ModelAndView(viewName);
	}

	@PostMapping(path = "/index")
	public ModelAndView postofLoginpage(@RequestParam("AccountNumber") String AccountNumber,@RequestParam("SecurityCode") String Password)
	{
		Map<String, String> model = new HashMap<>();
		RedirectView rd = new RedirectView();
		if(AccountNumber!=null) {
			BankEntity check = dbservice.findaccountdetails(AccountNumber);

			if (check != null) {
				if (Password.equals(check.getSecurityCode())) {
					System.out.println("login success");
					rd.setUrl("/Landuppage?AccountNumber=" + AccountNumber);
					return new ModelAndView(rd);
				} else {
					model.put("error", "Invalid Password");
					System.out.println("password is invalid");
					return new ModelAndView("index", model);
				}
			}
			else{
				System.out.println("Please Create New Online Account ");
				model.put("error","Please Create An Account");
				return new ModelAndView("index",model);
			}
		}

		System.out.println("it is invalid ");
		return  new ModelAndView("index");
	}

	
	
	@GetMapping(path = "/CreateAccountpage")
	public ModelAndView getCreateAccountPage()
	{
		String viewName = "CreateAccountpage";
		
		Map<String,BankEntity> model = new HashMap<String, BankEntity>();
		model.put("item",new BankEntity());
		
		return new ModelAndView(viewName,model);
	}
	
	
	
	@PostMapping(path = "/CreateAccountpage" )
	public ModelAndView submitCreateAccount(@Valid @ModelAttribute("item") BankEntity bankentity, BindingResult bindingresult)
	{
		if(bindingresult.hasErrors())
		{
			return new ModelAndView("CreateAccountpage");
		}
		
		bankentity.setBalance(internalservice.getBalance(bankentity.getAccountNumber()));
		dbservice.createAccount(bankentity);
		
		
		RedirectView rdview = new RedirectView();
		rdview.setUrl("/index");
		return new ModelAndView(rdview);
	
		
	}
	

	@GetMapping(path="/Landuppage")
	public ModelAndView getlanduppage(@RequestParam String AccountNumber,@RequestParam(required=false) String SecurityCode)
	{
		String viewName = "Landuppage";
		Map<String,BankEntity> model = new HashMap<>();
		BankEntity details = dbservice.findaccountdetails(AccountNumber);
		model.put("bankfield",details);
		return new ModelAndView(viewName,model);
	}
	
	//right
	@GetMapping(path="/UpdateDetailsPage")
	public ModelAndView getUpdateDetailspage(@RequestParam String AccountNumber)
	{
		
		String viewName = "UpdateDetailsPage";
		BankEntity bankentity = dbservice.findaccountdetails(AccountNumber);
		Map<String, BankEntity> model = new HashMap<>();
		model.put("up",bankentity);
		return new ModelAndView(viewName,model);
	}
	

	//right
	@PostMapping(path="/UpdateDetailsPage")
	public ModelAndView setUpdateDetails(@ModelAttribute("up") BankEntity bankentity)
	{
	    String AccountNumber = bankentity.getAccountNumber();
	    dbservice.UpdateAccountdetails(bankentity, AccountNumber);
	    RedirectView rdview = new RedirectView();
	    rdview.setUrl("/index");
	    return new ModelAndView(rdview);
	}
	
	//right
	@GetMapping(path="/Rtgs")
	public ModelAndView getRtgsPage(@RequestParam String AccountNumber)
	{
		String viewName = "Rtgs";
		Map<String, Object> model = new HashMap<>();
		
		model.put("ot",new OthersAccountdetails());
		return new ModelAndView(viewName);
	}
	
	@PostMapping(path="/Rtgs")
	public ModelAndView postRtgsPage(@ModelAttribute("ot") OthersAccountdetails other)
	{
		String AccountNumber = other.getAccountNumber();
		InternalBankBalance internalofother = internalservice.findAccount(other.getOtherAccountNumber());
		BankEntity currentbankAccount = dbservice.findaccountdetails(AccountNumber);
		
		if(currentbankAccount.getBalance()>=other.getAmount())
		{
			System.out.println("it is the successful transaction");
			double net = currentbankAccount.getBalance()-other.getAmount();
			currentbankAccount.setBalance(net);
			double gain = internalofother.getBalance()+other.getAmount();
			internalofother.setBalance(gain);
			dbservice.createAccount(currentbankAccount);
			internalservice.save(internalofother);
			
			BankEntity internaltoonline = dbservice.findaccountdetails(internalofother.getAccountNumber());
			internaltoonline.setBalance(internalofother.getBalance());
			
			dbservice.createAccount(internaltoonline);
			InternalBankBalance currentinternal = internalservice.findAccount(currentbankAccount.getAccountNumber());
			currentinternal.setBalance(net);
			internalservice.save(currentinternal);
			
		}
		else {
			System.out.println("insufficent money");
		}
		
		RedirectView rd = new RedirectView();
		rd.setUrl("/Landuppage?AccountNumber="+currentbankAccount.getAccountNumber());
		return new ModelAndView(rd);
	}
	
	@GetMapping(path="/UPI")
	public ModelAndView getUPIpage(@RequestParam String AccountNumber,@ModelAttribute("PhoneNumber")String PhoneNumber)
	{
		String Phone = dbservice.findaccountdetails(AccountNumber).getPhoneNumber();
		Map<String,Object> model = new HashMap<String, Object>();
		model.put("PhoneNumber", Phone);
		String viewName = "UPI";
		return new ModelAndView(viewName,model);
	}
}

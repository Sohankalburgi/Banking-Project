package com.example.banking.Controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
		Map<String,Object> model = new HashMap<>();
		RedirectView rd = new RedirectView();
		if(AccountNumber!=null) {
			BankEntity check = dbservice.findaccountdetails(AccountNumber);

			if (check != null) {
				if (Password.equals(check.getSecurityCode())) {
					System.out.println("login success");
					rd.setUrl("/Landuppage?AccountNumber=" + AccountNumber);
					return new ModelAndView(rd);
				} else {
					model.put("erroh", "password is invalid");
					System.out.println("either password or AccountNumber is invalid");
					return new ModelAndView("index", model);
				}
			}
			else{
				System.out.println("Please Create New Online Account ");
				model.put("erroh","Please Create An New Online Account");
				return new ModelAndView("index",model);
			}
		}else {
			model.put("erroh", "Ignoreit");
			System.out.println("it is invalid ");
			return new ModelAndView("index", model);
		}
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
		Map<String,String> model = new HashMap<>();
		if(bindingresult.hasErrors())
		{
			return new ModelAndView("CreateAccountpage");
		}
		if(dbservice.checkbankaccountexist(bankentity.getAccountNumber()))
		{
			System.out.println("The Account Already Exists");
			model.put("erroh","Account Already Exists");
			return new ModelAndView("index",model);
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
	public ModelAndView getRtgsPage(@RequestParam("AccountNumber") String account)
	{
		System.out.println(account);
		String viewName = "Rtgs";
		Map<String,Object> model = new HashMap<>();

		model.put("AccountNumber",account);

		return new ModelAndView(viewName,model);
	}

	@PostMapping(path = "/Rtgs")
	public ModelAndView postRtgsPage(@RequestParam("OtherAccountNumber")String AccountNumber,@RequestParam("Amount") String Amount,@RequestParam(name="AccountNumber")String currentAccount){

		System.out.println("Account Number of current Account"+currentAccount);

		InternalBankBalance beneficaryaccount = internalservice.findAccount(AccountNumber);
		Map<String,String> model = new HashMap<>();
        if(AccountNumber.equals(currentAccount))
        {
            System.out.println("Transaction not possible because transfer to yourself");
            model.put("AccountNumber",currentAccount);
			model.put("erroh","Transaction not possible because transfer to yourself");
			return  new ModelAndView("Rtgs",model);
        }
		if(!internalservice.checkAccountExist(AccountNumber))
		{
			System.out.println("Beneficary Account Does not exists in same Bank");
			model.put("AccountNumber",currentAccount);
			model.put("erroh","Beneficary Account Does not exists in same Bank");
			return  new ModelAndView("Rtgs",model);
		}



		System.out.println(currentAccount);
		System.out.println(AccountNumber);

		BankEntity currentbankaccount = dbservice.findaccountdetails(currentAccount);
		double amount = Double.parseDouble(Amount);

		if(currentbankaccount.getBalance()>=amount){
			System.out.println("it is the successful transaction");
			double net = currentbankaccount.getBalance()-amount;
			currentbankaccount.setBalance(net);

			double gain = beneficaryaccount.getBalance()+amount;
			beneficaryaccount.setBalance(gain);
			dbservice.createAccount(currentbankaccount);
			internalservice.save(beneficaryaccount);

			BankEntity beneficaryif = dbservice.findaccountdetails(AccountNumber);
			if(beneficaryif!=null)
			{
				System.out.println("it has also online account ");
				beneficaryif.setBalance(beneficaryaccount.getBalance());
				dbservice.createAccount(beneficaryif);
			}
			InternalBankBalance currentbankentity = internalservice.findAccount(currentAccount);
			currentbankentity.setBalance(net);
			internalservice.save(currentbankentity);
		}
		else{
			model.put("erroh","Insufficient Money");
			model.put("AccountNumber",currentAccount);
			System.out.println("Insufficient Money");
			return new ModelAndView("Rtgs",model);
		}
		model.put("erroh","Transaction Successful");
		RedirectView rd = new RedirectView();

		rd.setUrl("/Landuppage?AccountNumber="+currentAccount);
		return new ModelAndView(rd,model);
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

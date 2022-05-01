package com.billsExchange.api.Controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.billsExchange.api.Service.IBillableService;


@RestController
@RequestMapping("/bills")
public class BillsController 
{

private static final Logger LOGGER=LoggerFactory.getLogger(BillsController.class);
	
@Autowired
private IBillableService billableService;

@GetMapping(value = "/getBill/{billAmount}")
public String getCoinExchangeForBillAmount(@PathVariable("billAmount")int pBillAmount) 
{
	LOGGER.info("Calling through Controller Class:"+this.getClass()+", Method: getCoinExchangeForBillAmount");
	return billableService.getExchangeForBillAmount(pBillAmount); 
}

@GetMapping(value = "/getAvailableChange")
public String getAvailableChange() 
{
	LOGGER.info("Calling through Controller Class:"+this.getClass()+", Method: getAvailableChange");
	return billableService.getRemainingChange(); 
}

@PostMapping(path = "addBill")
public String addBillAmount(@RequestParam(value="billAmount")int billAmount) 
{
	LOGGER.info("Calling through Controller Class:"+this.getClass()+", Method: addBillAmount");
	return billableService.addBill(billAmount); 
}


}

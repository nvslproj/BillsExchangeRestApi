package com.billsExchange.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.test.context.junit4.SpringRunner;

import com.billsExchange.api.Service.BillableService;

@RunWith(SpringRunner.class)
@SpringBootTest()
class BillsExchangeApplicationTests {

	@Autowired
	BillableService service;

	private static final Logger LOGGER=LoggerFactory.getLogger(BillsExchangeApplicationTests.class);
	
	//Contains 10 positive test cases as per work flow
	@Test
	public void test_complete_flow() {
		LOGGER.info("Entered into Class:"+LOGGER.getName()+", Method: test_complete_flow");
		
		//Test_CheckAvailableChange
		String expectedResult = "{0.01=100, 0.05=100, 0.1=100, 0.25=100}";
		String actualResult = service.getRemainingChange();
		assertEquals(expectedResult, actualResult);
		
		//Test_GetChangeForBill
		expectedResult = "Coins provided for the bill are: {0.25=4}";
		actualResult = service.getExchangeForBillAmount(new Integer(1));
		assertEquals(expectedResult, actualResult);
		
		//Test_Unregistered_Bill_Amount
		expectedResult = "Sorry!!! Your billamount(40) is not listed under the RegisteredBills Section";
		actualResult = service.getExchangeForBillAmount(new Integer(40));
		assertEquals(expectedResult, actualResult);
		
		//Test_add_new_bill_amount
		expectedResult = "Bill Amount: 40 is successfully registered!!!";
		actualResult = service.addBill(new Integer(40));
		assertEquals(expectedResult, actualResult);

		//Test_add_already_registered_bill_amount_again
		expectedResult = "Bill Amount: 40 is already registered. No need to register again!!!";
		actualResult = service.addBill(new Integer(40));
		assertEquals(expectedResult, actualResult);

		//Test_GetCompleteChangeForBill
		expectedResult = "Coins provided for the bill are: {0.25=96, 0.1=100, 0.05=100, 0.01=100}";
		actualResult = service.getExchangeForBillAmount(new Integer(40));
		assertEquals(expectedResult, actualResult);
		
		//Test_Unavailability_of_change_for_bill
		expectedResult = "Sorry!!! We don't have enough change for your bill amount";
		actualResult = service.getExchangeForBillAmount(new Integer(50));
		assertEquals(expectedResult, actualResult);
		
		//Test_with_Invalid_Input_Value
		expectedResult = "Please provide proper registered bill number(positive number) as input";
		actualResult = service.getExchangeForBillAmount(new Integer(0));
		assertEquals(expectedResult, actualResult);
		
		LOGGER.info("Exited from Class:"+LOGGER.getName()+", Method: test_complete_flow");
	}
	
	@Test
	public void test_negative_flow() {
		LOGGER.info("Entered into Class:"+LOGGER.getName()+", Method: test_complete_flow");

		//Test_Negative_input_as_registered_Bill_Number
		String expectedResult = "Please provide proper registered bill number(positive number) as input";
		String actualResult = service.getExchangeForBillAmount(-1);
		assertEquals(expectedResult, actualResult);
		
		LOGGER.info("Exited from Class:"+LOGGER.getName()+", Method: test_complete_flow");
	}

}
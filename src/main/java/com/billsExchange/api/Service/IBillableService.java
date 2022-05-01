package com.billsExchange.api.Service;

public interface IBillableService 
{
	String getExchangeForBillAmount(Integer pBillAmount);
	String addBill(Integer billAmount);
	String getRemainingChange();
}

package com.billsExchange.api.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BillableService implements IBillableService {

	private static int maxCoinCount = 100, currentCoinCount = 0;
	private final String allow_only_positive_number_as_bill_number = "Please provide proper registered bill number(positive number) as input";
	private final String change_not_available_for_bill = "Sorry!!! We don't have enough change for your bill amount";
	private final String coins_for_bill_info = "Coins provided for the bill are: ";
	
	private static final Logger LOGGER=LoggerFactory.getLogger(BillableService.class);
	
	private List<Double> availableCoinslist = initiateCoins(new ArrayList<Double>());
	private List<Integer> availableBillslist = initiateBills(new ArrayList<Integer>());
	private Map<Double, Integer> coinRemainingMap = initiateCoinsRemainingMap(availableCoinslist);
	private Map<Double, Integer> coinCombinationMap = new LinkedHashMap<Double, Integer>();


	@Override
	public String getExchangeForBillAmount(Integer pBillAmount) {
		LOGGER.info("Entered into Class:"+LOGGER.getName()+", Method: getExchangeForBillAmount");
		int coinIndex = 1;
		String result = null;
		int totalCount = 0;
		
		if(pBillAmount<=0) {
			return allow_only_positive_number_as_bill_number;
		}
		
		if (!availableBillslist.contains(pBillAmount)) {
			result = "Sorry!!! Your billamount(" + pBillAmount + ") is not listed under the RegisteredBills Section";
			return result;
		}
		
		for(int i=0;i<availableCoinslist.size();i++) {
			double coinDenomination = availableCoinslist.get(i);
			totalCount+=(coinDenomination*coinRemainingMap.get(coinDenomination));
		}
		
		if(pBillAmount>totalCount) {
			return change_not_available_for_bill;
		}

		while (pBillAmount != 0 && coinIndex <= availableCoinslist.size()) {
			int index = availableCoinslist.size() - coinIndex;
			int divCount = (int) (pBillAmount / availableCoinslist.get(index));
			currentCoinCount = coinRemainingMap.get(availableCoinslist.get(index));

			if (divCount > currentCoinCount) {
				coinCombinationMap.put(availableCoinslist.get(availableCoinslist.size() - coinIndex), currentCoinCount);
				coinRemainingMap.put(availableCoinslist.get(availableCoinslist.size() - coinIndex), 0);
				pBillAmount = (int) (pBillAmount
						- availableCoinslist.get(availableCoinslist.size() - coinIndex) * currentCoinCount);
				coinIndex++;

			} else {
				coinCombinationMap.put(availableCoinslist.get(availableCoinslist.size() - coinIndex), divCount);
				coinRemainingMap.put(availableCoinslist.get(availableCoinslist.size() - coinIndex),
						currentCoinCount - divCount);
				pBillAmount = (int) (pBillAmount
						- availableCoinslist.get(availableCoinslist.size() - coinIndex) * divCount);
			}
		}

		result = coinCombinationMap.keySet().stream().map(key -> key + "=" + coinCombinationMap.get(key))
				.collect(Collectors.joining(", ", "{", "}"));

		if (pBillAmount == 0)
			result =  coins_for_bill_info.concat(coinCombinationMap.toString());

		LOGGER.info("Exited from Class:"+LOGGER.getName()+", Method: getExchangeForBillAmount");
		return result;
	}

	@Override
	public String addBill(Integer billAmount) {
		LOGGER.info("Entered into Class:"+LOGGER.getName()+", Method: addBill");
		
		if(availableBillslist.contains(billAmount))
			return "Bill Amount: "+ billAmount + " is already registered. No need to register again!!!";
		availableBillslist.add(billAmount);		
		LOGGER.info("Exited from Class:"+LOGGER.getName()+", Method: addBill");
		
		return "Bill Amount: "+ billAmount + " is successfully registered!!!";
	}

	@Override
	public String getRemainingChange() {
		LOGGER.info("Entered into Class:"+LOGGER.getName()+", Method: getRemainingChange");
		String result = coinRemainingMap.keySet().stream().map(key -> key + "=" + coinRemainingMap.get(key))
				.collect(Collectors.joining(", ", "{", "}"));
		LOGGER.info("Exited from Class:"+LOGGER.getName()+", Method: getRemainingChange");
		return result;
	}

	private List<Integer> initiateBills(List<Integer> availableBillslist) {
		LOGGER.info("Entered into Class:"+LOGGER.getName()+", Method: initiateBills");
		availableBillslist.add(1);
		availableBillslist.add(2);
		availableBillslist.add(5);
		availableBillslist.add(10);
		availableBillslist.add(20);
		availableBillslist.add(50);
		availableBillslist.add(100);
		LOGGER.info("Exited from Class:"+LOGGER.getName()+", Method: initiateBills");
		return availableBillslist;
	}

	private List<Double> initiateCoins(List<Double> availableCoinslist) {
		LOGGER.info("Entered into Class:"+LOGGER.getName()+", Method: initiateCoins");
		availableCoinslist.add(0.01);
		availableCoinslist.add(0.05);
		availableCoinslist.add(0.10);
		availableCoinslist.add(0.25);
		LOGGER.info("Exited from Class:"+LOGGER.getName()+", Method: initiateCoins");
		
		return availableCoinslist;
	}

	private Map<Double, Integer> initiateCoinsRemainingMap(List<Double> availableCoinslist) {
		LOGGER.info("Entered into Class:"+LOGGER.getName()+", Method: initiateCoinsRemainingMap");
		
		coinRemainingMap = new LinkedHashMap<Double, Integer>();
		coinRemainingMap.put(0.01, maxCoinCount);
		coinRemainingMap.put(0.05, maxCoinCount);
		coinRemainingMap.put(0.10, maxCoinCount);
		coinRemainingMap.put(0.25, maxCoinCount);

		LOGGER.info("Exited from Class:"+LOGGER.getName()+", Method: initiateCoinsRemainingMap");
		return coinRemainingMap;
	}

}

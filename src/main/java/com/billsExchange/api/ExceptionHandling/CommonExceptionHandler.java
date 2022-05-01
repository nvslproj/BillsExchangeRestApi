package com.billsExchange.api.ExceptionHandling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

	private static final Logger LOGGER=LoggerFactory.getLogger(CommonExceptionHandler.class);
	
	@ExceptionHandler(value = { NumberFormatException.class })
	public ResponseEntity<ErrorPage> NumberFormatException(NumberFormatException ex) {
		LOGGER.error("Entered into Class:"+LOGGER.getName()+", Method: NumberFormatException");
		String str = "Please provide proper registered bill number as input. Eg: 10";
		ErrorPage message = new ErrorPage(str);
		LOGGER.error("Exited from Class:"+LOGGER.getName()+", Method: NumberFormatException");
		return new ResponseEntity<ErrorPage>(message, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<ErrorPage> CommonException(NumberFormatException ex) {
		LOGGER.error("Entered into Class:"+LOGGER.getName()+", Method: CommonException");
		String str = "Unexpected Exception arises. Error message is: "+ex.getMessage()+", Please provide the proper registered bill in the proper format. Eg: 10 as input string";
		ErrorPage message = new ErrorPage(str);
		LOGGER.error("Exited from Class:"+LOGGER.getName()+", Method: CommonException");
		return new ResponseEntity<ErrorPage>(message, HttpStatus.BAD_REQUEST);
	}

}

package com.ey.filterfiles.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(FolderExceptions.class)
	public ResponseEntity<ErrorResponse> handleFolderNotFoundException(FolderExceptions ex) {
		ErrorResponse response = new ErrorResponse(ex.getMessage());
		LOGGER.error(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(PathNotFoundException.class)
	public ResponseEntity<ErrorResponse> handlePathNotFoundException(PathNotFoundException ex) {
		ErrorResponse response = new ErrorResponse(ex.getMessage());
		LOGGER.error(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex) {
		ErrorResponse response = new ErrorResponse(ex.getMessage());
		LOGGER.error(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}

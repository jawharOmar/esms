package com.joh.esms.controller;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;

import com.joh.esms.exception.CusDataIntegrityViolationException;
import com.joh.esms.exception.ItemExistsException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ ItemExistsException.class })
	public String handleItemExistsException(HttpServletRequest request, ItemExistsException ex, Model model) {
		logger.info("handleItemExistsException->fired");
		logger.info("handleItemExistsException Occurred:: URL=" + request.getRequestURL());
		model.addAttribute("message", ex.getMessage());
		return "itemExistsException";

	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ DataIntegrityViolationException.class })
	public String handleSQLException(HttpServletRequest request, Exception ex, Model model) {
		logger.info("SQLException Occurred:: URL=" + request.getRequestURL());

		if (ex instanceof CusDataIntegrityViolationException) {
			CusDataIntegrityViolationException cex = (CusDataIntegrityViolationException) ex;
			logger.info("cex=" + cex);
			model.addAttribute("message", cex.getCustomMessage());
		}
		ex.printStackTrace();

		return "dataIntigrityException";
	}

	@ExceptionHandler({ MaxUploadSizeExceededException.class })
	public String maxUploadSizeExceptionHandler(HttpServletRequest request, MaxUploadSizeExceededException ex) {
		logger.info("maxUploadSizeExceptionHandler occurred:: URL=" + request.getRequestURL());
		logger.info("Entity refernece=" + ex.getMessage());

		return "maxUploadSizeException";
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ EntityNotFoundException.class })
	public String handleEntityNotFoundException(HttpServletRequest request, EntityNotFoundException ex) {
		logger.info("handleEntityNotFoundException occurred:: URL=" + request.getRequestURL());
		logger.info("Entity refernece=" + ex.getMessage());

		ex.printStackTrace();

		return "entityNotFoundException";
	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({ NoResultException.class })
	public String handleNoResultException(HttpServletRequest request, Exception ex) {
		logger.info("NoResultException occurred:: URL=" + request.getRequestURL());
		return "noResultException";
	}

	// @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	// @ExceptionHandler({ Exception.class })
	// public String handleInternalServerError(HttpServletRequest request, Exception
	// ex) {
	// logger.info("internalServerError occurred:: URL=" + request.getRequestURL());
	// return "internalServerError";
	// }
}
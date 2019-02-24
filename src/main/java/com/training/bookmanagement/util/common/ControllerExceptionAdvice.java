package com.training.bookmanagement.util.common;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.training.bookmanagement.util.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice("com.training.bookmanagement")
@RestController
@Slf4j
public class ControllerExceptionAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<ResponseModel> handleAll(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ResponseModel bookersError = new ResponseModel(CommonConstants.ErrorCode.SERVER_ERROR, ex.getMessage());
        return new ResponseEntity<>(bookersError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<ResponseModel> handleUserNotFoundException(CustomException ex) {
        ResponseModel bookersError = new ResponseModel(CommonConstants.ErrorCode.BAD_REQUEST_ERROR, ex.getMessage());
        return new ResponseEntity<>(bookersError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<ResponseModel> handleUserNotFoundException(EntityNotFoundException ex) {
		ResponseModel bookersError = new ResponseModel(CommonConstants.ErrorCode.ENTITY_NOT_FOUND, ex.getMessage());
		return new ResponseEntity<>(bookersError, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<ResponseModel> handleConstraintViolation(ConstraintViolationException ex) {
        log.error(ex.getMessage(), ex);
		ResponseModel bookersError = new ResponseModel(CommonConstants.ErrorCode.REQUEST_VALIDATION_ERROR, "validation failed");
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
		    bookersError.addError(violation.getLeafBean().toString(), violation.getMessage());
		}
		return new ResponseEntity<>(bookersError, HttpStatus.BAD_REQUEST);
	}

    @ExceptionHandler({MismatchedInputException.class})
    public ResponseEntity<ResponseModel> handleMismatchedInputException(MismatchedInputException ex) {
        log.error(ex.getMessage(), ex);
        ResponseModel bookersError = new ResponseModel(CommonConstants.ErrorCode.REQUEST_VALIDATION_ERROR,
                "Invalid input of type " + ex.getTargetType().getSimpleName());
        return new ResponseEntity<>(bookersError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ DataAccessException.class })
    public ResponseEntity<ResponseModel> handleDataAccessException(DataAccessException ex) {
        log.error(ex.getMessage(), ex);
        ResponseModel bookersError = new ResponseModel(CommonConstants.ErrorCode.DB_ERROR, ex.getMessage());
        return new ResponseEntity<>(bookersError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("Returning HTTP 400 Bad Request", ex);
        ex.printStackTrace();
        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ResponseModel bookersError = new ResponseModel(CommonConstants.ErrorCode.REQUEST_ARGUMENT_INVALID,
                "Error in input request");
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            if(null != error.getDefaultMessage()) {
                bookersError.addError(error.getField(), error.getDefaultMessage());
            }
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            bookersError.addError(error.getObjectName(), error.getDefaultMessage());
        }
        return new ResponseEntity<>(bookersError, HttpStatus.BAD_REQUEST);
    }
}
package com.training.bookmanagement.util.common;

import com.training.bookmanagement.util.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class AbstractBaseController {

    protected <T> ResponseEntity<T> ok() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    protected ResponseEntity<ResponseModel> ok(String message) {
        return new ResponseEntity<>(new ResponseModel(CommonConstants.ErrorCode.SUCCESS, message, null), HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> ok(T message) {
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    protected <T> ResponseEntity<T> created(T message) {
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    protected ResponseEntity<ResponseModel> failure(String message) {
        return new ResponseEntity<>(
                new ResponseModel(CommonConstants.ErrorCode.BAD_REQUEST_ERROR, message, null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage(), e);
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler
    void handleNotFoundException(EntityNotFoundException e, HttpServletResponse response) throws IOException {
        log.error(e.getMessage(), e);
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }
}

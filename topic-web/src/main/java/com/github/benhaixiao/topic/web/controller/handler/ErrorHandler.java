package com.github.benhaixiao.topic.web.controller.handler;

import com.github.benhaixiao.topic.exception.ServiceException;
import com.github.benhaixiao.topic.web.domain.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author xiaobenhai
 *
 */
@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse errorResponse(Exception exception) {
        if(exception instanceof ServiceException){
            return new ErrorResponse(((ServiceException) exception).getStatusCode(),exception.getMessage());
        }
        return new ErrorResponse(900,exception.getMessage());
    }
}




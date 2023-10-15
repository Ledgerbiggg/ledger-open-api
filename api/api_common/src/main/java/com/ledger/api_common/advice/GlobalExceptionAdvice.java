package com.ledger.api_common.advice;

import com.ledger.api_common.Exception.KnowException;
import com.ledger.api_common.response.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionAdvice{
    @ExceptionHandler(KnowException.class)
    public Result<String> handleKnowException(KnowException e) {
        return Result.fail(e.getMessage());
    }
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        return Result.fail(e.getMessage());
    }



}

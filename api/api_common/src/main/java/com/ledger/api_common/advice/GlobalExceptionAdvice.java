package com.ledger.api_common.advice;

import com.ledger.api_common.Exception.KnowException;
import com.ledger.api_common.response.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionAdvice{
    @ExceptionHandler(KnowException.class)
    public Result<String> handleKnowException(KnowException e) {
        return Result.fail(e.getMessage());
    }
}

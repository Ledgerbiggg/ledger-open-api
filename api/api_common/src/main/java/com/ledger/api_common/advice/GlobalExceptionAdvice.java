package com.ledger.api_common.advice;

import com.alibaba.fastjson.JSONException;
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
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }
    @ExceptionHandler(JSONException.class)
    public Result<String> handleJSONException(JSONException e) {
        e.printStackTrace();
        return Result.fail("JSON的格式出现问题");
    }

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        e.printStackTrace();
        return Result.fail(e.getMessage());
    }



}

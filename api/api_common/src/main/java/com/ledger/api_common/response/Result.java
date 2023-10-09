package com.ledger.api_common.response;

import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class Result<T> {

    static final Result<String> Fail = new Result<>(500, "", "");
    static final Result<String> SUCCESS = new Result<>(200, "", "");

    private Integer code = HttpStatus.OK.value();
    private String msg;
    private T data;

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(T data) {
        this.data = data;
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

    public static <T> Result<T> success(String msg, T data) {
        return new Result<>(200, msg, data);
    }

    public static <T> Result<T> fail(T data) {
        return new Result<>(500, "fail", data);
    }

    public static <T> Result<T> fail(T data, String msg) {
        return new Result<>(500, msg, data);
    }

    public static <T> Result<T> fail() {
        return new Result<>(500, "fail", null);
    }

    public static <T> Result<T> fail(T data, String msg, Integer code) {
        return new Result<>(code, msg, data);
    }

    public static <T> Result<T> fail(String msg, Integer code) {
        return new Result<>(code, msg, null);
    }
}

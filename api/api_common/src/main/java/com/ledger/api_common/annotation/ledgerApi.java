package com.ledger.api_common.annotation;

import com.ledger.api_common.enums.Method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ledgerApi {
    String url();
    Method method() default Method.GET;

    String AccessKey();
    String SecretKey();

}

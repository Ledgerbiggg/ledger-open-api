package com.ledger.api_filterConfig.interceptor;


import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Feign拦截器
 */
@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        try {
            // 获取请求对象
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (servletRequestAttributes != null) {
                HttpServletRequest request = servletRequestAttributes.getRequest();
                // 获取当前请求的header，获取到jwt令牌
                if (request.getHeader("Authorization") != null) {
                    String header = request.getHeader("Authorization");
                    template.header("Authorization", header);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
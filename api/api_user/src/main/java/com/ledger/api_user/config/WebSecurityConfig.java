package com.ledger.api_user.config;


import com.ledger.api_user.filter.JwtAuthenticationTokenFilter;
import com.ledger.api_user.filter.ValidateCodeFilter;
import com.ledger.api_user.handler.AppAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private AppAccessDeniedHandler appAccessDeniedHandler;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Resource
    private ValidateCodeFilter validateCodeFilter;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 禁用跨域资源共享 (CORS) 功能
                .cors().disable()
                // 在 UsernamePasswordAuthenticationFilter 之前添加自定义验证码过滤器
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 在 UsernamePasswordAuthenticationFilter 之前添加JWT认证令牌过滤器
                .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
                // 配置权限控制规则
                .authorizeRequests()
                // 任何请求都需要认证通过（需要登录）
                .anyRequest()
                .authenticated()
                .and()
                // 禁用跨站请求伪造 (CSRF) 保护
                .csrf().disable()
                // 配置HTTP响应头部，包括缓存控制(禁用缓存)
                .headers()
                .disable()
                //禁用session 的记住用户的功能
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // 配置异常处理 - 设置身份验证入口点 (AuthenticationEntryPoint)
        http
                .exceptionHandling()
                // 设置身份验证入口点，用于处理未经身份验证的请求(这个可以替代默认的重定向到/login!!!!!!!)
                .authenticationEntryPoint(authenticationEntryPoint);

        // 配置异常处理 - 设置访问被拒绝的处理程序 (AccessDeniedHandler)
        http
                .exceptionHandling()
                // 设置访问被拒绝的处理程序，用于处理未经授权的请求
                .accessDeniedHandler(appAccessDeniedHandler);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",//swagger api json
                "/swagger-resources/configuration/ui",//用来获取支持的动作
                "/swagger-resources",//用来获取api-docs的URI
                "/swagger-resources/configuration/security",//安全选项
                "/swagger-ui.html",
                "/doc.html",
                "/user/captcha",
                "/user/login",
                "/user/logout",
                "/user/register",
                "/order/pay",
                "/order/notify"
                );

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }






}
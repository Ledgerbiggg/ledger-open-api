package com.ledger.api_user.filter;


import com.ledger.api_user.model.domain.SecurityUser;
import com.ledger.api_user.model.domain.UserInfo;
import com.ledger.api_user.service.UserInfoService;
import com.ledger.api_user.service.UserPermissionsService;
import com.ledger.api_user.util.JwtUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private UserPermissionsService userPermissionsService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.secret}")
    private String secret;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/user/login") || request.getRequestURI().equals("/user/register")) {
            filterChain.doFilter(request, response);
            return;
        }
        String header = request.getHeader(tokenHeader);
        if (header != null && header.startsWith(tokenHead)) {
            String token = header.replace(tokenHead + " ", "");
            //是否过期或者有效
            boolean b = JwtUtil.validateJwt(token, secret);
            if (b) {
                String userNameFromToken = JwtUtil.getUserNameFromToken(token, secret);
                if (userNameFromToken != null) {
                    UserInfo userByUsername = userInfoService.getUserByUsername(userNameFromToken);
                    List<String> auth = userPermissionsService.getAuthByUserId(userByUsername.getId());
                    SecurityUser securityUser = new SecurityUser(userByUsername, auth);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(securityUser, null, securityUser.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    filterChain.doFilter(request, response);
                    response.setHeader(tokenHeader, tokenHead + " " + JwtUtil.refreshToken(token, secret));
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}

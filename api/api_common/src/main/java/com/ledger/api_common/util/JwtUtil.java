package com.ledger.api_common.util;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class JwtUtil {

    public static final String CLAIM_KEY_USERNAME = "sub";
    public static final String CLAIM_KEY_CREATE = "create";
    public static final String CLAIM_Authority = "auth";
    public static final Integer EXPIRE_TIME = 20 * 60 * 1000;

    /**
     * 创建一个 JSON Web Token (JWT)。
     *
     * @param secret        用于签名JWT的密钥，确保令牌的完整性和真实性。
     * @return 生成的JWT字符串。
     */
    public static String createJwt(UserDetails userDetails, List<String> auths, String secret) {
        String username = userDetails.getUsername();
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATE, new Date());
        claims.put(CLAIM_Authority, auths);
        return createJwt(claims, secret,userDetails.getUsername(),Calendar.HOUR,30);
    }

    public static String createTempJwt(UserDetails userDetails, List<String> auths, String secret) {
        String username = userDetails.getUsername();
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, username);
        claims.put(CLAIM_KEY_CREATE, new Date());
        claims.put(CLAIM_Authority, auths);
        return createJwt(claims, secret,userDetails.getUsername(),Calendar.MINUTE,10);
    }

    private static String createJwt(Map<String, Object> claims, String secret, String username, Integer hour, Integer minute) {
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(hour, minute);
        Date futureDate = calendar.getTime();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setExpiration(futureDate) //设置过期时间
                .signWith(SignatureAlgorithm.HS512, secret) // 加密签名
                .compact();
    }



    /**
     * 校验jwt的生成的token
     *
     * @param jwtToken token
     * @param secret   密钥
     * @return true:校验成功;false:校验失败
     */

    public static boolean validateJwt(String jwtToken, String secret) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwtToken)
                    .getBody();
            // 在此处添加任何其他的验证逻辑，例如过期时间等
            Date expiration = claims.getExpiration();
            // JWT已过期
            if (expiration == null) {
                return false;
            }
            return !expiration.before(new Date());
        } catch (Exception e) {

            return false;
        }
    }


    /**
     * 从token中获取用户名
     * @param jwtToken token
     * @param secret 秘钥
     * @return 用户名
     */
    public static String getUserNameFromToken(String jwtToken, String secret) {
        Claims claimsFromToken = getClaimsFromToken(jwtToken, secret);
        return claimsFromToken.get(CLAIM_KEY_USERNAME, String.class);
    }
    public static String refreshToken(String jwtToken, String secret) {
        Claims claimsFromToken = getClaimsFromToken(jwtToken, secret);
        return createJwt(claimsFromToken, secret, claimsFromToken.getSubject(),Calendar.HOUR,30);
    }

    public static String getClaimKeyUsernameFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        String token = header.replace("Bearer ", "");
        if (validateJwt(token, "ledger")) {
            return getUserNameFromToken(token, "ledger");
        }else {
            return null;
        }
    }

    /**
     * 从token中获取负载
     * @param jwtToken token
     * @param secret   秘钥
     * @return 荷载
     */
    private static Claims getClaimsFromToken(String jwtToken, String secret) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwtToken)
                    .getBody();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}

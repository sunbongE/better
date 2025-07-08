package com.better.better.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;

public class JwtProvider {
    private final String ERROR_EMAIL_NULL = "******\n Generate Token Failed : email is null \n******";
    private final String secretKey = "better8secretKey";
    private final long expiration = 1000L*60*60*2;  // 2시간

    public String generateToken(String email){
        if(email == null) {
             throw new NullPointerException(ERROR_EMAIL_NULL);
        };

        // TODO : email로 user_info 조회해서 데이터 추가. ex) user_type, user_name
        HashMap<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setSubject(email)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.ES256, secretKey.getBytes())
                .compact();
    }

    public String extracEmail(String token){
        return Jwts.parser()
                .setSigningKey(secretKey.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}



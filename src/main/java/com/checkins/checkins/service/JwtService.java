package com.checkins.checkins.service;

import com.checkins.checkins.request.UserLoginRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtService.class);
    private static final String SECRET_KEY = "614E645267556B58703272357538782F413F4428472B4B6250655368566D5971";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder().setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

//    public String generateToken(UserDetails userDetails) {
//        JwtBuilder jwtBuilder = Jwts.builder();
//        jwtBuilder.setSubject(userDetails.getUsername());
////        jwtBuilder.setSubject(userDetails.getAuthorities().toString());
//        jwtBuilder.setIssuedAt(new Date(System.currentTimeMillis()));
//        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 1000));
//        jwtBuilder.signWith(SignatureAlgorithm.HS256, SECRET_KEY);
//        String token = jwtBuilder.compact();
//        return token;
////        return generateToken(new HashMap<>(), userDetails);
//    }

    public String generateToken(UserLoginRequest userLoginRequest) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setSubject(userLoginRequest.getUsername());
//        jwtBuilder.setSubject(userDetails.getAuthorities().toString());
        jwtBuilder.setIssuedAt(new Date(System.currentTimeMillis()));
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 1000));
        jwtBuilder.signWith(SignatureAlgorithm.HS256, SECRET_KEY);
        String token = jwtBuilder.compact();
        return token;
//        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserLoginRequest userLoginRequest) {
        LOGGER.info(">>>>>>>>>>"+extraClaims.values());
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(extraClaims);
        jwtBuilder.setSubject(userLoginRequest.getUsername());
        jwtBuilder.setIssuedAt(new Date(System.currentTimeMillis()));
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 1000));
        jwtBuilder.signWith(SignatureAlgorithm.HS256, SECRET_KEY);
        String token = jwtBuilder.compact();
        return token;

    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String encodePwd(String userPwd){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(userPwd);
    }


}


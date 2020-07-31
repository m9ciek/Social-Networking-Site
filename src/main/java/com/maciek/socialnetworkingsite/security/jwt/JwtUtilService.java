package com.maciek.socialnetworkingsite.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtUtilService {

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Value("${jwt.token.expiration}")
    private long expirationTime;

    public String createToken(UserDetails userDetails) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime exp = LocalDateTime.now().plusSeconds(expirationTime);

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(exp.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    public String extractUsername(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public Date extractExpirationDate(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return claims.getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpirationDate(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String name = userDetails.getUsername();
        return (!isTokenExpired(token) && extractUsername(token).equals(name));
    }
}

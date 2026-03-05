package net.dimmid.crawler_rs_java.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class JWTUtil {
    @Value("${jwt_token_exp_date:10}")
    private long JWT_TOKEN_EXP_DATE;
    @Value("${issuer:Undefined}")
    private String ISSUER;
    @Value("${jwt_key}")
    private byte[] KEY;

    public JWTUtil() {
    }

    public String generateJWTToken(String username, String... scopes) {
        return this.generateJWTToken(username, Map.of("scopes", scopes));
    }

    public String generateJWTToken(String username, List<String> scopes) {
        return this.generateJWTToken(username, Map.of("scopes", scopes));
    }

    public String generateJWTToken(String username) {
        return this.generateJWTToken(username, Map.of());
    }

    public String generateJWTToken(String username, Map<String, Object> scopes) {
        return Jwts.builder()
                .claims(scopes)
                .subject(username)
                .issuer(this.ISSUER)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(this.JWT_TOKEN_EXP_DATE, ChronoUnit.MINUTES)))
                .signWith(this.getKey()).compact();
    }

    private Key getKey() {
        return Keys.hmacShaKeyFor(this.KEY);
    }
}




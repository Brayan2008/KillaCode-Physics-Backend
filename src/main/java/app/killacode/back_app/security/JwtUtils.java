package app.killacode.back_app.security;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtils {

    @Value("${jwt.privateKey}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    public String generarToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + Long.parseLong(expiration)))
                .signWith(getSigninKey())
                .compact();
    }

    Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        log.info("Key bytes: {}", keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }


    public <T> T getClaim(String token, Function<Claims, T> claimsFuction) {
        Claims claims = extractClaims(token); 

        return claimsFuction.apply(claims);
    }

    public Claims extractClaims(String token) {

        return Jwts.parser()
                .verifyWith((SecretKey) getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean revisarToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith((SecretKey) getSigninKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error("El token {} es invalido", token);
            return false;
        }
    }

}

package com.foresight.usermanagementservicebackend.service;


import com.foresight.usermanagementservicebackend.entity.SystemUser;
import com.foresight.usermanagementservicebackend.entity.UserRole;
import com.foresight.usermanagementservicebackend.exception.ErrorCode;
import com.foresight.usermanagementservicebackend.exception.RuntimeErrorCodedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@NoArgsConstructor
public class JwtService {

    @Value("${secret}")
    private String SECRET;

    public String generateToken(SystemUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",user.getRole());
        return createToken(claims, user.getEmail());
    }

    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 *30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    private Claims extractAllClaims(String token) {
        try {
            return Jwts
                    .parserBuilder()
                    .setSigningKey(getSignKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (JwtException e){
            throw new RuntimeErrorCodedException(ErrorCode.INVALID_AUTHENTICATION_TOKEN);
        }


    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public String extractEmail(String token)throws JwtException {
        return extractClaim(token, Claims::getSubject);
    }
    public String extractRole(String token)throws JwtException {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }


}

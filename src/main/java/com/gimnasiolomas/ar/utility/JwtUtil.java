package com.gimnasiolomas.ar.utility;

import com.gimnasiolomas.ar.entity.MyUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private String SECRET_KEY = "secret";

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJwt(token).getBody();
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public String generateToken(MyUserDetails myUserDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, myUserDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date((System.currentTimeMillis())))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) -----------TODO ver que onda con ésto
                .compact();
    }
    public boolean validateToken(String token, MyUserDetails myUserDetails){
        final String username = extractUserName(token);
        return (username.equals(myUserDetails.getUsername()) && !isTokenExpired(token));
    }
}

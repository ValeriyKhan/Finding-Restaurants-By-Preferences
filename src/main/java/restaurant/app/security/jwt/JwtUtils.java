package restaurant.app.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import restaurant.app.security.property.SecurityPropertyConfiguration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    private final SecurityPropertyConfiguration securityProperty;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(securityProperty.getKey()).build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails, String salt) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails, salt);
    }

    public String generateRefreshToken(UserDetails userDetails, String salt) {
        Map<String, Object> claims = new HashMap<>();
        return createRefreshToken(claims, userDetails, salt);
    }
    public String generateToken(UserDetails userDetails, Map<String, Object> claims, String salt) {
        return createToken(claims, userDetails, salt);
    }

    private String createToken(Map<String, Object> claims, UserDetails userDetails, String salt) {

        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .claim("salt", salt)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(securityProperty.getKey())
                .compact();
    }
    private String createRefreshToken(Map<String, Object> claims, UserDetails userDetails, String salt) {
        return Jwts.builder().setClaims(claims)
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .claim("salt", salt)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 1000))
                .signWith(securityProperty.getKey())
                .compact();
    }

    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}

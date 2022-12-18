package valeriy.khan.Finding.Restaurants.By.Preferences.property;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
//@ConfigurationProperties(prefix = "jwt")
public class SecurityPropertyConfiguration {
    private String key = "testtestvalerasecret1237fffasdj2123";
    public SecretKey getKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }
}

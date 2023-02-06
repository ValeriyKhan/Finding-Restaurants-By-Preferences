package restaurant.app.security.property;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
//@ConfigurationProperties(prefix = "jwt")
public class SecurityPropertyConfiguration {
    private String key = "testtestvaler123dfr4534dfgsa#cst";
    public SecretKey getKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }
}

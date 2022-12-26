package restaurant.app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import restaurant.app.user.User;
import restaurant.app.user.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthenticationConfig implements AuthenticationManager {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getPrincipal() + "";
        String password = authentication.getCredentials() + "";

        Optional<User> optionalAppUser = userRepository.findByUsername(username);
        if (optionalAppUser.isEmpty()) {
            throw new BadCredentialsException("User does not exist");
        }
        User user = optionalAppUser.get();
        if (user.isBlocked()) {
            throw new BadCredentialsException("User is blocked");
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Password does not match");
        }
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(
                username,
                null,
                authorities
        );
    }
}

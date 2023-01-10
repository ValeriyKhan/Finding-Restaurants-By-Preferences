package restaurant.app.security.Filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import restaurant.app.langMessage.Lang;
import restaurant.app.security.jwt.JwtUtils;
import restaurant.app.user.UserRepository;
import restaurant.app.user.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static restaurant.app.threadLocalSingleton.ThreadLocalSingleton.*;

@Component
@RequiredArgsConstructor
public class JwtTokenVerifierFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String langHeader = request.getHeader("LANG");
        final String username;
        setLang(new Lang(Objects.requireNonNullElse(langHeader, "RU")));
        if (authHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            username = jwtUtils.extractUsername(authHeader);
            Claims claims = jwtUtils.extractAllClaims(authHeader);
            String saltFromRequest = (String) claims.get("salt");
            Optional<User> optionalAppUser = userRepository.findByUsername(username);
            if (optionalAppUser.isEmpty()) {
                throw new UsernameNotFoundException("User does not exist");
            }
            User user = optionalAppUser.get();
            if (!Objects.equals(saltFromRequest, user.getSalt())) {
                throw new IllegalStateException("Token can not be trusted!");
            }
            if (user.isBlocked()) {
                throw new IllegalStateException("User is blocked!");
            }
            List<Map<String, String>> authorities = claims.get("authorities", List.class);
            if (Objects.isNull(authorities)) {
                throw new IllegalStateException("Token can not be trusted!");
            }
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities =
                    authorities.stream()
                            .map(a -> new SimpleGrantedAuthority(a.get("authority")))
                            .collect(Collectors.toSet());
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    simpleGrantedAuthorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            setUser(user);
        } catch (JwtException e) {
            throw new IllegalStateException("Token can not be trusted!");
        }
        filterChain.doFilter(request, response);
    }
}

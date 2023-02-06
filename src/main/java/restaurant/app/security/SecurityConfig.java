package restaurant.app.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import restaurant.app.security.Filters.JwtTokenVerifierFilter;

import static org.springframework.http.HttpMethod.*;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenVerifierFilter jwtTokenVerifierFilter;

    @Bean
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtTokenVerifierFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/**/auth/create-admin").permitAll()

                .antMatchers("/**/login").permitAll()

                .antMatchers("/**/register").permitAll()

                .antMatchers(GET, "/**/user/**").hasAuthority("user:read")
                .antMatchers(POST, "/**/user/**").hasAuthority("user:write")
                .antMatchers(DELETE, "/**/user/**").hasAuthority("user:delete")

                .antMatchers(GET, "/**/merchant-place/**").hasAuthority("merchant:read")
                .antMatchers(PUT, "/**/merchant-place/**").hasAuthority("merchant:write")
                .antMatchers(POST, "/**/merchant-place/**").hasAuthority("merchant:write")
                .antMatchers(DELETE, "/**/merchant-place/**").hasAuthority("merchant:delete")

                .antMatchers(GET, "/**/filial/**").hasAuthority("filial:read")
                .antMatchers(PUT, "/**/filial/**").hasAuthority("filial:write")
                .antMatchers(POST, "/**/filial/**").hasAuthority("filial:write")
                .antMatchers(DELETE, "/**/filial/**").hasAuthority("filial:delete")

                .antMatchers(POST, "/**/preference/**").hasAuthority("preference:write")
                .antMatchers(GET, "/**/preference/**").hasAuthority("preference:read")
                .antMatchers(DELETE, "/**/preference/**").hasAuthority("preference:delete")

                .antMatchers(GET, "/**/rating/**").hasAuthority("rating:read")
                .antMatchers(PUT, "/**/rating/**").hasAuthority("rating:write")
                .antMatchers(POST, "/**/rating/**").hasAuthority("rating:write")
                .antMatchers(DELETE, "/**/rating/**").hasAuthority("rating:delete")

                .antMatchers(POST, "/**/lang-message/**").hasAuthority("langMessage:write")
                .antMatchers(GET, "/**/lang-message/**").hasAuthority("langMessage:read")
                .anyRequest()
                .authenticated();
        return http.build();
    }

}

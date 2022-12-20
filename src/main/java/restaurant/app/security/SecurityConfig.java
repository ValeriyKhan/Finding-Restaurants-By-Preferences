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
                .antMatchers("/**/auth/create-admin").denyAll()
                .antMatchers("/**/login").permitAll()
                .antMatchers("/**/register").permitAll()
                .antMatchers(HttpMethod.GET, "/**/user/**").hasAuthority("user:read")
                .antMatchers(HttpMethod.POST, "/**/user/**").hasAuthority("user:write")
                .antMatchers(HttpMethod.DELETE,"/**/user/**").hasAuthority("user:delete")
                .anyRequest()
                .authenticated();
        return http.build();
    }

}

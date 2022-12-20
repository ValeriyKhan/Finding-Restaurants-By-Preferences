package valeriy.khan.Finding.Restaurants.By.Preferences.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import valeriy.khan.Finding.Restaurants.By.Preferences.role.AppUserRole;
import valeriy.khan.Finding.Restaurants.By.Preferences.security.Filters.JwtTokenVerifierFilter;

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

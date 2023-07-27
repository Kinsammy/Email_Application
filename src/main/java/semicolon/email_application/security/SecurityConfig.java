package semicolon.email_application.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import semicolon.email_application.security.filter.EmailAuthenticationFilter;
import semicolon.email_application.security.filter.EmailAuthorizationFilter;
import semicolon.email_application.security.util.JwtUtil;
import semicolon.email_application.security.util.SignKey;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final SignKey signKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        UsernamePasswordAuthenticationFilter authenticationFilter = new EmailAuthenticationFilter(authenticationManager, signKey);
        authenticationFilter.setFilterProcessesUrl("/api/v1/login");
        return http.csrf().disable().cors().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new EmailAuthorizationFilter(signKey), EmailAuthenticationFilter.class)
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "api/v1/sender/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .build();
    }
}

package semicolon.email_application.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.exception.EmailManagementException;
import semicolon.email_application.security.util.JwtUtil;
import semicolon.email_application.security.util.SignKey;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class EmailAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final SignKey signKey;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper mapper = new ObjectMapper();

        AppUser user;
        try{
            user = mapper.readValue(request.getInputStream(), AppUser.class);
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());

            Authentication authenticationResult = authenticationManager.authenticate(authentication);

            if (authenticationResult != null) {
                return getAuthenticated(authenticationResult);
            }
        } catch (IOException exception){
            throw new EmailManagementException(exception.getMessage());
        }
        throw new EmailManagementException("oops!");
    }

    private Authentication getAuthenticated(Authentication authenticationResult) {
        SecurityContextHolder.getContext().setAuthentication(authenticationResult);
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, Object> claims = authResult.getAuthorities().stream()
                .collect(Collectors.toMap(k->"claim", v->v));


        long secondsToAdd = BigInteger.valueOf(60).longValue() *
                BigInteger.valueOf(60).intValue();
        String accessToken = Jwts.builder()
                .setIssuer("Sammy Mail")
                .setIssuedAt(new Date())
                .setClaims(claims)
                .setExpiration(Date.from(Instant.now()
                        .plusSeconds(secondsToAdd)))
                .signWith(signKey.getSignKey(), SignatureAlgorithm.ES256)
                .compact();


        String refreshToken = Jwts.builder()
                .setIssuer("Sammy Mail")
                .setExpiration(Date.from(Instant.now()
                        .plusSeconds(secondsToAdd)))
                .signWith(signKey.getSignKey(), SignatureAlgorithm.ES256)
                .compact();

        Map<String, String > tokens = new HashMap<>();
        tokens.put("access_token", accessToken);
        tokens.put("refresh_token", refreshToken);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), tokens);
    }
}

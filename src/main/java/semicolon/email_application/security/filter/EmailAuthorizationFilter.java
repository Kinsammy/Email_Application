package semicolon.email_application.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import semicolon.email_application.data.dto.response.ApiResponse;
import semicolon.email_application.data.dto.response.EmailResponse;
import semicolon.email_application.security.util.JwtUtil;
import semicolon.email_application.security.util.SignKey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@AllArgsConstructor
public class EmailAuthorizationFilter extends OncePerRequestFilter {
    private final SignKey signKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        String authHeader = request.getHeader(AUTHORIZATION);
        if (!request.getServletPath().equals("/api/v1/login") &&
                !request.getServletPath().equals("/api/v1/sender/**")) {
            if (authHeader != null && authHeader.startsWith("Bearer  ")) {
                String token = request.getHeader(AUTHORIZATION);
                String jwt = token.substring("Bearer ".length());
                try {
                    Claims jwtClaims = Jwts.parserBuilder()
                            .setSigningKey(signKey.getSignKey())
                            .build().parseClaimsJws(jwt).getBody();

                    List<String> roles = new ArrayList<>();
                    jwtClaims.forEach((k, v) -> roles.add(v.toString()));
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(null, null,
                                    roles.stream().map(SimpleGrantedAuthority::new).toList());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception exception) {
                    System.out.println("here!!");
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    mapper.writeValue(response.getOutputStream(), ApiResponse.builder().message("auth failed").build());
                }

            }
            else{
                filterChain.doFilter(request, response);
            }
        }
    }
}

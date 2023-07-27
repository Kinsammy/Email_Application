package semicolon.email_application.security.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtUtil {
    private final String jwtSecret;
}

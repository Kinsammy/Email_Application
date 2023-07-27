package semicolon.email_application.data.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterAppUserRequest {
    private String email;
    private String password;
}

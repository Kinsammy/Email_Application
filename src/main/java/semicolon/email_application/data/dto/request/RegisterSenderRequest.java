package semicolon.email_application.data.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RegisterSenderRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}

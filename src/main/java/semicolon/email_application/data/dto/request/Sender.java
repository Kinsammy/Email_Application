package semicolon.email_application.data.dto.request;

import lombok.*;
import semicolon.email_application.data.models.AppUser;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Sender {
    private String name;
    private String email;
}

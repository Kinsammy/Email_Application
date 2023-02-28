package semicolon.email_application.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterResponse {
    private Long id;
    private String message;
    private int code;
    private boolean isSuccess;
}

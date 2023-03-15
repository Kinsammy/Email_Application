package semicolon.email_application.data.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import semicolon.email_application.data.models.EmailStatus;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EmailResponse {
    private Long id;
    private String message;
    private boolean success;

}

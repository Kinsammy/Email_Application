package semicolon.email_application.data.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SendMailRequest {
    private Sender sender;
    private Recipient to;
    private String subject;
    private String textContent;
}

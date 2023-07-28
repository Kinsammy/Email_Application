package semicolon.email_application.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SendMailRequest {
    private Sender sender;
    private Recipient to;
    private String subject;
    private String textContent;
    private MultipartFile attachments;

}

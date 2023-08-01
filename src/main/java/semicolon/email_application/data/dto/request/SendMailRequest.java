package semicolon.email_application.data.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.data.models.Attachment;


import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SendMailRequest {
    private Sender sender;
    private Recipient recipient;
    private String subject;
    private String body;
    private MultipartFile attachments;

}

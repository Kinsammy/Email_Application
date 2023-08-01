package semicolon.email_application.data.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.data.models.Attachment;


import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SendMailRequest {
    private String senderEmail;
    private String recipientEmail;
    private String subject;
    private String body;
}

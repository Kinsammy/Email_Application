package semicolon.email_application.data.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SendMailRequest {
    private Sender sender;
    private List<Recipient> to = new ArrayList<>();
    private String subject;
    private String textContent;
    private MultipartFile attachments;

}

package semicolon.email_application.service;

import org.springframework.web.multipart.MultipartFile;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.dto.request.SystemEMailRequest;
import semicolon.email_application.data.models.Message;

import java.io.IOException;

public interface IMessageService {
    String sendMessage(SendMailRequest request);
}

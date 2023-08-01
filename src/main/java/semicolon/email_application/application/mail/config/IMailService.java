package semicolon.email_application.application.mail.config;

import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.dto.request.SystemEMailRequest;
import semicolon.email_application.data.models.Message;

public interface IMailService {
    String sendNotification(SystemEMailRequest request);
    String sendMail(SendMailRequest mailRequest);
}

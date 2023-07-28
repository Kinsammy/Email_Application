package semicolon.email_application.application.mail.config;

import semicolon.email_application.data.dto.request.SystemEMailRequest;

public interface IMailService {
    String sendNotification(SystemEMailRequest request);
}

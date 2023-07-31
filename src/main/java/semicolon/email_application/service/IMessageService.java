package semicolon.email_application.service;

import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.dto.request.SystemEMailRequest;
import semicolon.email_application.data.models.Message;

public interface IMessageService {
    String sendMessage(SendMailRequest request);
}

package semicolon.email_application.service;

import semicolon.email_application.data.dto.request.SendMailRequest;

public interface IMessageService {
    String sendMessage(SendMailRequest mailRequest);
}

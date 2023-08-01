package semicolon.email_application.service;

import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.dto.request.SystemEMailRequest;
import semicolon.email_application.data.models.Message;

import java.io.IOException;

public interface IMessageService {
    String sendMessage(SendMailRequest request) throws IOException;
}

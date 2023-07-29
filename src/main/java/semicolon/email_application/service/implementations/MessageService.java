package semicolon.email_application.service.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import semicolon.email_application.application.mail.config.IMailService;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.dto.request.SystemEMailRequest;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.data.models.Message;
import semicolon.email_application.data.repositories.AppUserRepository;
import semicolon.email_application.data.repositories.MessageRepository;
import semicolon.email_application.exception.EmailManagementException;
import semicolon.email_application.service.IMessageService;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService implements IMessageService {
    private final IMailService mailService;
    private final AppUserRepository userRepository;
    private final MessageRepository messageRepository;

    @Override
    public String sendMessage(Message message) {
        var sender = userRepository.findByEmail(message.getSender().getEmail());
        if (sender.isEmpty()){
            throw new EmailManagementException("Sender email not registered.");
        }
        var sendMailRequest = new SendMailRequest();
        sendMailRequest.setSender(message.getSender());
        sendMailRequest.setRecipient(message.getRecipient());
        sendMailRequest.setSubject(message.getSubject());
        sendMailRequest.setBody(message.getBody());
        sendMailRequest.setAttachments(message.getAttachments());
        messageRepository.save(message);
        mailService.sendMail(sendMailRequest);
        return "Message sent.";
    }
}

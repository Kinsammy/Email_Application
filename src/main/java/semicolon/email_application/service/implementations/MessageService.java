package semicolon.email_application.service.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import semicolon.email_application.application.mail.config.IMailService;
import semicolon.email_application.data.dto.request.Recipient;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.dto.request.Sender;
import semicolon.email_application.data.models.Message;
import semicolon.email_application.data.repositories.AppUserRepository;
import semicolon.email_application.data.repositories.AttachmentRepository;
import semicolon.email_application.data.repositories.MessageRepository;
import semicolon.email_application.exception.EmailManagementException;
import semicolon.email_application.service.IMessageService;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService implements IMessageService {
    private final IMailService mailService;
    private final AppUserRepository userRepository;
    private final MessageRepository messageRepository;
    private final AttachmentRepository attachmentRepository;
    private final AppUserService appUserService;


    @Override
    public String sendMessage(SendMailRequest request) {
        var foundSender = userRepository.findByEmail(request.getSender().getEmail());
        if (foundSender.isEmpty()) throw new EmailManagementException(
                String.format("Sender with email %s not fund.", request.getSender())
        );

        var message = Message.builder()
                .sender(foundSender.get())
                .recipientEmail(request.getTo().getEmail())
                .subject(request.getSubject())
                .body(request.getTextContent())
                .timeStamp(LocalDateTime.now().toString())
                .build();
        messageRepository.save(message);
        log.info("Sending email request: {}", request);

        var emailRequest = SendMailRequest.builder()
                .sender(new Sender(foundSender.get().getName(), foundSender.get().getEmail()))
                .to(new Recipient(request.getTo().getName(), request.getTo().getEmail()))
                .subject(request.getSubject())
                .textContent(request.getTextContent())
                .build();
        mailService.sendMail(emailRequest);
        log.info("Sending email request: {}", request);

        try {
            mailService.sendMail(emailRequest);
            return "Message sent successfully";
        } catch (Exception e) {
            log.error("Error sending email: {}", e.getMessage());
            throw new EmailManagementException("Error sending email.");
        }
    }


}

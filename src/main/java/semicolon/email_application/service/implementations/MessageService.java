package semicolon.email_application.service.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import semicolon.email_application.application.mail.config.IMailService;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.models.AppUser;
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
        AppUser foundSender = null;
        if (request.getSenderId() != null) {
             foundSender = appUserService.getAppUserById(request.getSenderId());
            if (foundSender == null) {
                throw new EmailManagementException(
                        String.format("Sender with ID %d not found.", request.getSenderId())
                );
            }
            // Handle sending the message using foundSender
        } else if (request.getSenderEmail() != null) {
            foundSender = appUserService.getAppUserByEmail(request.getSenderEmail());
            if (foundSender == null) {
                throw new EmailManagementException(
                        String.format("Sender with email %s not found.", request.getSenderEmail())
                );
            }
            // Handle sending the message using foundSender
        } else {
            throw new EmailManagementException("Either senderId or senderEmail must be provided.");
        }

        var message = Message.builder()
                .sender(foundSender)
                .recipientEmail(request.getRecipientEmail())
                .subject(request.getSubject())
                .body(request.getBody())
                .timeStamp(LocalDateTime.now().toString())
                .build();
        messageRepository.save(message);
        mailService.sendMail(message);
        return "Message sent successfully";
    }




}

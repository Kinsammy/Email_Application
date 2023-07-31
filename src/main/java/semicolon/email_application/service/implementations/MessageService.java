package semicolon.email_application.service.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import semicolon.email_application.application.mail.config.IMailService;
import semicolon.email_application.data.dto.request.SendMailRequest;
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
    public String sendMessage(SendMailRequest request) {
        String authenticatedUser = getAuthenticatedUser();
        if (!authenticatedUser.equals(request.getSenderEmail())) {
            throw new EmailManagementException("You are not authorized to send messages for this user.");
        }

        var message = new Message();
        message.setSender();
        message.setRecipient(request.getRecipient());
        message.setSubject(request.getSubject());
        message.setBody(request.getBody());
        message.setAttachments(request.getAttachments());

        messageRepository.save(message);
        mailService.sendMail(message);
        return "Message sent.";

    }

    private String getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (principal instanceof UserDetails){
            return(((UserDetails) principal).getUsername());
        }
        else {
            return principal.toString();
        }
    }
}

package semicolon.email_application.service.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import semicolon.email_application.application.mail.config.IMailService;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.data.models.Attachment;
import semicolon.email_application.data.models.Message;
import semicolon.email_application.data.repositories.AppUserRepository;
import semicolon.email_application.data.repositories.AttachmentRepository;
import semicolon.email_application.data.repositories.MessageRepository;
import semicolon.email_application.exception.EmailManagementException;
import semicolon.email_application.service.IMessageService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService implements IMessageService {
    private final IMailService mailService;
    private final AppUserRepository userRepository;
    private final MessageRepository messageRepository;
    private final AttachmentRepository attachmentRepository;

    @Override
    public String sendMessage(SendMailRequest request) throws IOException {
        String authenticatedUser = getAuthenticatedUser();
        if (!authenticatedUser.equals(request.getSender().getEmail())) {
            throw new EmailManagementException("You are not authorized to send messages for this user.");
        }

        var message = new Message();
        var sender = new AppUser(request.getSender().getName(), request.getSender().getEmail());
        var recipient = new AppUser(request.getRecipient().getName(), request.getRecipient().getEmail());
        List<Attachment> attachments = saveAttachments((List<MultipartFile>) request.getAttachments());
        message.setSender(sender);
        message.setRecipient(recipient);
        message.setSubject(request.getSubject());
        message.setBody(request.getBody());
        message.setAttachments(attachments);

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

    private List<Attachment> saveAttachments(List<MultipartFile> attachments) throws IOException{
        List<Attachment> savedAttachments = new ArrayList<>();

        for (MultipartFile file : attachments){
            var attachment = new Attachment();
                  attachment.setFile(file.getOriginalFilename());

            var savedAttachment = attachmentRepository.save(attachment);
            savedAttachments.add(savedAttachment);
        }
        return savedAttachments;
    }
}

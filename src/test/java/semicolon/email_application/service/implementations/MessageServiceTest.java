package semicolon.email_application.service.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import semicolon.email_application.application.mail.config.IMailService;
import semicolon.email_application.data.dto.request.Recipient;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.dto.request.Sender;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.data.models.Message;
import semicolon.email_application.data.repositories.AppUserRepository;
import semicolon.email_application.data.repositories.MessageRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private IMailService mailService;

    @Mock
    private AppUserRepository userRepository;
    @Mock
    private MessageRepository messageRepository;
    @InjectMocks
    private MessageService messageService;
    @InjectMocks
    private AppUserService userService;
    private String senderEmail;
    @BeforeEach
    void setUp() {
        senderEmail = "fanusamuel@gmail.com";
        MockitoAnnotations.openMocks(this);
        when(userService.getAppUserByEmail(anyString()))
                .thenReturn(AppUser.builder().email(senderEmail).build());
    }

    @Test
    void sendMessage() {
        String receiptEmail =  "samuelfanu53@gmail.com";
        String subject= "Time wait for nobody";
        String textContent= "Dear Samuel, Move ahead of time";
        var foundSender = AppUser.builder()
                .email(senderEmail)
                .build();
        when(userRepository.findByEmail(senderEmail)).thenReturn(Optional.of(foundSender));
        var emailRequest = SendMailRequest.builder()
                .sender(Sender.builder().email(senderEmail).build())
                .to(Recipient.builder().email(receiptEmail).build())
                .subject(subject)
                .textContent(textContent)
                .build();

        String result = messageService.sendMessage(emailRequest);
        assertEquals("Message sent successfully", result);
        verify(messageRepository).save(any(Message.class));
        verify(mailService).sendMail(eq(emailRequest));
    }
}
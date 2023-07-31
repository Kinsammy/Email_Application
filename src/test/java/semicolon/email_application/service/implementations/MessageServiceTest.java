//package semicolon.email_application.service.implementations;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentMatcher;
//import org.mockito.ArgumentMatchers;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import semicolon.email_application.application.mail.config.IMailService;
//import semicolon.email_application.data.dto.request.SendMailRequest;
//import semicolon.email_application.data.models.AppUser;
//import semicolon.email_application.data.models.Message;
//import semicolon.email_application.data.repositories.AppUserRepository;
//import semicolon.email_application.data.repositories.MessageRepository;
//import semicolon.email_application.service.IMessageService;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//import static org.mockito.hamcrest.MockitoHamcrest.argThat;
//
//@ExtendWith(MockitoExtension.class)
//class MessageServiceTest {
//    @Mock
//    private IMailService mailService;
//    @Mock
//    private AppUserRepository userRepository;
//    @Mock
//    private MessageRepository messageRepository;
//
//    private IMessageService messageService;
//
//
//
//    @BeforeEach
//    void setUp() {
//        messageService = new MessageService(mailService, userRepository, messageRepository);
//
//    }
//
//    @Test
//    void sendMessageTest() {
//        var sender = new AppUser();
//        sender.setEmail("fanusamuel@gmail.com");
//        var message = new Message();
//        message.setSender(sender);
//        var expectedSendMailRequest = new SendMailRequest();
//        expectedSendMailRequest.setSender(sender);
//
//        when(userRepository.findByEmail(sender.getEmail())).thenReturn(Optional.of(sender));
//        when(messageRepository.save(message)).thenReturn(message);
//        when(mailService.sendMail(ArgumentMatchers.argThat(isSendMailRequestWithSender(sender))))
//                .thenReturn("Mail sent.");
//
//        String expectedResult = messageService.sendMessage(message);
//
//        assertEquals("Message sent.", expectedResult);
//
//        verify(userRepository).findByEmail(sender.getEmail());
//        verify(messageRepository).save(message);
//        verify(mailService).sendMail(argThat(isSendMailRequestWithSender(sender)));
//    }
//
//    // Custom ArgumentMatcher to verify SendMailRequest has the correct sender
//    private static ArgumentMatcher<SendMailRequest> isSendMailRequestWithSender(AppUser sender) {
//        return sendMailRequest -> sendMailRequest != null &&
//                sendMailRequest.getSender() != null &&
//                sendMailRequest.getSender().getEmail().equals(sender.getEmail());
//    }
//
//
//
//}
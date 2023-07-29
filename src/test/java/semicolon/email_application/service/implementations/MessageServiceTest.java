package semicolon.email_application.service.implementations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.data.models.Message;
import semicolon.email_application.data.repositories.AppUserRepository;
import semicolon.email_application.service.IMessageService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MessageServiceTest {
    @Autowired
    private AppUserRepository userRepository;
    private Message message;
    private IMessageService messageService;
    private SendMailRequest mailRequest;

    private AppUser user;

    @BeforeEach
    void setUp() {
        user = new AppUser();
        mailRequest = new SendMailRequest();
        message = new Message();
        var sender = userRepository.findByEmail(user.getEmail());
        mailRequest.setSender(message.getSender());
        mailRequest.setRecipient(new AppUser().setEmail("samuelfanu@gmail.com"));
    }

    @Test
    void sendMessageTest() {
        var response = messageService.sendMessage(mailRequest);
        assertThat(response).isNotNull();
    }



}
package semicolon.email_application.service.implementations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.ReplaceOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import semicolon.email_application.data.dto.request.Recipient;
import semicolon.email_application.data.dto.request.RegisterAppUserRequest;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.dto.request.Sender;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.exception.EmailManagementException;
import semicolon.email_application.service.AppUserService;

import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
class AppUserServiceImpTest {
    @Autowired
    private AppUserService appUserService;
    private RegisterAppUserRequest request;

    private SendMailRequest mailRequest;

    @BeforeEach
    void setUp(){
        request = new RegisterAppUserRequest();
        request.setEmail("test@email.com");
//        request.setFirstName("Sam");
//        request.setLastName("Idowu");
        request.setPassword("testPassword");

        List<Recipient> to = List.of(
                new Recipient("Fanu", "fanusamuel@gmail.com")
        );
        Sender sender = new Sender("SamuelMail", "noreply@app.net");
        mailRequest = new SendMailRequest(sender, to, "Test sending email", "Let build the world together");

    }

    @Test
    void registerTest() {
        var registerResponse = appUserService.register(request);
        assertThat(registerResponse).isNotNull();
        assertThat(registerResponse.getCode())
                .isEqualTo(HttpStatus.CREATED.value());

    }

    @Test
    void getAllAppUsersTest() {
        var registerResponse = appUserService.getAllAppUsers();
        assertThat(registerResponse).isNotNull();
    }

    @Test
    void getSenderByIdTest() {
        var response = appUserService.register(request);
        AppUser foundAppUser = appUserService.getAppUserById(response.getId());
        assertThat(foundAppUser).isNotNull();
        assertThat(foundAppUser.getFirstName()).isEqualTo(request.getFirstName());
    }

    @Test
    void updateAppUserTest() throws JsonPointerException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree("2348102402753");
        JsonPatch updatePayload = new JsonPatch(List.of(
                new ReplaceOperation(new JsonPointer("/phoneNumber"),
                        node)
        ));
        var registerResponse = appUserService.register(request);
        var updatedSender = appUserService.updateAppUser(registerResponse.getId(), updatePayload);
        assertThat(updatedSender).isNotNull();
        assertThat(updatedSender.getPhoneNumber()).isNotNull();
    }


    @Test
    void deleteSenderTest() {
        var response = appUserService.register(request);
        appUserService.deleteAppUser(response.getId());
        assertThrows(EmailManagementException.class,
                ()-> appUserService.getAppUserById(response.getId()));
    }


    @Test
    void sendEmailTest() {
        var response = appUserService.sendEmail(mailRequest);
        assertThat(response).isNotNull();
    }
}
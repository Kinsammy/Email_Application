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
import semicolon.email_application.data.dto.request.RegisterSenderRequest;
import semicolon.email_application.service.SenderService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SenderServiceImpTest {
    @Autowired
    private SenderService senderService;
    private RegisterSenderRequest request;

    @BeforeEach
    void setUp(){
        request = new RegisterSenderRequest();
        request.setEmail("test@email.com");
        request.setFirstName("Sam");
        request.setLastName("Idowu");
        request.setPassword("testPassword");

    }

    @Test
    void registerTest() {
        var registerResponse = senderService.register(request);
        assertThat(registerResponse).isNotNull();
        assertThat(registerResponse.getCode())
                .isEqualTo(HttpStatus.CREATED.value());

    }

    @Test
    void getAllSendersTest() {
        var registerResponse = senderService.getAllSenders();
        assertThat(registerResponse).isNotNull();
    }

    @Test
    void updateSenderTest() throws JsonPointerException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree("2348102402753");
        JsonPatch updatePayload = new JsonPatch(List.of(
                new ReplaceOperation(new JsonPointer("/phoneNumber"),
                        node)
        ));
        var registerResponse = senderService.register(request);
        var updatedSender = senderService.updateSender(registerResponse.getId(), updatePayload);
        assertThat(updatedSender).isNotNull();
        assertThat(updatedSender.getPhoneNumber()).isNotNull();
    }

    @Test
    void getSenderById() {
    }





    @Test
    void deleteSender() {
    }
}
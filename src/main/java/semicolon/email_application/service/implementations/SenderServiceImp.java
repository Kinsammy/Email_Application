package semicolon.email_application.service.implementations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import semicolon.email_application.data.dto.request.RegisterSenderRequest;
import semicolon.email_application.data.dto.response.RegisterResponse;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.data.models.Sender;
import semicolon.email_application.data.repositories.SenderRepository;
import semicolon.email_application.exception.EmailManagementException;
import semicolon.email_application.mapper.EmailMapper;
import semicolon.email_application.service.SenderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class SenderServiceImp implements SenderService {

    private final SenderRepository senderRepository;


    @Override
    public RegisterResponse register(RegisterSenderRequest registerRequest){
        ModelMapper mapper = new ModelMapper();
        AppUser appUser = mapper.map(registerRequest,AppUser.class);
        log.info("appUser{}", appUser);
        appUser.setCreatedAt(LocalDateTime.now().toString());
        Sender sender = new Sender();
        sender.setUserDetails(appUser);
        log.info("sender{}", sender);
        Sender savedSender = senderRepository.save(sender);
        log.info("sender{}", savedSender);
        RegisterResponse registerResponse = getRegisterResponse(savedSender);
        return registerResponse;
    }
    private RegisterResponse getRegisterResponse(Sender savedSender) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(savedSender.getId());
        registerResponse.setCode(HttpStatus.CREATED.value());
        registerResponse.setSuccess(true);
        registerResponse.setMessage("Sender Registration Successful");
        return registerResponse;
    }
    @Override
    public Sender getSenderById(Long senderId) {
        return senderRepository.findById(senderId).orElseThrow(()->
                new EmailManagementException(
                        String.format("Sender with id %d not found", senderId)));
    }
    @Override
    public List<Sender> getAllSenders() {
        List<Sender> senders = new ArrayList<>();
        senderRepository.findAll()
                .forEach(senders::add);
        return senders;
    }



    @Override
    public Sender updateSender(Long senderId, JsonPatch updatePayload) {
        ObjectMapper mapper = new ObjectMapper();
        Sender foundSender = getSenderById(senderId);
        AppUser senderDetails = foundSender.getUserDetails();
        JsonNode node = mapper.convertValue(foundSender, JsonNode.class);
        try {
            JsonNode updatedNode = updatePayload.apply(node);
            var updatedSender = mapper.convertValue(updatedNode, Sender.class);
            updatedSender = senderRepository.save(updatedSender);
            return updatedSender;
        } catch (JsonPatchException exception){
            log.error(exception.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteSender(Long senderId) {
        senderRepository.deleteById(senderId);
    }

    @Override
    public void sendEmail(String recipientEmail, String subject, String message) {

    }
}

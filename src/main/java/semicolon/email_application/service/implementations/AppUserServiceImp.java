package semicolon.email_application.service.implementations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import semicolon.email_application.config.MailConfig;
import semicolon.email_application.data.dto.request.RegisterAppUserRequest;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.dto.request.Sender;
import semicolon.email_application.data.dto.response.RegisterResponse;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.data.models.Role;
import semicolon.email_application.data.repositories.AppUserRepository;
import semicolon.email_application.exception.EmailManagementException;
import semicolon.email_application.service.AppUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImp implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailConfig mailConfig;


    @Override
    public RegisterResponse register(RegisterAppUserRequest registerRequest){
        ModelMapper mapper = new ModelMapper();
        AppUser appUser = mapper.map(registerRequest, AppUser.class);
        appUser.getRoles().add(Role.SENDER);
        appUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        appUser.setCreatedAt(LocalDateTime.now().toString());
        AppUser savedAppUser = appUserRepository.save(appUser);
        RegisterResponse registerResponse = getRegisterResponse(savedAppUser);
        return registerResponse;
    }
    private RegisterResponse getRegisterResponse(AppUser savedSender) {
        RegisterResponse registerResponse = new RegisterResponse();
        registerResponse.setId(savedSender.getId());
        registerResponse.setCode(HttpStatus.CREATED.value());
        registerResponse.setSuccess(true);
        registerResponse.setMessage("Sender Registration Successful");
        return registerResponse;
    }
    @Override
    public AppUser getAppUserById(Long appUserId) {
        return appUserRepository.findById(appUserId).orElseThrow(()->
                new EmailManagementException(
                        String.format("Sender with id %d not found", appUserId)));
    }

    @Override
    public AppUser getAppUserByEmail(String email) {
        Optional<AppUser> optionalAppUser = appUserRepository.findByEmail(email);
        if (optionalAppUser.isPresent()){
            return optionalAppUser.get();
        } else {
            throw new EmailManagementException(
                    String.format("User with email %s not found", email)
            );
        }
    }

    @Override
    public List<AppUser> getAllAppUsers() {
        List<AppUser> appUsers = new ArrayList<>();
        appUserRepository.findAll()
                .forEach(appUsers::add);
        return appUsers;
    }



    @Override
    public AppUser updateAppUser(Long appUserId, JsonPatch updatePayload) {
        ObjectMapper mapper = new ObjectMapper();
        AppUser foundSender = getAppUserById(appUserId);
        JsonNode node = mapper.convertValue(foundSender, JsonNode.class);
        try {
            JsonNode updatedNode = updatePayload.apply(node);
            var updatedSender = mapper.convertValue(updatedNode, AppUser.class);
            updatedSender = appUserRepository.save(updatedSender);
            return updatedSender;
        } catch (JsonPatchException exception){
            log.error(exception.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteAppUser(Long senderId) {
        appUserRepository.deleteById(senderId);
    }

    @Override
    public String sendEmail(SendMailRequest mailRequest) {
        ModelMapper mapper = new ModelMapper();
        AppUser appUser = mapper.map(mailRequest, AppUser.class);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", mailConfig.getApiKey());
//        log.info("api-key{}", mailConfig.getApiKey());

        HttpEntity<SendMailRequest> requestEntity = new HttpEntity<>(mailRequest, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(mailConfig.getMailUrl(), requestEntity, String.class);
//        log.info("response->{}", response);
        return response.getBody();

    }


}

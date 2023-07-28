package semicolon.email_application.service.implementations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import semicolon.email_application.config.MailConfig;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.service.IMessageService;

@Service
@AllArgsConstructor
@Slf4j
public class MessageService implements IMessageService {
    private final MailConfig mailConfig;

    @Override
    public String sendMessage(SendMailRequest mailRequest) {
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

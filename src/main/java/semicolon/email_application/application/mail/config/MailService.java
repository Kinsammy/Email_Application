package semicolon.email_application.application.mail.config;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import semicolon.email_application.config.MailConfig;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.dto.request.SystemEMailRequest;

@Service
@AllArgsConstructor
public class MailService implements IMailService{
    private final MailConfig mailConfig;
    @Override
    public String sendNotification(SystemEMailRequest request) {
        var template = new RestTemplate();
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", mailConfig.getApiKey());
        HttpEntity<SystemEMailRequest> requestHttpEntity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = template.postForEntity(mailConfig.getMailUrl(), requestHttpEntity, String.class);
        return response.getBody();
    }
}

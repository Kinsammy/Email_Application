//package semicolon.email_application.utils;
//
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//import semicolon.email_application.config.MailConfig;
//import semicolon.email_application.data.dto.request.SendMailRequest;
//
//@AllArgsConstructor
//public class AppUtils {
//
//    private final MailConfig mailConfig;
//
//    public String mailTemplate(){
//        SendMailRequest mailRequest = new SendMailRequest();
//        RestTemplate restTemplate = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("api-key", mailConfig.getApiKey());
//        HttpEntity<SendMailRequest> requestEntity = new HttpEntity<>(mailRequest, headers);
//        ResponseEntity<String> response = restTemplate.postForEntity(mailConfig.getMailUrl(), requestEntity, String.class);
////        log.info("response->{}", response);
//        return response.getBody();
//    }
//}

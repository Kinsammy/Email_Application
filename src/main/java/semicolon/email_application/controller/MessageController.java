package semicolon.email_application.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.dto.request.SystemEMailRequest;
import semicolon.email_application.service.IMessageService;

@RestController
@RequestMapping("/api/v1/message")
@AllArgsConstructor
public class MessageController {
    private final IMessageService messageService;

    @PostMapping("sendmail")
    public ResponseEntity<String> sendEmail(@RequestBody SystemEMailRequest request){
        messageService.sendMessage(request);
        return  ResponseEntity.ok("Message sent successfully");
    }
}

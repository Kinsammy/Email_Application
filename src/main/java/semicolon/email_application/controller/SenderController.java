package semicolon.email_application.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semicolon.email_application.data.dto.request.RegisterSenderRequest;
import semicolon.email_application.data.dto.response.RegisterResponse;
import semicolon.email_application.data.models.Sender;
import semicolon.email_application.service.SenderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sender")
@AllArgsConstructor
public class SenderController {
    private final SenderService senderService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterSenderRequest registerSenderRequest){
        RegisterResponse registerResponse = senderService.register(registerSenderRequest);
        return  ResponseEntity.status(registerResponse.getCode()).body(registerResponse);
    }

    @GetMapping
    public List<Sender> getAllSenders(){
        return senderService.getAllSenders();
    }

    @PatchMapping(value = "{senderId}", consumes = {"application/json-patch+json"})
    public ResponseEntity<?> updateSender(@PathVariable Long senderId, @RequestBody JsonPatch updatePatch){
       try {
           var response = senderService.updateSender(senderId, updatePatch);
           return ResponseEntity.status(HttpStatus.OK).body(response);
       } catch (Exception exception){
           return ResponseEntity.badRequest().body(exception.getMessage());
       }
    }

}

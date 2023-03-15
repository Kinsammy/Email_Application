package semicolon.email_application.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import semicolon.email_application.config.MailConfig;
import semicolon.email_application.data.dto.request.RegisterAppUserRequest;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.dto.response.RegisterResponse;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.service.AppUserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sender")
@AllArgsConstructor
public class AppUserController {
    private final AppUserService appUserService;
    private final MailConfig mailConfig;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterAppUserRequest registerAppUserRequest){
        RegisterResponse registerResponse = appUserService.register(registerAppUserRequest);
        return  ResponseEntity.status(registerResponse.getCode()).body(registerResponse);
    }

    @GetMapping
    public List<AppUser> getAllSenders(){
        return appUserService.getAllAppUsers();
    }

    @GetMapping("{appUserId}")
    public ResponseEntity<?> getAppUserById(@PathVariable Long appUserId){
        var foundUser = appUserService.getAppUserById(appUserId);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }

    @GetMapping("{email}")
    public ResponseEntity<?> getAllUserByEmail(@PathVariable String email){
        var foundEmail = appUserService.getAppUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(foundEmail);
    }

    @PatchMapping(value = "{appUserId}", consumes = {"application/json-patch+json"})
    public ResponseEntity<?> updateAppUser(@PathVariable Long appUserId, @RequestBody JsonPatch updatePatch){
       try {
           var response = appUserService.updateAppUser(appUserId, updatePatch);
           return ResponseEntity.status(HttpStatus.OK).body(response);
       } catch (Exception exception){
           return ResponseEntity.badRequest().body(exception.getMessage());
       }
    }

    @DeleteMapping("{appUserId}")
    public ResponseEntity<?> deleteAppUser(@PathVariable Long appUserId){
        appUserService.deleteAppUser(appUserId);
        return ResponseEntity.ok("Sender deleted successfully");
    }

    @PostMapping("sendmail")
    public ResponseEntity<String> sendEmail(@RequestBody SendMailRequest request){
       appUserService.sendEmail(request);
       return  ResponseEntity.ok("Message sent successfully");
    }

}

package semicolon.email_application.controller;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import semicolon.email_application.data.dto.request.RegisterRequest;
import semicolon.email_application.data.dto.response.RegisterResponse;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.service.IAppUserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class AppUserController {
    private final IAppUserService IAppUserService;


//    @PostMapping
//    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest){
//        RegisterResponse registerResponse = IAppUserService.register(registerRequest);
//        return  ResponseEntity.status(registerResponse.getCode()).body(registerResponse);
//    }

    @GetMapping
    public List<AppUser> getAllUsers(){
        return IAppUserService.getAllAppUsers();
    }

    @GetMapping("{appUserId}")
    public ResponseEntity<?> getAppUserById(@PathVariable Long appUserId){
        var foundUser = IAppUserService.getAppUserById(appUserId);
        return ResponseEntity.status(HttpStatus.OK).body(foundUser);
    }

    @GetMapping("{email}")
    public ResponseEntity<?> getAllUserByEmail(@PathVariable String email){
        var foundEmail = IAppUserService.getAppUserByEmail(email);
        return ResponseEntity.status(HttpStatus.OK).body(foundEmail);
    }

    @PatchMapping(value = "{appUserId}", consumes = {"application/json-patch+json"})
    public ResponseEntity<?> updateAppUser(@PathVariable Long appUserId, @RequestBody JsonPatch updatePatch){
       try {
           var response = IAppUserService.updateAppUser(appUserId, updatePatch);
           return ResponseEntity.status(HttpStatus.OK).body(response);
       } catch (Exception exception){
           return ResponseEntity.badRequest().body(exception.getMessage());
       }
    }

    @DeleteMapping("{appUserId}")
    public ResponseEntity<?> deleteAppUser(@PathVariable Long appUserId){
        IAppUserService.deleteAppUser(appUserId);
        return ResponseEntity.ok("Sender deleted successfully");
    }



}

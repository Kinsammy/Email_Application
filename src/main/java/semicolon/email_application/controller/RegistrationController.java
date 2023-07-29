package semicolon.email_application.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import semicolon.email_application.application.event.RegistrationCompleteEvent;
import semicolon.email_application.data.dto.request.RegisterRequest;
import semicolon.email_application.data.dto.request.VerifyRequest;
import semicolon.email_application.data.repositories.TokenRepository;
import semicolon.email_application.service.IAppUserService;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class RegistrationController {
    private final IAppUserService userService;
    private final ApplicationEventPublisher publisher;
    private final TokenRepository tokenRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest  registerRequest, final HttpServletRequest request){
        var user = userService.register(registerRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));
        return ResponseEntity.ok("Registration successful! Please check your email to complete your registration");
    }

    @GetMapping("/verifyAccount")
    public ResponseEntity<?> verifyAccount(@RequestParam("email") String email, @RequestParam("token") String token) {
        var request = new VerifyRequest();
        request.setEmail(email);
        request.setVerificationToken(token);
        var response = userService.verifyAccount(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}

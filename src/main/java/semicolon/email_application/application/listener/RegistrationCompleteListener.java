package semicolon.email_application.application.listener;


import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import semicolon.email_application.application.event.RegistrationCompleteEvent;
import semicolon.email_application.application.mail.config.IMailService;
import semicolon.email_application.config.security.service.JwtService;
import semicolon.email_application.data.dto.request.Recipient;
import semicolon.email_application.data.dto.request.SystemEMailRequest;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.service.IAppUserService;

@Component
@RequiredArgsConstructor
public class RegistrationCompleteListener implements ApplicationListener<RegistrationCompleteEvent> {
    private final IMailService mailService;
    private final IAppUserService userService;
    private final JwtService jwtService;
    private AppUser user;
    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        user = event.getUser();
//        String verificationToken = UUID.randomUUID().toString();
        String verification =  userService.generateAndSaveToken(user);
        String url = event.getApplicationUrl() + "/api/v1/auth/verifyAccount?email=" + user.getEmail() + "&token=" + verification;

        sendVerificationEmail(url);
    }

    private void sendVerificationEmail(String url) {
        var request = new SystemEMailRequest();
        request.setSubject("Email Verification");
        request.setTextContent("<p> Hi, " + user.getName() + ", </p>" +
                "<p>Thank you for registering with us,</p>" +
                "<p>Please, follow the link below to complete your registration.</p>" +
                "<p><a href=\"" + url + "\">Verify your email and activate your account</a></p>" +
                "<p>Thank you<br>users Registration Portal Service</p>");
        request.getTo().add(new Recipient(user.getName(), user.getEmail()));
        mailService.sendNotification(request);
    }
}

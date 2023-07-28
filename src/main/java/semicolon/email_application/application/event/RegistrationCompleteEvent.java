package semicolon.email_application.application.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import semicolon.email_application.data.models.AppUser;

@Setter
@Getter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private AppUser user;
    private String applicationUrl;

    public RegistrationCompleteEvent(AppUser user, String applicationUrl){
        super(user);
        this.user = user;
        this.applicationUrl =applicationUrl;
    }
}

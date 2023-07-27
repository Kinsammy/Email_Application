package semicolon.email_application.security.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import semicolon.email_application.config.AppConfig;

import java.security.Key;

@AllArgsConstructor
@Getter
@Configuration
public class SignKey {
    private final AppConfig signKey;

//    @Bean
    public Key getSignKey(){
        return  new AppConfig().getSignKey();
    }

}

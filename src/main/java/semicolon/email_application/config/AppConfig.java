package semicolon.email_application.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import semicolon.email_application.data.repositories.AppUserRepository;
import semicolon.email_application.security.util.JwtUtil;

import java.security.Key;

@Configuration
public class AppConfig {
    @Value("${sendinblue.mail.url}")
    private String mailUrl;
    @Value("${sendinblue.api.key}")
    private String mailApiKey;

    @Value("${jwt.secret.key}")
    private String jwtSecret;

//    private final AppUserRepository userRepository;



    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public MailConfig mailConfig(){
        return new MailConfig(mailApiKey, mailUrl);
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil(jwtSecret);
    }


//    @Bean
//    public UserDetailsService userDetailsService(){
//        return username -> userRepository.findByEmail(username)
//                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
//    }



//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

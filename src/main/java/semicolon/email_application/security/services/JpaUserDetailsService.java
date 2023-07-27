package semicolon.email_application.security.services;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.security.users.SecureUser;
import semicolon.email_application.service.AppUserService;

@AllArgsConstructor
@Service
public class JpaUserDetailsService implements UserDetailsService {
    private final AppUserService appUserService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserService.getAppUserByEmail(username);
        return new SecureUser(user);
    }
}

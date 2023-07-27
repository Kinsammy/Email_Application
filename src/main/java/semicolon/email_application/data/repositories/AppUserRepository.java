package semicolon.email_application.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import semicolon.email_application.data.models.AppUser;

import java.util.Optional;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
}

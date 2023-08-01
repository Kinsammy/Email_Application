package semicolon.email_application.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import semicolon.email_application.data.models.AppUser;

import java.util.Optional;


public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
    @Query("SELECT u FROM AppUser u JOIN FETCH u.role WHERE u.email = :email")
    Optional<AppUser> findByEmailWithRole(String email);
}

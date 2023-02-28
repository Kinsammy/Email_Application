package semicolon.email_application.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import semicolon.email_application.data.models.Sender;

import java.util.Optional;

public interface SenderRepository extends JpaRepository<Sender, Long> {
    Sender findSenderById(Long id);
}

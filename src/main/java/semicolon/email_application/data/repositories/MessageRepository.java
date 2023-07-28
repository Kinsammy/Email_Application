package semicolon.email_application.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import semicolon.email_application.data.models.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}

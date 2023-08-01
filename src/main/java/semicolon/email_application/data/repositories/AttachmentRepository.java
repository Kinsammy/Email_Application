package semicolon.email_application.data.repositories;

import org.springframework.data.repository.CrudRepository;
import semicolon.email_application.data.models.Attachment;

public interface AttachmentRepository extends CrudRepository<Attachment, Long> {
}

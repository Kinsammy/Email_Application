package semicolon.email_application.data.models;

import jakarta.persistence.*;
import lombok.*;
import semicolon.email_application.data.dto.request.Recipient;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
   @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AppUser sender;
    private String recipientEmail;
    private String subject;
    private String body;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Attachment> attachments;
    private String timeStamp;


}

package semicolon.email_application.data.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Message {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String subject;
    private String body;

//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private Attachment attachment;
    private String timeStamp;

// @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private AppUser userDetails;

}

package semicolon.email_application.data.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    private String timeStamp;
}

package semicolon.email_application.data.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private AppUser userDetails;

}

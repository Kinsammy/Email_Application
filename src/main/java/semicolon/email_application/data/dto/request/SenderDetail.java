package semicolon.email_application.data.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SenderDetail {
    private Long senderId;
    private String name;
    private String email;
}

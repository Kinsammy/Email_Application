package semicolon.email_application.data.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SystemEMailRequest {
    private Sender sender = new Sender("SamTech Email Application", "noreply@sametech.net");
    private List<Recipient> to = new ArrayList<>();
    private String subject;
    private String textContent;
}

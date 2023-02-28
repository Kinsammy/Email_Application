package semicolon.email_application.service;


import com.github.fge.jsonpatch.JsonPatch;
import semicolon.email_application.data.dto.request.RegisterSenderRequest;
import semicolon.email_application.data.dto.response.RegisterResponse;
import semicolon.email_application.data.models.Sender;

import java.util.List;


public interface SenderService {
    RegisterResponse register(RegisterSenderRequest registerRequest);

    List<Sender> getAllSenders();
    Sender getSenderById(Long senderId);
    Sender updateSender(Long senderId, JsonPatch updatePayload);
//    Sender addNewSender(Sender sender);
    void deleteSender(Long senderId);
    void sendEmail(String recipientEmail, String subject, String message);

}

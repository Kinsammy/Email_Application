package semicolon.email_application.service;


import com.github.fge.jsonpatch.JsonPatch;
import semicolon.email_application.data.dto.request.RegisterAppUserRequest;
import semicolon.email_application.data.dto.request.SendMailRequest;
import semicolon.email_application.data.dto.response.RegisterResponse;
import semicolon.email_application.data.models.AppUser;


import java.util.List;


public interface AppUserService {
    RegisterResponse register(RegisterAppUserRequest registerRequest);

    List<AppUser> getAllAppUsers();
    AppUser getAppUserById(Long appUserId);
    AppUser getAppUserByEmail(String email);
    AppUser updateAppUser(Long appUserId, JsonPatch updatePayload);
//    Sender addNewSender(Sender sender);
    void deleteAppUser(Long appUserId);

    String sendEmail(SendMailRequest mailRequest);

}

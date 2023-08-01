package semicolon.email_application.service;


import com.github.fge.jsonpatch.JsonPatch;
import semicolon.email_application.data.dto.request.AuthenticationRequest;
import semicolon.email_application.data.dto.request.RegisterRequest;
import semicolon.email_application.data.dto.request.UserUpdateRequest;
import semicolon.email_application.data.dto.request.VerifyRequest;
import semicolon.email_application.data.dto.response.ApiResponse;
import semicolon.email_application.data.dto.response.AuthenticationResponse;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.data.models.Token;


import java.util.List;
import java.util.Optional;


public interface IAppUserService {
    AppUser register(RegisterRequest registerRequest);
    String verifyAccount(VerifyRequest request);
    AuthenticationResponse login(AuthenticationRequest request);
    List<AppUser> getAllAppUsers();
    AppUser getAppUserById(Long appUserId);
    AppUser getAppUserByEmail(String email);
    AppUser updateAppUser(Long appUserId, JsonPatch updatePayload);
    AppUser updateUser(UserUpdateRequest request);
    //    Sender addNewSender(Sender sender);
    void deleteAppUser(Long appUserId);


    String generateAndSaveToken(AppUser user);


    Optional<Token> validateToken(AppUser user, String token);

}

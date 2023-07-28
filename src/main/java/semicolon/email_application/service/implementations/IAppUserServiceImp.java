package semicolon.email_application.service.implementations;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import semicolon.email_application.config.security.service.JwtService;
import semicolon.email_application.data.dto.request.RegisterRequest;
import semicolon.email_application.data.dto.request.VerifyRequest;
import semicolon.email_application.data.dto.response.ApiResponse;
import semicolon.email_application.data.dto.response.RegisterResponse;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.data.models.Role;
import semicolon.email_application.data.models.Token;
import semicolon.email_application.data.repositories.AppUserRepository;
import semicolon.email_application.data.repositories.TokenRepository;
import semicolon.email_application.exception.EmailManagementException;
import semicolon.email_application.exception.UserAlreadyExistException;
import semicolon.email_application.service.IAppUserService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class IAppUserServiceImp implements IAppUserService {
    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final EntityManager entityManager;



    @Override
    public AppUser register(RegisterRequest request){
        Optional<AppUser>  user = appUserRepository.findByEmail(request.getEmail());
        if (user.isPresent()){
            throw new UserAlreadyExistException(
                    String.format("User with email: %s already exists", request.getEmail())
            );
        }
        var mapper = new ModelMapper();
        var appUser = mapper.map(request, AppUser.class);
        appUser.setRole(Role.USER);
        appUser.setPassword(passwordEncoder.encode(request.getPassword()));
        appUser.setCreatedAt(LocalDateTime.now().toString());
        return appUserRepository.save(appUser);
    }

    @Override
    public String verifyAccount(VerifyRequest request) {
        if (getAppUserByEmail(request.getEmail())== null) {
            throw new EmailManagementException("Invalid Email");
        }
        var appUser = getAppUserByEmail(request.getEmail());
        Optional<Token> receivedToken = validateToken(appUser,request.getVerificationToken());

        appUser.setEnabled(true);
        appUserRepository.save(appUser);
        tokenRepository.delete(receivedToken.get());
       return "Email verified successfully! Now you can login to your account.";
    }


    private RegisterResponse getRegisterResponse(AppUser savedSender) {
        var registerResponse = new RegisterResponse();
        registerResponse.setId(savedSender.getId());
        registerResponse.setCode(HttpStatus.CREATED.value());
        registerResponse.setSuccess(true);
        registerResponse.setMessage("Sender Registration Successful");
        return registerResponse;
    }
    @Override
    public AppUser getAppUserById(Long appUserId) {
        return appUserRepository.findById(appUserId).orElseThrow(()->
                new EmailManagementException(
                        String.format("Sender with id %d not found", appUserId)));
    }

    @Override
    public AppUser getAppUserByEmail(String email) {
        Optional<AppUser> optionalAppUser = appUserRepository.findByEmail(email);
        if (optionalAppUser.isPresent()){
            return optionalAppUser.get();
        } else {
            throw new EmailManagementException(
                    String.format("User with email %s not found", email)
            );
        }
    }

    @Override
    public List<AppUser> getAllAppUsers() {
        List<AppUser> appUsers = new ArrayList<>();
        appUserRepository.findAll()
                .forEach(appUsers::add);
        return appUsers;
    }



    @Override
    public AppUser updateAppUser(Long appUserId, JsonPatch updatePayload) {
        ObjectMapper mapper = new ObjectMapper();
        AppUser foundSender = getAppUserById(appUserId);
        JsonNode node = mapper.convertValue(foundSender, JsonNode.class);
        try {
            JsonNode updatedNode = updatePayload.apply(node);
            var updatedSender = mapper.convertValue(updatedNode, AppUser.class);
            updatedSender = appUserRepository.save(updatedSender);
            return updatedSender;
        } catch (JsonPatchException exception){
            log.error(exception.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteAppUser(Long senderId) {
        appUserRepository.deleteById(senderId);
    }

    @Override
    @Transactional
    public String generateAndSaveToken(AppUser user) {
        Optional<Token> existingToken = tokenRepository.findTokenByAppUser(user);
        existingToken.ifPresent(tokenRepository::delete);
        String generateToken = jwtService.generateToken(user);
        var token = Token.builder()
                .appUser(entityManager.merge(user))
                .token(generateToken)
                .build();
        tokenRepository.save(token);
        return generateToken;
    }

    @Override
    public Optional<Token> validateToken(AppUser user, String token) {
        Optional<Token> receivedToken = tokenRepository.findTokenByAppUserAndToken(user, token);
        if (receivedToken.isEmpty()) throw new EmailManagementException("Invalid verification token.");
        else if (receivedToken.get().getExpiryTime().isBefore(LocalDateTime.now())){
            tokenRepository.delete(receivedToken.get());
            throw new EmailManagementException("Token already expired");
        }

        return receivedToken;
    }


}

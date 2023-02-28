package semicolon.email_application.mapper;

import semicolon.email_application.data.dto.request.RegisterSenderRequest;
import semicolon.email_application.data.models.AppUser;

public class EmailMapper {

    public static AppUser map(RegisterSenderRequest request){
//        AppUser appUser = new AppUser();
//        appUser.setFirstName(request.getFirstName());
//        appUser.setLastName(request.getLastName());
//        appUser.setEmail(request.getEmail());
//        appUser.setPassword(request.getPassword());
//        return appUser;
        return AppUser.builder()
                .email(request.getEmail())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .password(request.getPassword())
                .build();

    }
}

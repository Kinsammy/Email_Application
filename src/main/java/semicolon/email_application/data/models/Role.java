package semicolon.email_application.data.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static semicolon.email_application.data.models.Permission.*;

@RequiredArgsConstructor
public enum Role {
    ADMIN(
            Set.of(
                    ADMIN_READ,
                    ADMIN_CREATE,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    SENDER_READ,
                    SENDER_CREATE,
                    SENDER_UPDATE,
                    SENDER_DELETE,
                    RECIPIENT_READ,
                    RECIPIENT_CREATE,
                    RECIPIENT_UPDATE,
                    RECIPIENT_DELETE
            )
    ),
    SENDER(
            Set.of(
                    SENDER_READ,
                    SENDER_CREATE,
                    SENDER_UPDATE,
                    SENDER_DELETE
            )
    ),
    RECIPIENT(
            Set.of(
                    RECIPIENT_READ,
                    RECIPIENT_CREATE,
                    RECIPIENT_UPDATE,
                    RECIPIENT_DELETE
            )
    ), USER(
            Set.of(
                    SENDER_READ,
                    SENDER_CREATE,
                    SENDER_UPDATE,
                    SENDER_DELETE,
                    RECIPIENT_READ,
                    RECIPIENT_CREATE,
                    RECIPIENT_UPDATE,
                    RECIPIENT_DELETE
            )
    );



    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}

package semicolon.email_application.data.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    SENDER_READ("sender:read"),
    SENDER_CREATE("sender:create"),
    SENDER_UPDATE("sender:update"),
    SENDER_DELETE("sender:delete"),
    RECIPIENT_READ("recipient:read"),
    RECIPIENT_CREATE("recipient:create"),
    RECIPIENT_UPDATE("recipient:update"),
    RECIPIENT_DELETE("recipient:delete");

    @Getter
    private final String permission;
}

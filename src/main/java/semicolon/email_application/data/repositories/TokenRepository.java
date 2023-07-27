package semicolon.email_application.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import semicolon.email_application.data.models.AppUser;
import semicolon.email_application.data.models.Token;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query("""
select t from Token t inner join AppUser au on t.appUser.id = au.id
where au.id = :appUserId and (t.expired = false  or t.revoked = false)
"""
    )
    List<Token> findValidTokenByAppUserId(Long appUserId);
    Optional<Token> findByToken(String token);
    Optional<Token> findTokenByAppUserAndToken(AppUser appUser, String token);

    Optional<Token> findTokenByAppUser(AppUser appUser);


}

package fr.bz.jwt;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class TokenGenerator {
    public static String generateToken(String login, Set<String> roles) {
        JwtClaimsBuilder claims = Jwt.claims();
        claims.subject(login);
        claims.groups(roles);
        claims.issuedAt(System.currentTimeMillis());
        claims.expiresIn(Duration.ofHours(1));
        return Jwt.claims((Map<String, Object>) claims).sign();
    }
}

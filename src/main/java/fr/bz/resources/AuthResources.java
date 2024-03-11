package fr.bz.resources;

import fr.bz.entities.UtilisateurEntity;
import fr.bz.jwt.TokenGenerator;
import fr.bz.repositories.UtilisateurRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Collections;

@Path("/auth")
public class AuthResources {
    @Inject
    JsonWebToken jwt;
    @Inject
    UtilisateurRepository utilisateurRepository;
    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("login") String login, @FormParam("password") String password) {
        if (!isValidUser(login, password)) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        String generatedToken = TokenGenerator.generateToken(login, Collections.singleton("admin, user"));
        return Response.ok().entity(generatedToken).build();
    }

    private boolean isValidUser(String login, String password) {
        UtilisateurEntity utilisateur = utilisateurRepository.find("login", login).firstResult();
        if (utilisateur == null || !BCrypt.checkpw(password, utilisateur.getPassword())) {
            return false;
        }
        return true;
    }

}

package fr.bz.resources;

import fr.bz.entities.UtilisateurEntity;
import fr.bz.jwt.TokenGenerator;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Collections;

@Path("/auth")
public class AuthResources {
    @Inject
    JsonWebToken jwt;

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
        // Implémentation de la validation des informations d'identification de base
        // Vérifiez si l'utilisateur et le mot de passe sont valides
        // Cela peut impliquer l'accès à une base de données ou à un service externe
        // Dans cet exemple, nous faisons une validation basique
        return "admin".equals(login) && "password".equals(password);
    }

}

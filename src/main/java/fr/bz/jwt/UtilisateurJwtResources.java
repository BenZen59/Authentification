package fr.bz.jwt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/jwt")
@ApplicationScoped
public class UtilisateurJwtResources {
    public Response getJwt(){
        String jwt = "";
        return Response.ok(jwt).build();
    }

}

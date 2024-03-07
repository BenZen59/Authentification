package fr.bz.resources;

import fr.bz.entities.UtilisateurEntity;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/users")
@ApplicationScoped
public class UtilisateurResources {
    List<UtilisateurEntity> utilisateurs = new ArrayList<>();

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUtilisateurs() {
        return Response.ok(utilisateurs).build();
    }

    @Path("/create")
    @POST
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUtilisateurs(UtilisateurEntity newUtilisateur) {
        utilisateurs.add(newUtilisateur);
        return Response.ok(utilisateurs).build();
    }

    @Path("{id}")
    @DELETE
    @RolesAllowed({"admin"})
    public Response deleteUtilisateurs(@PathParam("id") int id) {
        utilisateurs.stream()
                .filter(utilisateur -> utilisateur.getIdUtilisateur() == id)
                .findFirst()
                .ifPresent(utilisateur -> utilisateurs.remove(utilisateur));
        return Response.noContent().build();
    }

}

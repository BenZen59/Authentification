package fr.bz.resources;

import fr.bz.dto.UtilisateurDto;
import fr.bz.entities.UtilisateurEntity;
import fr.bz.repositories.UtilisateurRepository;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.ArrayList;
import java.util.List;

@Path("/users")
@ApplicationScoped
public class UtilisateurResources {
    List<UtilisateurEntity> utilisateurs = new ArrayList<>();
    @Inject
    private UtilisateurRepository utilisateurRepository;

    @Path("/signup")
    @POST
    @PermitAll
    @Operation(summary = "Créer un compte", description = "Permet de crée un compte")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createCompte(UtilisateurEntity nouveauUtilisateur) {
        //On vérifie si tout les champs sont remplis
        if (nouveauUtilisateur.getNom() == null || nouveauUtilisateur.getNom().isEmpty()
                || nouveauUtilisateur.getPrenom() == null || nouveauUtilisateur.getPrenom().isEmpty()
                || nouveauUtilisateur.getLogin() == null || nouveauUtilisateur.getLogin().isEmpty()
                || nouveauUtilisateur.getPassword() == null || nouveauUtilisateur.getPassword().isEmpty()
                || nouveauUtilisateur.getRole() == null || nouveauUtilisateur.getRole().isEmpty()) {

            return Response.status(Response.Status.BAD_REQUEST).entity("Tout les champs sont obligatoires").build();
        }

        //On vérifie si le login est unique
        if (utilisateurs.stream().anyMatch(u -> u.getLogin().equals(nouveauUtilisateur.getLogin()))) {
            return Response.status(Response.Status.CONFLICT).entity("Le login est déjà utilisé").build();
        }
        // Créer le nouveau compte utilisateur
        String hashedPassword = BCrypt.hashpw(nouveauUtilisateur.getPassword(), BCrypt.gensalt());
        nouveauUtilisateur.setPassword(hashedPassword);
        nouveauUtilisateur.setIdUtilisateur(null); // On laisse JPA determiner l'id
        utilisateurRepository.persist(nouveauUtilisateur);

        // Renvoie une réponse HTTP 201 Created avec le nouvel utilisateur en tant qu'entité
        return Response.status(Response.Status.CREATED).entity(nouveauUtilisateur).build();
    }

    @GET
    @Operation(summary = "Obtenir liste des comptes", description = "Permet d'obtenir la liste des comptes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUtilisateurs() {

        List<UtilisateurEntity> utilisateurEntities = utilisateurRepository.listAll();
        return Response.ok(UtilisateurDto.toDtoList(utilisateurEntities)).build();
    }

    @Path("/{id}")
    @RolesAllowed({"admin", "user"})
    @Operation(summary = "Mettre à jour un compte", description = "Permet de changer les infos d'un compte")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUtilisateur(@PathParam("id") int id, UtilisateurEntity updatedUtilisateur) {
        UtilisateurEntity utilisateurToUpdate = utilisateurs.stream()
                .filter(utilisateur -> utilisateur.getIdUtilisateur() == id)
                .findFirst()
                .orElse(null);

        if (utilisateurToUpdate == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        utilisateurToUpdate.setNom(updatedUtilisateur.getNom());
        utilisateurToUpdate.setPrenom(updatedUtilisateur.getPrenom());
        utilisateurToUpdate.setDateNaissance(updatedUtilisateur.getDateNaissance());
        utilisateurToUpdate.setLogin(updatedUtilisateur.getLogin());
        utilisateurToUpdate.setPassword(updatedUtilisateur.getPassword());
        utilisateurToUpdate.setRole(updatedUtilisateur.getRole());

        return Response.ok(utilisateurToUpdate).build();
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

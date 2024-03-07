package fr.bz.repositories;

import fr.bz.entities.UtilisateurEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.RequestScoped;

@RequestScoped
public class UtilisateurRepository implements PanacheRepositoryBase<UtilisateurEntity, Integer> {
}

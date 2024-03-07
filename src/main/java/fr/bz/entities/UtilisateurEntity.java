package fr.bz.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity(name="UTILISATEUR")
@Table(name="UTILISATEUR")
@Getter
@Setter
public class UtilisateurEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID_UTILISATEUR")

    private int idUtilisateur;
    @Column(name="NOM")
    private String nom;
    @Column(name="PRENOM")
    private String prenom;
    @Column(name="DATE_NAISSANCE")
    private LocalDateTime dateNaissance;
    @Column(name="LOGIN")
    private String login;
    @Column(name="PASSWORD")
    private String password;
    @Column(name="ROLE")
    private String role;
}

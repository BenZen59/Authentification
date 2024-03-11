package fr.bz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.bz.entities.UtilisateurEntity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDto {
    @JsonProperty(index = 1)
    private Integer idUtilisateur;
    @JsonProperty(index = 2)
    private String nom;
    @JsonProperty(index = 3)
    private String prenom;
    @JsonProperty(index = 4)
    private LocalDateTime dateNaissance;
    @JsonProperty(index = 5)
    private String login;
    @JsonProperty(index = 6)
    private String password;
    @JsonProperty(index = 7)
    private String role;

    public UtilisateurDto(UtilisateurEntity utilisateurEntity){
            idUtilisateur = utilisateurEntity.getIdUtilisateur();
            nom = utilisateurEntity.getNom();
            prenom = utilisateurEntity.getPrenom();
            dateNaissance = utilisateurEntity.getDateNaissance();
            login = utilisateurEntity.getLogin();
            password = utilisateurEntity.getPassword();
            role = utilisateurEntity.getRole();
    }

    public static List<UtilisateurDto> toDtoList(List<UtilisateurEntity> utilisateurEntities){
        List<UtilisateurDto> utilisateurDtoList = new ArrayList<>();
        for(UtilisateurEntity utilisateurEntity : utilisateurEntities)
            utilisateurDtoList.add(new UtilisateurDto(utilisateurEntity));
            return utilisateurDtoList;

    }
}

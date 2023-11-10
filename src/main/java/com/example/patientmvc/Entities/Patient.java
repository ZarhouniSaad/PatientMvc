package com.example.patientmvc.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
@Entity
@Data //genere les getters et setters (besoin d'avoir la dependences lombok)
@NoArgsConstructor
@AllArgsConstructor
//@Data pour ajouter les getters et setters
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="",length = 50)
    @NotEmpty
    @Size(min = 4,max = 40)
    private String nom;
    @Temporal(TemporalType.DATE)
    //L'annotation @DateTimeFormat concerne le formatage des dates lors de l'extraction des données depuis
    // la base de données et lors de la présentation des données dans l'application, pas le stockage des données
    // dans la base de données.
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dateNaissance;

    private boolean malade;

    @DecimalMin("100")
    private int score;
}

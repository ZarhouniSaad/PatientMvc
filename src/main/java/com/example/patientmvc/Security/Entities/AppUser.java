package com.example.patientmvc.Security.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
    @Id
    private String userId;
    @Column(unique = true)
    public String userName;
    private String passWord;
    private boolean active;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> appRoleList=new ArrayList<>();

}

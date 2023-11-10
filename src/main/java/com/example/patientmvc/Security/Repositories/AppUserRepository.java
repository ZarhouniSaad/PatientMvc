package com.example.patientmvc.Security.Repositories;

import com.example.patientmvc.Security.Entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUserName(String userName);
}

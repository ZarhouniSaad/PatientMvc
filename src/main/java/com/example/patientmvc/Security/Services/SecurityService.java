package com.example.patientmvc.Security.Services;

import com.example.patientmvc.Security.Entities.AppRole;
import com.example.patientmvc.Security.Entities.AppUser;

public interface SecurityService {
    AppUser saveNewUser(String userName,String passWord,String rePassWord);
    AppRole saveNewRole(String roleName,String description);
    void addRoleToUser(String userName,String roleName);
    void removeRoleFromUser(String userName,String roleName);
    AppUser loadUserByUserName(String userName);

}

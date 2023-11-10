package com.example.patientmvc.Security.Services;

import com.example.patientmvc.Security.Entities.AppRole;
import com.example.patientmvc.Security.Entities.AppUser;
import com.example.patientmvc.Security.Repositories.AppRoleRepository;
import com.example.patientmvc.Security.Repositories.AppUserRepository;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
//log
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveNewUser(String userName, String passWord, String rePassWord) {
        if (!passWord.equals(rePassWord)) throw new RuntimeException("Password not match");
        String hashedPwd=passwordEncoder.encode(passWord);
        AppUser appUser=new AppUser();
        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setUserName(userName);
        appUser.setPassWord(hashedPwd);
        appUser.setActive(true);
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if (appRole!=null) throw new RuntimeException("Role"+appRole.getRoleName()+"existe");
        appRole=new AppRole();
        appRole.setRoleName(roleName);
        appRole.setDescription(description);
        appRoleRepository.save(appRole);
        return appRole;
    }

    @Override
    public void addRoleToUser(String userName, String roleName) {
        AppUser appUser=appUserRepository.findByUserName(userName);
        if (appUser==null) throw new RuntimeException("User Not found");
        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if (appRole==null) throw new RuntimeException("Role Not found");
        appUser.getAppRoleList().add(appRole);

    }

    @Override
    public void removeRoleFromUser(String userName, String roleName) {
        AppUser appUser=appUserRepository.findByUserName(userName);
        if (appUser==null) throw new RuntimeException("User Not found");
        AppRole appRole=appRoleRepository.findByRoleName(roleName);
        if (appRole==null) throw new RuntimeException("Role Not found");
        appUser.getAppRoleList().remove(appRole);

    }

    @Override
    public AppUser loadUserByUserName(String userName) {
        return appUserRepository.findByUserName(userName);
    }
}

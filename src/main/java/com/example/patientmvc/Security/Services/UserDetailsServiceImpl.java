package com.example.patientmvc.Security.Services;

import com.example.patientmvc.Security.Entities.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SecurityService securityService;
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser=securityService.loadUserByUserName(userName);


        //La programmation imperative classique --------------------------------------------------------------
        Collection<GrantedAuthority> authorities=new ArrayList<>();
        appUser.getAppRoleList().forEach(
                appRole -> {
                    //SimpleGrantedAuthority implement interface GrantedAuthority
                    SimpleGrantedAuthority authority=new SimpleGrantedAuthority(appRole.getRoleName());
                    authorities.add(authority);
                }
        );

        //En utilisant l'API Streams (programmation declarative)
        Collection<GrantedAuthority> authorities1=appUser.getAppRoleList()
                .stream().map(
                        appRole -> new SimpleGrantedAuthority(appRole.getRoleName())
                )
                .collect(Collectors.toList());

        //User de springSecurity
        //Quand spring va gérer la securité il va utiliser User de SpringSecurity alors je recupere les données de UserApp
        // de la BDD et je créé l'objet User de SpringSecurity qui implemente UserDetails
        User user=new User(appUser.getUserName(), appUser.getPassWord(),authorities );
        return user;
    }
}

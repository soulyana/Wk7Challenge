package me.afua.daveslistdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional
@Service
public class SSUDS implements UserDetailsService {

    //Authenticating users from the database

    @Autowired
    AppUserRepository users;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        //Find  user whose name matches 's' in the database

        //In order to do this, you should have a repository that can be used to retrieve users from the database:
            AppUser thisUser = users.findByName(s);


        //Find the roles associated with the user and change them into SimpleGrantedAuthorities


        //add the set of SimpleGrantedAuthorities to the new user returned by this method.

        //Watch Spring Security work!
        return null;
    }

}

package me.soulyana.wk7challenge;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;


@Transactional
@Service
public class SSUDS implements UserDetailsService {

    //Authenticating users from the database

    AppUserRepository userRepo;

    public SSUDS(AppUserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        //Find  user whose name matches 's' in the database
        //In order to do this, you should have a repository that can be used to retrieve users from the database:
            AppUser thisUser = userRepo.findByUsername(s);

            if(thisUser==null)
                throw new UsernameNotFoundException("Your login attempt was not successful, try again");

        //Find the roles associated with the user and change them into SimpleGrantedAuthorities


        //add the set of SimpleGrantedAuthorities to the new user returned by this method.

        //Watch Spring Security work!
        return new User(thisUser.getUsername(),thisUser.getPassword(),userAuthorities(thisUser));
    }

    Set<GrantedAuthority> userAuthorities(AppUser thisUser)
    {

        //Create a hash set of authorities that will be returned to Sprng security
        Set <GrantedAuthority> myAuthorities = new HashSet<>();

        for(AppRole eachRole:thisUser.getRoles())
        {
            //Convert each role into an authority that can be used by the configure method in the AppConfig class
            myAuthorities.add(new SimpleGrantedAuthority(eachRole.getName()));
        }
        return myAuthorities;

    }

}

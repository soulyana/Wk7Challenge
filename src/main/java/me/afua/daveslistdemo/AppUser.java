package me.afua.daveslistdemo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class AppUser {

    //Use the password encoder here to save encoded passwords to the database.
    //Use the @Transient annotation to omit that as a field in the database

    @Transient
    PasswordEncoder encoder;

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(unique=true)
    private String username;

    private String password;

    //Include the relationship with roles here
    @ManyToMany(mappedBy = "users",fetch =FetchType.EAGER)
    private Set<AppRole> roles;

    public AppUser() {
        //Create an empty set of roles so that it can be added to when a new user is created in memory
        this.roles=new HashSet<>();

        //Instantiate the password encoder so it can be used.
        encoder = new BCryptPasswordEncoder();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        //Encode the password to be saved
        this.password = encoder.encode(password);
    }

    public Set<AppRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<AppRole> roles) {
        this.roles = roles;
    }
}

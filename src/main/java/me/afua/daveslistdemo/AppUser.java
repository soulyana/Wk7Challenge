package me.afua.daveslistdemo;

import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
public class AppUser {

    //Use the password encoder here to save encoded passwords to the database


    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;

    @Column(unique=true)
    private String username;


    private String password;


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
        this.password = password;
    }
}

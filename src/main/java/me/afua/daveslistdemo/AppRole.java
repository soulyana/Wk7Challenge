package me.afua.daveslistdemo;

import javax.persistence.*;

@Entity
public class AppRole {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    // Don't have more than one role with the same name. It's ambiguous and unnecessary.
    @Column(unique=true)
    private String name;

    //Each role should have a relationship with a number of users


    //Don't forget your getters and setters!
}

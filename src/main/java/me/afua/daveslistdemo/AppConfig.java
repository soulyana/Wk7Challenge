package me.afua.daveslistdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity

public class AppConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    AppUserRepository users;

    @Bean
    //Using the bean for the password encoder, instead of putting it in the configure method.
    //The bean is always available in the context path once the application is run.
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    //Pass a repository to the SSUDS, so that only one repository is used for authentication - instead of creating one every time


    //Override the userDetailServiceBean method to return a new SSUDS to authenticate with

    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return new SSUDS(users);
    }

    private String[] everyone = {"/","/viewrooms","/newindex","/assets/**","/signup","/updateroom","/pkindex"};
    private String[] administrators = {"/addroom","/saveroom","/**","/h2/**"};
    private String[] dave = administrators;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers(everyone).permitAll()
                .antMatchers(dave).hasAuthority("DAVE")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        //FOR H2
        http.csrf().disable();

        http       .headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Set up in memory authentication. REMOVE THIS for production deployments
        auth.inMemoryAuthentication().withUser("DaveWolf")
                .password(passwordEncoder().encode("begreat")).authorities("DAVE")
                .and()
                .withUser("adminuser").password("admin").authorities("ADMIN")
                .and()
                .passwordEncoder(passwordEncoder());;

        //Get user details from the SS User Details Service for the user who is trying to log in.
        auth.userDetailsService(userDetailsServiceBean()).passwordEncoder(passwordEncoder());


    }

}

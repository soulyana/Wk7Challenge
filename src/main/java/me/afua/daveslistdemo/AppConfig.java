package me.afua.daveslistdemo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppConfig extends WebSecurityConfigurerAdapter {

    @Bean
    //Using the bean for the password encoder, instead of putting it in the configure method.
    //The bean is always available in the context path once the application is run.
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    //Pass a repository to the SSUDS, so that only one repository is used for authentication - instead of creating one every time


    private String[] everyone = {"/","/viewrooms","/newindex","/assets/**"};
    private String[] administrators = {"/addroom","/saveroom","/**","/h2/**","/assets/**"};
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
        auth.inMemoryAuthentication().withUser("DaveWolf")
                .password(passwordEncoder().encode("begreat")).authorities("DAVE")
        .and()
        .passwordEncoder(passwordEncoder());
    }

}

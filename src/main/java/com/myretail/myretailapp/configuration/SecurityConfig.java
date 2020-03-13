/*
* Security configuration and global user settings.
* */
package com.myretail.myretailapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    /*
    * Access permissions for each role
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                //HTTP Basic authentication
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/myretail/product/**").hasRole("USER")
                .antMatchers(HttpMethod.POST, "/myretail/product").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/myretail/product/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.PATCH, "/myretail/product/**").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/myretail/product/**").hasRole("ADMIN")
                .and()
                .csrf().disable()
                .formLogin().disable();
    }

    /*
     *  Global default basic auth credentials
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception
    {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER")
                .and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");
    }
}

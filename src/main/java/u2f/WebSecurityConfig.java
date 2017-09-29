/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package u2f;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author ENESTORO
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                    .antMatchers("/resources/**", "/registration").permitAll().anyRequest().permitAll()
//                    .antMatchers("/registration-u2f").permitAll()
//                    .antMatchers("/authenticate").permitAll()
//                    .antMatchers("/success").permitAll()
//                    .anyRequest().authenticated()
//                    .and()
//                .formLogin()
//                    .loginPage("/login")
//                    .permitAll()
//                    .and()
//                .logout()
//                    .permitAll()
//                    .and()
//                .authorizeRequests()
//                .antMatchers("/console/**").permitAll();

//         http
//                .authorizeRequests()
//                    .antMatchers("/resources/**").permitAll().anyRequest().permitAll()
//                    .antMatchers("/registration").permitAll()
//                    .antMatchers("/registration-u2f").permitAll()
//                    .antMatchers("/authenticate").permitAll()
//                    .antMatchers("/success").permitAll()
////                    .anyRequest().authenticated()
//                    .and()
//                .authorizeRequests()
//                .antMatchers("/console/**").permitAll();
        http.authorizeRequests().antMatchers("/").permitAll().and().csrf().disable();
         http.csrf().disable();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}

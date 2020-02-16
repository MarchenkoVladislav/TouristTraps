package com.netcracker.config;

import com.netcracker.db.entity.User;
import com.netcracker.db.repository.UsersRepository;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.time.LocalDateTime;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/", "../uploads/**", "/photos1","/users/**", "/img/**", "/img/", "/js/**", "/routes/**","/reviews/**",
                        "/static/**", "/css/**").permitAll()
                .anyRequest().authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll().and()
                .csrf().disable();
    }



    @Bean
    public PrincipalExtractor principalExtractor(UsersRepository usersRepository) {
        return map -> {
            String id = (String) map.get("sub");

            User user = usersRepository.findById(id).orElseGet(() -> {
                User newUser = new User();

                newUser.setUserID(id);
                newUser.setUserEmail((String) map.get("email"));
                newUser.setUserName((String) map.get("name"));
                return newUser;
            });

            user.setLastVisit(LocalDateTime.now());

            return usersRepository.save(user);
        };
    }
}



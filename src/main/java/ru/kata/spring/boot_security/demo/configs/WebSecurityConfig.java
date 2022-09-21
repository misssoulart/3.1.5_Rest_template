package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final SuccessUserHandler successUserHandler;

    private final UserServiceImpl userService;

    @Autowired
    public WebSecurityConfig(SuccessUserHandler successUserHandler, UserServiceImpl userService) {
        this.successUserHandler = successUserHandler;
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests() //авторизация запроса
                .antMatchers("/admin/**").hasRole("ADMIN") // доступ для url admin
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN") //доступ для url user (две роли у пользователя)
                .antMatchers("/", "/index").permitAll()
                .anyRequest().authenticated() //все запросы должны быть аутентифицированы и авторизованы
                .and()
                .formLogin().successHandler(successUserHandler)  //форма для ввода логина-пароля, по дефолту это /login
                .permitAll() //доступно всем
                .and()
                .logout()// настройка выхода
                .permitAll();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
    }

}
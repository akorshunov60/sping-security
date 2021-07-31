package com.example.spingsecurity.configs;

import com.example.spingsecurity.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeRequests()
                .antMatchers("/users/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                .antMatchers("/products/**").hasAnyRole("ADMIN", "MANAGER")
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/");

    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui/**",
                "/webjars/**"
        );
    }
/*
    In-Memory
    @Bean
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2y$12$rR9i3Ct3hvXAH9tC6t.Ayu6WHMTli.u7EbWbw.5/Ah8Ab.UEPWBQK")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2y$12$c1y8ELB6YhZNzO4iWRh3AO4lJOYYahcmV3nrQWkXDdaOiC9vSwjAG")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }
 */
/*
    JDBC Authentication
    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource) {
        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2y$12$rR9i3Ct3hvXAH9tC6t.Ayu6WHMTli.u7EbWbw.5/Ah8Ab.UEPWBQK")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2y$12$c1y8ELB6YhZNzO4iWRh3AO4lJOYYahcmV3nrQWkXDdaOiC9vSwjAG")
                .roles("ADMIN", "USER")
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        if(jdbcUserDetailsManager.userExists(user.getUsername())){
            jdbcUserDetailsManager.deleteUser(user.getUsername());
        }
        if(jdbcUserDetailsManager.userExists(admin.getUsername())){
            jdbcUserDetailsManager.deleteUser(admin.getUsername());
        }
        jdbcUserDetailsManager.createUser(user);
        jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }
 */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);

        return authenticationProvider;
    }
}

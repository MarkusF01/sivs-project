package at.fehringer.authentication.config;

import at.fehringer.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    UserService userService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder
                .userDetailsService(userService)
                .passwordEncoder(bCryptPasswordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(authenticationManager)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "/pages/create_account",
                            "/scripts/*",
                            "/api/users",
                            "/api/users/*/*").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .logout(Customizer.withDefaults())

                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(IF_REQUIRED) //
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession) //
                )
                .build();
    }
}
package kg.edu.alatoo.springWeb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Throwable {
        security
                //.csrf().disable()
                .csrf().ignoringRequestMatchers("/api/**").and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll().and()

                .formLogin(login ->
                        login.loginPage("/login")
                                .permitAll()
                                .usernameParameter("username")
                                .passwordParameter("password")
                )

                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers("/", "/login","/logout", "/css/**", "/img/**", "/js/**", "/api/v1/**").permitAll()
                                .requestMatchers("/admin").hasRole("ADMIN")
                                .anyRequest().authenticated()
                )
        ;


        return security.build();

    }
}

package kg.edu.alatoo.springWeb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity security) throws Throwable {
        security
                //.csrf().disable()
                .csrf().ignoringRequestMatchers("/api/**", "/filter").and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll().and()

                .formLogin(login ->
                        login.loginPage("/login")
                                .permitAll()
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/")
                                .failureForwardUrl("/error")
                                .permitAll()
                )

                .authorizeHttpRequests(req ->
                        req
                                .requestMatchers( "error", "/login","/", "/css/**", "/img/**", "/js/**", "/api/v1/**", "/register/**", "/forgot_password/**", "/reset_password/**", "/webjars/**").permitAll()
                                .requestMatchers("/admin", "/login").hasRole("ADMIN")

                                .anyRequest().authenticated()
                )
        ;


        return security.build();

    }
//    UserDetailsService userDetailsService() {
//        return new InMemoryUserDetailsManager(
//                User.withDefaultPasswordEncoder()
//                        .username("user").password("user").roles("USER").build()
//        );
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}

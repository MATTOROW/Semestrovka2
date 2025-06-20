package ru.itis.semesterwork.second.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import ru.itis.semesterwork.second.security.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/register", "/api/v1/accounts/create", "/login", "/"))
                .formLogin(form -> form
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .defaultSuccessUrl("/projects")
                        .permitAll()
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/").permitAll()
                        .requestMatchers("/api/v1/accounts/{username}", "/api/v1/accounts/create").permitAll()
                        .requestMatchers("/api/v1/accounts/current",
                                "/api/v1/accounts/update/{username}",
                                "/api/v1/accounts/patch/{username}",
                                "/api/v1/accounts/delete/{username}"
                        ).authenticated()
                        .requestMatchers(
                                "/css/**", "/js/**", "/img/**"
                        ).permitAll()
                        .requestMatchers("/forgot-password", "/reset-password").permitAll()
                        .requestMatchers("/api/v1/auth/forgot-password", "/api/v1/auth/reset-password").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
                .exceptionHandling(
                        exception -> exception
                                // если запрос в /api/** и неавторизован — вернём 401 без редиректа
                                .defaultAuthenticationEntryPointFor(
                                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                        new AntPathRequestMatcher("/api/**")
                                )
                )
                .httpBasic(Customizer.withDefaults())
                .rememberMe(remember -> remember
                    .key("Ahsase213_dadd3AAd__da123ff")
                    .rememberMeParameter("remember-me")
                    .tokenValiditySeconds(3 * 24 * 60 * 60)
                    .userDetailsService(userDetailsService)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityContextLogoutHandler securityContextLogoutHandler() {
        return new SecurityContextLogoutHandler();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}

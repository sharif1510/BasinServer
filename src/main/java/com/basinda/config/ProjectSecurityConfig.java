package com.basinda.config;

import com.basinda.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import java.util.List;

@Configuration
public class ProjectSecurityConfig {

    private final UserLoadService userLoadService;

    public ProjectSecurityConfig(UserLoadService userLoadService) {
        this.userLoadService = userLoadService;
    }

    List<String> whiteListUrl = List.of("/websocket","/auth/register", "/auth/login", "/divisions", "/districts/**", "/upozilas/**", "/pourosovas/**", "/verify/**","/upload/flat/**","download/flat/**","/admin/login");

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        r -> r.requestMatchers(
                                        whiteListUrl.stream()
                                                .map(AntPathRequestMatcher::new)
                                                .toArray(RequestMatcher[]::new)
                                )
                                .permitAll()
                                .requestMatchers("/admin/**").hasAuthority("eSuperAdmin")
                                .requestMatchers("/flats/create").hasAuthority("eLandlord")
                                .anyRequest()
                                .authenticated()
                ).addFilterBefore(new JwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                //.requestMatchers("/actuator","/auth/register","/auth/login").permitAll()
                .httpBasic()
                .and().formLogin()
                .and().build();
    }

    /**@Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userLoadService);
        authenticationProvider.setUserDetailsService(adminUserLoadService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }*/

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class).build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(whiteListUrl.toArray(String[]::new));
    }
}
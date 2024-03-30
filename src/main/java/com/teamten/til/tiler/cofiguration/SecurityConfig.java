package com.teamten.til.tiler.cofiguration;

import com.teamten.til.tiler.service.TilerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final TilerService tilerService;
    @Value("${jwt.secret}")
    private String secretKey;
    // CORS 설정
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("http://localhost")); // 허용할 origin
            config.setAllowCredentials(true);
            return config;
        };
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(csrf -> csrf.disable())
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/api/**").permitAll()
                )
                 .sessionManagement((sessionManagement) ->
                         sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                 .addFilterBefore(new JwtFilter(tilerService, secretKey), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }
}
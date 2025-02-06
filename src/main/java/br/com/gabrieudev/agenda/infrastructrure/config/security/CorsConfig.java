package br.com.gabrieudev.agenda.infrastructrure.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {
    @Value("${api.base-url}")
    private String apiUrl;
    @Value("${frontend.base-url}")
    private String frontendUrl;

    @Bean
    CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        config.setAllowCredentials(true);
        
        config.setAllowedOriginPatterns(Arrays.asList(apiUrl, frontendUrl));

        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Accept"));
        
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));

        config.setMaxAge(3600L);
        
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}

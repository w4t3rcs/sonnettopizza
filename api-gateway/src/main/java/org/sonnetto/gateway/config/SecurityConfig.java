package org.sonnetto.gateway.config;

//import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Value("${spring.security.unsecured-paths}")
    private String[] unsecuredPaths;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchangeSpec ->
                        exchangeSpec.pathMatchers(unsecuredPaths)
                                .permitAll()
                                .anyExchange()
                                .authenticated())
                .oauth2ResourceServer(serverSpec -> serverSpec.jwt(Customizer.withDefaults()))
                .build();
    }
}

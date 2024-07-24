package org.sonnetto.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity, ReactiveJwtDecoder jwtDecoder) {
        return httpSecurity.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchangeSpec ->
//                        exchangeSpec.pathMatchers("/eureka/**")
//                                .permitAll()
//                                .anyExchange()
//                                .authenticated())
                        exchangeSpec.anyExchange()
                                .permitAll())
                .oauth2ResourceServer(serverSpec -> serverSpec.jwt(jwtSpec -> jwtSpec.jwtDecoder(jwtDecoder)))
                .build();
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder(@Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}") String jwkSetUri) {
        return NimbusReactiveJwtDecoder.withJwkSetUri(jwkSetUri).jwsAlgorithm(SignatureAlgorithm.RS256).build();
    }
}

package com.ucamp.api.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationsFilter
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/auth/signup", "/auth/login").permitAll()

                    .requestMatchers(HttpMethod.GET, "/boards", "/boards/**").permitAll()

                    .requestMatchers(HttpMethod.POST, "/boards").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/boards/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/boards/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/boards/**").authenticated()

                    .anyRequest().permitAll()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .cors(Customizer.withDefaults())

        return http.build()
    }
}

package com.ucamp.api.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationsFilter
) {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("http://localhost:5173")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        configuration.allowedHeaders = listOf("*")
        configuration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin { it.disable() }
            .httpBasic { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/auth/signup", "/auth/login", "/auth/refresh").permitAll()

                    .requestMatchers(HttpMethod.GET, "/boards").permitAll()
                    .requestMatchers(HttpMethod.GET, "/boards/*").permitAll()
                    .requestMatchers(HttpMethod.GET, "/boards/*/posts").permitAll()

                    .requestMatchers(HttpMethod.POST, "/boards").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/boards/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/boards/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/boards/**").authenticated()

                    .requestMatchers(HttpMethod.GET, "/posts", "/posts/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/posts").authenticated()
                    .requestMatchers(HttpMethod.PUT, "/posts/**").authenticated()
                    .requestMatchers(HttpMethod.PATCH, "/posts/**").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/posts/**").authenticated()

                    .requestMatchers(HttpMethod.GET, "/comments/posts/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/comments").authenticated()
                    .requestMatchers(HttpMethod.DELETE, "/comments/**").authenticated()

                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .cors(Customizer.withDefaults())

        return http.build()
    }
}

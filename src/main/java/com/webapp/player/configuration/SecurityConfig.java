package com.webapp.player.configuration;

import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http
//            .cors().and()
//            .authorizeRequests()
//            .antMatchers("/api/audio/**").permitAll() // Or add authentication
//            .anyRequest().authenticated()
//            .and()
//            .csrf().disable();
//  }
//
//  @Bean
//  public CorsConfigurationSource corsConfigurationSource() {
//    CorsConfiguration config = new CorsConfiguration();
//    config.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Next.js origin
//    config.setAllowedMethods(Arrays.asList("GET", "HEAD"));
//    config.setAllowedHeaders(Arrays.asList("Range", "Content-Type"));
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", config);
//    return source;
//  }
//}
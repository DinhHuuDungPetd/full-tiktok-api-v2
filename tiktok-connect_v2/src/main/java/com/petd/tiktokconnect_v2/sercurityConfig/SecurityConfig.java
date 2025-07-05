package com.petd.tiktokconnect_v2.sercurityConfig;

import com.petd.tiktokconnect_v2.exception.CustomAccessDeniedHandler;
import com.petd.tiktokconnect_v2.helper.SecurityConstants;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SecurityConfig {

  CustomPreFilter preFilter;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(){
    return new CustomUserDetailsService();
  }

  @Bean
  public AuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder());
    provider.setUserDetailsService(userDetailsService());
    return provider;
  }

  String[] PUBLIC_USER_URL = { "/login" };

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http,  CustomAccessDeniedHandler accessDeniedHandler) throws Exception {
    http.cors(cors -> cors.configurationSource(request -> {
      var corsConfig = new CorsConfiguration();
      corsConfig.addAllowedOriginPattern("*");
      corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
      corsConfig.setAllowedHeaders(List.of("*"));
      corsConfig.setAllowCredentials(true);
      return corsConfig;
    }));

    http
        .authorizeHttpRequests(request -> request
            .requestMatchers(SecurityConstants.PUBLIC_URLS).permitAll()
            .requestMatchers("/user/admin/**", "/admin").hasAnyAuthority("role/Admin")
            .anyRequest().authenticated()
        )
        .csrf(AbstractHttpConfigurer::disable)
        .authenticationProvider(authenticationProvider())
        .logout(AbstractHttpConfigurer::disable)
        .exceptionHandling(ex -> ex
            .accessDeniedHandler(accessDeniedHandler)
        )
        .addFilterBefore(preFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}

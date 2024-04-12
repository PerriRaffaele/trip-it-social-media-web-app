package ch.usi.inf.bsc.sa4.tripit.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.authorizeHttpRequests(
            a ->
                a.requestMatchers(
                        "/",
                        "/error",
                        "/webjars/**",
                        "/users",
                        "/users/getUser",
                        "/users/{id}",
                        "/users/is-logged",
                        "/**",
                        "/users/{id}/privacy")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .csrf()
        .disable()
        .oauth2Login()
        .defaultSuccessUrl("http://localhost:3000/profile", true)
        .and()
        .logout()
        .logoutSuccessUrl("http://localhost:3000/")
        .permitAll();
    return http.build();
  }
}

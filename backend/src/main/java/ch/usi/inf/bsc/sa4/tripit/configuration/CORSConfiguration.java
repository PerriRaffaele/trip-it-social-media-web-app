package ch.usi.inf.bsc.sa4.tripit.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/** The CORS configuration. */
@Configuration
@EnableWebMvc
public class CORSConfiguration implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins("http://localhost:8080", "http://localhost:3000")
        .allowedHeaders("*")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD")
        // add maxAge
        .maxAge(-1)
        .allowCredentials(false);
  }
}

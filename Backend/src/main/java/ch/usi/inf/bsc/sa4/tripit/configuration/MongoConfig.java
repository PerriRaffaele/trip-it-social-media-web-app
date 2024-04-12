package ch.usi.inf.bsc.sa4.tripit.configuration;
//

import ch.usi.inf.bsc.sa4.tripit.converter.ZonedDateTimeReadConverter;
import ch.usi.inf.bsc.sa4.tripit.converter.ZonedDateTimeWriteConverter;
import ch.usi.inf.bsc.sa4.tripit.repository.ActivityRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//

/** The type Mongo config. */
@Configuration
@EnableMongoRepositories(basePackageClasses = {ActivityRepository.class})
@Profile("production")
public class MongoConfig {

  @Value("${spring.data.mongodb.uri}")
  private String uri;

  private final List<Converter<?, ?>> converters = new ArrayList<>();

  /**
   * Mongo client mongo client.
   *
   * @return the mongo client
   */
  @Bean
  public MongoClient mongoClient() {
    return MongoClients.create(this.uri);
  }

  /**
   * Custom conversions mongo custom conversions.
   *
   * @return the mongo custom conversions
   */
  @Bean
  public MongoCustomConversions customConversions() {
    converters.add(new ZonedDateTimeWriteConverter());
    converters.add(new ZonedDateTimeReadConverter());
    return new MongoCustomConversions(converters);
  }
}

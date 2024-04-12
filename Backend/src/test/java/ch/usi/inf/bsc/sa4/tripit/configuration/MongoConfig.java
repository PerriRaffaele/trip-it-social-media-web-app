package ch.usi.inf.bsc.sa4.tripit.configuration;
//

import ch.usi.inf.bsc.sa4.tripit.converter.ZonedDateTimeReadConverter;
import ch.usi.inf.bsc.sa4.tripit.converter.ZonedDateTimeWriteConverter;
import ch.usi.inf.bsc.sa4.tripit.repository.ActivityRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//

@Configuration
@EnableMongoRepositories(basePackageClasses = {ActivityRepository.class})
@Profile("test")
public class MongoConfig {

  private final List<Converter<?, ?>> converters = new ArrayList<Converter<?, ?>>();

  @Bean
  public MongoCustomConversions customConversions() {
    converters.add(new ZonedDateTimeWriteConverter());
    converters.add(new ZonedDateTimeReadConverter());
    return new MongoCustomConversions(converters);
  }
}
